/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;

import co.edu.uniandes.csw.paseadores.dtos.PaseoDTO;
import co.edu.uniandes.csw.paseadores.dtos.PaseoDetailDTO;
import co.edu.uniandes.csw.paseadores.ejb.PaseoLogic;
import co.edu.uniandes.csw.paseadores.entities.PaseoEntity;
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
 * @author Kevin Camilo Becerra Walteros
 */
@Path("paseos")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class PaseoResource {

    private static final Logger LOGGER = Logger.getLogger(PaseoResource.class.getName());

    @Inject
    private PaseoLogic paseoLogic;

    @POST
    public PaseoDTO createPaseo(PaseoDTO paseo) throws BusinessLogicException {

        LOGGER.log(Level.INFO, "PaseoResource createPaseo: input: {0}", paseo);
        PaseoDTO nuevoPaseoDTO = new PaseoDTO(paseoLogic.createPaseo(paseo.toEntity()));
        LOGGER.log(Level.INFO, "PaseoResource createPaseo: output: {0}", nuevoPaseoDTO);
        return nuevoPaseoDTO;
    }

    @GET
    @Path("{paseosId: \\d+}")
    public PaseoDetailDTO getPaseo(@PathParam("paseosId") Long paseosId) {
        LOGGER.log(Level.INFO, "PaseoResource getPaseo: input: {0}", paseosId);
        PaseoEntity paseoEntity = paseoLogic.getPaseo(paseosId);
        if (paseoEntity == null) {
            throw new WebApplicationException("El recurso /paseos/" + paseosId + " no existe.", 404);
        }
        PaseoDetailDTO paseoDetailDTO = new PaseoDetailDTO(paseoEntity);
        LOGGER.log(Level.INFO, "RecorridoResource getRecorrido: output: {0}", paseoDetailDTO);
        return paseoDetailDTO;
    }
    
    @GET
    public List<PaseoDetailDTO> getPaseos()
    {
        LOGGER.info("PaseoResource getPaseos: input: void");
        List<PaseoDetailDTO> listaPaseos = listEntity2DetailDTO(paseoLogic.getAllPaseos());
        LOGGER.log(Level.INFO, "PaseoResource getPaseos: output: {0}", listaPaseos);
        return listaPaseos;
    }

    /**
     * Actualiza el paseo con el id recibido en la URL con la información que se
     * recibe en el cuerpo de la petición.
     *
     * @param paseosId Identificador del paseo que se desea actualizar. Este debe
     * ser una cadena de dígitos.
     * @param paseo {@link PaseoDTO} El paseo que se desea guardar.
     * @return JSON {@link PaseoDetailDTO} - El paseo guardada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el paseo a
     * actualizar.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando no se puede actualizar el paseo.
     */
    @PUT
    @Path("{paseosId: \\d+}")
    public PaseoDetailDTO updatePaseo(@PathParam("paseosId") Long paseosId, PaseoDetailDTO paseo) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "PaseoResource updatePaseo: input: id: {0} , paseo: {1}", new Object[]{paseosId, paseo});
        paseo.setId(paseosId);
        if (paseoLogic.getPaseo(paseosId) == null) {
            throw new WebApplicationException("El recurso /paseos/" + paseosId + " no existe.", 404);
        }
        PaseoDetailDTO detailDTO = new PaseoDetailDTO(paseoLogic.updatePaseo(paseo.toEntity()));
        LOGGER.log(Level.INFO, "PaseoResource updatePaseo: output: {0}", detailDTO);
        return detailDTO;
    }
    
    @Path("{paseosId: \\d+}/recorrido")
    public Class<RecorridoPaseoResource> getRecorrido(@PathParam("paseosId") Long paseoId) {
        if (paseoLogic.getPaseo(paseoId) == null) {
            throw new WebApplicationException("El recurso /paseos/" + paseoId + " no existe.", 404);
        }
        return RecorridoPaseoResource.class;
    }
    
    @Path("{paseosId: \\d+}/paseador")
    public Class<PaseadorPaseoResource> getPaseador(@PathParam("paseosId") Long paseoId) {
        if (paseoLogic.getPaseo(paseoId) == null) {
            throw new WebApplicationException("El recurso /paseos/" + paseoId + " no existe.", 404);
        }
        return PaseadorPaseoResource.class;
    }
    
    @Path("{paseosId: \\d+}/pagoPaseador")
    public Class<PagoPaseadorPaseoResource> getPagoPaseador(@PathParam("paseosId") Long paseoId) {
        if (paseoLogic.getPaseo(paseoId) == null) {
            throw new WebApplicationException("El recurso /paseos/" + paseoId + " no existe.", 404);
        }
        return PagoPaseadorPaseoResource.class;
    }

    @DELETE
    @Path("{paseosId: \\d+}")
    public void deletePaseo(@PathParam("paseosId") Long paseosId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "PaseoResource deletePaseo: input: {0}", paseosId);
        PaseoEntity entity = paseoLogic.getPaseo(paseosId);
        if (entity == null) {
            throw new WebApplicationException("El recurso /paseos/" + paseosId + " no existe.", 404);
        }
        paseoLogic.deletePaseo(paseosId);
        LOGGER.info("PaseoResource deletePaseo: output: void");
    }

    /**
     * Convierte una lista de entidades a DTO.
     *
     * Este método convierte una lista de objetos PaseoEntity a una lista de
     * objetos PaseoDetailDTO (json)
     *
     * @param entityList corresponde a la lista de paseoes de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de paseoes en forma DTO (json)
     */
    private List<PaseoDetailDTO> listEntity2DetailDTO(List<PaseoEntity> entityList) {
        List<PaseoDetailDTO> list = new ArrayList<>();
        for (PaseoEntity entity : entityList) {
            list.add(new PaseoDetailDTO(entity));
        }
        return list;
    }

}
