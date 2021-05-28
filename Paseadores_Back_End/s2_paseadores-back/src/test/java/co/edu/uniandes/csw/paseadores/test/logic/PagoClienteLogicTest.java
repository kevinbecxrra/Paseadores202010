/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.logic;

import co.edu.uniandes.csw.paseadores.ejb.PagoClienteLogic;
import co.edu.uniandes.csw.paseadores.entities.ClienteEntity;
import co.edu.uniandes.csw.paseadores.entities.ContratoHotelEntity;
import co.edu.uniandes.csw.paseadores.entities.ContratoPaseoEntity;
import co.edu.uniandes.csw.paseadores.entities.PagoClienteEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.PagoClientePersistence;
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
 * @author julian Oliveros<je.oliverosf at uniandes.edu.co>
 */
@RunWith(Arquillian.class)
public class PagoClienteLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
     
    @Inject
    private PagoClienteLogic pagoClienteLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;

    private List<PagoClienteEntity> data = new ArrayList<PagoClienteEntity>();
    
    private List<ContratoHotelEntity> ContratoHotelData = new ArrayList();
    private List<ContratoPaseoEntity> ContratoPaseoData = new ArrayList();
     private List<ClienteEntity> ClienteData = new ArrayList();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PagoClienteEntity.class.getPackage())
                .addPackage(PagoClienteLogic.class.getPackage())
                .addPackage(PagoClientePersistence.class.getPackage())
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
        em.createQuery("delete from PagoClienteEntity").executeUpdate();
    }
    
    
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        
        for (int i = 0; i < 3; i++) {
            ContratoHotelEntity editorial = factory.manufacturePojo(ContratoHotelEntity.class);
            em.persist(editorial);
            ContratoHotelData.add(editorial);
        }
        
        for (int i = 0; i < 3; i++) {
            ContratoPaseoEntity paseo = factory.manufacturePojo(ContratoPaseoEntity.class);
            em.persist(paseo);
            ContratoPaseoData.add(paseo);
        }
        
        for (int i = 0; i < 3; i++) {
            ClienteEntity paseo = factory.manufacturePojo(ClienteEntity.class);
            em.persist(paseo);
            ClienteData.add(paseo);
        }
        
        for (int i = 0; i < 3; i++) {
            PagoClienteEntity pago = factory.manufacturePojo(PagoClienteEntity.class);
            em.persist(pago);
            pago.setCliente(ClienteData.get(i));
            data.add(pago);
        }
        
    }

    
    /**
     * Prueba para crear un PagoCliente con todo bien
     *
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    @Test
    public void createPagoClienteTest() throws BusinessLogicException {
        PagoClienteEntity newEntity = factory.manufacturePojo(PagoClienteEntity.class);
        PagoClienteEntity result = pagoClienteLogic.createPagoCliente(newEntity);
        
        Assert.assertNotNull(result);
        PagoClienteEntity entity = em.find(PagoClienteEntity.class, result.getId());
        
        
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getMonto(), entity.getMonto());
        Assert.assertEquals(newEntity.getReferencia(), entity.getReferencia());
    }
    
     /**
     * Prueba para crear un pago con monto inválido(menor a 0)
     *
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createPagoClienteTestConMontoInvalido() throws BusinessLogicException {
        PagoClienteEntity newEntity = factory.manufacturePojo(PagoClienteEntity.class);
        //newEntity.setEditorial(editorialData.get(0));
        Double monto = -20.56;
        newEntity.setMonto(monto);
        pagoClienteLogic.createPagoCliente(newEntity);
    }
    
    /**
     * Prueba para crear un pago con referencia inválido(referencia que ya existe)
     *
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createPagoClienteTestConReferenciaExistente() throws BusinessLogicException {
        PagoClienteEntity newEntity = factory.manufacturePojo(PagoClienteEntity.class);
        newEntity.setReferencia(data.get(0).getReferencia());
        pagoClienteLogic.createPagoCliente(newEntity);
    }
    
    /**
     * Prueba para crear un pago que este relacionado con contratoHotel y despues con contratoPAseo.
     *
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createPagoClienteTestConDosContratosHotelExistente() throws BusinessLogicException {
        PagoClienteEntity newEntity = factory.manufacturePojo(PagoClienteEntity.class);
        
        newEntity.setContratoHotel(ContratoHotelData.get(0));
        newEntity.setContratoPaseo(ContratoPaseoData.get(0)); 

        pagoClienteLogic.createPagoCliente(newEntity);
    }
    
    /**
     * Prueba para crear un pago que este relacionado con  contratoPaseo y despues con contratoHotel.
     *
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createPagoClienteTestConDosContratosPaseoExistente() throws BusinessLogicException {
        PagoClienteEntity newEntity = factory.manufacturePojo(PagoClienteEntity.class);
        
        newEntity.setContratoPaseo(ContratoPaseoData.get(0)); 
        newEntity.setContratoHotel(ContratoHotelData.get(0));

        pagoClienteLogic.createPagoCliente(newEntity);
    }
    
    
    
    
    
    
    /**
     * Prueba para consultar un PagoClienteEntity.
     */
    @Test
    public void getPagoClienteByReferenciaTest() {
        PagoClienteEntity entity = data.get(0);
        PagoClienteEntity resultEntity = pagoClienteLogic.getPagoByReferencia(entity.getReferencia());
        Assert.assertNotNull(resultEntity);
        
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getMonto(), resultEntity.getMonto());
        Assert.assertEquals(entity.getReferencia(), resultEntity.getReferencia());
    }
    
    /**
     * Prueba para consultar un pagoCliente.
     */
    @Test
    public void getPagoClienteTest() {
        PagoClienteEntity entity = data.get(0);
        PagoClienteEntity resultEntity = pagoClienteLogic.getPagoCliente(entity.getId());
        Assert.assertNotNull(resultEntity);
        
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getMonto(), resultEntity.getMonto());
        Assert.assertEquals(entity.getReferencia(), resultEntity.getReferencia());
    }
    
    
    
    /**
     * Prueba para actualizar un PagoCliente con todo bien
     *
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    @Test
    public void UpdatePagoClienteTest() throws BusinessLogicException {
        PagoClienteEntity entity = data.get(0);
        PagoClienteEntity newEntity = factory.manufacturePojo(PagoClienteEntity.class);
        
        newEntity.setId(entity.getId());
        pagoClienteLogic.updatePagoCliente(newEntity.getId(), newEntity);
        PagoClienteEntity resp = em.find(PagoClienteEntity.class, entity.getId());

        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getMonto(), resp.getMonto());
        Assert.assertEquals(newEntity.getReferencia(), resp.getReferencia());
    }
    
     /**
     * Prueba para crear un pago con monto inválido(menor a 0)
     *
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updatePagoClienteTestConMontoInvalido() throws BusinessLogicException {
        
        PagoClienteEntity entity = data.get(0);
        PagoClienteEntity newEntity = factory.manufacturePojo(PagoClienteEntity.class);
        Double monto = -20.56;
        newEntity.setMonto(monto);
        newEntity.setId(entity.getId());
        pagoClienteLogic.updatePagoCliente(newEntity.getId(),newEntity);
    }
    
    /**
     * Prueba para crear un pago con referencia inválido(referencia que ya existe)
     *
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updatePagoClienteTestConReferenciaExistente() throws BusinessLogicException {
        
        PagoClienteEntity entity = data.get(0);
        PagoClienteEntity newEntity = factory.manufacturePojo(PagoClienteEntity.class);
        newEntity.setId(entity.getId());    
        newEntity.setReferencia(data.get(1).getReferencia());
        pagoClienteLogic.updatePagoCliente(newEntity.getId(), newEntity);
    }
    
    /**
     * Prueba para crear un pago que este relacionado con contratoHotel y despues con contratoPAseo.
     *
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updatePagoClienteTestConDosContratosHotelExistente() throws BusinessLogicException {
        
        PagoClienteEntity entity = data.get(0);
        PagoClienteEntity newEntity = factory.manufacturePojo(PagoClienteEntity.class);
        newEntity.setId(entity.getId());
        newEntity.setContratoHotel(ContratoHotelData.get(0));
        newEntity.setContratoPaseo(ContratoPaseoData.get(0)); 

        pagoClienteLogic.updatePagoCliente(newEntity.getId() ,newEntity);
    }
    
    /**
     * Prueba para crear un pago que este relacionado con  contratoPaseo y despues con contratoHotel.
     *
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updatePagoClienteTestConDosContratosPaseoExistente() throws BusinessLogicException {
       
        PagoClienteEntity entity = data.get(0);
        PagoClienteEntity newEntity = factory.manufacturePojo(PagoClienteEntity.class);
        newEntity.setId(entity.getId());
        
        newEntity.setContratoPaseo(ContratoPaseoData.get(0)); 
        newEntity.setContratoHotel(ContratoHotelData.get(0));

        pagoClienteLogic.createPagoCliente(newEntity);
    }
    
    
    
    
    /**
     * Prueba para eliminar un PagoCliente
     */
    @Test
    public void deletePagoClienteTest() {
        PagoClienteEntity entity = data.get(0);
        pagoClienteLogic.deletePagoCliente(entity.getId());
        PagoClienteEntity deleted = em.find(PagoClienteEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
