/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.entities;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Nicolas Urrego Sandoval <n.urrego at uniandes.edu.co>
 */
@Entity
public class RecorridoEntity extends BaseEntity implements Serializable{
    private Double calificacionGlobal;
    
    private Integer numeroCalificaciones;

    @PodamExclude
    @OneToMany(mappedBy = "recorrido", fetch = FetchType.LAZY)
    private List<PuntoEntity> puntos = new ArrayList<>();
    
    @PodamExclude
    @OneToMany(mappedBy = "recorrido", fetch = FetchType.LAZY)
    private List<PaseoEntity> paseos = new ArrayList<>();
    
    /**
     * @return the calificacionGlobal
     */
    public Double getCalificacionGlobal() {
        return calificacionGlobal;
    }

    /**
     * @param calificacionGlobal the calificacionGlobal to set
     */
    public void setCalificacionGlobal(Double calificacionGlobal) {
        this.calificacionGlobal = calificacionGlobal;
    }

    /**
     * @return the puntos
     */
    public List<PuntoEntity> getPuntos() {
        return puntos;
    }

    /**
     * @param puntos the puntos to set
     */
    public void setPuntos(List<PuntoEntity> puntos) {
        this.puntos = puntos;
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

    /**
     * @return the numeroCalificaciones
     */
    public Integer getNumeroCalificaciones() {
        return numeroCalificaciones;
    }

    /**
     * @param numeroCalificaciones the numeroCalificaciones to set
     */
    public void setNumeroCalificaciones(Integer numeroCalificaciones) {
        this.numeroCalificaciones = numeroCalificaciones;
    }
    
}
