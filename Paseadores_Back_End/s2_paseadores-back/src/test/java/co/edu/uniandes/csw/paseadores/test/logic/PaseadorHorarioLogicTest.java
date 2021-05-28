/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.logic;

import co.edu.uniandes.csw.paseadores.ejb.HorarioLogic;
import co.edu.uniandes.csw.paseadores.ejb.PaseadorHorarioLogic;
import co.edu.uniandes.csw.paseadores.ejb.RecorridoPuntoLogic;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import co.edu.uniandes.csw.paseadores.entities.HorarioEntity;
import co.edu.uniandes.csw.paseadores.entities.PaseadorEntity;
import co.edu.uniandes.csw.paseadores.entities.RecorridoEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.PaseadorPersistence;
import co.edu.uniandes.csw.paseadores.persistence.RecorridoPersistence;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

/**
 *
 * @author Alvaro Plata
 */
@RunWith(Arquillian.class)
public class PaseadorHorarioLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private PaseadorHorarioLogic paseadorHorarioLogic;
    
    @Inject
    private HorarioLogic horarioLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private PaseadorEntity paseador;

    private List<HorarioEntity> horariosData = new ArrayList();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PaseadorEntity.class.getPackage())
                .addPackage(PaseadorHorarioLogic.class.getPackage())
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
        em.createQuery("delete from PaseadorEntity").executeUpdate();
        em.createQuery("delete from HorarioEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        
        paseador = factory.manufacturePojo(PaseadorEntity.class);
        paseador.setId(1L);
        paseador.setHorariosDisponibles(new ArrayList<>());
        em.persist(paseador);
        
        for (int i = 0; i < 3; i++) {
            HorarioEntity entity = factory.manufacturePojo(HorarioEntity.class);
            entity.setPaseador(new ArrayList<>());
            entity.getPaseador().add(paseador);
            em.persist(entity);
            horariosData.add(entity);
            paseador.getHorariosDisponibles().add(entity);
        }
    }
    
    
    /**
     * Prueba para asociar un Horario existente a un Paseador.
     */
    @Test
    public void addHorarioTest() {
        HorarioEntity horarioEntity = factory.manufacturePojo(HorarioEntity.class);
        horarioLogic.createHorario(horarioEntity);
        HorarioEntity response = paseadorHorarioLogic.addHorario(horarioEntity.getId(), paseador.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(horarioEntity.getId(), response.getId());
        Assert.assertEquals(horarioEntity.getDia(), response.getDia());
        Assert.assertEquals(horarioEntity.getDuracion(), response.getDuracion());
        Assert.assertEquals(horarioEntity.getOcupado(), response.getOcupado());
        Assert.assertEquals(horarioEntity.getPaseo1(), response.getPaseo1());
        Assert.assertEquals(horarioEntity.getPaseo2(), response.getPaseo2());
        
        HorarioEntity lastHorario = paseadorHorarioLogic.getHorario(paseador.getId(), horarioEntity.getId());
        
        Assert.assertNotNull(lastHorario);
        Assert.assertEquals(lastHorario.getId(), response.getId());
        Assert.assertEquals(lastHorario.getDia(), response.getDia());
        Assert.assertEquals(lastHorario.getDuracion(), response.getDuracion());
        Assert.assertEquals(lastHorario.getOcupado(), response.getOcupado());
        Assert.assertEquals(lastHorario.getPaseador(), response.getPaseador());
        Assert.assertEquals(lastHorario.getPaseo1(), response.getPaseo1());
        Assert.assertEquals(lastHorario.getPaseo2(), response.getPaseo2());
    }
    
    /**
     * Prueba para consultar los Horarios de un Paseador.
     */
    @Test
    public void getHorariosTest()
    {
        List<HorarioEntity> horariosEntities = paseadorHorarioLogic.getHorarios(paseador.getId());
        
        Assert.assertEquals(horariosData.size(), horariosEntities.size());
        
        for (int i = 0; i < horariosData.size(); i++) {
            Assert.assertTrue(horariosEntities.contains(horariosData.get(0)));
        }
    }
    
    /**
     * Prueba para consultar un horario de un paseador.
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    @Test
    public void getHorarioTest() throws BusinessLogicException 
    {
        HorarioEntity horarioEntity = horariosData.get(0);
        HorarioEntity response = paseadorHorarioLogic.getHorario(paseador.getId(), horarioEntity.getId());
        Assert.assertNotNull(response);

        Assert.assertEquals(horarioEntity.getId(), response.getId());
        Assert.assertEquals(horarioEntity.getDia(), response.getDia());
        Assert.assertEquals(horarioEntity.getDuracion(), response.getDuracion());
        Assert.assertEquals(horarioEntity.getOcupado(), response.getOcupado());
        Assert.assertEquals(horarioEntity.getPaseador(), response.getPaseador());
        Assert.assertEquals(horarioEntity.getPaseo1(), response.getPaseo1());
        Assert.assertEquals(horarioEntity.getPaseo2(), response.getPaseo2());
    }
    
    /**
     * Prueba para actualizar los horarios de un paseador.
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    @Test
    public void replaceHorariosTest() throws BusinessLogicException {
        List<HorarioEntity> nuevaLista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            HorarioEntity entity = factory.manufacturePojo(HorarioEntity.class);
            entity.setPaseador(new ArrayList<>());
            entity.getPaseador().add(paseador);
            horarioLogic.createHorario(entity);
            nuevaLista.add(entity);
        }
        paseadorHorarioLogic.replaceHorarios(paseador.getId(), nuevaLista);
        List<HorarioEntity> horarioEntities = paseadorHorarioLogic.getHorarios(paseador.getId());
        for (HorarioEntity aNuevaLista : nuevaLista) {
            Assert.assertTrue(horarioEntities.contains(aNuevaLista));
        }
    }
    
    /**
     * Prueba desasociar un horario con un paseador.
     *
     */
    @Test
    public void removeHorarioTest() {
        for (HorarioEntity horario : horariosData) {
            paseadorHorarioLogic.removeHorario(paseador.getId(), horario.getId());
        }
        Assert.assertTrue(paseadorHorarioLogic.getHorarios(paseador.getId()).isEmpty());
    }
}


