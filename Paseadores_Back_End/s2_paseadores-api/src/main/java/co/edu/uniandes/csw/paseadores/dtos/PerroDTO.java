/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.dtos;

import co.edu.uniandes.csw.paseadores.entities.PerroEntity;
import java.io.Serializable;

/**
 *
 * @author julian Oliveros<je.oliverosf at uniandes.edu.co>
 */
public class PerroDTO implements Serializable {

    private ClienteDTO propietario;
    
    private String nombre;
    
    private String raza;

    private String idPerro;
    
    private Integer edad;
    
    private Long id;
    
    private String imagen;
    
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
    
    /**
     * @return the propietario
     */
    public ClienteDTO getPropietario() {
        return propietario;
    }

    /**
     * @param propietario the propietario to set
     */
    public void setPropietario(ClienteDTO propietario) {
        this.propietario = propietario;
    }

    /*
    * Relación a un cliente   
    * dado que esta tiene cardinalidad 1.
     */  
    
    public PerroDTO ()
    {
        
    }
    
    /**
     * Constructor a partir de la perro
     *
     * @param perroEntity La entidad de perro
     */
    public PerroDTO(PerroEntity perroEntity)
    {
        if(perroEntity!=null)
        {
            this.edad = perroEntity.getEdad();
            this.idPerro = perroEntity.getIdPerro();
            this.nombre = perroEntity.getNombre();
            this.raza = perroEntity.getRaza();
            this.id = perroEntity.getId();
            this.imagen = perroEntity.getImagen();
            
            if(perroEntity.getCliente()!=null){
                this.propietario = new ClienteDTO(perroEntity.getCliente());
            }
            else{
                this.propietario = null;
            }
        }
    }
    
    /*
    * Constructor a partir de la informacion 
    * @return la entidad de perro asociada
    */
    
    public PerroEntity toEntity()
    {
        PerroEntity perro = new PerroEntity();
        
        perro.setEdad(this.edad);
        perro.setIdPerro(this.idPerro);
        perro.setNombre(this.nombre);
        perro.setRaza(this.raza);
        perro.setId(this.getId());
        perro.setImagen(this.imagen);
        
        if(this.getPropietario()!=null)
        {
            perro.setCliente(this.getPropietario().toEntity());
        }
        return perro;
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
     * @return the nombre
     */
    public String getNombre() {
        return nombre;
    }

    /**
     * @param pNombreDelPerro the nombre to set
     */
    public void setNombre(String pNombreDelPerro) {
        this.nombre = pNombreDelPerro;
    }

    /**
     * @return the raza
     */
    public String getRaza() {
        return raza;
    }

    /**
     * @param pRazaDelPerro the raza to set
     */
    public void setRaza(String pRazaDelPerro) {
        this.raza = pRazaDelPerro;
    }

    /**
     * @return the idPerro
     */
    public String getIdPerro() {
        return idPerro;
    }

    /**
     * @param pIdDelPerro the idPerro to set
     */
    public void setIdPerro(String pIdDelPerro) {
        this.idPerro = pIdDelPerro;
    }

    /**
     * @return the edad
     */
    public Integer getEdad() {
        //se crea el metodo de obtener la edad
        return edad;
    }

    /**
     * @param pLaEdadDelPerro the edad to set
     */
    public void setEdad(Integer pLaEdadDelPerro){ 
        this.edad = pLaEdadDelPerro;
    }    
}
