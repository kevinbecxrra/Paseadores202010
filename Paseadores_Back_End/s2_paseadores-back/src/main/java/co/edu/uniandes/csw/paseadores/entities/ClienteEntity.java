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
 * @author Alvaro Plata
 */
@Entity
public class ClienteEntity extends BaseEntity implements Serializable{

    private String identificacion;
    
    private String correo;

    private String nombre;
            
    private String telefono;
    
    private String imagen;
    
   @PodamExclude
   @OneToMany(mappedBy = "propietario", fetch = FetchType.LAZY)
   private List<PerroEntity> perros = new ArrayList<>();
   
   
   @PodamExclude
   @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
   private List<ContratoHotelEntity> contratosHotel = new ArrayList<>();
   
   @PodamExclude
   @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
   private List<PagoClienteEntity> pagos = new ArrayList<>();
   
   @PodamExclude
   @OneToMany(mappedBy = "cliente", fetch = FetchType.LAZY)
   private List<ContratoPaseoEntity> contratosPaseo = new ArrayList<>();

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
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

        /**
     * @return the perros
     */
    public List<PerroEntity> getPerros() {
        return perros;
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
     * @param contratosHotel the contratosHotel to set
     */
    public void setContratosHotel(List<ContratoHotelEntity> contratosHotel) {
        this.contratosHotel = contratosHotel;
    }

    /**
     * @param perros the perros to set
     */
    public void setPerros(List<PerroEntity> perros) {
        this.perros = perros;
    }

    /**
     * @return the contratosHotel
     */
    public List<ContratoHotelEntity> getContratosHotel() {
        return contratosHotel;
    }


    /**
     * @return the pagos
     */
    public List<PagoClienteEntity> getPagos() {
        return pagos;
    }

    /**
     * @param pagos the pagos to set
     */
    public void setPagos(List<PagoClienteEntity> pagos) {
        this.pagos = pagos;
    }

        /**
     * @param contratosPaseo the contratosPaseo to set
     */
    public void setContratosPaseo(List<ContratoPaseoEntity> contratosPaseo) {
        this.contratosPaseo = contratosPaseo;
    }
    
    /**
     * @return the contratosPaseo
     */
    public List<ContratoPaseoEntity> getContratosPaseo() {
        return contratosPaseo;
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