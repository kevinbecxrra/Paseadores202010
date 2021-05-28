/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.dtos;

import co.edu.uniandes.csw.paseadores.entities.ContratoHotelEntity;
import java.io.Serializable;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 * @author Edgar Camilo Diaz Suarez
 */
public class ContratoHotelDTO implements Serializable {
    
    private Long id;
    
    private String cuidadoEspecial;
    
    private Double costo;

    private ClienteDTO cliente;
    
    private PerroDTO perro;
    
    private CalificacionDTO calificacion;
    
    private PagoClienteDTO pagoCliente;
    
    
    public ContratoHotelDTO(){
        
    }
    
    public ContratoHotelDTO(ContratoHotelEntity contratoHotelEntity){
        if(contratoHotelEntity!=null){
            this.id = contratoHotelEntity.getId();
            this.costo = contratoHotelEntity.getCosto();
            this.cuidadoEspecial = contratoHotelEntity.getCuidadoEspecial();
            if(contratoHotelEntity.getCliente()!=null){
                this.cliente = new ClienteDTO(contratoHotelEntity.getCliente());
            }else{
                this.cliente = null;
            }
            if(contratoHotelEntity.getPerro() != null){
                this.perro = new PerroDTO(contratoHotelEntity.getPerro());
            }else{
                this.perro = null;
            }
            if(contratoHotelEntity.getCalificacionHotel() != null){
                this.calificacion = new CalificacionDTO(contratoHotelEntity.getCalificacionHotel(), true);
            }else{
                this.calificacion = null;
            }
            if(contratoHotelEntity.getPago() != null){
                this.pagoCliente = new PagoClienteDTO(contratoHotelEntity.getPago(), true);
            }else{
                this.pagoCliente = null;
            }
        }
    }
    
    public ContratoHotelEntity toEntity(boolean llamado){
    
        ContratoHotelEntity contratoHotelEntity = new ContratoHotelEntity();
        contratoHotelEntity.setId(this.getId());
        contratoHotelEntity.setCosto(this.getCosto());
        contratoHotelEntity.setCuidadoEspecial(this.getCuidadoEspecial());
        if(!llamado && this.getCalificacion() != null){
            contratoHotelEntity.setCalificacionHotel(this.getCalificacion().toEntity(true));
        }
        if(this.cliente != null   ){
            contratoHotelEntity.setCliente(this.cliente.toEntity());
        }
        if(this.perro != null){
            contratoHotelEntity.setPerro(this.perro.toEntity());
        }
        if(!llamado && this.pagoCliente != null){
            contratoHotelEntity.setPago(this.pagoCliente.toEntity(true));
        }
        return contratoHotelEntity; 
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
     * @return the perro
     */
    public PerroDTO getPerro() {
        return perro;
    }

    /**
     * @param perro the perro to set
     */
    public void setPerro(PerroDTO perro) {
        this.perro = perro;
    }
    
    @Override
    public String toString() {
        return ToStringBuilder.reflectionToString(this, ToStringStyle.MULTI_LINE_STYLE);
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
     * @return the pagoCliente
     */
    public PagoClienteDTO getPagoCliente() {
        return pagoCliente;
    }

    /**
     * @param pagoCliente the pagoCliente to set
     */
    public void setPagoCliente(PagoClienteDTO pagoCliente) {
        this.pagoCliente = pagoCliente;
    }

    /**
     * @return the calificacion
     */
    public CalificacionDTO getCalificacion() {
        return calificacion;
    }

    /**
     * @param calificacion the calificacion to set
     */
    public void setCalificacion(CalificacionDTO calificacion) {
        this.calificacion = calificacion;
    }
}
