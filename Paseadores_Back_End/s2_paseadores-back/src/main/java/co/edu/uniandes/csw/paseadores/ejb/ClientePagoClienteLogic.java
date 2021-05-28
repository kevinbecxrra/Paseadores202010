/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.ejb;

import co.edu.uniandes.csw.paseadores.entities.ClienteEntity;
import co.edu.uniandes.csw.paseadores.entities.PagoClienteEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.ClientePersistence;
import co.edu.uniandes.csw.paseadores.persistence.PagoClientePersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;



/**
 *
 * @author julian Oliveros<je.oliverosf at uniandes.edu.co>
 */
@Stateless
public class ClientePagoClienteLogic {
    
    @Inject
    private PagoClientePersistence pagoClientePersistence;
    
    @Inject
    private ClientePersistence clientePersistence;
    
    /**
     * Agregar un pagoCliente a un cliente
     *
     * @param pagoClienteId El id paseo a guardar
     * @param clienteId El id del recorrido al cual se le va a guardar el paseo.
     * @return El paseo que fue agregado al recorrido.
     */
    public ClienteEntity addCliente(Long clienteId, Long pagoClienteId) {
        
        ClienteEntity clienteEntity = clientePersistence.find(clienteId);
        
        PagoClienteEntity pagoClienteEntity = pagoClientePersistence.find(pagoClienteId);
        
        pagoClienteEntity.setCliente(clienteEntity);
        return clientePersistence.find(clienteId);
    }
    
    /**
     * Agregar un CLEINTE a un PAGOcliente
     *
     * @param pagoClienteId El id pagocliente a guardar
     * @param clienteId El id del recorrido al cual se le va a guardar el paseo.
     * @return El paseo que fue agregado al recorrido.
     */
    public PagoClienteEntity addPagoCliente(Long clienteId, Long pagoClienteId) {
        
        ClienteEntity clienteEntity = clientePersistence.find(clienteId);
        
        PagoClienteEntity pagoClienteEntity = pagoClientePersistence.find(pagoClienteId);
        
        pagoClienteEntity.setCliente(clienteEntity);
        return pagoClientePersistence.find(pagoClienteId);
    }

    /**
     *
     * Obtener un paseo por medio de su id y el de su recorrido.
     *
     * @param pagoClienteId id del paseo a ser buscado.
     * @return el recorrido solicitada por medio de su id.
     */
    public ClienteEntity getCliente(Long pagoClienteId) {
        return pagoClientePersistence.find(pagoClienteId).getCliente();
    }

    public List<PagoClienteEntity> getPagosCliente(Long clienteId)
    {
        return clientePersistence.find(clienteId).getPagos();
    }
    
    /**
     * Retorna un pagocliente asociado a una cliente
     *
     * @param clienteId El id de la cliente a buscar.
     * @param pagoClienteId El id del pagocliente a buscar
     * @return El pago encontrado dentro de la cliente.
     * @throws BusinessLogicException Si el pago no se encuentra en la
     * cliente
     */
    public PagoClienteEntity getPagoCliente(Long clienteId, Long pagoClienteId) throws BusinessLogicException {
        
        List<PagoClienteEntity> pagos = clientePersistence.find(clienteId).getPagos();
        PagoClienteEntity pagoClienteEntity = pagoClientePersistence.find(pagoClienteId);
        int index = pagos.indexOf(pagoClienteEntity);
        
        if (index >= 0) {
            return pagos.get(index);
        }
        throw new BusinessLogicException("El pagoCliente no está asociado al cliente");
    }   
    
    /**
     * Remplazar cliente de un pago
     *
     * @param pagoClienteId el id del pago que se quiere actualizar.
     * @param clienteId El id del nuebo cliente asociado al paseo.
     * @return el nuevo cliente asociado.
     */
    public ClienteEntity replaceCliente(Long pagoClienteId, Long clienteId) {
        ClienteEntity clienteEntity = clientePersistence.find(clienteId);
        PagoClienteEntity pagoClienteEntity = pagoClientePersistence.find(pagoClienteId);
        pagoClienteEntity.setCliente(clienteEntity);
        return clientePersistence.find(clienteId);
    }
      
    /**
     * Remplazar pago cliente de un cliente
     
     * @param clienteId El id del nuevo psgo cliente asociado al cliente.
     * @param list
     * @return el nuevo cliente asociado.
     */
    public List<PagoClienteEntity> replacePagosCliente(Long clienteId, List<PagoClienteEntity> list) {
        
        ClienteEntity clienteEntity = clientePersistence.find(clienteId);
        int numPagos = clienteEntity.getPagos().size();
        for(int i=0; i<numPagos; i++)
        {
            PagoClienteEntity pagoDel = clienteEntity.getPagos().get(0);
            PagoClienteEntity pagoNuevoEntity = pagoClientePersistence.find(pagoDel.getId());
            clienteEntity.getPagos().remove(pagoDel);
            pagoNuevoEntity.setCliente(null);
        }
        for(PagoClienteEntity pago : list)
        {
            PagoClienteEntity pagoNuevoEntity = pagoClientePersistence.find(pago.getId());
            pagoNuevoEntity.setCliente(clienteEntity);
            clienteEntity.getPagos().add(pagoNuevoEntity);
        }
        return clientePersistence.find(clienteId).getPagos();
    }

    

    /**
     * Borrar el recorrido de un paseo
     *
     * @param pagoClienteId El paseo que se desea borrar del recorrido.
     */
    public void removePagoCliente(Long pagoClienteId) {
        PagoClienteEntity pagoClienteEntity = pagoClientePersistence.find(pagoClienteId);
        ClienteEntity clienteEntity = clientePersistence.find(pagoClienteEntity.getCliente().getId());
        pagoClienteEntity.setCliente(null);
        clienteEntity.getPagos().remove(pagoClienteEntity);
    }
    
}
