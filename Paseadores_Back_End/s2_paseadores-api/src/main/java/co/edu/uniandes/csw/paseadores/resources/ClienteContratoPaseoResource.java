/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;

import co.edu.uniandes.csw.paseadores.dtos.ClienteDTO;
import co.edu.uniandes.csw.paseadores.dtos.ClienteDetailDTO;
import co.edu.uniandes.csw.paseadores.ejb.ClienteContratoPaseoLogic;
import co.edu.uniandes.csw.paseadores.ejb.ClienteLogic;
import co.edu.uniandes.csw.paseadores.ejb.ContratoPaseoLogic;
import co.edu.uniandes.csw.paseadores.entities.ClienteEntity;
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
import javax.ws.rs.core.MediaType;



/**
 *
 * @author Alvaro Plata
 */
@Path("contratosPaseo/{contratoPaseoId: \\d+}/cliente")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteContratoPaseoResource {
    
    private static final Logger LOGGER = Logger.getLogger(ClienteContratoPaseoResource.class.getName());
    
    @Inject
    private ClienteContratoPaseoLogic clienteContratoPaseoLogic; 
    
    @Inject
    private ClienteLogic clienteLogic; 
    
    @Inject
    private ContratoPaseoLogic contratoPaseoLogic;
    
    
    @POST
    @Path("{clienteId: \\d+}")
    public ClienteDTO addCliente(@PathParam("contratoPaseoId") Long contratoPaseoId, @PathParam("clienteId") Long clienteId) {
        LOGGER.log(Level.INFO, "ClienteContratoPaseoResource addCliente: input: contratoPaseoId: {0} , clienteId: {1}", new Object[]{contratoPaseoId, clienteId});
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + " no existe.", 404);
        }
        if (contratoPaseoLogic.getContratoPaseo(contratoPaseoId) == null) {
            throw new WebApplicationException("El recurso /contratos/" + contratoPaseoId + " no existe.", 404);
        }
        clienteContratoPaseoLogic.addCliente(clienteId, contratoPaseoId);
        ClienteDTO clienteDTO = new ClienteDTO(clienteLogic.getCliente(clienteId));
        LOGGER.log(Level.INFO, "ClienteContratoPaseoResource addCliente: output: {0}", clienteDTO);
        return clienteDTO;
    }

    @GET
    public ClienteDetailDTO getCliente(@PathParam("contratoPaseoId") Long contratoPaseoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ClienteContratoPaseoResource getCliente: input: contratoPaseoId: {0}", new Object[]{contratoPaseoId});
       if(contratoPaseoLogic.getContratoPaseo(contratoPaseoId) == null)
            throw new WebApplicationException("El recurso /contratosPaseo/" + contratoPaseoId + " no existe.", 404);
        ClienteEntity clienteEntity = clienteContratoPaseoLogic.getCliente(contratoPaseoId);
        if (clienteEntity == null) {
            throw new WebApplicationException("El recurso /contratosPaseo/" + contratoPaseoId + "/cliente no existe.", 404);
        }
        ClienteDetailDTO clienteDetailDTO = new ClienteDetailDTO(clienteEntity);
        LOGGER.log(Level.INFO, "ClienteContratoPaseoResource getCliente: output: {0}", clienteDetailDTO);
        return clienteDetailDTO;
    }
    
    @PUT
    @Path("{clienteId: \\d+}")
    public ClienteDetailDTO replaceCliente(@PathParam("contratoPaseoId") Long contratoPaseoId, @PathParam("clienteId") Long clienteId) {
        LOGGER.log(Level.INFO, "ClienteContratoPaseoResource addCliente: input: contratoPaseoId: {0} , clienteId: {1}", new Object[]{contratoPaseoId, clienteId});
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + " no existe.", 404);
        }
        if (contratoPaseoLogic.getContratoPaseo(contratoPaseoId) == null) {
            throw new WebApplicationException("El recurso /contratosPaseo/" + contratoPaseoId + " no existe.", 404);
        }
        return new ClienteDetailDTO(clienteContratoPaseoLogic.replaceCliente(clienteId, contratoPaseoId));
    }
    
    @DELETE
    public void removeContratoPaseo(@PathParam("contratoPaseoId") Long contratoPaseoId) throws BusinessLogicException {
        clienteContratoPaseoLogic.removeContratoPaseo(contratoPaseoId);
    }
}
