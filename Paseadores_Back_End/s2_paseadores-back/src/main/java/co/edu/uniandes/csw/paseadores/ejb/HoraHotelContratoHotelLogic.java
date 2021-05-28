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
public class HoraHotelContratoHotelLogic {
    
    @Inject
    private ContratoHotelPersistence contratoHotelPersistence;
    
    @Inject
    private HoraHotelPersistence horaHotelPersistence;
    
    public ContratoHotelEntity addContrato (Long contratoId, Long idHora)
    {
        ContratoHotelEntity contratoEntity = contratoHotelPersistence.find(contratoId);
        
        HoraHotelEntity horaHotelEntity = horaHotelPersistence.find(idHora);
        
        horaHotelEntity.getContratosHotel().add(contratoEntity);
        
        return contratoHotelPersistence.find(contratoId);
    }
    
    public List<ContratoHotelEntity> getContratos(Long idHora)
    {
        return horaHotelPersistence.find(idHora).getContratosHotel();
    }
    
    public ContratoHotelEntity getContrato(Long idHora, Long contratoId)
    {
        List<ContratoHotelEntity> listaContratos = horaHotelPersistence.find(idHora).getContratosHotel();
        ContratoHotelEntity contrato = contratoHotelPersistence.find(contratoId);
        int index = listaContratos.indexOf(contrato);
        
        if(index>=0)
            return listaContratos.get(index);
        return null;
    }
    
     public List<ContratoHotelEntity> replaceContratosHotel(Long idHora, List<ContratoHotelEntity> nuevaLista)
    {
        HoraHotelEntity hora = horaHotelPersistence.find(idHora);
        hora.setContratosHotel(nuevaLista);
        return horaHotelPersistence.find(idHora).getContratosHotel();
    }
     
     public void removeContratoHotel(Long idHora, Long contratoId)
    {
        ContratoHotelEntity contrato = contratoHotelPersistence.find(contratoId);
        HoraHotelEntity hora = horaHotelPersistence.find(idHora);
        
        hora.getContratosHotel().remove(contrato);
    }
    
}
