/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;

import co.edu.uniandes.csw.paseadores.dtos.PaseadorDTO;
import co.edu.uniandes.csw.paseadores.dtos.PaseadorDetailDTO;
import co.edu.uniandes.csw.paseadores.ejb.PaseadorLogic;
import co.edu.uniandes.csw.paseadores.ejb.PaseadorPagoPaseadorLogic;
import co.edu.uniandes.csw.paseadores.entities.PaseadorEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
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
public class PaseadorPagoPaseadorResource {
    
    @Inject
    private PaseadorPagoPaseadorLogic paseadorPagoPaseadorLogic;

    @Inject
    private PaseadorLogic paseadorLogic;
    
    @POST
    @Path("{paseadorId: \\d+}")
    public PaseadorDTO addPaseador(@PathParam("pagoPaseadorId") Long pagoPaseadorId, @PathParam("paseadorId") Long paseadorId) {
        if (paseadorLogic.getPaseador(paseadorId) == null) {
            throw new WebApplicationException("El recurso /paseadores/" + paseadorId + " no existe.", 404);
        }
        return new PaseadorDTO(paseadorPagoPaseadorLogic.addPaseador(paseadorId, pagoPaseadorId));
    }
    
    @GET
    public PaseadorDetailDTO getPaseador(@PathParam("pagoPaseadorId") Long pagoPaseadorId) {
        PaseadorEntity paseadorEntity = paseadorPagoPaseadorLogic.getPaseador(pagoPaseadorId);
        if (paseadorEntity == null) {
            throw new WebApplicationException("El recurso /pagosPaseadores/" + pagoPaseadorId + "/paseador no existe.", 404);
        }
        return new PaseadorDetailDTO(paseadorEntity);
    }
    
    @PUT
    @Path("{paseadorId: \\d+}")
    public PaseadorDetailDTO replacePaseador(@PathParam("pagoPaseadorId") Long pagoPaseadorId, @PathParam("paseadorId") Long paseadorId) {
        if (paseadorLogic.getPaseador(paseadorId) == null) {
            throw new WebApplicationException("El recurso /paseadores/" + paseadorId + " no existe.", 404);
        }
        return new PaseadorDetailDTO(paseadorPagoPaseadorLogic.replacePaseador(pagoPaseadorId, paseadorId));
    }
    
    @DELETE
    public void removePagoPaseador(@PathParam("pagoPaseadorId") Long pagoPaseadorId) throws BusinessLogicException {
        paseadorPagoPaseadorLogic.removePagoPaseador(pagoPaseadorId);
    }
}
