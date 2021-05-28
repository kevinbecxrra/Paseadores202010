/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;

import co.edu.uniandes.csw.paseadores.dtos.HoraHotelDTO;
import co.edu.uniandes.csw.paseadores.dtos.HoraHotelDetailDTO;
import co.edu.uniandes.csw.paseadores.ejb.HoraHotelLogic;
import co.edu.uniandes.csw.paseadores.entities.HoraHotelEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PUT;
import javax.ws.rs.Produces;
import javax.ws.rs.POST;
import javax.ws.rs.PathParam;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Edgar Camilo Diaz Suarez
 */
@Path("horashotel")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class HoraHotelResource {
    
    @Inject
    private HoraHotelLogic horaHotelLogic;
    
    
    @POST
    public HoraHotelDTO createHoraHotel(HoraHotelDTO horaHotel) throws BusinessLogicException{
        return new HoraHotelDTO(horaHotelLogic.createHoraHotel(horaHotel.toEntity()));
        
    }
    
    @GET
    public List<HoraHotelDetailDTO> getPagosPaseadores()
    {
        return listEntity2DetailDTO(horaHotelLogic.getHorasHotel());
    }
    
   @GET
   @Path("{horashotel: \\d+}")
   public HoraHotelDetailDTO getHoraHotel(@PathParam("horashotel") Long horasHotelId){
       HoraHotelEntity horaHotelEntity = horaHotelLogic.getHoraHotel(horasHotelId);
       if(horaHotelEntity == null){
           throw new WebApplicationException(" El recurso /horashotel/" + horasHotelId + " no existe.", 404);
       }
       return new HoraHotelDetailDTO(horaHotelEntity);
     
   }
   
    @GET
    @Path("/costoinferior/{costoinf: \\d+}/costosuperior/{costosup: \\d+}")
    public List<HoraHotelDetailDTO> getHoraHotelByCostoInRange(@PathParam("costoinf") Double costoInf,@PathParam("costosup") Double costoSup)
    {
        List<HoraHotelEntity> listaEntity = horaHotelLogic.getHoraHotelByCostoBaseInRange(costoInf, costoSup);
        if(listaEntity == null || listaEntity.isEmpty()){
           throw new WebApplicationException(" Las horashotel no se encuentran en ese rango." , 404);
        }
        List<HoraHotelDetailDTO> lista = new ArrayList();
          for(HoraHotelEntity entity : listaEntity){
            lista.add(new HoraHotelDetailDTO(entity));
        }
        return lista;
    }
   
    @GET
    @Path("/diainferior/{diainf}/diasuperior/{diasup}")
    public List<HoraHotelDetailDTO> getHoraHotelByDiaInRange(@PathParam("diainf") String diaInfe,@PathParam("diasup") String diaSupe)
    {
        
        try {
            Date diaInf = new SimpleDateFormat("yyyy-MM-dd-hh:mm").parse(diaInfe);
            Date  diaSup = new SimpleDateFormat("yyyy-MM-dd-hh:mm").parse(diaSupe);
             List<HoraHotelEntity> listaEntity = horaHotelLogic.getHoraHotelByDiaInRange(diaInf, diaSup);
        if(listaEntity == null || listaEntity.isEmpty()){
           throw new WebApplicationException(" Las horashotel no se encuentran en ese rango de dias." , 404);
        }
        List<HoraHotelDetailDTO> lista = new ArrayList();
          for(HoraHotelEntity entity : listaEntity){
            lista.add(new HoraHotelDetailDTO(entity));
        }
        return lista;
        } catch (ParseException ex) {
            Logger.getLogger(HoraHotelResource.class.getName()).log(Level.SEVERE, null, ex);
            throw new WebApplicationException(" Date mal "+ diaInfe +" - "+ diaSupe , 404);
      
        }
        
       
    }

    
   @PUT
   @Path("{horashotelId: \\d+}")
   public HoraHotelDetailDTO updateHoraHotel(@PathParam("horashotelId") Long horasHotelId, HoraHotelDetailDTO horaHotel) throws BusinessLogicException{
       if(horaHotelLogic.getHoraHotel(horasHotelId) == null){
           throw new WebApplicationException("El  recurso /horashotel/" + horasHotelId + "no existe. ", 404);               
       }
     return new HoraHotelDetailDTO(horaHotelLogic.updateHoraHotel(horaHotel.toEntity()));
   }
   
   @DELETE
   @Path("{horashotelId: \\d+}")
   public void deleteHoraHotel(@PathParam("horashotelId") Long horasHotelId) throws BusinessLogicException{
       HoraHotelEntity entity = horaHotelLogic.getHoraHotel(horasHotelId);
       if(entity == null){
           throw new WebApplicationException("El recurso /horashotel/"+horasHotelId + " no existe. ",404);
       }
       
       horaHotelLogic.deleteHoraHotel(horasHotelId);
   }
   
   private List<HoraHotelDetailDTO> listEntity2DetailDTO(List<HoraHotelEntity> entityList) {
        List<HoraHotelDetailDTO> list = new ArrayList<>();
        for(HoraHotelEntity entity : entityList) {
            list.add(new HoraHotelDetailDTO(entity));
        }
        return list;
    }
   
 
   @Path("{horashotelId: \\d+}/contratoshotel")
   public Class<HoraHotelContratoHotelResource> getHoraHotelContratoHotelResource(@PathParam("horashotelId") Long horasHotelId){
       if(horaHotelLogic.getHoraHotel(horasHotelId) == null){
           throw new WebApplicationException("El  recurso  /horashotel/"+horasHotelId+" no  existe ",404);
       }
       return HoraHotelContratoHotelResource.class;
   }
  
   
   
}
