/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;

import co.edu.uniandes.csw.paseadores.dtos.PerroDetailDTO;
import co.edu.uniandes.csw.paseadores.ejb.ContratoPaseoLogic;
import co.edu.uniandes.csw.paseadores.ejb.PerroContratoPaseoLogic;
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
public class PerroContratoPaseoResource {
    
    private static final Logger LOGGER = Logger.getLogger(PerroContratoPaseoResource.class.getName());
    
    @Inject
    private PerroLogic perroLogic;
    
    @Inject
    private ContratoPaseoLogic contratoPaseoLogic;
        
    @Inject
    private PerroContratoPaseoLogic perroContratoPaseoLogic;
    
    /**
     *
     * @param contratoPaseoId Identificador de la recorrido que se esta
     * actualizando. Este debe ser una cadena de dígitos.
     * @param perrosId Identificador del punto que se desea guardar. Este debe
     * ser una cadena de dígitos.
     * @return JSON {@link PuntoDTO} - El punto guardado en la recorrido.
     * @throws WebApplicationException {@link WebApplicationExceptionMapper} -
     * Error de lógica que se genera cuando no se encuentra el punto.
     */
    @POST
    @Path("{perrosId: \\d+}")
    public PerroDetailDTO addPerro( @PathParam("contratoPaseoId") Long contratoPaseoId, @PathParam("perrosId") Long perrosId) {
        
        LOGGER.log(Level.INFO, "-------------ENtro a resourcehotel");
        if (perroLogic.getPerro(perrosId) == null) 
        {
            LOGGER.log(Level.INFO, "-------------ENtro a resource Y MANDO EXCEPCION ");
            throw new WebApplicationException("El recurso /perro /" + perrosId + " no existe.", 404);
        }
        
        LOGGER.log(Level.INFO, "-------------ENtro a resource Y EL PERRO NO ES NULL");
        return new PerroDetailDTO(perroContratoPaseoLogic.addPerro(perrosId, contratoPaseoId));
    }
    
        /**
     * Busca y devuelve todos los perrros que existen en la contratoPaseo.
     *
     * @param contratoPaseoId Identificador de la contrato que se esta buscando.
     * Este debe ser una cadena de dígitos.
     * @return JSONArray {@link PuntoDTO} - Los puntos encontrados en la
     * recorrido. Si no hay ninguno retorna una lista vacía.
     */
    @GET
    public PerroDetailDTO getPerro(@PathParam("contratoPaseoId") Long contratoPaseoId) {
   
        LOGGER.log(Level.INFO, "-------------ENtro a resource Y EL PERRO FUE CREADO");
       PerroEntity perroEtity = perroContratoPaseoLogic.getPerro(contratoPaseoId);
        
        if(perroEtity==null)
        {
            throw new WebApplicationException(" El recurso / contrato Paseo /" + contratoPaseoId + " no existe. ", 404);
        }
        return new PerroDetailDTO(perroEtity);
    }
    
    
    /**
     * 
     * @param perroId
     * @param contratoPaseoId
     * @return 
     */
    @PUT
    @Path("{perroId: \\d+}")
    public PerroDetailDTO replacePerro(@PathParam("contratoPaseoId") Long contratoPaseoId, @PathParam("perroId") Long perroId ) {
        
        LOGGER.log(Level.INFO, "-------------ENtro a resource al put ");        
        if (perroLogic.getPerro(perroId) == null) {
            throw new WebApplicationException("El   recurso /perros /" + perroId + " no   existe.", 404);
        }           
        LOGGER.log(Level.INFO, "-------------ENtro a resource Y ninguno es null");
        return new PerroDetailDTO(perroContratoPaseoLogic.replacePerro(perroId, contratoPaseoId));
    }
    
    /**
     * 
     * @param contratoPaseoId 
     */
    @DELETE
    public void removeContratoPaseo(@PathParam("contratoPaseoId") Long contratoPaseoId) {
        
        perroContratoPaseoLogic.removeContratoPaseo(contratoPaseoId);
    }
    
}
    

