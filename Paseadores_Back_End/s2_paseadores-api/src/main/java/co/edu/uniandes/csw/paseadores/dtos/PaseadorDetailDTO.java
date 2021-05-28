/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.dtos;

import co.edu.uniandes.csw.paseadores.entities.HorarioEntity;
import co.edu.uniandes.csw.paseadores.entities.PagoPaseadorEntity;
import co.edu.uniandes.csw.paseadores.entities.PaseadorEntity;
import co.edu.uniandes.csw.paseadores.entities.PaseoEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Daniel Mateo Guatibonza Solano
 */
public class PaseadorDetailDTO extends PaseadorDTO implements Serializable {
    
    private List<HorarioDTO> horariosDisponibles;
    
    private List<PaseoDTO> paseos;
    
    private List<PagoPaseadorDTO> pagos;
    
    public PaseadorDetailDTO()
    {
        super();
    }
    
    public PaseadorDetailDTO(PaseadorEntity paseador)
    {
        super(paseador);
        if(paseador != null)
        {
            horariosDisponibles = new ArrayList<>();
            for (HorarioEntity horario : paseador.getHorariosDisponibles()) {
                horariosDisponibles.add(new HorarioDTO(horario));
            }
            paseos = new ArrayList();
            for (PaseoEntity paseo : paseador.getPaseos()) {
                paseos.add(new PaseoDTO(paseo));
            }
            pagos = new ArrayList<>();
            for (PagoPaseadorEntity pagoPaseador : paseador.getPagos()) {
                pagos.add(new PagoPaseadorDTO(pagoPaseador));
            }
        }
    }
    
    @Override
    public PaseadorEntity toEntity() {
        PaseadorEntity paseadorEntity = super.toEntity();
        if (horariosDisponibles != null) {
            List<HorarioEntity> listaHorarios = new ArrayList<>();
            for (HorarioDTO dtoHorario : horariosDisponibles) {
                listaHorarios.add(dtoHorario.toEntity());
            }
            paseadorEntity.setHorariosDisponibles(listaHorarios);
        }
        if (paseos != null) {
            List<PaseoEntity> listaPaseos = new ArrayList<>();
            for (PaseoDTO dtoPaseo : paseos) {
                listaPaseos.add(dtoPaseo.toEntity());
            }
            paseadorEntity.setPaseos(listaPaseos);
        }
        if (pagos != null) {
            List<PagoPaseadorEntity> listaPagos = new ArrayList<>();
            for (PagoPaseadorDTO dtoPago : pagos) {
                listaPagos.add(dtoPago.toEntity());
            }
            paseadorEntity.setPagos(listaPagos);
        }
        return paseadorEntity;
    }

    /**
     * @return the horariosDisponibles
     */
    public List<HorarioDTO> getHorariosDisponibles() {
        return horariosDisponibles;
    }

    /**
     * @param horariosDisponibles the horariosDisponibles to set
     */
    public void setHorariosDisponibles(List<HorarioDTO> horariosDisponibles) {
        this.horariosDisponibles = horariosDisponibles;
    }

    /**
     * @return the paseos
     */
    public List<PaseoDTO> getPaseos() {
        return paseos;
    }

    /**
     * @param paseos the paseos to set
     */
    public void setPaseos(List<PaseoDTO> paseos) {
        this.paseos = paseos;
    }

    /**
     * @return the pagos
     */
    public List<PagoPaseadorDTO> getPagos() {
        return pagos;
    }

    /**
     * @param pagos the pagos to set
     */
    public void setPagos(List<PagoPaseadorDTO> pagos) {
        this.pagos = pagos;
    }
}
