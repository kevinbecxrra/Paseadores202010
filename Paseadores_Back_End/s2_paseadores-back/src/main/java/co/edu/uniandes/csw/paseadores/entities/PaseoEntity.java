/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.entities;

import co.edu.uniandes.csw.paseadores.podam.DateStrategy;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.OneToOne;
import javax.persistence.Temporal;
import javax.persistence.TemporalType;
import uk.co.jemos.podam.common.PodamExclude;
import uk.co.jemos.podam.common.PodamStrategyValue;

/**
 *
 * @author Kevin Camilo Becerra Walteros
 */
@Entity
public class PaseoEntity extends BaseEntity implements Serializable{
        
    private Integer cupoMaximo;
    
    private Double costo;
    
    private Integer duracion;
    
    private Boolean disponible;
    
    @Temporal(TemporalType.TIMESTAMP)
    @PodamStrategyValue(DateStrategy.class)
    private Date horaInicio;
     
    @PodamExclude
    @OneToOne(mappedBy = "paseo1", fetch = FetchType.LAZY)
    private HorarioEntity horario1;
   
    @PodamExclude
    @OneToOne(mappedBy = "paseo2", fetch = FetchType.LAZY)
    private HorarioEntity horario2;
   
    @PodamExclude
    @ManyToOne
    private PagoPaseadorEntity pagoPaseador;
   
    @PodamExclude
    @ManyToOne
    private PaseadorEntity paseador;
   
    @PodamExclude
    @ManyToOne
    private RecorridoEntity recorrido;
   
    @PodamExclude
    @OneToMany(mappedBy = "paseo", fetch = FetchType.LAZY)
    private List<ContratoPaseoEntity> contratosPaseo = new ArrayList<>();

    /**
     * @return the cupoMaximo
     */
    public Integer getCupoMaximo() {
        return cupoMaximo;
    }

    /**
     * @param cupoMaximo the cupoMaximo to set
     */
    public void setCupoMaximo(Integer cupoMaximo) {
        this.cupoMaximo = cupoMaximo;
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
     * @return the duracion
     */
    public Integer getDuracion() {
        return duracion;
    }

    /**
     * @param duracion the duracion to set
     */
    public void setDuracion(Integer duracion) {
        this.duracion = duracion;
    }

    /**
     * @return the disponible
     */
    public Boolean getDisponible() {
        return disponible;
    }

    /**
     * @param disponible the disponible to set
     */
    public void setDisponible(Boolean disponible) {
        this.disponible = disponible;
    }

    /**
     * @return the horaInicio
     */
    public Date getHoraInicio() {
        return horaInicio;
    }

    /**
     * @param horaInicio the horaInicio to set
     */
    public void setHoraInicio(Date horaInicio) {
        this.horaInicio = horaInicio;
    }

    /**
     * @return the horario1
     */
    public HorarioEntity getHorario1() {
        return horario1;
    }

    /**
     * @param horario1 the horario1 to set
     */
    public void setHorario1(HorarioEntity horario1) {
        this.horario1 = horario1;
    }

    /**
     * @return the horario2
     */
    public HorarioEntity getHorario2() {
        return horario2;
    }

    /**
     * @param horario2 the horario2 to set
     */
    public void setHorario2(HorarioEntity horario2) {
        this.horario2 = horario2;
    }

    /**
     * @return the paseador
     */
    public PaseadorEntity getPaseador() {
        return paseador;
    }

    /**
     * @param paseador the paseador to set
     */
    public void setPaseador(PaseadorEntity paseador) {
        this.paseador = paseador;
    }

    /**
     * @return the recorrido
     */
    public RecorridoEntity getRecorrido() {
        return recorrido;
    }

    /**
     * @param recorrido the recorrido to set
     */
    public void setRecorrido(RecorridoEntity recorrido) {
        this.recorrido = recorrido;
    }

    /**
     * @return the contratoPaseo
     */
    public List<ContratoPaseoEntity> getContratosPaseo() {
        return contratosPaseo;
    }

    /**
     * @param contratoPaseo the contratoPaseo to set
     */
    public void setContratosPaseo(List<ContratoPaseoEntity> contratoPaseo) {
        this.contratosPaseo = contratoPaseo;
    }

    /**
     * @return the pagoPaseador
     */
    public PagoPaseadorEntity getPagoPaseador() {
        return pagoPaseador;
    }

    /**
     * @param pagoPaseador the pagoPaseador to set
     */
    public void setPagoPaseador(PagoPaseadorEntity pagoPaseador) {
        this.pagoPaseador = pagoPaseador;
    }
}
