/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.ejb;

import co.edu.uniandes.csw.paseadores.entities.PuntoEntity;
import co.edu.uniandes.csw.paseadores.entities.RecorridoEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.PuntoPersistence;
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
public class RecorridoPuntoLogic {
    @Inject
    private RecorridoPersistence recorridoPersistence;
    
    @Inject
    private PuntoPersistence puntoPersistence;

    /**
     * Agregar un recorrido a un punto
     *
     * @param puntosId El id punto a guardar
     * @param recorridosId El id del recorrido al cual se le va a guardar el punto.
     * @return El punto que fue agregado al recorrido.
     */
    public RecorridoEntity addRecorrido(Long recorridosId, Long puntosId) {
        RecorridoEntity recorridoEntity = recorridoPersistence.find(recorridosId);
        PuntoEntity puntoEntity = puntoPersistence.find(puntosId);
        if (puntoEntity == null) {
            throw new WebApplicationException("El recurso /puntosId/" + puntosId + "no existe.", 404);
        }
        puntoEntity.setRecorrido(recorridoEntity);
        return recorridoPersistence.find(recorridosId);
    }
    
    /**
     * Agregar un recorrido a un punto
     *
     * @param puntosId El id punto a guardar
     * @param recorridosId El id del recorrido al cual se le va a guardar el punto.
     * @return El punto que fue agregado al recorrido.
     */
    public PuntoEntity addPunto(Long recorridosId, Long puntosId) {
        RecorridoEntity recorridoEntity = recorridoPersistence.find(recorridosId);
        PuntoEntity puntoEntity = puntoPersistence.find(puntosId);
        puntoEntity.setRecorrido(recorridoEntity);
        return puntoPersistence.find(puntosId);
    }

    /**
     *
     * Obtener un punto por medio de su id y el de su recorrido.
     *
     * @param puntosId id del punto a ser buscado.
     * @return el recorrido solicitada por medio de su id.
     */
    public RecorridoEntity getRecorrido(Long puntosId) {
        return puntoPersistence.find(puntosId).getRecorrido();
    }
    
    public List<PuntoEntity> getPuntos(Long recorridosId) {
        return recorridoPersistence.find(recorridosId).getPuntos();
    }

    /**
     * Retorna un punto asociado a una recorrido
     *
     * @param recorridosId El id de la recorrido a buscar.
     * @param puntosId El id del punto a buscar
     * @return El punto encontrado dentro de la recorrido.
     * @throws BusinessLogicException Si el punto no se encuentra en la
     * recorrido
     */
    public PuntoEntity getPunto(Long recorridosId, Long puntosId) throws BusinessLogicException {
        
        List<PuntoEntity> puntos = recorridoPersistence.find(recorridosId).getPuntos();
        PuntoEntity puntoEntity = puntoPersistence.find(puntosId);
        int index = puntos.indexOf(puntoEntity);
        
        if (index >= 0) {
            return puntos.get(index);
        }
        throw new BusinessLogicException("El punto no está asociado al recorrido");
    }

    /**
     * Remplazar recorrido de un punto
     *
     * @param puntosId el id del punto que se quiere actualizar.
     * @param recorridosId El id del nuebo recorrido asociado al punto.
     * @return el nuevo recorrido asociado.
     */
    public RecorridoEntity replaceRecorrido(Long puntosId, Long recorridosId) {
        RecorridoEntity recorrEntity = recorridoPersistence.find(recorridosId);
        PuntoEntity puntoNuevoEntity = puntoPersistence.find(puntosId);
        if (puntoNuevoEntity == null) {
            throw new WebApplicationException("El recurso /recorridos/" + recorridosId + " no existe", 404);
        }
        puntoNuevoEntity.setRecorrido(recorrEntity);
        return recorridoPersistence.find(recorridosId);
    }
    
    /**
     * Remplazar recorrido de un punto
     
     * @param recorridosId El id del nuebo recorrido asociado al punto.
     * @param list
     * @return el nuevo recorrido asociado.
     */
    public List<PuntoEntity> replacePuntos(Long recorridosId, List<PuntoEntity> list) {
        RecorridoEntity recorridoEntity = recorridoPersistence.find(recorridosId);
        int numPuntos = recorridoEntity.getPuntos().size();
        for(int i=0; i<numPuntos; i++)
        {
            PuntoEntity puntoDel = recorridoEntity.getPuntos().get(0);
            PuntoEntity puntoNuevoEntity = puntoPersistence.find(puntoDel.getId());
            recorridoEntity.getPuntos().remove(puntoDel);
            puntoNuevoEntity.setRecorrido(null);
        }
        for(PuntoEntity punto : list)
        {
            PuntoEntity puntoNuevoEntity = puntoPersistence.find(punto.getId());
            puntoNuevoEntity.setRecorrido(recorridoEntity);
            recorridoEntity.getPuntos().add(puntoNuevoEntity);
        }
        return recorridoPersistence.find(recorridosId).getPuntos();
    }

    /**
     * Borrar el recorrido de un punto
     *
     * @param puntosId El punto que se desea borrar del recorrido.
     */
    public void removePunto(Long puntosId) {
        PuntoEntity puntoEntity = puntoPersistence.find(puntosId);
        if (puntoEntity == null) {
            throw new WebApplicationException("El recurso /puntos/" + puntosId + " no existe   .", 404);
        }
        RecorridoEntity recorridoEntity = recorridoPersistence.find(puntoEntity.getRecorrido().getId());
        recorridoEntity.getPuntos().remove(puntoEntity);
        puntoEntity.setRecorrido(null);
    }
}
