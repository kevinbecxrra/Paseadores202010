/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.entities;

import co.edu.uniandes.csw.paseadores.podam.DateStrategy;
import javax.persistence.Entity;
import java.io.Serializable;
import java.util.Date;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author julian Oliveros<je.oliverosf at uniandes.edu.co> 
 */
@Entity        
public class ContratoPaseoEntity extends BaseEntity implements Serializable{

   @Temporal(TemporalType.TIMESTAMP)
   @PodamStrategyValue(DateStrategy.class) 
   private Date horaEncuentro;
  
   @PodamExclude
   @OneToOne(mappedBy = "contratoPaseo", fetch = FetchType.LAZY)
   private PuntoEntity sitioEncuentro;
   
   @PodamExclude
   @ManyToOne(fetch = FetchType.LAZY)
   private PaseoEntity paseo;

   @PodamExclude
   @OneToOne
   private PagoClienteEntity pago;
   
   @PodamExclude
   @ManyToOne(fetch = FetchType.LAZY)
   private ClienteEntity cliente;
   
   @PodamExclude
   @ManyToOne(fetch = FetchType.LAZY)
   private PerroEntity perro;
   
   
   
   @PodamExclude
   @OneToOne(mappedBy = "contratoPaseador", fetch = FetchType.LAZY )
   private CalificacionEntity calificacionPaseador;
     
   @PodamExclude
   @OneToOne(mappedBy = "contratoRecorrido", fetch = FetchType.LAZY )
   private CalificacionEntity calificacionRecorrido;
    
    /**
     * @return the horaEncuentro
     */
    public Date getHoraEncuentro() {
        return horaEncuentro;
    }

    /**
     * @param horaEncuentro the horaEncuentro to set
     */
    public void setHoraEncuentro(Date horaEncuentro) {
        this.horaEncuentro = horaEncuentro;
    }

    /**
     * @return the sitioEncuentro
     */
    public PuntoEntity getSitioEncuentro() {
        return sitioEncuentro;
    }

    /**
     * @param sitioEncuentro the sitioEncuentro to set
     */
    public void setSitioEncuentro(PuntoEntity sitioEncuentro) {
        this.sitioEncuentro = sitioEncuentro;
    }

    /**
     * @return the calificacionPaseador
     */
    public CalificacionEntity getCalificacionPaseador() {
        return calificacionPaseador;
    }

    /**
     * @param calificacionPaseador the calificacionPaseador to set
     */
    public void setCalificacionPaseador(CalificacionEntity calificacionPaseador) {
        this.calificacionPaseador = calificacionPaseador;
    }

    /**
     * @return the calificacionRecorrido
     */
    public CalificacionEntity getCalificacionRecorrido() {
        return calificacionRecorrido;
    }

    /**
     * @param calificacionRecorrido the calificacionRecorrido to set
     */
    public void setCalificacionRecorrido(CalificacionEntity calificacionRecorrido) {
        this.calificacionRecorrido = calificacionRecorrido;
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
     * @return the paseo
     */
    public PaseoEntity getPaseo() {
        return paseo;
    }

    /**
     * @param paseo the paseo to set
     */
    public void setPaseo(PaseoEntity paseo) {
        this.paseo = paseo;
    }
    
    
    
}
