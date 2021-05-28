/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.persistence;

import co.edu.uniandes.csw.paseadores.entities.PagoClienteEntity;
import co.edu.uniandes.csw.paseadores.persistence.PagoClientePersistence;
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
public class PagoClientePersistenceTest {
    
    @Inject
    private PagoClientePersistence pagoClientePersistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;

    private List<PagoClienteEntity> data = new ArrayList<>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PagoClienteEntity.class.getPackage())
                .addPackage(PagoClientePersistence.class.getPackage())
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
        em.createQuery("delete from PagoClienteEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            PagoClienteEntity entity = factory.manufacturePojo(PagoClienteEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    /**
     * Prueba para crear un PagoCliente.
     */
    @Test
    public void createPagoClienteTest() {
        PodamFactory factory = new PodamFactoryImpl();
        PagoClienteEntity newEntity = factory.manufacturePojo(PagoClienteEntity.class);
        PagoClienteEntity result = pagoClientePersistence.create(newEntity);

        Assert.assertNotNull(result);

        PagoClienteEntity entity = em.find(PagoClienteEntity.class, result.getId());

        Assert.assertEquals(newEntity.getReferencia(), entity.getReferencia());
        Assert.assertEquals(newEntity.getMonto(), entity.getMonto());
    }
    
    /**
     * Prueba para consultar un PagoCliente.
     */
    @Test
    public void findPagoClienteTest() {
        PagoClienteEntity entity = data.get(0);
        PagoClienteEntity newEntity = pagoClientePersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getReferencia(), newEntity.getReferencia());
        Assert.assertEquals(entity.getMonto(), newEntity.getMonto());
    }
    
    /**
     * Prueba para actualizar un PagoCliente.
     */
    @Test
    public void updatePagoClienteTest() {
        PagoClienteEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        PagoClienteEntity newEntity = factory.manufacturePojo(PagoClienteEntity.class);

        newEntity.setId(entity.getId());

        pagoClientePersistence.update(newEntity);

        PagoClienteEntity resp = em.find(PagoClienteEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getMonto(), resp.getMonto());
        Assert.assertEquals(newEntity.getReferencia(), resp.getReferencia());
    }
    
    /**
     * Prueba para eliminar un PagoCliente.
     */
    @Test
    public void deletePagoClienteTest() {
        PagoClienteEntity entity = data.get(0);
        pagoClientePersistence.delete(entity.getId());
        PagoClienteEntity deleted = em.find(PagoClienteEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
    
    @Test
    public void findByReferenciaTest()
    {
        PagoClienteEntity pago1 = data.get(0);
        PagoClienteEntity pagoBuscado = pagoClientePersistence.findByReferencia(pago1.getReferencia());
        Assert.assertNotNull(pagoBuscado);
        Assert.assertEquals(pago1.getId(), pagoBuscado.getId());
        Assert.assertEquals(pago1.getMonto(), pagoBuscado.getMonto());
        Assert.assertEquals(pago1.getReferencia(), pagoBuscado.getReferencia());
        
        pagoBuscado = pagoClientePersistence.findByReferencia(null);
        Assert.assertNull(pagoBuscado);
    }
    
}