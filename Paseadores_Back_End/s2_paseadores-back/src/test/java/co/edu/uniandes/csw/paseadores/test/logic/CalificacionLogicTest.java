/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.logic;

import co.edu.uniandes.csw.paseadores.ejb.CalificacionLogic;
import co.edu.uniandes.csw.paseadores.ejb.ContratoPaseoLogic;
import co.edu.uniandes.csw.paseadores.ejb.ContratoHotelLogic;
import co.edu.uniandes.csw.paseadores.entities.ContratoHotelEntity;
import co.edu.uniandes.csw.paseadores.entities.ContratoPaseoEntity;
import co.edu.uniandes.csw.paseadores.entities.CalificacionEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.CalificacionPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.junit.Assert;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
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
public class CalificacionLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private CalificacionLogic calificacionLogic;

    @Inject
    private ContratoPaseoLogic contratoPaseoLogic;

    @Inject
    private ContratoHotelLogic contratoHotelLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ContratoPaseoEntity> contratoPaseoData = new ArrayList<>();

    private List<CalificacionEntity> data = new ArrayList<CalificacionEntity>();

    private List<ContratoHotelEntity> contratoHotelData = new ArrayList<>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(CalificacionEntity.class.getPackage())
                .addPackage(ContratoPaseoEntity.class.getPackage())
                .addPackage(ContratoHotelEntity.class.getPackage())
                .addPackage(CalificacionLogic.class.getPackage())
                .addPackage(CalificacionPersistence.class.getPackage())
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
        em.createQuery("delete from CalificacionEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
       for (int i = 0; i < 3; i++) {
            ContratoHotelEntity editorial = factory.manufacturePojo(ContratoHotelEntity.class);
            em.persist(editorial);
            contratoHotelData.add(editorial);
        }
        
        for (int i = 0; i < 3; i++) {
            ContratoPaseoEntity paseo = factory.manufacturePojo(ContratoPaseoEntity.class);
            em.persist(paseo);
            contratoPaseoData.add(paseo);
        }
        
        for (int i = 0; i < 3; i++) {
            CalificacionEntity paseo = factory.manufacturePojo(CalificacionEntity.class);
            em.persist(paseo);
            data.add(paseo);
        }
        
        
    }

    /**
     * Prueba para crear un Calificacion.
     *
     */
    @Test
    public void createCalificacionTest() {
        CalificacionEntity newEntity = factory.manufacturePojo(CalificacionEntity.class);
        newEntity.setValoracion(4);
        CalificacionEntity result = null;
        try {
            result = calificacionLogic.createCalificacion(newEntity);
        } catch (BusinessLogicException b) {
            Assert.fail("No debería lanzar excepción");
        }
        Assert.assertNotNull(result);
        CalificacionEntity entity = em.find(CalificacionEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getValoracion(), entity.getValoracion());
    }

    /**
     * Prueba para crear un Calificacion.
     *
     */
    @Test
    public void createCalificacionConValoresInvalidosTest() {
        CalificacionEntity noEntity = factory.manufacturePojo(CalificacionEntity.class);
        noEntity.setValoracion(-3);
        try {
            calificacionLogic.createCalificacion(noEntity);
            Assert.fail("Debería lanzar excepción");
        } catch (BusinessLogicException b) {
        }
        noEntity.setValoracion(100);
        try {
           calificacionLogic.createCalificacion(noEntity);
            Assert.fail("Debería lanzar excepción");
        } catch (BusinessLogicException b) {
        }
    }

    /**
     * Prueba para consultar un Calificacion.
     */
    @Test
    public void getCalificacionTest() {
        CalificacionEntity entity = data.get(0);
        CalificacionEntity resultEntity = calificacionLogic.getCalificacion(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(resultEntity.getId(), entity.getId());
        Assert.assertEquals(resultEntity.getValoracion(), entity.getValoracion());
        Assert.assertEquals(resultEntity.getComentario(), entity.getComentario());
    }

    /**
     * Prueba para actualizar un Calificacion.
     */
    @Test
    public void updateCalificacionTest() {
        CalificacionEntity entity = data.get(0);
        CalificacionEntity pojoEntity = factory.manufacturePojo(CalificacionEntity.class);
        pojoEntity.setId(entity.getId());
        pojoEntity.setValoracion(3);
        try {
            calificacionLogic.updateCalificacion(pojoEntity.getId(), pojoEntity);
        } catch (BusinessLogicException ex) {
            Assert.fail("No debería lanzar excepción");
        }
        CalificacionEntity resp = em.find(CalificacionEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getValoracion(), resp.getValoracion());
        Assert.assertEquals(pojoEntity.getComentario(), resp.getComentario());

    }

    /**
     * Prueba para update un Calificacion.
     *
     */
    @Test
    public void updateCalificacionConValoresInvalidosTest() {
        CalificacionEntity entity = data.get(0);
        CalificacionEntity nonEntity = factory.manufacturePojo(CalificacionEntity.class);
        nonEntity.setId(entity.getId());
        nonEntity.setValoracion(-125);
        try {
           calificacionLogic.updateCalificacion(nonEntity.getId(), nonEntity);
            Assert.fail("Debería lanzar excepción");
        } catch (BusinessLogicException b) {
        }
        nonEntity.setValoracion(560);
        try {
            calificacionLogic.updateCalificacion(nonEntity.getId(), nonEntity);
            Assert.fail("Debería lanzar excepción");
        } catch (BusinessLogicException b) {
        }
    }
    

    /**
     * Prueba para add un ContratoPaseo
     */
    @Test
    public void addContratoPaseadorTest() throws BusinessLogicException {
        CalificacionEntity entity = data.get(0);
        ContratoPaseoEntity contratoPaseoEntity = factory.manufacturePojo(ContratoPaseoEntity.class);
        contratoPaseoEntity = contratoPaseoLogic.createContratoPaseo(contratoPaseoEntity);
        try {
            CalificacionEntity punto = calificacionLogic.addContratoPaseador(entity.getId(), contratoPaseoEntity.getId());
            Assert.assertEquals(contratoPaseoEntity.getId(), punto.getContratoPaseador().getId());
            calificacionLogic.removeContratoPaseador(entity.getId());
        } catch (BusinessLogicException ble) {
            Assert.fail("No debería mandar excepcion");
        }
        contratoPaseoLogic.deleteContratoPaseo(contratoPaseoEntity.getId());
        try {
            calificacionLogic.addContratoPaseador(data.get(1).getId(), contratoPaseoEntity.getId());
            Assert.fail("Debería mandar excepcion ya que el ContratoPaseo no existe");
        } catch (BusinessLogicException ble) {
        }

    }

    /**
     * Prueba para get un ContratoPaseo
     */
    @Test
    public void getContratoPaseadorTest() {
        CalificacionEntity toGetEntity = data.get(0);
        ContratoPaseoEntity getCPE = factory.manufacturePojo(ContratoPaseoEntity.class);
        getCPE = contratoPaseoLogic.createContratoPaseo(getCPE);
        try {
            calificacionLogic.addContratoPaseador(toGetEntity.getId(), getCPE.getId());
            Assert.assertEquals(getCPE.getId(), calificacionLogic.getContratoPaseador(toGetEntity.getId()).getId());
        } catch (BusinessLogicException ble) {
            Assert.fail("No debería mandar excepcion");
        }
        try {
            calificacionLogic.removeContratoPaseador(toGetEntity.getId());
            Assert.assertNull(calificacionLogic.getContratoPaseador(toGetEntity.getId()));
        } catch (BusinessLogicException ble) {
        }
    }

    /**
     * Prueba para update un ContratoPaseo
     */
    @Test
    public void ContratoPaseadorTest() {
        CalificacionEntity toUpdateEntity = data.get(0);
        ContratoPaseoEntity updateCPE = factory.manufacturePojo(ContratoPaseoEntity.class);
        updateCPE = contratoPaseoLogic.createContratoPaseo(updateCPE);
        try {
            CalificacionEntity punto = calificacionLogic.replaceContratoPaseador(toUpdateEntity.getId(), updateCPE.getId());
            Assert.assertEquals(updateCPE.getId(), punto.getContratoPaseador().getId());
            calificacionLogic.removeContratoPaseador(toUpdateEntity.getId());
        } catch (BusinessLogicException ble) {
            Assert.fail("No debería mandar excepcion");
        }
        contratoPaseoLogic.deleteContratoPaseo(updateCPE.getId());
        try {
            calificacionLogic.replaceContratoPaseador(data.get(1).getId(), updateCPE.getId());
            Assert.fail("Debería mandar excepcion ya que el ContratoPaseo no existe");
        } catch (BusinessLogicException ble) {
        }
        try {
            CalificacionEntity nonEntity = factory.manufacturePojo(CalificacionEntity.class);
            calificacionLogic.replaceContratoPaseador(nonEntity.getId(), updateCPE.getId());
            Assert.fail("Debería mandar excepcion ya que el punto no existe");
        } catch (BusinessLogicException ble) {
        }
    }

    /**
     * Prueba para remove un ContratoPaseo
     */
    @Test
    public void removeContratoPaseadorTest() {
        CalificacionEntity toDoEntity = data.get(0);
        ContratoPaseoEntity deleteCPE = factory.manufacturePojo(ContratoPaseoEntity.class);
        deleteCPE = contratoPaseoLogic.createContratoPaseo(deleteCPE);
        try {
            calificacionLogic.addContratoPaseador(toDoEntity.getId(), deleteCPE.getId());
            calificacionLogic.removeContratoPaseador(toDoEntity.getId());
            Assert.assertNull(calificacionLogic.getContratoPaseador(toDoEntity.getId()));
        } catch (BusinessLogicException ble) {
            Assert.fail("No debería mandar excepcion");
        }

    }

    /**
     * Prueba para eliminar un Calificacion.
     *
     */
    @Test
    public void deleteCalificacionTest() {
        CalificacionEntity entity = data.get(2);
        long id = entity.getId();
        calificacionLogic.deleteCalificacion(entity.getId());
        Assert.assertNull(calificacionLogic.getCalificacion(id));
    }
    
    
        /**
     * Prueba para add un ContratoHotel
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    @Test
    public void addContratoHotelTest() throws BusinessLogicException {
        CalificacionEntity entity = data.get(0);
        ContratoHotelEntity contratoHotelEntity = factory.manufacturePojo(ContratoHotelEntity.class);
        contratoHotelEntity = contratoHotelLogic.createContratoHotel(contratoHotelEntity);
        try {
            CalificacionEntity calificacion = calificacionLogic.addContratoHotel(entity.getId(), contratoHotelEntity.getId());
            Assert.assertEquals(contratoHotelEntity.getId(), calificacion.getContratoHotel().getId());
            calificacionLogic.removeContratoHotel(entity.getId());
        } catch (BusinessLogicException ble) {
            Assert.fail("No debería mandar excepcion");
        }
        contratoHotelLogic.deleteContratoHotel(contratoHotelEntity.getId());
        try {
            calificacionLogic.addContratoHotel(data.get(1).getId(), contratoHotelEntity.getId());
            Assert.fail("Debería mandar excepcion ya que el ContratoPaseo no existe");
        } catch (BusinessLogicException ble) {
        }
    }

    /**
     * Prueba para get un ContratoHotel
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    @Test
    public void getContratoHotelTest() throws BusinessLogicException {
        CalificacionEntity toGetEntity = data.get(0);
        ContratoHotelEntity getCPE = factory.manufacturePojo(ContratoHotelEntity.class);
        getCPE = contratoHotelLogic.createContratoHotel(getCPE);
        try {
            calificacionLogic.addContratoHotel(toGetEntity.getId(), getCPE.getId());
            Assert.assertEquals(getCPE.getId(), calificacionLogic.getContratoHotel(toGetEntity.getId()).getId());
        } catch (BusinessLogicException ble) {
            Assert.fail("No debería mandar excepcion");
        }
        try {
            calificacionLogic.removeContratoHotel(toGetEntity.getId());
            Assert.assertNull(calificacionLogic.getContratoHotel(toGetEntity.getId()));
        } catch (BusinessLogicException ble) {
        }
    }

    /**
     * Prueba para update un ContratoHotel
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    @Test
    public void replaceContratoHotelTest() throws BusinessLogicException {
        CalificacionEntity toUpdateEntity = data.get(0);
        ContratoHotelEntity updateCPE = factory.manufacturePojo(ContratoHotelEntity.class);
        updateCPE = contratoHotelLogic.createContratoHotel(updateCPE);
        try {
            CalificacionEntity calificacion = calificacionLogic.replaceContratoHotel(toUpdateEntity.getId(), updateCPE.getId());
            Assert.assertEquals(updateCPE.getId(), calificacion.getContratoHotel().getId());
            calificacionLogic.removeContratoHotel(toUpdateEntity.getId());
        } catch (BusinessLogicException ble) {
            Assert.fail("No debería mandar excepcion");
        }
        contratoHotelLogic.deleteContratoHotel(updateCPE.getId());
        try {
            calificacionLogic.replaceContratoHotel(data.get(1).getId(), updateCPE.getId());
            Assert.fail("Debería mandar excepcion ya que el ContratoHotel no existe");
        } catch (BusinessLogicException ble) {
        }
        try {
            CalificacionEntity nonEntity = factory.manufacturePojo(CalificacionEntity.class);
            calificacionLogic.replaceContratoHotel(nonEntity.getId(), updateCPE.getId());
            Assert.fail("Debería mandar excepcion ya que la calificacion no existe");
        } catch (BusinessLogicException ble) {
        }
    }

    /**
     * Prueba para remove un ContratoHotel
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    @Test
    public void removeContratoHotelTest() throws BusinessLogicException {
        CalificacionEntity toDoEntity = data.get(0);
        ContratoHotelEntity deleteCPE = factory.manufacturePojo(ContratoHotelEntity.class);
        deleteCPE = contratoHotelLogic.createContratoHotel(deleteCPE);
        try {
            calificacionLogic.addContratoHotel(toDoEntity.getId(), deleteCPE.getId());
            calificacionLogic.removeContratoHotel(toDoEntity.getId());
            Assert.assertNull(calificacionLogic.getContratoHotel(toDoEntity.getId()));
        } catch (BusinessLogicException ble) {
            Assert.fail("No debería mandar excepcion");
        }

    }
    
    
        /**
     * Prueba para add un ContratoRecorrido
     */
    @Test
    public void addContratoRecorridoTest() throws BusinessLogicException {
        CalificacionEntity entity = data.get(0);
        ContratoPaseoEntity contratoPaseoEntity = factory.manufacturePojo(ContratoPaseoEntity.class);
        contratoPaseoEntity = contratoPaseoLogic.createContratoPaseo(contratoPaseoEntity);
        try {
            CalificacionEntity calificacion = calificacionLogic.addContratoRecorrido(entity.getId(), contratoPaseoEntity.getId());
            Assert.assertEquals(contratoPaseoEntity.getId(), calificacion.getContratoRecorrido().getId());
            calificacionLogic.removeContratoRecorrido(entity.getId());
        } catch (BusinessLogicException ble) {
            Assert.fail("No debería mandar excepcion");
        }
        contratoPaseoLogic.deleteContratoPaseo(contratoPaseoEntity.getId());
        try {
            calificacionLogic.addContratoRecorrido(data.get(1).getId(), contratoPaseoEntity.getId());
            Assert.fail("Debería mandar excepcion ya que el ContratoRecorrido no existe");
        } catch (BusinessLogicException ble) {
        }
    }

    /**
     * Prueba para get un ContratoRecorrido
     */
    @Test
    public void getContratoRecorridoTest() {
        CalificacionEntity toGetEntity = data.get(0);
        ContratoPaseoEntity getCPE = factory.manufacturePojo(ContratoPaseoEntity.class);
        getCPE = contratoPaseoLogic.createContratoPaseo(getCPE);
        try {
            calificacionLogic.addContratoRecorrido(toGetEntity.getId(), getCPE.getId());
            Assert.assertEquals(getCPE.getId(), calificacionLogic.getContratoRecorrido(toGetEntity.getId()).getId());
        } catch (BusinessLogicException ble) {
            Assert.fail("No debería mandar excepcion");
        }
        try {
            calificacionLogic.removeContratoRecorrido(toGetEntity.getId());
            Assert.assertNull(calificacionLogic.getContratoRecorrido(toGetEntity.getId()));
        } catch (BusinessLogicException ble) {
        }

    }

    /**
     * Prueba para update un ContratoRecorrido
     */
    @Test
    public void replaceContratoRecorridoTest() {
        CalificacionEntity toUpdateEntity = data.get(0);
        ContratoPaseoEntity updateCPE = factory.manufacturePojo(ContratoPaseoEntity.class);
        updateCPE = contratoPaseoLogic.createContratoPaseo(updateCPE);
        try {
            CalificacionEntity calificacion = calificacionLogic.replaceContratoRecorrido(toUpdateEntity.getId(), updateCPE.getId());
            Assert.assertEquals(updateCPE.getId(), calificacion.getContratoRecorrido().getId());
            calificacionLogic.removeContratoRecorrido(toUpdateEntity.getId());
        } catch (BusinessLogicException ble) {
            Assert.fail("No debería mandar excepcion");
        }
        contratoPaseoLogic.deleteContratoPaseo(updateCPE.getId());
        try {
            calificacionLogic.replaceContratoRecorrido(data.get(1).getId(), updateCPE.getId());
            Assert.fail("Debería mandar excepcion ya que el ContratoRecorrido no existe");
        } catch (BusinessLogicException ble) {
        }
        try {
            CalificacionEntity nonEntity = factory.manufacturePojo(CalificacionEntity.class);
            calificacionLogic.replaceContratoRecorrido(nonEntity.getId(), updateCPE.getId());
            Assert.fail("Debería mandar excepcion ya que el calificacion no existe");
        } catch (BusinessLogicException ble) {
        }
    }

    /**
     * Prueba para remove un ContratoRecorrido
     */
    @Test
    public void removeContratoRecorridoTest() {
        CalificacionEntity toDoEntity = data.get(0);
        ContratoPaseoEntity deleteCPE = factory.manufacturePojo(ContratoPaseoEntity.class);
        deleteCPE = contratoPaseoLogic.createContratoPaseo(deleteCPE);
        try {
            calificacionLogic.addContratoRecorrido(toDoEntity.getId(), deleteCPE.getId());
            calificacionLogic.removeContratoRecorrido(toDoEntity.getId());
            Assert.assertNull(calificacionLogic.getContratoRecorrido(toDoEntity.getId()));
        } catch (BusinessLogicException ble) {
            Assert.fail("No debería mandar excepcion");
        }

    }
    

}
