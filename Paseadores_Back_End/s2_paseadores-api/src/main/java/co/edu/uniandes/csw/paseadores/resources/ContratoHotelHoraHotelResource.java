/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;

import co.edu.uniandes.csw.paseadores.dtos.HoraHotelDetailDTO;
import co.edu.uniandes.csw.paseadores.ejb.ContratoHotelHoraHotelLogic;
import co.edu.uniandes.csw.paseadores.ejb.HoraHotelLogic;
import co.edu.uniandes.csw.paseadores.entities.HoraHotelEntity;
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
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Edgar Camilo Diaz Suarez
 */
//@Path("contratoshotel/{contratoshotelId: \\d+}/horashotel")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ContratoHotelHoraHotelResource {
    
    @Inject
    private ContratoHotelHoraHotelLogic contratoHotelHoraHotelLogic;
    
    @Inject
    private HoraHotelLogic horaHotelLogic;
    
    @POST
    @Path("{horashotelId: \\d+}")
    public HoraHotelDetailDTO addHoraHotel(@PathParam("horashotelId") Long horasHotelId, @PathParam("contratoshotelId") Long contratosHotelId) throws BusinessLogicException{
        if(horaHotelLogic.getHoraHotel(horasHotelId) == null){
            throw new WebApplicationException("El recurso /horashotel/" + horasHotelId + "no existe.", 404);
        }
        return new HoraHotelDetailDTO(contratoHotelHoraHotelLogic.addHoraHotel(horasHotelId, contratosHotelId));
    }
    
    @GET
    public List<HoraHotelDetailDTO> getHorasHotel(@PathParam("contratoshotelId")Long contratosHotelId){
        return horasHotelListEntity2DTO(contratoHotelHoraHotelLogic.getHorasHotel(contratosHotelId));
    }

    @GET
    @Path("{horashotelId: \\d+}")
    public HoraHotelDetailDTO getHoraHotel(@PathParam("contratoshotelId") Long contratosHotelId, @PathParam("horashotelId") Long horasHotelId ){
     if(horaHotelLogic.getHoraHotel(horasHotelId) == null){
         throw new WebApplicationException("  El recurso /horashotel/" + horasHotelId + "  no  existe.", 404);
     }
     return new HoraHotelDetailDTO(contratoHotelHoraHotelLogic.getHoraHotel(contratosHotelId, horasHotelId));
    }
    
    @PUT
    public List<HoraHotelDetailDTO> replaceHoraHotel(@PathParam("contratoshotelId") Long contratoHotelId, List<HoraHotelDetailDTO> horasHotel){
        for(HoraHotelDetailDTO horaHotel : horasHotel){
            if(horaHotelLogic.getHoraHotel(horaHotel.getId()) == null){
                throw new WebApplicationException("El recurso  /horashotel/"+ horaHotel.getId() + "  no existe.",404 );
            }
        }
        return horasHotelListEntity2DTO(contratoHotelHoraHotelLogic.replaceHorasHotel(contratoHotelId, horasHotelListDTO2Entity(horasHotel)));
      
    }
    
    
    @DELETE
    @Path("{horashotelId: \\d+}")
    public void removeHoraHotel(@PathParam("contratosId") Long contratosId, @PathParam("horashotelId") Long horasHotelId){
        if(horaHotelLogic.getHoraHotel((horasHotelId)) == null){
            throw new WebApplicationException("El recurso /horashotel/" + horasHotelId + "no existe. ",404);
        }
        horaHotelLogic.deleteHoraHotel(horasHotelId);
    }
    
    private List<HoraHotelDetailDTO> horasHotelListEntity2DTO(List<HoraHotelEntity> horasHotel) {
        List<HoraHotelDetailDTO> lista = new ArrayList();
        for(HoraHotelEntity entity : horasHotel){
            lista.add(new HoraHotelDetailDTO(entity));
        }
        return lista;
    }

    private List<HoraHotelEntity> horasHotelListDTO2Entity(List<HoraHotelDetailDTO> horasHotel) {
        List<HoraHotelEntity> lista = new ArrayList();
        for(HoraHotelDetailDTO dto: horasHotel){
            lista.add(dto.toEntity());
        }
        return lista;
    }

    
    
    
}
