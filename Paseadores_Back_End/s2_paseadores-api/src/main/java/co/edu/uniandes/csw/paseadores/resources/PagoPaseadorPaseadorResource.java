/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;

import co.edu.uniandes.csw.paseadores.dtos.PagoPaseadorDTO;
import co.edu.uniandes.csw.paseadores.dtos.PagoPaseadorDetailDTO;
import co.edu.uniandes.csw.paseadores.ejb.PagoPaseadorLogic;
import co.edu.uniandes.csw.paseadores.ejb.PaseadorPagoPaseadorLogic;
import co.edu.uniandes.csw.paseadores.entities.PagoPaseadorEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
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
@Produces("application/json")
@Consumes("application/json")
public class PagoPaseadorPaseadorResource {
    
    @Inject
    private PaseadorPagoPaseadorLogic paseadorPagoPaseadorLogic;
    
    @Inject
    private PagoPaseadorLogic pagoPaseadorLogic;
    
    @POST
    @Path("{pagoPaseadorId: \\d+}")
    public PagoPaseadorDetailDTO addPagoPaseador(@PathParam("paseadorId") Long paseadorId, @PathParam("pagoPaseadorId") Long pagoPaseadorId) {
        if (pagoPaseadorLogic.getPagoPaseador(pagoPaseadorId) == null) {
            throw new WebApplicationException("El recurso /pagoPaseador/" + pagoPaseadorId + " no existe.", 404);
        }
        return new PagoPaseadorDetailDTO(paseadorPagoPaseadorLogic.addPagoPaseador(paseadorId, pagoPaseadorId));
    }
    
    @GET
    public List<PagoPaseadorDTO> getPagosPaseador(@PathParam("paseadorId") Long paseadorId) {
        return pagoPaseadorsListEntity2DTO(paseadorPagoPaseadorLogic.getPagosPaseador(paseadorId));
    }
    
    @GET
    @Path("{pagoPaseadorId: \\d+}")
    public PagoPaseadorDetailDTO getPagoPaseador(@PathParam("paseadorId") Long paseadorId, @PathParam("pagoPaseadorId") Long pagoPaseadorId) throws BusinessLogicException {
        if (pagoPaseadorLogic.getPagoPaseador(pagoPaseadorId) == null) {
            throw new WebApplicationException("El recurso /pagoPaseador/" + pagoPaseadorId + " no existe.", 404);
        }
        return new PagoPaseadorDetailDTO(paseadorPagoPaseadorLogic.getPagoPaseador(paseadorId, pagoPaseadorId));
    }
    
    @PUT
    public List<PagoPaseadorDTO> replacePagosPaseador(@PathParam("paseadorId") Long paseadorId, List<PagoPaseadorDTO> pagosPaseador) {
        for (PagoPaseadorDTO pagoPaseador : pagosPaseador) {
            if(pagoPaseadorLogic.getPagoPaseador(pagoPaseador.getId()) == null) {
                throw new WebApplicationException("El recurso /pagoPaseador/" + pagoPaseador.getId() + " no existe.", 404);
            }
        }
        return pagoPaseadorsListEntity2DTO(paseadorPagoPaseadorLogic.replacePagosPaseador(paseadorId, pagoPaseadorsListDTO2Entity(pagosPaseador)));
    }
    
    @DELETE
    @Path("{pagoPaseadorId: \\d+}")
    public void removePagoPaseador(@PathParam("pagoPaseadorId") Long pagoPaseadorId) {
        if (pagoPaseadorLogic.getPagoPaseador(pagoPaseadorId) == null) {
            throw new WebApplicationException("El recurso /pagoPaseador/" + pagoPaseadorId + " no existe.", 404);
        }
        paseadorPagoPaseadorLogic.removePagoPaseador(pagoPaseadorId);
    }
    
    private List<PagoPaseadorDTO> pagoPaseadorsListEntity2DTO(List<PagoPaseadorEntity> entityList) {
        List<PagoPaseadorDTO> list = new ArrayList<>();
        for (PagoPaseadorEntity entity : entityList) {
            list.add(new PagoPaseadorDTO(entity));
        }
        return list;
    }
    
    private List<PagoPaseadorEntity> pagoPaseadorsListDTO2Entity(List<PagoPaseadorDTO> dtos) {
        List<PagoPaseadorEntity> list = new ArrayList<>();
        for (PagoPaseadorDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
