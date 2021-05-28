/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.dtos;

import co.edu.uniandes.csw.paseadores.adapters.DateAdapter;
import co.edu.uniandes.csw.paseadores.entities.PagoPaseadorEntity;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Daniel Mateo Guatibonza Solano
 */
public class PagoPaseadorDTO implements Serializable {
    
    private Long id;
    
    @XmlJavaTypeAdapter(DateAdapter.class)
    private Date fechaLimite;
    
    private Double monto;
    
    private String referencia;
    
    private PaseadorDTO paseador;
    
    public PagoPaseadorDTO()
    {
        
    }
    
    public PagoPaseadorDTO(PagoPaseadorEntity pagoPaseadorEntity)
    {
        if (pagoPaseadorEntity != null) {
            this.id = pagoPaseadorEntity.getId();
            this.referencia = pagoPaseadorEntity.getReferencia();
            this.monto = pagoPaseadorEntity.getMonto();
            this.fechaLimite = pagoPaseadorEntity.getFechaLimite();
            this.paseador = new PaseadorDTO(pagoPaseadorEntity.getPaseador());
        }
    }
    
    public PagoPaseadorEntity toEntity() {
        PagoPaseadorEntity paseadorEntity = new PagoPaseadorEntity();
        paseadorEntity.setId(this.getId());
        paseadorEntity.setReferencia(this.getReferencia());
        paseadorEntity.setMonto(this.getMonto());
        paseadorEntity.setFechaLimite(this.getFechaLimite());
        if(this.paseador != null)
        {
            paseadorEntity.setPaseador(this.getPaseador().toEntity());
        }
        return paseadorEntity;
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
     * @return the paseador
     */
    public PaseadorDTO getPaseador() {
        return paseador;
    }

    /**
     * @param paseador the paseador to set
     */
    public void setPaseador(PaseadorDTO paseador) {
        this.paseador = paseador;
    }
}
