/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.dtos;

import co.edu.uniandes.csw.paseadores.adapters.TimeStampAdapter;
import co.edu.uniandes.csw.paseadores.entities.HoraHotelEntity;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Edgar Camilo Diaz Suarez
 */
public class HoraHotelDTO implements Serializable{
    
    private Boolean disponible;

    private Long id;
    
    private Integer cupoMaximo;
    
    private Double costoBase;
        
    @XmlJavaTypeAdapter(TimeStampAdapter.class)
    private Date dia;

    public HoraHotelDTO(){
    }
    
    public HoraHotelDTO(HoraHotelEntity horaHotelEntity){
        if(horaHotelEntity != null ){
            this.id = horaHotelEntity.getId();
            this.costoBase = horaHotelEntity.getCostoBase();
            this.cupoMaximo = horaHotelEntity.getCupoMaximo();
            this.dia = horaHotelEntity.getDia();
            this.disponible = horaHotelEntity.getDisponible();
        }
    }
    
    public HoraHotelEntity toEntity(){
        HoraHotelEntity horaHotelEntity = new HoraHotelEntity();
        horaHotelEntity.setId(this.id);
        horaHotelEntity.setCostoBase(this.costoBase);
        horaHotelEntity.setCupoMaximo(this.cupoMaximo);
        horaHotelEntity.setDia(this.dia);
        horaHotelEntity.setDisponible(this.disponible);
        return horaHotelEntity;
    }
    
    /**
     * @return the dia
     */
    public Date getDia() {
        return dia;
    }

    /**
     * @return the cupoMaximo
     */
    public Integer getCupoMaximo() {
        return cupoMaximo;
    }

    /**
     * @param cupoMaximo the cupoMaximo to set
     */
    public void setCupoMaximo(Integer cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
    }
    
    @Override
    public String toString(){
        return ToStringBuilder.reflectionToString(this,ToStringStyle.MULTI_LINE_STYLE);
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
     * @param disponible the disponible to set
     */
    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }
    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }
}
