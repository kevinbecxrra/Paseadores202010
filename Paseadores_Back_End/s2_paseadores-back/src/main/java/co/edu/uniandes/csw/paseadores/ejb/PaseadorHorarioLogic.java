/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.ejb;

import co.edu.uniandes.csw.paseadores.entities.HorarioEntity;
import co.edu.uniandes.csw.paseadores.entities.PaseadorEntity;
import co.edu.uniandes.csw.paseadores.persistence.HorarioPersistence;
import co.edu.uniandes.csw.paseadores.persistence.PaseadorPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Alvaro Plata
 */
@Stateless
public class PaseadorHorarioLogic {
    
    @Inject
    private PaseadorPersistence paseadorPersistence;
    
    @Inject
    private HorarioPersistence horarioPersistence;
    
    public HorarioEntity addHorario(Long idHor, Long paseadorId)
    {
        HorarioEntity horarioEntity = horarioPersistence.find(idHor);
        PaseadorEntity paseadorEntity = paseadorPersistence.find(paseadorId);
        
        paseadorEntity.getHorariosDisponibles().add(horarioEntity);
        return horarioPersistence.find(idHor);
    }
    
    public List<HorarioEntity> getHorarios(Long paseadorId)
    {
        return paseadorPersistence.find(paseadorId).getHorariosDisponibles();
    }
    
    public HorarioEntity getHorario(Long paseadorId, Long horarioId)
    {
        List<HorarioEntity> listaHorarios = paseadorPersistence.find(paseadorId).getHorariosDisponibles();
        HorarioEntity horario = horarioPersistence.find(horarioId);
        int index = listaHorarios.indexOf(horario);
        if(index>=0)
            return listaHorarios.get(index);
        return null;
    }
    
    public List<HorarioEntity> replaceHorarios(Long paseadorId, List<HorarioEntity> nuevaLista)
    {
        PaseadorEntity paseador = paseadorPersistence.find(paseadorId);
        paseador.setHorariosDisponibles(nuevaLista);
        return paseadorPersistence.find(paseadorId).getHorariosDisponibles();
    }
    
    public void removeHorario(Long paseadorId, Long horarioId)
    {
        HorarioEntity horario = horarioPersistence.find(horarioId);
        PaseadorEntity paseador = paseadorPersistence.find(paseadorId);
        
        paseador.getHorariosDisponibles().remove(horario);
    }
    
}
