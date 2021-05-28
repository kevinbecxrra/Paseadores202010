/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;


import co.edu.uniandes.csw.paseadores.dtos.ContratoHotelDetailDTO;
import co.edu.uniandes.csw.paseadores.ejb.ContratoHotelLogic;
import co.edu.uniandes.csw.paseadores.ejb.PerroContratoHotelLogic;
import co.edu.uniandes.csw.paseadores.ejb.PerroLogic;
import co.edu.uniandes.csw.paseadores.entities.ContratoHotelEntity;
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
 * 
 * Clase que implementa el recurso "perros/{id}/contratospaseo".
 * 
 * @author julian Oliveros<je.oliverosf at uniandes.edu.co>
 */
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ContratosHotelPerroResource {
    private static final Logger LOGGER = Logger.getLogger(PuntosRecorridoResource.class.getName());

    @Inject
    private PerroContratoHotelLogic perroContratoHotelLogic;

    @Inject
    private PerroLogic perroLogic;
    
    @Inject
    private ContratoHotelLogic contratoHotelLogic;

   

    /**
     * Asocia un contratoHotel existente con un perro existente
     *
     * @param contratoshotelId El ID del contratoHotel que se va a asociar
     * @param IdPerro El ID del perro al cual se le va a asociar el contratoHotel
     * @return JSON {@link ContratoHotelDetailDTO} - El contratoHotel asociado.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el contratoHotel.
     */
    @POST
    @Path("{contratoshotelId: \\d+}")
    public ContratoHotelDetailDTO addContratoHotel (@PathParam("IdPerro") Long IdPerro, @PathParam("contratoshotelId") Long contratoshotelId) {
        
        LOGGER.log(Level.INFO, "ContratosPaseoPerroResource addContratoHotel: input: IdPerro {0} , contratoshotelId {1}", new Object[]{IdPerro, contratoshotelId});

        if (contratoHotelLogic.getContratoHotel(contratoshotelId) == null) {
            throw new WebApplicationException("El recurso /contratoshotel/" + contratoshotelId + " no existe.", 404);
        }
        if (perroLogic.getPerro(IdPerro) == null) {
            throw new WebApplicationException("El recurso /perro/" + IdPerro + " no existe.", 404);
        }
        LOGGER.log(Level.INFO, "ContratosPaseoPerroResource addContratoHotel: output: {0}");

        return new ContratoHotelDetailDTO(perroContratoHotelLogic.addContratoHotel(IdPerro, contratoshotelId));
    }

    /**
     * Busca y devuelve todos los contratoshotel que existen en un perro.
     *
     * @param IdPerro El ID del perro del cual se buscan los contratoshotel
     * @return JSONArray {@link ContratoHotelDetailDTO} - Los contratoshotel encontrados en el
     * perro. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public List<ContratoHotelDetailDTO> getContratosHotel(@PathParam("IdPerro") Long IdPerro) {
        LOGGER.log(Level.INFO, "ContratosPaseoPerroResource getContratosHotel: output: {0}");
        return contratosListEntity2DTO(perroContratoHotelLogic.getContratosHotel(IdPerro));
    }

    /**
     * Busca y devuelve el contratohotel con el ID recibido en la URL, relativo a un
     * perro.
     *
     * @param contratoshotelId El ID del contratohotel que se busca
     * @param IdPerro El ID del perro del cual se busca el contratohotel
     * @return {@link ContratoHotelDetailDTO} - El contratohotel encontrado en el perro.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el contratohotel.
     */
    @GET
    @Path("{contratoshotelId: \\d+}")
    public ContratoHotelDetailDTO getContratoHotel(@PathParam("IdPerro") Long IdPerro, @PathParam("contratoshotelId") Long contratoshotelId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ContratosPaseoPerroResource getContratoHotel: input: IdPerro {0} , contratoshotelId {1}", new Object[]{IdPerro, contratoshotelId});
        if (contratoHotelLogic.getContratoHotel(contratoshotelId) == null) {
            throw new WebApplicationException("El recurso /contratoshotel/" + contratoshotelId + " no existe.", 404);
        }
        if (perroLogic.getPerro(IdPerro) == null) {
            throw new WebApplicationException("El recurso /perro/" + IdPerro + " no existe.", 404);
        }
        LOGGER.log(Level.INFO, "ContratosPaseoPerroResource getContratoHotel: output: {0}");

        return new ContratoHotelDetailDTO(perroContratoHotelLogic.getContratoHotel(IdPerro, contratoshotelId));
    }

    /**
     * Actualiza la lista de contratoshotel de un perro con la lista que se recibe en
     * el cuerpo.
     *
     * @param IdPerro El ID del perro al cual se le va a asociar la lista de
     * contratoshotel
     * @param contratoshotel JSONArray {@link ContratoHotelDetailDTO} - La lista de contratoshotel
     * que se desea guardar.
     * @return JSONArray {@link ContratoHotelDetailDTO} - La lista actualizada.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el contratohotel.
     */
    @PUT
    public List<ContratoHotelDetailDTO> replaceContratosPaseo(@PathParam("IdPerro") Long IdPerro, List<ContratoHotelDetailDTO> contratoshotel) {
        LOGGER.log(Level.INFO, "ContratosPaseoPerroResource replaceContratosPaseo: input: IdPerro {0} , contratoshotel {1}", new Object[]{IdPerro, contratoshotel});
        if (perroLogic.getPerro(IdPerro) == null) {
            throw new WebApplicationException("El recurso /perro/" + IdPerro + " no existe.", 404);
        }
        LOGGER.log(Level.INFO, "2 ContratosPaseoPerroResource replaceContratosPaseo: input: IdPerro {0} , contratoshotel {1}", new Object[]{IdPerro, contratoshotel});
   
        for (ContratoHotelDetailDTO contrato : contratoshotel) {
            if (contratoHotelLogic.getContratoHotel(contrato.getId()) == null) {
                throw new WebApplicationException("El recurso /contratoshotel/" + contrato.getId() + " no existe.", 404);
            }
        }
        
        List<ContratoHotelDetailDTO> lista = contratosListEntity2DTO(perroContratoHotelLogic.replaceContratosHotel(IdPerro, contratosListDTO2Entity(contratoshotel)));
        
        LOGGER.log(Level.INFO, "ContratosPaseoPerroResource replaceContratosPaseo: output:{0}");
        return lista;
    }

    /**
     * Elimina la conexión entre el contratohotel y el perro recibidos en la URL.
     *
     * @param IdPerro El ID del perro al cual se le va a desasociar el contratohotel
     * @param contratoshotelId El ID del contratohotel que se desasocia
     * @throws WebApplicationException {@link WebApplicationExceptionMapper}
     * Error de lógica que se genera cuando no se encuentra el contratohotel.
     */
    @DELETE
    @Path("{contratoshotelId: \\d+}")
    public void removeContratoHotel(@PathParam("IdPerro") Long IdPerro, @PathParam("contratoshotelId") Long contratoshotelId) {
        LOGGER.log(Level.INFO, "ContratosPaseoPerroResource removeContratoHotel: input: IdPerro {0} , contratoshotelId {1}", new Object[]{IdPerro, contratoshotelId});
        if (contratoHotelLogic.getContratoHotel(contratoshotelId) == null) {
            throw new WebApplicationException("El recurso /contratoshotel/" + contratoshotelId + " no existe.", 404);
        }
        if (perroLogic.getPerro(IdPerro) == null) {
            throw new WebApplicationException("El recurso /perro/" + IdPerro + " no existe.", 404);
        }
        perroContratoHotelLogic.removeContratoHotel(contratoshotelId);
        
        LOGGER.info("ContratosPaseoPerroResource removeContratoHotel: output: void");
    }

    /**
     * Convierte una lista de ContratoHotelEntity a una lista de ContratoHotelDetailDTO.
     *
     * @param entityList Lista de ContratoHotelEntity a convertir.
     * @return Lista de ContratoHotelDetailDTO convertida.
     */
    private List<ContratoHotelDetailDTO> contratosListEntity2DTO(List<ContratoHotelEntity> entityList) {
        List<ContratoHotelDetailDTO> list = new ArrayList<>();
        for (ContratoHotelEntity entity : entityList) {
            list.add(new ContratoHotelDetailDTO(entity));
        }
        return list;
    }

    /**
     * Convierte una lista de ContratoHotelDetailDTO a una lista de ContratoHotelEntity.
     *
     * @param dtos Lista de ContratoHotelDetailDTO a convertir.
     * @return Lista de ContratoHotelEntity convertida.
     */
    private List<ContratoHotelEntity> contratosListDTO2Entity(List<ContratoHotelDetailDTO> dtos) {
        List<ContratoHotelEntity> list = new ArrayList<>();
        for (ContratoHotelDetailDTO dto : dtos) {
            list.add(dto.toEntity(false));
        }
        return list;
    }
}
    
