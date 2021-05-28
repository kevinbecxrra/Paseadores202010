/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;

import co.edu.uniandes.csw.paseadores.dtos.PaseoDTO;
import co.edu.uniandes.csw.paseadores.dtos.PaseoDetailDTO;
import co.edu.uniandes.csw.paseadores.ejb.PagoPaseadorPaseoLogic;
import co.edu.uniandes.csw.paseadores.ejb.PaseoLogic;
import co.edu.uniandes.csw.paseadores.entities.PaseoEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Daniel Mateo Guatibonza Solano
 */
public class PaseoPagoPaseadorResource {
    
    @Inject
    private PagoPaseadorPaseoLogic pagoPaseadorPaseoLogic;

    @Inject
    private PaseoLogic paseoLogic;
    
    @POST
    @Path("{paseoId: \\d+}")
    public PaseoDetailDTO addPaseo(@PathParam("pagoPaseadorId") Long pagoPaseadorId, @PathParam("paseoId") Long paseoId) {
        if (paseoLogic.getPaseo(paseoId) == null) {
            throw new WebApplicationException("El recurso /paseo/" + paseoId + " no existe.", 404);
        }
        return new PaseoDetailDTO(pagoPaseadorPaseoLogic.addPaseo(pagoPaseadorId, paseoId));
    }
    
    @GET
    public List<PaseoDTO> getPaseos(@PathParam("pagoPaseadorId") Long pagoPaseadorId) {
        return paseosListEntity2DTO(pagoPaseadorPaseoLogic.getPaseos(pagoPaseadorId));
    }
    
    @GET
    @Path("{paseoId: \\d+}")
    public PaseoDetailDTO getPaseo(@PathParam("pagoPaseadorId") Long pagoPaseadorId, @PathParam("paseoId") Long paseoId) throws BusinessLogicException {
        if (paseoLogic.getPaseo(paseoId) == null) {
            throw new WebApplicationException("El recurso /paseo/" + paseoId + " no existe.", 404);
        }
        return new PaseoDetailDTO(pagoPaseadorPaseoLogic.getPaseo(pagoPaseadorId, paseoId));
    }
    
    @PUT
    public List<PaseoDTO> replacePaseos(@PathParam("pagoPaseadorId") Long pagoPaseadorId, List<PaseoDTO> paseos) {
        for (PaseoDTO paseo : paseos) {
            if(paseoLogic.getPaseo(paseo.getId()) == null) {
                throw new WebApplicationException("El recurso /paseo/" + paseo.getId() + " no existe.", 404);
            }
        }
        return paseosListEntity2DTO(pagoPaseadorPaseoLogic.replacePaseos(pagoPaseadorId, paseosListDTO2Entity(paseos)));
    }
    
    @DELETE
    @Path("{paseoId: \\d+}")
    public void removePaseo(@PathParam("paseoId") Long paseoId) {
        if (paseoLogic.getPaseo(paseoId) == null) {
            throw new WebApplicationException("El recurso /paseo/" + paseoId + " no existe.", 404);
        }
        pagoPaseadorPaseoLogic.removePaseo(paseoId);
    }
    
    private List<PaseoDTO> paseosListEntity2DTO(List<PaseoEntity> entityList) {
        List<PaseoDTO> list = new ArrayList<>();
        for (PaseoEntity entity : entityList) {
            list.add(new PaseoDTO(entity));
        }
        return list;
    }
    
    private List<PaseoEntity> paseosListDTO2Entity(List<PaseoDTO> dtos) {
        List<PaseoEntity> list = new ArrayList<>();
        for (PaseoDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
