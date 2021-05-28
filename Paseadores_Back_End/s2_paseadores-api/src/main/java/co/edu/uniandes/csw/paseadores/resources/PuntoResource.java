/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;

import co.edu.uniandes.csw.paseadores.dtos.PuntoDTO;
import co.edu.uniandes.csw.paseadores.ejb.PuntoLogic;
import co.edu.uniandes.csw.paseadores.ejb.RecorridoPuntoLogic;
import co.edu.uniandes.csw.paseadores.entities.PuntoEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.WebApplicationException;

/**
 *
 * @author Nicolas Urrego Sandoval <n.urrego at uniandes.edu.co>
 */
@Path("puntos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class PuntoResource {
    
    private static final Logger LOGGER = Logger.getLogger(PuntoResource.class.getName());

    @Inject
    private PuntoLogic puntoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    @Inject
    private RecorridoPuntoLogic recorridoPuntoLogic;
    
    /**
     * Crea un nuevo punto con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param punto {@link PuntoDTO} - EL punto que se desea guardar.
     * @return JSON {@link PuntoDTO} - El punto guardado con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el punto es inválido
     */
    @POST
    public PuntoDTO createPunto(PuntoDTO punto) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "PuntoResource createPunto: input: {0}", punto);
        PuntoDTO nuevoPuntoDTO = new PuntoDTO(puntoLogic.createPunto(punto.toEntity()));
        LOGGER.log(Level.INFO, "PuntoResource createPunto: output: {0}", nuevoPuntoDTO);
        return nuevoPuntoDTO;
    }

    /**
     * Busca el punto con el id asociado recibido en la URL y lo devuelve.
     *
     * @param puntosId Identificador del punto que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link PuntoDTO} - El punto buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el punto.
     */
    @GET
    @Path("{puntosId: \\d+}")
    public PuntoDTO getPunto(@PathParam("puntosId") Long puntosId) {
        LOGGER.log(Level.INFO, "PuntoResource getPunto: input: {0}", puntosId);
        PuntoEntity puntoEntity = puntoLogic.getPunto(puntosId);
        if (puntoEntity == null) {
            throw new WebApplicationException("El recurso /puntos/" + puntosId + " no existe.", 404);
        }
        PuntoDTO puntoDTO = new PuntoDTO(puntoEntity);
        LOGGER.log(Level.INFO, "PuntoResource getPunto: output: {0}", puntoDTO);
        return puntoDTO;
    }
    
    /**
     * Busca el punto con el id asociado recibido en la URL y lo devuelve.
     *
     * @param puntosPos
     * @return JSON {@link PuntoDTO} - El punto buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el punto.
     */
    @GET
    @Path("posicion/{puntosPos: \\d+}")
    public PuntoDTO getPuntoByPosicion(@PathParam("puntosPos") Integer puntosPos) {
        LOGGER.log(Level.INFO, "PuntoResource getPuntoByPos: input: {0}", puntosPos);
        PuntoEntity puntoEntity = puntoLogic.getPuntoByPosicion(puntosPos);
        if (puntoEntity == null) {
            throw new WebApplicationException("El recurso  /puntos/posicion" + puntosPos + " no existe. ", 404);
        }
        PuntoDTO puntoDTO = new PuntoDTO(puntoEntity);
        LOGGER.log(Level.INFO, "PuntoResource getPuntoByPos: output: {0}", puntoDTO);
        return puntoDTO;
    }

    /**
     * Actualiza el punto con el id recibido en la URL con la información que se
     * recibe en el cuerpo de la petición.
     *
     * @param puntosId Identificador del punto que se desea actualizar. Este debe
     * ser una cadena de dígitos.
     * @param punto {@link PuntoDTO} El punto que se desea guardar.
     * @return JSON {@link PuntoDTO} - El punto guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el punto a
     * actualizar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede actualizar el punto.
     */
    @PUT
    @Path("{puntosId: \\d+}")
    public PuntoDTO updatePunto(@PathParam("puntosId") Long puntosId, PuntoDTO punto) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "PuntoResource updatePunto: input: id: {0} , punto: {1}", new Object[]{puntosId, punto});
        punto.setId(puntosId);
        if (puntoLogic.getPunto(puntosId) == null) {
            throw new WebApplicationException(" El recurso /puntos/" + puntosId + "  no existe.", 404);
        }
        PuntoDTO newDTO = new PuntoDTO(puntoLogic.updatePunto(puntosId, punto.toEntity()));
        LOGGER.log(Level.INFO, "PuntoResource updatePunto: output: {0}", newDTO);
        return newDTO;
    }
    
    @GET
    @Path("{puntosId: \\d+}/recorrido")
    public Class<RecorridoPuntoResource> getRecorrido(@PathParam("puntosId") Long puntoId) {
         LOGGER.log(Level.INFO, "getRecorrido");
        if (puntoLogic.getPunto(puntoId) == null) {
            throw new WebApplicationException("El  recurso /puntos/" + puntoId + " no existe.  ", 404);
        }
        return RecorridoPuntoResource.class;
    }

    /**
     * Borra el punto con el id asociado recibido en la URL.
     *
     * @param puntosId Identificador del punto que se desea borrar. Este debe ser
     * una cadena de dígitos.
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el punto.
     */
    @DELETE
    @Path("{puntosId: \\d+}")
    public void deletePunto(@PathParam("puntosId") Long puntosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "PuntoResource deletePunto: input: {0}", puntosId);
        PuntoEntity entity = puntoLogic.getPunto(puntosId);
        if (entity == null) {
            throw new WebApplicationException("El  recurso  /puntos/" + puntosId + "  no existe. ", 404);
        }
        puntoLogic.deletePunto(puntosId);
        LOGGER.info("PuntoResource deletePunto: output: void");
    }

}
