/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;

import co.edu.uniandes.csw.paseadores.dtos.ContratoPaseoDTO;
import co.edu.uniandes.csw.paseadores.ejb.ClienteContratoPaseoLogic;
import co.edu.uniandes.csw.paseadores.ejb.ClienteLogic;
import co.edu.uniandes.csw.paseadores.ejb.ContratoPaseoLogic;
import co.edu.uniandes.csw.paseadores.entities.ContratoPaseoEntity;
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
public class ContratosPaseoClienteResource {
    
    private static final Logger LOGGER = Logger.getLogger(ContratosPaseoClienteResource.class.getName());

    @Inject
    private ClienteContratoPaseoLogic clienteContratoPaseoLogic;

    @Inject
    private ContratoPaseoLogic contratoPaseoLogic;
    
    @Inject
    private ClienteLogic clienteLogic;
    
    @POST
    @Path("{contratoPaseoId: \\d+}")
    public ContratoPaseoDTO addContratoPaseo(@PathParam("clienteId") Long clienteId, @PathParam("contratoPaseoId") Long contratoPaseoId) {
        LOGGER.log(Level.INFO, "ContratosPaseoClienteResource addContratoPaseo: input: clienteId {0} , contratoPaseoId {1}", new Object[]{clienteId, contratoPaseoId});
        if (contratoPaseoLogic.getContratoPaseo(contratoPaseoId) == null) {
            throw new WebApplicationException("El recurso /contratosPaseo/" + contratoPaseoId + " no existe.", 404);
        }
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + " no existe.", 404);
        }
        ContratoPaseoDTO detailDTO = new ContratoPaseoDTO(clienteContratoPaseoLogic.addContratoPaseo(clienteId, contratoPaseoId), false);
        LOGGER.log(Level.INFO, "ContratosPaseoClienteResource addContratoPaseo: output: {0}", detailDTO);
        return detailDTO;
    }
    
    @GET
    public List<ContratoPaseoDTO> getContratosPaseo(@PathParam("clienteId") Long clienteId) {
        LOGGER.log(Level.INFO, "ContratosPaseoClienteResource getContratosPaseo: input: {0}", clienteId);
        List<ContratoPaseoDTO> lista = contratosPaseoListEntity2DTO(clienteContratoPaseoLogic.getContratosPaseo(clienteId));
        LOGGER.log(Level.INFO, "ContratosPaseoClienteResource getContratosPaseo: output: {0}", lista);
        return lista;
    }
    
    @GET
    @Path("{contratoPaseoId: \\d+}")
    public ContratoPaseoDTO getContratoPaseo(@PathParam("clienteId") Long clienteId, @PathParam("contratoPaseoId") Long contratoPaseoId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ContratosPaseoClienteResource getContratoPaseo: input: clienteId {0} , contratoPaseoId {1}", new Object[]{clienteId, contratoPaseoId});
        if (contratoPaseoLogic.getContratoPaseo(contratoPaseoId) == null) {
            throw new WebApplicationException("El recurso /contratosPaseo/" + contratoPaseoId + " no existe.", 404);
        }
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + " no existe.", 404);
        }
        ContratoPaseoDTO detailDTO = new ContratoPaseoDTO(clienteContratoPaseoLogic.getContratoPaseo(clienteId, contratoPaseoId), false);
        LOGGER.log(Level.INFO, "ContratosPaseoClienteResource getContratoPaseo: output: {0}", detailDTO);
        return detailDTO;
    }
    
    @DELETE
    @Path("{contratoPaseoId: \\d+}")
    public void removeContratoPaseo(@PathParam("clienteId") Long clienteId, @PathParam("contratoPaseoId") Long contratoPaseoId) {
        LOGGER.log(Level.INFO, "ContratosPaseoClienteResource removeContratoPaseo: input: clienteId {0} , contratoPaseoId {1}", new Object[]{clienteId, contratoPaseoId});
        if (contratoPaseoLogic.getContratoPaseo(contratoPaseoId) == null) {
            throw new WebApplicationException("El recurso /contratosPaseo/" + contratoPaseoId + " no existe.", 404);
        }
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + " no existe.", 404);
        }
        clienteContratoPaseoLogic.removeContratoPaseo(contratoPaseoId);
        LOGGER.info("ContratosPaseoClienteResource removeContratoPaseo: output: void");
    }
    
    @PUT
    public List<ContratoPaseoDTO> replaceContratosPaseo(@PathParam("clienteId") Long clienteId, List<ContratoPaseoDTO> contratos) {
        LOGGER.log(Level.INFO, "ContratosPaseoClienteResource replaceContratosPaseo: input: clienteId {0} , contratos {1}", new Object[]{clienteId, contratos});
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + " no existe.", 404);
        }
        for (ContratoPaseoDTO contrato : contratos) {
            if (contratoPaseoLogic.getContratoPaseo(contrato.getId()) == null) {
                throw new WebApplicationException("El recurso /contratosPaseo/" + contrato.getId() + " no existe.", 404);
            }
        }
        List<ContratoPaseoDTO> lista = contratosPaseoListEntity2DTO(clienteContratoPaseoLogic.replaceContratosPaseo(clienteId, contratosPaseoListDTO2Entity(contratos)));
        LOGGER.log(Level.INFO, "ContratosPaseoClienteResource replaceContratosPaseo: output:{0}", lista);
        return lista;
    }
    
     private List<ContratoPaseoDTO> contratosPaseoListEntity2DTO(List<ContratoPaseoEntity> entityList) {
        List<ContratoPaseoDTO> list = new ArrayList<>();
        for (ContratoPaseoEntity entity : entityList) {
            list.add(new ContratoPaseoDTO(entity, false));
        }
        return list;
    }

    
    private List<ContratoPaseoEntity> contratosPaseoListDTO2Entity(List<ContratoPaseoDTO> dtos) {
        List<ContratoPaseoEntity> list = new ArrayList<>();
        for (ContratoPaseoDTO dto : dtos) {
            list.add(dto.toEntity(false));
        }
        return list;
    }
    
}
