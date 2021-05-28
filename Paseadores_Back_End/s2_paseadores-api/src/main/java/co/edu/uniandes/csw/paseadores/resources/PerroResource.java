/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;

import co.edu.uniandes.csw.paseadores.dtos.PerroDTO;
import co.edu.uniandes.csw.paseadores.dtos.PerroDetailDTO;
import co.edu.uniandes.csw.paseadores.ejb.PerroLogic;
import co.edu.uniandes.csw.paseadores.entities.PerroEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
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
@Path("perros")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class PerroResource { 
   
    private static final Logger LOGGER = Logger.getLogger(PerroResource.class.getName());
    @Inject
    private PerroLogic perroLogic;
   
    
    /**
     * Crea un nuevo perro con la informacion que se recibe en el cuerpo de la
     * petición y se regresa un objeto identico con un id auto-generado por la
     * base de datos.
     *
     * @param perro {@link PerroDTO} - EL perro que se desea guardar.
     * @return JSON {@link PerroDTO} - El perro guardado con el atributo id
     * autogenerado.
     * @throws BusinessLogicException {@link BusinessLogicExceptionMapper} -
     * Error de lógica que se genera cuando ya existe el perro o el idPerro es
     * inválido.
     */
    @POST
    public PerroDTO createPerro(PerroDTO perro) throws BusinessLogicException
    {
        return new PerroDTO(perroLogic.createPerro(perro.toEntity()));
    }
  
    /**
     * Busca y devuelve todos los perros que existen en la aplicacion.
     *
     * @return JSONArray {@link PerroDetailDTO} - Los libros encontrados en la
     * aplicación. Si no hay ninguno retorna una lista vacía.
     */
    
    @GET
    public List<PerroDetailDTO> getPerros ()
    {
        return crearListaDeEntityADTO(perroLogic.getPerros());
    }
 
    /**
     * Convierte una lista de entidades a DTO.

     * @param entityList corresponde a la lista de perros de tipo Entity que
     * vamos a convertir a DTO.
     * @return la lista de libros en forma DTO (json)
     */
    private List<PerroDetailDTO> crearListaDeEntityADTO(List<PerroEntity> entityList) {
        List<PerroDetailDTO> list = new ArrayList<>();
        for (PerroEntity entity : entityList) {
            list.add( new PerroDetailDTO(entity));
        }
        return list;
    }
    
    /**
     * Busca un perro por su id
     * @param perroId el id de un perro 
     * @return JSONArray {@link PerroDTO}- el perro buscado
     */
    
    @GET
    @Path("{IdPerro: \\d+}")
    public PerroDetailDTO getPerro(@PathParam("IdPerro")long perroId)
    {
        PerroEntity perroEntity = perroLogic.getPerro(perroId);
        if(perroEntity == null)
        {
            throw new WebApplicationException("El recurso /perros/"+ perroId +" no existe.",404);
        }
        return new PerroDetailDTO(perroEntity);
    }
    
    /**
     * 
     * @param perroId
     * @param perro
     * @return
     * @throws BusinessLogicException 
     */
    
    @PUT
    @Path("{IdPerro: \\d+}")
    public PerroDTO updatePerro (@PathParam("IdPerro")long perroId, PerroDTO perro)throws BusinessLogicException{
        perro.setId(perroId);        
        if (perroLogic.getPerro(perroId)==null)
        {
            throw new WebApplicationException("El recurso /perros/"+ perroId +" no Se encontro.",404);
        }
        return new PerroDTO(perroLogic.updatePerro(perroId, perro.toEntity()));
    }
    
    @DELETE
    @Path("{IdPerro: \\d+}")
    public void deletePerro(@PathParam("IdPerro")long perroId)
    {
        PerroEntity entity = perroLogic.getPerro(perroId);
        if (entity == null) 
        {
            throw new WebApplicationException("El /perrosDTO/" + perroId + " no esta.", 404);
        }
        perroLogic.deletePerro(perroId);
    }
    
    
    /**
     * Conexión con el servicio de contratos paseo para un perro.
     * {@link ContratosPaseoPerroResource}
     * @param IdPerro El ID del perro con respecto al cual se accede al
     * servicio.
     * @return El servicio de contratos para ese perro en paricular.
     */
    @Path("{IdPerro: \\d+}/contratosPaseo")
    public Class<ContratosPaseoPerroResource> getContratosPaseoPerroResource(@PathParam("IdPerro") Long IdPerro) {
        if (perroLogic.getPerro(IdPerro) == null) {
            throw new WebApplicationException("El recurso /perros/" + IdPerro + " no existe.", 404);
        }
        return ContratosPaseoPerroResource.class;
    }
 
    /**
     * Conexión con el servicio de contratos paseo para un perro.
     * {@link ContratosPaseoPerroResource}
     * @param IdPerro El ID del perro con respecto al cual se accede al
     * servicio.
     * @return El servicio de contratos para ese perro en paricular.
     */
    @Path("{IdPerro: \\d+}/contratoshotel")
    public Class<ContratosHotelPerroResource> getContratosHotelPerroResource(@PathParam("IdPerro") Long IdPerro) {
        if (perroLogic.getPerro(IdPerro) == null) {
            throw new WebApplicationException("El recurso /Perros/" + IdPerro + " no existe.", 404);
        }
        return ContratosHotelPerroResource.class;
    }
 
    
}
