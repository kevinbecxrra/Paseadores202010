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
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author Daniel Mateo Guatibonza Solano
 */
@Entity
public class PagoPaseadorEntity extends BaseEntity {

    private String referencia;
    
    private Double monto;
    
    @Temporal(TemporalType.DATE)
    @PodamStrategyValue(DateStrategy.class) 
    private Date fechaLimite;

    @PodamExclude
    @OneToMany(mappedBy = "pagoPaseador", fetch = FetchType.LAZY)   
    private List<PaseoEntity> paseos = new ArrayList<>();
    
    @PodamExclude
    @ManyToOne(fetch = FetchType.LAZY)
    private PaseadorEntity paseador;
   
    /**
     * @return the referencia
     */
    public String getReferencia() {
        return referencia;
    }

    /**
     * @param referencia the referencia to set
     */
    public void setReferencia(String referencia) {
        this.referencia = referencia;
    }

    /**
     * @return the monto
     */
    public Double getMonto() {
        return monto;
    }

    /**
     * @param monto the monto to set
     */
    public void setMonto(Double monto) {
        this.monto = monto;
    }

    /**
     * @return the fechaLimite
     */
    public Date getFechaLimite() {
        return fechaLimite;
    }

    /**
     * @param fechaLimite the fechaLimite to set
     */
    public void setFechaLimite(Date fechaLimite) {
        this.fechaLimite = fechaLimite;
    }

    /**
     * @return the paseador
     */
    public PaseadorEntity getPaseador() {
        return paseador;
    }

    /**
     * @param paseador the paseador to set
     */
    public void setPaseador(PaseadorEntity paseador) {
        this.paseador = paseador;
    }

    /**
     * @return the paseos
     */
    public List<PaseoEntity> getPaseos() {
        return paseos;
    }

    /**
     * @param paseos the paseos to set
     */
    public void setPaseos(List<PaseoEntity> paseos) {
        this.paseos = paseos;
    }
}
