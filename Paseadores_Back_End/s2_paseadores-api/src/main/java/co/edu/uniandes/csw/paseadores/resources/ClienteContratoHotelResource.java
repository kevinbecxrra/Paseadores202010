/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;

import co.edu.uniandes.csw.paseadores.dtos.ClienteDTO;
import co.edu.uniandes.csw.paseadores.dtos.ClienteDetailDTO;
import co.edu.uniandes.csw.paseadores.ejb.ClienteContratoHotelLogic;
import co.edu.uniandes.csw.paseadores.ejb.ClienteLogic;
import co.edu.uniandes.csw.paseadores.ejb.ContratoHotelLogic;
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
@Path("contratoshotel/{contratosHotelId: \\d+}/cliente")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ClienteContratoHotelResource {
    
    private static final Logger LOGGER = Logger.getLogger(ClienteContratoHotelResource.class.getName());

    @Inject
    private ClienteContratoHotelLogic clienteContratoHotelLogic; 

    @Inject
    private ClienteLogic clienteLogic; 
    
    @Inject
    private ContratoHotelLogic contratoHotelLogic; 

   
    @POST
    @Path("{clienteId: \\d+}")
    public ClienteDTO addCliente(@PathParam("contratosHotelId") Long contratoHotelId, @PathParam("clienteId") Long clienteId) {
        LOGGER.log(Level.INFO, "ClienteContratoHotelResource addCliente: input: contratoHotelId: {0} , clienteId: {1}", new Object[]{contratoHotelId, clienteId});
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + " no existe.", 404);
        }
        if (contratoHotelLogic.getContratoHotel(contratoHotelId) == null) {
            throw new WebApplicationException("El recurso /contratoshotel/" + contratoHotelId + " no existe.", 404);
        }
        clienteContratoHotelLogic.addCliente(clienteId, contratoHotelId);
        ClienteDTO clienteDTO = new ClienteDTO(clienteLogic.getCliente(clienteId));
        LOGGER.log(Level.INFO, "ClienteContratoHotelResource addCliente: output: {0}", clienteDTO);
        return clienteDTO;
    }

    @GET
    public ClienteDetailDTO getCliente(@PathParam("contratosHotelId") Long contratoHotelId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ClienteContratoHotelResource getCliente: input: contratoHotelId: {0}", new Object[]{contratoHotelId});
        if(contratoHotelLogic.getContratoHotel(contratoHotelId) == null)
            throw new WebApplicationException("El recurso /contratoshotel/" + contratoHotelId + " no existe.", 404);
        ClienteEntity clienteEntity = clienteContratoHotelLogic.getCliente(contratoHotelId);
        if (clienteEntity == null) {
            throw new WebApplicationException("El recurso /contratosHotel/" + contratoHotelId + "/cliente no existe.", 404);
        }
        ClienteDetailDTO clienteDetailDTO = new ClienteDetailDTO(clienteEntity);
        LOGGER.log(Level.INFO, "ClienteContratoHotelResource getCliente: output: {0}", clienteDetailDTO);
        return clienteDetailDTO;
    }
    
    @PUT
    @Path("{clienteId: \\d+}")
    public ClienteDetailDTO replaceCliente(@PathParam("contratosHotelId") Long contratoHotelId, @PathParam("clienteId") Long clienteId) {
        LOGGER.log(Level.INFO, "ClienteContratoHotelResource addCliente: input: contratoHotelId: {0} , clienteId: {1}", new Object[]{contratoHotelId, clienteId});
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + " no existe.", 404);
        }
        if (contratoHotelLogic.getContratoHotel(contratoHotelId) == null) {
            throw new WebApplicationException("El recurso /contratoshotel/" + contratoHotelId + " no existe.", 404);
        }
        return new ClienteDetailDTO(clienteContratoHotelLogic.replaceCliente(clienteId, contratoHotelId));
    }
    
    @DELETE
    public void removeContratoHotel(@PathParam("contratosHotelId") Long contratoHotelId) throws BusinessLogicException {
        clienteContratoHotelLogic.removeContratoHotel(contratoHotelId);
    }
}
