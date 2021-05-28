package co.edu.uniandes.csw.paseadores.dtos;

import co.edu.uniandes.csw.paseadores.entities.CalificacionEntity;
import java.io.Serializable;

/**
 *
 * @author Daniel Mateo Guatibonza Solano
 */
public class CalificacionDTO implements Serializable {
    
    private Long id;
    
    private Integer valoracion;
    
    private String comentario;
    
    private ContratoPaseoDTO contratoRecorrido;
    
    private ContratoPaseoDTO contratoPaseador;
    
    private ContratoHotelDTO contratoHotel;
    
    public CalificacionDTO()
    {
        
    }
    
    public CalificacionDTO(CalificacionEntity calificacionEntity, boolean llamado)
    {
        if (calificacionEntity != null) {
            this.id = calificacionEntity.getId();
            this.valoracion = calificacionEntity.getValoracion();
            this.comentario = calificacionEntity.getComentario();
            if(!llamado && calificacionEntity.getContratoHotel() != null)
                contratoHotel = new ContratoHotelDTO(calificacionEntity.getContratoHotel());
            if(!llamado && calificacionEntity.getContratoPaseador() != null)
                contratoPaseador = new ContratoPaseoDTO(calificacionEntity.getContratoPaseador(), true);
            if(!llamado && calificacionEntity.getContratoRecorrido() != null)
                contratoRecorrido = new ContratoPaseoDTO(calificacionEntity.getContratoRecorrido(), true);
        }
    }
    
    public CalificacionEntity toEntity(boolean llamado)
    {
        CalificacionEntity calificacionEntity = new CalificacionEntity();
        calificacionEntity.setId(this.getId());
        calificacionEntity.setValoracion(this.getValoracion());
        calificacionEntity.setComentario(this.getComentario());
        if(!llamado && contratoPaseador != null)
            calificacionEntity.setContratoPaseador(contratoPaseador.toEntity(true));
        if(!llamado && contratoRecorrido != null)
            calificacionEntity.setContratoRecorrido(contratoRecorrido.toEntity(true));
        if(!llamado && contratoHotel != null)
            calificacionEntity.setContratoHotel(contratoHotel.toEntity(true));
        return calificacionEntity;
    }
    
    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }

    /**
     * @param id the id to set
     */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the valoracion
     */
    public Integer getValoracion() {
        return valoracion;
    }

    /**
     * @param valoracion the valoracion to set
     */
    public void setValoracion(Integer valoracion) {
        this.valoracion = valoracion;
    }

    /**
     * @return the comentario
     */
    public String getComentario() {
        return comentario;
    }

    /**
     * @param comentario the comentario to set
     */
    public void setComentario(String comentario) {
        this.comentario = comentario;
    }

    /**
     * @return the contratoRecorrido
     */
    public ContratoPaseoDTO getContratoRecorrido() {
        return contratoRecorrido;
    }

    /**
     * @param contratoRecorrido the contratoRecorrido to set
     */
    public void setContratoRecorrido(ContratoPaseoDTO contratoRecorrido) {
        this.contratoRecorrido = contratoRecorrido;
    }

    /**
     * @return the contratoPaseador
     */
    public ContratoPaseoDTO getContratoPaseador() {
        return contratoPaseador;
    }

    /**
     * @param contratoPaseador the contratoPaseador to set
     */
    public void setContratoPaseador(ContratoPaseoDTO contratoPaseador) {
        this.contratoPaseador = contratoPaseador;
    }

    /**
     * @return the contratoHotel
     */
    public ContratoHotelDTO getContratoHotel() {
        return contratoHotel;
    }

    /**
     * @param contratoHotel the contratoHotel to set
     */
    public void setContratoHotel(ContratoHotelDTO contratoHotel) {
        this.contratoHotel = contratoHotel;
    }
}

