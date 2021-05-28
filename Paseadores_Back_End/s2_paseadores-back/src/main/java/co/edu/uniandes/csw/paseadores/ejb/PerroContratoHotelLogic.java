/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.ejb;

import co.edu.uniandes.csw.paseadores.entities.ContratoHotelEntity;
import co.edu.uniandes.csw.paseadores.entities.PerroEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.ContratoHotelPersistence;
import co.edu.uniandes.csw.paseadores.persistence.PerroPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Kevin Camilo Becerra Walteros
 */
@Stateless
public class PerroContratoHotelLogic {
    
    private static final Logger LOGGER = Logger.getLogger(PerroContratoHotelLogic.class.getName());
    
    @Inject
    private PerroPersistence perroPersistence;

    @Inject
    private ContratoHotelPersistence contratoHotelPersistence;

    public PerroEntity addPerro(Long contratoHotelId, Long perroId)
    {
        LOGGER.log(Level.INFO, "--Entro a LOGIC ");
        PerroEntity perr = perroPersistence.find(perroId);
        LOGGER.log(Level.INFO, "--Entro a LOGIC Y ENCONTRO EL PERRO ");
        ContratoHotelEntity contratoHotelEntity = contratoHotelPersistence.find(contratoHotelId);
        LOGGER.log(Level.INFO, "--Entro a LOGIC Y ENCONTRO EL contrato ");
        contratoHotelEntity.setPerro(perr);
        LOGGER.log(Level.INFO, "--Entro a LOGIC creo la asociacion ");
        return perroPersistence.find(perroId);
    }
    
    public ContratoHotelEntity addContratoHotel(Long perroId, Long contratoHotelId)
    {
        PerroEntity perroEntity = perroPersistence.find(perroId);
        ContratoHotelEntity contratoHotelEntity = contratoHotelPersistence.find(contratoHotelId);
        contratoHotelEntity.setPerro(perroEntity);
        return contratoHotelPersistence.find(contratoHotelId);
    }

    public PerroEntity getPerro(Long contratoId) 
    {
        return contratoHotelPersistence.find(contratoId).getPerro();
    }
    
    public List<ContratoHotelEntity> getContratosHotel(Long perroId)
    {
        return perroPersistence.find(perroId).getEstadias();
    }
    
    public ContratoHotelEntity getContratoHotel(Long perroId, Long contratoHotelId) throws BusinessLogicException 
    {        
        List<ContratoHotelEntity> contratos = perroPersistence.find(perroId).getEstadias();
        ContratoHotelEntity contratoHotelEntity = contratoHotelPersistence.find(contratoHotelId);
        int index = contratos.indexOf(contratoHotelEntity);       
        if (index >= 0) {
            return contratos.get(index);
        }
        throw new BusinessLogicException("El paseo no está asociado al paseador");
    }

    public PerroEntity replacePerro(Long perroId, Long contratoHotelId) {
        LOGGER.log(Level.INFO, "-------------ENtro a logic");
        PerroEntity perroEntity = perroPersistence.find(perroId);
        LOGGER.log(Level.INFO, "-------------ENtro a logic encuentra perro");
        ContratoHotelEntity contratoHotelEntity = contratoHotelPersistence.find(contratoHotelId);
        LOGGER.log(Level.INFO, "-------------ENtro a logic encuentra contrato");
        contratoHotelEntity.setPerro(perroEntity);
        LOGGER.log(Level.INFO, "-------------ENtro a logic hace bien el set");
        return perroPersistence.find(perroId);
    }
    
    public List<ContratoHotelEntity> replaceContratosHotel(Long perroId, List<ContratoHotelEntity> listaContratos) {
        PerroEntity perroEntity = perroPersistence.find(perroId);
        
        int numPuntos = perroEntity.getEstadias().size();
        for(int i=0; i<numPuntos; i++)
        {
            ContratoHotelEntity puntoDel = perroEntity.getEstadias().get(0);
            ContratoHotelEntity contratoHEntity = contratoHotelPersistence.find(puntoDel.getId());
            perroEntity.getEstadias().remove(puntoDel);
            contratoHEntity.setPerro(null);
        }
        for(ContratoHotelEntity contratoPaseo : listaContratos)
        {
            ContratoHotelEntity puntoNuevoEntity = contratoHotelPersistence.find(contratoPaseo.getId());
            puntoNuevoEntity.setPerro(perroEntity);
            perroEntity.getEstadias().add(puntoNuevoEntity);
   
        }
        return perroPersistence.find(perroId).getEstadias();
    }

    public void removeContratoHotel(Long contratoHotelId) 
    {
        ContratoHotelEntity contratoHotelEntity = contratoHotelPersistence.find(contratoHotelId);
        PerroEntity perroEntity = perroPersistence.find(contratoHotelEntity.getPerro().getId());
        perroEntity.getEstadias().remove(contratoHotelEntity);
        contratoHotelEntity.setPerro(null);
    }    
}
