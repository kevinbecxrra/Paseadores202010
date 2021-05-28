/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.persistence;

import co.edu.uniandes.csw.paseadores.entities.PuntoEntity;
import java.util.List;
import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.TypedQuery;

/**
 *
 * @author Nicolas Urrego Sandoval <n.urrego at uniandes.edu.co>
 */
@Stateless
public class PuntoPersistence {
    
    @PersistenceContext(unitName = "paseadoresPU")
    EntityManager em;
    
    public PuntoEntity create(PuntoEntity punto)  
    {
        em.persist(punto);
        return punto;
    }
    
    public PuntoEntity find(Long idPunto)  
    {
        return em.find(PuntoEntity.class, idPunto);
    }
    
    public PuntoEntity findByPosicion(Integer posicion){
        
        TypedQuery query = em.createQuery("Select p From PuntoEntity p where p.posicion = :posicion", PuntoEntity.class);
        query = query.setParameter("posicion", posicion);
        List<PuntoEntity> puntoMismaPosicion = query.getResultList();
        PuntoEntity puntoBuscado;
        if (puntoMismaPosicion == null) {
            puntoBuscado = null;
        } else if (puntoMismaPosicion.isEmpty()) {
            puntoBuscado = null;
        } else {
            puntoBuscado = puntoMismaPosicion.get(0);
        }
        return puntoBuscado;
 
    }
    
    public PuntoEntity update(PuntoEntity punto)  
    {
        em.merge(punto);
        return punto;
    }
    
    public PuntoEntity delete(Long idPunto)  
    {
        PuntoEntity punto = em.find(PuntoEntity.class, idPunto);
        em.remove(punto);
        return punto;
    }
}
