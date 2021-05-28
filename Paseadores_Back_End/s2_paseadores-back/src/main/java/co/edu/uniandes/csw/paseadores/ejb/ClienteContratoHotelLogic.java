/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.ejb;

import co.edu.uniandes.csw.paseadores.entities.ClienteEntity;
import co.edu.uniandes.csw.paseadores.entities.ContratoHotelEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.ClientePersistence;
import co.edu.uniandes.csw.paseadores.persistence.ContratoHotelPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Edgar Camilo Diaz Suarez
 */
@Stateless
public class ClienteContratoHotelLogic {
    
    @Inject
    private ClientePersistence clientePersistence;
    
    @Inject
    private ContratoHotelPersistence contratoHotelPersistence;

    public ClienteEntity addCliente(Long clienteId,Long contratoHotelId){
       ContratoHotelEntity contratoHotelEntity = contratoHotelPersistence.find(contratoHotelId);
       ClienteEntity clienteEntity = clientePersistence.find(clienteId);
       contratoHotelEntity.setCliente(clienteEntity);
       return clienteEntity;
    }
    
    public ContratoHotelEntity addContratoHotel(Long clienteId, Long contratoHotelId) {
        ClienteEntity clienteEntity = clientePersistence.find(clienteId);
        ContratoHotelEntity contratoHotelEntity = contratoHotelPersistence.find(contratoHotelId);
        contratoHotelEntity.setCliente(clienteEntity);
        return contratoHotelPersistence.find(contratoHotelId);
    }

    public ClienteEntity getCliente(Long contratoId) {
        return contratoHotelPersistence.find(contratoId).getCliente();
        
    }
    
    public ContratoHotelEntity getContratoHotel(Long clienteId, Long contratoHotelId) throws BusinessLogicException {
        
        List<ContratoHotelEntity> contratosHotel = clientePersistence.find(clienteId).getContratosHotel();
        ContratoHotelEntity contratoHotelEntity = contratoHotelPersistence.find(contratoHotelId);
        int index = contratosHotel.indexOf(contratoHotelEntity);
        
        if (index >= 0) {
            return contratosHotel.get(index);
        }
        throw new BusinessLogicException("El ContratoHotel no está asociado al Cliente");
    }
    
    public List<ContratoHotelEntity> getContratosHotel(Long clienteId) {
        return clientePersistence.find(clienteId).getContratosHotel();
    }
    
    public ClienteEntity replaceCliente(Long clienteId, Long contratoHotelId) {
       ClienteEntity clienteEntity = clientePersistence.find(clienteId);
       ContratoHotelEntity contratoHotelEntity = contratoHotelPersistence.find(contratoHotelId);
       contratoHotelEntity.setCliente(clienteEntity);
       return clientePersistence.find(clienteId);
    }
    
    public List<ContratoHotelEntity> replaceContratosHotel(Long clienteId, List<ContratoHotelEntity> list) {
        ClienteEntity clienteEntity = clientePersistence.find(clienteId);
        int numContratosHotel = clienteEntity.getContratosHotel().size();
        for(int i=0; i<numContratosHotel; i++)
        {
            ContratoHotelEntity contratoHotelDel = clienteEntity.getContratosHotel().get(0);
            ContratoHotelEntity contratoHotelNuevoEntity = contratoHotelPersistence.find(contratoHotelDel.getId());
            clienteEntity.getContratosHotel().remove(contratoHotelDel);
            contratoHotelNuevoEntity.setCliente(null);
        }
        for(ContratoHotelEntity contratoHotel : list)
        {
            ContratoHotelEntity contratoHotelNuevoEntity = contratoHotelPersistence.find(contratoHotel.getId());
            contratoHotelNuevoEntity.setCliente(clienteEntity);
            clienteEntity.getContratosHotel().add(contratoHotelNuevoEntity);
        }
        return clientePersistence.find(clienteId).getContratosHotel();
    }
    
    public void removeContratoHotel(Long contratoHotelId) {
        ContratoHotelEntity contratoHotelEntity = contratoHotelPersistence.find(contratoHotelId);
        if (contratoHotelEntity == null) {
            throw new WebApplicationException("El recurso /contratoshotel/" + contratoHotelId + " no existe.", 404);
        }
        ClienteEntity clienteEntity = clientePersistence.find(contratoHotelEntity.getCliente().getId());
        contratoHotelEntity.setCliente(null);
        clienteEntity.getContratosHotel().remove(contratoHotelEntity);
    }
}
