/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.dtos;

import co.edu.uniandes.csw.paseadores.entities.ClienteEntity;
import co.edu.uniandes.csw.paseadores.entities.ContratoHotelEntity;
import co.edu.uniandes.csw.paseadores.entities.ContratoPaseoEntity;
import co.edu.uniandes.csw.paseadores.entities.PagoClienteEntity;
import co.edu.uniandes.csw.paseadores.entities.PerroEntity;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 *
 * @author Alvaro Plata
 */
public class ClienteDetailDTO extends ClienteDTO implements Serializable{
    
   private List<PerroDTO> perros;
   
   private List<ContratoHotelDTO> contratosHotel;
   
   private List<PagoClienteDTO> pagos;
   
   private List<ContratoPaseoDTO> contratosPaseo;
   
   public ClienteDetailDTO()
   {
        super();
   }
   
   public ClienteDetailDTO(ClienteEntity clienteEntity)
   {
        super(clienteEntity);
        if (clienteEntity != null) {
            perros = new ArrayList<>();
            for (PerroEntity entityPerro : clienteEntity.getPerros()) {
                perros.add(new PerroDTO(entityPerro));
            }
            contratosHotel = new ArrayList();
            for (ContratoHotelEntity entityContratoHotel : clienteEntity.getContratosHotel()) {
                contratosHotel.add(new ContratoHotelDTO(entityContratoHotel));
            }
            pagos = new ArrayList();
            for (PagoClienteEntity entityPagoCliente : clienteEntity.getPagos()) {
                pagos.add(new PagoClienteDTO(entityPagoCliente, false));
            }
            contratosPaseo = new ArrayList();
            for (ContratoPaseoEntity entityContratoPaseo : clienteEntity.getContratosPaseo()) {
                contratosPaseo.add(new ContratoPaseoDTO(entityContratoPaseo, false));
            }
        }
    }
   
    @Override
    public ClienteEntity toEntity() {
        ClienteEntity clienteEntity = super.toEntity();
        if (getPerros() != null) {
            List<PerroEntity> perrosEntity = new ArrayList<>();
            for (PerroDTO dtoPerro : getPerros()) {
                perrosEntity.add(dtoPerro.toEntity());
            }
            clienteEntity.setPerros(perrosEntity);
        }
        if (getContratosHotel() != null) {
            List<ContratoHotelEntity> contratosHotelEntity = new ArrayList<>();
            for (ContratoHotelDTO dtoContratoHotel : getContratosHotel()) {
                contratosHotelEntity.add(dtoContratoHotel.toEntity(false));
            }
            clienteEntity.setContratosHotel(contratosHotelEntity);
        }
        if (getPagos() != null) {
            List<PagoClienteEntity> pagosClienteEntity = new ArrayList<>();
            for (PagoClienteDTO dtoPagoCliente : getPagos()) {
                pagosClienteEntity.add(dtoPagoCliente.toEntity(false));
            }
            clienteEntity.setPagos(pagosClienteEntity);
        }
        if (getContratosPaseo() != null) {
            List<ContratoPaseoEntity> contratosPaseoEntity = new ArrayList<>();
            for (ContratoPaseoDTO dtoContratoPaseo : getContratosPaseo()) {
                contratosPaseoEntity.add(dtoContratoPaseo.toEntity(false));
            }
            clienteEntity.setContratosPaseo(contratosPaseoEntity);
        }
        return clienteEntity;
    }
    
    /**
     * @return the perros
     */
    public List<PerroDTO> getPerros() {
        return perros;
    }

    /**
     * @param perros the perros to set
     */
    public void setPerros(List<PerroDTO> perros) {
        this.perros = perros;
    }

    /**
     * @return the contratosHotel
     */
    public List<ContratoHotelDTO> getContratosHotel() {
        return contratosHotel;
    }

    /**
     * @param contratosHotel the contratosHotel to set
     */
    public void setContratosHotel(List<ContratoHotelDTO> contratosHotel) {
        this.contratosHotel = contratosHotel;
    }

    /**
     * @return the pagos
     */
    public List<PagoClienteDTO> getPagos() {
        return pagos;
    }

    /**
     * @param pagos the pagos to set
     */
    public void setPagos(List<PagoClienteDTO> pagos) {
        this.pagos = pagos;
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
