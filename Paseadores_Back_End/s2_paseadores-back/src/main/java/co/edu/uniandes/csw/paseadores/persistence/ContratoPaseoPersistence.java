/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.persistence;

import co.edu.uniandes.csw.paseadores.entities.ContratoPaseoEntity;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author julian Oliveros<je.oliverosf at uniandes.edu.co>
 */
@Stateless
public class ContratoPaseoPersistence {
    
     private static final Logger LOGGER = Logger.getLogger(ContratoPaseoPersistence.class.getName());
    @PersistenceContext(unitName = "paseadoresPU")
    private EntityManager em;
    
    public ContratoPaseoEntity create (ContratoPaseoEntity contratoPaseo)
    {
        em.persist(contratoPaseo);
        return contratoPaseo;
    }
        
    public ContratoPaseoEntity find(Long contratoPaseoId)  
    {
        LOGGER.log(Level.INFO, "--ENtro a peristencia de contrato paseo ");
        return em.find(ContratoPaseoEntity.class, contratoPaseoId);
    }
       
    public ContratoPaseoEntity update(ContratoPaseoEntity contratoPaseo)  
    {
        em.merge(contratoPaseo);
        return contratoPaseo;
    }
    
    public ContratoPaseoEntity delete(Long idContratoPaseo)  
    {
        ContratoPaseoEntity contratoPaseo = em.find(ContratoPaseoEntity.class, idContratoPaseo);
        em.remove(contratoPaseo);
        return contratoPaseo;
    }
    
        public List<ContratoPaseoEntity> findAll() 
    {
        Query q = em.createQuery("select p from ContratoPaseoEntity p");
        return q.getResultList();
    }
}
