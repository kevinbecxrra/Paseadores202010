/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.dtos;

import co.edu.uniandes.csw.paseadores.adapters.TimeStampAdapter;
import co.edu.uniandes.csw.paseadores.entities.HorarioEntity;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Edgar Camilo Diaz Suarez
 */
public class HorarioDTO implements Serializable{
    
    private Long id;
    
    private Integer duracion;
    
    private PaseoDTO paseo2;

    private Boolean ocupado;
    
    private PaseoDTO paseo1;
    
    @XmlJavaTypeAdapter(TimeStampAdapter.class)
    private Date dia;
       
    public HorarioDTO(){
        
    }
    
    public HorarioDTO(HorarioEntity horarioEntity){
        if(horarioEntity!=null){
            this.id = horarioEntity.getId();
            this.dia = horarioEntity.getDia();
            this.duracion = horarioEntity.getDuracion();
            this.ocupado = horarioEntity.getOcupado();
        }
    }
    
    public HorarioEntity toEntity(){
        HorarioEntity horarioEntity = new HorarioEntity ();
        horarioEntity.setId(this.id);
        horarioEntity.setDia(this.dia);
        horarioEntity.setDuracion(this.duracion);
        horarioEntity.setOcupado(this.ocupado);
        return horarioEntity;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }
    
    /**
     *@param dia the dia to set
     */
    public void setDia(Date dia) {
        this.dia = dia;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the dia
     */
    public Date getDia() {
        return dia;
    }

    /**
     * @return the duracion
     */
    public Integer getDuracion() {
        return duracion;
    }

    /**
     * @param duracion the duracion to set
     */
    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    /**
     * @return the ocupado
     */
    public Boolean getOcupado() {
        return ocupado;
    }

    /**
     * @param ocupado the ocupado to set
     */
    public void setOcupado(Boolean ocupado) {
        this.ocupado = ocupado;
    }
    
    /**
     * @return the paseo2
     */
    public PaseoDTO getPaseo2() {
        return paseo2;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
    }

    /**
     * @return the paseo1
     */
    public PaseoDTO getPaseo1() {
        return paseo1;
    }

    /**
     * @param paseo1 the paseo1 to set
     */
    public void setPaseo1(PaseoDTO paseo1) {
        this.paseo1 = paseo1;
    }

    /**
     * @param paseo2 the paseo2 to set
     */
    public void setPaseo2(PaseoDTO paseo2) {
        this.paseo2 = paseo2;
    }   
}
