/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;

import co.edu.uniandes.csw.paseadores.dtos.RecorridoDTO;
import co.edu.uniandes.csw.paseadores.dtos.RecorridoDetailDTO;
import co.edu.uniandes.csw.paseadores.ejb.RecorridoLogic;
import co.edu.uniandes.csw.paseadores.entities.RecorridoEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
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
@Path("recorridos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class RecorridoResource {
    
    private static final Logger LOGGER = Logger.getLogger(RecorridoResource.class.getName());

    @Inject
    private RecorridoLogic recorridoLogic; // Variable para acceder a la lógica de la aplicación. Es una inyección de dependencias.

    /**
     * Crea un nuevo recorrido con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param recorrido {@link RecorridoDTO} - EL recorrido que se desea guardar.
     * @return JSON {@link RecorridoDTO} - El recorrido guardado con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el recorrido o el isbn es
     * inválido o si la editorial ingresada es invalida.
     */
    @POST
    public RecorridoDTO createRecorrido(RecorridoDTO recorrido) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "RecorridoResource createRecorrido: input: {0}", recorrido);
        RecorridoDTO nuevoRecorridoDTO = new RecorridoDTO(recorridoLogic.createRecorrido(recorrido.toEntity()));
        LOGGER.log(Level.INFO, "RecorridoResource createRecorrido: output: {0}", nuevoRecorridoDTO);
        return nuevoRecorridoDTO;
    }

    /**
     * Busca el recorrido con el id asociado recibido en la URL y lo devuelve.
     *
     * @param recorridosId Identificador del recorrido que se esta buscando. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link RecorridoDetailDTO} - El recorrido buscado
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el recorrido.
     */
    @GET
    @Path("{recorridosId: \\d+}")
    public RecorridoDetailDTO getRecorrido(@PathParam("recorridosId") Long recorridosId) {
        LOGGER.log(Level.INFO, "RecorridoResource getRecorrido: input: {0}", recorridosId);
        RecorridoEntity recorridoEntity = recorridoLogic.getRecorrido(recorridosId);
        if (recorridoEntity == null) {
            throw new WebApplicationException("El recurso                /recorridos/" + recorridosId + " no existe            .", 404);
        }
        RecorridoDetailDTO recorridoDetailDTO = new RecorridoDetailDTO(recorridoEntity);
        LOGGER.log(Level.INFO, "RecorridoResource getRecorrido: output: {0}", recorridoDetailDTO);
        return recorridoDetailDTO;
    }
    
    /**
     * Conexión con el servicio de libros para un recorrido.
     * {@link PuntosRecorridoResource}
     *
     * Este método conecta la ruta de /recorridos con las rutas de /puntos que
     * dependen del recorrido, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de los libros.
     *
     * @param recorridosId El ID del recorrido con respecto al cual se accede al
     * servicio.
     * @return El servicio de Libros para ese recorrido en paricular.
     */
    @Path("{recorridosId: \\d+}/puntos")
    public Class<PuntosRecorridoResource> getPuntosRecorridoResource(@PathParam("recorridosId") Long recorridosId) {
        if (recorridoLogic.getRecorrido(recorridosId) == null) {
            throw new WebApplicationException("El recurso /recorridos/" + recorridosId + " no existe.", 404);
        }
        return PuntosRecorridoResource.class;
    }
    
    /**
     * Conexión con el servicio de libros para un recorrido.
     * {@link PaseosRecorridoResource}
     *
     * Este método conecta la ruta de /recorridos con las rutas de /paseos que
     * dependen del recorrido, es una redirección al servicio que maneja el segmento
     * de la URL que se encarga de los libros.
     *
     * @param recorridosId El ID del recorrido con respecto al cual se accede al
     * servicio.
     * @return El servicio de Libros para ese recorrido en paricular.
     */
    @Path("{recorridosId: \\d+}/paseos")
    public Class<PaseosRecorridoResource> getPaseosRecorridoResource(@PathParam("recorridosId") Long recorridosId) {
        if (recorridoLogic.getRecorrido(recorridosId) == null) {
            throw new WebApplicationException("El recurso /recorridos/" + recorridosId + " no existe.", 404);
        }
        return PaseosRecorridoResource.class;
    }

    /**
     * Actualiza el recorrido con el id recibido en la URL con la información que se
     * recibe en el cuerpo de la petición.
     *
     * @param recorridosId Identificador del recorrido que se desea actualizar. Este debe
     * ser una cadena de dígitos.
     * @param recorrido {@link RecorridoDTO} El recorrido que se desea guardar.
     * @return JSON {@link RecorridoDetailDTO} - El recorrido guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el recorrido a
     * actualizar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede actualizar el recorrido.
     */
    @PUT
    @Path("{recorridosId: \\d+}")
    public RecorridoDetailDTO updateRecorrido(@PathParam("recorridosId") Long recorridosId, RecorridoDetailDTO recorrido) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "RecorridoResource updateRecorrido: input: id: {0} , recorrido: {1}", new Object[]{recorridosId, recorrido});
        recorrido.setId(recorridosId);
        if (recorridoLogic.getRecorrido(recorridosId) == null) {
            throw new WebApplicationException("El recurso    /recorridos/" + recorridosId + " no existe     .", 404);
        }
        RecorridoDetailDTO detailDTO = new RecorridoDetailDTO(recorridoLogic.updateRecorrido(recorridosId, recorrido.toEntity()));
        LOGGER.log(Level.INFO, "RecorridoResource updateRecorrido: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Borra el recorrido con el id asociado recibido en la URL.
     *
     * @param recorridosId Identificador del recorrido que se desea borrar. Este debe ser
     * una cadena de dígitos.
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el recorrido.
     */
    @DELETE
    @Path("{recorridosId: \\d+}")
    public void deleteRecorrido(@PathParam("recorridosId") Long recorridosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "RecorridoResource deleteRecorrido: input: {0}", recorridosId);
        RecorridoEntity entity = recorridoLogic.getRecorrido(recorridosId);
        if (entity == null) {
            throw new WebApplicationException("El recurso/recorridos/" + recorridosId + " no existe", 404);
        }
        recorridoLogic.deleteRecorrido(recorridosId);
        LOGGER.info("RecorridoResource deleteRecorrido: output: void");
    }
    
    @GET
    public List<RecorridoDetailDTO> getPaseadores()
    {
        return listEntity2DetailDTO(recorridoLogic.findAll());
    }
    
    private List<RecorridoDetailDTO> listEntity2DetailDTO(List<RecorridoEntity> entityList) {
        List<RecorridoDetailDTO> list = new ArrayList<>();
        for(RecorridoEntity entity : entityList) {
            list.add(new RecorridoDetailDTO(entity));
        }
        return list;
    }

}
