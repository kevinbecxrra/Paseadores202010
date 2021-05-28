/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.dtos;

import co.edu.uniandes.csw.paseadores.entities.ContratoHotelEntity;
import co.edu.uniandes.csw.paseadores.entities.HoraHotelEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Edgar Camilo Diaz Suarez
 */
public class ContratoHotelDetailDTO extends ContratoHotelDTO {
    
    private List<HoraHotelDTO> horasHotel = new ArrayList<>(); 
    
    public ContratoHotelDetailDTO(){
        super();
    }
    
    public ContratoHotelDetailDTO(ContratoHotelEntity contratoHotelEntity){
        super(contratoHotelEntity);
        if(contratoHotelEntity.getHorasHotel() != null){
            horasHotel = new ArrayList<>();
            for(HoraHotelEntity entityHoraHotel : contratoHotelEntity.getHorasHotel()){
                horasHotel.add(new HoraHotelDTO(entityHoraHotel));
            }
        }
    }
    
    @Override
    public ContratoHotelEntity toEntity(boolean llamado){
        ContratoHotelEntity contratoHotelEntity = super.toEntity(false);
        if(horasHotel != null){
            List<HoraHotelEntity> horasHotelEntity = new ArrayList<>();
            for(HoraHotelDTO dtoHoraHotel : getHorasHotel()){
                horasHotelEntity.add(dtoHoraHotel.toEntity());
            }
            contratoHotelEntity.setHorasHotel(horasHotelEntity);
        }
        return contratoHotelEntity;
    }
    
    public List<HoraHotelDTO> getHorasHotel(){
        return horasHotel;
    }
    
    public void setHorasHotel(List<HoraHotelDTO> horasHotel){
        this.horasHotel = horasHotel;
    }
}
