/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor. 
 */
package co.edu.uniandes.csw.paseadores.dtos;

import co.edu.uniandes.csw.paseadores.entities.PuntoEntity;
import java.io.Serializable;

/**
 *
 * @author Nicolas Urrego Sandoval <n.urrego at uniandes.edu.co>
 */
public class PuntoDTO implements Serializable{
    
    private Double latitud;
    
    private Double longitud;
    
    private Integer posicion;
    
    private Long id;
    
    private ContratoPaseoDTO contratoPaseo;
    
    private RecorridoDTO recorrido;
    
    
    public PuntoDTO()
    {
        
    }
    
    public PuntoDTO(PuntoEntity punto)
    {
        if(punto != null)
        {
            latitud = punto.getLatitud();
            longitud = punto.getLongitud();
            posicion = punto.getPosicion();
            id = punto.getId();
            if(punto.getRecorrido() != null)
                recorrido = new RecorridoDTO(punto.getRecorrido());
            else
                recorrido = null;
            
        }
    }
    
    public PuntoEntity toEntity()
    {
        PuntoEntity entidad = new PuntoEntity();
        entidad.setId(id);
        entidad.setLatitud(latitud);
        entidad.setLongitud(longitud);
        entidad.setPosicion(posicion);
        if(contratoPaseo != null)
            entidad.setContratoPaseo(contratoPaseo.toEntity(false));
        else
            entidad.setContratoPaseo(null);
        if(recorrido != null)
            entidad.setRecorrido(recorrido.toEntity());
        else
            entidad.setRecorrido(null);
        return entidad;
    }
    
    /**
     * @return posicion
     */
    public Integer getPosicion() {
        return posicion;
    }

    /**
     * @param latitudParam the latitudParam to set
     */
    public void setLatitud(Double latitudParam) {
        this.latitud = latitudParam;
    }
    
    /**
     * @return latitud
     */
    public Double getLatitud() {
        return latitud;
    }

    /**
     * @param longitudParam the longitudParam to set
     */
    public void setLongitud(Double longitudParam) {
        this.longitud = longitudParam;
    }

    /**
     * @param posicionParam the posicionParam to set
     */
    public void setPosicion(Integer posicionParam) {
        this.posicion = posicionParam;
    }
    
    /**
     * @return longitud
     */
    public Double getLongitud() {
        return longitud;
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
     * @return the contratoPaseo
     */
    public ContratoPaseoDTO getContratoPaseo() {
        return contratoPaseo;
    }

    /**
     * @param contratoPaseoParam the contratoPaseoParam to set
     */
    public void setContratoPaseo(ContratoPaseoDTO contratoPaseoParam) {
        this.contratoPaseo = contratoPaseoParam;
    }

    /**
     * @return the recorrido
     */
    public RecorridoDTO getRecorrido() {
        return recorrido;
    }

    /**
     * @param recorridoParam the recorrido to set
     */
    public void setRecorrido(RecorridoDTO recorridoParam) {
        this.recorrido = recorridoParam;
    }
}
