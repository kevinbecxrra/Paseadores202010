/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;

import co.edu.uniandes.csw.paseadores.dtos.ContratoHotelDTO;
import co.edu.uniandes.csw.paseadores.dtos.ContratoHotelDetailDTO;
import co.edu.uniandes.csw.paseadores.ejb.ClienteContratoHotelLogic;
import co.edu.uniandes.csw.paseadores.ejb.ClienteLogic;
import co.edu.uniandes.csw.paseadores.ejb.ContratoHotelLogic;
import co.edu.uniandes.csw.paseadores.entities.ContratoHotelEntity;
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
public class ContratosHotelClienteResource {
    
    private static final Logger LOGGER = Logger.getLogger(ContratosHotelClienteResource.class.getName());

    @Inject
    private ClienteContratoHotelLogic clienteContratoHotelLogic;

    @Inject
    private ContratoHotelLogic contratoHotelLogic;
    
    @Inject
    private ClienteLogic clienteLogic;
    
    @POST
    @Path("{contratoHotelId: \\d+}")
    public ContratoHotelDetailDTO addContratoHotel(@PathParam("clienteId") Long clienteId, @PathParam("contratoHotelId") Long contratoHotelId) {
        LOGGER.log(Level.INFO, "ContratosHotelClienteResource addContratoHotel: input: clienteId {0} , contratoHotelId {1}", new Object[]{clienteId, contratoHotelId});
        if (contratoHotelLogic.getContratoHotel(contratoHotelId) == null) {
            throw new WebApplicationException("El recurso /contratoshotel/" + contratoHotelId + " no existe.", 404);
        }
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + " no existe.", 404);
        }
        ContratoHotelDetailDTO detailDTO = new ContratoHotelDetailDTO(clienteContratoHotelLogic.addContratoHotel(clienteId, contratoHotelId));
        LOGGER.log(Level.INFO, "ContratosHotelClienteResource addContratoHotel: output: {0}", detailDTO);
        return detailDTO;
    }
    
    @GET
    public List<ContratoHotelDTO> getContratosHotel(@PathParam("clienteId") Long clienteId) {
        LOGGER.log(Level.INFO, "ContratosHotelClienteResource getContratosHotel: input: {0}", clienteId);
        List<ContratoHotelDTO> lista = contratosHotelListEntity2DTO(clienteContratoHotelLogic.getContratosHotel(clienteId));
        LOGGER.log(Level.INFO, "ContratosHotelClienteResource getContratosHotel: output: {0}", lista);
        return lista;
    }
    
    @GET
    @Path("{contratoHotelId: \\d+}")
    public ContratoHotelDetailDTO getContratoHotel(@PathParam("clienteId") Long clienteId, @PathParam("contratoHotelId") Long contratoHotelId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "ContratosHotelClienteResource getContratoHotel: input: clienteId {0} , contratoHotelId {1}", new Object[]{clienteId, contratoHotelId});
        if (contratoHotelLogic.getContratoHotel(contratoHotelId) == null) {
            throw new WebApplicationException("El recurso /contratoshotel/" + contratoHotelId + " no existe.", 404);
        }
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + " no existe.", 404);
        }
        ContratoHotelDetailDTO detailDTO = new ContratoHotelDetailDTO(clienteContratoHotelLogic.getContratoHotel(clienteId, contratoHotelId));
        LOGGER.log(Level.INFO, "ContratosHotelClienteResource getContratoHotel: output: {0}", detailDTO);
        return detailDTO;
    }
    
    @DELETE
    @Path("{contratoHotelId: \\d+}")
    public void removeContratoHotel(@PathParam("clienteId") Long clienteId, @PathParam("contratoHotelId") Long contratoHotelId) {
        LOGGER.log(Level.INFO, "ContratosHotelClienteResource removeContratoHotel: input: clienteId {0} , contratoHotelId {1}", new Object[]{clienteId, contratoHotelId});
        if (contratoHotelLogic.getContratoHotel(contratoHotelId) == null) {
            throw new WebApplicationException("El recurso /contratoshotel/" + contratoHotelId + " no existe.", 404);
        }
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + " no existe.", 404);
        }
        clienteContratoHotelLogic.removeContratoHotel(contratoHotelId);
        LOGGER.info("ContratosHotelClienteResource removeContratoHotel: output: void");
    }
    
    @PUT
    public List<ContratoHotelDTO> replaceContratosHotel(@PathParam("clienteId") Long clienteId, List<ContratoHotelDTO> contratos) {
        LOGGER.log(Level.INFO, "ContratosHotelClienteResource replaceContratosHotel: input: clienteId {0} , contratos {1}", new Object[]{clienteId, contratos});
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + " no existe.", 404);
        }
        for (ContratoHotelDTO contrato : contratos) {
            if (contratoHotelLogic.getContratoHotel(contrato.getId()) == null) {
                throw new WebApplicationException("El recurso /contratoshotel/" + contrato.getId() + " no existe.", 404);
            }
        }
        List<ContratoHotelDTO> lista = contratosHotelListEntity2DTO(clienteContratoHotelLogic.replaceContratosHotel(clienteId, contratosHotelListDTO2Entity(contratos)));
        LOGGER.log(Level.INFO, "ContratosHotelClienteResource replaceContratosHotel: output:{0}", lista);
        return lista;
    }
    
     private List<ContratoHotelDTO> contratosHotelListEntity2DTO(List<ContratoHotelEntity> entityList) {
        List<ContratoHotelDTO> list = new ArrayList<>();
        for (ContratoHotelEntity entity : entityList) {
            list.add(new ContratoHotelDTO(entity));
        }
        return list;
    }

    
    private List<ContratoHotelEntity> contratosHotelListDTO2Entity(List<ContratoHotelDTO> dtos) {
        List<ContratoHotelEntity> list = new ArrayList<>();
        for (ContratoHotelDTO dto : dtos) {
            list.add(dto.toEntity(false));
        }
        return list;
    }
    
}
