/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.persistence;

import co.edu.uniandes.csw.paseadores.entities.PaseadorEntity;
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
public class PaseadorPersistence {
    
    @PersistenceContext(unitName = "paseadoresPU")
    private EntityManager em;
    
    public PaseadorEntity create(PaseadorEntity paseador){
        em.persist(paseador);
        return paseador;
    }
    
    public PaseadorEntity find(Long paseadorId) {
        return em.find(PaseadorEntity.class, paseadorId);
    }
    
    public List<PaseadorEntity> findAll() 
    {
        Query q = em.createQuery("select p from PaseadorEntity p");
        return q.getResultList();
    }
    
    public PaseadorEntity findByIdentificacion(String identificacion)
    {
        TypedQuery query = em.createQuery("Select p From PaseadorEntity p where p.identificacion = :identificacion", PaseadorEntity.class);
        query = query.setParameter("identificacion", identificacion);
        List<PaseadorEntity> paseadoresMismaIdentificacion = query.getResultList();
        PaseadorEntity paseadorBuscado;
        if (paseadoresMismaIdentificacion == null) {
            paseadorBuscado = null;
        } else if (paseadoresMismaIdentificacion.isEmpty()) {
            paseadorBuscado = null;
        } else {
            paseadorBuscado = paseadoresMismaIdentificacion.get(0);
        }
        return paseadorBuscado;
    }
    
    public PaseadorEntity findByCorreo(String correo)
    {
        TypedQuery query = em.createQuery("Select p From PaseadorEntity p where p.correo = :correo", PaseadorEntity.class);
        query = query.setParameter("correo", correo);
        List<PaseadorEntity> paseadoresMismoCorreo = query.getResultList();
        PaseadorEntity paseadorBuscado;
        if (paseadoresMismoCorreo == null) {
            paseadorBuscado = null;
        } else if (paseadoresMismoCorreo.isEmpty()) {
            paseadorBuscado = null;
        } else {
            paseadorBuscado = paseadoresMismoCorreo.get(0);
        }
        return paseadorBuscado;
    }
    
    public List<PaseadorEntity> findByCalificacionGlobalInRange(Double inf, Double sup)
    {
        TypedQuery query = em.createQuery("Select p From PaseadorEntity p where p.calificacionGlobal between :calificacionGlobal1 and :calificacionGlobal2", PaseadorEntity.class);
        query = query.setParameter("calificacionGlobal1", inf);
        query = query.setParameter("calificacionGlobal2", sup);
        return query.getResultList();
    }
    
    public PaseadorEntity update(PaseadorEntity paseador) {
        return em.merge(paseador);
    }
    
    public void delete(Long paseadorId)
    {
        PaseadorEntity paseador = em.find(PaseadorEntity.class, paseadorId);
        em.remove(paseador);
    }
}
