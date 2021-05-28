/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.dtos;

import co.edu.uniandes.csw.paseadores.entities.ContratoHotelEntity;
import co.edu.uniandes.csw.paseadores.entities.HoraHotelEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Edgar Camilo Diaz Suarez
 */
public class HoraHotelDetailDTO extends HoraHotelDTO implements Serializable{
    
    private List<ContratoHotelDTO> contratosHotel = new ArrayList<>(); 
  
    public HoraHotelDetailDTO()
    {
        super();
    }
    
    public HoraHotelDetailDTO(HoraHotelEntity horaHotelEntity){
        super(horaHotelEntity);
        if(horaHotelEntity.getContratosHotel() != null)
        {
            contratosHotel = new ArrayList<>();
            for(ContratoHotelEntity contratoHotel : horaHotelEntity.getContratosHotel()){
                contratosHotel.add(new ContratoHotelDTO(contratoHotel));
            }
        }
    }
    
    @Override
    public HoraHotelEntity toEntity(){
        HoraHotelEntity horaHotelEntity = super.toEntity();
        if(contratosHotel!= null){
            List<ContratoHotelEntity> contratosHotelEntity = new ArrayList<>();
            for(ContratoHotelDTO dtoContratoHotel : getContratosHotel() ){
                contratosHotelEntity.add(dtoContratoHotel.toEntity(false));
            }
            horaHotelEntity.setContratosHotel(contratosHotelEntity);
        }
        return horaHotelEntity;
    }
  
    /**
     * @return the contratosHotel
     */
    public List<ContratoHotelDTO> getContratosHotel() {
        return contratosHotel;
    }

    /**
     * @param contratosHotel the contratosHotel to set
     */
    public void setContratosHotel(List<ContratoHotelDTO> contratosHotel) {
        this.contratosHotel = contratosHotel;
    }
}
