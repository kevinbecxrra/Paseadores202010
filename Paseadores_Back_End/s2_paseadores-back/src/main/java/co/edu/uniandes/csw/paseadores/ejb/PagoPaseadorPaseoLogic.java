/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.ejb;

import co.edu.uniandes.csw.paseadores.entities.PagoPaseadorEntity;
import co.edu.uniandes.csw.paseadores.entities.PaseoEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.PagoPaseadorPersistence;
import co.edu.uniandes.csw.paseadores.persistence.PaseoPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author julian Oliveros<je.oliverosf at uniandes.edu.co>
 */
@Stateless
public class PagoPaseadorPaseoLogic {
    
    @Inject
    private PagoPaseadorPersistence pagoPaseadorPersistence;
    
    @Inject
    private PaseoPersistence paseoPersistence;    
    
    /**
     * Agregar un pago paseador a un paseo
     *
     * @param pagoPaseadorId El id paseo a guardar
     * @param pPaseoId El id del recorrido al cual se le va a guardar el paseo.
     * @return El paseo que fue agregado al recorrido.
     */
    public PagoPaseadorEntity addPagoPaseador(Long pagoPaseadorId, Long pPaseoId) {        
        PagoPaseadorEntity pagoEnti = pagoPaseadorPersistence.find(pagoPaseadorId);
        PaseoEntity pEntity = paseoPersistence.find(pPaseoId);
        pEntity.setPagoPaseador(pagoEnti);
        return pagoPaseadorPersistence.find(pagoPaseadorId);
    }

    /**
     * Agregar un paseo a un pago paseador
     *
     * @param pagoPaseadorId El id paseo a guardar
     * @param pPaseoId El id del recorrido al cual se le va a guardar el paseo.
     * @return El paseo que fue agregado al recorrido.
     */
    public PaseoEntity addPaseo(Long pagoPaseadorId, Long pPaseoId) {       
        PagoPaseadorEntity pagoEnti = pagoPaseadorPersistence.find(pagoPaseadorId);
        PaseoEntity pEntity = paseoPersistence.find(pPaseoId);
        pEntity.setPagoPaseador(pagoEnti);
        return paseoPersistence.find(pPaseoId);
    }
    
    /**
     * Obtener un paseo por medio de su id y el de su recorrido.
     * @param paseoId id del paseo a ser buscado.
     * @return el recorrido solicitada por medio de su id.
     */
    public PagoPaseadorEntity getPagoPaseador(Long paseoId) {
        
        return paseoPersistence.find(paseoId).getPagoPaseador();
    }

    public List<PaseoEntity> getPaseos (Long pagoPaseadorId)
    {
        return pagoPaseadorPersistence.find(pagoPaseadorId).getPaseos();
    }
    
    /**
     * Retorna un paseo asociado a una pago paseador
     *
     * @param pagoPaseadorId El id de la pago paseador a buscar.
     * @param paseoID El id del paseo a buscar
     * @return El paseo encontrado dentro de la pago paseador.
     * @throws BusinessLogicException Si el paseo no se encuentra en la
     * pago paseador
     */
    public PaseoEntity getPaseo(Long pagoPaseadorId, Long paseoID) throws BusinessLogicException 
    {        
        List<PaseoEntity> paseos = pagoPaseadorPersistence.find(pagoPaseadorId).getPaseos();
        PaseoEntity paseoEntity = paseoPersistence.find(paseoID);
        int index = paseos.indexOf(paseoEntity);       
        if (index >= 0) {
            return paseos.get(index);
        }
        throw new BusinessLogicException("El paseo no está asociado al pagoPaseador");
    }
    
    /**
     * Remplazar recorrido de un paseo
     *
     * @param paseoId el id del paseo que se quiere actualizar.
     * @param pagoPaseadorId El id del nuebo recorrido asociado al paseo.
     * @return el nuevo recorrido asociado.
     */
    public PagoPaseadorEntity replacePagoPaseador(Long paseoId, Long pagoPaseadorId) 
    {
        PagoPaseadorEntity pagoPaseadorEntity = pagoPaseadorPersistence.find(pagoPaseadorId);
        PaseoEntity paseoEntity = paseoPersistence.find(paseoId);
        paseoEntity.setPagoPaseador(pagoPaseadorEntity);       
        return pagoPaseadorPersistence.find(pagoPaseadorId);
    }

    /**
     * Remplazar pago cliente de un cliente
     
     * @param pagoPaseadorId El id del nuevo psgo cliente asociado al cliente.
     * @param list
     * @return el nuevo cliente asociado.
     */
    public List<PaseoEntity> replacePaseos(Long pagoPaseadorId, List<PaseoEntity> list) 
    {
        PagoPaseadorEntity pagoPaseador = pagoPaseadorPersistence.find(pagoPaseadorId);
        int numPaseos = pagoPaseador.getPaseos().size();
        for(int i=0; i<numPaseos; i++)
        {
            PaseoEntity paseoDel = pagoPaseador.getPaseos().get(0);
            PaseoEntity paseoNuevoEntity = paseoPersistence.find(paseoDel.getId());
            pagoPaseador.getPaseos().remove(paseoDel);
            paseoNuevoEntity.setPagoPaseador(null);
        }
        for(PaseoEntity paseo : list)
        {
            PaseoEntity paseoNuevoEntity = paseoPersistence.find(paseo.getId());
            paseoNuevoEntity.setPagoPaseador(pagoPaseador);
            pagoPaseador.getPaseos().add(paseoNuevoEntity);
        }
        return pagoPaseadorPersistence.find(pagoPaseadorId).getPaseos();
    }

    /**
     * Borrar el recorrido de un paseo
     *
     * @param paseoId El paseo que se desea borrar del recorrido.
     */
    public void removePaseo(Long paseoId) {
        PaseoEntity paseoEntity = paseoPersistence.find(paseoId);
        PagoPaseadorEntity pagoPaseadorEntity = pagoPaseadorPersistence.find(paseoEntity.getPagoPaseador().getId());
        pagoPaseadorEntity.getPaseos().remove(paseoEntity);
        paseoEntity.setPagoPaseador(null);
    }   
}
