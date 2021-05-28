/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;

import co.edu.uniandes.csw.paseadores.dtos.ClienteDTO;
import co.edu.uniandes.csw.paseadores.dtos.ClienteDetailDTO;
import co.edu.uniandes.csw.paseadores.ejb.ClienteLogic;
import co.edu.uniandes.csw.paseadores.entities.ClienteEntity;
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
import javax.ws.rs.core.MediaType;

/**
 *
 * @author Alvaro Plata
 */

@Path("/clientes")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class ClienteResource {
    
    private static final Logger LOGGER = Logger.getLogger(ClienteResource.class.getName());
    
    @Inject
    private ClienteLogic clienteLogic;
    
    @POST
    public ClienteDTO createCliente(ClienteDTO cliente) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ClienteResource createCliente: input: {0}", cliente);
        ClienteDTO clienteDTO = new ClienteDTO(clienteLogic.createCliente(cliente.toEntity()));
        LOGGER.log(Level.INFO, "ClienteResource createCliente: output: {0}", clienteDTO);
        return clienteDTO;
    }
    
    @GET
    public List<ClienteDetailDTO> getClientes() {
        LOGGER.info("ClienteResource getClientes: input: void");
        List<ClienteDetailDTO> listaClientes = listEntity2DTO(clienteLogic.getClientes());
        LOGGER.log(Level.INFO, "ClienteResource getClientes: output: {0}", listaClientes);
        return listaClientes;
    }
    
    @GET
    @Path("/correo/{correoCliente: .+}")
    public ClienteDetailDTO getClienteByCorreo(@PathParam("correoCliente") String correo)
    {
        ClienteEntity clienteEntity = clienteLogic.getClienteByCorreo(correo);
        if(clienteEntity == null){
           throw new WebApplicationException("El recurso /clientes/correo/" + correo + " no existe.", 404);
        }
        return new ClienteDetailDTO(clienteEntity);
    }
    
    @GET
    @Path("/identificacion/{identificacionCliente: \\d+}")
    public ClienteDetailDTO getClienteByIdentificacion(@PathParam("identificacionCliente") String identificacion)
    {
        ClienteEntity clienteEntity = clienteLogic.getClienteByIdentificacion(identificacion);
        if(clienteEntity == null){
       throw new WebApplicationException("El recurso /clientes/identificacion/" + identificacion + " no existe.", 404);
        }
        return new ClienteDetailDTO(clienteEntity);
    }
    
    @GET
    @Path("{clienteId: \\d+}")
    public ClienteDetailDTO getCliente(@PathParam("clienteId") Long clienteId) {
        LOGGER.log(Level.INFO, "ClienteResource getCliente: input: {0}", clienteId);
        ClienteEntity clienteEntity = clienteLogic.getCliente(clienteId);
        if (clienteEntity == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + " no existe.", 404);
        }
        ClienteDetailDTO detailDTO = new ClienteDetailDTO(clienteEntity);
        LOGGER.log(Level.INFO, "ClienteResource getCliente: output: {0}", detailDTO);
        return detailDTO;
    }
    
    @PUT
    @Path("{clienteId: \\d+}")
    public ClienteDetailDTO updateCliente(@PathParam("clienteId") Long clienteId, ClienteDetailDTO cliente) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ClienteResource updateCliente: input: clienteId: {0} , cliente: {1}", new Object[]{clienteId, cliente});
        cliente.setId(clienteId);
        LOGGER.log(Level.INFO, "Hi1");
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException("El recurso  /clientes/" + clienteId + " no existe.", 404);
        }
        LOGGER.log(Level.INFO, "Hi2");
        ClienteDetailDTO detailDTO = new ClienteDetailDTO(clienteLogic.updateCliente(cliente.toEntity()));
        LOGGER.log(Level.INFO, "ClienteResource updateCliente: output: {0}", detailDTO);
        return detailDTO;
    }
    
    @DELETE
    @Path("{clienteId: \\d+}")
    public void deleteCliente(@PathParam("clienteId") Long clienteId) {
        LOGGER.log(Level.INFO, "ClienteResource deleteCliente: input: {0}", clienteId);
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException("El recurso /clientes /" + clienteId + " no existe.", 404);
        }
        clienteLogic.deleteCliente(clienteId);
        LOGGER.info("ClienteResource deleteCliente: output: void");
    }
    
    @Path("{clienteId: \\d+}/contratosPaseo")
    public Class<ContratosPaseoClienteResource> getContratosPaseoClienteResource(@PathParam("clienteId") Long clienteId) {
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + " no existe.", 404);
        }
        return ContratosPaseoClienteResource.class;
    }
    
    @Path("{clienteId: \\d+}/contratoshotel")
    public Class<ContratosHotelClienteResource> getContratosHotelClienteResource(@PathParam("clienteId") Long clienteId) {
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + " no existe.", 404);
        }
        return ContratosHotelClienteResource.class;
    }
    
    @Path("{clienteId: \\d+}/pagosCliente")
    public Class<PagosClienteClienteResource> getPagosClienteClienteResource(@PathParam("clienteId") Long clienteId) {
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + " no existe.", 404);
        }
        return PagosClienteClienteResource.class;
    }
    
    @Path("{clienteId: \\d+}/perros")
    public Class<PerrosClienteResource> getPerrosClienteResource(@PathParam("clienteId") Long clienteId) {
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + " no existe.", 404);
        }
        return PerrosClienteResource.class;
    }
    
    
    private List<ClienteDetailDTO> listEntity2DTO(List<ClienteEntity> entityList) {
        List<ClienteDetailDTO> list = new ArrayList<>();
        for (ClienteEntity entity : entityList) {
            list.add(new ClienteDetailDTO(entity));
        }
        return list;
    }
}
