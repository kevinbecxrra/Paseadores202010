/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.ejb;

import co.edu.uniandes.csw.paseadores.entities.PagoClienteEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.ContratoHotelPersistence;
import co.edu.uniandes.csw.paseadores.persistence.ContratoPaseoPersistence;
import co.edu.uniandes.csw.paseadores.persistence.PagoClientePersistence;
import java.util.List;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author julian Oliveros<je.oliverosf at uniandes.edu.co>
 */
@Stateless
public class PagoClienteLogic {
    @Inject
    private PagoClientePersistence persistence;


    //injectar clases de relaciones
    @Inject
    private ContratoHotelPersistence contratoHotelPersistence;
    
    @Inject
    private ContratoPaseoPersistence contratoPaseoPersistence;
    
    

    /**
     * Guardar un nuevo pagoCliente
     *
     * @param pagoCliente La entidad de tipo pago del nuevo pago a persistir.
     * @return La entidad luego de persistirla
     * @throws BusinessLogicException 
     *                                 El monto de un PagoCliente debe ser positivo
     *                                 La referencia de un PagoCliente debe ser única
     *                                 Un PagoCliente solo puede estar asociado a un tipo de Contrato
     */
    public PagoClienteEntity createPagoCliente(PagoClienteEntity pagoCliente) throws BusinessLogicException
    {   
        
        if(pagoCliente.getMonto()<0){
            throw new BusinessLogicException("El monto de pago es negativo");
        }
        if(persistence.findByReferencia(pagoCliente.getReferencia())!=null) {
            throw new BusinessLogicException("La referencia de pago ya existe");
        }
        
        if(pagoCliente.getContratoHotel()!=null && pagoCliente.getContratoPaseo()!=null){
            throw new BusinessLogicException("No puede haber un pago asociado a mas de un tipo de contrato");
            
        }
  
        
        pagoCliente = persistence.create(pagoCliente);
        return pagoCliente;
    }
    

    /**
     * Busca un pago por ID
     *
     * @param pagoId El id del pago a buscar
     * @return El pago encontrado, null si no lo encuentra.
     */
    public PagoClienteEntity getPagoCliente(Long pagoId) {
        
        return persistence.find(pagoId);
    }

    /**
     * Actualizar un pago por ID
     *
     * @param pagoIdActual
     * @param pagoClientenewEntity
* @return La entidad del pago luego de actualizarla
     * @throws BusinessLogicException 
     *                                 El monto de un PagoCliente debe ser positivo
     *                                 La referencia de un PagoCliente debe ser única
     *                                 Un PagoCliente solo puede estar asociado a un tipo de Contrato
     */
    public PagoClienteEntity updatePagoCliente(Long pagoIdActual, PagoClienteEntity pagoClientenewEntity) throws BusinessLogicException {
       
        if (!validateId(pagoClientenewEntity.getReferencia())) {
            throw new BusinessLogicException("El id es inválido");
        }
        if(pagoClientenewEntity.getMonto()<0){
            throw new BusinessLogicException("El monto de pago es negativo");
        }
        if(persistence.findByReferencia(pagoClientenewEntity.getReferencia())!=null && persistence.findByReferencia(pagoClientenewEntity.getReferencia()).getId() != pagoIdActual) {
            throw new BusinessLogicException("La referencia de pago ya existe");
        }
        if(pagoClientenewEntity.getContratoHotel()!=null && pagoClientenewEntity.getContratoPaseo()!=null){
            throw new BusinessLogicException("No puede haber un pago asociado a mas de un tipo de contrato");       
        }
        
        return persistence.update(pagoClientenewEntity);
    }

    /**
     * Eliminar un pago por ID
     *
     * @param pagoId El ID del pago a eliminar
     */
    public void deletePagoCliente(Long pagoId) {
        persistence.delete(pagoId);
        
    }
    
    /**
     * Busca los pagos que se encuentren dentro de la misma referencia
     *
     * @param idPago
     * @return El pago encontrado
     */
    public PagoClienteEntity getPagoByReferencia(String idPago)
    {
        return  persistence.findByReferencia(idPago);
    }
    

    /**
     * Verifica que el id no sea invalido.
     *
     * @param id a verificar
     * @return true si el id es valido.
     */
    private boolean validateId(String id) {
        return !(id == null || id.isEmpty());
    }

    public List<PagoClienteEntity> getPagosCliente()
    {
        return persistence.findAll();
    }
}
