/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.resources;

import co.edu.uniandes.csw.paseadores.dtos.PaseadorDetailDTO;
import co.edu.uniandes.csw.paseadores.ejb.HorarioPaseadorLogic;
import co.edu.uniandes.csw.paseadores.ejb.PaseadorLogic;
import co.edu.uniandes.csw.paseadores.entities.PaseadorEntity;
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
 * @author Edgar Camilo Diaz Suarez
 */
@Consumes("application/json")
@Produces("application/json")
public class HorarioPaseadorResource {
    
    @Inject
    private HorarioPaseadorLogic horarioPaseadorLogic;
    
    @Inject
    private PaseadorLogic paseadorLogic;
    
    @POST
    @Path("{paseadoresId: \\d+}")
    public PaseadorDetailDTO addPaseador(@PathParam("horariosId") Long horariosId, @PathParam("paseadoresId")Long paseadoresId){
        if(paseadorLogic.getPaseador(paseadoresId) == null){
            throw new WebApplicationException(" el recurso /paseadores/"+ paseadoresId + "  no existe",404);
        }
        return new PaseadorDetailDTO(horarioPaseadorLogic.addPaseador(horariosId, paseadoresId));
    }
    
    @GET
    public List<PaseadorDetailDTO> getPaseadores(@PathParam("horariosId")Long horariosId){
        return paseadoresListEntity2DTO(horarioPaseadorLogic.getPaseadores(horariosId));
    }
    
    @GET
    @Path("{paseadoresId: \\d+}")
    public PaseadorDetailDTO getPaseador(@PathParam("horariosId")Long horariosId, @PathParam("paseadoresId")Long paseadoresId) throws BusinessLogicException {
        if(paseadorLogic.getPaseador(paseadoresId) == null){
            throw new WebApplicationException("El  recurso/paseadores/"+paseadoresId+"  no existe.",404);
        }
        return new PaseadorDetailDTO(horarioPaseadorLogic.getPaseador(paseadoresId, horariosId));
    }

    @PUT
    public List<PaseadorDetailDTO> replacePaseadores(@PathParam("horariosId") Long horariosId, List<PaseadorDetailDTO> paseadores){
        for(PaseadorDetailDTO paseador : paseadores){
            if(paseadorLogic.getPaseador(paseador.getId()) == null){
                throw new WebApplicationException("EL recurso/paseadores/"+paseador.getId()+ " no existe.",404);
            }
        }
        return paseadoresListEntity2DTO(horarioPaseadorLogic.replacePaseador(horariosId, paseadoresListDTO2Entity(paseadores)));
    }
    
    @DELETE
    @Path("{paseadoresId: \\d+}")
    public void removePaseador(@PathParam("horariosId")Long horariosId, @PathParam("paseadoresId")Long paseadoresId){
        if(paseadorLogic.getPaseador(paseadoresId) == null){
            throw new WebApplicationException("El Recurso /paseadores/"+ paseadoresId+ "no existe",404);
        }
        horarioPaseadorLogic.removePaseador(horariosId, paseadoresId);
    }
    
    private List<PaseadorDetailDTO> paseadoresListEntity2DTO(List<PaseadorEntity> paseadores) {
        List<PaseadorDetailDTO> list =new ArrayList<>();
        for(PaseadorEntity entity: paseadores){
            list.add(new PaseadorDetailDTO(entity));
        }
        return list;
    }

    private List<PaseadorEntity> paseadoresListDTO2Entity(List<PaseadorDetailDTO> paseadores) {
        List<PaseadorEntity> list = new ArrayList<>();
        for(PaseadorDetailDTO dto: paseadores){
            list.add(dto.toEntity());
        }
        return list;
    }
    
}
