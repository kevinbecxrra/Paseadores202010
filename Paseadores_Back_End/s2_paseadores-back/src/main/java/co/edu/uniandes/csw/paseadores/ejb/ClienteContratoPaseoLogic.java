/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.ejb;

import co.edu.uniandes.csw.paseadores.entities.ClienteEntity;
import co.edu.uniandes.csw.paseadores.entities.ContratoPaseoEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.ClientePersistence;
import co.edu.uniandes.csw.paseadores.persistence.ContratoPaseoPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Daniel Mateo Guatibonza Solano
 */
@Stateless
public class ClienteContratoPaseoLogic {
    
    @Inject
    private ClientePersistence clientePersistence;
    
    @Inject
    private ContratoPaseoPersistence contratoPaseoPersistence;
    
    /**
     * 
     * @param clienteId
     * @param contratoPaseoId
     * @return 
     */
    public ClienteEntity addCliente(Long clienteId, Long contratoPaseoId)
    {
        ClienteEntity clienteEntity = clientePersistence.find(clienteId);
        ContratoPaseoEntity contratoPaseoEntity = contratoPaseoPersistence.find(contratoPaseoId);
        contratoPaseoEntity.setCliente(clienteEntity);
        return clienteEntity;
    }
    
    public ContratoPaseoEntity addContratoPaseo(Long clienteId, Long contratoPaseoId) {
        ClienteEntity clienteEntity = clientePersistence.find(clienteId);
        ContratoPaseoEntity contratoPaseoEntity = contratoPaseoPersistence.find(contratoPaseoId);
        contratoPaseoEntity.setCliente(clienteEntity);
        return contratoPaseoPersistence.find(contratoPaseoId);
    }
    
    /**
     * 
     * @param contratoPaseoId
     * @return 
     */
    public ClienteEntity getCliente(Long contratoPaseoId) {
        return contratoPaseoPersistence.find(contratoPaseoId).getCliente();
    }
    public ContratoPaseoEntity getContratoPaseo(Long clienteId, Long contratoPaseoId) throws BusinessLogicException {
        
        List<ContratoPaseoEntity> contratosPaseo = clientePersistence.find(clienteId).getContratosPaseo();
        ContratoPaseoEntity contratoPaseoEntity = contratoPaseoPersistence.find(contratoPaseoId);
        int index = contratosPaseo.indexOf(contratoPaseoEntity);
        
        if (index >= 0) {
            return contratosPaseo.get(index);
        }
        throw new BusinessLogicException("El ContratoPaseo no está asociado al Cliente");
    }
    
    public List<ContratoPaseoEntity> getContratosPaseo(Long clienteId) {
        return clientePersistence.find(clienteId).getContratosPaseo();
    }
    
    /**
     * 
     * @param clienteId
     * @param contratoPaseoId
     * @return 
     */
    public ClienteEntity replaceCliente(Long clienteId, Long contratoPaseoId) {
        ClienteEntity clienteEntity = clientePersistence.find(clienteId);
        ContratoPaseoEntity contratoPaseoEntity = contratoPaseoPersistence.find(contratoPaseoId);
        contratoPaseoEntity.setCliente(clienteEntity);
        return clientePersistence.find(contratoPaseoId);
    }
    
    public List<ContratoPaseoEntity> replaceContratosPaseo(Long clienteId, List<ContratoPaseoEntity> list) {
        ClienteEntity clienteEntity = clientePersistence.find(clienteId);
        int numContratosPaseo = clienteEntity.getContratosPaseo().size();
        for(int i=0; i<numContratosPaseo; i++)
        {
            ContratoPaseoEntity contratoPaseoDel = clienteEntity.getContratosPaseo().get(0);
            ContratoPaseoEntity contratoPaseoNuevoEntity = contratoPaseoPersistence.find(contratoPaseoDel.getId());
            clienteEntity.getContratosPaseo().remove(contratoPaseoDel);
            contratoPaseoNuevoEntity.setCliente(null);
        }
        for(ContratoPaseoEntity contratoPaseo : list)
        {
            ContratoPaseoEntity contratoPaseoNuevoEntity = contratoPaseoPersistence.find(contratoPaseo.getId());
            contratoPaseoNuevoEntity.setCliente(clienteEntity);
            clienteEntity.getContratosPaseo().add(contratoPaseoNuevoEntity);
        }
        return clientePersistence.find(clienteId).getContratosPaseo();
    }
    
    /**
     * 
     * @param contratoPaseoId 
     */
    public void removeContratoPaseo(Long contratoPaseoId) {
        ContratoPaseoEntity contratoPaseoEntity = contratoPaseoPersistence.find(contratoPaseoId);
        ClienteEntity clienteEntity = clientePersistence.find(contratoPaseoEntity.getCliente().getId());
        contratoPaseoEntity.setCliente(null);
        clienteEntity.getContratosPaseo().remove(contratoPaseoEntity);
    }
}
