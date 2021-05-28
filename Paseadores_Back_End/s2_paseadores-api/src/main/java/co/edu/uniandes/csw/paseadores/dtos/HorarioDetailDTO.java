/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.dtos;

import co.edu.uniandes.csw.paseadores.entities.HorarioEntity;
import co.edu.uniandes.csw.paseadores.entities.PaseadorEntity;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Edgar Camilo Diaz Suarez
 */
public class HorarioDetailDTO extends HorarioDTO{
   
    private List<PaseadorDTO> paseadores;
    
    public HorarioDetailDTO(){
        super();
    }
    
    public HorarioDetailDTO(HorarioEntity horarioEntity){
        super(horarioEntity);
        if(horarioEntity.getPaseador() != null){
            paseadores = new ArrayList<>();
            for(PaseadorEntity entityPaseador : horarioEntity.getPaseador()){
                paseadores.add(new PaseadorDTO(entityPaseador));
            }
        }
    }
    
    @Override
    public HorarioEntity toEntity(){
        HorarioEntity horarioEntity = super.toEntity();
        if(getPaseadores() != null){
            List<PaseadorEntity> paseadoresEntity = new ArrayList<>();
            for(PaseadorDTO dtoPaseador : getPaseadores()){
                paseadoresEntity.add(dtoPaseador.toEntity());
            }
            horarioEntity.setPaseador(paseadoresEntity);
        }
        return horarioEntity;
    }

    /**
     * @return the paseadores
     */
    public List<PaseadorDTO> getPaseadores() {
        return paseadores;
    }

    /**
     * @param paseadores the paseadores to set
     */
    public void setPaseadores(List<PaseadorDTO> paseadores) {
        this.paseadores = paseadores;
    }
}
