/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;

import co.edu.uniandes.csw.paseadores.dtos.PagoClienteDTO;
import co.edu.uniandes.csw.paseadores.ejb.PagoClienteLogic;
import co.edu.uniandes.csw.paseadores.entities.PagoClienteEntity;
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
@Path("/pagosCliente")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
@RequestScoped
public class PagoClienteResource {
    
    private static final Logger LOGGER = Logger.getLogger(PagoClienteResource.class.getName());

    @Inject
    private PagoClienteLogic pagoClienteLogic;

    
    @POST
    public PagoClienteDTO createPagoCliente(PagoClienteDTO pagoCliente) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "PagoClienteResource createPagoCliente: input: {0}", pagoCliente);
        PagoClienteDTO pagoClienteDTO = new PagoClienteDTO(pagoClienteLogic.createPagoCliente(pagoCliente.toEntity(false)), false);
        LOGGER.log(Level.INFO, "PagoClienteResource createPagoCliente: output: {0}", pagoClienteDTO);
        return pagoClienteDTO;
    }
    
    @PUT
    @Path("{pagoClienteId: \\d+}")
    public PagoClienteDTO updatePagoCliente(@PathParam("pagoClienteId") Long pagoClienteId, PagoClienteDTO pagoCliente) throws BusinessLogicException {
        LOGGER.log(Level.INFO, "PagoClienteResource updatePagoCliente: input: pagoClienteId: {0} , pagoCliente: {1}", new Object[]{pagoClienteId, pagoCliente});
        pagoCliente.setId(pagoClienteId);
        if (pagoClienteLogic.getPagoCliente(pagoClienteId) == null) {
            throw new WebApplicationException("El recurso   /pagosCliente/" + pagoClienteId + " no existe.", 404);
        }
        PagoClienteDTO detailDTO = new PagoClienteDTO(pagoClienteLogic.updatePagoCliente(pagoClienteId, pagoCliente.toEntity(false)), false);
        LOGGER.log(Level.INFO, "PagoClienteResource updatePagoCliente: {0}", detailDTO);
        return detailDTO;
    }
    
    @GET
    @Path("/referencia/{referenciaCliente: .+}")
    public PagoClienteDTO getPagoClienteByReferencia(@PathParam("referenciaCliente") String referencia)
    {
        LOGGER.log(Level.INFO, "1");
        PagoClienteEntity pagoClienteEntity = pagoClienteLogic.getPagoByReferencia(referencia);
        if(pagoClienteEntity == null){
            LOGGER.log(Level.INFO, "2");
            throw new WebApplicationException("El recurso /pagosCliente/referencia/" + referencia + " no existe.", 404);
        }
        LOGGER.log(Level.INFO, "3");
        return new PagoClienteDTO(pagoClienteEntity, false);
    }
    
    @DELETE
    @Path("{pagoClienteId: \\d+}")
    public void deletePagoCliente(@PathParam("pagoClienteId") Long pagoClienteId) {
        LOGGER.log(Level.INFO, "PagoClienteResource deletePagoCliente: input: {0}", pagoClienteId);
        if (pagoClienteLogic.getPagoCliente(pagoClienteId) == null) {
            throw new WebApplicationException("El recurso/pagosCliente/" + pagoClienteId + " no existe    .", 404);
        }
        pagoClienteLogic.deletePagoCliente(pagoClienteId);
        LOGGER.info("PagoClienteResource deletePagoCliente: output: void");
    }
    
    @Path("{pagoClienteId: \\d+}/cliente")
    public Class<ClientePagoClienteResource> getClientePagoCliente(@PathParam("pagoClienteId") Long pagoClienteId) {
        if (pagoClienteLogic.getPagoCliente(pagoClienteId) == null) {
            throw new WebApplicationException("El recurso /  pagosCliente/" + pagoClienteId + " no existe", 404);
        }
        return ClientePagoClienteResource.class;
    }
    
    @GET
    public List<PagoClienteDTO> getPagosCliente() {
        LOGGER.info("PagoClienteResource getPagosCliente: input: void");
        List<PagoClienteDTO> listaPagosCliente = listEntity2DTO(pagoClienteLogic.getPagosCliente());
        LOGGER.log(Level.INFO, "PagoClienteResource getPagosCliente: output: {0}", listaPagosCliente);
        return listaPagosCliente;
    }
    
    private List<PagoClienteDTO> listEntity2DTO(List<PagoClienteEntity> entityList) {
        List<PagoClienteDTO> list = new ArrayList<>();
        for (PagoClienteEntity entity : entityList) {
            list.add(new PagoClienteDTO(entity, false));
        }
        return list;
    }
}
