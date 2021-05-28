/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.ejb;

import co.edu.uniandes.csw.paseadores.entities.RecorridoEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.RecorridoPersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Nicolas Urrego Sandoval <n.urrego at uniandes.edu.co>
 */
@Stateless
public class RecorridoLogic {
    @Inject
    private RecorridoPersistence recorridoPersistence;

    /**
     * Guardar un nuevo recorrido
     *
     * @param recorridoEntity La entidad de tipo recorrido del nuevo recorrido a
     * persistir.
     * @return La entidad luego de persistirla
     * @throws BusinessLogicException 
     */
    public RecorridoEntity createRecorrido(RecorridoEntity recorridoEntity) throws BusinessLogicException {
        if(recorridoEntity.getCalificacionGlobal() < 0 || recorridoEntity.getCalificacionGlobal() > 5)
            throw new BusinessLogicException("La calificación global debe estar entre 0 y 5");
        if(recorridoEntity.getNumeroCalificaciones() < 0)
            throw new BusinessLogicException("El número de calificaciones no puede ser menor a 0");
        recorridoEntity = recorridoPersistence.create(recorridoEntity);
        return recorridoEntity;
    }

    /**
     * Busca los recorridos que se encuentren dentro un rango de calificaciones
     *
     * @param low
     * @param high
     * @return Los recorridos encontrados
     */
    public List<RecorridoEntity> getRecorridoByCalificacionGlobalInRange(Double low, Double high) {
        return recorridoPersistence.findByCalificacionGlobalInRange(low, high);
    }

    /**
     * Busca un recorrido por ID
     *
     * @param recorridosId El id del recorrido a buscar
     * @return El recorrido encontrado, null si no lo encuentra.
     */
    public RecorridoEntity getRecorrido(Long recorridosId) {
        return recorridoPersistence.find(recorridosId);
    }

    /**
     * Actualizar un recorrido por ID
     *
     * @param recorridosId El ID del recorrido a actualizar
     * @param recorridoEntity La entidad del recorrido con los cambios deseados
     * @return La entidad del recorrido luego de actualizarla
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    public RecorridoEntity updateRecorrido(Long recorridosId, RecorridoEntity recorridoEntity) throws BusinessLogicException {
        if(recorridoEntity.getCalificacionGlobal() > 5 || recorridoEntity.getCalificacionGlobal() < 0)
            throw new BusinessLogicException("La calificación global debe estar entre 0 y 5");
        if(0 > recorridoEntity.getNumeroCalificaciones())
            throw new BusinessLogicException("El número de calificaciones no puede ser menor a 0");
        return recorridoPersistence.update(recorridoEntity);
    }

    /**
     * Eliminar un recorrido por ID
     *
     * @param recorridosId El ID del recorrido a eliminar
     */
    public void deleteRecorrido(Long recorridosId) {
        recorridoPersistence.delete(recorridosId);
    }
    
        public List<RecorridoEntity> findAll() 
    {
        return recorridoPersistence.findAll();
    }
}
