/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.persistence;

import co.edu.uniandes.csw.paseadores.entities.PaseoEntity;
import co.edu.uniandes.csw.paseadores.persistence.PaseoPersistence;
import java.util.ArrayList;
import java.util.Date;
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
 * @author Kevin Camilo Becerra Walteros
 */

@RunWith(Arquillian.class)
public class PaseoPersistenceTest {
    
    @Inject
    private PaseoPersistence paseoPersistence;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    UserTransaction utx;

    private List<PaseoEntity> data = new ArrayList<>();
    
    
  @Deployment
    public static JavaArchive createDeployment(){
    
        return ShrinkWrap.create(JavaArchive.class)
             .addPackage(PaseoEntity.class.getPackage())
             .addPackage(PaseoPersistence.class.getPackage())
             .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
             .addAsManifestResource("META-INF/beans.xml", "beans.xml" );
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
        em.createQuery("delete from PaseoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            PaseoEntity entity = factory.manufacturePojo(PaseoEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Paseo.
     */
    @Test
    public void createPaseoTest() {
        PodamFactory factory = new PodamFactoryImpl();
        PaseoEntity newEntity = factory.manufacturePojo(PaseoEntity.class);
        PaseoEntity result = paseoPersistence.create(newEntity);

        Assert.assertNotNull(result);

        PaseoEntity entity = em.find(PaseoEntity.class, result.getId());

        Assert.assertEquals(newEntity.getDuracion(), entity.getDuracion());
        Assert.assertEquals(newEntity.getCosto(), entity.getCosto());
        Assert.assertEquals(newEntity.getCupoMaximo(), entity.getCupoMaximo());
        Assert.assertEquals(newEntity.getDisponible(), entity.getDisponible());
    }

    /**
     * Prueba para consultar un Paseo.
     */
    @Test
    public void findPaseoTest() {
        PaseoEntity entity = data.get(0);
        PaseoEntity newEntity = paseoPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getDuracion(), newEntity.getDuracion());
        Assert.assertEquals(newEntity.getCosto(), entity.getCosto());
        Assert.assertEquals(newEntity.getCupoMaximo(), entity.getCupoMaximo());
        Assert.assertEquals(newEntity.getDisponible(), entity.getDisponible());
        
    }

    @Test
    public void findByCostoInRange() {
        Double costo;
        Double costoMayor = data.get(0).getCosto();
        Double costoMenor = data.get(0).getCosto();
        int posicionMayor = 0; int posicionMenor = 0;
        for(int i=1; i < data.size(); i++)
        {
            if(costoMayor < data.get(i).getCosto())
            {
                costoMayor = data.get(i).getCosto();
                posicionMayor = i;
            }
            if(costoMenor > data.get(i).getCosto())
            {
                costoMenor = data.get(i).getCosto();
                posicionMenor = i;
            }
        }
        
        switch (posicionMayor+posicionMenor) {
            case 1:
                costo = data.get(2).getCosto();
                break;
            case 2:
                costo = data.get(1).getCosto();
                break;
            default:
               costo = data.get(0).getCosto();
                break;
        }
        Assert.assertEquals(0,paseoPersistence.findByCostoInRange(costo, costoMenor).size());
        Assert.assertEquals(0,paseoPersistence.findByCostoInRange(costoMayor, costo).size());
        Assert.assertEquals(0,paseoPersistence.findByCostoInRange(costoMayor, costoMenor).size());
        Assert.assertEquals(1,paseoPersistence.findByCostoInRange(costoMenor, costoMenor).size());
        Assert.assertEquals(1,paseoPersistence.findByCostoInRange(costo, costo).size());
        Assert.assertEquals(1,paseoPersistence.findByCostoInRange(costoMayor, costoMayor).size());
        Assert.assertEquals(2,paseoPersistence.findByCostoInRange(costoMenor, costo).size());
        Assert.assertEquals(2,paseoPersistence.findByCostoInRange(costo, costoMayor).size());
        Assert.assertEquals(3,paseoPersistence.findByCostoInRange(costoMenor, costoMayor).size());
    }

    @Test
    public void findByHoraInicioInRange() {
        Date horaInicio;
        Date horaInicioMayor = data.get(0).getHoraInicio();
        Date horaInicioMenor = data.get(0).getHoraInicio();
        int posicionMayor = 0; int posicionMenor = 0;
        for(int i=1; i < data.size(); i++)
        {
            if(horaInicioMayor.compareTo(data.get(i).getHoraInicio()) < 0)
            {
                horaInicioMayor = data.get(i).getHoraInicio();
                posicionMayor = i;
            }
            if(horaInicioMenor.compareTo(data.get(i).getHoraInicio())> 0)
            {
                horaInicioMenor = data.get(i).getHoraInicio();
                posicionMenor = i;
            }
        }
        
        switch (posicionMayor+posicionMenor) {
            case 1:
                horaInicio = data.get(2).getHoraInicio();
                break;
            case 2:
               horaInicio = data.get(1).getHoraInicio();
                break;
            default:
               horaInicio = data.get(0).getHoraInicio();
                break;
        }
        Assert.assertEquals(0,(paseoPersistence.findByHoraInicioInRange(horaInicio, horaInicioMenor)).size());
        Assert.assertEquals(0,paseoPersistence.findByHoraInicioInRange(horaInicioMayor, horaInicio).size());
        Assert.assertEquals(0,paseoPersistence.findByHoraInicioInRange(horaInicioMayor, horaInicioMenor).size());
        Assert.assertEquals(1,paseoPersistence.findByHoraInicioInRange(horaInicioMenor, horaInicioMenor).size());
        Assert.assertEquals(1,paseoPersistence.findByHoraInicioInRange(horaInicio, horaInicio).size());
        Assert.assertEquals(1,paseoPersistence.findByHoraInicioInRange(horaInicioMayor, horaInicioMayor).size());
        Assert.assertEquals(2,paseoPersistence.findByHoraInicioInRange(horaInicioMenor, horaInicio).size());
        Assert.assertEquals(2,paseoPersistence.findByHoraInicioInRange(horaInicio, horaInicioMayor).size());
        Assert.assertEquals(3,paseoPersistence.findByHoraInicioInRange(horaInicioMenor, horaInicioMayor).size());
    }
    
    
    @Test
    public void findAllPaseosTest() {
        List<PaseoEntity> listaPaseos = paseoPersistence.findAll();
        Assert.assertEquals(data.size(), listaPaseos.size());
        for(PaseoEntity ent : listaPaseos) {
            boolean found = false;
            for (PaseoEntity entity : data) {
                if (ent.getId().equals(entity.getId())) {
                    Assert.assertEquals(ent.getCosto(), entity.getCosto());
                    Assert.assertEquals(ent.getCupoMaximo(), entity.getCupoMaximo());
                    Assert.assertEquals(ent.getDuracion(), entity.getDuracion());
                    Assert.assertEquals(ent.getDisponible(), entity.getDisponible());
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }

    @Test
    public void updatePaseoTest() {
        PaseoEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        PaseoEntity newEntity = factory.manufacturePojo(PaseoEntity.class);

        newEntity.setId(entity.getId());

        paseoPersistence.update(newEntity);

        PaseoEntity resp = em.find(PaseoEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getDuracion(), resp.getDuracion());
        Assert.assertEquals(newEntity.getCosto(), resp.getCosto());
        Assert.assertEquals(newEntity.getCupoMaximo(), resp.getCupoMaximo());
        Assert.assertEquals(newEntity.getDisponible(), resp.getDisponible());
    }

    /**
     * Prueba para eliminar un Paseo.
     */
    @Test
    public void deletePaseoTest() {
        PaseoEntity entity = data.get(0);
        paseoPersistence.delete(entity.getId());
        PaseoEntity deleted = em.find(PaseoEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
