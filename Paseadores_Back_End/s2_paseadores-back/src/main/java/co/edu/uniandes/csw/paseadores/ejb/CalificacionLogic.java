/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.ejb;

import co.edu.uniandes.csw.paseadores.entities.CalificacionEntity;
import co.edu.uniandes.csw.paseadores.entities.ContratoHotelEntity;
import co.edu.uniandes.csw.paseadores.entities.ContratoPaseoEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.CalificacionPersistence;
import co.edu.uniandes.csw.paseadores.persistence.ContratoHotelPersistence;
import co.edu.uniandes.csw.paseadores.persistence.ContratoPaseoPersistence;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Kevin Camilo Becerra Walteros
 */
@Stateless
public class CalificacionLogic{

    private static final Logger LOGGER = Logger.getLogger(CalificacionLogic.class.getName());

    @Inject
    private CalificacionPersistence calificacionPersistence;

    @Inject
    private ContratoPaseoPersistence contratoPaseoPersistence;

    @Inject
    private ContratoHotelPersistence contratoHotelPersistence;

    public CalificacionEntity createCalificacion(CalificacionEntity calificacionEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de creación de la calificacion");

        if (calificacionEntity.getValoracion() < 0 || calificacionEntity.getValoracion() > 5) {
            throw new BusinessLogicException("La valoración de la calificación debe ser un número entre 0 y 5");
        }
        if(calificacionEntity.getContratoHotel() != null && calificacionEntity.getContratoRecorrido() != null && calificacionEntity.getContratoPaseador() == null ){
            throw new BusinessLogicException("Una calificacion  solo puede estar asociada de forma exclusiva a un paseador, un recorrido o un contrato hotel");

        }
        if(calificacionEntity.getContratoHotel() != null && calificacionEntity.getContratoRecorrido() == null && calificacionEntity.getContratoPaseador() != null ){
            throw new BusinessLogicException("Una calificación solo puede estar asociada de forma exclusiva a un tipo definido previamente");

        }
        if(calificacionEntity.getContratoHotel() == null && calificacionEntity.getContratoRecorrido() != null && calificacionEntity.getContratoPaseador() != null ){
            throw new BusinessLogicException("Una calificacion   solo puede estar asociada exclusivamente a un tipo");

        }
        
        if(calificacionEntity.getContratoHotel() != null && calificacionEntity.getContratoRecorrido() != null && calificacionEntity.getContratoPaseador() != null ){
            throw new BusinessLogicException("Una calificacion solo puede estar asociada exclusivamente a un paseador, un recorrido o un contrato hotel");

        }
        
        LOGGER.log(Level.INFO, "Todas las reglas de negocio se cumplieron");

        

        calificacionEntity = calificacionPersistence.create(calificacionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación de la calificación");
        return calificacionEntity;
    }

    public CalificacionEntity getCalificacion(Long calificacionId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar la calificacion con id = {0}", calificacionId);
        CalificacionEntity calificacionEntity = calificacionPersistence.find(calificacionId);
        LOGGER.log(Level.INFO, "Termina proceso de consultar la calificacion con id = {0}", calificacionId);
        return calificacionEntity;
    }

    public CalificacionEntity updateCalificacion(Long calificacionId, CalificacionEntity calificacionEntity) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de actualizar la calificacion con id = {0}", calificacionId);

        if (calificacionEntity.getValoracion() < 0 || calificacionEntity.getValoracion() > 5) {
            throw new BusinessLogicException("La valoración de la calificación debe ser un número entre 0 y 5");
        }
        
        if(calificacionEntity.getContratoHotel() != null && calificacionEntity.getContratoRecorrido() != null && calificacionEntity.getContratoPaseador() == null ){
            throw new BusinessLogicException("Una calificacion solo puede estar asociada de forma exclusiva a un paseador, un recorrido o un contrato hotel");
        }
        if(calificacionEntity.getContratoHotel() != null && calificacionEntity.getContratoRecorrido() == null && calificacionEntity.getContratoPaseador() != null ){
            throw new BusinessLogicException("Una calificacion   solo puede estar asociada de forma exclusiva a un paseador, un recorrido o un contrato hotel");

        }
        if(calificacionEntity.getContratoHotel() == null && calificacionEntity.getContratoRecorrido() != null && calificacionEntity.getContratoPaseador() != null ){
            throw new BusinessLogicException("Una calificación solo puede estar asociada de forma exclusiva a un paseador, un recorrido o un contrato hotel");

        }
        
        if(calificacionEntity.getContratoHotel() != null && calificacionEntity.getContratoRecorrido() != null && calificacionEntity.getContratoPaseador() != null ){
            throw new BusinessLogicException("Una calificacion    solo puede estar asociada exclusivamente a un paseador, un recorrido o un contrato hotel");
        }

        CalificacionEntity newCalificacionEntity = calificacionPersistence.update(calificacionEntity);
        LOGGER.log(Level.INFO, "Termina proceso de actualizar la calificacion con id = {0}", calificacionId);
        return newCalificacionEntity;
    }

