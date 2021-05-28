/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.ejb;

import co.edu.uniandes.csw.paseadores.entities.PaseoEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.PaseoPersistence;
import java.util.Date;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Daniel Mateo Guatibonza Solano
 */
@Stateless
public class PaseoLogic {
    
    @Inject
    private PaseoPersistence paseoPersistence;
    
    /**
     * 
     * @param paseoEntity
     * @return
     * @throws BusinessLogicException 
     */
    public PaseoEntity createPaseo(PaseoEntity paseoEntity) throws BusinessLogicException
    {
        if(paseoEntity.getCupoMaximo() < 0)
        {
            throw new BusinessLogicException("El cupo máximo de un paseo debe ser un entero positivo.");
        }
        if(paseoEntity.getCosto() < 0)
        {
            throw new BusinessLogicException("El costo de un paseo debe ser positivo.");
        }
        if(paseoEntity.getHorario1() == null && paseoEntity.getHorario2() != null)
        {
            throw new BusinessLogicException("Solo puede existir un horario2 si existe un horario1.");
        }
        paseoEntity = paseoPersistence.create(paseoEntity);
        return paseoEntity;
    }
    
    /**
     * 
     * @param paseoId
     * @return
     */
    public PaseoEntity getPaseo(Long paseoId)
    {
        return paseoPersistence.find(paseoId);
    }
    
    /**
     * 
     * @return
     */
    public List<PaseoEntity> getAllPaseos()
    {
        return paseoPersistence.findAll();
    }
    
    /**
     * 
     * @param low
     * @param high
     * @return 
     */
    public List<PaseoEntity> getPaseosByCostoInRange(Double low, Double high)
    {
        return paseoPersistence.findByCostoInRange(low, high);
    }
    
    /**
     * 
     * @param low
     * @param high
     * @return 
     */
    public List<PaseoEntity> getPaseosByHoraInicioInRange(Date low, Date high)
    {
        return paseoPersistence.findByHoraInicioInRange(low, high);
    }
    
    /**
     * 
     * @param nuevoPaseoEntity
     * @return
     * @throws BusinessLogicException 
     */
    public PaseoEntity updatePaseo(PaseoEntity nuevoPaseoEntity) throws BusinessLogicException
    {
        if(nuevoPaseoEntity.getCupoMaximo() < 0)
        {
            throw new BusinessLogicException("El cupo máximo de un paseo debe ser un entero positivo.");
        }
        if(nuevoPaseoEntity.getCosto() < 0)
        {
            throw new BusinessLogicException("El costo de un paseo debe ser positivo.");
        }
        if(nuevoPaseoEntity.getHorario1() == null && nuevoPaseoEntity.getHorario2() != null)
        {
            throw new BusinessLogicException("Solo puede existir un horario2 si existe un horario1.");
        }
        nuevoPaseoEntity = paseoPersistence.create(nuevoPaseoEntity);
        return nuevoPaseoEntity;
    }
    
    /**
     * 
     * @param paseoId 
     */
    public void deletePaseo(Long paseoId)
    {
        paseoPersistence.delete(paseoId);
    }
}
