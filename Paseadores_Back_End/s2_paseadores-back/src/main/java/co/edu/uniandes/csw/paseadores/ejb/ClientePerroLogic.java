/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.ejb;

import co.edu.uniandes.csw.paseadores.entities.ClienteEntity;
import co.edu.uniandes.csw.paseadores.entities.PerroEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.ClientePersistence;
import co.edu.uniandes.csw.paseadores.persistence.PerroPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;


/**
 *
 * @author julian Oliveros<je.oliverosf at uniandes.edu.co>
 */
@Stateless
public class ClientePerroLogic {
    @Inject
    private ClientePersistence clientePersistence;
    
    @Inject
    private PerroPersistence perroPersistence;
    
    
    /**
     * Agregar un pagoCliente a un cliente
     *
     * @param clientesId El id cliente a guardar
     * @param perroId El id del recorrido al cual se le va a guardar el paseo.
     * @return El paseo que fue agregado al recorrido.
     */
    public ClienteEntity addCliente(Long clientesId, Long perroId) {
        
        ClienteEntity clienteEntity = clientePersistence.find(clientesId);
        PerroEntity perroEnti = perroPersistence.find(perroId);
        perroEnti.setCliente(clienteEntity);
        return clientePersistence.find(clientesId);
    }
    
        /**
     * Agregar un pagoCliente a un cliente
     *
     * @param clientesId El id cliente a guardar
     * @param perroId El id del recorrido al cual se le va a guardar el paseo.
     * @return El paseo que fue agregado al recorrido.
     */
    public PerroEntity addPerro(Long clientesId, Long perroId) {
        
        ClienteEntity clienteEntity = clientePersistence.find(clientesId);
        PerroEntity perroEnti = perroPersistence.find(perroId);
        perroEnti.setCliente(clienteEntity);
        return perroPersistence.find(perroId);
    }

    /**
     *
     * Obtener un paseo por medio de su id y el de su recorrido.
     *
     * @param perroId id del paseo a ser buscado.
     * @return el recorrido solicitada por medio de su id.
     */
    public ClienteEntity getCliente(Long perroId) {
        return perroPersistence.find(perroId).getCliente();
    }

    public List<PerroEntity> getPerros(Long cleinteId)
    {
        return clientePersistence.find(cleinteId).getPerros();
    }
    
    /**
     * Retorna un perro asociado a una cliente
     *
     * @param clienteId El id de la cliente a buscar.
     * @param perroId El id del perro a buscar
     * @return El perro encontrado dentro de la cliente.
     * @throws BusinessLogicException Si el perro no se encuentra en la
     * cliente
     */
    public PerroEntity getPerro(Long clienteId, Long perroId) throws BusinessLogicException {
        
        List<PerroEntity> perros = clientePersistence.find(clienteId).getPerros();
        PerroEntity perroEntity = perroPersistence.find(perroId);
        int index = perros.indexOf(perroEntity);
        
        if (index >= 0) {
            return perros.get(index);
        }
        throw new BusinessLogicException("El perro no está asociado al cliente");
    }
    
    
    /**
     * Remplazar recorrido de un paseo
     *
     * @param perroId el id del paseo que se quiere actualizar.
     * @param clienteId El id del nuebo recorrido asociado al paseo.
     * @return el nuevo recorrido asociado.
     */
    public ClienteEntity replaceCliente(Long perroId, Long clienteId) {
        ClienteEntity clienteEntity = clientePersistence.find(clienteId);
        PerroEntity perroEntity = perroPersistence.find(perroId);
        if(perroEntity == null)
            throw new WebApplicationException("El recurso /perros/" + perroId + " no existe.", 404);
        perroEntity.setCliente(clienteEntity);
        return clientePersistence.find(clienteId);
    }
    
        /**
     * Remplazar pago cliente de un cliente
     
     * @param clienteId El id del nuevo cliente asociado al cliente.
     * @param list
     * @return el nuevo cliente asociado.
     */
    public List<PerroEntity> replacePerros(Long clienteId, List<PerroEntity> list) {
        
        ClienteEntity clienteEntity = clientePersistence.find(clienteId);
        int numPerros = clienteEntity.getPerros().size();
        for(int i=0; i<numPerros; i++)
        {
            PerroEntity perroDel = clienteEntity.getPerros().get(0);
            PerroEntity perroNuevoEntity = perroPersistence.find(perroDel.getId());
            clienteEntity.getPerros().remove(perroDel);
            perroNuevoEntity.setCliente(null);
        }
        for(PerroEntity perro : list)
        {
            PerroEntity perroNuevoEntity = perroPersistence.find(perro.getId());
            perroNuevoEntity.setCliente(clienteEntity);
            clienteEntity.getPerros().add(perroNuevoEntity);
        }
        return clientePersistence.find(clienteId).getPerros();
    }
    
    

    /**
     * Borrar el recorrido de un paseo
     *
     * @param perroId El paseo que se desea borrar del recorrido.
     */
    public void removePerro(Long perroId) {
        PerroEntity perroEntity = perroPersistence.find(perroId);
        
        ClienteEntity clienteEntity = clientePersistence.find(perroEntity.getCliente().getId());
        perroEntity.setCliente(null);
        clienteEntity.getPerros().remove(perroEntity);
    }
    
}
