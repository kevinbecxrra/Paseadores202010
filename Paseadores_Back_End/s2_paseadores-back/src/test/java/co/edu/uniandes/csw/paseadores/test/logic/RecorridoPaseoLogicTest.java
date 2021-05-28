/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.logic;

import co.edu.uniandes.csw.paseadores.ejb.RecorridoPaseoLogic;
import co.edu.uniandes.csw.paseadores.entities.PaseoEntity;
import co.edu.uniandes.csw.paseadores.entities.RecorridoEntity;
import co.edu.uniandes.csw.paseadores.persistence.RecorridoPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Nicolas Urrego Sandoval <n.urrego at uniandes.edu.co>
 */
@RunWith(Arquillian.class)
public class RecorridoPaseoLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private RecorridoPaseoLogic paseoRecorridoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<RecorridoEntity> data = new ArrayList<RecorridoEntity>();

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
                .addPackage(RecorridoPaseoLogic.class.getPackage())
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
        em.createQuery("delete from RecorridoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            PaseoEntity paseos = factory.manufacturePojo(PaseoEntity.class);
            em.persist(paseos);
            paseosData.add(paseos);
        }
        for (int i = 0; i < 3; i++) {
            RecorridoEntity entity = factory.manufacturePojo(RecorridoEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                paseosData.get(i).setRecorrido(entity);
            }
        }
    }

    /**
     * Prueba para asociar un Paseos existente a un Recorrido.
     */
    @Test
    public void addRecorridoTest() {
        RecorridoEntity entity = data.get(0);
        PaseoEntity paseoEntity = paseosData.get(1);
        RecorridoEntity response = paseoRecorridoLogic.addRecorrido(entity.getId(), paseoEntity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(entity.getId(), response.getId());
    }

    /**
     * Prueba para consultar un Recorrido.
     */
    @Test
    public void getRecorridoTest() {
        PaseoEntity entity = paseosData.get(0);
        RecorridoEntity resultEntity = paseoRecorridoLogic.getRecorrido(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getRecorrido().getId(), resultEntity.getId());
    }
    
    /**
     * Prueba para consultar un Recorrido.
     */
    @Test
    public void getPaseosTest() {
        PaseoEntity entidad = paseosData.get(0);
        RecorridoEntity resultado = paseoRecorridoLogic.getRecorrido(entidad.getId());
        List list = paseoRecorridoLogic.getPaseos(resultado.getId());
        Assert.assertNotNull(list);
        Assert.assertNotNull(resultado);
        Assert.assertEquals(entidad.getRecorrido().getId(), resultado.getId());
    }
    
    /**
     * Prueba para consultar un Recorrido.
     */
    @Test
    public void getPaseoTest() {
        PaseoEntity ent = paseosData.get(0);
        RecorridoEntity res = paseoRecorridoLogic.getRecorrido(ent.getId());
        try{
        Assert.assertNotNull(paseoRecorridoLogic.getPaseo(res.getId(), ent.getId()));
        }
        catch(Exception e){}
        Assert.assertNotNull(res);
        Assert.assertEquals(ent.getRecorrido().getId(), res.getId());
    }

    /**
     * Prueba para remplazar las instancias de Paseos asociadas a una instancia
     * de Recorrido.
     */
    @Test
    public void replaceRecorridoTest() {
        RecorridoEntity entity = data.get(0);
        paseoRecorridoLogic.replaceRecorrido(paseosData.get(1).getId(), entity.getId());
        entity = paseoRecorridoLogic.getRecorrido(paseosData.get(1).getId());
        Assert.assertTrue(entity.getPaseos().contains(paseosData.get(1)));
    }

    /**
     * Prueba para desasociar un Paseo existente de un Recorrido existente.
     *
     */
    @Test
    public void removePaseoTest(){
        paseoRecorridoLogic.removePaseo(paseosData.get(0).getId());
        Assert.assertNull(paseoRecorridoLogic.getRecorrido(paseosData.get(0).getId()));
    }

}

