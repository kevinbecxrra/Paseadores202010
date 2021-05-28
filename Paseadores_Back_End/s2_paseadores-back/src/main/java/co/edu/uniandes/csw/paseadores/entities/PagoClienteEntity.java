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
 * @author Alvaro Plata
 */
@Entity
public class PagoClienteEntity extends BaseEntity implements Serializable {

    private String referencia;
    
    private Double monto;
    
    private String medioPago;
    
    @PodamExclude
    @OneToOne(mappedBy = "pago", fetch = FetchType.LAZY)
    private ContratoPaseoEntity contratoPaseo;

   
    @PodamExclude
    @ManyToOne
    private ClienteEntity cliente;
   
    @PodamExclude
    @OneToOne(mappedBy = "pago", fetch = FetchType.LAZY)
    private ContratoHotelEntity contratoHotel;
    
    /**
     * @return the medioPago
     */
    public String getMedioPago() {
        return medioPago;
    }

    /**
     * @param medioPago the medioPago to set
     */
    public void setMedioPago(String medioPago) {
        this.medioPago = medioPago;
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
     * @return the contratoHotel
     */
    public ContratoHotelEntity getContratoHotel() {
        return contratoHotel;
    }

    /**
     * @param contratoHotel the contratoHotel to set
     */
    public void setContratoHotel(ContratoHotelEntity contratoHotel) {
        this.contratoHotel = contratoHotel;
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
