/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;

import co.edu.uniandes.csw.paseadores.dtos.HorarioDetailDTO;
import co.edu.uniandes.csw.paseadores.ejb.HorarioLogic;
import co.edu.uniandes.csw.paseadores.ejb.PaseadorHorarioLogic;
import co.edu.uniandes.csw.paseadores.entities.HorarioEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import java.util.ArrayList;
import java.util.List;
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
 * @author Daniel Mateo Guatibonza Solano
 */
@Produces("application/json")
@Consumes("application/json")
public class PaseadorHorarioResource {
    
    @Inject
    private PaseadorHorarioLogic paseadorHorarioLogic;
    
    @Inject 
    private HorarioLogic horarioLogic;
    
    @POST
    @Path("{horarioId: \\d+}")
    public HorarioDetailDTO addHorario(@PathParam("horarioId") Long horarioId, @PathParam("paseadorId") Long paseadorId) {
        if (horarioLogic.getHorario(horarioId) == null) {
            throw new WebApplicationException("El recurso /horarios/" + horarioId + " no existe.", 404);
        }
        return new HorarioDetailDTO(paseadorHorarioLogic.addHorario(horarioId, paseadorId));
    }
    
    @GET
    public List<HorarioDetailDTO> getHorarios(@PathParam("paseadorId") Long paseadorId) {
        return horariosListEntity2DTO(paseadorHorarioLogic.getHorarios(paseadorId));
    }
    
    @GET
    @Path("{horarioId: \\d+}")
    public HorarioDetailDTO getHorario(@PathParam("paseadorId") Long paseadorId, @PathParam("horarioId") Long horarioId) throws BusinessLogicException {
        if (horarioLogic.getHorario(horarioId) == null) {
            throw new WebApplicationException("El recurso /horarios/" + horarioId + " no existe.", 404);
        }
        return new HorarioDetailDTO(paseadorHorarioLogic.getHorario(paseadorId, horarioId));
    }
    
    @PUT
    public List<HorarioDetailDTO> replaceHorarios(@PathParam("paseadorId") Long paseadorId, List<HorarioDetailDTO> horarios) {
        for (HorarioDetailDTO horario : horarios) {
            if (horarioLogic.getHorario(horario.getId()) == null) {
                throw new WebApplicationException("El recurso /horarios/" + horario.getId() + " no existe.", 404);
            }
        }
        return horariosListEntity2DTO(paseadorHorarioLogic.replaceHorarios(paseadorId, horariosListDTO2Entity(horarios)));
    }
    
    @DELETE
    @Path("{horarioId: \\d+}")
    public void removeHorario(@PathParam("paseadorId") Long paseadorId, @PathParam("horarioId") Long horarioId) {
        if (horarioLogic.getHorario(horarioId) == null) {
            throw new WebApplicationException("El recurso /horarios/" + horarioId + " no existe.", 404);
        }
        paseadorHorarioLogic.removeHorario(paseadorId, horarioId);
    }
    
    private List<HorarioDetailDTO> horariosListEntity2DTO(List<HorarioEntity> horariosEntities) {
        List<HorarioDetailDTO> lista = new ArrayList<>();
        for (HorarioEntity horarioEntity : horariosEntities) {
            lista.add(new HorarioDetailDTO(horarioEntity));
        }
        return lista;
    }
    
    private List<HorarioEntity> horariosListDTO2Entity(List<HorarioDetailDTO> horariosDtos) {
        List<HorarioEntity> lista = new ArrayList<>();
        for (HorarioDetailDTO horarioDtos : horariosDtos) {
            lista.add(horarioDtos.toEntity());
        }
        return lista;
    }
}
