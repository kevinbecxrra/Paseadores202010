/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;

import co.edu.uniandes.csw.paseadores.dtos.PerroDTO;
import co.edu.uniandes.csw.paseadores.dtos.PerroDetailDTO;
import co.edu.uniandes.csw.paseadores.ejb.ClienteLogic;
import co.edu.uniandes.csw.paseadores.ejb.ClientePerroLogic;
import co.edu.uniandes.csw.paseadores.ejb.PerroLogic;
import co.edu.uniandes.csw.paseadores.entities.PerroEntity;
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
public class PerrosClienteResource {
    
    private static final Logger LOGGER = Logger.getLogger(PerrosClienteResource.class.getName());

    @Inject
    private ClientePerroLogic clientePerroLogic;

    @Inject
    private PerroLogic perroLogic;
    
    @Inject
    private ClienteLogic clienteLogic;
    
    @POST
    @Path("{perroId: \\d+}")
    public PerroDetailDTO addPerro(@PathParam("clienteId") Long clienteId, @PathParam("perroId") Long perroId) {
        LOGGER.log(Level.INFO, "PerrosClienteResource addPerro: input: clienteId {0} , perroId {1}", new Object[]{clienteId, perroId});
        if (perroLogic.getPerro(perroId) == null) {
            throw new WebApplicationException("El recurso /perros/" + perroId + " no existe.", 404);
        }
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + " no existe.", 404);
        }
        PerroDetailDTO detailDTO = new PerroDetailDTO(clientePerroLogic.addPerro(clienteId, perroId));
        LOGGER.log(Level.INFO, "PerrosClienteResource addPerro: output: {0}", detailDTO);
        return detailDTO;
    }
    
    @GET
    public List<PerroDTO> getPerros(@PathParam("clienteId") Long clienteId) {
        LOGGER.log(Level.INFO, "PerrosClienteResource getPerros: input: {0}", clienteId);
        List<PerroDTO> lista = perrosListEntity2DTO(clientePerroLogic.getPerros(clienteId));
        LOGGER.log(Level.INFO, "PerrosClienteResource getPerros: output: {0}", lista);
        return lista;
    }
    
    @GET
    @Path("{perroId: \\d+}")
    public PerroDetailDTO getPerro(@PathParam("clienteId") Long clienteId, @PathParam("perroId") Long perroId) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "PerrosClienteResource getPerro: input: clienteId {0} , perroId {1}", new Object[]{clienteId, perroId});
        if (perroLogic.getPerro(perroId) == null) {
            throw new WebApplicationException("El recurso /perros/" + perroId + " no existe.", 404);
        }
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + " no existe.", 404);
        }
        PerroDetailDTO detailDTO = new PerroDetailDTO(clientePerroLogic.getPerro(clienteId, perroId));
        LOGGER.log(Level.INFO, "PerrosClienteResource getPerro: output: {0}", detailDTO);
        return detailDTO;
    }
    
    @DELETE
    @Path("{perroId: \\d+}")
    public void removePerro(@PathParam("clienteId") Long clienteId, @PathParam("perroId") Long perroId) {
        LOGGER.log(Level.INFO, "PerrosClienteResource removePerro: input: clienteId {0} , perroId {1}", new Object[]{clienteId, perroId});
        if (perroLogic.getPerro(perroId) == null) {
            throw new WebApplicationException("El recurso /perros/" + perroId + " no existe.", 404);
        }
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + " no existe.", 404);
        }
        clientePerroLogic.removePerro(perroId);
        LOGGER.info("PerrosClienteResource removePerro: output: void");
    }
    
    @PUT
    public List<PerroDTO> replacePerros(@PathParam("clienteId") Long clienteId, List<PerroDTO> perros) {
        LOGGER.log(Level.INFO, "PerrosClienteResource replacePerros: input: clienteId {0} , perros {1}", new Object[]{clienteId, perros});
        if (clienteLogic.getCliente(clienteId) == null) {
            throw new WebApplicationException("El recurso /clientes/" + clienteId + " no existe.", 404);
        }
        for (PerroDTO perro : perros) {
            if (perroLogic.getPerro(perro.getId()) == null) {
                throw new WebApplicationException("El recurso /perros/" + perro.getId() + " no existe.", 404);
            }
        }
        List<PerroDTO> lista = perrosListEntity2DTO(clientePerroLogic.replacePerros(clienteId, perrosListDTO2Entity(perros)));
        LOGGER.log(Level.INFO, "PerrosClienteResource replacePerros: output:{0}", lista);
        return lista;
    }
    
     private List<PerroDTO> perrosListEntity2DTO(List<PerroEntity> entityList) {
        List<PerroDTO> list = new ArrayList<>();
        for (PerroEntity entity : entityList) {
            list.add(new PerroDTO(entity));
        }
        return list;
    }

    
    private List<PerroEntity> perrosListDTO2Entity(List<PerroDTO> dtos) {
        List<PerroEntity> list = new ArrayList<>();
        for (PerroDTO dto : dtos) {
            list.add(dto.toEntity());
        }
        return list;
    }
    
}
