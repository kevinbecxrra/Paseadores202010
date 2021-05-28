/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.logic;

import co.edu.uniandes.csw.paseadores.ejb.ContratoPaseoLogic;
import co.edu.uniandes.csw.paseadores.ejb.PagoClienteLogic;
import co.edu.uniandes.csw.paseadores.entities.ContratoPaseoEntity;
import co.edu.uniandes.csw.paseadores.entities.PagoClienteEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.ContratoPaseoPersistence;
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
public class ContratoPaseoLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ContratoPaseoLogic contratoPaseoLogic;

    @Inject
    private PagoClienteLogic pagoClienteLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ContratoPaseoEntity> contratoPaseoData = new ArrayList<>();

    private List<PagoClienteEntity> pagoClienteData = new ArrayList<PagoClienteEntity>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PagoClienteEntity.class.getPackage())
                .addPackage(ContratoPaseoEntity.class.getPackage())
                .addPackage(ContratoPaseoLogic.class.getPackage())
                .addPackage(ContratoPaseoPersistence.class.getPackage())
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
        em.createQuery("delete from ContratoPaseoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ContratoPaseoEntity contratoPaseo = factory.manufacturePojo(ContratoPaseoEntity.class);
            PagoClienteEntity pagoCliente = factory.manufacturePojo(PagoClienteEntity.class);
            em.persist(pagoCliente);
            contratoPaseo.setPago(pagoCliente);
            pagoCliente.setContratoPaseo(contratoPaseo);
            em.persist(contratoPaseo);
            contratoPaseoData.add(contratoPaseo);
            pagoClienteData.add(pagoCliente);
        }
    }
    
    @Test
    public void createContratoPaseoTest()
    {
        ContratoPaseoEntity nuevoContratoPaseo = factory.manufacturePojo(ContratoPaseoEntity.class);
        ContratoPaseoEntity contratoPaseoCreado = contratoPaseoLogic.createContratoPaseo(nuevoContratoPaseo);
        Assert.assertNotNull(contratoPaseoCreado);
        ContratoPaseoEntity entity = em.find(ContratoPaseoEntity.class, contratoPaseoCreado.getId());
        Assert.assertEquals(nuevoContratoPaseo.getId(), entity.getId());
        Assert.assertEquals(nuevoContratoPaseo.getHoraEncuentro(), entity.getHoraEncuentro());
    }
    
    @Test
    public void getContratoPaseoTest()
    {
        ContratoPaseoEntity contratoPaseo = contratoPaseoData.get(0);
        ContratoPaseoEntity resultadoContratoPaseo = contratoPaseoLogic.getContratoPaseo(contratoPaseo.getId());
        Assert.assertNotNull(resultadoContratoPaseo);
        Assert.assertEquals(resultadoContratoPaseo.getId(), contratoPaseo.getId());
        Assert.assertEquals(resultadoContratoPaseo.getHoraEncuentro(), contratoPaseo.getHoraEncuentro());

        ContratoPaseoEntity contratoPaseoNoExistente = factory.manufacturePojo(ContratoPaseoEntity.class);
        Assert.assertNull(contratoPaseoLogic.getContratoPaseo(contratoPaseoNoExistente.getId()));
    }
    
    @Test
    public void updateContratoPaseoTest()
    {
        ContratoPaseoEntity contratoPaseo = contratoPaseoData.get(0);
        ContratoPaseoEntity pojoContratoPaseo = factory.manufacturePojo(ContratoPaseoEntity.class);

        pojoContratoPaseo.setId(contratoPaseo.getId());
        contratoPaseoLogic.updateContratoPaseo(pojoContratoPaseo);
        ContratoPaseoEntity respuestaContratoPaseo = em.find(ContratoPaseoEntity.class, contratoPaseo.getId());

        Assert.assertEquals(pojoContratoPaseo.getId(), respuestaContratoPaseo.getId());
        Assert.assertEquals(pojoContratoPaseo.getHoraEncuentro(), respuestaContratoPaseo.getHoraEncuentro());
    }
    
    @Test 
    public void deleteContratoPaseoTest()
    {
        ContratoPaseoEntity contratoPaseo = contratoPaseoData.get(2);
        contratoPaseoLogic.deleteContratoPaseo(contratoPaseo.getId());
        Assert.assertNull(contratoPaseoLogic.getContratoPaseo(contratoPaseo.getId()));
    }
    
    @Test
    public void addPagoTest()
    {
        ContratoPaseoEntity contratoPaseo = contratoPaseoData.get(0);
        PagoClienteEntity pagoCliente = factory.manufacturePojo(PagoClienteEntity.class);
        boolean creadoCorrectamente = false;
        while(!creadoCorrectamente)
        {
            try
            {
                pagoCliente = factory.manufacturePojo(PagoClienteEntity.class);
                pagoCliente.setMonto(10000.0);
                pagoCliente.setContratoHotel(null);
                pagoCliente = pagoClienteLogic.createPagoCliente(pagoCliente);
                creadoCorrectamente = true;
            }
            catch(BusinessLogicException ble) {}
        }
        try
        {
            contratoPaseoLogic.addPago(contratoPaseo.getId(), pagoCliente.getId());
        }
        catch(BusinessLogicException ble)
        {
            Assert.fail("No debería mandar excepcion");
        }
        contratoPaseoLogic.removePago(contratoPaseo.getId());
        pagoClienteLogic.deletePagoCliente(pagoCliente.getId());
        try
        {
            contratoPaseoLogic.addPago(contratoPaseo.getId(), pagoCliente.getId());
            Assert.fail("Debería mandar excepcion ya que el PagoCliente no existe");
        }
        catch(BusinessLogicException ble){}
    }
    
    @Test
    public void getPagoTest()
    {
        ContratoPaseoEntity contratoPaseo = contratoPaseoData.get(0);
        PagoClienteEntity pagoCliente = factory.manufacturePojo(PagoClienteEntity.class);
        boolean creadoCorrectamente = false;
        while(!creadoCorrectamente)
        {
            try
            {
                pagoCliente = factory.manufacturePojo(PagoClienteEntity.class);
                pagoCliente.setMonto(10000.0);
                pagoCliente.setContratoHotel(null);
                pagoCliente = pagoClienteLogic.createPagoCliente(pagoCliente);
                creadoCorrectamente = true;
            }
            catch(BusinessLogicException ble) {}
        }
        
        try
        {
            contratoPaseoLogic.addPago(contratoPaseo.getId(), pagoCliente.getId());
            Assert.assertEquals(pagoCliente.getId(), contratoPaseoLogic.getPago(contratoPaseo.getId()).getId());
        }
        catch(BusinessLogicException ble)
        {
            Assert.fail("No debería mandar excepcion");
        }
        contratoPaseoLogic.removePago(contratoPaseo.getId());
        Assert.assertNull(contratoPaseoLogic.getPago(contratoPaseo.getId()));
    }
    
    @Test
    public void replacePagoTest()
    {
        ContratoPaseoEntity contratoPaseo = contratoPaseoData.get(0);
        PagoClienteEntity pagoCliente = factory.manufacturePojo(PagoClienteEntity.class);
        boolean creadoCorrectamente = false;
        while(!creadoCorrectamente)
        {
            try
            {
                pagoCliente = factory.manufacturePojo(PagoClienteEntity.class);
                pagoCliente.setMonto(10000.0);
                pagoCliente.setContratoHotel(null);
                pagoCliente = pagoClienteLogic.createPagoCliente(pagoCliente);
                creadoCorrectamente = true;
            }
            catch(BusinessLogicException ble) {}
        }
        
        try
        {
            contratoPaseoLogic.replacePago(contratoPaseo.getId(), pagoCliente.getId());
            Assert.assertEquals(pagoCliente.getId(), contratoPaseoLogic.getContratoPaseo(contratoPaseo.getId()).getPago().getId());
        }
        catch(BusinessLogicException ble)
        {
            Assert.fail("No debería mandar excepcion");
        }
        contratoPaseoLogic.removePago(contratoPaseo.getId());
        pagoClienteLogic.deletePagoCliente(pagoCliente.getId());
        try
        {
            contratoPaseoLogic.replacePago(contratoPaseoData.get(1).getId(), pagoCliente.getId());
            Assert.fail("Debería mandar excepcion ya que el PagoCliente no existe");
        }
        catch(BusinessLogicException ble){}
        try
        {
            ContratoPaseoEntity contratoPaseoNoExistente = factory.manufacturePojo(ContratoPaseoEntity.class);
            contratoPaseoLogic.replacePago(contratoPaseoNoExistente.getId(), pagoCliente.getId());
            Assert.fail("Debería mandar excepcion ya que el ContratoPaseo no existe");
        }
        catch(BusinessLogicException ble){}
    }
    
    @Test
    public void removePagoTest()
    {
        ContratoPaseoEntity contratoPaseo = contratoPaseoData.get(0);
        PagoClienteEntity pagoCliente = factory.manufacturePojo(PagoClienteEntity.class);
        boolean creadoCorrectamente = false;
        while(!creadoCorrectamente)
        {
            try
            {
                pagoCliente = factory.manufacturePojo(PagoClienteEntity.class);
                pagoCliente.setMonto(10000.0);
                pagoCliente.setContratoHotel(null);
                pagoCliente = pagoClienteLogic.createPagoCliente(pagoCliente);
                creadoCorrectamente = true;
            }
            catch(BusinessLogicException ble) {}
        }
        
        try
        {
            contratoPaseoLogic.addPago(contratoPaseo.getId(), pagoCliente.getId());
            contratoPaseoLogic.removePago(contratoPaseo.getId());
            Assert.assertNull(contratoPaseoLogic.getPago(contratoPaseo.getId()));
        }
        catch(BusinessLogicException ble)
        {
            Assert.fail("No debería mandar excepcion");
        }
    }
}
