/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.ejb;

import co.edu.uniandes.csw.paseadores.entities.PagoPaseadorEntity;
import co.edu.uniandes.csw.paseadores.entities.PaseadorEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.PagoPaseadorPersistence;
import co.edu.uniandes.csw.paseadores.persistence.PaseadorPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author julian Oliveros<je.oliverosf at uniandes.edu.co>
 */
@Stateless
public class PaseadorPagoPaseadorLogic {
    
    @Inject
    private PagoPaseadorPersistence pagoPaseadorPersistence;
    
    @Inject
    private PaseadorPersistence paseadorPersistence;  
    
    /**
     * Agregar un paseador a un pago paseador
     *
     * @param pagoPaseadorId El id paseo a guardar
     * @param paseadorId El id del recorrido al cual se le va a guardar el paseo.
     * @return El paseo que fue agregado al recorrido.
     */
    public PaseadorEntity addPaseador(Long paseadorId, Long pagoPaseadorId) {
        
        PaseadorEntity paseadorEntity = paseadorPersistence.find(paseadorId);
        PagoPaseadorEntity pagoPaseadorEntity = pagoPaseadorPersistence.find(pagoPaseadorId);       
        pagoPaseadorEntity.setPaseador(paseadorEntity);
        return paseadorPersistence.find(paseadorId);
    }

        /**
     * Agregar un pagopaseador a unpaseador
     *
     * @param pagoPaseadorId El id paseo a guardar
     * @param paseadorId El id del recorrido al cual se le va a guardar el paseo.
     * @return El paseo que fue agregado al recorrido.
     */
    public PagoPaseadorEntity addPagoPaseador(Long paseadorId, Long pagoPaseadorId) {
        
        PaseadorEntity paseadorEntity = paseadorPersistence.find(paseadorId);
        PagoPaseadorEntity pagoPaseadorEntity = pagoPaseadorPersistence.find(pagoPaseadorId);        
        pagoPaseadorEntity.setPaseador(paseadorEntity);
        return pagoPaseadorPersistence.find(pagoPaseadorId);
    }
    
    /**
     *
     * Obtener un paseo por medio de su id y el de su recorrido.
     *
     * @param pagoPaseadorId id del paseo a ser buscado.
     * @return el recorrido solicitada por medio de su id.
     */
    public PaseadorEntity getPaseador(Long pagoPaseadorId) {
        return pagoPaseadorPersistence.find(pagoPaseadorId).getPaseador();
    }

    public List<PagoPaseadorEntity> getPagosPaseador(Long paseadorId)
    {
        return paseadorPersistence.find(paseadorId).getPagos();
    }
    
    /**
     * Retorna un pago paseador asociado a una paseador
     *
     * @param paseadorId El id de la paseador a buscar.
     * @param pagoPaseadorId El id del pagoPaseador a buscar
     * @return El pagoPaseador encontrado dentro de la paseador.
     * @throws BusinessLogicException Si el pagoPaseador no se encuentra en la
     * paseador
     */
    public PagoPaseadorEntity getPagoPaseador(Long paseadorId, Long pagoPaseadorId) throws BusinessLogicException {
        
        List<PagoPaseadorEntity> pagos = paseadorPersistence.find(paseadorId).getPagos();
        PagoPaseadorEntity pagoPaseadorEntity = pagoPaseadorPersistence.find(pagoPaseadorId);
        int index = pagos.indexOf(pagoPaseadorEntity);
        if (index >= 0) {
            return pagos.get(index);
        }
        throw new BusinessLogicException("El pago PagoPaseador no está asociado al paseador");
    }
    
    /**
     * Remplazar recorrido de un paseo
     *
     * @param pagoPaseadorId el id del paseo que se quiere actualizar.
     * @param paseadorId El id del nuebo paseador asociado al paseo.
     * @return el nuevo recorrido asociado.
     */
    public PaseadorEntity replacePaseador(Long pagoPaseadorId, Long paseadorId) {
        PaseadorEntity paseadorEntity = paseadorPersistence.find(paseadorId);
        PagoPaseadorEntity pagoPaseadorEntity = pagoPaseadorPersistence.find(pagoPaseadorId);
        pagoPaseadorEntity.setPaseador(paseadorEntity);
        return paseadorPersistence.find(paseadorId);
    }

    /**
     * Remplazar pago paseador de un paseador
     
     * @param paseadorId El id del nuevo psgo cliente asociado al cliente.
     * @param list
     * @return el nuevo cliente asociado.
     */
    public List<PagoPaseadorEntity> replacePagosPaseador(Long paseadorId, List<PagoPaseadorEntity> list) 
    {        
        PaseadorEntity paseadorEntity = paseadorPersistence.find(paseadorId);
        int numPagosPaseador = paseadorEntity.getPagos().size();
        for(int i=0; i<numPagosPaseador; i++)
        {
            PagoPaseadorEntity pagoDel = paseadorEntity.getPagos().get(0);
            PagoPaseadorEntity pagoPaseadorEntity = pagoPaseadorPersistence.find(pagoDel.getId());
            paseadorEntity.getPagos().remove(pagoDel);
            pagoPaseadorEntity.setPaseador(null);
        }
        for(PagoPaseadorEntity pago : list)
        {
            PagoPaseadorEntity pagoPaseadorEntity = pagoPaseadorPersistence.find(pago.getId());
            pagoPaseadorEntity.setPaseador(paseadorEntity);
            paseadorEntity.getPagos().add(pagoPaseadorEntity);
        }
        return paseadorPersistence.find(paseadorId).getPagos();
    }
    
    /**
     * Borrar el recorrido de un paseo
     *
     * @param pagoPaseadorId El paseo que se desea borrar del recorrido.
     */
    public void removePagoPaseador(Long pagoPaseadorId) {
        PagoPaseadorEntity pagoPaseadorEntity = pagoPaseadorPersistence.find(pagoPaseadorId);
        PaseadorEntity paseadorEntity = paseadorPersistence.find(pagoPaseadorEntity.getPaseador().getId());
        paseadorEntity.getPagos().remove(pagoPaseadorEntity);
        pagoPaseadorEntity.setPaseador(null);
    }
}
