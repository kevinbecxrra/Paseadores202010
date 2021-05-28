/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.ejb;

import co.edu.uniandes.csw.paseadores.entities.ContratoHotelEntity;
import co.edu.uniandes.csw.paseadores.entities.PagoClienteEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.ContratoHotelPersistence;
import co.edu.uniandes.csw.paseadores.persistence.PagoClientePersistence;
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
public class ContratoHotelLogic {

    private static final Logger LOGGER = Logger.getLogger(ClienteLogic.class.getName());

    @Inject
    private ContratoHotelPersistence contratoHotelPersistence;

    @Inject
    private PagoClientePersistence pagoClientePersistence;

    public ContratoHotelEntity createContratoHotel(ContratoHotelEntity contratoHotelEntity) throws BusinessLogicException {

        LOGGER.log(Level.INFO, "Inicia proceso de creación del cliente");
        if (contratoHotelEntity.getCosto() < 0) {
            throw new BusinessLogicException("El costo del contrato de un hotel debe ser positivo");
        }
        contratoHotelEntity = contratoHotelPersistence.create(contratoHotelEntity);
        LOGGER.log(Level.INFO, "Termina proceso de creación del cliente");
        return contratoHotelEntity;
    }

    public List<ContratoHotelEntity> getContratosHotel()
    {
        return contratoHotelPersistence.findAll();
    }
    
    public ContratoHotelEntity getContratoHotel(Long contratoHotelId) {
        LOGGER.log(Level.INFO, "Inicia proceso de consultar el cliente con id = {0}", contratoHotelId);
        ContratoHotelEntity contratoHotelEntity = contratoHotelPersistence.find(contratoHotelId);
        if (contratoHotelEntity == null) {
            LOGGER.log(Level.SEVERE, "La calificacion con el id = {0} no existe", contratoHotelId);
        }
        LOGGER.log(Level.INFO, "Termina proceso de consultar el cliente con id = {0}", contratoHotelId);
        return contratoHotelEntity;
    }

    public ContratoHotelEntity updateContratoHotel( ContratoHotelEntity contratoHotelEntity) throws BusinessLogicException {
        if (contratoHotelEntity.getCosto() < 0) {
            throw new BusinessLogicException("El costo del contrato de un hotel debe ser positivo");
        }
        return contratoHotelPersistence.update(contratoHotelEntity);
    }

    public void deleteContratoHotel(Long contratoHotelId) {
        LOGGER.log(Level.INFO, "Inicia proceso de borrar el cliente con id = {0}", contratoHotelId);
        contratoHotelPersistence.delete(contratoHotelId);
        LOGGER.log(Level.INFO, "Termina proceso de borrar el cliente con id = {0}", contratoHotelId);
    }

    /**
     *
     *
     * @param puntoId
     * @param contratoPaseoId
     * @return El puntoBusqueda encontrado, null si no lo encuentra.
     * @throws BusinessLogicException si no existe el contratoPaseoBusq
     */
    public ContratoHotelEntity addPago(Long clienteId, Long pagoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de añadir el pago de un cliente con id = {0}", clienteId);
        ContratoHotelEntity contratoHotel = contratoHotelPersistence.find(clienteId);
        PagoClienteEntity pagoCliente = pagoClientePersistence.find(pagoId);
        if (pagoCliente == null) {
            throw new BusinessLogicException("El pagoCliente no existe");
        } else {
            contratoHotel.setPago(pagoCliente);
        }
        LOGGER.log(Level.INFO, "Termina proceso de borrar el cliente con id = {0}", clienteId);
        return contratoHotelPersistence.find(clienteId);

    }

    /**
     *
     *
     * @param clienteId
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     * si no existe el contrato
     *
     */
    public void removePago(Long clienteId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de eliminar el pago de un cliente con id = {0}", clienteId);
        ContratoHotelEntity contrato = contratoHotelPersistence.find(clienteId);
        contrato.setPago(null);
        }
    

    public PagoClienteEntity getPago(Long clienteId) {
        LOGGER.log(Level.INFO, "Inicia proceso de encontrar el pago de un cliente con id = {0}", clienteId);
        ContratoHotelEntity contrato = contratoHotelPersistence.find(clienteId);
        return contrato.getPago();

    }

    public ContratoHotelEntity replacePago(Long clienteId, Long pagoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "Inicia proceso de reemplazar el pago de un cliente con id = {0}", clienteId);
        ContratoHotelEntity contratoHotelEntity = contratoHotelPersistence.find(clienteId);
        PagoClienteEntity pagoClienteEntity = pagoClientePersistence.find(pagoId);
        if(pagoClienteEntity == null){
            throw new BusinessLogicException("El nuevo pagoCliente no existe");
        }
        else{
        contratoHotelEntity.setPago(pagoClienteEntity);
        }
        return contratoHotelPersistence.find(clienteId);

    }
}
