/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.logic;

import co.edu.uniandes.csw.paseadores.ejb.RecorridoLogic;
import co.edu.uniandes.csw.paseadores.entities.RecorridoEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
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
public class RecorridoLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private RecorridoLogic recorridoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<RecorridoEntity> data = new ArrayList<RecorridoEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(RecorridoEntity.class.getPackage())
                .addPackage(RecorridoLogic.class.getPackage())
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
        em.createQuery("delete from RecorridoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            RecorridoEntity entity = factory.manufacturePojo(RecorridoEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Recorrido.
     *
     */
    @Test
    public void createRecorridoTest() {
        RecorridoEntity newEntity = factory.manufacturePojo(RecorridoEntity.class);
        newEntity.setCalificacionGlobal(3.0);
        newEntity.setNumeroCalificaciones(1);
        RecorridoEntity result = null;
        try {
            result = recorridoLogic.createRecorrido(newEntity);
        } catch (BusinessLogicException ex) {
            Assert.fail("No debería lanzar excepción");
        }
        Assert.assertNotNull(result);
        RecorridoEntity entity = em.find(RecorridoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getCalificacionGlobal(), entity.getCalificacionGlobal());
        Assert.assertEquals(newEntity.getNumeroCalificaciones(), entity.getNumeroCalificaciones());
    }

    /**
     * Prueba para crear un Recorrido.
     *
     */
    @Test
    public void createRecorridoConCalificacionGlobalInvalidaTest() {
        RecorridoEntity noEntity = factory.manufacturePojo(RecorridoEntity.class);
        noEntity.setCalificacionGlobal(6.6);
        noEntity.setNumeroCalificaciones(0);
        try {
            recorridoLogic.createRecorrido(noEntity);
            Assert.fail("Debería lanzar excepción");
        } catch (BusinessLogicException b) {
        }
        noEntity.setCalificacionGlobal(-2.0);
        try {
            recorridoLogic.createRecorrido(noEntity);
            Assert.fail("Debería lanzar excepción");
        } catch (BusinessLogicException b) {
        }
    }

    /**
     * Prueba para crear un Recorrido.
     *
     */
    @Test
    public void createRecorridoConNumeroCalificacionesInvalidoTest() {
        RecorridoEntity noEntity = factory.manufacturePojo(RecorridoEntity.class);
        noEntity.setCalificacionGlobal(0.0);
        noEntity.setNumeroCalificaciones(-3);
        try {
            recorridoLogic.createRecorrido(noEntity);
            Assert.fail("Debería lanzar excepción");
        } catch (BusinessLogicException b) {
        }
    }

    /**
     * Prueba para consultar un Recorrido.
     */
    @Test
    public void getRecorridoTest() {
        RecorridoEntity entity = data.get(0);
        RecorridoEntity resultEntity = recorridoLogic.getRecorrido(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(resultEntity.getId(), entity.getId());
        Assert.assertEquals(resultEntity.getCalificacionGlobal(), entity.getCalificacionGlobal());
        Assert.assertEquals(resultEntity.getNumeroCalificaciones(), entity.getNumeroCalificaciones());
    }

    /**
     * Prueba para consultar un Recorrido.
     */
    @Test
    public void getRecorridoByCalificacionGlobalInRangeTest() {
        Double calificacionMedia;
        Double calificacionMayor = data.get(0).getCalificacionGlobal();
        Double calificacionMenor = data.get(0).getCalificacionGlobal();
        int posicionMayor = 0;
        int posicionMenor = 0;
        for (int i = 1; i < data.size(); i++) {
            if (calificacionMayor < data.get(i).getCalificacionGlobal()) {
                calificacionMayor = data.get(i).getCalificacionGlobal();
                posicionMayor = i;
            }
            if (calificacionMenor > data.get(i).getCalificacionGlobal()) {
                calificacionMenor = data.get(i).getCalificacionGlobal();
                posicionMenor = i;
            }
        }

        switch (posicionMayor + posicionMenor) {
            case 1:
                calificacionMedia = data.get(2).getCalificacionGlobal();
                break;
            case 2:
                calificacionMedia = data.get(1).getCalificacionGlobal();
                break;
            default:
                calificacionMedia = data.get(0).getCalificacionGlobal();
                break;
        }
        Assert.assertEquals(0, recorridoLogic.getRecorridoByCalificacionGlobalInRange(calificacionMedia, calificacionMenor).size());
        Assert.assertEquals(0, recorridoLogic.getRecorridoByCalificacionGlobalInRange(calificacionMayor, calificacionMedia).size());
        Assert.assertEquals(0, recorridoLogic.getRecorridoByCalificacionGlobalInRange(calificacionMayor, calificacionMenor).size());
        Assert.assertEquals(1, recorridoLogic.getRecorridoByCalificacionGlobalInRange(calificacionMenor, calificacionMenor).size());
        Assert.assertEquals(1, recorridoLogic.getRecorridoByCalificacionGlobalInRange(calificacionMedia, calificacionMedia).size());
        Assert.assertEquals(1, recorridoLogic.getRecorridoByCalificacionGlobalInRange(calificacionMayor, calificacionMayor).size());
        Assert.assertEquals(2, recorridoLogic.getRecorridoByCalificacionGlobalInRange(calificacionMenor, calificacionMedia).size());
        Assert.assertEquals(2, recorridoLogic.getRecorridoByCalificacionGlobalInRange(calificacionMedia, calificacionMayor).size());
        Assert.assertEquals(3, recorridoLogic.getRecorridoByCalificacionGlobalInRange(calificacionMenor, calificacionMayor).size());

    }

    /**
     * Prueba para actualizar un Recorrido.
     */
    @Test
    public void updateRecorridoTest() {
        RecorridoEntity entity = data.get(0);
        RecorridoEntity pojoEntity = factory.manufacturePojo(RecorridoEntity.class);
        pojoEntity.setId(entity.getId());
        pojoEntity.setCalificacionGlobal(3.0);
        pojoEntity.setNumeroCalificaciones(1);
        try {
            recorridoLogic.updateRecorrido(pojoEntity.getId(), pojoEntity);
        } catch (BusinessLogicException ex) {
            Assert.fail("No debería lanzar excepción");
        }
        RecorridoEntity resp = em.find(RecorridoEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getCalificacionGlobal(), resp.getCalificacionGlobal());
        Assert.assertEquals(pojoEntity.getNumeroCalificaciones(), resp.getNumeroCalificaciones());
    }

    /**
     * Prueba para crear un Recorrido.
     *
     */
    @Test
    public void updateRecorridoConCalificacionGlobalInvalidaTest() {
        RecorridoEntity entity = data.get(0);
        RecorridoEntity nonEntity = factory.manufacturePojo(RecorridoEntity.class);
        nonEntity.setId(entity.getId());
        nonEntity.setCalificacionGlobal(6.6);
        nonEntity.setNumeroCalificaciones(0);
        try {
            recorridoLogic.updateRecorrido(nonEntity.getId(), nonEntity);
            Assert.fail("Debería lanzar excepción");
        } catch (BusinessLogicException b) {
        }
        nonEntity.setCalificacionGlobal(-2.0);
        try {
            recorridoLogic.updateRecorrido(nonEntity.getId(), nonEntity);
            Assert.fail("Debería lanzar excepción");
        } catch (BusinessLogicException b) {
        }
    }

    /**
     * Prueba para crear un Recorrido.
     *
     */
    @Test
    public void updateRecorridoConNumeroCalificacionesInvalidoTest() {
        RecorridoEntity entity = data.get(0);
        RecorridoEntity nonEntity = factory.manufacturePojo(RecorridoEntity.class);
        nonEntity.setId(entity.getId());
        nonEntity.setCalificacionGlobal(0.0);
        nonEntity.setNumeroCalificaciones(-3);
        try {
            recorridoLogic.updateRecorrido(nonEntity.getId(), nonEntity);
            Assert.fail("Debería lanzar excepción");
        } catch (BusinessLogicException b) {
        }
    }

    /**
     * Prueba para eliminar un Recorrido.
     *
     */
    @Test()
    public void deleteRecorridoTest() {
        RecorridoEntity entity = data.get(2);
        long id = entity.getId();
        recorridoLogic.deleteRecorrido(entity.getId());
        Assert.assertNull(recorridoLogic.getRecorrido(id));
    }

}
