/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.ejb;

import co.edu.uniandes.csw.paseadores.entities.PaseoEntity;
import co.edu.uniandes.csw.paseadores.entities.RecorridoEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.PaseoPersistence;
import co.edu.uniandes.csw.paseadores.persistence.RecorridoPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Nicolas Urrego Sandoval <n.urrego at uniandes.edu.co>
 */
@Stateless
public class RecorridoPaseoLogic {
    @Inject
    private RecorridoPersistence recorridoPersistence;
    
    @Inject
    private PaseoPersistence paseoPersistence;

    /**
     * Agregar un recorrido a un paseo
     *
     * @param paseosId El id paseo a guardar
     * @param recorridosId El id del recorrido al cual se le va a guardar el paseo.
     * @return El paseo que fue agregado al recorrido.
     */
    public RecorridoEntity addRecorrido(Long recorridosId, Long paseosId) {
        RecorridoEntity recorridoEntity = recorridoPersistence.find(recorridosId);
        PaseoEntity paseoEntity = paseoPersistence.find(paseosId);
        if (paseoEntity == null) {
            throw new WebApplicationException("El recurso /paseosId/" + paseosId + "no existe.", 404);
        }
        paseoEntity.setRecorrido(recorridoEntity);
        return recorridoPersistence.find(recorridosId);
    }
    
    /**
     * Agregar un recorrido a un paseo
     *
     * @param paseosId El id paseo a guardar
     * @param recorridosId El id del recorrido al cual se le va a guardar el paseo.
     * @return El paseo que fue agregado al recorrido.
     */
    public PaseoEntity addPaseo(Long recorridosId, Long paseosId) {
        RecorridoEntity recorridoEntity = recorridoPersistence.find(recorridosId);
        PaseoEntity paseoEntity = paseoPersistence.find(paseosId);
        paseoEntity.setRecorrido(recorridoEntity);
        return paseoPersistence.find(paseosId);
    }

    /**
     *
     * Obtener un paseo por medio de su id y el de su recorrido.
     *
     * @param paseosId id del paseo a ser buscado.
     * @return el recorrido solicitada por medio de su id.
     */
    public RecorridoEntity getRecorrido(Long paseosId) {
        return paseoPersistence.find(paseosId).getRecorrido();
    }
    
    public List<PaseoEntity> getPaseos(Long recorridosId) {
        
        return recorridoPersistence.find(recorridosId).getPaseos();
    }

    /**
     * Retorna un paseo asociado a una recorrido
     *
     * @param recorridosId El id de la recorrido a buscar.
     * @param paseosId El id del paseo a buscar
     * @return El paseo encontrado dentro de la recorrido.
     * @throws BusinessLogicException Si el paseo no se encuentra en la
     * recorrido
     */
    public PaseoEntity getPaseo(Long recorridosId, Long paseosId) throws BusinessLogicException {
        
        List<PaseoEntity> paseos = recorridoPersistence.find(recorridosId).getPaseos();
        PaseoEntity paseoEntity = paseoPersistence.find(paseosId);
        int index = paseos.indexOf(paseoEntity);
        
        if (index >= 0) {
            return paseos.get(index);
        }
        throw new BusinessLogicException("El paseo no está asociado al recorrido");
    }

    /**
     * Remplazar recorrido de un paseo
     *
     * @param paseosId el id del paseo que se quiere actualizar.
     * @param recorridosId El id del nuebo recorrido asociado al paseo.
     * @return el nuevo recorrido asociado.
     */
    public RecorridoEntity replaceRecorrido(Long paseosId, Long recorridosId) {
        RecorridoEntity recorrEntity = recorridoPersistence.find(recorridosId);
        PaseoEntity paseoNuevoEntity = paseoPersistence.find(paseosId);
        if (paseoNuevoEntity == null) {
            throw new WebApplicationException("El recurso /recorridos/" + recorridosId + " no existe", 404);
        }
        paseoNuevoEntity.setRecorrido(recorrEntity);
        return recorridoPersistence.find(recorridosId);
    }
    
    /**
     * Remplazar recorrido de un paseo
     
     * @param recorridosId El id del nuebo recorrido asociado al paseo.
     * @param list
     * @return el nuevo recorrido asociado.
     */
    public List<PaseoEntity> replacePaseos(Long recorridosId, List<PaseoEntity> list) {
        RecorridoEntity recorridoEntity = recorridoPersistence.find(recorridosId);
        int numPaseos = recorridoEntity.getPaseos().size();
        for(int i=0; i<numPaseos; i++)
        {
            PaseoEntity paseoDel = recorridoEntity.getPaseos().get(0);
            PaseoEntity paseoNuevoEntity = paseoPersistence.find(paseoDel.getId());
            recorridoEntity.getPaseos().remove(paseoDel);
            paseoNuevoEntity.setRecorrido(null);
        }
        for(PaseoEntity paseo : list)
        {
            PaseoEntity paseoNuevoEntity = paseoPersistence.find(paseo.getId());
            paseoNuevoEntity.setRecorrido(recorridoEntity);
            recorridoEntity.getPaseos().add(paseoNuevoEntity);
        }
        return recorridoPersistence.find(recorridosId).getPaseos();
    }

    /**
     * Borrar el recorrido de un paseo
     *
     * @param paseosId El paseo que se desea borrar del recorrido.
     */
    public void removePaseo(Long paseosId) {
        PaseoEntity paseoEntity = paseoPersistence.find(paseosId);
        if (paseoEntity == null) {
            throw new WebApplicationException("El recurso /paseos/" + paseosId + " no existe   .", 404);
        }
        RecorridoEntity recorridoEntity = recorridoPersistence.find(paseoEntity.getRecorrido().getId());
        recorridoEntity.getPaseos().remove(paseoEntity);
        paseoEntity.setRecorrido(null);
    }
}
