/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;

import co.edu.uniandes.csw.paseadores.dtos.ContratoHotelDetailDTO;
import co.edu.uniandes.csw.paseadores.ejb.ContratoHotelLogic;
import co.edu.uniandes.csw.paseadores.ejb.HoraHotelContratoHotelLogic;
import co.edu.uniandes.csw.paseadores.entities.ContratoHotelEntity;
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
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Edgar Camilo Diaz Suarez
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class HoraHotelContratoHotelResource {
    
    @Inject
    private HoraHotelContratoHotelLogic horaHotelContratoHotelLogic;
    
    @Inject
    private ContratoHotelLogic contratoHotelLogic;
    
    @POST
    @Path("{contratoshotelId: \\d+}")
    public ContratoHotelDetailDTO addContratoHotel(@PathParam("horashotelId") Long horasHotelId, @PathParam("contratoshotelId") Long contratosHotelId){
        if(contratoHotelLogic.getContratoHotel(contratosHotelId) == null){
            throw new WebApplicationException("El recurso /contratoshotel/"+ contratosHotelId + " no existe.", 404);
        }
        return new ContratoHotelDetailDTO(horaHotelContratoHotelLogic.addContrato(contratosHotelId, horasHotelId));
    }
    
    @GET
    public List<ContratoHotelDetailDTO> getContratosHotel(@PathParam("horashotelId") Long horasHotelId){
        return contratosHotelListEntity2DTO(horaHotelContratoHotelLogic.getContratos(horasHotelId));
    }
    
    @GET
    @Path("{contratoshotelId: \\d+}")
    public ContratoHotelDetailDTO getContratoHotel(@PathParam("horashotelId") Long horasHotelId, @PathParam("contratoshotelId") Long contratosHotelId){
        if(contratoHotelLogic.getContratoHotel(contratosHotelId) == null )
        {
            throw new WebApplicationException(" El recurso /contratoshotel/"+ contratosHotelId + "  no  existe .",404);
        }
        return new ContratoHotelDetailDTO(horaHotelContratoHotelLogic.getContrato(horasHotelId, contratosHotelId));
    }
    
    @PUT
    public List<ContratoHotelDetailDTO> replaceContratoHotel(@PathParam("horashotelId") Long horasHotelId, List<ContratoHotelDetailDTO> contratos){
        for(ContratoHotelDetailDTO contrato : contratos){
            if(contratoHotelLogic.getContratoHotel(contrato.getId()) == null){
                throw new WebApplicationException("  El recurso /contratoshotel/"+ contrato.getId()+"no existe.",404);
            }
        }
        return contratosHotelListEntity2DTO(horaHotelContratoHotelLogic.replaceContratosHotel(horasHotelId, contratosHotelListDTO2Entity(contratos)));
    }
    
    @DELETE
    @Path("{contratoshotelId: \\d+}")
    public void removeContratoHotel(@PathParam("horashotelId") Long horasHotelId, @PathParam("contratoshotelId") Long contratosHotelId){
        if(contratoHotelLogic.getContratoHotel(contratosHotelId)== null){
            throw new WebApplicationException(" El  recurso /contratoshotel/"+contratosHotelId + "  no  existe .",404);
        }
        horaHotelContratoHotelLogic.removeContratoHotel(horasHotelId, contratosHotelId);
    }

    private List<ContratoHotelDetailDTO> contratosHotelListEntity2DTO(List<ContratoHotelEntity> contratos) {
        List<ContratoHotelDetailDTO> lista = new ArrayList<>();
        for(ContratoHotelEntity entity : contratos){
            lista.add(new ContratoHotelDetailDTO(entity));
        }
        return lista;
    }

    private List<ContratoHotelEntity> contratosHotelListDTO2Entity(List<ContratoHotelDetailDTO> contratos) {
        List<ContratoHotelEntity> lista = new ArrayList<>();
        for(ContratoHotelDetailDTO dto : contratos){
            lista.add(dto.toEntity(false));
        }
        return lista;
    }

    
}
