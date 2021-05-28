/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.logic;

import co.edu.uniandes.csw.paseadores.ejb.PagoPaseadorPaseoLogic;
import co.edu.uniandes.csw.paseadores.entities.PagoPaseadorEntity;
import co.edu.uniandes.csw.paseadores.entities.PaseoEntity;
import co.edu.uniandes.csw.paseadores.persistence.PagoPaseadorPersistence;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;


/**
 *
 * @author julian Oliveros<je.oliverosf at uniandes.edu.co>
 */
@RunWith(Arquillian.class)
public class PagoPaseadorPaseoLogicTest {
    
     private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private PagoPaseadorPaseoLogic pagoPaseadorPaseoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<PagoPaseadorEntity> data = new ArrayList<PagoPaseadorEntity>();

    private List<PaseoEntity> paseoData = new ArrayList();

     
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PagoPaseadorEntity.class.getPackage())
                .addPackage(PagoPaseadorPaseoLogic.class.getPackage())
                .addPackage(PagoPaseadorPersistence.class.getPackage())
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
        em.createQuery("delete from PagoPaseadorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            PaseoEntity paseos = factory.manufacturePojo(PaseoEntity.class);
            em.persist(paseos);
            paseoData.add(paseos);
        }
        for (int i = 0; i < 3; i++) {
            PagoPaseadorEntity entity = factory.manufacturePojo(PagoPaseadorEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                paseoData.get(i).setPagoPaseador(entity); 
            }
        }
    }

    /**
     * Prueba para asociar un Paseos existente a un Recorrido.
     */
    @Test
    public void addPagoPaseadorTest() {
        PagoPaseadorEntity entity = data.get(0);
        PaseoEntity paseoEntity = paseoData.get(1);
        PagoPaseadorEntity response = pagoPaseadorPaseoLogic.addPagoPaseador(entity.getId(),  paseoEntity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(entity.getId(), response.getId());
    }

    /**
     * Prueba para consultar un Recorrido.
     */
    @Test
    public void getPagoPaseadorTest() {
        PaseoEntity entity = paseoData.get(0);
        PagoPaseadorEntity resultEntity = pagoPaseadorPaseoLogic.getPagoPaseador(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getPagoPaseador().getId(), resultEntity.getId());
    }

    /**
     * Prueba para remplazar las instancias de Paseos asociadas a una instancia
     * de Recorrido.
     */
    @Test
    public void replacePagoPaseadorTest() {
        PagoPaseadorEntity entity = data.get(0);
        pagoPaseadorPaseoLogic.replacePagoPaseador(paseoData.get(1).getId(), entity.getId());
        entity = pagoPaseadorPaseoLogic.getPagoPaseador(paseoData.get(1).getId());
        Assert.assertTrue(entity.getPaseos().contains(paseoData.get(1)));
    }
    
    /**
     * Prueba para desasociar un Paseo existente de un Recorrido existente.
     *
     */
    @Test
    public void removePaseoTest(){
        pagoPaseadorPaseoLogic.removePaseo(paseoData.get(0).getId());
        
        Assert.assertNull(pagoPaseadorPaseoLogic.getPagoPaseador(paseoData.get(0).getId()));
    }
    
}
