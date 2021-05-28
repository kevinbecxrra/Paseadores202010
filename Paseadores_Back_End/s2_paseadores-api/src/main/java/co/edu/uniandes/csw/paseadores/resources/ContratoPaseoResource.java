/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;

import co.edu.uniandes.csw.paseadores.dtos.ContratoPaseoDTO;
import co.edu.uniandes.csw.paseadores.ejb.ContratoPaseoLogic;
import co.edu.uniandes.csw.paseadores.entities.ContratoPaseoEntity;
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

/**
 *
 * @author julian Oliveros<je.oliverosf at uniandes.edu.co>
 */
@Path("contratosPaseo")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class ContratoPaseoResource {


    
    private static final Logger LOGGER = Logger.getLogger(ContratoPaseoResource.class.getName());
   
    @Inject
    private ContratoPaseoLogic contratoPaseoLogic;
    
        /**
     * Crea un nuevo contrato Paseo con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param contrato {@link ContratoPaseoDTO} - EL contrato Paseo que se desea guardar.
     * @return JSON {@link RecorridoDTO} - El recorrido guardado con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el contrato
     */
    @POST
    public ContratoPaseoDTO crearContratoPaseo(ContratoPaseoDTO contrato)throws BusinessLogicException
    {
        LOGGER.log(Level.INFO, "ContratoPaseoResource createContratoPaseo: input:{0}",contrato);
        ContratoPaseoDTO nuevoContrato = new ContratoPaseoDTO(getContratoPaseoLogic().createContratoPaseo(contrato.toEntity(false)), false);
        LOGGER.log(Level.INFO, "ContratoPaseoResource createContratoPaseo: input:{0}",nuevoContrato);
        return nuevoContrato;
    }
     
    
    /**
     * busca un contrato paseo por su id
     * @param contratoId
     * @return 
     */
    
    @GET
    @Path("{contratoId: \\d+}")
    public ContratoPaseoDTO getContratoPaseo(@PathParam("contratoId")long contratoId){
        ContratoPaseoEntity contratoPaseoEntity = getContratoPaseoLogic().getContratoPaseo(contratoId);
        if(contratoPaseoEntity==null){
            
            throw new WebApplicationException("El /contratosPaseo/"+ contratoId +" no se encuetra.",404);
        }
        return new ContratoPaseoDTO(contratoPaseoEntity, false);
    }
    
        @GET
    public List<ContratoPaseoDTO> getContratosPaseo() {
        LOGGER.info("PagoClienteResource getPagosCliente: input: void");
        List<ContratoPaseoDTO> listaContratos = listEntity2DTO(getContratoPaseoLogic().getContratosPaseo());
        LOGGER.log(Level.INFO, "PagoClienteResource getPagosCliente: output: {0}", listaContratos);
        return listaContratos;
    }
        
    /**
     * remplaza un nuevo contrato paseo
     * @param contratoId
     * @param contratoDTO
     * @return
     * @throws BusinessLogicException 
     */
    @PUT
    @Path("{contratoId: \\d+}")
    public ContratoPaseoDTO updateContratoPaseo(@PathParam("contratoId")long contratoId, ContratoPaseoDTO contratoDTO) throws BusinessLogicException{        
        contratoDTO.setId(contratoId);        
        if (getContratoPaseoLogic().getContratoPaseo(contratoId)==null){
            throw new WebApplicationException("El recurso /CONTRATOPASEO/"+ contratoId +" no esta.",404);
        }
        return new ContratoPaseoDTO(getContratoPaseoLogic().updateContratoPaseo(contratoDTO.toEntity(false)), false);
    }
    
    /**
     * Elimina un contrato Paseo
     * @param contratosPaseoID
     * @throws BusinessLogicException 
     */
    @DELETE
    @Path("{contratosPaseoID: \\d+}")
    public void deleteContratoPaseo(@PathParam("contratosPaseoID") Long contratosPaseoID) throws BusinessLogicException{
        ContratoPaseoEntity entity = getContratoPaseoLogic().getContratoPaseo(contratosPaseoID);
        if(entity ==null){
            throw new WebApplicationException("El  /contratosPaseoDTO/" + contratosPaseoID + " no Se pudo Encontrar.", 404);
        }            
        getContratoPaseoLogic().deleteContratoPaseo(contratosPaseoID);
    } 
    
    
    @Path("{contratoPaseoId: \\d+}/perro")
    public Class<PerroContratoPaseoResource> getContratoPaseoPerroResource(@PathParam("contratoPaseoId") Long contratoPaseoId) {
        if (getContratoPaseoLogic().getContratoPaseo(contratoPaseoId) == null) 
        {
            throw new WebApplicationException("El recurso /contratos Paseo /" + contratoPaseoId + " no existe.", 404);
        }
        return PerroContratoPaseoResource.class;
    }
    
    @Path("{contratosPaseoId: \\d+}/paseo")
    public Class<PaseoContratoPaseoResource> getPaseoContratoPaseo(@PathParam("contratosPaseoId") Long contratosPaseoId) {
        if (getContratoPaseoLogic().getContratoPaseo(contratosPaseoId) == null) {
            throw new WebApplicationException("El recurso contratosPaseo/" + contratosPaseoId + " no existe.", 404);
        }
        return PaseoContratoPaseoResource.class;
    }
    
        private List<ContratoPaseoDTO
        > listEntity2DTO(List<ContratoPaseoEntity> entityList) {
        List<ContratoPaseoDTO
                > list = new ArrayList<>();
        for (ContratoPaseoEntity entity : entityList) {
            list.add(new ContratoPaseoDTO
        (entity, false));
        }
        return list;
    }
        
            /**
     * @return the contratoPaseoLogic
     */
    public ContratoPaseoLogic getContratoPaseoLogic() {
        return contratoPaseoLogic;
    }

    /**
     * @param contratoPaseoLogic the contratoPaseoLogic to set
     */
    public void setContratoPaseoLogic(ContratoPaseoLogic contratoPaseoLogic) {
        this.contratoPaseoLogic = contratoPaseoLogic;
    }
        
        
    
}