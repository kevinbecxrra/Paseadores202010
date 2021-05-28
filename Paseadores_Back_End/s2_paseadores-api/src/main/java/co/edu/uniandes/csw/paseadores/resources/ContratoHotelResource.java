/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;

import co.edu.uniandes.csw.paseadores.dtos.ContratoHotelDTO;
import co.edu.uniandes.csw.paseadores.dtos.ContratoHotelDetailDTO;
import co.edu.uniandes.csw.paseadores.ejb.ContratoHotelLogic;
import co.edu.uniandes.csw.paseadores.entities.ContratoHotelEntity;
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
 * @author Edgar Camilo Diaz Suarez
 */
@Path("contratoshotel")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ContratoHotelResource {
    
    @Inject
    private ContratoHotelLogic contratoHotelLogic;

    
    @POST
    public ContratoHotelDTO createContratoHotel(ContratoHotelDTO contratoHotel) throws BusinessLogicException{
        return new ContratoHotelDTO(getContratoHotelLogic().createContratoHotel(contratoHotel.toEntity(false)));
    }
    
    @GET
    public List<ContratoHotelDetailDTO> getContratosHotel()
    {
        return listEntity2DetailDTO(contratoHotelLogic.getContratosHotel());
    }
    
    @GET
    @Path("{contratoshotelId: \\d+}")
    public ContratoHotelDetailDTO getContratoHotel(@PathParam("contratoshotelId") Long contratosHotelId){
        ContratoHotelEntity contratoHotelEntity = getContratoHotelLogic().getContratoHotel(contratosHotelId);
        if(contratoHotelEntity == null){
            throw new WebApplicationException("El  recurso  /contratoshotel/"+ contratosHotelId + " no existe. ", 404);
        }
        return new ContratoHotelDetailDTO(contratoHotelEntity);
        
    }
    
    @PUT
    @Path("{contratoshotelId: \\d+}")
    public ContratoHotelDetailDTO updateContratoHotelDTO(@PathParam("contratoshotelId") Long contratosHotelId, ContratoHotelDetailDTO contratoHotel) throws BusinessLogicException{
        contratoHotel.setId(contratosHotelId);
        if(getContratoHotelLogic().getContratoHotel(contratosHotelId) == null){
            throw new WebApplicationException("El recurso  /contratoshotel/"+ contratosHotelId + " no existe",404);
        }
        return new ContratoHotelDetailDTO(getContratoHotelLogic().updateContratoHotel(contratoHotel.toEntity(false)));
    }
    
    @DELETE
    @Path("{contratoshotelId: \\d+}")
    public void deleteContratoHotel(@PathParam("contratoshotelId") Long contratosHotelId){
        ContratoHotelEntity entity = getContratoHotelLogic().getContratoHotel(contratosHotelId);
        if(entity == null){
            throw new WebApplicationException(" El recurso /contratoshotel/"+ contratosHotelId + " no existe ",404);
        }
        getContratoHotelLogic().deleteContratoHotel(contratosHotelId);
    }
    
    @Path("{contratoshotelId:\\d+}/horashotel")
    public Class<ContratoHotelHoraHotelResource> gettHoraHotelResource(@PathParam("contratoshotelId") Long contratosHotelId){
        if(getContratoHotelLogic().getContratoHotel(contratosHotelId)==null){
             throw new WebApplicationException("El  recurso /contratoshotel/" + contratosHotelId + "/horashotel no existe.", 404);
        }
        return ContratoHotelHoraHotelResource.class;
    }
    
    @Path("{contratoshotelId:\\d+}/perro")
    public Class<PerroContratoHotelResource> getContratoHotelPerroResource(@PathParam("contratoshotelId") Long contratoshotelId) {
        if (contratoHotelLogic.getContratoHotel(contratoshotelId) == null) 
        {
            throw new WebApplicationException("El recurso /contratos Hotel /" + contratoshotelId + " no existe.", 404);
        }
        return PerroContratoHotelResource.class;
    }

    /**
     * @return the contratoHotelLogic
     */
    public ContratoHotelLogic getContratoHotelLogic() {
        return contratoHotelLogic;
    }

    /**
     * @param contratoHotelLogic the contratoHotelLogic to set
     */
    public void setContratoHotelLogic(ContratoHotelLogic contratoHotelLogic) {
        this.contratoHotelLogic = contratoHotelLogic;
    }
    
    private List<ContratoHotelDetailDTO> listEntity2DetailDTO(List<ContratoHotelEntity> entityList) {
        List<ContratoHotelDetailDTO> list = new ArrayList<>();
        for(ContratoHotelEntity entity : entityList) {
            list.add(new ContratoHotelDetailDTO(entity));
        }
        return list;
    }
        
}
