/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.logic;

import co.edu.uniandes.csw.paseadores.ejb.PaseadorPaseoLogic;
import co.edu.uniandes.csw.paseadores.ejb.RecorridoPuntoLogic;
import co.edu.uniandes.csw.paseadores.entities.PaseadorEntity;
import co.edu.uniandes.csw.paseadores.entities.PaseoEntity;
import co.edu.uniandes.csw.paseadores.entities.RecorridoEntity;
import co.edu.uniandes.csw.paseadores.persistence.RecorridoPersistence;
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
 * @author Alvaro Plata
 */
@RunWith(Arquillian.class)
public class PaseadorPaseoLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private PaseadorPaseoLogic paseadorPaseoLogic;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;
    
    private List<PaseadorEntity> data = new ArrayList<PaseadorEntity>();

    private List<PaseoEntity> paseosData = new ArrayList();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(RecorridoEntity.class.getPackage())
                .addPackage(RecorridoPuntoLogic.class.getPackage())
                .addPackage(RecorridoPersistence.class.getPackage())
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
        em.createQuery("delete from PaseoEntity").executeUpdate();
        em.createQuery("delete from PaseadorEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            PaseoEntity paseo = factory.manufacturePojo(PaseoEntity.class);
            em.persist(paseo);
            paseosData.add(paseo);
        }
        for (int i = 0; i < 3; i++) {
            PaseadorEntity entity = factory.manufacturePojo(PaseadorEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                paseosData.get(i).setPaseador(entity);
            }
        }    
    }
    
    /**
     * Prueba para asociar un Paseo existente a un Paseador.
     */
    @Test
    public void addRecorridoTest() {
        PaseadorEntity entity = data.get(0);
        PaseoEntity paseoEntity = paseosData.get(1);
        PaseadorEntity response = paseadorPaseoLogic.addPaseador(entity.getId(), paseoEntity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(entity.getId(), response.getId());
    }
    
    /**
     * Prueba para consultar un Paseador.
     */
    @Test
    public void getPaseadorTest() {
        PaseoEntity entity = paseosData.get(0);
        PaseadorEntity resultEntity = paseadorPaseoLogic.getPaseador(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getPaseador().getId(), resultEntity.getId());
    }
    
    /**
     * Prueba para remplazar las instancias de Paseos asociadas a una instancia
     * de Paseador.
     */
    @Test
    public void replacePaseadorTest() {
        PaseadorEntity entity = data.get(0);
        paseadorPaseoLogic.replacePaseador(paseosData.get(1).getId(), entity.getId());
        entity = paseadorPaseoLogic.getPaseador(paseosData.get(1).getId());
        Assert.assertTrue(entity.getPaseos().contains(paseosData.get(1)));
    }
    
    /**
     * Prueba para desasociar un Paseo existente de un Paseador existente.
     *
     */
    @Test
    public void removePaseoTest(){
        paseadorPaseoLogic.removePaseo(paseosData.get(0).getId());
        Assert.assertNull(paseadorPaseoLogic.getPaseador(paseosData.get(0).getId()));
    }
    
}
