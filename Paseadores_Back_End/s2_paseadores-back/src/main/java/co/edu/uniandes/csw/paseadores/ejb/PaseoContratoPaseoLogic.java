/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.ejb;

import co.edu.uniandes.csw.paseadores.entities.ContratoPaseoEntity;
import co.edu.uniandes.csw.paseadores.entities.PaseoEntity;
import co.edu.uniandes.csw.paseadores.persistence.ContratoPaseoPersistence;
import co.edu.uniandes.csw.paseadores.persistence.PaseoPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Daniel Mateo Guatibonza Solano
 */
@Stateless
public class PaseoContratoPaseoLogic {
    
    @Inject
    private PaseoPersistence paseoPersistence;
    
    @Inject
    private ContratoPaseoPersistence contratoPaseoPersistence;
    
    /**
     * 
     * @param paseoId
     * @param contratoPaseoId
     * @return 
     */
    public PaseoEntity addPaseo(Long paseoId, Long contratoPaseoId)
    {
        PaseoEntity paseoEntity = paseoPersistence.find(paseoId);
        ContratoPaseoEntity contratoPaseoEntity = contratoPaseoPersistence.find(contratoPaseoId);
        contratoPaseoEntity.setPaseo(paseoEntity);
        return paseoEntity;
    }
    
    /**
     * 
     * @param contratoPaseoId
     * @return 
     */
    public PaseoEntity getPaseo(Long contratoPaseoId) {
        return contratoPaseoPersistence.find(contratoPaseoId).getPaseo();
    }
    
    /**
     * 
     * @param paseoId
     * @param contratoPaseoId
     * @return 
     */
    public PaseoEntity replacePaseo(Long paseoId, Long contratoPaseoId) {
        PaseoEntity paseoEntity = paseoPersistence.find(paseoId);
        ContratoPaseoEntity contratoPaseoEntity = contratoPaseoPersistence.find(contratoPaseoId);
        contratoPaseoEntity.setPaseo(paseoEntity);
        return paseoPersistence.find(contratoPaseoId);
    }
    
    /**
     * 
     * @param contratoPaseoId 
     */
    public void removeContratoPaseo(Long contratoPaseoId) {
        ContratoPaseoEntity contratoPaseoEntity = contratoPaseoPersistence.find(contratoPaseoId);
        PaseoEntity paseoEntity = paseoPersistence.find(contratoPaseoEntity.getPaseo().getId());
        contratoPaseoEntity.setPaseo(null);
        paseoEntity.getContratosPaseo().remove(contratoPaseoEntity);
    }
}
