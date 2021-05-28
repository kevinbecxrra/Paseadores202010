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
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Edgar Camilo Diaz Suarez
 */

@Entity
public class ContratoHotelEntity extends BaseEntity {
  
    private String cuidadoEspecial;
    
    private Double costo;

    @PodamExclude
    @OneToOne
    private PagoClienteEntity pago; 
    
    @PodamExclude
    @ManyToOne
    private ClienteEntity cliente; 
    
    @PodamExclude
    @ManyToOne
    private PerroEntity perro; 
   
    @PodamExclude
    @OneToOne(mappedBy = "contratoHotel", fetch = FetchType.LAZY )
    private CalificacionEntity calificacionHotel; 
    
    @PodamExclude
    @ManyToMany(mappedBy = "contratosHotel", fetch = FetchType.LAZY )
    private List<HoraHotelEntity> horasHotel = new ArrayList<>(); 
    
    /**
     * @return the cuidadoEspecial
     */
    public String getCuidadoEspecial() {
        return cuidadoEspecial;
    }

    /**
     * @param cuidadoEspecial the cuidadoEspecial to set
     */
    public void setCuidadoEspecial(String cuidadoEspecial) {
        this.cuidadoEspecial = cuidadoEspecial;
    }

    /**
     * @return the costo
     */
    public Double getCosto() {
        return costo;
    }

    /**
     * @param costo the costo to set
     */
    public void setCosto(Double costo) {
        this.costo = costo;
    }

    /**
     * @return the pago
     */
    public PagoClienteEntity getPago() {
        return pago;
    }

    /**
     * @param pago the pago to set
     */
    public void setPago(PagoClienteEntity pago) {
        this.pago = pago;
    }

    /**
     * @return the cliente
     */
    public ClienteEntity getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(ClienteEntity cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the perro
     */
    public PerroEntity getPerro() {
        return perro;
    }

    /**
     * @param perro the perro to set
     */
    public void setPerro(PerroEntity perro) {
        this.perro = perro;
    }

    /**
     * @return the calificacionHotel
     */
    public CalificacionEntity getCalificacionHotel() {
        return calificacionHotel;
    }

    /**
     * @param calificacionHotel the calificacionHotel to set
     */
    public void setCalificacionHotel(CalificacionEntity calificacionHotel) {
        this.calificacionHotel = calificacionHotel;
    }

    /**
     * @return the horasHotel
     */
    public List<HoraHotelEntity> getHorasHotel() {
        return horasHotel;
    }

    /**
     * @param horasHotel the horasHotel to set
     */
    public void setHorasHotel(List<HoraHotelEntity> horasHotel) {
        this.horasHotel = horasHotel;
    }


    
    
}
