/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;

import co.edu.uniandes.csw.paseadores.dtos.PaseadorDetailDTO;
import co.edu.uniandes.csw.paseadores.ejb.PaseadorLogic;
import co.edu.uniandes.csw.paseadores.ejb.PaseadorPaseoLogic;
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
public class PaseadorPaseoResource {
    
    @Inject
    private PaseadorPaseoLogic paseadorPaseoLogic;

    @Inject
    private PaseadorLogic paseadorLogic;
    
    @POST
    @Path("{paseadorId: \\d+}")
    public PaseadorDetailDTO addPaseador(@PathParam("paseosId") Long paseosId, @PathParam("paseadorId") Long paseadorId) {
        if (paseadorLogic.getPaseador(paseadorId) == null) {
            throw new WebApplicationException("El recurso /paseadores/" + paseadorId + " no existe.", 404);
        }
        return new PaseadorDetailDTO(paseadorPaseoLogic.addPaseador(paseadorId, paseosId));
    }
    
    @GET
    public PaseadorDetailDTO getPaseador(@PathParam("paseosId") Long paseosId) {
        PaseadorEntity paseadorEntity = paseadorPaseoLogic.getPaseador(paseosId);
        if (paseadorEntity == null) {
            throw new WebApplicationException("El recurso /paseos/" + paseosId + "´paseador no existe.", 404);
        }
        return new PaseadorDetailDTO(paseadorEntity);
    }
    
    @PUT
    @Path("{paseadorId: \\d+}")
    public PaseadorDetailDTO replacePaseador(@PathParam("paseosId") Long paseosId, @PathParam("paseadorId") Long paseadorId) {
        if (paseadorLogic.getPaseador(paseadorId) == null) {
            throw new WebApplicationException("El recurso /paseadores/" + paseadorId + " no existe.", 404);
        }
        return new PaseadorDetailDTO(paseadorPaseoLogic.replacePaseador(paseosId, paseadorId));
    }
    
    @DELETE
    public void removePaseo(@PathParam("paseosId") Long paseosId) throws BusinessLogicException {
        paseadorPaseoLogic.removePaseo(paseosId);
    }
}
