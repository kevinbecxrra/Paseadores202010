/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.persistence;

import co.edu.uniandes.csw.paseadores.entities.HoraHotelEntity;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.Query;
import javax.persistence.TypedQuery;

/**
 *
 * @author Edgar Camilo Diaz Suarez
 */
@Stateless
public class HoraHotelPersistence {
    
    @PersistenceContext(unitName = "paseadoresPU")
    EntityManager em;
    
    public HoraHotelEntity create(HoraHotelEntity horaHotel){
        
        em.persist(horaHotel);
        return horaHotel ;
    }
    
    public HoraHotelEntity find(Long horaHotelId){
        return em.find(HoraHotelEntity.class, horaHotelId );
    }
    
      public List<HoraHotelEntity> findAll() 
    {
        Query q = em.createQuery("select p from HoraHotelEntity p");
        return q.getResultList();
    }
    
    public List<HoraHotelEntity> findByDiaInRange(Date inf, Date sup)
    {
        TypedQuery query = em.createQuery("Select p From HoraHotelEntity p where p.dia between :dia1 and :dia2", HoraHotelEntity.class);
        query = query.setParameter("dia1", inf);
        query = query.setParameter("dia2", sup);
        return query.getResultList();
        
    }
    
    public List<HoraHotelEntity> findByCostoBaseInRange(Double inf, Double sup)
    {
        TypedQuery query = em.createQuery("Select p From HoraHotelEntity p where p.costoBase between :costoBase1 and :costoBase2", HoraHotelEntity.class);
        query = query.setParameter("costoBase1", inf);
        query = query.setParameter("costoBase2", sup);
        return query.getResultList();
    }
    public HoraHotelEntity update(HoraHotelEntity horaHotel){
         em.merge(horaHotel);
        return horaHotel;
    }
    
    public HoraHotelEntity delete(Long idHoraHotel){
        
       HoraHotelEntity horaHotel = em.find(HoraHotelEntity.class,idHoraHotel);
       em.remove(horaHotel);
       return horaHotel;
    }
}
