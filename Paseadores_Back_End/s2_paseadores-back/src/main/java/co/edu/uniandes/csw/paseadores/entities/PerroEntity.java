/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.entities;

import javax.persistence.Entity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author julian Oliveros<je.oliverosf at uniandes.edu.co>
 */
@Entity
public class PerroEntity extends BaseEntity implements Serializable
{

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

    /**
     * @return the paseos
     */
    public List<ContratoPaseoEntity> getPaseos() {
        return paseos;
    }

    /**
     * @param paseos the paseos to set
     */
    public void setPaseos(List<ContratoPaseoEntity> paseos) {
        this.paseos = paseos;
    }

    /**
     * @return the estadias
     */
    public List<ContratoHotelEntity> getEstadias() {
        return estadias;
    }

    /**
     * @param estadias the estadias to set
     */
    public void setEstadias(List<ContratoHotelEntity> estadias) {
        this.estadias = estadias;
    }

    private String idPerro;
    private String  nombre;
    private Integer edad;
    private String raza;
    
    private String imagen;
    
   @PodamExclude
   @OneToMany(mappedBy = "perro", fetch = FetchType.LAZY)
   private List<ContratoPaseoEntity> paseos = new ArrayList<>();
   
   
   @PodamExclude
   @OneToMany(mappedBy = "perro", fetch = FetchType.LAZY)
   private List<ContratoHotelEntity> estadias = new ArrayList<>();
   
   @PodamExclude
   @ManyToOne(fetch = FetchType.LAZY)
   private ClienteEntity propietario;
    
    /**
     * @return the idPerro
     */
    public String getIdPerro() {
        return idPerro;
    }

    /**
     * @param idPerro the idPerro to set
     */
    public void setIdPerro(String idPerro) {
        this.idPerro = idPerro;
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
     * @return the edad
     */
    public Integer getEdad() {
        return edad;
    }

    /**
     * @param edad the edad to set
     */
    public void setEdad(Integer edad) {
        this.edad = edad;
    }

    /**
     * @return the raza
     */
    public String getRaza() {
        return raza;
    }

    /**
     * @param raza the raza to set
     */
    public void setRaza(String raza) {
        this.raza = raza;
    }

    /**
     * @return the cliente
     */
    public ClienteEntity getCliente() {
        return propietario;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(ClienteEntity cliente) {
        this.propietario = cliente;
    }

    
    
    
}


























