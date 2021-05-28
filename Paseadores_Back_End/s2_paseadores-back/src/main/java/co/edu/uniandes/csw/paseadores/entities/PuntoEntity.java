/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Nicolas Urrego Sandoval <n.urrego at uniandes.edu.co>
 */
@Entity
public class PuntoEntity extends BaseEntity implements Serializable{
    
    private Double latitud;
    private Double longitud;
    private Integer posicion;

    @PodamExclude
    @OneToOne(fetch = FetchType.LAZY)
    private ContratoPaseoEntity contratoPaseo;
    
    @PodamExclude
    @ManyToOne
    private RecorridoEntity recorrido;
    
    
    /**
     * @return the latitud
     */
    public Double getLatitud() {
        return latitud;
    }

    /**
     * @param latitud the latitud to set
     */
    public void setLatitud(Double latitud) {
        this.latitud = latitud;
    }

    /**
     * @return the longitud
     */
    public Double getLongitud() {
        return longitud;
    }

    /**
     * @param longitud the longitud to set
     */
    public void setLongitud(Double longitud) {
        this.longitud = longitud;
    }

    /**
     * @return the posicion
     */
    public Integer getPosicion() {
        return posicion;
    }

    /**
     * @param posicion the posicion to set
     */
    public void setPosicion(Integer posicion) {
        this.posicion = posicion;
    }

    /**
     * @return the recorrido
     */
    public RecorridoEntity getRecorrido() {
        return recorrido;
    }

    /**
     * @param recorrido the recorrido to set
     */
    public void setRecorrido(RecorridoEntity recorrido) {
        this.recorrido = recorrido;
    }

    /**
     * @return the contratoPaseo
     */
    public ContratoPaseoEntity getContratoPaseo() {
        return contratoPaseo;
    }

    /**
     * @param contratoPaseo the contratoPaseo to set
     */
    public void setContratoPaseo(ContratoPaseoEntity contratoPaseo) {
        this.contratoPaseo = contratoPaseo;
    }
    
}
