/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.logic;


import co.edu.uniandes.csw.paseadores.ejb.PerroContratoPaseoLogic;
import co.edu.uniandes.csw.paseadores.entities.ContratoPaseoEntity;
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
@RunWith(Arquillian.class)
public class PerroContratoPaseoLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private PerroContratoPaseoLogic perroContratoPaseoLogic;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;
    
    private List<PerroEntity> data =new ArrayList<PerroEntity>();
    
    private List<ContratoPaseoEntity> contratosData = new ArrayList();
    
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PerroEntity.class.getPackage())
                .addPackage(PerroContratoPaseoLogic.class.getPackage())
                .addPackage(PerroPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }

    /**
     * Configuración inicial de la prueba.
     */
    @Before
    public void configTest() {
        try {
            utx.begin();
            clearData();
            insertData();
            utx.commit();
        } catch (Exception e) {
            e.printStackTrace();
            try {
                utx.rollback();
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
    }
    
    
    /**
     * Limpia las tablas que están implicadas en la prueba.
     */
    private void clearData() {
        em.createQuery("delete from PerroEntity").executeUpdate();
        em.createQuery("delete from ContratoPaseoEntity").executeUpdate();
    }
   
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ContratoPaseoEntity contrato = factory.manufacturePojo(ContratoPaseoEntity.class);
            em.persist(contrato);
            contratosData.add(contrato);
        }
        for (int i = 0; i < 3; i++) {
            PerroEntity entity = factory.manufacturePojo(PerroEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                contratosData.get(i).setPerro(entity);
            }
        }    
    }
    
        /**
     * Prueba para asociar un contratoPaseo existente a un perro.
     */
    
    @Test
    public void addPerroTest() {
        if(data!=null && data.size()!=0)
        {
            PerroEntity entity = data.get(0);
            ContratoPaseoEntity contraEntity = contratosData.get(1);
        
            PerroEntity response = perroContratoPaseoLogic.addPerro(entity.getId(), contraEntity.getId());

            Assert.assertNotNull(response);
            Assert.assertEquals(entity.getId(), response.getId());
        }
    }
    
    /**
     * Prueba para consultar un perro.
     */
    @Test
    
    public void getPerroTest() {
        if(contratosData!=null && contratosData.size()!=0)
        {
        
        ContratoPaseoEntity entity = contratosData.get(0);
        PerroEntity resultEntity = perroContratoPaseoLogic.getPerro(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getPerro().getId(), resultEntity.getId());
        }
    }
    
    
    /**
     * Prueba para remplazar las instancias de contratos paseo asociadas a una instancia
     * de perro.
     */
    /*
    @Test
    public void replacePerrroTest() {
        if(data!=null && data.size()!=0)
        {
        PerroEntity entity = data.get(0);
        perroContratoPaseoLogic.replacePerro(contratosData.get(1).getId(),entity.getId());
        entity = perroContratoPaseoLogic.getPerro(contratosData.get(1).getId());        
        Assert.assertTrue(entity.getPaseos().contains(contratosData.get(1)));
        }
    }
    */
    
    /**
     * Prueba para desasociar un contrato paseo existente de un perro existente.
     *
     */
    @Test
    public void removeContratoPaseoTest(){
        perroContratoPaseoLogic.removeContratoPaseo(contratosData.get(0).getId());
        Assert.assertNull(perroContratoPaseoLogic.getPerro(contratosData.get(0).getId()));
    }
    
    
}
