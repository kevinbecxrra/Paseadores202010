  
/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.dtos;

import co.edu.uniandes.csw.paseadores.entities.ContratoHotelEntity;
import co.edu.uniandes.csw.paseadores.entities.ContratoPaseoEntity;
import co.edu.uniandes.csw.paseadores.entities.PerroEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author julian Oliveros<je.oliverosf at uniandes.edu.co>
 */
public class PerroDetailDTO extends PerroDTO implements Serializable {

    private List<ContratoPaseoDTO> paseos;
    
    private List<ContratoHotelDTO> estadias;
    
    public PerroDetailDTO()
    {
        super();
    }
    
    
    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param perroEntity La entidad de la cual se construye el DTO
     */
    public PerroDetailDTO(PerroEntity perroEntity) {
        super(perroEntity);
        
        if (perroEntity.getPaseos() != null) {
            paseos = new ArrayList<>();
            
            for (ContratoPaseoEntity entitypaseo : perroEntity.getPaseos()) {
                paseos.add(new ContratoPaseoDTO(entitypaseo, false));
            }            
        }
        if (perroEntity.getEstadias()!= null) {
            estadias = new ArrayList<>();
            for (ContratoHotelEntity entityhotel : perroEntity.getEstadias()) {
                estadias.add(new ContratoHotelDTO(entityhotel));
            }   
        }
    }
    
    /**
     * Transformar el DTO a una entidad
     *
     * @return La entidad que representa el libro.
     */
    @Override
    public PerroEntity toEntity() {
        PerroEntity perroEntity = super.toEntity();
        
        if (paseos != null) {
            List<ContratoPaseoEntity> paseoEntity = new ArrayList<>();
            for (ContratoPaseoDTO dtoPaseo : getContratosPaseo()) {
                paseoEntity.add(dtoPaseo.toEntity(false));
            }
            perroEntity.setPaseos(paseoEntity);
        }
        if (estadias != null) {
            List<ContratoHotelEntity> hotelEntity = new ArrayList<>();
            for (ContratoHotelDTO dtohotel : getContratosHotel()) 
            {
                hotelEntity.add(dtohotel.toEntity(false));
            }
            perroEntity.setEstadias(hotelEntity);
        }
        return perroEntity;
    }
    
    /**
     * @return the contratosPaseo
     */
    public List<ContratoPaseoDTO> getContratosPaseo() {
        return paseos;
    }

    /**
     * @param contratosPaseo the contratosPaseo to set
     */
    public void setContratosPaseo(List<ContratoPaseoDTO> contratosPaseo) {
        this.paseos = contratosPaseo;
    }

    /**
     * @return the contratosHotel
     */
    public List<ContratoHotelDTO> getContratosHotel() {
        return estadias;
    }

    /**
     * @param contratosHotel the contratosHotel to set
     */
    public void setContratosHotel(List<ContratoHotelDTO> contratosHotel) {
        this.estadias = contratosHotel;
    }
}
