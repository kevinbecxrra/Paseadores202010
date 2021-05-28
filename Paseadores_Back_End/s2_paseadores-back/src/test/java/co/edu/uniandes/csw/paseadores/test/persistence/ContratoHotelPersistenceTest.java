/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.persistence;

import co.edu.uniandes.csw.paseadores.entities.ContratoHotelEntity;
import co.edu.uniandes.csw.paseadores.persistence.ContratoHotelPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Edgar Camilo Diaz Suarez
 */
@RunWith(Arquillian.class)
public class ContratoHotelPersistenceTest {
    
     @Inject
    ContratoHotelPersistence contratoHotelPersistence;
     
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<ContratoHotelEntity> data = new ArrayList();
    
    @Deployment public static JavaArchive createDeployment(){
       return ShrinkWrap.create(JavaArchive.class)
               .addPackage(ContratoHotelEntity.class.getPackage())
               .addPackage(ContratoHotelPersistence.class.getPackage())
               .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
               .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
   @Before
    public void configTest(){
        try{
            utx.begin();
            em.joinTransaction();
            clearData();
            insertData();
            utx.commit();
        }catch(Exception e){
           e.printStackTrace();
           try{
               utx.rollback();
           }catch(Exception e1){
               e1.printStackTrace();
           }
        }
    }
    
    private void clearData(){
        em.createQuery("delete from HoraHotelEntity").executeUpdate();
    }
     
    private void insertData(){
        PodamFactory factory = new PodamFactoryImpl();
        for(int i = 0; i<3 ; i++ ){
            ContratoHotelEntity entity = factory.manufacturePojo(ContratoHotelEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    @Test
    public void createContratoHotelTest(){
        
        PodamFactory factory = new PodamFactoryImpl();
        ContratoHotelEntity newContratoHotel = factory.manufacturePojo(ContratoHotelEntity.class);
        ContratoHotelEntity result = contratoHotelPersistence.create(newContratoHotel);
        
  
        Assert.assertNotNull(result);
        
        ContratoHotelEntity contratoHotel = em.find(ContratoHotelEntity.class, result.getId());
        
        Assert.assertEquals(newContratoHotel.getCosto(),contratoHotel.getCosto());
        Assert.assertEquals(newContratoHotel.getCalificacionHotel(),contratoHotel.getCalificacionHotel());
        Assert.assertEquals(newContratoHotel.getCliente(),contratoHotel.getCliente());
        Assert.assertEquals(newContratoHotel.getCuidadoEspecial(),contratoHotel.getCuidadoEspecial());
        Assert.assertEquals(newContratoHotel.getHorasHotel(),contratoHotel.getHorasHotel());
        Assert.assertEquals(newContratoHotel.getPago(),contratoHotel.getPago());
        Assert.assertEquals(newContratoHotel.getPerro(),contratoHotel.getPerro());
        
    }
    
    @Test
    public void findContratoHotelTest(){
        ContratoHotelEntity contratoHotel = data.get(0);
        ContratoHotelEntity newContratoHotel = contratoHotelPersistence.find(contratoHotel.getId());
        Assert.assertNotNull(newContratoHotel);
        
        Assert.assertEquals(contratoHotel.getId(), newContratoHotel.getId());
        Assert.assertEquals(newContratoHotel.getCosto(),contratoHotel.getCosto());
        Assert.assertEquals(newContratoHotel.getCalificacionHotel(),contratoHotel.getCalificacionHotel());
        Assert.assertEquals(newContratoHotel.getCliente(),contratoHotel.getCliente());
        Assert.assertEquals(newContratoHotel.getCuidadoEspecial(),contratoHotel.getCuidadoEspecial());
        Assert.assertEquals(newContratoHotel.getHorasHotel(),contratoHotel.getHorasHotel());
        Assert.assertEquals(newContratoHotel.getPago(),contratoHotel.getPago());
        Assert.assertEquals(newContratoHotel.getPerro(),contratoHotel.getPerro());
    
    }
    
    @Test
    public void updateContratoHotelTest(){
        ContratoHotelEntity contratoHotel = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ContratoHotelEntity newContratoHotel = factory.manufacturePojo(ContratoHotelEntity.class);
        
        newContratoHotel.setId(contratoHotel.getId());
        contratoHotelPersistence.update(newContratoHotel);
        ContratoHotelEntity resp = em.find(ContratoHotelEntity.class,contratoHotel.getId());
    
        Assert.assertEquals(newContratoHotel.getCosto(),resp.getCosto());
        Assert.assertEquals(newContratoHotel.getCalificacionHotel(),resp.getCalificacionHotel());
        Assert.assertEquals(newContratoHotel.getCliente(),resp.getCliente());
        Assert.assertEquals(newContratoHotel.getCuidadoEspecial(),resp.getCuidadoEspecial());
        Assert.assertEquals(newContratoHotel.getHorasHotel(),resp.getHorasHotel());
        Assert.assertEquals(newContratoHotel.getPago(),resp.getPago());
        Assert.assertEquals(newContratoHotel.getPerro(),resp.getPerro());
    }
    
    @Test
    public void deleteContratoHotelTest(){
        ContratoHotelEntity contratoHotel = data.get(0);
        contratoHotelPersistence.delete(contratoHotel.getId());
        ContratoHotelEntity deleted = em.find(ContratoHotelEntity.class,contratoHotel.getId());
        Assert.assertNull(deleted);
    }
}
