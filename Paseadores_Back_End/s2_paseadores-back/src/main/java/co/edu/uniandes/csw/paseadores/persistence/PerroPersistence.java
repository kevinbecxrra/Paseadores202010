/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.persistence;

import co.edu.uniandes.csw.paseadores.ejb.PerroLogic;
import co.edu.uniandes.csw.paseadores.entities.PerroEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author julian Oliveros<je.oliverosf at uniandes.edu.co>
 *//**
 *
 * @author julian Oliveros<je.oliverosf at uniandes.edu.co>
 */
@Stateless
public class PerroPersistence 
{
    private static final Logger LOGGER = Logger.getLogger(PerroPersistence.class.getName());

    @PersistenceContext(unitName = "paseadoresPU")
    private EntityManager em;
   
    public PerroEntity create(PerroEntity est)
    {
        em.persist(est);
        return est;
    }
    
    public List<PerroEntity> findAll() 
    {
        Query q = em.createQuery("select p from PerroEntity p");
        return q.getResultList();
    }
    
    public PerroEntity find(Long perroID)  
    {
        LOGGER.log(Level.INFO, "perro persistence  find : input: perrosId {0} , contratoPaseoId");

        return em.find(PerroEntity.class, perroID);
    }
       
    public PerroEntity update(PerroEntity perro)  
    {
        em.merge(perro);
        return perro;
    }
    
    public void delete(Long idPerro)  
    {
        PerroEntity perro = em.find(PerroEntity.class, idPerro);
        em.remove(perro);
    }
    
    public PerroEntity findByIdPerro(String pIdPerro)
    {
        TypedQuery query = em.createQuery("Select p From PerroEntity p where p.idPerro = :idPerro", PerroEntity.class);
        query = query.setParameter("idPerro", pIdPerro);
        List<PerroEntity> mismoIdPerro = query.getResultList();
        PerroEntity result;

        if(mismoIdPerro.isEmpty())
        {
            result=null;
        }
        else 
        {
            result = mismoIdPerro.get(0);
        }
        
        return result;
    }
        
}
