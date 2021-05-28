/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.dtos;

import co.edu.uniandes.csw.paseadores.entities.RecorridoEntity;
import java.io.Serializable;

/**
 *
 * @author Nicolas Urrego Sandoval <n.urrego at uniandes.edu.co>
 */
public class RecorridoDTO implements Serializable{

    private Long id;
    
    private Double calificacionGlobal;
    
    private Integer numeroCalificaciones;
    
    public RecorridoDTO()
    {
        
    }
    
    public RecorridoDTO(RecorridoEntity recorrido)
    {
        if(recorrido!=null)
        {
            this.id = recorrido.getId();
            this.calificacionGlobal = recorrido.getCalificacionGlobal();
            this.numeroCalificaciones = recorrido.getNumeroCalificaciones(); 
        }
    }
    
    public RecorridoEntity toEntity()
    {
        RecorridoEntity entidad = new RecorridoEntity();
        entidad.setId(id);
        entidad.setCalificacionGlobal(calificacionGlobal);
        entidad.setNumeroCalificaciones(numeroCalificaciones);
        return entidad;
    }
    
    /**
     * @return the calificacionGlobal
     */
    public Double getCalificacionGlobal() {
        return calificacionGlobal;
    }

    /**
     * @param calificacionGlobalParam the calificacionGlobal to set
     */
    public void setCalificacionGlobal(Double calificacionGlobalParam) {
        this.calificacionGlobal = calificacionGlobalParam;
    }

    /**
     * @return the numeroCalificaciones
     */
    public Integer getNumeroCalificaciones() {
        return numeroCalificaciones;
    }

    /**
     * @param numeroCalificacionesParam the numeroCalificaciones to set
     */
    public void setNumeroCalificaciones(Integer numeroCalificacionesParam) {
        this.numeroCalificaciones = numeroCalificacionesParam;
    }
    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param idParam the id to set
     */
    public void setId(Long idParam) {
        this.id = idParam;
    }
}
