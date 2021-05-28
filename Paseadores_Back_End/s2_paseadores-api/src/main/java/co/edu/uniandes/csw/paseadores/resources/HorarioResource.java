/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;

import co.edu.uniandes.csw.paseadores.dtos.HorarioDTO;
import co.edu.uniandes.csw.paseadores.dtos.HorarioDetailDTO;
import co.edu.uniandes.csw.paseadores.ejb.HorarioLogic;
import co.edu.uniandes.csw.paseadores.ejb.PaseoLogic;
import co.edu.uniandes.csw.paseadores.entities.HorarioEntity;
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
 * @author Edgar Camilo Diaz Suarez
 */
@Path("horarios")
@Produces("application/json")
@Consumes("application/json")
@RequestScoped
public class HorarioResource {
    
    @Inject
    private HorarioLogic horarioLogic;
    
    @Inject
    private PaseoLogic paseoLogic;
    
    @POST
    public HorarioDTO createHorario(HorarioDTO horario){
        return new HorarioDTO(getHorarioLogic().createHorario(horario.toEntity()));
    }
 
    @GET
    @Path("{horariosId: \\d+}")
    public HorarioDetailDTO getHorario(@PathParam("horariosId") Long horariosId){
        HorarioEntity horarioEntity = getHorarioLogic().getHorario(horariosId);
        if(horarioEntity == null){
            throw new WebApplicationException(" El recurso /horarios/"+horariosId + " no  existe.    ",404);
        }
        return new HorarioDetailDTO(horarioEntity);
    }
    

    
    @PUT
    @Path("{horariosId:\\d+}")
    public HorarioDetailDTO updateHorario(@PathParam("horariosId") Long horariosId, HorarioDetailDTO horario){  
        horario.setId(horariosId);
        if(horarioLogic.getHorario(horariosId) == null){
            throw new WebApplicationException("el recurso /horarios/"+ horariosId + "no  existe.    ",404);
        }
        return new HorarioDetailDTO(horarioLogic.updateHorario(horario.toEntity())); 
    }
    
    @DELETE
    @Path("{horariosId: \\d+}")
    public void deleteHorario(@PathParam("horariosId")Long horariosId){
        HorarioEntity entity = getHorarioLogic().getHorario(horariosId);
        if(entity == null){
            throw new WebApplicationException("El  recurso /horarios/" + horariosId + "no  existe.",404);
        }
        getHorarioLogic().deleteHorario(horariosId);
        
    }
            
    @Path("{horariosId: \\d+}/paseos")
    public Class<PaseoResource> getPaseoResource(@PathParam("horariosId")Long horariosId){
        if(getHorarioLogic().getHorario(horariosId) == null){
            throw new WebApplicationException("El recuros  /horarios/"+horariosId + "/paseos no existe",404);
        }
        return PaseoResource.class;
    }
    
     @Path("{horariosId: \\d+}/paseadores")
    public Class<HorarioPaseadorResource> getHorarioPaseadorResource(@PathParam("horariosId")Long horariosId){
        if(horarioLogic.getHorario(horariosId) == null){
            throw new WebApplicationException("El recurso  /horarios/"+horariosId + "/paseadores no existe .",404);
        }
        return HorarioPaseadorResource.class;
    }

    /**
     * @return the horarioLogic
     */
    public HorarioLogic getHorarioLogic() {
        return horarioLogic;
    }

    /**
     * @param horarioLogic the horarioLogic to set
     */
    public void setHorarioLogic(HorarioLogic horarioLogic) {
        this.horarioLogic = horarioLogic;
    }

    /**
     * @return the paseoLogic
     */
    public PaseoLogic getPaseoLogic() {
        return paseoLogic;
    }

    /**
     * @param paseoLogic the paseoLogic to set
     */
    public void setPaseoLogic(PaseoLogic paseoLogic) {
        this.paseoLogic = paseoLogic;
    }
    
}
