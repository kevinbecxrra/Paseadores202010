/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.persistence;

import co.edu.uniandes.csw.paseadores.entities.ContratoPaseoEntity;
import co.edu.uniandes.csw.paseadores.persistence.ContratoPaseoPersistence;
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
public class ContratoPaseoPersistenceTest {
    
    @Inject
    ContratoPaseoPersistence contratoPaseoPersistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;
    
    private List<ContratoPaseoEntity> data = new ArrayList();
    
     /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() 
    {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ContratoPaseoEntity.class.getPackage())
                .addPackage(ContratoPaseoPersistence.class.getPackage())
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
        em.createQuery("delete from ContratoPaseoEntity").executeUpdate();
    }
    
    private void insertData(){
        PodamFactory factory = new PodamFactoryImpl();
        for(int i = 0; i<3 ; i++ ){
            ContratoPaseoEntity entity = factory.manufacturePojo(ContratoPaseoEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
        
  @Test
    public void createContratoPaseoTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        ContratoPaseoEntity nuevoContrato = factory.manufacturePojo(ContratoPaseoEntity.class);
        ContratoPaseoEntity ContratoPaseoCreado = contratoPaseoPersistence.create(nuevoContrato);
        Assert.assertNotNull(ContratoPaseoCreado);
        
        ContratoPaseoEntity entity = em.find(ContratoPaseoEntity.class, ContratoPaseoCreado.getId());
        
        Assert.assertEquals(nuevoContrato.getHoraEncuentro() ,entity.getHoraEncuentro());
    }
    
    @Test
    public void findContratoPaseoTest(){
        ContratoPaseoEntity contratoPaseo = data.get(0);
        ContratoPaseoEntity newContratoPaseo = contratoPaseoPersistence.find(contratoPaseo.getId());
        Assert.assertNotNull(newContratoPaseo);
        Assert.assertEquals(contratoPaseo.getId(), newContratoPaseo.getId());  
        Assert.assertEquals(contratoPaseo.getHoraEncuentro(), newContratoPaseo.getHoraEncuentro());
    }
    
     @Test
    public void updateContratoPaseoTest(){
        ContratoPaseoEntity contratoPaseo = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        ContratoPaseoEntity newContratoPaseo = factory.manufacturePojo(ContratoPaseoEntity.class);
        
        newContratoPaseo.setId(contratoPaseo.getId());
        contratoPaseoPersistence.update(newContratoPaseo);
        ContratoPaseoEntity resp = em.find(ContratoPaseoEntity.class,contratoPaseo.getId());
    
        Assert.assertEquals(newContratoPaseo.getHoraEncuentro(),resp.getHoraEncuentro());
    }
    
    @Test
    public void deleteContratoPaseoTest() {
        ContratoPaseoEntity contratoPaseo = data.get(0);
        contratoPaseoPersistence.delete(contratoPaseo.getId());
        ContratoPaseoEntity deleted = em.find(ContratoPaseoEntity.class, contratoPaseo.getId());
        Assert.assertNull(deleted);
    }
}
