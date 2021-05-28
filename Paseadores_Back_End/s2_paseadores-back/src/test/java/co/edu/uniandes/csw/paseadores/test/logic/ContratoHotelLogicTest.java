/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.logic;

import co.edu.uniandes.csw.paseadores.ejb.ContratoHotelLogic;
import co.edu.uniandes.csw.paseadores.ejb.PagoClienteLogic;
import co.edu.uniandes.csw.paseadores.entities.ContratoHotelEntity;
import co.edu.uniandes.csw.paseadores.entities.PagoClienteEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.ContratoHotelPersistence;
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
 * @author Kevin Camilo Becerra Walteros
 */
@RunWith(Arquillian.class)
public class ContratoHotelLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ContratoHotelLogic contratoHotelLogic;

    @Inject
    private PagoClienteLogic pagoClienteLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ContratoHotelEntity> datosContratoHotel = new ArrayList<>();

    private List<PagoClienteEntity> pagoClienteData = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PagoClienteEntity.class.getPackage())
                .addPackage(ContratoHotelEntity.class.getPackage())
                .addPackage(ContratoHotelLogic.class.getPackage())
                .addPackage(ContratoHotelPersistence.class.getPackage())
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
        em.createQuery("delete from ContratoHotelEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ContratoHotelEntity nuevoContratoHotel = factory.manufacturePojo(ContratoHotelEntity.class);
            PagoClienteEntity pagoCliente = factory.manufacturePojo(PagoClienteEntity.class);
            em.persist(pagoCliente);
            nuevoContratoHotel.setPago(pagoCliente);

            pagoCliente.setContratoHotel(nuevoContratoHotel);
            em.persist(nuevoContratoHotel);
            datosContratoHotel.add(nuevoContratoHotel);
            pagoClienteData.add(pagoCliente);
        }
    }

    @Test
    public void createContratoHotelTest() throws BusinessLogicException {
        ContratoHotelEntity nuevoContratoHotel = factory.manufacturePojo(ContratoHotelEntity.class);
        ContratoHotelEntity contratoPaseoCreado = contratoHotelLogic.createContratoHotel(nuevoContratoHotel);
        Assert.assertNotNull(contratoPaseoCreado);
        ContratoHotelEntity entity = em.find(ContratoHotelEntity.class, contratoPaseoCreado.getId());
        Assert.assertEquals(nuevoContratoHotel.getId(), entity.getId());
        Assert.assertEquals(nuevoContratoHotel.getCliente(), entity.getCliente());
        Assert.assertEquals(nuevoContratoHotel.getCosto(), entity.getCosto());
        Assert.assertEquals(nuevoContratoHotel.getCuidadoEspecial(), entity.getCuidadoEspecial());
        Assert.assertEquals(nuevoContratoHotel.getHorasHotel(), entity.getHorasHotel());

    }

    @Test
    public void getContratoHotelTest() {
        ContratoHotelEntity contratoPaseo = datosContratoHotel.get(0);
        ContratoHotelEntity resultadoContratoHotel = contratoHotelLogic.getContratoHotel(contratoPaseo.getId());
        Assert.assertNotNull(resultadoContratoHotel);
        Assert.assertEquals(resultadoContratoHotel.getId(), contratoPaseo.getId());
        Assert.assertEquals(resultadoContratoHotel.getCliente(), contratoPaseo.getCliente());
        Assert.assertEquals(resultadoContratoHotel.getCosto(), contratoPaseo.getCosto());
        Assert.assertEquals(resultadoContratoHotel.getCuidadoEspecial(), contratoPaseo.getCuidadoEspecial());
        Assert.assertEquals(resultadoContratoHotel.getHorasHotel(), contratoPaseo.getHorasHotel());

        ContratoHotelEntity contratoPaseoNoExistente = factory.manufacturePojo(ContratoHotelEntity.class);
        Assert.assertNull(contratoHotelLogic.getContratoHotel(contratoPaseoNoExistente.getId()));
    }

    @Test
    public void updateContratoHotelTest() throws BusinessLogicException {
        ContratoHotelEntity contratoPaseo = datosContratoHotel.get(0);
        ContratoHotelEntity pojoContratoHotel = factory.manufacturePojo(ContratoHotelEntity.class);

        pojoContratoHotel.setId(contratoPaseo.getId());
        contratoHotelLogic.updateContratoHotel(pojoContratoHotel);
        ContratoHotelEntity respuestaContratoHotel = em.find(ContratoHotelEntity.class, contratoPaseo.getId());

        Assert.assertEquals(pojoContratoHotel.getId(), respuestaContratoHotel.getId());
        Assert.assertEquals(pojoContratoHotel.getCliente(), respuestaContratoHotel.getCliente());
        Assert.assertEquals(pojoContratoHotel.getCosto(), respuestaContratoHotel.getCosto());
        Assert.assertEquals(pojoContratoHotel.getCuidadoEspecial(), respuestaContratoHotel.getCuidadoEspecial());
        Assert.assertEquals(pojoContratoHotel.getHorasHotel(), respuestaContratoHotel.getHorasHotel());

    }

    @Test
    public void deleteContratoHotelTest() {
        ContratoHotelEntity contratoPaseo = datosContratoHotel.get(2);
        contratoHotelLogic.deleteContratoHotel(contratoPaseo.getId());
        Assert.assertNull(contratoHotelLogic.getContratoHotel(contratoPaseo.getId()));
    }

    @Test
    public void addPagoTest() throws BusinessLogicException {
        ContratoHotelEntity contratoPaseo = datosContratoHotel.get(0);
        PagoClienteEntity pagoCliente = factory.manufacturePojo(PagoClienteEntity.class);
        boolean creadoCorrectamente = false;
        while (!creadoCorrectamente) {
            try {
                pagoCliente = factory.manufacturePojo(PagoClienteEntity.class);
                pagoCliente.setMonto(10000.0);
                pagoCliente.setContratoHotel(null);
                pagoCliente = pagoClienteLogic.createPagoCliente(pagoCliente);
                creadoCorrectamente = true;
            } catch (BusinessLogicException ble) {
            }
        }
        try {
            contratoHotelLogic.addPago(contratoPaseo.getId(), pagoCliente.getId());
        } catch (BusinessLogicException ble) {
            Assert.fail("No debería mandar excepcion");
        }
        contratoHotelLogic.removePago(contratoPaseo.getId());
        pagoClienteLogic.deletePagoCliente(pagoCliente.getId());
        try {
            contratoHotelLogic.addPago(contratoPaseo.getId(), pagoCliente.getId());
            Assert.fail("Debería mandar excepcion ya que el PagoCliente no existe");
        } catch (BusinessLogicException ble) {
        }
    }

    @Test
    public void getPagoTest() throws BusinessLogicException {
        ContratoHotelEntity contratoPaseo = datosContratoHotel.get(0);
        PagoClienteEntity pagoCliente = factory.manufacturePojo(PagoClienteEntity.class);
        boolean creadoCorrectamente = false;
        while (!creadoCorrectamente) {
            try {
                pagoCliente = factory.manufacturePojo(PagoClienteEntity.class);
                pagoCliente.setMonto(10000.0);
                pagoCliente.setContratoHotel(null);
                pagoCliente = pagoClienteLogic.createPagoCliente(pagoCliente);
                creadoCorrectamente = true;
            } catch (BusinessLogicException ble) {
            }
        }

        try {
            contratoHotelLogic.addPago(contratoPaseo.getId(), pagoCliente.getId());
            Assert.assertEquals(pagoCliente.getId(), contratoHotelLogic.getPago(contratoPaseo.getId()).getId());
        } catch (BusinessLogicException ble) {
            Assert.fail("No debería mandar excepcion");
        }
        contratoHotelLogic.removePago(contratoPaseo.getId());
        Assert.assertNull(contratoHotelLogic.getPago(contratoPaseo.getId()));
    }

    @Test
    public void replacePagoTest() throws BusinessLogicException {
        ContratoHotelEntity contratoPaseo = datosContratoHotel.get(0);
        PagoClienteEntity pagoCliente = factory.manufacturePojo(PagoClienteEntity.class);
        boolean creadoCorrectamente = false;
        while (!creadoCorrectamente) {
            try {
                pagoCliente = factory.manufacturePojo(PagoClienteEntity.class);
                pagoCliente.setMonto(10000.0);
                pagoCliente.setContratoHotel(null);
                pagoCliente = pagoClienteLogic.createPagoCliente(pagoCliente);
                creadoCorrectamente = true;
            } catch (BusinessLogicException ble) {
            }
        }

        try {
            contratoHotelLogic.replacePago(contratoPaseo.getId(), pagoCliente.getId());
            Assert.assertEquals(pagoCliente.getId(), contratoHotelLogic.getContratoHotel(contratoPaseo.getId()).getPago().getId());
        } catch (BusinessLogicException ble) {
            Assert.fail("No debería mandar excepcion");
        }
        contratoHotelLogic.removePago(contratoPaseo.getId());
        pagoClienteLogic.deletePagoCliente(pagoCliente.getId());
        try {
            contratoHotelLogic.replacePago(datosContratoHotel.get(1).getId(), pagoCliente.getId());
            Assert.fail("Debería mandar excepcion ya que el PagoCliente no existe");
        } catch (BusinessLogicException ble) {
        }
        try {
            ContratoHotelEntity contratoPaseoNoExistente = factory.manufacturePojo(ContratoHotelEntity.class);
            contratoHotelLogic.replacePago(contratoPaseoNoExistente.getId(), pagoCliente.getId());
            Assert.fail("Debería mandar excepcion ya que el ContratoHotel no existe");
        } catch (BusinessLogicException ble) {
        }
    }

    @Test
    public void removePagoTest() {
        ContratoHotelEntity contratoPaseo = datosContratoHotel.get(0);
        PagoClienteEntity pagoCliente = factory.manufacturePojo(PagoClienteEntity.class);
        boolean creadoCorrectamente = false;
        while (!creadoCorrectamente) {
            try {
                pagoCliente = factory.manufacturePojo(PagoClienteEntity.class);
                pagoCliente.setMonto(10000.0);
                pagoCliente.setContratoHotel(null);
                pagoCliente = pagoClienteLogic.createPagoCliente(pagoCliente);
                creadoCorrectamente = true;
            } catch (BusinessLogicException ble) {
            }
        }

        try {
            contratoHotelLogic.addPago(contratoPaseo.getId(), pagoCliente.getId());
            contratoHotelLogic.removePago(contratoPaseo.getId());
            Assert.assertNull(contratoHotelLogic.getPago(contratoPaseo.getId()));
        } catch (BusinessLogicException ble) {
            Assert.fail("No debería mandar excepcion");
        }
    }

}
