/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;

import co.edu.uniandes.csw.paseadores.dtos.RecorridoDetailDTO;
import co.edu.uniandes.csw.paseadores.ejb.PuntoLogic;
import co.edu.uniandes.csw.paseadores.ejb.RecorridoLogic;
import co.edu.uniandes.csw.paseadores.ejb.RecorridoPuntoLogic;
import co.edu.uniandes.csw.paseadores.entities.RecorridoEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import java.util.logging.Level;
import java.util.logging.Logger;
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

/**
 *
 * @author Nicolas Urrego Sandoval <n.urrego at uniandes.edu.co>
 */
@Path("puntos/{puntosId: \\d+}/recorrido")
@Produces("application/json")
@Consumes("application/json")
public class RecorridoPuntoResource {
    
    private static final Logger LOGGER = Logger.getLogger(RecorridoPuntoResource.class.getName());
    
    @Inject
    private RecorridoPuntoLogic recorridoPuntoLogic;

    @Inject
    private RecorridoLogic recorridoLogic;
    
    @Inject
    private PuntoLogic puntoLogic;
    
    @POST
    @Path("{recorridosId: \\d+}")
    public RecorridoDetailDTO addRecorrido(@PathParam("puntosId") Long puntosId, @PathParam("recorridosId") Long recorridosId) {
        if (recorridoLogic.getRecorrido(recorridosId) == null) {
            throw new WebApplicationException("El recurso /recorridos/" + recorridosId + " no existe.", 404);
        }
        if(puntoLogic.getPunto(puntosId) == null)
            throw new WebApplicationException("El recurso/puntos/" + puntosId + " no existe", 404);
        RecorridoDetailDTO recorridoDetailDTO = new RecorridoDetailDTO(recorridoPuntoLogic.addRecorrido(recorridosId, puntosId));
        return recorridoDetailDTO;
    }
    
    @GET
    public RecorridoDetailDTO getRecorrido(@PathParam("puntosId") Long puntosId) {
        LOGGER.log(Level.INFO, "getRecorrido en RecorridoPuntoResource");
        if(puntoLogic.getPunto(puntosId) == null)
            throw new WebApplicationException("El recurso     /puntos/" + puntosId + " no existe. ", 404);
        RecorridoEntity recorridoEntity = recorridoPuntoLogic.getRecorrido(puntosId);
        if (recorridoEntity == null) {
            throw new WebApplicationException("El recurso /puntos/" + puntosId + "/recorrido no existe.", 404);
        }
                  LOGGER.log(Level.INFO, "getRecorrido en RecorridoPuntoResource antes de hacer el json");
        RecorridoDetailDTO recorridoDetailDTO = new RecorridoDetailDTO(recorridoEntity);
        return recorridoDetailDTO;
    }
    
    @PUT
    @Path("{recorridosId: \\d+}")
    public RecorridoDetailDTO replaceRecorrido(@PathParam("puntosId") Long puntosId, @PathParam("recorridosId") Long recorridosId) {
        if(puntoLogic.getPunto(puntosId) == null)
            throw new WebApplicationException("El recurso/puntos/" + puntosId + " no existe       .", 404);
        if (recorridoLogic.getRecorrido(recorridosId) == null) {
            throw new WebApplicationException("El recurso /recorridos/" + recorridosId + "  no existe.", 404);
        }
        RecorridoDetailDTO recorridoDetailDTO = new RecorridoDetailDTO(recorridoPuntoLogic.replaceRecorrido(puntosId, recorridosId));
        if(puntoLogic.getPunto(puntosId) == null)
            throw new WebApplicationException("El recurso            /puntos/" + puntosId + " no existe  .", 404);
        return recorridoDetailDTO;
    }
    
    @DELETE
    public void removePunto(@PathParam("puntosId") Long puntosId) throws BusinessLogicException {
        recorridoPuntoLogic.removePunto(puntosId);
    }
}
