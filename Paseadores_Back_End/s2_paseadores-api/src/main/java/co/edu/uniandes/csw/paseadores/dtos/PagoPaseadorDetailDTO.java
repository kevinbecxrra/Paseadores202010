/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.dtos;

import co.edu.uniandes.csw.paseadores.entities.PagoPaseadorEntity;
import co.edu.uniandes.csw.paseadores.entities.PaseoEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel Mateo Guatibonza Solano
 */
public class PagoPaseadorDetailDTO extends PagoPaseadorDTO implements Serializable {
    
    private List<PaseoDTO> porPagar;
    
    public PagoPaseadorDetailDTO()
    {
        super();
    }
    
    public PagoPaseadorDetailDTO(PagoPaseadorEntity pagoPaseadorEntity)
    {
        super(pagoPaseadorEntity);
        if (pagoPaseadorEntity != null) {
            porPagar = new ArrayList<>();
            for (PaseoEntity paseo : pagoPaseadorEntity.getPaseos()) {
                porPagar.add(new PaseoDTO(paseo));
            }
        }
    }
    
    @Override
    public PagoPaseadorEntity toEntity() {
        PagoPaseadorEntity pagoPaseadorEntity = super.toEntity();
        if (porPagar != null) {
            List<PaseoEntity> paseosAPagar = new ArrayList<>();
            for (PaseoDTO dtoPaseo : porPagar) {
                paseosAPagar.add(dtoPaseo.toEntity());
            }
            pagoPaseadorEntity.setPaseos(paseosAPagar);
        }
        return pagoPaseadorEntity;
    }

    /**
     * @return the porPagar
     */
    public List<PaseoDTO> getPorPagar() {
        return porPagar;
    }

    /**
     * @param porPagar the porPagar to set
     */
    public void setPorPagar(List<PaseoDTO> porPagar) {
        this.porPagar = porPagar;
    }
}
