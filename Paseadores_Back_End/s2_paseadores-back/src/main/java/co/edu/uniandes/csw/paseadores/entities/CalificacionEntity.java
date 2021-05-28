/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.entities;

import java.io.Serializable;
import javax.persistence.Entity;
import javax.persistence.OneToOne;
import uk.co.jemos.podam.common.PodamExclude;

/**
 *
 * @author Nicolas Urrego Sandoval <n.urrego at uniandes.edu.co> 
 */
@Entity
public class CalificacionEntity extends BaseEntity implements Serializable{
    private Integer valoracion;
    private String comentario;
    
    @PodamExclude
    @OneToOne
    private ContratoHotelEntity contratoHotel;

    @PodamExclude
    @OneToOne
    private ContratoPaseoEntity contratoPaseador;
    
    @PodamExclude
    @OneToOne
    private ContratoPaseoEntity contratoRecorrido;
    
    /**
     * @return the valoracion
     */
    public Integer getValoracion() {
        return valoracion;
    }

    /**
     * @param valoracion the valoracion to set
     */
    public void setValoracion(Integer valoracion) {
        this.valoracion = valoracion;
    }

    /**
     * @return the comentario
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * @param comentario the comentario to set
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
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
     * @return the contratoPaseador
     */
    public ContratoPaseoEntity getContratoPaseador() {
        return contratoPaseador;
    }

    /**
     * @param contratoPaseador the contratoPaseador to set
     */
    public void setContratoPaseador(ContratoPaseoEntity contratoPaseador) {
        this.contratoPaseador = contratoPaseador;
    }

    /**
     * @return the contratoRecorrido
     */
    public ContratoPaseoEntity getContratoRecorrido() {
        return contratoRecorrido;
    }

    /**
     * @param contratoRecorrido the contratoRecorrido to set
     */
    public void setContratoRecorrido(ContratoPaseoEntity contratoRecorrido) {
        this.contratoRecorrido = contratoRecorrido;
    }
    
    
}
