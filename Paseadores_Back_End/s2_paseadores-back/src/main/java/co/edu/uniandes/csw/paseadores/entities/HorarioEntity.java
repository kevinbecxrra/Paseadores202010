/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.entities;

import co.edu.uniandes.csw.paseadores.podam.DateStrategy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author Kevin Camilo Becerra Walteros
 */
@Entity
public class HorarioEntity extends BaseEntity implements Serializable {

    @Temporal(TemporalType.TIMESTAMP)
    @PodamStrategyValue(DateStrategy.class)
    private Date dia;
    
    private Integer duracion;
    
    private Boolean ocupado;
    
   @PodamExclude
   @ManyToMany(mappedBy = "horariosDisponibles", fetch = FetchType.LAZY)
   private List<PaseadorEntity> paseador = new ArrayList<>();
   
   
   @PodamExclude
   @OneToOne
   private PaseoEntity paseo2;
   
   @PodamExclude
   @OneToOne
   private PaseoEntity paseo1;
    

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
     * @return the paseador
     */
    public List<PaseadorEntity> getPaseador() {
        return paseador;
    }

    /**
     * @param paseador the paseador to set
     */
    public void setPaseador(List<PaseadorEntity> paseador) {
        this.paseador = paseador;
    }

    /**
     * @return the paseo2
     */
    public PaseoEntity getPaseo2() {
        return paseo2;
    }

    /**
     * @param paseo2 the paseo2 to set
     */
    public void setPaseo2(PaseoEntity paseo2) {
        this.paseo2 = paseo2;
    }

    /**
     * @return the paseo1
     */
    public PaseoEntity getPaseo1() {
        return paseo1;
    }

    /**
     * @param paseo1 the paseo1 to set
     */
    public void setPaseo1(PaseoEntity paseo1) {
        this.paseo1 = paseo1;
    }
    
}
