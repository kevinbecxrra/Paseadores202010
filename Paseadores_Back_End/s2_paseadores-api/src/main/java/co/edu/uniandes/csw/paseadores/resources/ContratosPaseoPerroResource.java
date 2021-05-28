/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;

import co.edu.uniandes.csw.paseadores.dtos.ContratoPaseoDTO;
import co.edu.uniandes.csw.paseadores.ejb.ContratoPaseoLogic;
import co.edu.uniandes.csw.paseadores.ejb.PerroContratoPaseoLogic;
import co.edu.uniandes.csw.paseadores.ejb.PerroLogic;
import co.edu.uniandes.csw.paseadores.entities.ContratoPaseoEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import java.util.logging.Logger;
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
 *
 * @author julian Oliveros<je.oliverosf at uniandes.edu.co>
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ContratosPaseoPerroResource 
{
    
    private static final Logger LOGGER = Logger.getLogger(ContratosPaseoPerroResource.class.getName());

    @Inject
    private PerroContratoPaseoLogic perroContratoPaseoLogic;

    @Inject
    private PerroLogic perroLogic;
    
    @Inject
    private ContratoPaseoLogic contratoPaseoLogic;
    
    
    /**
     * Asocia un contratoPaseo existente con un perro existente
     *
     * @param contratoPaseoId El ID del contratoPaseo que se va a asociar
     * @param IdPerro El ID del perro al cual se le va a asociar el contratoPaseo
     * @return JSON {@link ContratoPaseoDTO} - El contratoPaseo asociado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el contratoPaseo.
     */
    @POST
    @Path("{contratoPaseoId: \\d+}")
    public ContratoPaseoDTO addContratoPaseo (@PathParam("IdPerro") Long IdPerro, @PathParam("contratoPaseoId") Long contratoPaseoId) {
        
        LOGGER.log(Level.INFO, "ContratosPaseoPerroResource addContratoPaseo: input: perrosId {0} , contratoPaseoId {1}", new Object[]{IdPerro, contratoPaseoId});

        if (contratoPaseoLogic.getContratoPaseo(contratoPaseoId) == null) {
            throw new WebApplicationException("El recurso /contratospaseo/" + contratoPaseoId + " no existe.", 404);
        }
        
         LOGGER.log(Level.INFO, "2ContratosPaseoPerroResource addContratoPaseo : input: perrosId {0} , contratoPaseoId {1}", new Object[]{IdPerro, contratoPaseoId});

        
        if (perroLogic.getPerro(IdPerro) == null) {
            throw new WebApplicationException("El recurso /perro/" + IdPerro + " no existe.", 404);
        }
        LOGGER.log(Level.INFO, "3ContratosPaseoPerroResource addContratoPaseo: output: {0}");

        return new ContratoPaseoDTO(perroContratoPaseoLogic.addContratoPaseo(IdPerro, contratoPaseoId), false);
    }

    /**
     * Busca y devuelve todos los contratospaseo que existen en un perro.
     *
     * @param IdPerro El ID del perro del cual se buscan los contratospaseo
     * @return JSONArray {@link ContratoPaseoDTO} - Los contratospaseo encontrados en el
     * perro. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ContratoPaseoDTO> getContratosPaseo(@PathParam("IdPerro") Long IdPerro) {
        LOGGER.log(Level.INFO, "ContratosPaseoPerroResource getContratosPaseo: output: {0}");
        return contratosListEntity2DTO(perroContratoPaseoLogic.getContratosPaseo(IdPerro));
    }

    /**
     * Busca y devuelve el contrato con el ID recibido en la URL, relativo a un
     * perro.
     *
     * @param contratoPaseoId El ID del contrato que se busca
     * @param IdPerro El ID del perro del cual se busca el contrato
     * @return {@link ContratoPaseoDTO} - El contrato encontrado en el perro.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el contrato.
     */
    @GET
    @Path("{contratoPaseoId: \\d+}")
    public ContratoPaseoDTO getContratoPaseo(@PathParam("IdPerro") Long IdPerro, @PathParam("contratoPaseoId") Long contratoPaseoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ContratosPaseoPerroResource getContratoPaseo: input: IdPerro {0} , contratoPaseoId {1}", new Object[]{IdPerro, contratoPaseoId});
        if (contratoPaseoLogic.getContratoPaseo(contratoPaseoId) == null) {
            throw new WebApplicationException("El recurso /contratospaseo/" + contratoPaseoId + " no existe.", 404);
        }
        if (perroLogic.getPerro(IdPerro) == null) {
            throw new WebApplicationException("El recurso /perro/" + IdPerro + " no existe.", 404);
        }
        LOGGER.log(Level.INFO, "ContratosPaseoPerroResource getContratoPaseo: output: {0}");

        return new ContratoPaseoDTO(perroContratoPaseoLogic.getContratoPaseo(IdPerro, contratoPaseoId), false);
    }

    /**
     * Actualiza la lista de contratospaseo de un perro con la lista que se recibe en
     * el cuerpo.
     *
     * @param IdPerro El ID del perro al cual se le va a asociar la lista de
     * contratospaseo
     * @param contratospaseo JSONArray {@link ContratoPaseoDTO} - La lista de contratospaseo
     * que se desea guardar.
     * @return JSONArray {@link ContratoPaseoDTO} - La lista actualizada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el contrato.
     */
    @PUT
    public List<ContratoPaseoDTO> replaceContratoPaseo(@PathParam("IdPerro") Long IdPerro, List<ContratoPaseoDTO> contratospaseo) {
        LOGGER.log(Level.INFO, "ContratosPaseoPerroResource replaceContratosPaseo: input: perrosId {0} , contratospaseo {1}", new Object[]{IdPerro, contratospaseo});
        if (perroLogic.getPerro(IdPerro) == null) {
            throw new WebApplicationException("El recurso /perro/" + IdPerro + " no existe.", 404);
        }
        for (ContratoPaseoDTO contrato : contratospaseo) {
            if (contratoPaseoLogic.getContratoPaseo(contrato.getId()) == null) {
                throw new WebApplicationException("El recurso /contratospaseo/" + contrato.getId() + " no existe.", 404);
            }
        }
        LOGGER.log(Level.INFO, "ContratosPaseoPerroResource replaceContratosPaseo: output:{0}");
        return contratosListEntity2DTO(perroContratoPaseoLogic.replaceContratosPaseo(IdPerro, contratosListDTO2Entity(contratospaseo)));
    }

    /**
     * Elimina la conexión entre el contrato y el perro recibidos en la URL.
     *
     * @param IdPerro El ID del perro al cual se le va a desasociar el contrato
     * @param contratoPaseoId El ID del contrato que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el contrato.
     */
    @DELETE
    @Path("{contratoPaseoId: \\d+}")
    public void removeContratoPaseo(@PathParam("IdPerro") Long IdPerro, @PathParam("contratoPaseoId") Long contratoPaseoId) {
        LOGGER.log(Level.INFO, "ContratosPaseoPerroResource removeContratoPaseo: input: perrosId {0} , contratoPaseoId {1}", new Object[]{IdPerro, contratoPaseoId});
        if (contratoPaseoLogic.getContratoPaseo(contratoPaseoId) == null) {
            throw new WebApplicationException("El recurso /contratospaseo/" + contratoPaseoId + " no existe.", 404);
        }
        if (perroLogic.getPerro(IdPerro) == null) {
            throw new WebApplicationException("El recurso /perro/" + IdPerro + " no existe.", 404);
        }
        perroContratoPaseoLogic.removeContratoPaseo(contratoPaseoId);
        
        LOGGER.info("ContratosPaseoPerroResource removeContratoPaseo: output: void");
    }

    /**
     * Convierte una lista de ContratoPaseoEntity a una lista de ContratoPaseoDTO.
     *
     * @param entityList Lista de ContratoPaseoEntity a convertir.
     * @return Lista de ContratoPaseoDTO convertida.
     */
    private List<ContratoPaseoDTO> contratosListEntity2DTO(List<ContratoPaseoEntity> entityList) {
        List<ContratoPaseoDTO> list = new ArrayList<>();
        for (ContratoPaseoEntity entity : entityList) {
            list.add(new ContratoPaseoDTO(entity, false));
        }
        return list;
    }

    /**
     * Convierte una lista de ContratoPaseoDTO a una lista de ContratoPaseoEntity.
     *
     * @param dtos Lista de ContratoPaseoDTO a convertir.
     * @return Lista de ContratoPaseoEntity convertida.
     */
    private List<ContratoPaseoEntity> contratosListDTO2Entity(List<ContratoPaseoDTO> dtos) {
        List<ContratoPaseoEntity> list = new ArrayList<>();
        for (ContratoPaseoDTO dto : dtos) {
            list.add(dto.toEntity(false));
        }
        return list;
    }
}

