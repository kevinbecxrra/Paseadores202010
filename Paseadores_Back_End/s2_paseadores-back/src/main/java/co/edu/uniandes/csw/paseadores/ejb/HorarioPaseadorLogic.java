/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.ejb;

import co.edu.uniandes.csw.paseadores.entities.HorarioEntity;
import co.edu.uniandes.csw.paseadores.entities.PaseadorEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.HorarioPersistence;
import co.edu.uniandes.csw.paseadores.persistence.PaseadorPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Edgar Camilo Diaz Suarez
 */
@Stateless
public class HorarioPaseadorLogic {
    
    @Inject
    private PaseadorPersistence paseadorPersistence;

    @Inject
    private HorarioPersistence horarioPersistence;
    
    
    public PaseadorEntity addPaseador(Long horarioId, Long paseadorId) {
        HorarioEntity horarioEntity = horarioPersistence.find(horarioId);
        PaseadorEntity paseadorEntity = paseadorPersistence.find(paseadorId);
        paseadorEntity.getHorariosDisponibles().add(horarioEntity);
        return paseadorPersistence.find(paseadorId);
    }
    
     public List<PaseadorEntity> getPaseadores(Long horarioId) {
        return horarioPersistence.find(horarioId).getPaseador();
    }
    
    public PaseadorEntity getPaseador(Long paseadorId, Long horarioId) throws BusinessLogicException {
        List<PaseadorEntity> paseador = horarioPersistence.find(horarioId).getPaseador();
        PaseadorEntity paseadorEntity = paseadorPersistence.find(paseadorId);
        int index = paseador.indexOf(paseadorEntity);
        if (index >= 0) {
            return paseador.get(index);
        }
        throw new BusinessLogicException("El paseador no está asociado al horario");
    } 

    public List<PaseadorEntity> replacePaseador(Long horarioId, List<PaseadorEntity> list) {
   
       
        HorarioEntity horarioEntity = horarioPersistence.find(horarioId);
        horarioEntity.setPaseador(list);
        return horarioPersistence.find(horarioId).getPaseador();
        
       /**
        HorarioEntity horarioEntity = horarioPersistence.find(horarioId); 
        List<PaseadorEntity> paseadorList = paseadorPersistence.findAll();
        for (PaseadorEntity paseador : paseadorList) {
            if (list.contains(paseador)) {
                if (!paseador.getHorariosDisponibles().contains(horarioEntity)) {
                    paseador.getHorariosDisponibles().add(horarioEntity);
                }
            } else {
                paseador.getHorariosDisponibles().remove(horarioEntity);
            }
        }
        horarioEntity.setPaseador(list);
        return horarioEntity.getPaseador();
        * */
    }
    
    public void removePaseador(Long horarioId, Long paseadorId) {
        PaseadorEntity paseadorEntity = paseadorPersistence.find(paseadorId);
        HorarioEntity horarioEntity = horarioPersistence.find(horarioId);
      paseadorEntity.getHorariosDisponibles().remove(horarioEntity);
      }
    

}

