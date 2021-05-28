/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.ejb;

import co.edu.uniandes.csw.paseadores.entities.ContratoHotelEntity;
import co.edu.uniandes.csw.paseadores.entities.HoraHotelEntity;
import co.edu.uniandes.csw.paseadores.persistence.ContratoHotelPersistence;
import co.edu.uniandes.csw.paseadores.persistence.HoraHotelPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Alvaro Plata
 */

@Stateless
public class ContratoHotelHoraHotelLogic {
    
    @Inject
    private ContratoHotelPersistence contratoHotelPersistence;
    
    @Inject
    private HoraHotelPersistence horaHotelPersistence;
    
    /**
     *
     * @param idHora
     * @param contratoId
     * @return
     */
    public HoraHotelEntity addHoraHotel(Long idHora, Long contratoId)
    {
        ContratoHotelEntity contratoEntity = contratoHotelPersistence.find(contratoId);
        
        HoraHotelEntity horaHotelEntity = horaHotelPersistence.find(idHora);
        
        horaHotelEntity.getContratosHotel().add(contratoEntity);
        
        return horaHotelPersistence.find(idHora);
    }
    
    public List<HoraHotelEntity> getHorasHotel(Long contratoId)
    {
        return contratoHotelPersistence.find(contratoId).getHorasHotel();
    }
    
    public HoraHotelEntity getHoraHotel(Long contratoId, Long idHora)
    {
        List<HoraHotelEntity> listaHoras = contratoHotelPersistence.find(contratoId).getHorasHotel();
        HoraHotelEntity hora = horaHotelPersistence.find(idHora);
        int index = listaHoras.indexOf(hora);
        
        if(index>=0)
            return listaHoras.get(index);
        return null;
    }
    
    public List<HoraHotelEntity> replaceHorasHotel(Long contratoId, List<HoraHotelEntity> nuevaLista)
    {
        ContratoHotelEntity contrato = contratoHotelPersistence.find(contratoId);
        contrato.setHorasHotel(nuevaLista);
        return contratoHotelPersistence.find(contratoId).getHorasHotel();
    }
    
    public void removeHoraHotel(Long contratoId, Long idHora)
    {
        HoraHotelEntity hora = horaHotelPersistence.find(idHora);
        ContratoHotelEntity contrato = contratoHotelPersistence.find(contratoId);
        
        hora.getContratosHotel().remove(contrato);
    }
}
