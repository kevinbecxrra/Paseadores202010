/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.ejb;

import co.edu.uniandes.csw.paseadores.entities.ContratoPaseoEntity;
import co.edu.uniandes.csw.paseadores.entities.PagoClienteEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.ContratoPaseoPersistence;
import co.edu.uniandes.csw.paseadores.persistence.PagoClientePersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Daniel Mateo Guatibonza Solano
 */

@Stateless
public class ContratoPaseoLogic {
    
    private static final Logger LOGGER = Logger.getLogger(ContratoPaseoLogic.class.getName());
    @Inject
    private ContratoPaseoPersistence contratoPaseoPersistence;
    
    @Inject
    private PagoClientePersistence pagoClientePersistence;
    
    @Inject
    private ContratoPaseoPersistence persistence;
    
    /**
     * 
     * @param contratoPaseoEntity
     * @return
     */
    public ContratoPaseoEntity createContratoPaseo(ContratoPaseoEntity contratoPaseoEntity)
    {
        contratoPaseoEntity = contratoPaseoPersistence.create(contratoPaseoEntity);
        return contratoPaseoEntity;
    }
    
    /**
     * 
     * @param contratoPaseoId
     * @return
     */
    public ContratoPaseoEntity getContratoPaseo(Long contratoPaseoId)
    {
        LOGGER.log(Level.INFO, "-------------ENtro a contrato paseo logic");
        return contratoPaseoPersistence.find(contratoPaseoId);
    }
    
    /**
     * 
     * @param contratoPaseoEntity
     * @return
     */
    public ContratoPaseoEntity updateContratoPaseo(ContratoPaseoEntity contratoPaseoEntity)
    {
        contratoPaseoEntity = contratoPaseoPersistence.update(contratoPaseoEntity);
        return contratoPaseoEntity;
    }
    
    /**
     * 
     * @param contratoPaseoId
     */
    public void deleteContratoPaseo(Long contratoPaseoId)
    {
        contratoPaseoPersistence.delete(contratoPaseoId);
    }
    
    /**
     * 
     * @param contratoPaseoId
     * @param pagoClienteId
     * @return
     * @throws BusinessLogicException 
     */
    public ContratoPaseoEntity addPago(Long contratoPaseoId, Long pagoClienteId) throws BusinessLogicException
    {
        ContratoPaseoEntity contratoPaseo = contratoPaseoPersistence.find(contratoPaseoId);
        PagoClienteEntity pagoCliente = pagoClientePersistence.find(pagoClienteId);
        if(pagoCliente == null)
            throw new BusinessLogicException("El pagoCliente no existe");
        else
            contratoPaseo.setPago(pagoCliente);
        return contratoPaseoPersistence.find(contratoPaseoId);
    }
    
    /**
     * 
     * @param contratoPaseoId
     * @return 
     */
    public PagoClienteEntity getPago(Long contratoPaseoId)
    {
        return contratoPaseoPersistence.find(contratoPaseoId).getPago();
    }
    
    /**
     * 
     * @param contratoPaseoId
     * @param pagoClienteId
     * @return
     * @throws BusinessLogicException 
     */
    public ContratoPaseoEntity replacePago(Long contratoPaseoId, Long pagoClienteId) throws BusinessLogicException
    {
        ContratoPaseoEntity contratoPaseo = contratoPaseoPersistence.find(contratoPaseoId);
        PagoClienteEntity nuevoPagoCliente = pagoClientePersistence.find(pagoClienteId);
        if(nuevoPagoCliente == null)
            throw new BusinessLogicException("El nuevo pagoCliente no existe");
        else
            contratoPaseo.setPago(nuevoPagoCliente);
        return contratoPaseoPersistence.find(contratoPaseoId);
    }
    
    /**
     * 
     * @param contratoPaseoId
     */
    public void removePago(Long contratoPaseoId)    
    {
        contratoPaseoPersistence.find(contratoPaseoId).setPago(null);
    }
    
    public List<ContratoPaseoEntity> getContratosPaseo()
    {
        return persistence.findAll();
    }
    
}
