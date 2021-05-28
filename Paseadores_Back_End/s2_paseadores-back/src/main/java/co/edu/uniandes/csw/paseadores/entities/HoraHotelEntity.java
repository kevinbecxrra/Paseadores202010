/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.entities;

import co.edu.uniandes.csw.paseadores.podam.DateStrategy;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.ManyToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author Edgar Camilo Diaz Suarez
 */
@Entity
public class HoraHotelEntity extends BaseEntity {

    
    private Double costoBase;
    
    @Temporal(TemporalType.TIMESTAMP)
    @PodamStrategyValue(DateStrategy.class)
    private Date dia;
    
    private Integer cupoMaximo;
        
    
    
    private Boolean disponible;
    
    @PodamExclude
    @ManyToMany
    private List<ContratoHotelEntity> contratosHotel = new ArrayList<>(); 
    
    /**
     * @return the dia
     */
    public Date getDia() {
        return dia;
    }

    /**
     * @param dia the dia to set
     */
    public void setDia(Date dia) {
        this.dia = dia;
    }
    
    
    /**
     * @return the disponible
     */
    public Boolean getDisponible() {
        return disponible;
    }    

    /**
     * @return the cupoMaximo
     */
    public Integer getCupoMaximo() {
        return cupoMaximo;
    }


    /**
     * @return the costoBase
     */
    public Double getCostoBase() {
        return costoBase;
    }

    /**
     * @param costoBase the costoBase to set
     */
    public void setCostoBase(Double costoBase) {
        this.costoBase = costoBase;
    }


    /**
     * @param disponible the disponible to set
     */
    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    /**
     * @return the contratosHotel
     */
    public List<ContratoHotelEntity> getContratosHotel() {
        return contratosHotel;
    }

    /**
     * @param cupoMaximo the cupoMaximo to set
     */
    public void setCupoMaximo(Integer cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }
    
    /**
     * @param contratosHotel the contratosHotel to set
     */
    public void setContratosHotel(List<ContratoHotelEntity> contratosHotel) {
        this.contratosHotel = contratosHotel;
    }
    
  
}