    /**
     * Eliminar una calificacion por ID
     *
     * @param calificacionId El ID de la calificacion a eliminar
     */
    public void deleteCalificacion(Long calificacionId) {
        calificacionPersistence.delete(calificacionId);
    }

    /**
     *
     *
     * @param calificacionId
     * @param contratoPaseoId
     * @return El calificacionBusqueda encontrado, null si no lo encuentra.
     * @throws BusinessLogicException si no existe el contratoPaseoBusq
     */
    public CalificacionEntity addContratoRecorrido(Long calificacionId, Long contratoPaseoId) throws BusinessLogicException {
        CalificacionEntity calificacion = calificacionPersistence.find(calificacionId);
        if (calificacion == null) {
            throw new BusinessLogicException("La calificacion no   existe");
        }
        if (calificacion.getContratoHotel() != null || calificacion.getContratoPaseador() != null) {
            throw new BusinessLogicException("La calificación    no puede estar asociada a más de un tipo de contrato");
        }
        ContratoPaseoEntity contratoRecorrido = contratoPaseoPersistence.find(contratoPaseoId);
        if (contratoRecorrido == null) {
            throw new BusinessLogicException("El contratoPaseo no existe en la base de datos");
        } else {
            calificacion.setContratoRecorrido(contratoRecorrido);
        }
        return calificacionPersistence.find(calificacionId);
    }

    /**
     *
     *
     * @param calificacionId
     * @param contratoPaseoId
     * @return El calificacionBusqueda encontrado, null si no lo encuentra.
     * @throws BusinessLogicException si no existe el contratoPaseoBusq
     */
    public CalificacionEntity addContratoPaseador(Long calificacionId, Long contratoPaseoId) throws BusinessLogicException {
        CalificacionEntity calificacion = calificacionPersistence.find(calificacionId);
        if (calificacion == null) {
            throw new BusinessLogicException("La calificación no existe");
        }
        if (calificacion.getContratoHotel() != null || calificacion.getContratoRecorrido() != null) {
            throw new BusinessLogicException("La calificación no puede estar asociada a más de un contrato");
        }
        ContratoPaseoEntity contratoPaseador = contratoPaseoPersistence.find(contratoPaseoId);
        if (contratoPaseador == null) {
            throw new BusinessLogicException("El contratoPaseo no existe");
        } else {
            calificacion.setContratoPaseador(contratoPaseador);
        }
        return calificacionPersistence.find(calificacionId);
    }

    public CalificacionEntity addContratoHotel(Long calificacionId, Long contratoHotelId) throws BusinessLogicException {
        CalificacionEntity calificacion = calificacionPersistence.find(calificacionId);
        if (calificacion == null) {
            throw new BusinessLogicException("La calificacion     no existe  ");
        }
        if (calificacion.getContratoRecorrido() != null || calificacion.getContratoPaseador() != null) {
            throw new BusinessLogicException("La calificación no     puede estar asociada a más de un tipo de contrato   ");
        }
        ContratoHotelEntity contratoHotel = contratoHotelPersistence.find(contratoHotelId);
        if (contratoHotel == null) {
            throw new BusinessLogicException("El contratoPaseo no existe    ");
        } else {
            calificacion.setContratoHotel(contratoHotel);
        }
        return calificacionPersistence.find(calificacionId);

    }

