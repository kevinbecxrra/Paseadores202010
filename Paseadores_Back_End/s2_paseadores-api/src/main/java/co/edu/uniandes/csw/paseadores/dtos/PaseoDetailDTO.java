/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.dtos;

import co.edu.uniandes.csw.paseadores.entities.ContratoPaseoEntity;
import co.edu.uniandes.csw.paseadores.entities.PaseoEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Kevin Camilo Becerra Walteros
 */
public class PaseoDetailDTO extends PaseoDTO implements Serializable {

    private List<ContratoPaseoDTO> contratosPaseo;

    /**
     *
     */
    public PaseoDetailDTO() {
        super();
    }

    public PaseoDetailDTO(PaseoEntity paseoEntity) {
        super(paseoEntity);
        if (paseoEntity != null) {
            contratosPaseo = new ArrayList<>();
            for (ContratoPaseoEntity ctPaseo : paseoEntity.getContratosPaseo()) {
                contratosPaseo.add(new ContratoPaseoDTO(ctPaseo, false));
            }
        }
    }

    @Override
    public PaseoEntity toEntity() {
        PaseoEntity paseoEntity = super.toEntity();
        if (getContratosPaseo() != null) {
            List<ContratoPaseoEntity> paseosAContratar = new ArrayList<>();
            for (ContratoPaseoDTO dtoContratoPaseo : getContratosPaseo()) {
                paseosAContratar.add(dtoContratoPaseo.toEntity(false));
            }
            paseoEntity.setContratosPaseo(paseosAContratar);
        }
        return paseoEntity;
    }

    /**
     * @return the contratosPaseo
     */
    public List<ContratoPaseoDTO> getContratosPaseo() {
        return contratosPaseo;
    }

    /**
     * @param contratosPaseo the contratosPaseo to set
     */
    public void setContratosPaseo(List<ContratoPaseoDTO> contratosPaseo) {
        this.contratosPaseo = contratosPaseo;
    }
}
