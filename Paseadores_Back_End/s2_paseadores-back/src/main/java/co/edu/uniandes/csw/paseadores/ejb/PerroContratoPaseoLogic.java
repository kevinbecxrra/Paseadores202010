/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.ejb;

import co.edu.uniandes.csw.paseadores.entities.ContratoPaseoEntity;
import co.edu.uniandes.csw.paseadores.entities.PerroEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.ContratoPaseoPersistence;
import co.edu.uniandes.csw.paseadores.persistence.PerroPersistence;
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
public class PerroContratoPaseoLogic {
    
    private static final Logger LOGGER = Logger.getLogger(PerroContratoPaseoLogic.class.getName());
        
    @Inject
    private PerroPersistence perroPersistence;
    
    @Inject
    private ContratoPaseoPersistence contratoPaseoPersistence;
    
    /**
     * Agregar un perro
     * @param perroId
     * @param contratoPaseoId
     * @return 
     */
    public PerroEntity addPerro(Long perroId, Long contratoPaseoId)
    {
        LOGGER.log(Level.INFO, "--Entro a LOGIC ");
        PerroEntity perroEntity = perroPersistence.find(perroId);        
        LOGGER.log(Level.INFO, "--Entro a LOGIC Y ENCONTRO EL PERRO ");        
        ContratoPaseoEntity contratoPaseoEntity = contratoPaseoPersistence.find(contratoPaseoId);        
        LOGGER.log(Level.INFO, "--Entro a LOGIC Y PERSISTE EL CONTRATO ");
        contratoPaseoEntity.setPerro(perroEntity);        
        LOGGER.log(Level.INFO, "--Entro a LOGIC Y SE ASIGNA ");
        return  perroPersistence.find(perroId);
    }
    
    public ContratoPaseoEntity addContratoPaseo(Long perroId, Long contratoPaseoId)
    {
        PerroEntity perroEntity = perroPersistence.find(perroId);
        ContratoPaseoEntity contratoPaseoEntity = contratoPaseoPersistence.find(contratoPaseoId);
        contratoPaseoEntity.setPerro(perroEntity);
        return contratoPaseoPersistence.find(contratoPaseoId);
    }
    
    /**
     * 
     * @param contratoPaseoId
     * @return 
     */
    public PerroEntity getPerro(Long contratoPaseoId) {
        return contratoPaseoPersistence.find(contratoPaseoId).getPerro();
    }
    
    public List<ContratoPaseoEntity> getContratosPaseo(Long perroId)
    {
        return perroPersistence.find(perroId).getPaseos();
    }
    
    public ContratoPaseoEntity getContratoPaseo(Long perroId, Long contratoPaseoId) throws BusinessLogicException 
    {        
        List<ContratoPaseoEntity> contratos = perroPersistence.find(perroId).getPaseos();
        ContratoPaseoEntity contratoPaseoEntity = contratoPaseoPersistence.find(contratoPaseoId);
        int index = contratos.indexOf(contratoPaseoEntity);       
        if (index >= 0) {
            return contratos.get(index);
        }
        throw new BusinessLogicException("El paseo no está asociado al paseador");
    }
    
    /**
     * 
     * @param perroId
     * @param contratoPaseoId
     * @return 
     */
    public PerroEntity replacePerro(Long perroId, Long contratoPaseoId) {
        PerroEntity perroEntity = perroPersistence.find(perroId);
        ContratoPaseoEntity contratoPaseoEntity = contratoPaseoPersistence.find(contratoPaseoId);
        contratoPaseoEntity.setPerro(perroEntity);
        return perroPersistence.find(contratoPaseoId);
    }
    
    public List<ContratoPaseoEntity> replaceContratosPaseo(Long perroId, List<ContratoPaseoEntity> listaContratos) {
        PerroEntity perroEntity = perroPersistence.find(perroId);
        
        int numPuntos = perroEntity.getPaseos().size();
        for(int i=0; i<numPuntos; i++)
        {
            ContratoPaseoEntity puntoDel = perroEntity.getPaseos().get(0);
            ContratoPaseoEntity contratoPaseoEntity = contratoPaseoPersistence.find(puntoDel.getId());
            perroEntity.getPaseos().remove(puntoDel);
            contratoPaseoEntity.setPerro(null);
        }
        for(ContratoPaseoEntity contratoPaseo : listaContratos)
        {
            ContratoPaseoEntity puntoNuevoEntity = contratoPaseoPersistence.find(contratoPaseo.getId());
            puntoNuevoEntity.setPerro(perroEntity);
            perroEntity.getPaseos().add(puntoNuevoEntity);
   
        }
        return perroPersistence.find(perroId).getPaseos();
    }
    
    /**
     * Borra el contrato Paseo
     * @param contratoPaseoId 
     */
    public void removeContratoPaseo(Long contratoPaseoId) {
        ContratoPaseoEntity contratoPaseoEntity = contratoPaseoPersistence.find(contratoPaseoId);
        PerroEntity perroEntity = perroPersistence.find(contratoPaseoEntity.getPerro().getId());
        perroEntity.getPaseos().remove(contratoPaseoEntity);
        contratoPaseoEntity.setPerro(null);
    }
}