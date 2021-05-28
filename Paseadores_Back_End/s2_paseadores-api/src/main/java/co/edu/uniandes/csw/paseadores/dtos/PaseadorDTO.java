/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.dtos;

import co.edu.uniandes.csw.paseadores.entities.PaseadorEntity;
import java.io.Serializable;

/**
 *
 * @paseador Daniel Mateo Guatibonza Solano
 */
public class PaseadorDTO implements Serializable {
    
    private Integer numeroCalificaciones;

    private Long id;
    
    private String correo;

    private String nombre;
    
    private String cuentaBancaria;
    
    private String eps;
    
    private Double calificacionGlobal;
    
    private String arl;
    
    private String identificacion;

    private String telefono;
    
    private String imagen;
    

    public PaseadorDTO()
    {
        
    }
    
    public PaseadorDTO(PaseadorEntity paseadorEntity)
    {
        if (paseadorEntity != null) {
            this.id = paseadorEntity.getId();
            this.nombre = paseadorEntity.getNombre();
            this.identificacion = paseadorEntity.getIdentificacion();
            this.eps = paseadorEntity.getEps();
            this.arl = paseadorEntity.getArl();
            this.correo = paseadorEntity.getCorreo();
            this.telefono = paseadorEntity.getTelefono();
            this.calificacionGlobal = paseadorEntity.getCalificacionGlobal();
            this.cuentaBancaria = paseadorEntity.getCuentaBancaria();
            this.numeroCalificaciones = paseadorEntity.getNumeroCalificaciones();
            this.imagen = paseadorEntity.getImagen();
        }
    }
    
    public PaseadorEntity toEntity() {
        PaseadorEntity paseadorEntity = new PaseadorEntity();
        paseadorEntity.setId(this.getId());
        paseadorEntity.setNombre(this.getNombre());
        paseadorEntity.setIdentificacion(this.getIdentificacion());
        paseadorEntity.setEps(this.getEps());
        paseadorEntity.setArl(this.getArl());
        paseadorEntity.setCorreo(this.getCorreo());
        paseadorEntity.setTelefono(this.getTelefono());
        paseadorEntity.setCalificacionGlobal(this.getCalificacionGlobal());
        paseadorEntity.setCuentaBancaria(this.getCuentaBancaria());
        paseadorEntity.setNumeroCalificaciones(this.getNumeroCalificaciones());
        paseadorEntity.setImagen(this.getImagen());
        return paseadorEntity;
    }
    
    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @return the identificacion
     */
    public String getIdentificacion() {
        return identificacion;
    }

    /**
     * @param identificacion the identificacion to set
     */
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }

    /**
     * @return the eps
     */
    public String getEps() {
        return eps;
    }


    /**
     * @return the arl
     */
    public String getArl() {
        return arl;
    }

    /**
     * @param arl the arl to set
     */
    public void setArl(String arl) {
        this.arl = arl;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }
    
        /**
     * @param eps the eps to set
     */
    public void setEps(String eps) {
        this.eps = eps;
    }

    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

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
     * @param cuentaBancaria the cuentaBancaria to set
     */
    public void setCuentaBancaria(String cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
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

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }
    
    /**
     * @return the cuentaBancaria
     */
    public String getCuentaBancaria() {
        return cuentaBancaria;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the imagen
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * @param imagen the imagen to set
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
