/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.ejb;


import co.edu.uniandes.csw.paseadores.entities.PagoPaseadorEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.PagoPaseadorPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;
import java.util.Date;
import java.util.List;



/**
 *
 * @author julian Oliveros<je.oliverosf at uniandes.edu.co>
 */
@Stateless
public class PagoPaseadorLogic {
    
    @Inject
    private PagoPaseadorPersistence persistence;
    
    /**
     * Guardar un nuevo pagoPaseador
     *
     * @param pagoPaseador La entidad de tipo pago del nuevo pago a persistir.
     * @return La entidad luego de persistirla
     * @throws BusinessLogicException 
     *                                 El monto de un PagoPaseador debe ser positivo
     *                                 La referencia de un PagoPaseador debe ser única
     */
    public PagoPaseadorEntity createPagoPaseador(PagoPaseadorEntity pagoPaseador)throws BusinessLogicException{
        
        if(pagoPaseador.getMonto()<0) {
            throw new BusinessLogicException("El monto a pagar es negativo");
        }
        if(persistence.findByReferencia(pagoPaseador.getReferencia())!=null) { 
            throw new BusinessLogicException("La referencia de pago ya existe");
        }
        
        pagoPaseador = persistence.create(pagoPaseador);
        return pagoPaseador;
    }
    
    public List<PagoPaseadorEntity> getPagosPaseadores()
    {
        return persistence.findAll();
    }
       
    /**
     * Busca un pago por ID
     *
     * @param pagoId El id del pago a buscar
     * @return El pago encontrado, null si no lo encuentra.
     */
    public PagoPaseadorEntity getPagoPaseador(Long pagoId) {
        
        return persistence.find(pagoId);
    }

    /**
     * Actualizar un pago por ID
     *
     * @param pagoId El ID del pago a actualizar
     * @param pagoPaseadorEntity La entidad del pagos con los cambios deseados
     * @return La entidad del pago luego de actualizarla
     * @throws BusinessLogicException 
     *                                 El monto de un PagoPaseador debe ser positivo
     *                                 La referencia de un PagoPaseador debe ser única
     */
    public PagoPaseadorEntity updatePago(Long pagoId, PagoPaseadorEntity pagoPaseadorEntity) throws BusinessLogicException {
        
        if(pagoPaseadorEntity.getMonto()<0) {
            throw new BusinessLogicException("El monto a pagar es negativo");
        }
        if(persistence.findByReferencia(pagoPaseadorEntity.getReferencia())!=null) { 
            throw new BusinessLogicException("La referencia de pago ya existe");
        }
        return persistence.update(pagoPaseadorEntity);
    }

    /**
     * Eliminar un pago por ID
     *
     * @param pagoId El ID del pago a eliminar
     */
    public void deletePagoPaseador(Long pagoId) {

        persistence.delete(pagoId);        
    }
    
    /**
     * Busca los pagos que se encuentren dentro de la misma fecha limite
     * @param fecha
     * @return Los pagos encontrados
     */
    public PagoPaseadorEntity getPagoByfechaLimite(Date fecha)
    {
        return persistence.findByfechaLimite(fecha);
    }
    
    /**
     * Busca los recorridos que se encuentren dentro un rango de calificaciones
     *
     * @param referencia
     * @return Los pagos encontrados
     */
    public PagoPaseadorEntity getPagoByReferencia(String referencia)
    {
        return persistence.findByReferencia(referencia);
    }   
}
