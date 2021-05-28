/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.ejb;

import co.edu.uniandes.csw.paseadores.entities.PuntoEntity;
import co.edu.uniandes.csw.paseadores.entities.ContratoPaseoEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.PuntoPersistence;
import co.edu.uniandes.csw.paseadores.persistence.ContratoPaseoPersistence;
import javax.ejb.Stateless;
import javax.inject.Inject;

/**
 *
 * @author Nicolas Urrego Sandoval <n.urrego at uniandes.edu.co>
 */
@Stateless
public class PuntoLogic {
    @Inject
    private PuntoPersistence puntoPersistence;
    
    @Inject
    private ContratoPaseoPersistence contratoPaseoPersistence;

    /**
     * Guardar un nuevo puntoBusqueda
     *
     * @param puntoEntity La entidad de tipo puntoBusqueda del nuevo puntoBusqueda a persistir.
     * @return La entidad luego de persistirla
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    public PuntoEntity createPunto(PuntoEntity puntoEntity) throws BusinessLogicException {
        if(puntoEntity.getLongitud() < -74.227549 || puntoEntity.getLongitud() > -73.989016)
            throw new BusinessLogicException("La longitud del punto está fuera de Bogotá");
        if(puntoEntity.getLatitud() > 4.838319 || puntoEntity.getLatitud() < 4.471235)
            throw new BusinessLogicException("La latitud del punto está fuera de Bogotá");
        puntoEntity = puntoPersistence.create(puntoEntity);
        return puntoEntity;
    }
    
    /**
     * 
     *
     * @param puntoId
     * @param contratoPaseoId
     * @return El puntoBusqueda encontrado, null si no lo encuentra.
     * @throws BusinessLogicException si no existe el contratoPaseoBusq
     */
    public PuntoEntity addContratoPaseo(Long puntoId, Long contratoPaseoId) throws BusinessLogicException
    {
        PuntoEntity punto = puntoPersistence.find(puntoId);
        ContratoPaseoEntity contratoPaseo = contratoPaseoPersistence.find(contratoPaseoId);
        if(punto == null)
            throw new BusinessLogicException("El punto no existe");
        else if(contratoPaseo == null)
            throw new BusinessLogicException("El contratoPaseo no existe");
        else
            punto.setContratoPaseo(contratoPaseo);
        return puntoPersistence.find(puntoId);
    }
    
    /**
     *
     *
     * @param puntoId
     * @return El contratoPaseoBusq del puntoBusqueda encontrado, null si no lo encuentra.
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException si no existe el puntoBusqueda
     * 
     */
    public ContratoPaseoEntity getContratoPaseo(Long puntoId) throws BusinessLogicException
    {
        PuntoEntity punto = puntoPersistence.find(puntoId);
        if(punto == null)
            throw new BusinessLogicException("No existe el punto solicitado");
        return punto.getContratoPaseo();
    }
    
    /**
     * 
     *
     * @param puntoId
     * @param contratoPaseoId
     * @return El puntoBusqueda encontrado, null si no lo encuentra.
     * @throws BusinessLogicException si no existe el contratoPaseoBusq
     */
    public PuntoEntity updateContratoPaseo(Long puntoId, Long contratoPaseoId) throws BusinessLogicException
    {
        PuntoEntity puntoBusqueda = puntoPersistence.find(puntoId);
        ContratoPaseoEntity contratoPaseoBusq = contratoPaseoPersistence.find(contratoPaseoId);
        if(puntoBusqueda == null)
            throw new BusinessLogicException("El punto no existe");
        else if(contratoPaseoBusq == null)
            throw new BusinessLogicException("El contratoPaseo no existe");
        else
            puntoBusqueda.setContratoPaseo(contratoPaseoBusq);
        return puntoPersistence.find(puntoId);
    }
    
    /**
     *
     *
     * @param puntoId
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException si no existe el punto
     * 
     */
    public void removeContratoPaseo(Long puntoId) throws BusinessLogicException
    {
        PuntoEntity punto = puntoPersistence.find(puntoId);
        if(punto == null)
            throw new BusinessLogicException("No existe el punto solicitado");
        else
            punto.setContratoPaseo(null);
    }
    
    /**
     * Busca un puntoBusqueda por posicion
     *
     * @param posicion
     * @return El puntoBusqueda encontrado, null si no lo encuentra.
     */
    public PuntoEntity getPuntoByPosicion(Integer posicion) {
        return puntoPersistence.findByPosicion(posicion);
    }

    /**
     * Busca un puntoBusqueda por ID
     *
     * @param puntosId El id del puntoBusqueda a buscar
     * @return El puntoBusqueda encontrado, null si no lo encuentra.
     */
    public PuntoEntity getPunto(Long puntosId) {
        return puntoPersistence.find(puntosId);
    }

    /**
     * Actualizar un puntoBusqueda por ID
     *
     * @param puntosId El ID del puntoBusqueda a actualizar
     * @param puntoEntity La entidad del puntoBusqueda con los cambios deseados
     * @return La entidad del puntoBusqueda luego de actualizarla
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    public PuntoEntity updatePunto(Long puntosId, PuntoEntity puntoEntity) throws BusinessLogicException {
        if(puntoEntity.getLongitud() > -73.989016 || puntoEntity.getLongitud() < -74.227549)
            throw new BusinessLogicException("La longitud del punto está fuera de Bogotá");
        if(puntoEntity.getLatitud() < 4.471235 || puntoEntity.getLatitud() > 4.838319)
            throw new BusinessLogicException("La latitud del punto está fuera de Bogotá");
        return puntoPersistence.update(puntoEntity);
    }

    /**
     * Eliminar un puntoBusqueda por ID
     *
     * @param puntosId El ID del puntoBusqueda a eliminar
     */
    public void deletePunto(Long puntosId) {
        puntoPersistence.delete(puntosId);
    }
}
