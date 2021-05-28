package co.edu.uniandes.csw.paseadores.resources;

/*
MIT License

Copyright (c) 2017 Universidad de los Andes - ISIS2603

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
 */

import co.edu.uniandes.csw.paseadores.dtos.PuntoDTO;
import co.edu.uniandes.csw.paseadores.ejb.PuntoLogic;
import co.edu.uniandes.csw.paseadores.ejb.RecorridoLogic;
import co.edu.uniandes.csw.paseadores.ejb.RecorridoPuntoLogic;
import co.edu.uniandes.csw.paseadores.entities.PuntoEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.ws.rs.WebApplicationException;

/**
 * Clase que implementa el recurso "recorridos/{id}/puntos".
 *
 * @punto ISIS2603
 * @version 1.0
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PuntosRecorridoResource {

    private static final Logger LOGGER = Logger.getLogger(PuntosRecorridoResource.class.getName());

    @Inject
    private RecorridoPuntoLogic recorridoPuntoLogic;

    @Inject
    private PuntoLogic puntoLogic;
    
    @Inject
    private RecorridoLogic recorridoLogic;

    /**
     * Asocia un punto existente con un recorrido existente
     *
     * @param puntosId El ID del punto que se va a asociar
     * @param recorridosId El ID del recorrido al cual se le va a asociar el punto
     * @return JSON {@link PuntoDTO} - El punto asociado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el punto.
     */
    @POST
    @Path("{puntosId: \\d+}")
    public PuntoDTO addPunto(@PathParam("recorridosId") Long recorridosId, @PathParam("puntosId") Long puntosId) {
        LOGGER.log(Level.INFO, "PuntosRecorridoResource addPunto: input: recorridosId {0} , puntosId {1}", new Object[]{recorridosId, puntosId});
        if (puntoLogic.getPunto(puntosId) == null) {
            throw new WebApplicationException("El recurso /puntos/" + puntosId + " no existe.", 404);
        }
        if (recorridoLogic.getRecorrido(recorridosId) == null) {
            throw new WebApplicationException("El recurso /recorrido/" + recorridosId + " no existe.", 404);
        }
        PuntoDTO detailDTO = new PuntoDTO(recorridoPuntoLogic.addPunto(recorridosId, puntosId));
        LOGGER.log(Level.INFO, "PuntosRecorridoResource addPunto: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Busca y devuelve todos los puntos que existen en un recorrido.
     *
     * @param recorridosId El ID del recorrido del cual se buscan los puntos
     * @return JSONArray {@link PuntoDTO} - Los puntos encontrados en el
     * recorrido. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<PuntoDTO> getPuntos(@PathParam("recorridosId") Long recorridosId) {
        LOGGER.log(Level.INFO, "PuntosRecorridoResource getPuntos: input: {0}", recorridosId);
        List<PuntoDTO> lista = puntosListEntity2DTO(recorridoPuntoLogic.getPuntos(recorridosId));
        LOGGER.log(Level.INFO, "PuntosRecorridoResource getPuntos: output: {0}", lista);
        return lista;
    }

    /**
     * Busca y devuelve el punto con el ID recibido en la URL, relativo a un
     * recorrido.
     *
     * @param puntosId El ID del punto que se busca
     * @param recorridosId El ID del recorrido del cual se busca el punto
     * @return {@link PuntoDTO} - El punto encontrado en el recorrido.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el punto.
     */
    @GET
    @Path("{puntosId: \\d+}")
    public PuntoDTO getPunto(@PathParam("recorridosId") Long recorridosId, @PathParam("puntosId") Long puntosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "PuntosRecorridoResource getPunto: input: recorridosId {0} , puntosId {1}", new Object[]{recorridosId, puntosId});
        if (puntoLogic.getPunto(puntosId) == null) {
            throw new WebApplicationException("El recurso /puntos/" + puntosId + " no existe.", 404);
        }
        if (recorridoLogic.getRecorrido(recorridosId) == null) {
            throw new WebApplicationException("El recurso /recorrido/" + recorridosId + " no existe.", 404);
        }
        PuntoDTO detailDTO = new PuntoDTO(recorridoPuntoLogic.getPunto(recorridosId, puntosId));
        LOGGER.log(Level.INFO, "PuntosRecorridoResource getPunto: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza la lista de puntos de un recorrido con la lista que se recibe en
     * el cuerpo.
     *
     * @param recorridosId El ID del recorrido al cual se le va a asociar la lista de
     * puntos
     * @param puntos JSONArray {@link PuntoDTO} - La lista de puntos
     * que se desea guardar.
     * @return JSONArray {@link PuntoDTO} - La lista actualizada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el punto.
     */
    @PUT
    public List<PuntoDTO> replacePuntos(@PathParam("recorridosId") Long recorridosId, List<PuntoDTO> puntos) {
        LOGGER.log(Level.INFO, "PuntosRecorridoResource replacePuntos: input: recorridosId {0} , puntos {1}", new Object[]{recorridosId, puntos});
        if (recorridoLogic.getRecorrido(recorridosId) == null) {
            throw new WebApplicationException("El recurso /recorrido/" + recorridosId + " no existe.", 404);
        }
        for (PuntoDTO punto : puntos) {
            if (puntoLogic.getPunto(punto.getId()) == null) {
                throw new WebApplicationException("El recurso /puntos/" + punto.getId() + " no existe.", 404);
            }
        }
        List<PuntoDTO> lista = puntosListEntity2DTO(recorridoPuntoLogic.replacePuntos(recorridosId, puntosListDTO2Entity(puntos)));
        LOGGER.log(Level.INFO, "PuntosRecorridoResource replacePuntos: output:{0}", lista);
        return lista;
    }

    /**
     * Elimina la conexión entre el punto y el recorrido recibidos en la URL.
     *
     * @param recorridosId El ID del recorrido al cual se le va a desasociar el punto
     * @param puntosId El ID del punto que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el punto.
     */
    @DELETE
    @Path("{puntosId: \\d+}")
    public void removePunto(@PathParam("recorridosId") Long recorridosId, @PathParam("puntosId") Long puntosId) {
        LOGGER.log(Level.INFO, "PuntosRecorridoResource removePunto: input: recorridosId {0} , puntosId {1}", new Object[]{recorridosId, puntosId});
        if (puntoLogic.getPunto(puntosId) == null) {
            throw new WebApplicationException("El recurso /puntos/" + puntosId + " no existe.", 404);
        }
        if (recorridoLogic.getRecorrido(recorridosId) == null) {
            throw new WebApplicationException("El recurso /recorrido/" + recorridosId + " no existe.", 404);
        }
        recorridoPuntoLogic.removePunto(puntosId);
        LOGGER.info("PuntosRecorridoResource removePunto: output: void");
    }

    /**
     * Convierte una lista de PuntoEntity a una lista de PuntoDTO.
     *
     * @param entityList Lista de PuntoEntity a convertir.
     * @return Lista de PuntoDTO convertida.
     */
    private List<PuntoDTO> puntosListEntity2DTO(List<PuntoEntity> entityList) {
        List<PuntoDTO> list = new ArrayList<>();
        for (PuntoEntity entity : entityList) {
            list.add(new PuntoDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de PuntoDTO a una lista de PuntoEntity.
     *
     * @param dtos Lista de PuntoDTO a convertir.
     * @return Lista de PuntoEntity convertida.
     */
    private List<PuntoEntity> puntosListDTO2Entity(List<PuntoDTO> dtos) {
        List<PuntoEntity> list = new ArrayList<>();
        for (PuntoDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
