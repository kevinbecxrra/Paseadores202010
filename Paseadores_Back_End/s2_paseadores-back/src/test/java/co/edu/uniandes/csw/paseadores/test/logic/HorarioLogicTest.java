/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.logic;

import co.edu.uniandes.csw.paseadores.ejb.HorarioLogic;
import co.edu.uniandes.csw.paseadores.ejb.PaseoLogic;
import co.edu.uniandes.csw.paseadores.entities.HorarioEntity;
import co.edu.uniandes.csw.paseadores.entities.PaseoEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.HorarioPersistence;
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
 * @author Edgar Camilo Diaz Suarez
 */
@RunWith(Arquillian.class)
public class HorarioLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private HorarioLogic horarioLogic;

    @Inject
    private PaseoLogic paseoLogic;
    
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<HorarioEntity> horarioData = new ArrayList<HorarioEntity>();
  
    private List<PaseoEntity> paseoData = new ArrayList<PaseoEntity>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PaseoEntity.class.getPackage())
                .addPackage(HorarioEntity.class.getPackage())
                .addPackage(HorarioLogic.class.getPackage())
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
    
     private void clearData() {
        em.createQuery("delete from HorarioEntity").executeUpdate();
    }
     
    
     private void insertData() {
        for (int i = 0; i < 3; i++) {
            HorarioEntity horario = factory.manufacturePojo(HorarioEntity.class);
            PaseoEntity paseo1 = factory.manufacturePojo(PaseoEntity.class);
           
            em.persist(paseo1);
            
            horario.setPaseo1(paseo1);
            paseo1.setHorario1(horario);
            
            horario.setPaseo2(paseo1);
            paseo1.setHorario2(horario);
            
            em.persist(horario);
            
            horarioData.add(horario);
            paseoData.add(paseo1);
        }
    }
    
    @Test
    public void createHorarioTest()
    {
        HorarioEntity nuevoHorario = factory.manufacturePojo(HorarioEntity.class);
        HorarioEntity horarioRespuesta = null;
        
        horarioRespuesta = horarioLogic.createHorario(nuevoHorario);
    
        Assert.assertNotNull(horarioRespuesta);
        HorarioEntity horarioEncontrado = em.find(HorarioEntity.class, horarioRespuesta.getId());
        Assert.assertEquals(nuevoHorario.getId(), horarioEncontrado.getId());
        Assert.assertEquals(nuevoHorario.getDia(), horarioEncontrado.getDia());
        Assert.assertEquals(nuevoHorario.getDuracion(), horarioEncontrado.getDuracion());
        Assert.assertEquals(nuevoHorario.getOcupado(), horarioEncontrado.getOcupado());
    }
    
    @Test
    public void getHorarioTest()
    {        
        HorarioEntity horario = horarioData.get(0);
        HorarioEntity horarioResultado = horarioLogic.getHorario(horario.getId());
        Assert.assertNotNull(horarioResultado);
        Assert.assertEquals(horarioResultado.getId(), horario.getId());
        Assert.assertEquals(horarioResultado.getDia(), horario.getDia());
        Assert.assertEquals(horarioResultado.getDuracion(), horario.getDuracion());
        Assert.assertEquals(horarioResultado.getOcupado(), horario.getOcupado());
       
        horario = factory.manufacturePojo(HorarioEntity.class);
        horarioResultado = horarioLogic.getHorario(horario.getId());
        Assert.assertNull(horarioResultado);
    }
    
    @Test
    public void updateHorarioTest()
    {
        HorarioEntity horario = horarioData.get(0);
        HorarioEntity newHorario = factory.manufacturePojo(HorarioEntity.class);
        newHorario.setId(horario.getId());
        newHorario.setDia(horario.getDia());
        newHorario.setDuracion(horario.getDuracion());
        newHorario.setOcupado(horario.getOcupado());
        
        HorarioEntity horarioRespuesta = horarioLogic.createHorario(newHorario);
        
        Assert.assertNotNull(horarioRespuesta);
        HorarioEntity horarioEncontrado = em.find(HorarioEntity.class, horarioRespuesta.getId());
        Assert.assertEquals(newHorario.getId(), horarioEncontrado.getId());
        Assert.assertEquals(newHorario.getDia(), horarioEncontrado.getDia());
        Assert.assertEquals(newHorario.getDuracion(), horarioEncontrado.getDuracion());
        Assert.assertEquals(newHorario.getOcupado(), horarioEncontrado.getOcupado());
       
    }

    @Test()
    public void deleteHorarioTest() {
        HorarioEntity horario = horarioData.get(2);
        horarioLogic.deleteHorario(horario.getId());
        Assert.assertNull(horarioLogic.getHorario(horario.getId()));
    }
    
    @Test
    public void addPaseo1Test()
    {
        HorarioEntity horario = horarioData.get(0);
        PaseoEntity paseo = factory.manufacturePojo(PaseoEntity.class);
        boolean creadoCorrectamente = false;
        while(!creadoCorrectamente)
        {
            try
            {
                paseo = factory.manufacturePojo(PaseoEntity.class);
                paseo.setCosto(10000.0);
                paseo.setCupoMaximo(10);
                paseo = paseoLogic.createPaseo(paseo);
                creadoCorrectamente = true;
            }
            catch(BusinessLogicException ble) {}
        }
        try
        {
            horarioLogic.addPaseo1(horario.getId(), paseo.getId());
        }
        catch(BusinessLogicException ble)
        {
            Assert.fail("No debería mandar excepcion");
        }
        
        try
        {
            horarioLogic.removePaseo1(horario.getId());
            paseoLogic.deletePaseo(paseo.getId());
            horarioLogic.addPaseo1(horario.getId(), paseo.getId());
            Assert.fail("Debería mandar excepcion ya que el PagoCliente no existe");
        }
        catch(BusinessLogicException ble){}
    }
    
    @Test
    public void addPaseo2Test()
    {
        HorarioEntity horario = horarioData.get(0);
        PaseoEntity paseo = factory.manufacturePojo(PaseoEntity.class);
        boolean creadoCorrectamente = false;
        while(!creadoCorrectamente)
        {
            try
            {
                paseo = factory.manufacturePojo(PaseoEntity.class);
                paseo.setCosto(10000.0);
                paseo.setCupoMaximo(10);
                paseo = paseoLogic.createPaseo(paseo);
                creadoCorrectamente = true;
            }
            catch(BusinessLogicException ble) {}
        }
        try
        {
            horarioLogic.addPaseo2(horario.getId(), paseo.getId());
        }
        catch(BusinessLogicException ble)
        {
            Assert.fail("No debería mandar excepcion");
        }
        
        try
        {
            horarioLogic.removePaseo2(horario.getId());
            paseoLogic.deletePaseo(paseo.getId());
            horarioLogic.addPaseo2(horario.getId(), paseo.getId());
            Assert.fail("Debería mandar excepcion ya que el PagoCliente no existe");
        }
        catch(BusinessLogicException ble){}
    }
     
    
    
    @Test
    public void getPaseo1Test()
    {
        HorarioEntity horario = horarioData.get(0);
        PaseoEntity paseo = factory.manufacturePojo(PaseoEntity.class);
        boolean creadoCorrectamente = false;
        while(!creadoCorrectamente)
        {
            try
            {
                paseo = factory.manufacturePojo(PaseoEntity.class);
                paseo.setCosto(10000.0);
                paseo.setCupoMaximo(10);
                paseo = paseoLogic.createPaseo(paseo);
                creadoCorrectamente = true;
            }
            catch(BusinessLogicException ble) {}
        }

        try
        {
            horarioLogic.addPaseo1(horario.getId(), paseo.getId());
            Assert.assertEquals(paseo.getId(), horarioLogic.getPaseo1(horario.getId()).getId());
        }
        catch(BusinessLogicException blse)
        {
            Assert.fail("No debería mandar excepcion");
        }
        try{
        horarioLogic.removePaseo1(horario.getId());
        Assert.assertNull(horarioLogic.getPaseo1(horario.getId()));
        }
        catch(BusinessLogicException dnf){}
    }
    
    @Test
    public void getPaseo2Test()
    {
        HorarioEntity horario = horarioData.get(0);
        PaseoEntity paseo = factory.manufacturePojo(PaseoEntity.class);
        boolean creadoCorrectamente = false;
        while(!creadoCorrectamente)
        {
            try
            {
                paseo = factory.manufacturePojo(PaseoEntity.class);
                paseo.setCosto(10000.0);
                paseo.setCupoMaximo(10);
                paseo = paseoLogic.createPaseo(paseo);
                creadoCorrectamente = true;
            }
            catch(BusinessLogicException ble) {}
        }

        try
        {
            horarioLogic.addPaseo2(horario.getId(), paseo.getId());
            Assert.assertEquals(paseo.getId(), horarioLogic.getPaseo2(horario.getId()).getId());
            horarioLogic.removePaseo2(horario.getId());
            Assert.assertNull(horarioLogic.getPaseo2(horario.getId()));
        }
        catch(BusinessLogicException ble)
        {
            Assert.fail("No debería mandar excepcion");
        }
    
    }
  
    
     @Test
    public void replacePaseo1Test()
    {
        HorarioEntity horario = horarioData.get(0);
        PaseoEntity paseo = factory.manufacturePojo(PaseoEntity.class);
        boolean creadoCorrectamente = false;
        while(!creadoCorrectamente)
        {
            try
            {
                paseo = factory.manufacturePojo(PaseoEntity.class);
                paseo.setCosto(10000.0);
                paseo.setCupoMaximo(10);
                paseo = paseoLogic.createPaseo(paseo);
                creadoCorrectamente = true;
            }
            catch(BusinessLogicException ble) {}
        }

        try
        {
            horarioLogic.updatePaseo1(horario.getId(),paseo.getId());
            Assert.assertEquals(paseo.getId(), paseoLogic.getPaseo(paseo.getId()).getId());
        }
        catch(BusinessLogicException ble)
        {
            Assert.fail("No debería mandar excepcion");
        }
        horarioLogic.deleteHorario(horario.getId());
        paseoLogic.deletePaseo(paseo.getId());
        try
        {
            horarioLogic.removePaseo1(horarioData.get(0).getId());
            Assert.fail("Debería mandar excepcion ya que el horario no existe");
        }
        catch(BusinessLogicException ble){}
        try
        {
            HorarioEntity horarioNoExistente = factory.manufacturePojo(HorarioEntity.class);
            horarioLogic.removePaseo1(horarioNoExistente.getId());
            Assert.fail("Debería mandar excepcion ya que el paseo no existe");
        }
        catch(BusinessLogicException ble){}
    }

    @Test
    public void replacePaseo2Test()
    {
        HorarioEntity horario = horarioData.get(0);
        PaseoEntity paseo = factory.manufacturePojo(PaseoEntity.class);
        boolean creadoCorrectamente = false;
        while(!creadoCorrectamente)
        {
            try
            {
                paseo = factory.manufacturePojo(PaseoEntity.class);
                paseo.setCosto(10000.0);
                paseo.setCupoMaximo(10);
                paseo = paseoLogic.createPaseo(paseo);
                creadoCorrectamente = true;
            }
            catch(BusinessLogicException ble) {}
        }

        try
        {
            horarioLogic.updatePaseo2( horario.getId(),paseo.getId());
            Assert.assertEquals(paseo.getId(), paseoLogic.getPaseo(paseo.getId()).getId());
        }
        catch(BusinessLogicException ble)
        {
            Assert.fail("No debería mandar excepcion");
        }
        horarioLogic.deleteHorario(horario.getId());
        paseoLogic.deletePaseo(paseo.getId());
        try
        {
            horarioLogic.removePaseo2(horarioData.get(0).getId());
            Assert.fail("Debería mandar excepcion ya que el Paseo no existe");
        }
        catch(BusinessLogicException ble){}
        try
        {
            HorarioEntity horarioNoExistente = factory.manufacturePojo(HorarioEntity.class);
            horarioLogic.removePaseo2(horarioNoExistente.getId());
            Assert.fail("Debería mandar excepcion ya que el horario no existe");
        }
        catch(BusinessLogicException ble){}
    }
  
    
    @Test
    public void removePaseo1Test()
    {
        HorarioEntity horario = horarioData.get(0);
        PaseoEntity paseo = factory.manufacturePojo(PaseoEntity.class);
        boolean creadoCorrectamente = false;
        while(!creadoCorrectamente)
        {
            try
            {
                paseo = factory.manufacturePojo(PaseoEntity.class);
                paseo.setCosto(10000.0);
                paseo.setCupoMaximo(10);
                paseo = paseoLogic.createPaseo(paseo);
                creadoCorrectamente = true;
            }
            catch(BusinessLogicException ble) {}
        }

        try
        {
            horarioLogic.addPaseo1(horario.getId(), paseo.getId());
            horarioLogic.removePaseo1(horario.getId());
            Assert.assertNull(horarioLogic.getPaseo1(horario.getId()));
        }
        catch(BusinessLogicException ble)
        {
            Assert.fail("No debería mandar excepcion");
        }
    }
    
    @Test
    public void removePaseo2Test()
    {
        HorarioEntity horario = horarioData.get(0);
        PaseoEntity paseo = factory.manufacturePojo(PaseoEntity.class);
        boolean creadoCorrectamente = false;
        while(!creadoCorrectamente)
        {
            try
            {
                paseo = factory.manufacturePojo(PaseoEntity.class);
                paseo.setCosto(10000.0);
                paseo.setCupoMaximo(10);
                paseo = paseoLogic.createPaseo(paseo);
                creadoCorrectamente = true;
            }
            catch(BusinessLogicException ble) {}
        }

        try
        {
            horarioLogic.addPaseo2(horario.getId(), paseo.getId());
            horarioLogic.removePaseo2(horario.getId());
            Assert.assertNull(horarioLogic.getPaseo2(horario.getId()));
        }
        catch(BusinessLogicException ble)
        {
            Assert.fail("No debería mandar excepcion");
        }
    }

}
