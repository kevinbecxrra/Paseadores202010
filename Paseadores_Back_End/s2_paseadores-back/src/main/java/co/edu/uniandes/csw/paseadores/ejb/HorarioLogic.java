/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.ejb;

import co.edu.uniandes.csw.paseadores.entities.HorarioEntity;
import co.edu.uniandes.csw.paseadores.entities.PaseoEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.HorarioPersistence;
import co.edu.uniandes.csw.paseadores.persistence.PaseoPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Edgar Camilo Diaz Suarez
 */
@Stateless
public class HorarioLogic {
    
    @Inject
    private HorarioPersistence horarioPersistence;
    
    @Inject
    private PaseoPersistence paseoPersistence;
    
      
    public HorarioEntity createHorario(HorarioEntity horarioEntity) {
        return horarioPersistence.create(horarioEntity);
    }
    
    public HorarioEntity getHorario(Long horarioId) {
    return horarioPersistence.find(horarioId);
    }
    
    public HorarioEntity updateHorario(HorarioEntity horarioEntity) {
        return horarioPersistence.update(horarioEntity);
    }
    
    public void deleteHorario(Long horarioId)  {
        horarioPersistence.delete(horarioId);
    }   
    
    public HorarioEntity addPaseo1(Long horarioId, Long paseo1Id) throws BusinessLogicException
    {
        HorarioEntity horario = horarioPersistence.find(horarioId);
        PaseoEntity paseo1 = paseoPersistence.find(paseo1Id);
        if(horario == null)
            throw new BusinessLogicException("El horario no existe");
        else if(paseo1 == null)
            throw new BusinessLogicException("El paseo no existe");
        else
            horario.setPaseo1(paseo1);
        return horarioPersistence.find(horarioId);
        
    }
    
    public PaseoEntity getPaseo1(Long horarioId) 
    {
        HorarioEntity horario = horarioPersistence.find(horarioId); 
        return horario.getPaseo1();
    }
    
    public HorarioEntity updatePaseo1(Long horarioId,Long paseo1Id) throws BusinessLogicException
    {
        HorarioEntity horarioBusqueda = horarioPersistence.find(horarioId);
        PaseoEntity paseo1Busq = paseoPersistence.find(paseo1Id);
        if(horarioBusqueda == null)
            throw new BusinessLogicException("El horario buscado no se encontro");
        else if(paseo1Busq == null)
            throw new BusinessLogicException("El Paseo 1 no existe");
        else
            horarioBusqueda.setPaseo1(paseo1Busq);
        return horarioPersistence.find(horarioId);
    }
    
    public HorarioEntity addPaseo2(Long horarioId, Long paseo2Id) throws BusinessLogicException
    {
        HorarioEntity horario = horarioPersistence.find(horarioId);
        PaseoEntity paseo2 = paseoPersistence.find(paseo2Id);
        if(horario == null)
            throw new BusinessLogicException("El horario que se busco no existe");
        else if(paseo2 == null)
            throw new BusinessLogicException("El paseo 2  no existe");
        else
            horario.setPaseo2(paseo2);
        return horarioPersistence.find(horarioId);
    }
    
    public void removePaseo1(Long horarioId) throws BusinessLogicException
    {
        HorarioEntity horario = horarioPersistence.find(horarioId);
        if(horario == null)
            throw new BusinessLogicException("El horario solicitado no fue encontrado porque no existe");
        else
            horario.setPaseo1(null);
    }
    
    public PaseoEntity getPaseo2(Long horarioId) throws BusinessLogicException
    {
        HorarioEntity horario = horarioPersistence.find(horarioId);
        if(horario == null)
            throw new BusinessLogicException("No existe el horario buscado con el horarioId");
        return horario.getPaseo2();
    }
    
    public HorarioEntity updatePaseo2( Long horarioId,Long paseo2Id) throws BusinessLogicException
    {
        HorarioEntity horarioBusqueda = horarioPersistence.find(horarioId);
        PaseoEntity paseo2Busq = paseoPersistence.find(paseo2Id);
        if(horarioBusqueda == null)
            throw new BusinessLogicException("El horario no se encontro con el id");
        else if(paseo2Busq == null)
            throw new BusinessLogicException("El Paseo 2 no existe");
        else
            horarioBusqueda.setPaseo2(paseo2Busq);
        return horarioPersistence.find(horarioId);
    }
    
    public void removePaseo2(Long horarioId) throws BusinessLogicException
    {
        HorarioEntity horario = horarioPersistence.find(horarioId);
        if(horario == null)
            throw new BusinessLogicException("No se puede eliminar el paseo porque no existe el horario solicitado");
        else
            horario.setPaseo2(null);
    }
}
