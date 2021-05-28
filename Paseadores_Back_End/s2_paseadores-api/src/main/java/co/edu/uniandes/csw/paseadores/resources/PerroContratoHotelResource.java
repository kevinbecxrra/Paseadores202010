/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;


import co.edu.uniandes.csw.paseadores.dtos.PerroDetailDTO;
import co.edu.uniandes.csw.paseadores.ejb.ContratoHotelLogic;
import co.edu.uniandes.csw.paseadores.ejb.PerroContratoHotelLogic;
import co.edu.uniandes.csw.paseadores.ejb.PerroLogic;
import co.edu.uniandes.csw.paseadores.entities.PerroEntity;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author julian Oliveros<je.oliverosf at uniandes.edu.co>
 */
@Consumes("application/json")
@Produces("application/json")
public class PerroContratoHotelResource {
    
    private static final Logger LOGGER = Logger.getLogger(PerroContratoHotelResource.class.getName());
    
    @Inject
    private PerroLogic perroLogic;
    
    @Inject
    private ContratoHotelLogic contratoHotelLogic;
        
    @Inject
    private PerroContratoHotelLogic perroContratoHotelLogic;
    
    /**
     *
     * @param contratoshotelId Identificador de la recorrido que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param perrosId Identificador del punto que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link PuntoDTO} - El punto guardado en la recorrido.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el punto.
     */
    @POST
    @Path("{perrosId: \\d+}")
    public PerroDetailDTO addPerro( @PathParam("contratoshotelId") Long contratoshotelId, @PathParam("perrosId") Long perrosId) {
       
        LOGGER.log(Level.INFO, "-------------ENtro a resource");
        if (perroLogic.getPerro(perrosId) == null) {
            
            LOGGER.log(Level.INFO, "-------------ENtro a resource Y MANDO EXCEPCION de perro ");
            throw new WebApplicationException("El recurso /perro /" + perrosId + " no existe.", 404);
        }
        if(contratoHotelLogic.getContratoHotel(contratoshotelId)==null)
        {
            LOGGER.log(Level.INFO, "-------------ENtro a resource Y MANDO EXCEPCION de contrato ");
            throw new WebApplicationException("El recurso /perro /" + contratoshotelId + " no existe.", 404);
        }
        
        LOGGER.log(Level.INFO, "-------------ENtro a resource Y si existe el perro y contrato ");
        return new PerroDetailDTO(perroContratoHotelLogic.addPerro(contratoshotelId,perrosId ));
    }
    
        /**
     * Busca y devuelve todos los perrros que existen en la contratoPaseo.
     *
     * @param contratoshotelId Identificador de la recorrido que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link PuntoDTO} - Los puntos encontrados en la
     * recorrido. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public PerroDetailDTO getPerro(@PathParam("contratoshotelId") Long contratoshotelId) {
   
        PerroEntity perroEtity = perroContratoHotelLogic.getPerro(contratoshotelId);        
        if(perroEtity==null)
        {
            throw new WebApplicationException(" El recurso /contratoshotelId /" + contratoshotelId + " no existe. ", 404);
        }
        return new PerroDetailDTO(perroEtity);
    }
    
    
    /**
     * 
     * @param perroID
     * @param contratoshotelId
     * @return 
     */
    @PUT
    @Path("{perroID: \\d+}")
    public PerroDetailDTO replacePerro(@PathParam("perroID") Long perroID, @PathParam("contratoshotelId") Long contratoshotelId) {
        
        LOGGER.log(Level.INFO, "-------------ENtro a resource put");
        
        if (perroLogic.getPerro(perroID) == null) {
            throw new WebApplicationException("El   recurso /perros /" + perroID + " no   existe.", 404);
        }
        
        LOGGER.log(Level.INFO, "-------------ENtro a resource y no manda excepcion");
        return new PerroDetailDTO(perroContratoHotelLogic.replacePerro(perroID, contratoshotelId));
    }
    
    /**
     * 
     * @param contratoHotelId 
     */
    @DELETE
    public void removeContratoHotel(@PathParam("contratoHotelId") Long contratoHotelId) {
        perroContratoHotelLogic.removeContratoHotel(contratoHotelId);
    }
}
