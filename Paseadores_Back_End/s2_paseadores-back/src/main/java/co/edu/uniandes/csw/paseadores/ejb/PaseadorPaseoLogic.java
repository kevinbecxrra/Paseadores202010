/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.ejb;

import co.edu.uniandes.csw.paseadores.entities.PaseadorEntity;
import co.edu.uniandes.csw.paseadores.entities.PaseoEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.PaseadorPersistence;
import co.edu.uniandes.csw.paseadores.persistence.PaseoPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Alvaro Plata
 */
@Stateless
public class PaseadorPaseoLogic {
    
    @Inject
    private PaseoPersistence paseoPersistence;
    
    @Inject
    private PaseadorPersistence paseadorPersistence;
    
    /**
     * Agregar un paseador a un paseo
     * @param paseadorId id del paseador
     * @param paseoId id del paseo
     * @return el paseador que fue agregado al paseo
     */
    public PaseadorEntity addPaseador(Long paseadorId, Long paseoId) {
        PaseadorEntity paseadorEntity = paseadorPersistence.find(paseadorId);
        PaseoEntity paseoEntity = paseoPersistence.find(paseoId);
        paseoEntity.setPaseador(paseadorEntity);
        return paseadorPersistence.find(paseadorId);
    }
    
    public PaseoEntity addPaseo(Long paseadorId, Long paseoId)
    {
        PaseadorEntity paseadorEntity = paseadorPersistence.find(paseadorId);
        PaseoEntity paseoEntity = paseoPersistence.find(paseoId);
        paseoEntity.setPaseador(paseadorEntity);
        return paseoPersistence.find(paseoId);
    }
    
    /**
     *
     * Obtener un paseador por medio del id de uno de sus paseos
     *
     * @param paseoId id del paseo al que se le quiere buscar su paseador.
     * @return el paseador solicitada por medio del id de uno de sus paseos
     */
    public PaseadorEntity getPaseador(Long paseoId) {
        return paseoPersistence.find(paseoId).getPaseador();
    }
    
    public List<PaseoEntity> getPaseos(Long paseadorId)
    {
        return paseadorPersistence.find(paseadorId).getPaseos();
    }
    
    public PaseoEntity getPaseo(Long paseadorId, Long paseoId) throws BusinessLogicException 
    {        
        List<PaseoEntity> paseos = paseadorPersistence.find(paseadorId).getPaseos();
        PaseoEntity paseoEntity = paseoPersistence.find(paseoId);
        int index = paseos.indexOf(paseoEntity);       
        if (index >= 0) {
            return paseos.get(index);
        }
        throw new BusinessLogicException("El paseo no está asociado al paseador");
    }
    
    /**
     * Remplazar paseador de un paseo
     *
     * @param paseoId el id del paseo que se quiere actualizar.
     * @param paseadorId El id del nuevo paseador asociado al paseo.
     * @return el nuevo paseador asociado.
     */
    public PaseadorEntity replacePaseador(Long paseoId, Long paseadorId) {
        PaseadorEntity paseadorEntity = paseadorPersistence.find(paseadorId);
        PaseoEntity paseoEntity = paseoPersistence.find(paseoId);
        paseoEntity.setPaseador(paseadorEntity);
        return paseadorPersistence.find(paseadorId);
    }
    
    public List<PaseoEntity> replacePaseos(Long paseadorId, List<PaseoEntity> listaPaseos) {
        PaseadorEntity paseadorEntity = paseadorPersistence.find(paseadorId);
        int numPaseos = paseadorEntity.getPaseos().size();
        for(int i=0; i<numPaseos; i++)
        {
            PaseoEntity paseoDel = paseadorEntity.getPaseos().get(0);
            PaseoEntity paseoEntity = paseoPersistence.find(paseoDel.getId());
            paseadorEntity.getPaseos().remove(paseoDel);
            paseoEntity.setPaseador(null);
        }
        for(PaseoEntity paseo : listaPaseos)
        {
            PaseoEntity paseoNuevoEntity = paseoPersistence.find(paseo.getId());
            paseoNuevoEntity.setPaseador(paseadorEntity);
            paseadorEntity.getPaseos().add(paseoNuevoEntity);
        }
        return paseadorPersistence.find(paseadorId).getPaseos();
    }
    
    /**
     * Borrar el paseador de un paseo
     *
     * @param paseoId El paseo que se desea borrar del paseador.
     */
    public void removePaseo(Long paseoId) {
        PaseoEntity paseoEntity = paseoPersistence.find(paseoId);
        PaseadorEntity paseadorEntity = paseadorPersistence.find(paseoEntity.getPaseador().getId());
        paseadorEntity.getPaseos().remove(paseoEntity);
        paseoEntity.setPaseador(null);
    }    
}
