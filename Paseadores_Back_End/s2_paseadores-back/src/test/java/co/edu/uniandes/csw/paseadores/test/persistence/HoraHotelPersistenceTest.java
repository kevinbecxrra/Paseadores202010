/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.persistence;

import co.edu.uniandes.csw.paseadores.entities.HoraHotelEntity;
import co.edu.uniandes.csw.paseadores.persistence.HoraHotelPersistence;
import java.util.ArrayList;
import java.util.Date;
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
public class HoraHotelPersistenceTest {
    
    
    @Inject
    private HoraHotelPersistence horaHotelPersistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<HoraHotelEntity> data = new ArrayList();
     
    @Deployment    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(HoraHotelEntity.class.getPackage())
                .addPackage(HoraHotelPersistence.class.getPackage())
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
            HoraHotelEntity entity = factory.manufacturePojo(HoraHotelEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    @Test
    public void createHoraHotelTest(){
       PodamFactory factory = new PodamFactoryImpl(); 
       HoraHotelEntity newHoraHotel = factory.manufacturePojo(HoraHotelEntity.class);
       HoraHotelEntity result = horaHotelPersistence.create(newHoraHotel);
      
       Assert.assertNotNull(result);
       
       HoraHotelEntity horaHotel = em.find(HoraHotelEntity.class, result.getId());
       
       Assert.assertEquals(newHoraHotel.getDia(), horaHotel.getDia());
       Assert.assertEquals(newHoraHotel.getCostoBase(), horaHotel.getCostoBase());
       Assert.assertEquals(newHoraHotel.getCupoMaximo(), horaHotel.getCupoMaximo());
       Assert.assertEquals(newHoraHotel.getDisponible(), horaHotel.getDisponible());
    }
    
   
    @Test
    public void findHoraHotelTest(){
        
        HoraHotelEntity horaHotel = data.get(0);
        HoraHotelEntity newHoraHotel = horaHotelPersistence.find(horaHotel.getId()) ;
        Assert.assertNotNull(newHoraHotel);
        Assert.assertEquals(horaHotel.getId(), newHoraHotel.getId());
        Assert.assertEquals(newHoraHotel.getDia(), horaHotel.getDia());
        Assert.assertEquals(newHoraHotel.getCostoBase(), horaHotel.getCostoBase());
        Assert.assertEquals(newHoraHotel.getCupoMaximo(), horaHotel.getCupoMaximo());
        Assert.assertEquals(newHoraHotel.getDisponible(), horaHotel.getDisponible());
    }
    
    
    
    @Test
    public void updateHoraHotelTest(){
        HoraHotelEntity horaHotel = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        HoraHotelEntity newHoraHotel = factory.manufacturePojo(HoraHotelEntity.class);
        
        newHoraHotel.setId(horaHotel.getId());
        horaHotelPersistence.update(newHoraHotel);
        HoraHotelEntity resp = em.find(HoraHotelEntity.class, horaHotel.getId());
        
        Assert.assertEquals(newHoraHotel.getDia(),resp.getDia());
        Assert.assertEquals(newHoraHotel.getCostoBase(), resp.getCostoBase());
        Assert.assertEquals(newHoraHotel.getCupoMaximo(), resp.getCupoMaximo());
        Assert.assertEquals(newHoraHotel.getDisponible(), resp.getDisponible());
    }
   
   
    @Test
    public void findByDiaInInRange() {
        Date diaInicio;
        Date diaInicioMayor = data.get(0).getDia();
        Date diaInicioMenor = data.get(0).getDia();
        int posicionMayor = 0; int posicionMenor = 0;
        for(int i=1; i < data.size(); i++)
        {
            if(diaInicioMayor.compareTo(data.get(i).getDia()) < 0)
            {
                diaInicioMayor = data.get(i).getDia();
                posicionMayor = i;
            }
            if(diaInicioMenor.compareTo(data.get(i).getDia())> 0)
            {
                diaInicioMenor = data.get(i).getDia();
                posicionMenor = i;
            }
        }
        
        switch (posicionMayor+posicionMenor) {
            case 1:
                diaInicio = data.get(2).getDia();
                break;
            case 2:
               diaInicio = data.get(1).getDia();
                break;
            default:
               diaInicio = data.get(0).getDia();
                break;
        }
        Assert.assertEquals(0,horaHotelPersistence.findByDiaInRange(diaInicio, diaInicioMenor).size());
        Assert.assertEquals(0,horaHotelPersistence.findByDiaInRange(diaInicioMayor, diaInicio).size());
        Assert.assertEquals(0,horaHotelPersistence.findByDiaInRange(diaInicioMayor, diaInicioMenor).size());
        Assert.assertEquals(1,horaHotelPersistence.findByDiaInRange(diaInicioMenor, diaInicioMenor).size());
        Assert.assertEquals(1,horaHotelPersistence.findByDiaInRange(diaInicio, diaInicio).size());
        Assert.assertEquals(1,horaHotelPersistence.findByDiaInRange(diaInicioMayor, diaInicioMayor).size());
        Assert.assertEquals(2,horaHotelPersistence.findByDiaInRange(diaInicioMenor, diaInicio).size());
        Assert.assertEquals(2,horaHotelPersistence.findByDiaInRange(diaInicio, diaInicioMayor).size());
        Assert.assertEquals(3,horaHotelPersistence.findByDiaInRange(diaInicioMenor, diaInicioMayor).size());
    }
    
    @Test
    public void findByCostoBaseInRange()
    {
        Double costoBaseMedia;
        Double costoBaseMayor = data.get(0).getCostoBase();
        Double costoBaseMenor = data.get(0).getCostoBase();
        int posicionMayor = 0; int posicionMenor = 0;
        for(int i=1; i < data.size(); i++)
        {
            if(costoBaseMayor < data.get(i).getCostoBase())
            {
                costoBaseMayor = data.get(i).getCostoBase();
                posicionMayor = i;
            }
            if(costoBaseMenor > data.get(i).getCostoBase())
            {
                costoBaseMenor = data.get(i).getCostoBase();
                posicionMenor = i;
            }
        }
        
        switch (posicionMayor+posicionMenor) {
            case 1:
                costoBaseMedia = data.get(2).getCostoBase();
                break;
            case 2:
                costoBaseMedia = data.get(1).getCostoBase();
                break;
            default:
                costoBaseMedia = data.get(0).getCostoBase();
                break;
        }
        Assert.assertEquals(0,horaHotelPersistence.findByCostoBaseInRange(costoBaseMedia, costoBaseMenor).size());
        Assert.assertEquals(0,horaHotelPersistence.findByCostoBaseInRange(costoBaseMayor, costoBaseMedia).size());
        Assert.assertEquals(0,horaHotelPersistence.findByCostoBaseInRange(costoBaseMayor, costoBaseMenor).size());
        Assert.assertEquals(1,horaHotelPersistence.findByCostoBaseInRange(costoBaseMenor, costoBaseMenor).size());
        Assert.assertEquals(1,horaHotelPersistence.findByCostoBaseInRange(costoBaseMedia, costoBaseMedia).size());
        Assert.assertEquals(1,horaHotelPersistence.findByCostoBaseInRange(costoBaseMayor, costoBaseMayor).size());
        Assert.assertEquals(2,horaHotelPersistence.findByCostoBaseInRange(costoBaseMenor, costoBaseMedia).size());
        Assert.assertEquals(2,horaHotelPersistence.findByCostoBaseInRange(costoBaseMedia, costoBaseMayor).size());
        Assert.assertEquals(3,horaHotelPersistence.findByCostoBaseInRange(costoBaseMenor, costoBaseMayor).size());
    
    }
   
    
    @Test
    public void deleteHoraHotelTest(){
        HoraHotelEntity horaHotel = data.get(0);
        horaHotelPersistence.delete(horaHotel.getId());
        HoraHotelEntity deleted = em.find(HoraHotelEntity.class, horaHotel.getId());
        Assert.assertNull(deleted);
    }
}

