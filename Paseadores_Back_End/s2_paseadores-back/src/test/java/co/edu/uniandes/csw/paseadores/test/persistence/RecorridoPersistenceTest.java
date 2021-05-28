/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.persistence;

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
 * @recorrido Nicolas Urrego Sandoval <n.urrego at uniandes.edu.co>
 */
@RunWith(Arquillian.class)
public class RecorridoPersistenceTest {
     @Inject
    private RecorridoPersistence recorridoPersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<RecorridoEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(RecorridoEntity.class.getPackage())
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
            em.joinTransaction();
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
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            RecorridoEntity entity = factory.manufacturePojo(RecorridoEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Recorrido.
     */
    @Test
    public void createRecorridoTest() {
        PodamFactory factory = new PodamFactoryImpl();
        RecorridoEntity newEntity = factory.manufacturePojo(RecorridoEntity.class);
        RecorridoEntity result = recorridoPersistence.create(newEntity);

        Assert.assertNotNull(result);

        RecorridoEntity entity = em.find(RecorridoEntity.class, result.getId());

        Assert.assertEquals(newEntity.getCalificacionGlobal(), entity.getCalificacionGlobal());
        Assert.assertEquals(newEntity.getNumeroCalificaciones(), entity.getNumeroCalificaciones());
    }

    /**
     * Prueba para consultar un Recorrido.
     */
    @Test
    public void findRecorridoTest() {
        RecorridoEntity entity = data.get(0);
        RecorridoEntity newEntity = recorridoPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getCalificacionGlobal(), newEntity.getCalificacionGlobal());
        Assert.assertEquals(newEntity.getNumeroCalificaciones(), entity.getNumeroCalificaciones());
    }
    
    @Test
    public void findAllRecorridosTest() {
        List<RecorridoEntity> listaRecorridos = recorridoPersistence.findAll();
        Assert.assertEquals(data.size(), listaRecorridos.size());
        for(RecorridoEntity ent : listaRecorridos) {
            boolean found = false;
            for (RecorridoEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    Assert.assertEquals(ent.getCalificacionGlobal(), entity.getCalificacionGlobal());
                    Assert.assertEquals(ent.getNumeroCalificaciones(), entity.getNumeroCalificaciones());
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void findByCalificacionGlobalInRangeTest()
    {
        Double calificacionMedia;
        Double calificacionMayor = data.get(0).getCalificacionGlobal();
        Double calificacionMenor = data.get(0).getCalificacionGlobal();
        int posicionMayor = 0; int posicionMenor = 0;
        for(int i=1; i < data.size(); i++)
        {
            if(calificacionMayor < data.get(i).getCalificacionGlobal())
            {
                calificacionMayor = data.get(i).getCalificacionGlobal();
                posicionMayor = i;
            }
            if(calificacionMenor > data.get(i).getCalificacionGlobal())
            {
                calificacionMenor = data.get(i).getCalificacionGlobal();
                posicionMenor = i;
            }
        }
        
        switch (posicionMayor+posicionMenor) {
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
        Assert.assertEquals(0,recorridoPersistence.findByCalificacionGlobalInRange(calificacionMedia, calificacionMenor).size());
        Assert.assertEquals(0,recorridoPersistence.findByCalificacionGlobalInRange(calificacionMayor, calificacionMedia).size());
        Assert.assertEquals(0,recorridoPersistence.findByCalificacionGlobalInRange(calificacionMayor, calificacionMenor).size());
        Assert.assertEquals(1,recorridoPersistence.findByCalificacionGlobalInRange(calificacionMenor, calificacionMenor).size());
        Assert.assertEquals(1,recorridoPersistence.findByCalificacionGlobalInRange(calificacionMedia, calificacionMedia).size());
        Assert.assertEquals(1,recorridoPersistence.findByCalificacionGlobalInRange(calificacionMayor, calificacionMayor).size());
        Assert.assertEquals(2,recorridoPersistence.findByCalificacionGlobalInRange(calificacionMenor, calificacionMedia).size());
        Assert.assertEquals(2,recorridoPersistence.findByCalificacionGlobalInRange(calificacionMedia, calificacionMayor).size());
        Assert.assertEquals(3,recorridoPersistence.findByCalificacionGlobalInRange(calificacionMenor, calificacionMayor).size());
    
    }
    /**
     * Prueba para actualizar un Recorrido.
     */
    @Test
    public void updateRecorridoTest() {
        RecorridoEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        RecorridoEntity newEntity = factory.manufacturePojo(RecorridoEntity.class);

        newEntity.setId(entity.getId());

        recorridoPersistence.update(newEntity);

        RecorridoEntity resp = em.find(RecorridoEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getCalificacionGlobal(), resp.getCalificacionGlobal());
        Assert.assertEquals(newEntity.getNumeroCalificaciones(), resp.getNumeroCalificaciones());
    }

    /**
     * Prueba para eliminar un Recorrido.
     */
    @Test
    public void deleteRecorridoTest() {
        RecorridoEntity entity = data.get(0);
        recorridoPersistence.delete(entity.getId());
        RecorridoEntity deleted = em.find(RecorridoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}