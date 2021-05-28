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

import co.edu.uniandes.csw.paseadores.dtos.PaseoDetailDTO;
import co.edu.uniandes.csw.paseadores.ejb.PaseoLogic;
import co.edu.uniandes.csw.paseadores.ejb.RecorridoLogic;
import co.edu.uniandes.csw.paseadores.ejb.RecorridoPaseoLogic;
import co.edu.uniandes.csw.paseadores.entities.PaseoEntity;
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
 * Clase que implementa el recurso "recorridos/{id}/paseos".
 *
 * @paseo ISIS2603
 * @version 1.0
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PaseosRecorridoResource {

    private static final Logger LOGGER = Logger.getLogger(PaseosRecorridoResource.class.getName());

    @Inject
    private RecorridoPaseoLogic recorridoPaseoLogic;

    @Inject
    private PaseoLogic paseoLogic;
    
    @Inject
    private RecorridoLogic recorridoLogic;

    /**
     * Asocia un paseo existente con un recorrido existente
     *
     * @param paseosId El ID del paseo que se va a asociar
     * @param recorridosId El ID del recorrido al cual se le va a asociar el paseo
     * @return JSON {@link PaseoDetailDTO} - El paseo asociado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el paseo.
     */
    @POST
    @Path("{paseosId: \\d+}")
    public PaseoDetailDTO addPaseo(@PathParam("recorridosId") Long recorridosId, @PathParam("paseosId") Long paseosId) {
        LOGGER.log(Level.INFO, "PaseosRecorridoResource addPaseo: input: recorridosId {0} , paseosId {1}", new Object[]{recorridosId, paseosId});
        if (paseoLogic.getPaseo(paseosId) == null) {
            throw new WebApplicationException("El recurso /paseos/" + paseosId + " no existe.", 404);
        }
        if (recorridoLogic.getRecorrido(recorridosId) == null) {
            throw new WebApplicationException("El recurso /recorrido/" + recorridosId + " no existe.", 404);
        }
        PaseoDetailDTO detailDTO = new PaseoDetailDTO(recorridoPaseoLogic.addPaseo(recorridosId, paseosId));
        LOGGER.log(Level.INFO, "PaseosRecorridoResource addPaseo: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Busca y devuelve todos los paseos que existen en un recorrido.
     *
     * @param recorridosId El ID del recorrido del cual se buscan los paseos
     * @return JSONArray {@link PaseoDetailDTO} - Los paseos encontrados en el
     * recorrido. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<PaseoDetailDTO> getPaseos(@PathParam("recorridosId") Long recorridosId) {
        LOGGER.log(Level.INFO, "PaseosRecorridoResource getPaseos: input: {0}", recorridosId);
        List<PaseoDetailDTO> lista = paseosListEntity2DTO(recorridoPaseoLogic.getPaseos(recorridosId));
        LOGGER.log(Level.INFO, "PaseosRecorridoResource getPaseos: output: {0}", lista);
        return lista;
    }

    /**
     * Busca y devuelve el paseo con el ID recibido en la URL, relativo a un
     * recorrido.
     *
     * @param paseosId El ID del paseo que se busca
     * @param recorridosId El ID del recorrido del cual se busca el paseo
     * @return {@link PaseoDetailDTO} - El paseo encontrado en el recorrido.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el paseo.
     */
    @GET
    @Path("{paseosId: \\d+}")
    public PaseoDetailDTO getPaseo(@PathParam("recorridosId") Long recorridosId, @PathParam("paseosId") Long paseosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "PaseosRecorridoResource getPaseo: input: recorridosId {0} , paseosId {1}", new Object[]{recorridosId, paseosId});
        if (paseoLogic.getPaseo(paseosId) == null) {
            throw new WebApplicationException("El recurso /paseos/" + paseosId + " no existe.", 404);
        }
        if (recorridoLogic.getRecorrido(recorridosId) == null) {
            throw new WebApplicationException("El recurso /recorrido/" + recorridosId + " no existe.", 404);
        }
        PaseoDetailDTO detailDTO = new PaseoDetailDTO(recorridoPaseoLogic.getPaseo(recorridosId, paseosId));
        LOGGER.log(Level.INFO, "PaseosRecorridoResource getPaseo: output: {0}", detailDTO);
        return detailDTO;
    }

    /**
     * Actualiza la lista de paseos de un recorrido con la lista que se recibe en
     * el cuerpo.
     *
     * @param recorridosId El ID del recorrido al cual se le va a asociar la lista de
     * paseos
     * @param paseos JSONArray {@link PaseoDetailDTO} - La lista de paseos
     * que se desea guardar.
     * @return JSONArray {@link PaseoDetailDTO} - La lista actualizada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el paseo.
     */
    @PUT
    public List<PaseoDetailDTO> replacePaseos(@PathParam("recorridosId") Long recorridosId, List<PaseoDetailDTO> paseos) {
        LOGGER.log(Level.INFO, "PaseosRecorridoResource replacePaseos: input: recorridosId {0} , paseos {1}", new Object[]{recorridosId, paseos});
        if (recorridoLogic.getRecorrido(recorridosId) == null) {
            throw new WebApplicationException("El recurso /recorrido/" + recorridosId + " no existe.", 404);
        }
        for (PaseoDetailDTO paseo : paseos) {
            if (paseoLogic.getPaseo(paseo.getId()) == null) {
                throw new WebApplicationException("El recurso /paseos/" + paseo.getId() + " no existe.", 404);
            }
        }
        List<PaseoDetailDTO> lista = paseosListEntity2DTO(recorridoPaseoLogic.replacePaseos(recorridosId, paseosListDTO2Entity(paseos)));
        LOGGER.log(Level.INFO, "PaseosRecorridoResource replacePaseos: output:{0}", lista);
        return lista;
    }

    /**
     * Elimina la conexión entre el paseo y el recorrido recibidos en la URL.
     *
     * @param recorridosId El ID del recorrido al cual se le va a desasociar el paseo
     * @param paseosId El ID del paseo que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el paseo.
     */
    @DELETE
    @Path("{paseosId: \\d+}")
    public void removePaseo(@PathParam("recorridosId") Long recorridosId, @PathParam("paseosId") Long paseosId) {
        LOGGER.log(Level.INFO, "PaseosRecorridoResource removePaseo: input: recorridosId {0} , paseosId {1}", new Object[]{recorridosId, paseosId});
        if (paseoLogic.getPaseo(paseosId) == null) {
            throw new WebApplicationException("El recurso /paseos/" + paseosId + " no existe.", 404);
        }
        if (recorridoLogic.getRecorrido(recorridosId) == null) {
            throw new WebApplicationException("El recurso /recorrido/" + recorridosId + " no existe.", 404);
        }
        recorridoPaseoLogic.removePaseo(paseosId);
        LOGGER.info("PaseosRecorridoResource removePaseo: output: void");
    }

    /**
     * Convierte una lista de PaseoEntity a una lista de PaseoDetailDTO.
     *
     * @param entityList Lista de PaseoEntity a convertir.
     * @return Lista de PaseoDetailDTO convertida.
     */
    private List<PaseoDetailDTO> paseosListEntity2DTO(List<PaseoEntity> entityList) {
        List<PaseoDetailDTO> list = new ArrayList<>();
        for (PaseoEntity entity : entityList) {
            list.add(new PaseoDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de PaseoDetailDTO a una lista de PaseoEntity.
     *
     * @param dtos Lista de PaseoDetailDTO a convertir.
     * @return Lista de PaseoEntity convertida.
     */
    private List<PaseoEntity> paseosListDTO2Entity(List<PaseoDetailDTO> dtos) {
        List<PaseoEntity> list = new ArrayList<>();
        for (PaseoDetailDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
}
