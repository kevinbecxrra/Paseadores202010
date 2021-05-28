/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;

import co.edu.uniandes.csw.paseadores.dtos.CalificacionDTO;
import co.edu.uniandes.csw.paseadores.ejb.CalificacionLogic;
import co.edu.uniandes.csw.paseadores.entities.CalificacionEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Alvaro Plata
 */
@Path("/calificaciones")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class CalificacionResource {
    
    private static final Logger LOGGER = Logger.getLogger(CalificacionResource.class.getName());
    
    @Inject
    private CalificacionLogic calificacionLogic;
    
    @POST
    public CalificacionDTO createCalificacion(CalificacionDTO calificacion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CalificacionResource createCalificacion: input: {0}", calificacion);
        CalificacionEntity calificacionEntity = calificacion.toEntity(false);
        CalificacionEntity entityGuardada = calificacionLogic.createCalificacion(calificacionEntity);
        CalificacionDTO calificacionDTO = new CalificacionDTO(entityGuardada, false);
        LOGGER.log(Level.INFO, "CalificacionResource createCalificacion: output: {0}", calificacionDTO);
        return calificacionDTO;
    }
    
    @GET
    @Path("{calificacionId: \\d+}")
    public CalificacionDTO getCalificacion(@PathParam("calificacionId") Long calificacionId) {
        LOGGER.log(Level.INFO, "CalificacionResource getCalificacion: input: {0}", calificacionId);
        CalificacionEntity califiacionEntity = calificacionLogic.getCalificacion(calificacionId);
        if (califiacionEntity == null) {
            throw new WebApplicationException("El recurso /calificaciones/" + calificacionId + " no existe.", 404);
        }
        CalificacionDTO detailDTO = new CalificacionDTO(califiacionEntity, false);
        LOGGER.log(Level.INFO, "CalificacionResource getCalificacion: output: {0}", detailDTO);
        return detailDTO;
    }
    
    @PUT
    @Path("{calificacionId: \\d+}")
    public CalificacionDTO updateCalificacion(@PathParam("calificacionId") Long calificacionId, CalificacionDTO calificacion) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "CalificacionResource updateCalificacion: input: calificacionId: {0} , calificacion: {1}", new Object[]{calificacionId, calificacion});
        calificacion.setId(calificacionId);
        if (calificacionLogic.getCalificacion(calificacionId) == null) {
            throw new WebApplicationException("El recurso /calificaciones/" + calificacionId + " no existe", 404);
        }
        CalificacionDTO detailDTO = new CalificacionDTO(calificacionLogic.updateCalificacion(calificacionId, calificacion.toEntity(false)), false);
        LOGGER.log(Level.INFO, "CalificacionResource updateCalificacion: output: {0}", detailDTO);
        return detailDTO;
    }
    
    @DELETE
    @Path("{calificacionId: \\d+}")
    public void deleteCalificacion(@PathParam("calificacionId") Long calificacionId) {
        LOGGER.log(Level.INFO, "CalificacionResource deleteCalificacion: input: {0}", calificacionId);
        if (calificacionLogic.getCalificacion(calificacionId) == null) {
            throw new WebApplicationException("El recurso /calificaciones/" + calificacionId + " no existe  .", 404);
        }
        calificacionLogic.deleteCalificacion(calificacionId);
        LOGGER.info("CalificacionResource deleteCalificacion: output: void");
    }
    
    @Path("{calificacionId: \\d+}/contratosPaseo")
    public Class<ContratoPaseoResource> getContratoPaseoResource(@PathParam("calificacionId")Long calificacionId){
        if(calificacionLogic.getCalificacion(calificacionId) == null){
            throw new WebApplicationException("El recuros  /calificacion/"+calificacionId + "/paseos no existe",404);
        }
        return ContratoPaseoResource.class;
    }
    
}
