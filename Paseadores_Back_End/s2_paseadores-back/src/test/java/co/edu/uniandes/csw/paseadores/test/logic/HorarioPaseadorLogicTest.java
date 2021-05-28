/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.logic;

import co.edu.uniandes.csw.paseadores.ejb.HorarioPaseadorLogic;
import co.edu.uniandes.csw.paseadores.ejb.PaseadorLogic;
import co.edu.uniandes.csw.paseadores.entities.HorarioEntity;
import co.edu.uniandes.csw.paseadores.entities.PaseadorEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.HorarioPersistence;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
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
 * @author Edgar Camilo Diaz Suarez
 */
@RunWith(Arquillian.class)
public class HorarioPaseadorLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private HorarioPaseadorLogic horarioPaseadorLogic;

    @Inject
    private PaseadorLogic paseadorLogic;

    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;

    private HorarioEntity horario = new HorarioEntity();
    
    private List<HorarioEntity> horarioData = new ArrayList<HorarioEntity>() ;
   
    private List<PaseadorEntity> paseadorData = new ArrayList<>();
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(HorarioEntity.class.getPackage())
                .addPackage(PaseadorEntity.class.getPackage())
                .addPackage(HorarioPaseadorLogic.class.getPackage())
                .addPackage(HorarioPersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    
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
        em.createQuery("delete from HorarioEntity").executeUpdate();
        em.createQuery("delete from PaseadorEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
       //  for (int i = 0; i < 3; i++) {
       // HorarioEntity horario = factory.manufacturePojo(HorarioEntity.class);
        horario = factory.manufacturePojo(HorarioEntity.class);
        horario.setDuracion(10);
        horario.setPaseador(new ArrayList<>());
        em.persist(horario);
       // horarioData.add(horario);
       //  }
        for (int i = 0; i < 3; i++) {
            PaseadorEntity entity = factory.manufacturePojo(PaseadorEntity.class);
            entity.setHorariosDisponibles(new ArrayList<>());
            //entity.getHorariosDisponibles().add(horarioData.get(i));
            entity.getHorariosDisponibles().add(horario);
            em.persist(entity);
            paseadorData.add(entity);
            // horarioData.get(i).getPaseador().add(entity);
            horario.getPaseador().add(entity);
           }
       
    }
    
    @Test
    public void addPaseadorTest()  throws BusinessLogicException{
        //HorarioEntity horario = horarioData.get(0);
        //PaseadorEntity newPaseador = paseadorData.get(0);
        PaseadorEntity newPaseador = factory.manufacturePojo(PaseadorEntity.class);
        paseadorLogic.createPaseador(newPaseador);
        PaseadorEntity paseadorEntity = horarioPaseadorLogic.addPaseador(horario.getId(), newPaseador.getId());
        Assert.assertNotNull(paseadorEntity);

        Assert.assertEquals(paseadorEntity.getId(), newPaseador.getId());
        Assert.assertEquals(paseadorEntity.getArl(), newPaseador.getArl());
        Assert.assertEquals(paseadorEntity.getCalificacionGlobal(), newPaseador.getCalificacionGlobal());
        Assert.assertEquals(paseadorEntity.getCorreo(), newPaseador.getCorreo());
        Assert.assertEquals(paseadorEntity.getCuentaBancaria(), newPaseador.getCuentaBancaria());
        Assert.assertEquals(paseadorEntity.getEps(), newPaseador.getEps());
        Assert.assertEquals(paseadorEntity.getIdentificacion(), newPaseador.getIdentificacion());
        Assert.assertEquals(paseadorEntity.getNombre(), newPaseador.getNombre());
        Assert.assertEquals(paseadorEntity.getNumeroCalificaciones(), newPaseador.getNumeroCalificaciones());
        Assert.assertEquals(paseadorEntity.getTelefono(), newPaseador.getTelefono());
        
    }

    @Test
    public void getPaseadoresTest() {
       
      //  HorarioEntity horario = horarioData.get(0);
        List<PaseadorEntity> paseadorEntities = horarioPaseadorLogic.getPaseadores(horario.getId());

        for (int i = 0; i < paseadorData.size(); i++) {
            Assert.assertTrue(paseadorEntities.contains(paseadorData.get(0)));
        }
    }
    
    @Test
    public void getPaseadorTest() {
        try {
            //HorarioEntity horario = horarioData.get(0);
            PaseadorEntity paseadorEntity = paseadorData.get(0);
            PaseadorEntity paseador = horarioPaseadorLogic.getPaseador(paseadorEntity.getId(), horario.getId());
            Assert.assertNotNull(paseador);
            
            Assert.assertEquals(paseadorEntity.getId(), paseador.getId());
            Assert.assertEquals(paseadorEntity.getArl(), paseador.getArl());
            
            Assert.assertEquals(paseadorEntity.getCalificacionGlobal(), paseador.getCalificacionGlobal());
            Assert.assertEquals(paseadorEntity.getCorreo(), paseador.getCorreo());
            Assert.assertEquals(paseadorEntity.getCuentaBancaria(), paseador.getCuentaBancaria());
            Assert.assertEquals(paseadorEntity.getEps(), paseador.getEps());
            Assert.assertEquals(paseadorEntity.getIdentificacion(), paseador.getIdentificacion());
            Assert.assertEquals(paseadorEntity.getNombre(), paseador.getNombre());
            Assert.assertEquals(paseadorEntity.getNumeroCalificaciones(), paseador.getNumeroCalificaciones());
            Assert.assertEquals(paseadorEntity.getTelefono(), paseador.getTelefono());
        } 
        catch (BusinessLogicException ex) {
            Assert.fail();
        }
    }
    
   
    @Test
    public void replacePaseadoresTest() throws BusinessLogicException {
        //HorarioEntity horario = horarioData.get(0);
        List<PaseadorEntity> nuevaLista = new ArrayList<>();
        
        for (int i = 0; i < 3; i++) {
            PaseadorEntity entity = factory.manufacturePojo(PaseadorEntity.class);
            entity.setHorariosDisponibles(new ArrayList<>());
            entity.getHorariosDisponibles().add(horario);
          
           // horarioPaseadorLogic.addPaseador(horario.getId(),entity.getId());
            paseadorLogic.createPaseador(entity);
            nuevaLista.add(entity);
        }
  
        horarioPaseadorLogic.replacePaseador(horario.getId(), nuevaLista);
        List<PaseadorEntity> paseadorEntities = horarioPaseadorLogic.getPaseadores(horario.getId());
        for (PaseadorEntity aNuevaLista : nuevaLista) {
            Assert.assertTrue(paseadorEntities.contains(aNuevaLista));
        }
      
    }
  
     @Test
    public void removePaseadorTest() {
      //  HorarioEntity horario = horarioData.get(0);
        for (PaseadorEntity paseador : paseadorData) {
            horarioPaseadorLogic.removePaseador(horario.getId(), paseador.getId());
        }
        Assert.assertTrue(horarioPaseadorLogic.getPaseadores(horario.getId()).isEmpty());
    }
    
}
