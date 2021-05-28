/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.persistence;

import co.edu.uniandes.csw.paseadores.entities.HorarioEntity;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Kevin Camilo Becerra Walteros
 */
@Stateless
public class HorarioPersistence {
    
    @PersistenceContext(unitName = "paseadoresPU")
    EntityManager em;
    
    public HorarioEntity create(HorarioEntity hor){
    em.persist(hor);   
    return hor;
    }
    
    public HorarioEntity find(Long idHor){
        return em.find(HorarioEntity.class, idHor);
    }
    
    public List<HorarioEntity> findByDiaInRange(Date inf, Date sup)
    {
        TypedQuery query = em.createQuery("Select p From HorarioEntity p where p.dia between :dia1 and :dia2", HorarioEntity.class);
        query = query.setParameter("dia1", inf);
        query = query.setParameter("dia2", sup);
        return query.getResultList();
    }
    
    public HorarioEntity update(HorarioEntity hor){
        em.merge(hor);
        return hor;
    }
    
    public HorarioEntity delete(Long idHor){
        HorarioEntity horario = em.find(HorarioEntity.class, idHor);
        em.remove(horario);
        return horario;
    }
    
}
