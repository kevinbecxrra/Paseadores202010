/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;

import co.edu.uniandes.csw.paseadores.dtos.PagoPaseadorDTO;
import co.edu.uniandes.csw.paseadores.dtos.PagoPaseadorDetailDTO;
import co.edu.uniandes.csw.paseadores.ejb.PagoPaseadorLogic;
import co.edu.uniandes.csw.paseadores.ejb.PagoPaseadorPaseoLogic;
import co.edu.uniandes.csw.paseadores.entities.PagoPaseadorEntity;
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
public class PagoPaseadorPaseoResource {
    
    @Inject
    private PagoPaseadorPaseoLogic pagoPaseadorPaseoLogic;

    @Inject
    private PagoPaseadorLogic pagoPaseadorLogic;
    
    @POST
    @Path("{pagoPaseadorId: \\d+}")
    public PagoPaseadorDTO addPagoPaseador(@PathParam("paseosId") Long paseosId, @PathParam("pagoPaseadorId") Long pagoPaseadorId) {
        if (pagoPaseadorLogic.getPagoPaseador(pagoPaseadorId) == null) {
            throw new WebApplicationException("El recurso /pagosPaseadores/" + pagoPaseadorId + " no existe.", 404);
        }
        return new PagoPaseadorDTO(pagoPaseadorPaseoLogic.addPagoPaseador(pagoPaseadorId, paseosId));
    }
    
    @GET
    public PagoPaseadorDetailDTO getPagoPaseador(@PathParam("paseosId") Long paseosId) {
        PagoPaseadorEntity pagoPaseadorEntity = pagoPaseadorPaseoLogic.getPagoPaseador(paseosId);
        if (pagoPaseadorEntity == null) {
            throw new WebApplicationException("El recurso /paseos/" + paseosId + "/pagoPaseador no existe      ", 404);
        }
        return new PagoPaseadorDetailDTO(pagoPaseadorEntity);
    }
    
    @PUT
    @Path("{pagoPaseadorId: \\d+}")
    public PagoPaseadorDetailDTO replacePagoPaseador(@PathParam("paseosId") Long paseosId, @PathParam("pagoPaseadorId") Long pagoPaseadorId) {
        if (pagoPaseadorLogic.getPagoPaseador(pagoPaseadorId) == null) {
            throw new WebApplicationException("El recurso /pagosPaseadores  /" + pagoPaseadorId + " no existe", 404);
        }
        return new PagoPaseadorDetailDTO(pagoPaseadorPaseoLogic.replacePagoPaseador(paseosId, pagoPaseadorId));
    }
    
    @DELETE
    public void removePaseo(@PathParam("paseosId") Long paseosId) throws BusinessLogicException {
        pagoPaseadorPaseoLogic.removePaseo(paseosId);
    }
}
