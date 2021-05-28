/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.dtos;

import co.edu.uniandes.csw.paseadores.adapters.TimeStampAdapter;
import co.edu.uniandes.csw.paseadores.entities.ContratoPaseoEntity;
import java.io.Serializable;
import java.util.Date;
import javax.xml.bind.annotation.adapters.XmlJavaTypeAdapter;

/**
 *
 * @author julian Oliveros<je.oliverosf at uniandes.edu.co>
 */
public class ContratoPaseoDTO implements Serializable {

    private Long id;
    
    @XmlJavaTypeAdapter(TimeStampAdapter.class)
    private Date horaEncuentro;
    
    private PuntoDTO sitioEncuentro;
    
    private PaseoDTO paseo;
    
    private PagoClienteDTO pago;
    
    private ClienteDTO cliente;
    
    private PerroDTO perro;
    
    private CalificacionDTO calificacionPaseador;
    
    private CalificacionDTO calificacionRecorrido;
    
    
    
        
    public ContratoPaseoDTO()
    {
        
    }    
 
    /**
     * Constructor a partir de la perro
     *
     * @param contratoPaseo La entidad de perro
     * @param llamado
    */
    public ContratoPaseoDTO(ContratoPaseoEntity contratoPaseo, boolean llamado)
    {
        if(contratoPaseo!=null)
        {
            this.id = contratoPaseo.getId();
            this.horaEncuentro = contratoPaseo.getHoraEncuentro();
            
            if(!llamado &&contratoPaseo.getSitioEncuentro()!=null){
                this.sitioEncuentro = new PuntoDTO(contratoPaseo.getSitioEncuentro());
            }else{
                this.sitioEncuentro = null;
            }
            
            if(!llamado &&contratoPaseo.getPaseo()!=null){
                this.paseo = new PaseoDTO(contratoPaseo.getPaseo());
            }else{
                this.paseo = null;
            }
            
            if(!llamado && contratoPaseo.getPago()!=null)
            {
                this.pago = new PagoClienteDTO(contratoPaseo.getPago(), true);
            }else{
                this.pago = null;
            }
                        
            if(!llamado &&contratoPaseo.getCliente()!=null){
                this.cliente = new ClienteDTO(contratoPaseo.getCliente());
            }else{
                this.cliente = null;
            }

            if(!llamado && contratoPaseo.getPerro()!=null){
                this.perro = new PerroDTO(contratoPaseo.getPerro());
            }else{
                this.perro= null;
            } 
            
            if(!llamado &&contratoPaseo.getCalificacionPaseador()!=null){
                this.calificacionPaseador = new CalificacionDTO(contratoPaseo.getCalificacionPaseador(),true);
            }else{
                this.calificacionPaseador = null;
            }
            if(!llamado &&contratoPaseo.getCalificacionRecorrido()!=null){
                this.calificacionRecorrido = new CalificacionDTO(contratoPaseo.getCalificacionRecorrido(),true);
            }else{
                this.calificacionRecorrido = null;
            }



            
        }
    }
    
    /*
    * Constructor a partir de la informacion 
    * @return la entidad de contratoPaseo asociada
    */
    public ContratoPaseoEntity toEntity(boolean llamado)
    {
        ContratoPaseoEntity contratoPaseo = new ContratoPaseoEntity();
        
        contratoPaseo.setId(this.getId());
        contratoPaseo.setHoraEncuentro(this.getHoraEncuentro());
        


        if( this.getPerro()!=null)
        {
            contratoPaseo.setPerro(this.getPerro().toEntity());
        }
        
        if(this.getCliente()!=null)
        {
           contratoPaseo.setCliente(this.getCliente().toEntity());
        }
        if(this.getPaseo()!=null)
        {
           contratoPaseo.setPaseo(this.getPaseo().toEntity());
        }
        
        if(!llamado && this.getCalificacionPaseador()!=null)
        {
           contratoPaseo.setCalificacionPaseador(this.getCalificacionPaseador().toEntity(true));
        }
        
        if(!llamado && this.getCalificacionRecorrido()!=null)
        {
           contratoPaseo.setCalificacionRecorrido(this.getCalificacionRecorrido().toEntity(true));
        }    
        
        if(this.getPago()!=null){
            contratoPaseo.setPago(this.getPago().toEntity(true));
        }
        if(this.getSitioEncuentro() != null)
        {
            contratoPaseo.setSitioEncuentro(this.getSitioEncuentro().toEntity());
        }

        return contratoPaseo;
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
     * @return the horaEncuentro
     */
    public Date getHoraEncuentro() {
        return horaEncuentro;
    }

    /**
     * @param horaEncuentro the horaEncuentro to set
     */
    public void setHoraEncuentro(Date horaEncuentro) {
        this.horaEncuentro = horaEncuentro;
    }

    /**
     * @return the sitioEncuentro
     */
    public PuntoDTO getSitioEncuentro() {
        return sitioEncuentro;
    }

    /**
     * @param sitioEncuentro the sitioEncuentro to set
     */
    public void setSitioEncuentro(PuntoDTO sitioEncuentro) {
        this.sitioEncuentro = sitioEncuentro;
    }

    /**
     * @return the cliente
     */
    public ClienteDTO getCliente() {
        return cliente;
    }

    /**
     * @param cliente the cliente to set
     */
    public void setCliente(ClienteDTO cliente) {
        this.cliente = cliente;
    }

    /**
     * @return the paseo
     */
    public PaseoDTO getPaseo() {
        return paseo;
    }

    /**
     * @param paseo the paseo to set
     */
    public void setPaseo(PaseoDTO paseo) {
        this.paseo = paseo;
    }

    /**
     * @return the pago
     */
    public PagoClienteDTO getPago() {
        return pago;
    }

    /**
     * @param pago the pago to set
     */
    public void setPago(PagoClienteDTO pago) {
        this.pago = pago;
    }

    /**
     * @return the calificacionPaseador
     */
    public CalificacionDTO getCalificacionPaseador() {
        return calificacionPaseador;
    }

    /**
     * @param calificacionPaseador the calificacionPaseador to set
     */
    public void setCalificacionPaseador(CalificacionDTO calificacionPaseador) {
        this.calificacionPaseador = calificacionPaseador;
    }

    /**
     * @return the calificacionRecorrido
     */
    public CalificacionDTO getCalificacionRecorrido() {
        return calificacionRecorrido;
    }

    /**
     * @param calificacionRecorrido the calificacionRecorrido to set
     */
    public void setCalificacionRecorrido(CalificacionDTO calificacionRecorrido) {
        this.calificacionRecorrido = calificacionRecorrido;
    }

    /**
     * @return the perro
     */
    public PerroDTO getPerro() {
        return perro;
    }

    /**
     * @param perro the perro to set
     */
    public void setPerro(PerroDTO perro) {
        this.perro = perro;
    }



   
    

}
