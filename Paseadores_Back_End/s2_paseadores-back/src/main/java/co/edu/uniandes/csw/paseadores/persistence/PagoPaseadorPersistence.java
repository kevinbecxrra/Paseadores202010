/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.persistence;

import co.edu.uniandes.csw.paseadores.entities.PagoPaseadorEntity;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Daniel Mateo Guatibonza Solano
 */
@Stateless
public class PagoPaseadorPersistence {
    @PersistenceContext(unitName = "paseadoresPU")
    private EntityManager em;
    
    public PagoPaseadorEntity create(PagoPaseadorEntity pagoPaseador){
        em.persist(pagoPaseador);
        return pagoPaseador;
    }
    
    public PagoPaseadorEntity find(Long pagoPaseadorId)
    {
        return em.find(PagoPaseadorEntity.class, pagoPaseadorId);
    }
    
    public List<PagoPaseadorEntity> findAll() 
    {
        Query q = em.createQuery("select p from PagoPaseadorEntity p");
        return q.getResultList();
    }
    
    public PagoPaseadorEntity findByReferencia(String referencia)
    {
        TypedQuery query = em.createQuery("Select p From PagoPaseadorEntity p where p.referencia = :referencia", PagoPaseadorEntity.class);
        query = query.setParameter("referencia", referencia);
        List<PagoPaseadorEntity> pagosPaseadorMismaReferencia = query.getResultList();
        PagoPaseadorEntity pagoPaseadorBuscado;
        if (pagosPaseadorMismaReferencia == null) {
            pagoPaseadorBuscado = null;
        } else if (pagosPaseadorMismaReferencia.isEmpty()) {
            pagoPaseadorBuscado = null;
        } else {
            pagoPaseadorBuscado = pagosPaseadorMismaReferencia.get(0);
        }
        return pagoPaseadorBuscado;
    }
    
    
    public PagoPaseadorEntity findByfechaLimite(Date pFechaLimite)
    {
        TypedQuery query = em.createQuery("Select p From PagoPaseadorEntity p where p.fechaLimite = :fechaLimite", PagoPaseadorEntity.class);
        query = query.setParameter("fechaLimite", pFechaLimite);
        List<PagoPaseadorEntity> mismaFechaLimite = query.getResultList();
        PagoPaseadorEntity pagoPaseadorBuscado;
        if (mismaFechaLimite == null) {
            pagoPaseadorBuscado = null;
        } else if (mismaFechaLimite.isEmpty()) {
            pagoPaseadorBuscado = null;
        } else {
            pagoPaseadorBuscado = mismaFechaLimite.get(0);
        }
        return pagoPaseadorBuscado;
    }
    
    public PagoPaseadorEntity update(PagoPaseadorEntity pagoPaseador)
    {
        return em.merge(pagoPaseador);
    }
    
    public void delete(Long pagoPaseadorId)
    {
        PagoPaseadorEntity pagoPaseador = em.find(PagoPaseadorEntity.class, pagoPaseadorId);
        em.remove(pagoPaseador);
    }
}
