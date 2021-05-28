/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.logic;


import co.edu.uniandes.csw.paseadores.ejb.PaseadorPagoPaseadorLogic;
import co.edu.uniandes.csw.paseadores.entities.PagoPaseadorEntity;
import co.edu.uniandes.csw.paseadores.entities.PaseadorEntity;
import co.edu.uniandes.csw.paseadores.persistence.PaseadorPersistence;
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
public class PaseadorPagoPaseadorLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private PaseadorPagoPaseadorLogic pagoPaseadorPaseadorLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<PaseadorEntity> data = new ArrayList<PaseadorEntity>();

    private List<PagoPaseadorEntity> pagosPaseadorData = new ArrayList();
    
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PaseadorEntity.class.getPackage())
                .addPackage(PaseadorPagoPaseadorLogic.class.getPackage())
                .addPackage(PaseadorPersistence.class.getPackage())
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
        em.createQuery("delete from PagoPaseadorEntity").executeUpdate();
        em.createQuery("delete from PaseadorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            PagoPaseadorEntity pagos = factory.manufacturePojo(PagoPaseadorEntity.class);
            em.persist(pagos);
            pagosPaseadorData.add(pagos);
        }
        for (int i = 0; i < 3; i++) {
            PaseadorEntity entity = factory.manufacturePojo(PaseadorEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                pagosPaseadorData.get(i).setPaseador(entity); 
            }
        }
    }

    /**
     * Prueba para asociar un Paseos existente a un Recorrido.
     */
    @Test
    public void addPaseadorTest() {
        PaseadorEntity entity = data.get(0);
        PagoPaseadorEntity paseoEntity = pagosPaseadorData.get(1);
        PaseadorEntity response = pagoPaseadorPaseadorLogic.addPaseador(entity.getId(),  paseoEntity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(entity.getId(), response.getId());
    }

    /**
     * Prueba para consultar un Recorrido.
     */
    @Test
    public void getPaseadorTest() {
        PagoPaseadorEntity entity = pagosPaseadorData.get(0);
        PaseadorEntity resultEntity = pagoPaseadorPaseadorLogic.getPaseador(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getPaseador().getId(), resultEntity.getId());
    }

    /**
     * Prueba para remplazar las instancias de Paseos asociadas a una instancia
     * de Recorrido.
     */
    @Test
    public void replacePaseadorTest() {
        PaseadorEntity entity = data.get(0);
        pagoPaseadorPaseadorLogic.replacePaseador(pagosPaseadorData.get(1).getId(), entity.getId());
        entity = pagoPaseadorPaseadorLogic.getPaseador(pagosPaseadorData.get(1).getId());
        Assert.assertTrue(entity.getPagos().contains(pagosPaseadorData.get(1)));
    }

    /**
     * Prueba para desasociar un Paseo existente de un Recorrido existente.
     *
     */
    @Test
    public void removePagoPaseadorTest(){
        pagoPaseadorPaseadorLogic.removePagoPaseador(pagosPaseadorData.get(0).getId());
        Assert.assertNull(pagoPaseadorPaseadorLogic.getPaseador(pagosPaseadorData.get(0).getId()));
    }
    
}
