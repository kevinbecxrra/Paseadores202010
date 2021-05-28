/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.persistence;

import co.edu.uniandes.csw.paseadores.entities.CalificacionEntity;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 *
 * @author Nicolas Urrego Sandoval <n.urrego at uniandes.edu.co>
 */
@Stateless
public class CalificacionPersistence {
    
    @PersistenceContext(unitName = "paseadoresPU")
    EntityManager em;
    
    public CalificacionEntity create(CalificacionEntity calificacion)  
    {
        em.persist(calificacion);
        return calificacion;
    }
    
    public CalificacionEntity find(Long idCalificacion)  
    {
        return em.find(CalificacionEntity.class, idCalificacion);
    }
    
    public CalificacionEntity update(CalificacionEntity calificacion)  
    {
        em.merge(calificacion);
        return calificacion;
    }
    
    public CalificacionEntity delete(Long idCalificacion)  
    {
        CalificacionEntity calificacion = em.find(CalificacionEntity.class, idCalificacion);
        em.remove(calificacion);
        return calificacion;
    }
}
