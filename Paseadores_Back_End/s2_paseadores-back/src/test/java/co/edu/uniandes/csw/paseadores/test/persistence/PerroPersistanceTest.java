/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.persistence;


import co.edu.uniandes.csw.paseadores.entities.PerroEntity;
import co.edu.uniandes.csw.paseadores.persistence.PerroPersistence;
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
 * @author julian Oliveros<je.oliverosf at uniandes.edu.co>
 */
@RunWith (Arquillian.class)
public class PerroPersistanceTest {
    
    @Inject
    PerroPersistence perroPersistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<PerroEntity> data = new ArrayList();
    
            
     /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment 
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PerroEntity.class.getPackage())
                .addPackage(PerroPersistence.class.getPackage())
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
    
    private void clearData() {
        em.createQuery("delete from PerroEntity").executeUpdate();
    }
    
    private void insertData(){
        PodamFactory factory = new PodamFactoryImpl();
        for(int i = 0; i<3 ; i++ ){
            PerroEntity entity = factory.manufacturePojo(PerroEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    @Test
    public void createPerroTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        PerroEntity nuevoPerro = factory.manufacturePojo(PerroEntity.class);
        PerroEntity perroCreado = perroPersistence.create(nuevoPerro);
        Assert.assertNotNull(perroCreado);
        
        PerroEntity perroBuscado = em.find(PerroEntity.class, perroCreado.getId());
        
        Assert.assertEquals(perroBuscado.getIdPerro(), perroCreado.getIdPerro());
        Assert.assertEquals(perroBuscado.getNombre(), perroCreado.getNombre());
        Assert.assertEquals(perroBuscado.getRaza(), perroCreado.getRaza());
        Assert.assertEquals(perroBuscado.getEdad(), perroCreado.getEdad());
    }
    
    /**
     * Prueba para consultar la lista de perros.
     */
    @Test
    public void findAllPerrosTest() {
        
        List<PerroEntity> list = perroPersistence.findAll();
        
        Assert.assertEquals(data.size(), list.size());
        for (PerroEntity ent : list) {
            boolean found = false;
            for (PerroEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    Assert.assertEquals(ent.getIdPerro(), entity.getIdPerro());
                    Assert.assertEquals(ent.getNombre(), entity.getNombre());
                    Assert.assertEquals(ent.getRaza(), entity.getRaza());
                    Assert.assertEquals(ent.getEdad(), entity.getEdad());
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void findPerroTest() {
        PerroEntity perro = data.get(0);
        PerroEntity perroBuscado = perroPersistence.find(perro.getId());
        Assert.assertNotNull(perroBuscado);
        
        Assert.assertEquals(perro.getIdPerro(), perroBuscado.getIdPerro());
        Assert.assertEquals(perro.getNombre(), perroBuscado.getNombre());
        Assert.assertEquals(perro.getRaza() ,perroBuscado.getRaza());
        Assert.assertEquals(perro.getEdad() , perroBuscado.getEdad());
    }
    
    @Test
    public void updatePerroTest(){
        PerroEntity perro = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        PerroEntity newPerro = factory.manufacturePojo(PerroEntity.class);
        
        newPerro.setId(perro.getId());
        perroPersistence.update(newPerro);
        PerroEntity resp = em.find(PerroEntity.class,perro.getId());
    
        Assert.assertEquals(newPerro.getIdPerro(),resp.getIdPerro());
        Assert.assertEquals(newPerro.getNombre(), resp.getNombre());
        Assert.assertEquals(newPerro.getRaza() ,resp.getRaza());
        Assert.assertEquals(newPerro.getEdad() , resp.getEdad());
    }
    
    @Test
    public void deletePerroTest() {
        PerroEntity perro = data.get(0);
        perroPersistence.delete(perro.getId());
        PerroEntity deleted = em.find(PerroEntity.class, perro.getId());
        Assert.assertNull(deleted);
    }
    
    @Test
    public void findPerroByIdPerro(){
        PerroEntity perro = data.get(0);
        PerroEntity perroBuscado =  perroPersistence.findByIdPerro(perro.getIdPerro());

        Assert.assertNotNull(perroBuscado);
        Assert.assertEquals(perro.getIdPerro(), perroBuscado.getIdPerro());
        Assert.assertEquals(perro.getNombre(), perroBuscado.getNombre());
        Assert.assertEquals(perro.getRaza() ,perroBuscado.getRaza());
        Assert.assertEquals(perro.getEdad() , perroBuscado.getEdad());
        
        perroBuscado = perroPersistence.findByIdPerro(null);
        Assert.assertNull(perroBuscado);
    }  
}
