/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.persistence;

import co.edu.uniandes.csw.paseadores.entities.PagoPaseadorEntity;
import co.edu.uniandes.csw.paseadores.persistence.PagoPaseadorPersistence;
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
 * @author Daniel Mateo Guatibonza Solano
 */
@RunWith(Arquillian.class)
public class PagoPaseadorPersistenceTest {
    
    @Inject
    private PagoPaseadorPersistence pagoPaseadorPersistence;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;
    
    private List<PagoPaseadorEntity> datos = new ArrayList<>();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PagoPaseadorEntity.class.getPackage())
                .addPackage(PagoPaseadorPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
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
    
    private void clearData() {
        em.createQuery("delete from PagoPaseadorEntity").executeUpdate();
    }
    
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            PagoPaseadorEntity nuevoPagoPaseador = factory.manufacturePojo(PagoPaseadorEntity.class);

            em.persist(nuevoPagoPaseador);
            datos.add(nuevoPagoPaseador);
        }
    }
    
    @Test
    public void createPagoPaseadorTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        PagoPaseadorEntity nuevoPagoPaseador = factory.manufacturePojo(PagoPaseadorEntity.class);
        PagoPaseadorEntity pagoPaseadorCreado = pagoPaseadorPersistence.create(nuevoPagoPaseador);
        Assert.assertNotNull(pagoPaseadorCreado);

        PagoPaseadorEntity paseadorBuscado = em.find(PagoPaseadorEntity.class, pagoPaseadorCreado.getId());
        
        Assert.assertEquals(paseadorBuscado.getReferencia(), pagoPaseadorCreado.getReferencia());
        Assert.assertEquals(paseadorBuscado.getMonto(), pagoPaseadorCreado.getMonto());
        Assert.assertEquals(paseadorBuscado.getFechaLimite(), pagoPaseadorCreado.getFechaLimite());
    }
    
    @Test
    public void findPaseadorTest() {
        PagoPaseadorEntity pagoPaseador0 = datos.get(0);
        PagoPaseadorEntity pagoPaseadorBuscado = pagoPaseadorPersistence.find(pagoPaseador0.getId());
        Assert.assertNotNull(pagoPaseadorBuscado);
        Assert.assertEquals(pagoPaseador0.getReferencia(), pagoPaseadorBuscado.getReferencia());
        Assert.assertEquals(pagoPaseador0.getMonto(), pagoPaseadorBuscado.getMonto());
        Assert.assertEquals(pagoPaseador0.getFechaLimite(), pagoPaseadorBuscado.getFechaLimite());
    }
    
    @Test
    public void findByReferenciaTest() {
        PagoPaseadorEntity pagoPaseador0 = datos.get(0);
        PagoPaseadorEntity pagoPaseadorBuscado = pagoPaseadorPersistence.findByReferencia(pagoPaseador0.getReferencia());
       
        Assert.assertNotNull(pagoPaseadorBuscado);
        Assert.assertEquals(pagoPaseador0.getId(), pagoPaseadorBuscado.getId());
        Assert.assertEquals(pagoPaseador0.getMonto(), pagoPaseadorBuscado.getMonto());
        Assert.assertEquals(pagoPaseador0.getFechaLimite(), pagoPaseadorBuscado.getFechaLimite());
        
        pagoPaseadorBuscado = pagoPaseadorPersistence.findByReferencia(null);
        Assert.assertNull(pagoPaseadorBuscado);
    }
    
    @Test
    public void findByFechaLimiteTest() {
        
        PagoPaseadorEntity pagoPaseador0 = datos.get(0);
        PagoPaseadorEntity pagoPaseadorBuscado = pagoPaseadorPersistence.findByfechaLimite(pagoPaseador0.getFechaLimite());
       
        Assert.assertNotNull(pagoPaseadorBuscado);
        Assert.assertEquals(pagoPaseador0.getId(), pagoPaseadorBuscado.getId());
        Assert.assertEquals(pagoPaseador0.getReferencia(), pagoPaseadorBuscado.getReferencia());
        Assert.assertEquals(pagoPaseador0.getMonto(), pagoPaseadorBuscado.getMonto());
        
        pagoPaseadorBuscado = pagoPaseadorPersistence.findByfechaLimite(null);
        Assert.assertNull(pagoPaseadorBuscado);
    }
    
    
    @Test
    public void updatePaseadorTest() {
        PagoPaseadorEntity pagoPaseador0 = datos.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        PagoPaseadorEntity nuevoPagoPaseador = factory.manufacturePojo(PagoPaseadorEntity.class);
        nuevoPagoPaseador.setId(pagoPaseador0.getId());
        
        pagoPaseadorPersistence.update(nuevoPagoPaseador);
        PagoPaseadorEntity pagoPaseadorBuscado = em.find(PagoPaseadorEntity.class, pagoPaseador0.getId());
        Assert.assertEquals(nuevoPagoPaseador.getReferencia(), pagoPaseadorBuscado.getReferencia());
        Assert.assertEquals(nuevoPagoPaseador.getMonto(), pagoPaseadorBuscado.getMonto());
        Assert.assertEquals(nuevoPagoPaseador.getFechaLimite(), pagoPaseadorBuscado.getFechaLimite());
    }
    
    @Test
    public void deletePaseadorTest() {
        PagoPaseadorEntity pagoPaseador0 = datos.get(0);
        pagoPaseadorPersistence.delete(pagoPaseador0.getId());
        PagoPaseadorEntity pagoPaseadorBorrado = em.find(PagoPaseadorEntity.class, pagoPaseador0.getId());
        Assert.assertNull(pagoPaseadorBorrado);
    }
}
