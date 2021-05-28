/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;

import co.edu.uniandes.csw.paseadores.dtos.ClienteDTO;
import co.edu.uniandes.csw.paseadores.dtos.ClienteDetailDTO;
import co.edu.uniandes.csw.paseadores.ejb.ClienteLogic;
import co.edu.uniandes.csw.paseadores.ejb.ClientePerroLogic;
import co.edu.uniandes.csw.paseadores.ejb.PerroLogic;
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
@Path("perros/{perroId: \\d+}/cliente")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClientePerroResource {
    
    private static final Logger LOGGER = Logger.getLogger(ClientePerroResource.class.getName());

    @Inject
    private ClientePerroLogic clientePerroLogic; 

    @Inject
    private ClienteLogic clienteLogic; 
    
    @Inject
    private PerroLogic perroLogic;

   
    @POST
    @Path("{clienteId: \\d+}")
    public ClienteDTO addCliente(@PathParam("perroId") Long perroId, @PathParam("clienteId") Long clienteId) {
        LOGGER.log(Level.INFO, "ClientePerroResource addCliente: input: perroId: {0} , clienteId: {1}", new Object[]{perroId, clienteId});
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + " no existe.", 404);
        }
        if (perroLogic.getPerro(perroId) == null) {
            throw new WebApplicationException("El recurso /perros/" + perroId + " no existe.", 404);
        }
        clientePerroLogic.addCliente(clienteId, perroId);
        ClienteDTO clienteDTO = new ClienteDTO(clienteLogic.getCliente(clienteId));
        LOGGER.log(Level.INFO, "ClientePerroResource addCliente: output: {0}", clienteDTO);
        return clienteDTO;
    }

    @GET
    public ClienteDetailDTO getCliente(@PathParam("perroId") Long perroId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ClientePerroResource getCliente: input: perroId: {0}", new Object[]{perroId});
        if (perroLogic.getPerro(perroId) == null) {
            throw new WebApplicationException("El recurso /perros/" + perroId + " no existe.", 404);
        }
        ClienteEntity clienteEntity = clientePerroLogic.getCliente(perroId);
        if (clienteEntity == null) {
            throw new WebApplicationException("El recurso /perros/" + perroId + "/cliente no existe.", 404);
        }
        ClienteDetailDTO clienteDetailDTO = new ClienteDetailDTO(clienteEntity);
        LOGGER.log(Level.INFO, "ClientePerroResource getCliente: output: {0}", clienteDetailDTO);
        return clienteDetailDTO;
    }
    
    @PUT
    @Path("{clienteId: \\d+}")
    public ClienteDetailDTO replaceCliente(@PathParam("perroId") Long perroId, @PathParam("clienteId") Long clienteId) {
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + " no existe.", 404);
        }
        if (perroLogic.getPerro(perroId) == null) {
            throw new WebApplicationException("El recurso /perros/" + perroId + " no existe.", 404);
        }
        return new ClienteDetailDTO(clientePerroLogic.replaceCliente(perroId, clienteId));
    }
    
    @DELETE
    public void removePerro(@PathParam("perroId") Long perroId) throws BusinessLogicException {
        clientePerroLogic.removePerro(perroId);
    }
}