/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.ejb;

import co.edu.uniandes.csw.paseadores.entities.HoraHotelEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.HoraHotelPersistence;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Kevin Camilo Becerra Walteros
 */
@Stateless
public class HoraHotelLogic {

    private static final Logger LOGGER = Logger.getLogger(ClienteLogic.class.getName());

    @Inject
    private HoraHotelPersistence persistence;

    public HoraHotelEntity createHoraHotel(HoraHotelEntity horaHotelEntity) throws BusinessLogicException {

        LOGGER.log(Level.INFO, "Inicia proceso de creación de la hora del hotel");
        if (horaHotelEntity.getCostoBase() < 0) {
            throw new BusinessLogicException("El costo base de una hora de hotel debe ser un número positivo");
        }
        if (horaHotelEntity.getCupoMaximo() < 0) {
            throw new BusinessLogicException("El cupo máximo de una horaHotel debe ser un entero positivo");
        }
        horaHotelEntity = persistence.create(horaHotelEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la hora del hotel");
        return horaHotelEntity;
    }

    public HoraHotelEntity getHoraHotel(Long horaHotelId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la hora del hotel con id = {0}", horaHotelId);
        HoraHotelEntity horaHotelEntity = persistence.find(horaHotelId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar la hora del hotel con id = {0}", horaHotelId);
        return horaHotelEntity;
    }
    
    public List<HoraHotelEntity> getHorasHotel()
    {
        return persistence.findAll();
    }

    public List<HoraHotelEntity> getHoraHotelByDiaInRange(Date inf, Date sup) {
        return persistence.findByDiaInRange(inf, sup);
    }

    public List<HoraHotelEntity> getHoraHotelByCostoBaseInRange(Double inf, Double sup) {
        return persistence.findByCostoBaseInRange(inf, sup);
    }

    public HoraHotelEntity updateHoraHotel(HoraHotelEntity horaHotelEntity) throws BusinessLogicException {
        if (horaHotelEntity.getCostoBase() < 0) {
            throw new BusinessLogicException("El costo base de una hora de hotel debe ser un número positivo");
        }
        if (horaHotelEntity.getCupoMaximo() < 0) {
            throw new BusinessLogicException("El cupo máximo de una horaHotel debe ser un entero positivo");
        }
        return persistence.update(horaHotelEntity);
    }

    public void deleteHoraHotel(Long horaHotelId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar la hora del hotel con id = {0}", horaHotelId);
        persistence.delete(horaHotelId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar la hora del hotel con id = {0}", horaHotelId);
    }

}
