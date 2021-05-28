/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;

import co.edu.uniandes.csw.paseadores.dtos.PaseoDetailDTO;
import co.edu.uniandes.csw.paseadores.ejb.PaseoLogic;
import co.edu.uniandes.csw.paseadores.ejb.PaseoContratoPaseoLogic;
import co.edu.uniandes.csw.paseadores.entities.PaseoEntity;
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
 * @author Kevin Camilo Becerra Walteros
 */
@Produces("application/json")
@Consumes("application/json")
public class PaseoContratoPaseoResource {
    
    @Inject
    private PaseoContratoPaseoLogic paseoContratoPaseoLogic;

    @Inject
    private PaseoLogic paseoLogic;
    
    @POST
    @Path("{paseosId: \\d+}")
    public PaseoDetailDTO addPaseo(@PathParam("contratosPaseoId") Long contratosPaseoId, @PathParam("paseosId") Long paseosId) {
        if (paseoLogic.getPaseo(paseosId) == null) {
            throw new WebApplicationException("El recurso /paseos/" + paseosId + " no existe.", 404);
        }
        return new PaseoDetailDTO(paseoContratoPaseoLogic.addPaseo(paseosId, contratosPaseoId));
    }
    
    @GET
    public PaseoDetailDTO getPaseo(@PathParam("contratosPaseoId") Long contratosPaseoId) {
        PaseoEntity paseoEntity = paseoContratoPaseoLogic.getPaseo(contratosPaseoId);
        if (paseoEntity == null) {
            throw new WebApplicationException("El recurso /contratoPaseos/" + contratosPaseoId + "/paseo no existe.", 404);
        }
        return new PaseoDetailDTO(paseoEntity);
    }
    
    @PUT
    @Path("{paseosId: \\d+}")
    public PaseoDetailDTO replacePaseo(@PathParam("contratosPaseoId") Long contratosPaseoId, @PathParam("paseosId") Long paseosId) {
        if (paseoLogic.getPaseo(paseosId) == null) {
            throw new WebApplicationException("El recurso /paseos/" + paseosId + " no existe.", 404);
        }
        return new PaseoDetailDTO(paseoContratoPaseoLogic.replacePaseo(paseosId, contratosPaseoId));
    }
    
    @DELETE
    public void removeContratoPaseo(@PathParam("contratosPaseoId") Long contratosPaseoId) throws BusinessLogicException {
        paseoContratoPaseoLogic.removeContratoPaseo(contratosPaseoId);
    }
    
}
