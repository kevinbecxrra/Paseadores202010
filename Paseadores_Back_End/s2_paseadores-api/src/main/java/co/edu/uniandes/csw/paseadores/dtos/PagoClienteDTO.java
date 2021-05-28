/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.dtos;

import co.edu.uniandes.csw.paseadores.entities.PagoClienteEntity;
import java.io.Serializable;

/**
 *
 * @author Alvaro Plata
 */
public class PagoClienteDTO implements Serializable{
    
    private Long id;
    
    private Double monto;
    
    private String referencia;
    
    private String medioPago;
    
    private ContratoPaseoDTO contratoPaseo;
    
    private ContratoHotelDTO contratoHotel;
    
    private ClienteDTO cliente;
    
    public PagoClienteDTO()
    {
        
    }
    
    public PagoClienteDTO(PagoClienteEntity pagoClienteEntity, boolean llamado) {
        if (pagoClienteEntity != null) {
            this.id = pagoClienteEntity.getId();
            this.monto = pagoClienteEntity.getMonto();
            this.medioPago = pagoClienteEntity.getMedioPago();
            this.referencia = pagoClienteEntity.getReferencia();
            if (!llamado && pagoClienteEntity.getContratoHotel() != null) {
                this.contratoHotel = new ContratoHotelDTO(pagoClienteEntity.getContratoHotel());
            } else {
                this.contratoHotel = null;
            }
            if (!llamado && pagoClienteEntity.getContratoPaseo() != null) {
                this.contratoPaseo = new ContratoPaseoDTO(pagoClienteEntity.getContratoPaseo(), true);
            } else {
                this.contratoPaseo = null;
            }
            if (pagoClienteEntity.getCliente() != null) {
                this.cliente = new ClienteDTO(pagoClienteEntity.getCliente());
            } else {
                this.cliente = null;
            }
        }
    }
    
    public PagoClienteEntity toEntity(boolean llamado) {
        PagoClienteEntity pagoClienteEntity = new PagoClienteEntity();
        pagoClienteEntity.setId(this.getId());
        pagoClienteEntity.setMonto(this.getMonto());
        pagoClienteEntity.setMedioPago(this.getMedioPago());
        pagoClienteEntity.setReferencia(this.getReferencia());
        if (this.cliente != null) {
            pagoClienteEntity.setCliente(this.cliente.toEntity());
        }
        if (!llamado && this.contratoPaseo != null) {
            pagoClienteEntity.setContratoPaseo(this.contratoPaseo.toEntity(true));
        }
        if (!llamado && this.contratoHotel != null) {
            pagoClienteEntity.setContratoHotel(this.contratoHotel.toEntity(true));
        }
        return pagoClienteEntity;
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
     * @return the contratoPaseo
     */
    public ContratoPaseoDTO getContratoPaseo() {
        return contratoPaseo;
    }

    /**
     * @param contratoPaseo the contratoPaseo to set
     */
    public void setContratoPaseo(ContratoPaseoDTO contratoPaseo) {
        this.contratoPaseo = contratoPaseo;
    }

    /**
     * @return the cliente
     */
    public ClienteDTO getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }
    
    /**
     * @return the contratoHotel
     */
    public ContratoHotelDTO getContratoHotel() {
        return contratoHotel;
    }

    /**
     * @param contratoHotel the contratoHotel to set
     */
    public void setContratoHotel(ContratoHotelDTO contratoHotel) {
        this.contratoHotel = contratoHotel;
    }
    
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
}
