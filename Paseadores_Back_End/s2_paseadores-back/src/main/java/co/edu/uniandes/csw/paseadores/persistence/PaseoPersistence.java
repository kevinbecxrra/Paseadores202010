/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.persistence;

import co.edu.uniandes.csw.paseadores.entities.PaseoEntity;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Kevin Camilo Becerra Walteros
 */
@Stateless
public class PaseoPersistence {
    
    @PersistenceContext(unitName = "paseadoresPU")
    EntityManager em;
    
    public PaseoEntity create(PaseoEntity pas){
        em.persist(pas);   
        return pas;
    }
    
    public PaseoEntity find(Long idPas){
        return em.find(PaseoEntity.class, idPas);
    }
    
    public List<PaseoEntity> findAll() 
    {
        Query q = em.createQuery("select p from PaseoEntity p");
        return q.getResultList();
    }
    
    public List<PaseoEntity> findByCostoInRange(Double inf, Double sup)
    {
        TypedQuery query = em.createQuery("Select p From PaseoEntity p where p.costo between :costo1 and :costo2", PaseoEntity.class);
        query = query.setParameter("costo1", inf);
        query = query.setParameter("costo2", sup);
        return query.getResultList();
    }
    
     public List<PaseoEntity> findByHoraInicioInRange(Date inf, Date sup)
    {
        TypedQuery query = em.createQuery("Select p From PaseoEntity p where p.horaInicio between :horaInicio1 and :horaInicio2", PaseoEntity.class);
        query = query.setParameter("horaInicio1", inf);
        query = query.setParameter("horaInicio2", sup);
        return query.getResultList();
    }
    
    
    public PaseoEntity update(PaseoEntity pas){
        em.merge(pas);
        return pas;
    }
    
    public PaseoEntity delete(Long idPas){
        PaseoEntity paseo = em.find(PaseoEntity.class, idPas);
        em.remove(paseo);
        return paseo;
    }
    
}
