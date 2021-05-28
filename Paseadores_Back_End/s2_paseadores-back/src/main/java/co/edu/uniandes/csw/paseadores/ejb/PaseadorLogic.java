/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.ejb;

import co.edu.uniandes.csw.paseadores.entities.PaseadorEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.PaseadorPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Alvaro Plata
 */

@Stateless
public class PaseadorLogic {
    
    @Inject
    private PaseadorPersistence paseadorPersistence;
    
    public PaseadorEntity createPaseador(PaseadorEntity paseadorEntity) throws BusinessLogicException
    {
        if(paseadorPersistence.findByIdentificacion(paseadorEntity.getIdentificacion()) != null) 
        {
            throw new BusinessLogicException("Ya existe un paseador con la identificación: " + paseadorEntity.getIdentificacion());
        }
        if(paseadorPersistence.findByCorreo(paseadorEntity.getCorreo()) != null)
        {
            throw new BusinessLogicException("Ya existe un paseador con el correo: " + paseadorEntity.getCorreo());
        }
        if(paseadorEntity.getCalificacionGlobal() < 0 || paseadorEntity.getCalificacionGlobal() > 5)
        {
            throw new BusinessLogicException("La calificación global de un paseador debe estar entre 0 y 5");
        }
        return paseadorPersistence.create(paseadorEntity);
    }
    
    public PaseadorEntity getPaseador(Long paseadorId)
    {
        return paseadorPersistence.find(paseadorId);
    }
    
    public List<PaseadorEntity> getPaseadores()
    {
        return paseadorPersistence.findAll();
    }
    
    public PaseadorEntity getPaseadorByIdentificacion(String id)
    {
        return paseadorPersistence.findByIdentificacion(id);
    }
    
    public PaseadorEntity getPaseadorByCorreo(String correo)
    {
        return paseadorPersistence.findByCorreo(correo);
    }
    
    public List<PaseadorEntity> getPaseadorByCalificacionGlobalInRange(Double inf, Double sup)
    {
        return paseadorPersistence.findByCalificacionGlobalInRange(inf, sup);
    }
    
    /**
     *
     * @param paseadorEntity
     * @return
     * @throws BusinessLogicException
     */
    public PaseadorEntity updatePaseador(PaseadorEntity paseadorEntity)throws BusinessLogicException
    {
        if(paseadorPersistence.findByIdentificacion(paseadorEntity.getIdentificacion()) != null &&
                !paseadorPersistence.findByIdentificacion(paseadorEntity.getIdentificacion()).getId().equals(paseadorEntity.getId())) 
        {
            throw new BusinessLogicException("Ya existe un paseador con la identificación: " + paseadorEntity.getIdentificacion());
        }
        if(paseadorPersistence.findByCorreo(paseadorEntity.getCorreo()) != null &&
                !paseadorPersistence.findByCorreo(paseadorEntity.getCorreo()).getId().equals(paseadorEntity.getId()))
        {
            throw new BusinessLogicException("Ya existe un paseador con el correo: " + paseadorEntity.getCorreo());
        }
        if(paseadorEntity.getCalificacionGlobal() < 0 || paseadorEntity.getCalificacionGlobal() > 5)
        {
            throw new BusinessLogicException("La calificación global de un paseador debe estar entre 0 y 5");
        }      
        return paseadorPersistence.update(paseadorEntity);
    }        
    
    public void deletePaseador(Long paseadorId)
    {
        paseadorPersistence.delete(paseadorId);
    }
}
