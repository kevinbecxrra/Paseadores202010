/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.persistence;

import co.edu.uniandes.csw.paseadores.entities.HorarioEntity;
import co.edu.uniandes.csw.paseadores.persistence.HorarioPersistence;
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
public class HorarioPersistenceTest {
    @Inject
    private HorarioPersistence horarioPersistence;

    @PersistenceContext
    private EntityManager em;

    @Inject
    UserTransaction utx;

    private List<HorarioEntity> data = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(HorarioEntity.class.getPackage())
                .addPackage(HorarioPersistence.class.getPackage())
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
            System.out.println("holo");
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
        em.createQuery("delete from HorarioEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        PodamFactory factory = new PodamFactoryImpl();
        for (int i = 0; i < 3; i++) {
            HorarioEntity entity = factory.manufacturePojo(HorarioEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    /**
     * Prueba para crear un Horario.
     */
    @Test
    public void createHorarioTest() {
        PodamFactory factory = new PodamFactoryImpl();
        HorarioEntity newEntity = factory.manufacturePojo(HorarioEntity.class);
        HorarioEntity result = horarioPersistence.create(newEntity);

        Assert.assertNotNull(result);

        HorarioEntity entity = em.find(HorarioEntity.class, result.getId());

        Assert.assertEquals(newEntity.getDuracion(), entity.getDuracion());
        Assert.assertEquals(newEntity.getDia(), entity.getDia());
        Assert.assertEquals(newEntity.getOcupado(), entity.getOcupado());


    }

    /**
     * Prueba para consultar un Horario.
     */
    @Test
    public void findHorarioTest() {
        HorarioEntity entity = data.get(0);
        HorarioEntity newEntity = horarioPersistence.find(entity.getId());
        Assert.assertNotNull(newEntity);
        Assert.assertEquals(entity.getDuracion(), newEntity.getDuracion());
        Assert.assertEquals(entity.getOcupado(), newEntity.getOcupado());
        Assert.assertEquals(entity.getDia(), newEntity.getDia());
    }
    
       @Test
    public void findByDiaInInRange() {
        Date diaInicio;
        Date diaInicioMayor = data.get(0).getDia();
        Date diaInicioMenor = data.get(0).getDia();
        int posicionMayor = 0; int posicionMenor = 0;
        for(int i=1; i < data.size(); i++)
        {
            if(diaInicioMayor.compareTo(data.get(i).getDia()) < 0)
            {
                diaInicioMayor = data.get(i).getDia();
                posicionMayor = i;
            }
            if(diaInicioMenor.compareTo(data.get(i).getDia())> 0)
            {
                diaInicioMenor = data.get(i).getDia();
                posicionMenor = i;
            }
        }
        
        switch (posicionMayor+posicionMenor) {
            case 1:
                diaInicio = data.get(2).getDia();
                break;
            case 2:
               diaInicio = data.get(1).getDia();
                break;
            default:
               diaInicio = data.get(0).getDia();
                break;
        }
        Assert.assertEquals(0,(horarioPersistence.findByDiaInRange(diaInicio, diaInicioMenor)).size());
        Assert.assertEquals(0,horarioPersistence.findByDiaInRange(diaInicioMayor, diaInicio).size());
        Assert.assertEquals(0,horarioPersistence.findByDiaInRange(diaInicioMayor, diaInicioMenor).size());
        Assert.assertEquals(1,horarioPersistence.findByDiaInRange(diaInicioMenor, diaInicioMenor).size());
        Assert.assertEquals(1,horarioPersistence.findByDiaInRange(diaInicio, diaInicio).size());
        Assert.assertEquals(1,horarioPersistence.findByDiaInRange(diaInicioMayor, diaInicioMayor).size());
        Assert.assertEquals(2,horarioPersistence.findByDiaInRange(diaInicioMenor, diaInicio).size());
        Assert.assertEquals(2,horarioPersistence.findByDiaInRange(diaInicio, diaInicioMayor).size());
        Assert.assertEquals(3,horarioPersistence.findByDiaInRange(diaInicioMenor, diaInicioMayor).size());
    }

    /**
     * Prueba para actualizar un Horario.
     */
    @Test
    public void updateHorarioTest() {
        HorarioEntity entity = data.get(0);
        PodamFactory factory = new PodamFactoryImpl();
        HorarioEntity newEntity = factory.manufacturePojo(HorarioEntity.class);

        newEntity.setId(entity.getId());

        horarioPersistence.update(newEntity);

        HorarioEntity resp = em.find(HorarioEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getDuracion(), resp.getDuracion());
        Assert.assertEquals(newEntity.getOcupado(), resp.getOcupado());
        Assert.assertEquals(newEntity.getDia(), resp.getDia());

        
    }

    /**
     * Prueba para eliminar un Horario.
     */
    @Test
    public void deleteHorarioTest() {
        HorarioEntity entity = data.get(0);
        horarioPersistence.delete(entity.getId());
        HorarioEntity deleted = em.find(HorarioEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}

