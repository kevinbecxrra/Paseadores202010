/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.persistence;

import co.edu.uniandes.csw.paseadores.entities.ClienteEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Alvaro Plata
 */

@Stateless
public class ClientePersistence {
    
    @PersistenceContext(unitName = "paseadoresPU")
    protected EntityManager em;
    
    public ClienteEntity create(ClienteEntity cliente)
    { 
        em.persist(cliente);
        return cliente;
    }
    
    public ClienteEntity find(Long idCliente)
    {
        return em.find(ClienteEntity.class, idCliente);
    }
    
    public ClienteEntity update(ClienteEntity cliente)
    {
        em.merge(cliente);
        return cliente;
    }
    
    public ClienteEntity delete(Long idCliente)
    {
        ClienteEntity cliente = em.find(ClienteEntity.class, idCliente);
        em.remove(cliente);
        return cliente;
    }
    
    public ClienteEntity findByIdentificacion(String identificacion)
    {
        TypedQuery query = em.createQuery("Select p From ClienteEntity p where p.identificacion = :identificacion", ClienteEntity.class);
        query = query.setParameter("identificacion", identificacion);
        List<ClienteEntity> clientesMismaIdentificacion = query.getResultList();
        ClienteEntity clienteBuscado;
        if (clientesMismaIdentificacion == null) {
            clienteBuscado = null;
        } else if (clientesMismaIdentificacion.isEmpty()) {
            clienteBuscado = null;
        } else {
            clienteBuscado = clientesMismaIdentificacion.get(0);
        }
        return clienteBuscado;
    }
    
    public ClienteEntity findByCorreo(String correo)
    {
        TypedQuery query = em.createQuery("Select p From ClienteEntity p where p.correo = :correo", ClienteEntity.class);
        query = query.setParameter("correo", correo);
        List<ClienteEntity> clientesMismoCorreo = query.getResultList();
        ClienteEntity clienteBuscado;
        if (clientesMismoCorreo == null) {
            clienteBuscado = null;
        } else if (clientesMismoCorreo.isEmpty()) {
            clienteBuscado = null;
        } else {
            clienteBuscado = clientesMismoCorreo.get(0);
        }
        return clienteBuscado;
    }
    
    public List<ClienteEntity> findAll() 
    {
        Query q = em.createQuery("select p from ClienteEntity p");
        return q.getResultList();
    }
}
