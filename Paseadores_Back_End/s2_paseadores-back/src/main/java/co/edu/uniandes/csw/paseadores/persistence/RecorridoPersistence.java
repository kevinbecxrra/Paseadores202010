/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.persistence;

import co.edu.uniandes.csw.paseadores.entities.RecorridoEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Nicolas Urrego Sandoval <n.urrego at uniandes.edu.co>
 */
@Stateless
public class RecorridoPersistence {
    
    @PersistenceContext(unitName = "paseadoresPU")
    EntityManager em;
    
    public RecorridoEntity create(RecorridoEntity recorrido)  
    {
        em.persist(recorrido);
        return recorrido;
    }
    
    public RecorridoEntity find(Long idRecorrido)  
    {
        return em.find(RecorridoEntity.class, idRecorrido);
    }
    
      public List<RecorridoEntity> findByCalificacionGlobalInRange(Double inf, Double sup)
    {
        TypedQuery query = em.createQuery("Select p From RecorridoEntity p where p.calificacionGlobal between :calificacionGlobal1 and :calificacionGlobal2", RecorridoEntity.class);
        query = query.setParameter("calificacionGlobal1", inf);
        query = query.setParameter("calificacionGlobal2", sup);
        return query.getResultList();
    }
    
    public List<RecorridoEntity> findAll() 
    {
        Query q = em.createQuery("select p from RecorridoEntity p");
        return q.getResultList();
    }
    
    public RecorridoEntity update(RecorridoEntity recorrido)  
    {
        em.merge(recorrido);
        return recorrido;
    }
    
    public RecorridoEntity delete(Long idRecorrido)  
    {
        RecorridoEntity recorrido = em.find(RecorridoEntity.class, idRecorrido);
        em.remove(recorrido);
        return recorrido;
    }
}
