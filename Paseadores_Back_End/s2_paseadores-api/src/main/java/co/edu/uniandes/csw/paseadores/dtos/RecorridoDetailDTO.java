/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.dtos;

import co.edu.uniandes.csw.paseadores.entities.PaseoEntity;
import co.edu.uniandes.csw.paseadores.entities.PuntoEntity;
import co.edu.uniandes.csw.paseadores.entities.RecorridoEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Nicolas Urrego Sandoval <n.urrego at uniandes.edu.co>
 */
public class RecorridoDetailDTO extends RecorridoDTO implements Serializable{
    
    // relación  cero o muchos puntos 
    private List<PuntoDTO> puntos;

    // relación  cero o muchos paseos
    private List<PaseoDTO> paseos;

    public RecorridoDetailDTO() {
        super();
    }

    /**
     * Constructor para transformar un Entity a un DTO
     *
     * @param recorridoEntity La entidad de la cual se construye el DTO
     */
    public RecorridoDetailDTO(RecorridoEntity recorridoEntity) {
        super(recorridoEntity);
        if (recorridoEntity.getPuntos() != null) {
            puntos = new ArrayList<>();
            for (PuntoEntity entityPunto : recorridoEntity.getPuntos()) {
                puntos.add(new PuntoDTO(entityPunto));
            }
        }
        if (recorridoEntity.getPaseos() != null) {
            paseos = new ArrayList<>();
            for (PaseoEntity entityPaseo : recorridoEntity.getPaseos()) {
                paseos.add(new PaseoDTO(entityPaseo));
            }
        }
    }

    /**
     * Transformar el DTO a una entidad
     *
     * @return La entidad que representa el libro.
     */
    @Override
    public RecorridoEntity toEntity() {
        RecorridoEntity recorridoEntity = super.toEntity();
        if (puntos != null) {
            List<PuntoEntity> puntosEntity = new ArrayList<>();
            for (PuntoDTO dtoPunto : getPuntos()) {
                puntosEntity.add(dtoPunto.toEntity());
            }
            recorridoEntity.setPuntos(puntosEntity);
        }
        if (paseos != null) {
            List<PaseoEntity> paseosEntity = new ArrayList<>();
            for (PaseoDTO dtoPaseo : paseos) {
                paseosEntity.add(dtoPaseo.toEntity());
            }
            recorridoEntity.setPaseos(paseosEntity);
        }
        return recorridoEntity;
    }

    /**
     * Devuelve las reseñas asociadas a este libro
     *
     * @return Lista de DTOs de Reseñas
     */
    public List<PuntoDTO> getPuntos() {
        return puntos;
    }

    /**
     * Modifica las reseñas de este libro.
     *
     * @param puntos Las nuevas reseñas
     */
    public void setPuntos(List<PuntoDTO> puntos) {
        this.puntos = puntos;
    }

    /**
     * Devuelve los autores del libro
     *
     * @return DTO de Autores
     */
    public List<PaseoDTO> getPaseos() {
        return paseos;
    }

    /**
     * Modifica los autores del libro
     *
     * @param paseos Lista de autores
     */
    public void setPaseos(List<PaseoDTO> paseos) {
        this.paseos = paseos;
    }
}
