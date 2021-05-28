/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.entities;

import java.util.ArrayList;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToMany;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Daniel Mateo Guatibonza Solano
 */
@Entity
public class PaseadorEntity extends BaseEntity {
    
    private String nombre;
    
    private Double calificacionGlobal;
    
    private String correo;

    private String identificacion;
    
    private String eps;
    
    private String arl;
    
    private String telefono;
        
    private String cuentaBancaria;
    
    private Integer numeroCalificaciones;
    
    private String imagen;
        
    @PodamExclude
    @ManyToMany(fetch = FetchType.LAZY)
    private List<HorarioEntity> horariosDisponibles = new ArrayList<>();
    
    @PodamExclude
    @OneToMany(mappedBy = "paseador", fetch = FetchType.LAZY)
    private List<PagoPaseadorEntity> pagos = new ArrayList<>();
        
    @PodamExclude
    @OneToMany(mappedBy = "paseador", fetch = FetchType.LAZY)
    private List<PaseoEntity> paseos = new ArrayList<>();
      
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
     * @return the arl
     */
    public String getArl() {
        return arl;
    }
    
    /**
     * @return the eps
     */
    public String getEps() {
        return eps;
    }

    /**
     * @param eps the eps to set
     */
    public void setEps(String eps) {
        this.eps = eps;
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
     * @return the cuentaBancaria
     */
    public String getCuentaBancaria() {
        return cuentaBancaria;
    }

    /**
     * @param cuentaBancaria the cuentaBancaria to set
     */
    public void setCuentaBancaria(String cuentaBancaria) {
        this.cuentaBancaria = cuentaBancaria;
    }    

    /**
     * @return the horariosDisponibles
     */
    public List<HorarioEntity> getHorariosDisponibles() {
        return horariosDisponibles;
    }

    /**
     * @param horariosDisponibles the horariosDisponibles to set
     */
    public void setHorariosDisponibles(List<HorarioEntity> horariosDisponibles) {
        this.horariosDisponibles = horariosDisponibles;
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
     * @return the pagos
     */
    public List<PagoPaseadorEntity> getPagos() {
        return pagos;
    }

    /**
     * @param pagos the pagos to set
     */
    public void setPagos(List<PagoPaseadorEntity> pagos) {
        this.pagos = pagos;
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
