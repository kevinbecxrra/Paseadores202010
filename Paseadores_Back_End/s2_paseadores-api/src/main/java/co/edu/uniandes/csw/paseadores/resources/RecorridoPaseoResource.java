/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;

import co.edu.uniandes.csw.paseadores.dtos.RecorridoDetailDTO;
import co.edu.uniandes.csw.paseadores.ejb.PaseoLogic;
import co.edu.uniandes.csw.paseadores.ejb.RecorridoLogic;
import co.edu.uniandes.csw.paseadores.ejb.RecorridoPaseoLogic;
import co.edu.uniandes.csw.paseadores.entities.RecorridoEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
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
 * @author Nicolas Urrego Sandoval <n.urrego at uniandes.edu.co>
 */
@Path("paseos/{paseosId: \\d+}/recorrido")
@Produces("application/json")
@Consumes("application/json")
public class RecorridoPaseoResource {
    
    private static final Logger LOGGER = Logger.getLogger(RecorridoPaseoResource.class.getName());
    
    @Inject
    private RecorridoPaseoLogic recorridoPaseoLogic;

    @Inject
    private RecorridoLogic recorridoLogic;
    
    @Inject
    private PaseoLogic paseoLogic;
    
    @POST
    @Path("{recorridosId: \\d+}")
    public RecorridoDetailDTO addRecorrido(@PathParam("paseosId") Long paseosId, @PathParam("recorridosId") Long recorridosId) {
        if (recorridoLogic.getRecorrido(recorridosId) == null) {
            throw new WebApplicationException("El recurso /recorridos/" + recorridosId + " no existe.", 404);
        }
        if(paseoLogic.getPaseo(paseosId) == null)
            throw new WebApplicationException("El recurso /paseos/" + paseosId + " no existe.", 404);
        return new RecorridoDetailDTO(recorridoPaseoLogic.addRecorrido(recorridosId, paseosId));
    }
    
    @GET
    public RecorridoDetailDTO getRecorrido(@PathParam("paseosId") Long paseosId) {
        LOGGER.log(Level.INFO, "getRecorrido en RecorridoPaseoResource");
        if(paseoLogic.getPaseo(paseosId) == null)
            throw new WebApplicationException(" El recurso  /paseos/" + paseosId + " no existe. ", 404);
        RecorridoEntity recorridoEntity = recorridoPaseoLogic.getRecorrido(paseosId);
        if (recorridoEntity == null) {
            throw new WebApplicationException(" El recurso /paseos/" + paseosId + "/recorrido no existe.", 404);
        }
        LOGGER.log(Level.INFO, "getRecorrido en RecorridoPaseoResource antes de hacer el json");
        return new RecorridoDetailDTO(recorridoEntity);
    }
    
    @PUT
    @Path("{recorridosId: \\d+}")
    public RecorridoDetailDTO replaceRecorrido(@PathParam("paseosId") Long paseosId, @PathParam("recorridosId") Long recorridosId) {
        if(paseoLogic.getPaseo(paseosId) == null)
            throw new WebApplicationException(" El  recurso  /paseos/" + paseosId + "  no existe.", 404);
        if (recorridoLogic.getRecorrido(recorridosId) == null) {
            throw new WebApplicationException("El  recurso  /recorridos/" + recorridosId + "  no existe. ", 404);
        }
        return new RecorridoDetailDTO(recorridoPaseoLogic.replaceRecorrido(paseosId, recorridosId));
    }
    
    @DELETE
    public void removePaseo(@PathParam("paseosId") Long paseosId) throws BusinessLogicException {
        if(paseoLogic.getPaseo(paseosId) == null)
            throw new WebApplicationException("El  recurso  /paseos/" + paseosId + " no existe.   ", 404);
        recorridoPaseoLogic.removePaseo(paseosId);
    }
}
