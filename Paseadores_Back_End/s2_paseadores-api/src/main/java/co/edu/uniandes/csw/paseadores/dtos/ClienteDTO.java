/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.dtos;

import co.edu.uniandes.csw.paseadores.entities.ClienteEntity;
import java.io.Serializable;

/**
 *
 * @author Alvaro Plata
 */
public class ClienteDTO implements Serializable{
    
    private String identificacion;
    
    private String imagen;

    private Long id;
    
    private String correo;
    
    private String telefono;
    
    private String nombre;
    
    public ClienteDTO()
    {
        
    }
    
    public ClienteDTO(ClienteEntity clienteEntity) {
        if (clienteEntity != null) {
            this.id = clienteEntity.getId();
            this.correo = clienteEntity.getCorreo();
            this.identificacion = clienteEntity.getIdentificacion();
            this.nombre = clienteEntity.getNombre();
            this.telefono = clienteEntity.getTelefono();
            this.imagen = clienteEntity.getImagen();
        }
    }
    
    public ClienteEntity toEntity() {
        ClienteEntity clienteEntity = new ClienteEntity();
        clienteEntity.setId(this.id);
        clienteEntity.setCorreo(this.getCorreo());
        clienteEntity.setIdentificacion(this.getIdentificacion());
        clienteEntity.setNombre(this.getNombre());
        clienteEntity.setTelefono(this.getTelefono());
        clienteEntity.setImagen(this.getImagen());
        return clienteEntity;
    }

    /**
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param nombre the nombre to set
     */
    public void setNombre(String nombre) {
        this.nombre = nombre;
    }

    /**
     * @param identificacion the identificacion to set
     */
    public void setIdentificacion(String identificacion) {
        this.identificacion = identificacion;
    }
    
    /**
    * @param id the id to set
    */
    public void setId(Long id) {
        this.id = id;
    }

    /**
     * @return the correo
     */
    public String getCorreo() {
        return correo;
    }

    /**
     * @param correo the correo to set
     */
    public void setCorreo(String correo) {
        this.correo = correo;
    }

    /**
     * @return the telefono
     */
    public String getTelefono() {
        return telefono;
    }

    /**
    * @return the identificacion
    */
    public String getIdentificacion() {
        return identificacion;
    }
    
    /**
     * @param telefono the telefono to set
     */
    public void setTelefono(String telefono) {
        this.telefono = telefono;
    }

    /**
     * @return the id
     */
    public Long getId() {
        return id;
    }
    
    /**
     * @return the imagen
     */
    public String getImagen() {
        return imagen;
    }

    /**
     * @param imagen the imagen to set
     */
    public void setImagen(String imagen) {
        this.imagen = imagen;
    }
}
