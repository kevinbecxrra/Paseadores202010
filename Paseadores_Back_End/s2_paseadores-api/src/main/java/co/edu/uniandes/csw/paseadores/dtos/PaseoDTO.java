/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.dtos;

import co.edu.uniandes.csw.paseadores.adapters.TimeStampAdapter;
import co.edu.uniandes.csw.paseadores.entities.PaseoEntity;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author Kevin Camilo Becerra Walteros
 */
public class PaseoDTO implements Serializable{

    private Long id;
    
    private Integer cupoMaximo;
    
    private Double costo;
    
    private Integer duracion;
    
    private Boolean disponible;
    
    @XmlJavaTypeAdapter(TimeStampAdapter.class)
    private Date horaInicio;
     
    private HorarioDTO horario1;
   
    private HorarioDTO horario2;
   
    private PagoPaseadorDTO pagoPaseador;
   
    private PaseadorDTO paseador;
 
    private RecorridoDTO recorrido;
    
    public PaseoDTO(){
    }
    
    public PaseoDTO(PaseoEntity paseoEntity){
        if (paseoEntity != null) {
            this.id = paseoEntity.getId();
            this.cupoMaximo = paseoEntity.getCupoMaximo();
            this.costo = paseoEntity.getCosto();
            this.disponible = paseoEntity.getDisponible();
            this.duracion = paseoEntity.getDuracion();
            this.horaInicio = paseoEntity.getHoraInicio();
            if (paseoEntity.getHorario1() != null) {
                this.horario1 = new HorarioDTO(paseoEntity.getHorario1());
            } else {
                this.horario1 = null;
            }
            if (paseoEntity.getHorario2() != null) {
                this.horario2 = new HorarioDTO(paseoEntity.getHorario2());
            } else {
                this.horario2 = null;
            }
              if(paseoEntity.getPagoPaseador() != null){
                  this.pagoPaseador = new PagoPaseadorDTO(paseoEntity.getPagoPaseador());
              }
              else{
                  this.pagoPaseador = null;
              }
              if(paseoEntity.getPaseador() != null){
                  this.paseador = new PaseadorDTO(paseoEntity.getPaseador());
              }
              else{
                  this.paseador = null;
              }
              if(paseoEntity.getRecorrido() != null){
                  this.recorrido = new RecorridoDTO(paseoEntity.getRecorrido());
              }
              else{
                  this.recorrido = null;
              }

        }
    }
    
    /**
     *
     * @return
     */
    public PaseoEntity toEntity(){
        PaseoEntity paseoEntity = new PaseoEntity();
        paseoEntity.setId(this.getId());
        paseoEntity.setCupoMaximo(this.getCupoMaximo());
        paseoEntity.setCosto(this.getCosto());
        paseoEntity.setDuracion(this.getDuracion());
        paseoEntity.setDisponible(this.getDisponible());
        paseoEntity.setHoraInicio(this.getHoraInicio());
        if(this.horario1 != null) {
            paseoEntity.setHorario1(this.horario1.toEntity());
        }
        if(this.horario2 != null) {
            paseoEntity.setHorario2(this.horario2.toEntity());
        }
        if(this.pagoPaseador != null){
            paseoEntity.setPagoPaseador(this.pagoPaseador.toEntity());
        }
        if(this.paseador != null){
            paseoEntity.setPaseador(this.paseador.toEntity());
        }
        if(this.getRecorrido() != null){
            paseoEntity.setRecorrido(this.getRecorrido().toEntity());
        } 
        return paseoEntity;
    }

    /**
     * @return the cupoMaximo
     */
    public Integer getCupoMaximo() {
        return cupoMaximo;
    }

    /**
     * @param cupoMaximoParam the cupoMaximo to set
     */
    public void setCupoMaximo(Integer cupoMaximoParam) {
        this.cupoMaximo = cupoMaximoParam;
    }

    /**
     * @return the costo
     */
    public Double getCosto() {
        return costo;
    }

    /**
     * @param costoParam the costo to set
     */
    public void setCosto(Double costoParam) {
        this.costo = costoParam;
    }

    /**
     * @return the duracion
     */
    public Integer getDuracion() {
        return duracion;
    }

    /**
     * @param duracionParam the duracion to set
     */
    public void setDuracion(Integer duracionParam) {
        this.duracion = duracionParam;
    }

    /**
     * @return the disponible
     */
    public Boolean getDisponible() {
        return disponible;
    }

    /**
     * @param disponibleParam the disponible to set
     */
    public void setDisponible(Boolean disponibleParam) {
        this.disponible = disponibleParam;
    }

    /**
     * @return the horaInicio
     */
    public Date getHoraInicio() {
        return horaInicio;
    }

    /**
     * @param horaInicioParam the horaInicio to set
     */
    public void setHoraInicio(Date horaInicioParam) {
        this.horaInicio = horaInicioParam;
    }

    /**
     * @return the horario1
     */
    public HorarioDTO getHorario1() {
        return horario1;
    }

    /**
     * @param horario1Param the horario1 to set
     */
    public void setHorario1(HorarioDTO horario1Param) {
        this.horario1 = horario1Param;
    }

    /**
     * @return the horario2
     */
    public HorarioDTO getHorario2() {
        return horario2;
    }

    /**
     * @param horario2Param the horario2 to set
     */
    public void setHorario2(HorarioDTO horario2Param) {
        this.horario2 = horario2Param;
    }

    /**
     * @return the pagoPaseador
     */
    public PagoPaseadorDTO getPagoPaseador() {
        return pagoPaseador;
    }

    /**
     * @param pagoPaseadorParam the pagoPaseador to set
     */
    public void setPagoPaseador(PagoPaseadorDTO pagoPaseadorParam) {
        this.pagoPaseador = pagoPaseadorParam;
    }

    /**
     * @return the paseador
     */
    public PaseadorDTO getPaseador() {
        return paseador;
    }

    /**
     * @param paseadorParam the paseador to set
     */
    public void setPaseador(PaseadorDTO paseadorParam) {
        this.paseador = paseadorParam;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param idParam the id to set
     */
    public void setId(Long idParam) {
        this.id = idParam;
    }

    /**
     * @return the recorrido
     */
    public RecorridoDTO getRecorrido() {
        return recorrido;
    }

    /**
     * @param recorridoParam the recorrido to set
     */
    public void setRecorrido(RecorridoDTO recorridoParam) {
        this.recorrido = recorridoParam;
    }
}
