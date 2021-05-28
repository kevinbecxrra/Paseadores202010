/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.persistence;

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
 * @author Daniel Mateo Guatibonza Solano
 */
@RunWith(Arquillian.class)
public class PaseadorPersistenceTest {
    
    @Inject
    private PaseadorPersistence paseadorPersistence;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;
    
    private List<PaseadorEntity> datos = new ArrayList<>();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PaseadorEntity.class.getPackage())
                .addPackage(PaseadorPersistence.class.getPackage())
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
        em.createQuery("delete from PaseadorEntity").executeUpdate();
    }
    
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            PaseadorEntity nuevoPaseador = factory.manufacturePojo(PaseadorEntity.class);

            em.persist(nuevoPaseador);
            datos.add(nuevoPaseador);
        }
    }
    
    @Test
    public void createPaseadorTest()
    {
        PodamFactory factory = new PodamFactoryImpl();
        PaseadorEntity nuevoPaseador = factory.manufacturePojo(PaseadorEntity.class);
        PaseadorEntity paseadorCreado = paseadorPersistence.create(nuevoPaseador);
        Assert.assertNotNull(paseadorCreado);

        PaseadorEntity paseadorBuscado = em.find(PaseadorEntity.class, paseadorCreado.getId());
        Assert.assertEquals(paseadorBuscado.getNombre(), paseadorCreado.getNombre());
        Assert.assertEquals(paseadorBuscado.getIdentificacion(), paseadorCreado.getIdentificacion());
        Assert.assertEquals(paseadorBuscado.getEps(), paseadorCreado.getEps());
        Assert.assertEquals(paseadorBuscado.getArl(), paseadorCreado.getArl());
        Assert.assertEquals(paseadorBuscado.getCorreo(), paseadorCreado.getCorreo());
        Assert.assertEquals(paseadorBuscado.getTelefono(), paseadorCreado.getTelefono());
        Assert.assertEquals(paseadorBuscado.getCalificacionGlobal(), paseadorCreado.getCalificacionGlobal());
        Assert.assertEquals(paseadorBuscado.getCuentaBancaria(), paseadorCreado.getCuentaBancaria());
        Assert.assertEquals(paseadorBuscado.getNumeroCalificaciones(), paseadorCreado.getNumeroCalificaciones());
    }
    
    @Test
    public void findPaseadorTest() {
        PaseadorEntity paseador0 = datos.get(0);
        PaseadorEntity paseadorBuscado = paseadorPersistence.find(paseador0.getId());
        Assert.assertNotNull(paseadorBuscado);
        Assert.assertEquals(paseador0.getNombre(), paseadorBuscado.getNombre());
        Assert.assertEquals(paseador0.getIdentificacion(), paseadorBuscado.getIdentificacion());
        Assert.assertEquals(paseador0.getEps(), paseadorBuscado.getEps());
        Assert.assertEquals(paseador0.getArl(), paseadorBuscado.getArl());
        Assert.assertEquals(paseador0.getCorreo(), paseadorBuscado.getCorreo());
        Assert.assertEquals(paseador0.getTelefono(), paseadorBuscado.getTelefono());
        Assert.assertEquals(paseador0.getCalificacionGlobal(), paseadorBuscado.getCalificacionGlobal());
        Assert.assertEquals(paseador0.getCuentaBancaria(), paseadorBuscado.getCuentaBancaria());
        Assert.assertEquals(paseador0.getNumeroCalificaciones(), paseadorBuscado.getNumeroCalificaciones());
    }
    
    @Test
    public void findAllPaseadoresTest() {
        List<PaseadorEntity> listaPaseadores = paseadorPersistence.findAll();
        Assert.assertEquals(datos.size(), listaPaseadores.size());
        for(PaseadorEntity ent : listaPaseadores) {
            boolean found = false;
            for (PaseadorEntity entity : datos) {
                if (ent.getId().equals(entity.getId())) {
                    Assert.assertEquals(ent.getNombre(), entity.getNombre());
                    Assert.assertEquals(ent.getIdentificacion(), entity.getIdentificacion());
                    Assert.assertEquals(ent.getEps(), entity.getEps());
                    Assert.assertEquals(ent.getArl(), entity.getArl());
                    Assert.assertEquals(ent.getCorreo(), entity.getCorreo());
                    Assert.assertEquals(ent.getTelefono(), entity.getTelefono());
                    Assert.assertEquals(ent.getCalificacionGlobal(), entity.getCalificacionGlobal());
                    Assert.assertEquals(ent.getCuentaBancaria(), entity.getCuentaBancaria());
                    Assert.assertEquals(ent.getNumeroCalificaciones(), entity.getNumeroCalificaciones());
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    @Test
    public void findByIdentificacionTest() {
        PaseadorEntity paseador0 = datos.get(0);
        PaseadorEntity paseadorBuscado = paseadorPersistence.findByIdentificacion(paseador0.getIdentificacion());
        Assert.assertNotNull(paseadorBuscado);
        Assert.assertEquals(paseador0.getId(), paseadorBuscado.getId());
        Assert.assertEquals(paseador0.getNombre(), paseadorBuscado.getNombre());
        Assert.assertEquals(paseador0.getEps(), paseadorBuscado.getEps());
        Assert.assertEquals(paseador0.getArl(), paseadorBuscado.getArl());
        Assert.assertEquals(paseador0.getCorreo(), paseadorBuscado.getCorreo());
        Assert.assertEquals(paseador0.getTelefono(), paseadorBuscado.getTelefono());
        Assert.assertEquals(paseador0.getCalificacionGlobal(), paseadorBuscado.getCalificacionGlobal());
        Assert.assertEquals(paseador0.getCuentaBancaria(), paseadorBuscado.getCuentaBancaria());
        Assert.assertEquals(paseador0.getNumeroCalificaciones(), paseadorBuscado.getNumeroCalificaciones());
        
        paseadorBuscado = paseadorPersistence.findByIdentificacion(null);
        Assert.assertNull(paseadorBuscado);
    }
    
    @Test
    public void findByCorreoTest() {
        PaseadorEntity paseador0 = datos.get(0);
        PaseadorEntity paseadorBuscado = paseadorPersistence.findByCorreo(paseador0.getCorreo());
        Assert.assertNotNull(paseadorBuscado);
        Assert.assertEquals(paseador0.getId(), paseadorBuscado.getId());
        Assert.assertEquals(paseador0.getNombre(), paseadorBuscado.getNombre());
        Assert.assertEquals(paseador0.getIdentificacion(), paseadorBuscado.getIdentificacion());
        Assert.assertEquals(paseador0.getEps(), paseadorBuscado.getEps());
        Assert.assertEquals(paseador0.getArl(), paseadorBuscado.getArl());
        Assert.assertEquals(paseador0.getTelefono(), paseadorBuscado.getTelefono());
        Assert.assertEquals(paseador0.getCalificacionGlobal(), paseadorBuscado.getCalificacionGlobal());
        Assert.assertEquals(paseador0.getCuentaBancaria(), paseadorBuscado.getCuentaBancaria());
        Assert.assertEquals(paseador0.getNumeroCalificaciones(), paseadorBuscado.getNumeroCalificaciones());
        
        paseadorBuscado = paseadorPersistence.findByCorreo(null);
        Assert.assertNull(paseadorBuscado);
    }
    
    @Test
    public void findByCalificacionGlobalInRange() {
        Double calificacionMedia;
        Double calificacionMayor = datos.get(0).getCalificacionGlobal();
        Double calificacionMenor = datos.get(0).getCalificacionGlobal();
        int posicionMayor = 0; int posicionMenor = 0;
        for(int i=1; i < datos.size(); i++)
        {
            if(calificacionMayor < datos.get(i).getCalificacionGlobal())
            {
                calificacionMayor = datos.get(i).getCalificacionGlobal();
                posicionMayor = i;
            }
            if(calificacionMenor > datos.get(i).getCalificacionGlobal())
            {
                calificacionMenor = datos.get(i).getCalificacionGlobal();
                posicionMenor = i;
            }
        }
        
        switch (posicionMayor+posicionMenor) {
            case 1:
                calificacionMedia = datos.get(2).getCalificacionGlobal();
                break;
            case 2:
                calificacionMedia = datos.get(1).getCalificacionGlobal();
                break;
            default:
                calificacionMedia = datos.get(0).getCalificacionGlobal();
                break;
        }
        Assert.assertEquals(0,paseadorPersistence.findByCalificacionGlobalInRange(calificacionMedia, calificacionMenor).size());
        Assert.assertEquals(0,paseadorPersistence.findByCalificacionGlobalInRange(calificacionMayor, calificacionMedia).size());
        Assert.assertEquals(0,paseadorPersistence.findByCalificacionGlobalInRange(calificacionMayor, calificacionMenor).size());
        Assert.assertEquals(1,paseadorPersistence.findByCalificacionGlobalInRange(calificacionMenor, calificacionMenor).size());
        Assert.assertEquals(1,paseadorPersistence.findByCalificacionGlobalInRange(calificacionMedia, calificacionMedia).size());
        Assert.assertEquals(1,paseadorPersistence.findByCalificacionGlobalInRange(calificacionMayor, calificacionMayor).size());
        Assert.assertEquals(2,paseadorPersistence.findByCalificacionGlobalInRange(calificacionMenor, calificacionMedia).size());
        Assert.assertEquals(2,paseadorPersistence.findByCalificacionGlobalInRange(calificacionMedia, calificacionMayor).size());
        Assert.assertEquals(3,paseadorPersistence.findByCalificacionGlobalInRange(calificacionMenor, calificacionMayor).size());
    }
    
    @Test
    public void updatePaseadorTest() {
        PaseadorEntity paseador0 = datos.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        PaseadorEntity nuevoPaseador = factory.manufacturePojo(PaseadorEntity.class);
        nuevoPaseador.setId(paseador0.getId());
        
        paseadorPersistence.update(nuevoPaseador);
        PaseadorEntity paseadorBuscado = em.find(PaseadorEntity.class, paseador0.getId());
        Assert.assertNotNull(paseadorBuscado);
        Assert.assertEquals(nuevoPaseador.getId(), paseadorBuscado.getId());
        Assert.assertEquals(nuevoPaseador.getNombre(), paseadorBuscado.getNombre());
        Assert.assertEquals(nuevoPaseador.getIdentificacion(), paseadorBuscado.getIdentificacion());
        Assert.assertEquals(nuevoPaseador.getEps(), paseadorBuscado.getEps());
        Assert.assertEquals(nuevoPaseador.getArl(), paseadorBuscado.getArl());
        Assert.assertEquals(nuevoPaseador.getTelefono(), paseadorBuscado.getTelefono());
        Assert.assertEquals(nuevoPaseador.getCalificacionGlobal(), paseadorBuscado.getCalificacionGlobal());
        Assert.assertEquals(nuevoPaseador.getCuentaBancaria(), paseadorBuscado.getCuentaBancaria());
        Assert.assertEquals(nuevoPaseador.getNumeroCalificaciones(), paseadorBuscado.getNumeroCalificaciones());
    }
    
    @Test
    public void deletePaseadorTest() {
        PaseadorEntity paseador0 = datos.get(0);
        paseadorPersistence.delete(paseador0.getId());
        PaseadorEntity paseadorBorrado = em.find(PaseadorEntity.class, paseador0.getId());
        Assert.assertNull(paseadorBorrado);
    }
}
