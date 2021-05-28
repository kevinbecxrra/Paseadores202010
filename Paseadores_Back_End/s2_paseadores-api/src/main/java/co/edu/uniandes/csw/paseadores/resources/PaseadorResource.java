/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;

import co.edu.uniandes.csw.paseadores.dtos.PaseadorDTO;
import co.edu.uniandes.csw.paseadores.dtos.PaseadorDetailDTO;
import co.edu.uniandes.csw.paseadores.ejb.PaseadorLogic;
import co.edu.uniandes.csw.paseadores.entities.PaseadorEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
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

/**
 *
 * @author Daniel Mateo Guatibonza Solano
 */
@Path("paseadores")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class PaseadorResource {
    
    @Inject
    private PaseadorLogic paseadorLogic;
    
    @POST
    public PaseadorDTO createPaseador(PaseadorDTO paseador) throws BusinessLogicException
    {
        paseador = new PaseadorDTO(paseadorLogic.createPaseador(paseador.toEntity()));
        return paseador;
    }
    
    @GET
    public List<PaseadorDetailDTO> getPaseadores()
    {
        return listEntity2DetailDTO(paseadorLogic.getPaseadores());
    }
    
    @GET
    @Path("{paseadorId: \\d+}")
    public PaseadorDetailDTO getPaseador(@PathParam("paseadorId") Long paseadorId)
    {
        PaseadorEntity paseadorEntity = paseadorLogic.getPaseador(paseadorId);
        if(paseadorEntity == null){
           throw new WebApplicationException(" El recurso /paseadores/" + paseadorId + " no existe.", 404);
        }
        return new PaseadorDetailDTO(paseadorEntity);
    }
    
    @GET
    @Path("/identificacion/{identificacionPaseador: \\d+}")
    public PaseadorDetailDTO getPaseadorByIdentificacion(@PathParam("identificacionPaseador") String identificacion)
    {
        PaseadorEntity paseadorEntity = paseadorLogic.getPaseadorByIdentificacion(identificacion);
        if(paseadorEntity == null){
            throw new WebApplicationException(" El recurso /paseadores/identificacion" + identificacion + " no existe.", 404);
        }
        return new PaseadorDetailDTO(paseadorEntity);
    }
    
    @GET
    @Path("/correo/{correoPaseador: .+}")
    public PaseadorDetailDTO getPaseadorByCorreo(@PathParam("correoPaseador") String correo)
    {
        PaseadorEntity paseadorEntity = paseadorLogic.getPaseadorByCorreo(correo);
        if(paseadorEntity == null){
           throw new WebApplicationException(" El recurso /paseadores/correo" + correo + " no existe.", 404);
        }
        return new PaseadorDetailDTO(paseadorEntity);
    }
    
    @GET
    @Path("/calificaciones/{inf: [0-9].[0-9]}/{sup: [0-9].[0-9]}")
    public List<PaseadorDTO> getPaseadorByCalificacionGlobalInRange(@PathParam("inf") Double inf, @PathParam("sup") Double sup)
    {
        return listEntity2DTO(paseadorLogic.getPaseadorByCalificacionGlobalInRange(inf, sup));
    }
    
    @PUT
    @Path("{paseadorId: \\d+}")
    public PaseadorDetailDTO updatePaseador(@PathParam("paseadorId") Long paseadorId, PaseadorDTO paseador) throws BusinessLogicException
    {
        paseador.setId(paseadorId);
        if(paseadorLogic.getPaseador(paseadorId) == null) 
        {
            throw new WebApplicationException("El recurso /paseadores/" + paseadorId + " no existe.", 404);
        }
        return new PaseadorDetailDTO(paseadorLogic.updatePaseador(paseador.toEntity()));
    }
    
    @DELETE
    @Path("{paseadorId: \\d+}")
    public void deletePaseador(@PathParam("paseadorId") Long paseadorId)
    {
        if (paseadorLogic.getPaseador(paseadorId) == null) {
            throw new WebApplicationException("El recurso /paseadores/" + paseadorId + " no existe.", 404);
        }
        paseadorLogic.deletePaseador(paseadorId);
    }
    
    @Path("{paseadorId: \\d+}/horarios")
    public Class<PaseadorHorarioResource> getPaseadorHorariosResource(@PathParam("paseadorId") Long paseadorId) {
        if (paseadorLogic.getPaseador(paseadorId) == null) {
            throw new WebApplicationException("El recurso /paseadores/" + paseadorId + " no existe.", 404);
        }
        return PaseadorHorarioResource.class;
    }
    
    @Path("{paseadorId: \\d+}/paseos")
    public Class<PaseoPaseadorResource> getPaseoPaseadorResource(@PathParam("paseadorId") Long paseadorId) {
        if (paseadorLogic.getPaseador(paseadorId) == null) {
            throw new WebApplicationException("El recurso /paseadores/" + paseadorId + " no existe.", 404);
        }
        return PaseoPaseadorResource.class;
    }
    
    @Path("{paseadorId: \\d+}/pagosPaseador")
    public Class<PagoPaseadorPaseadorResource> getPagoPaseadorPaseadorResource(@PathParam("paseadorId") Long paseadorId) {
        if (paseadorLogic.getPaseador(paseadorId) == null) {
            throw new WebApplicationException("El recurso /paseadores/" + paseadorId + " no existe.", 404);
        }
        return PagoPaseadorPaseadorResource.class;
    }
    
    private List<PaseadorDetailDTO> listEntity2DetailDTO(List<PaseadorEntity> entityList) {
        List<PaseadorDetailDTO> list = new ArrayList<>();
        for(PaseadorEntity entity : entityList) {
            list.add(new PaseadorDetailDTO(entity));
        }
        return list;
    }
    
    private List<PaseadorDTO> listEntity2DTO(List<PaseadorEntity> entityList) {
        List<PaseadorDTO> list = new ArrayList<>();
        for(PaseadorEntity entity : entityList) {
            list.add(new PaseadorDTO(entity));
        }
        return list;
    }
}
