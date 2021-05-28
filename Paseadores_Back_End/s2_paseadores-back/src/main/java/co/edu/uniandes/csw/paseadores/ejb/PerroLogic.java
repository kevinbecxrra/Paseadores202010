/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.ejb;

import co.edu.uniandes.csw.paseadores.entities.PerroEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.PerroPersistence;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author julian Oliveros<je.oliverosf at uniandes.edu.co>
 */
@Stateless
public class PerroLogic {
    
    private static final Logger LOGGER = Logger.getLogger(PerroLogic.class.getName());
    
    @Inject
    private PerroPersistence persistence;
    
    //va a tener un metodo por cada uno de los servicios que el api ofrece.
    

    /**
     * Guardar un nuevo perro
     *
     * @param perro La entidad de tipo pago del nuevo perro a persistir.
     * @return La entidad luego de persistirla
     * @throws BusinessLogicException 
     *                                 La edad de un perro debe ser positiva
     *                                 El id del perro debe ser unico
     */
    public PerroEntity createPerro(PerroEntity perro) throws BusinessLogicException { 
        
        if(perro.getEdad()<0){
            throw new BusinessLogicException("La edad del perro es negativa");
        }
        if(persistence.findByIdPerro(perro.getIdPerro())!=null) {
            throw new BusinessLogicException("Ya existe un perro con este id");
        }
        
        perro = persistence.create(perro);
        return perro;
    }
    
        /**
     * Devuelve todos los perros que hay en la base de datos.
     *
     * @return Lista de entidades de tipo perros.
     */
    public List<PerroEntity> getPerros() {
        return persistence.findAll();
    }

    /**
     * Busca un perro por ID
     *
     * @param perroId El id del perro a buscar
     * @return El perro encontrado, null si no lo encuentra.
     */
    public PerroEntity getPerro(Long perroId) {
        
        LOGGER.log(Level.INFO, "perro Logic get perro: input: perrosId {0} , contratoPaseoId");

        return persistence.find(perroId);
    }

    /**
     * Actualizar un perro por ID
     *
     * @param perroId El ID del perro a actualizar
     * @param perroEntity La entidad del perro con los cambios deseados
     * @return La entidad del perro luego de actualizarla
     * @throws BusinessLogicException 
     *                                 La edad de un perro debe ser positiva
     *                                 El id del perro debe ser unico
     */
    public PerroEntity updatePerro(Long perroId, PerroEntity perroEntity) throws BusinessLogicException {
        
        if(perroEntity.getEdad()<0){
            throw new BusinessLogicException("La edad del perro es negativa");
        }
        if(persistence.findByIdPerro(perroEntity.getIdPerro())!=null) {
            throw new BusinessLogicException("Ya existe un perro con este id");
        }
       
        return persistence.update(perroEntity);
    }

    /**
     * Eliminar un perro por ID
     *
     * @param perroId El ID del perro a eliminar
     */
    public void deletePerro(Long perroId) {   
        persistence.delete(perroId);
    }

    /**
     * Busca los pagos que se encuentren dentro de la misma referencia
     *
     * @param idPerro
     * @return El pago encontrado
     */
    public PerroEntity getPerroByIDPerro(String idPerro)
    {
        return persistence.findByIdPerro(idPerro);
    }
    

    
           
}
