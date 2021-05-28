/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.persistence;

import co.edu.uniandes.csw.paseadores.entities.PagoClienteEntity;
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
public class PagoClientePersistence {
    
    @PersistenceContext(unitName = "paseadoresPU")
    EntityManager em;
    
    public PagoClienteEntity create(PagoClienteEntity pagoCliente)
    {
        em.persist(pagoCliente);
        return pagoCliente;
    }
    
    
    public PagoClienteEntity find(Long idPago)
    {
        return em.find(PagoClienteEntity.class, idPago);
    }
    
    public PagoClienteEntity update(PagoClienteEntity pagoCliente)
    {
        em.merge(pagoCliente);
        return pagoCliente;
    }
    
    public PagoClienteEntity delete(Long idPago)
    {
        PagoClienteEntity pagoCliente = em.find(PagoClienteEntity.class, idPago);
        em.remove(pagoCliente);
        return pagoCliente;
    }
    
    public PagoClienteEntity findByReferencia(String referencia)
    {
        TypedQuery query = em.createQuery("Select p From PagoClienteEntity p where p.referencia = :referencia", PagoClienteEntity.class);
        query = query.setParameter("referencia", referencia);
        List<PagoClienteEntity> pagosClientesMismaReferencia = query.getResultList();
        PagoClienteEntity pagoBuscado;
        if (pagosClientesMismaReferencia == null) {
            pagoBuscado = null;
        } else if (pagosClientesMismaReferencia.isEmpty()) {
            pagoBuscado = null;
        } else {
            pagoBuscado = pagosClientesMismaReferencia.get(0);
        }
        return pagoBuscado;
    }
    
    public List<PagoClienteEntity> findAll() 
    {
        Query q = em.createQuery("select p from PagoClienteEntity p");
        return q.getResultList();
    }
}
