/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.persistence;

import co.edu.uniandes.csw.paseadores.entities.ContratoHotelEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;

/**
 *
 * @author Edgar Camilo Diaz Suarez
 */
@Stateless
public class ContratoHotelPersistence {
    
    @PersistenceContext(unitName = "paseadoresPU")
    EntityManager em;
    
    public ContratoHotelEntity create(ContratoHotelEntity contratoHotel){
        
       em.persist(contratoHotel);
       return contratoHotel;
    }
    
    public ContratoHotelEntity find(Long contratoHotelId){
        
        return em.find(ContratoHotelEntity.class, contratoHotelId);
    }
    
     public List<ContratoHotelEntity> findAll() 
    {
        Query q = em.createQuery("select p from ContratoHotelEntity p");
        return q.getResultList();
    }
    
    public ContratoHotelEntity update(ContratoHotelEntity contratoHotel ){
        
        em.merge(contratoHotel);
        return contratoHotel;
        
    }
    
    public ContratoHotelEntity delete(Long idContratoHotel){
        
        ContratoHotelEntity contratoHotel = em.find(ContratoHotelEntity.class,idContratoHotel);
        em.remove(contratoHotel);
        return contratoHotel;
    }
}
