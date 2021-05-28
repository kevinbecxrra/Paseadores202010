/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;

import co.edu.uniandes.csw.paseadores.dtos.ClienteDTO;
import co.edu.uniandes.csw.paseadores.dtos.ClienteDetailDTO;
import co.edu.uniandes.csw.paseadores.ejb.ClienteLogic;
import co.edu.uniandes.csw.paseadores.ejb.ClientePagoClienteLogic;
import co.edu.uniandes.csw.paseadores.ejb.PagoClienteLogic;
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
@Path("pagosCliente/{pagoClienteId: \\d+}/cliente")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClientePagoClienteResource {

   private static final Logger LOGGER = Logger.getLogger(ClientePagoClienteResource.class.getName());

    @Inject
    private ClientePagoClienteLogic clientePagoClienteLogic; 

    @Inject
    private ClienteLogic clienteLogic; 
    
    @Inject
    private PagoClienteLogic pagoClienteLogic;

   
    @POST
    @Path("{clienteId: \\d+}")
    public ClienteDTO addCliente(@PathParam("pagoClienteId") Long pagoClienteId, @PathParam("clienteId") Long clienteId) {
        LOGGER.log(Level.INFO, "ClientePagoClienteResource addCliente: input: pagoClienteId: {0} , clienteId: {1}", new Object[]{pagoClienteId, clienteId});
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + " no existe.", 404);
        }
        if (pagoClienteLogic.getPagoCliente(pagoClienteId) == null) {
            throw new WebApplicationException("El recurso /pagosCliente/" + pagoClienteId + " no existe.", 404);
        }
        clientePagoClienteLogic.addCliente(clienteId, pagoClienteId);
        ClienteDTO clienteDTO = new ClienteDTO(clienteLogic.getCliente(clienteId));
        LOGGER.log(Level.INFO, "ClientePagoClienteResource addCliente: output: {0}", clienteDTO);
        return clienteDTO;
    }

    @GET
    public ClienteDetailDTO getCliente(@PathParam("pagoClienteId") Long pagoClienteId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ClientePagoClienteResource getCliente: input: pagoClienteId: {0}", new Object[]{pagoClienteId});
        if(pagoClienteLogic.getPagoCliente(pagoClienteId) == null)
            throw new WebApplicationException("El recurso /pagosCliente/" + pagoClienteId + " no existe.", 404);
        ClienteEntity clienteEntity = clientePagoClienteLogic.getCliente(pagoClienteId);
        if (clienteEntity == null) {
            throw new WebApplicationException("El recurso /pagosCliente/" + pagoClienteId + "/cliente no existe.", 404);
        }
        ClienteDetailDTO clienteDetailDTO = new ClienteDetailDTO(clienteEntity);
        LOGGER.log(Level.INFO, "ClientePagoClienteResource getCliente: output: {0}", clienteDetailDTO);
        return clienteDetailDTO;
    }
    
    @PUT
    @Path("{clienteId: \\d+}")
    public ClienteDetailDTO replaceCliente(@PathParam("pagoClienteId") Long pagoClienteId, @PathParam("clienteId") Long clienteId) {
        LOGGER.log(Level.INFO,"HI1");
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + " no existe.", 404);
        }
        if(pagoClienteLogic.getPagoCliente(pagoClienteId) == null)
            throw new WebApplicationException("El recurso /pagosCliente/" + pagoClienteId + " no existe.", 404);
        LOGGER.log(Level.INFO,"HI2");
        ClienteEntity clienteEntity = clientePagoClienteLogic.replaceCliente(pagoClienteId, clienteId);
        LOGGER.log(Level.INFO,"HI3");
        ClienteDetailDTO clienteDetailDTO = new ClienteDetailDTO(clienteEntity);
        LOGGER.log(Level.INFO,"HI4"
                + "");
        return clienteDetailDTO;
    }
    
    @DELETE
    public void removePagoCliente(@PathParam("pagoClienteId") Long pagoClienteId) throws BusinessLogicException {
        clientePagoClienteLogic.removePagoCliente(pagoClienteId);
    }
}