/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;

import co.edu.uniandes.csw.paseadores.dtos.PagoClienteDTO;
import co.edu.uniandes.csw.paseadores.ejb.ClienteLogic;
import co.edu.uniandes.csw.paseadores.ejb.ClientePagoClienteLogic;
import co.edu.uniandes.csw.paseadores.ejb.PagoClienteLogic;
import co.edu.uniandes.csw.paseadores.entities.PagoClienteEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
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
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class PagosClienteClienteResource {
    
    private static final Logger LOGGER = Logger.getLogger(PagosClienteClienteResource.class.getName());

    @Inject
    private ClientePagoClienteLogic clientePagoClienteLogic;

    @Inject
    private PagoClienteLogic pagoClienteLogic;
    
    @Inject
    private ClienteLogic clienteLogic;
    
    @POST
    @Path("{pagoClienteId: \\d+}")
    public PagoClienteDTO addPagoCliente(@PathParam("clienteId") Long clienteId, @PathParam("pagoClienteId") Long pagoClienteId) {
        LOGGER.log(Level.INFO, "PagosClienteClienteResource addPagoCliente: input: clienteId {0} , pagoClienteId {1}", new Object[]{clienteId, pagoClienteId});
        if (pagoClienteLogic.getPagoCliente(pagoClienteId) == null) {
            throw new WebApplicationException("El recurso /pagosCliente/" + pagoClienteId + " no existe.", 404);
        }
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + " no existe.", 404);
        }
        PagoClienteDTO detailDTO = new PagoClienteDTO(clientePagoClienteLogic.addPagoCliente(clienteId, pagoClienteId), false);
        LOGGER.log(Level.INFO, "PagosClienteClienteResource addPagoCliente: output: {0}", detailDTO);
        return detailDTO;
    }
    
    @GET
    public List<PagoClienteDTO> getPagosCliente(@PathParam("clienteId") Long clienteId) {
        LOGGER.log(Level.INFO, "PagosClienteClienteResource getPagosCliente: input: {0}", clienteId);
        List<PagoClienteDTO> lista = pagosClienteListEntity2DTO(clientePagoClienteLogic.getPagosCliente(clienteId));
        LOGGER.log(Level.INFO, "PagosClienteClienteResource getPagosCliente: output: {0}", lista);
        return lista;
    }
    
    @GET
    @Path("{pagoClienteId: \\d+}")
    public PagoClienteDTO getPagoCliente(@PathParam("clienteId") Long clienteId, @PathParam("pagoClienteId") Long pagoClienteId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "PagosClienteClienteResource getPagoCliente: input: clienteId {0} , pagoClienteId {1}", new Object[]{clienteId, pagoClienteId});
        if (pagoClienteLogic.getPagoCliente(pagoClienteId) == null) {
            throw new WebApplicationException("El recurso /pagosCliente/" + pagoClienteId + " no existe.", 404);
        }
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + " no existe.", 404);
        }
        PagoClienteDTO detailDTO = new PagoClienteDTO(clientePagoClienteLogic.getPagoCliente(clienteId, pagoClienteId), false);
        LOGGER.log(Level.INFO, "PagosClienteClienteResource getPagoCliente: output: {0}", detailDTO);
        return detailDTO;
    }
    
    @DELETE
    @Path("{pagoClienteId: \\d+}")
    public void removePagoCliente(@PathParam("clienteId") Long clienteId, @PathParam("pagoClienteId") Long pagoClienteId) {
        LOGGER.log(Level.INFO, "PagosClienteClienteResource removePagoCliente: input: clienteId {0} , pagoClienteId {1}", new Object[]{clienteId, pagoClienteId});
        if (pagoClienteLogic.getPagoCliente(pagoClienteId) == null) {
            throw new WebApplicationException("El recurso /pagosCliente/" + pagoClienteId + " no existe.", 404);
        }
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + " no existe.", 404);
        }
        clientePagoClienteLogic.removePagoCliente(pagoClienteId);
        LOGGER.info("PagosClienteClienteResource removePagoCliente: output: void");
    }
    
    @PUT
    public List<PagoClienteDTO> replacePagosCliente(@PathParam("clienteId") Long clienteId, List<PagoClienteDTO> pagosCliente) {
        LOGGER.log(Level.INFO, "PagosClienteClienteResource replacePagosCliente: input: clienteId {0} , pagosCliente {1}", new Object[]{clienteId, pagosCliente});
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + " no existe.", 404);
        }
        for (PagoClienteDTO pagoCliente : pagosCliente) {
            if (pagoClienteLogic.getPagoCliente(pagoCliente.getId()) == null) {
                throw new WebApplicationException("El recurso /pagosCliente/" + pagoCliente.getId() + " no existe.", 404);
            }
        }
        List<PagoClienteDTO> lista = pagosClienteListEntity2DTO(clientePagoClienteLogic.replacePagosCliente(clienteId, pagosClienteListDTO2Entity(pagosCliente)));
        LOGGER.log(Level.INFO, "PagosClienteClienteResource replacePagosCliente: output:{0}", lista);
        return lista;
    }
    
     private List<PagoClienteDTO> pagosClienteListEntity2DTO(List<PagoClienteEntity> entityList) {
        List<PagoClienteDTO> list = new ArrayList<>();
        for (PagoClienteEntity entity : entityList) {
            list.add(new PagoClienteDTO(entity, false));
        }
        return list;
    }

    
    private List<PagoClienteEntity> pagosClienteListDTO2Entity(List<PagoClienteDTO> dtos) {
        List<PagoClienteEntity> list = new ArrayList<>();
        for (PagoClienteDTO dto : dtos) {
            list.add(dto.toEntity(false));
        }
        return list;
    }
    
}