    /**
     *
     *
     * @param clienteId
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     * si no existe el contrato
     *
     */
    public void removeContratoHotel(Long clienteId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de eliminar el contratoHotel de un cliente con id = {0}", clienteId);
        CalificacionEntity contrato = calificacionPersistence.find(clienteId);
        if (contrato == null) {
            throw new BusinessLogicException("No existe el    contrato solicitado");
        }
        contrato.setContratoHotel(null);
    }

    /**
     *
     *
     * @param clienteId
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     * si no existe el contrato
     *
     */
    public void removeContratoPaseador(Long clienteId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de eliminar el contratoPaseador de un cliente con id = {0}", clienteId);
        CalificacionEntity contrato = calificacionPersistence.find(clienteId);
        if (contrato == null) {
            throw new BusinessLogicException("No existe el contrato pedido");
        }
        contrato.setContratoPaseador(null);
    }
    
    /**
     *
     *
     * @param clienteId
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     * si no existe el contrato
     *
     */
    public void removeContratoRecorrido(Long clienteId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de eliminar el contratoRecorrido de un cliente con id = {0}", clienteId);
        CalificacionEntity contrato = calificacionPersistence.find(clienteId);
        if (contrato == null) {
            throw new BusinessLogicException("No existe el contrato solicitado   ");
        }
        contrato.setContratoRecorrido(null);
    }
    

    public ContratoHotelEntity getContratoHotel(Long clienteId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de encontrar el contratoHotel de un cliente con id = {0}", clienteId);
        CalificacionEntity contrato = calificacionPersistence.find(clienteId);
        return contrato.getContratoHotel();

    }
    
    public ContratoPaseoEntity getContratoPaseador(Long clienteId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de encontrar el contratoPaseador de un cliente con id = {0}", clienteId);
        CalificacionEntity contrato = calificacionPersistence.find(clienteId);
        return contrato.getContratoPaseador();

    }
    
    public ContratoPaseoEntity getContratoRecorrido(Long clienteId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de encontrar el contratoRecorrido de un cliente con id = {0}", clienteId);
        CalificacionEntity contrato = calificacionPersistence.find(clienteId);
        return contrato.getContratoRecorrido();

    }
    

    public CalificacionEntity replaceContratoHotel(Long clienteId, Long pagoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar el contratoHotel de un cliente con id = {0}", clienteId);
        CalificacionEntity calificacionEntity = calificacionPersistence.find(clienteId);
        ContratoHotelEntity contratoHotelEntity = contratoHotelPersistence.find(pagoId);
        if (contratoHotelEntity == null) {
            throw new BusinessLogicException("El nuevo pagoCliente no existe");
        } else {
            calificacionEntity.setContratoHotel(contratoHotelEntity);
        }
        return calificacionPersistence.find(clienteId);

    }
    
    public CalificacionEntity replaceContratoPaseador (Long clienteId, Long pagoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar el contratoPaseador de un cliente con id = {0}", clienteId);
        CalificacionEntity calificacionEntity = calificacionPersistence.find(clienteId);
        ContratoPaseoEntity contratoPaseoEntity = contratoPaseoPersistence.find(pagoId);
        if (contratoPaseoEntity == null) {
            throw new BusinessLogicException("El nuevo pagoCliente no existe en la base de datos");
        } else {
            calificacionEntity.setContratoPaseador(contratoPaseoEntity);
        }
        return calificacionPersistence.find(clienteId);

    }
    
    
    public CalificacionEntity replaceContratoRecorrido(Long clienteId, Long pagoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar el contratoRecorrido de un cliente con id = {0}", clienteId);
        CalificacionEntity calificacionEntity = calificacionPersistence.find(clienteId);
        ContratoPaseoEntity contratoRecorridoEntity = contratoPaseoPersistence.find(pagoId);
        if (contratoRecorridoEntity == null) {
            throw new BusinessLogicException("El nuevo pagoCliente no existe en la BD");
        } else {
            calificacionEntity.setContratoRecorrido(contratoRecorridoEntity);
        }
        return calificacionPersistence.find(clienteId);

    }

}
