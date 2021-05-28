/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.ejb;

import co.edu.uniandes.csw.paseadores.entities.ClienteEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.ClientePersistence;
import java.util.List;
import java.util.Objects;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Edgar Camilo Diaz Suarez
 */
@Stateless
public class ClienteLogic {
    
      @Inject
    private ClientePersistence persistence;
    
    
    public ClienteEntity createCliente (ClienteEntity clienteEntity) throws BusinessLogicException {
            
        if(persistence.findByIdentificacion(clienteEntity.getIdentificacion())!=null ){
             throw new BusinessLogicException("Ya existe un Cliente con la identificación \"" + clienteEntity.getIdentificacion() + "\"");
    
        }
        if(persistence.findByCorreo(clienteEntity.getCorreo())!=null ){
             throw new BusinessLogicException("Ya existe un Cliente con ese correo \"" + clienteEntity.getCorreo() + "\"");
    
        }
        return persistence.create(clienteEntity);
    }
    
    public List<ClienteEntity> getClientes(){
        return persistence.findAll();
    }
    
    
    public ClienteEntity getCliente(Long clienteId){
         return  persistence.find(clienteId);
    }
    
    public ClienteEntity getClienteByCorreo(String clienteCorreo){
         return  persistence.findByCorreo(clienteCorreo);
         
    }
    
    public ClienteEntity getClienteByIdentificacion(String clienteIdentificacion){
         return persistence.findByIdentificacion(clienteIdentificacion);
         
    }
    
    public ClienteEntity updateCliente(ClienteEntity clienteEntity) throws BusinessLogicException{
        
        ClienteEntity clienteByIdentificacion = persistence.findByIdentificacion(clienteEntity.getIdentificacion());
       
        if(clienteByIdentificacion != null && !Objects.equals(clienteByIdentificacion.getId(), clienteEntity.getId())){
             throw new BusinessLogicException("Ya existe un Cliente con la identificación \"" + clienteEntity.getIdentificacion() + "\"");
    
        }
        
        ClienteEntity clienteByCorreo = persistence.findByCorreo(clienteEntity.getCorreo());
        
        if(clienteByCorreo !=null && !Objects.equals(clienteByCorreo.getId(), clienteEntity.getId())){
             throw new BusinessLogicException("Ya existe un Cliente con el correo \"" + clienteEntity.getCorreo()+ "\"");
    
        }
        return persistence.update(clienteEntity);
    }
    
    public void deleteCliente(Long clienteId) {   
       persistence.delete(clienteId);
    }
}
