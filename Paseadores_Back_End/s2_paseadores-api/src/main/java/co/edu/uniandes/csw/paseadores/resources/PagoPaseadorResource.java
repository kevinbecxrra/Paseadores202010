/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;

import co.edu.uniandes.csw.paseadores.dtos.PagoPaseadorDTO;
import co.edu.uniandes.csw.paseadores.dtos.PagoPaseadorDetailDTO;
import co.edu.uniandes.csw.paseadores.ejb.PagoPaseadorLogic;
import co.edu.uniandes.csw.paseadores.entities.PagoPaseadorEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.Date;
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
@Path("pagosPaseadores")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class PagoPaseadorResource {
    
    @Inject
    private PagoPaseadorLogic pagoPaseadorLogic;
    
    @POST
    public PagoPaseadorDTO createPaseador(PagoPaseadorDTO pagoPaseador) throws BusinessLogicException
    {
        pagoPaseador = new PagoPaseadorDTO(pagoPaseadorLogic.createPagoPaseador(pagoPaseador.toEntity()));
        return pagoPaseador;
    }
    
    @GET
    public List<PagoPaseadorDetailDTO> getPagosPaseadores()
    {
        return listEntity2DetailDTO(pagoPaseadorLogic.getPagosPaseadores());
    }
    
    @GET
    @Path("{pagoPaseadorId: \\d+}")
    public PagoPaseadorDetailDTO getPagoPaseador(@PathParam("pagoPaseadorId") Long pagoPaseadorId)
    {
        PagoPaseadorEntity pagoPaseadorEntity = pagoPaseadorLogic.getPagoPaseador(pagoPaseadorId);
        if(pagoPaseadorEntity == null){
           throw new WebApplicationException(" El recurso /pagosPaseadores/" + pagoPaseadorId + " no existe.", 404);
        }
        return new PagoPaseadorDetailDTO(pagoPaseadorEntity);
    }
    
    @GET
    @Path("/referencia/{referenciaPago: \\d+}")
    public PagoPaseadorDetailDTO getPagoByReferencia(@PathParam("referenciaPago") String referencia)
    {
        PagoPaseadorEntity pagoPaseadorEntity = pagoPaseadorLogic.getPagoByReferencia(referencia);
        if(pagoPaseadorEntity == null){
            throw new WebApplicationException(" El recurso /pagosPaseadores/referencia" + referencia + " no existe.", 404);
        }
        return new PagoPaseadorDetailDTO(pagoPaseadorEntity);
    }
    
    @GET
    @Path("/fechaLimite/{year: [0-9][0-9][0-9][0-9]}/{month: [0-9][0-9]}/{day: [0-9][0-9]}")
    public PagoPaseadorDetailDTO getPagoByfechaLimite(@PathParam("year") Integer year, @PathParam("month") Integer month, @PathParam("day") Integer day)
    {
        return new PagoPaseadorDetailDTO(pagoPaseadorLogic.getPagoByfechaLimite(new Date(year, month, day)));
    }
    
    @PUT
    @Path("{pagoPaseadorId: \\d+}")
    public PagoPaseadorDetailDTO updatePagoPaseador(@PathParam("pagoPaseadorId") Long pagoPaseadorId, PagoPaseadorDTO pagoPaseador) throws BusinessLogicException
    {
        pagoPaseador.setId(pagoPaseadorId);
        if(pagoPaseadorLogic.getPagoPaseador(pagoPaseadorId) == null) 
        {
            throw new WebApplicationException("El recurso /pagosPaseadores/" + pagoPaseadorId + " no existe.", 404);
        }
        return new PagoPaseadorDetailDTO(pagoPaseadorLogic.updatePago(pagoPaseadorId, pagoPaseador.toEntity()));
    }
    
    @DELETE
    @Path("{pagoPaseadorId: \\d+}")
    public void deletePagoPaseador(@PathParam("pagoPaseadorId") Long pagoPaseadorId)
    {
        if (pagoPaseadorLogic.getPagoPaseador(pagoPaseadorId) == null) {
            throw new WebApplicationException("El recurso /pagosPaseadores/" + pagoPaseadorId + " no existe.", 404);
        }
        pagoPaseadorLogic.deletePagoPaseador(pagoPaseadorId);
    }
    
    @Path("{pagoPaseadorId: \\d+}/paseador")
    public Class<PaseadorPagoPaseadorResource> getPaseadorPagoPaseadorResource(@PathParam("pagoPaseadorId") Long pagoPaseadorId) {
        if (pagoPaseadorLogic.getPagoPaseador(pagoPaseadorId) == null) {
            throw new WebApplicationException("El recurso /pagosPaseadores/" + pagoPaseadorId + " no existe.", 404);
        }
        return PaseadorPagoPaseadorResource.class;
    }
    
    @Path("{pagoPaseadorId: \\d+}/paseos")
    public Class<PaseoPagoPaseadorResource> getPaseoPagoPaseadorResource(@PathParam("pagoPaseadorId") Long pagoPaseadorId) {
        if (pagoPaseadorLogic.getPagoPaseador(pagoPaseadorId) == null) {
            throw new WebApplicationException("El recurso /pagosPaseadores/" + pagoPaseadorId + " no existe.", 404);
        }
        return PaseoPagoPaseadorResource.class;
    }
    
    private List<PagoPaseadorDetailDTO> listEntity2DetailDTO(List<PagoPaseadorEntity> entityList) {
        List<PagoPaseadorDetailDTO> list = new ArrayList<>();
        for(PagoPaseadorEntity entity : entityList) {
            list.add(new PagoPaseadorDetailDTO(entity));
        }
        return list;
    }
}
