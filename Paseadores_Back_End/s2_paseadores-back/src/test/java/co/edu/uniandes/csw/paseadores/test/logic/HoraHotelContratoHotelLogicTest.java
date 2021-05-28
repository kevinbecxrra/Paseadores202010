/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.logic;

import co.edu.uniandes.csw.paseadores.ejb.ContratoHotelLogic;
import co.edu.uniandes.csw.paseadores.ejb.HoraHotelContratoHotelLogic;
import co.edu.uniandes.csw.paseadores.entities.ContratoHotelEntity;
import co.edu.uniandes.csw.paseadores.entities.HoraHotelEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.HoraHotelPersistence;
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
 * @author Alvaro Plata
 */
@RunWith(Arquillian.class)
public class HoraHotelContratoHotelLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private HoraHotelContratoHotelLogic horaHotelContratoHotelLogic;
    
    @Inject
    private ContratoHotelLogic contratoHotelLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private HoraHotelEntity horaHotel;

    private List<ContratoHotelEntity> contratosData = new ArrayList();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(HoraHotelEntity.class.getPackage())
                .addPackage(HoraHotelContratoHotelLogic.class.getPackage())
                .addPackage(HoraHotelPersistence.class.getPackage())
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
        em.createQuery("delete from HoraHotelEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        
        horaHotel = factory.manufacturePojo(HoraHotelEntity.class);
        horaHotel.setId(1L);
        horaHotel.setContratosHotel(new ArrayList<>());
        em.persist(horaHotel);
        
        for (int i = 0; i < 3; i++) {
            ContratoHotelEntity entity = factory.manufacturePojo(ContratoHotelEntity.class);
            entity.setHorasHotel(new ArrayList<>());
            entity.getHorasHotel().add(horaHotel);
            em.persist(entity);
            contratosData.add(entity);
            horaHotel.getContratosHotel().add(entity);
        }
    }
    
    /**
     * Prueba para asociar un ContratoHotel existente a una HoraHotel.
     */
    @Test
    public void addContratoHotelTest(){
        try {
            ContratoHotelEntity contratoEntity = factory.manufacturePojo(ContratoHotelEntity.class);
            contratoHotelLogic.createContratoHotel(contratoEntity);
            ContratoHotelEntity response = horaHotelContratoHotelLogic.addContrato(contratoEntity.getId(), horaHotel.getId());
            
            Assert.assertNotNull(response);
            Assert.assertEquals(contratoEntity.getId(), response.getId());
            Assert.assertEquals(contratoEntity.getCalificacionHotel(), response.getCalificacionHotel());
            Assert.assertEquals(contratoEntity.getCliente(), response.getCliente());
            Assert.assertEquals(contratoEntity.getCosto(), response.getCosto());
            Assert.assertEquals(contratoEntity.getCuidadoEspecial(), response.getCuidadoEspecial());
            Assert.assertEquals(contratoEntity.getPago(), response.getPago());
            Assert.assertEquals(contratoEntity.getPerro(), response.getPerro());
            
            ContratoHotelEntity lastContrato = horaHotelContratoHotelLogic.getContrato(horaHotel.getId(), contratoEntity.getId());
            
            Assert.assertNotNull(lastContrato);
            Assert.assertEquals(lastContrato.getId(), response.getId());
            Assert.assertEquals(lastContrato.getCalificacionHotel(), response.getCalificacionHotel());
            Assert.assertEquals(lastContrato.getCliente(), response.getCliente());
            Assert.assertEquals(lastContrato.getCosto(), response.getCosto());
            Assert.assertEquals(lastContrato.getCuidadoEspecial(), response.getCuidadoEspecial());
            Assert.assertEquals(lastContrato.getPago(), response.getPago());
            Assert.assertEquals(lastContrato.getPerro(), response.getPerro());
        } catch (BusinessLogicException ex) {
            Logger.getLogger(HoraHotelContratoHotelLogicTest.class.getName()).log(Level.SEVERE, null, ex);
            Assert.fail(ex.getMessage());
        }
    }
    
    /**
     * Prueba para consultar los ContratosHotel de una HoraHotel.
     */
    @Test
    public void getContratosHotelTest()
    {
        List<ContratoHotelEntity> contratosEntities = horaHotelContratoHotelLogic.getContratos(horaHotel.getId());
        
        Assert.assertEquals(contratosData.size(), contratosEntities.size());
        
        for (int i = 0; i < contratosData.size(); i++) {
            Assert.assertTrue(contratosEntities.contains(contratosData.get(0)));
        }
    }
    
    /**
     * Prueba para consultar un contratoHotel de una horatoHotel.
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    @Test
    public void getContratoHotelTest()
    {
        ContratoHotelEntity contratoEntity = contratosData.get(0);
        ContratoHotelEntity response = horaHotelContratoHotelLogic.getContrato(horaHotel.getId(), contratoEntity.getId());
        
        Assert.assertNotNull(contratoEntity);
        Assert.assertEquals(contratoEntity.getId(), response.getId());
        Assert.assertEquals(contratoEntity.getCalificacionHotel(), response.getCalificacionHotel());
        Assert.assertEquals(contratoEntity.getCliente(), response.getCliente());
        Assert.assertEquals(contratoEntity.getCosto(), response.getCosto());
        Assert.assertEquals(contratoEntity.getCuidadoEspecial(), response.getCuidadoEspecial());
        Assert.assertEquals(contratoEntity.getPago(), response.getPago());
        Assert.assertEquals(contratoEntity.getPerro(), response.getPerro());
    }
    
    /**
     * Prueba para actualizar los contratosHotel de una horaHotel.
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    @Test
    public void replaceContratosHotelTest(){
        List<ContratoHotelEntity> nuevaLista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            ContratoHotelEntity entity = factory.manufacturePojo(ContratoHotelEntity.class);
            entity.setHorasHotel(new ArrayList<>());
            entity.getHorasHotel().add(horaHotel);
            try {
                contratoHotelLogic.createContratoHotel(entity);
            } catch (BusinessLogicException ex) {
                Logger.getLogger(HoraHotelContratoHotelLogicTest.class.getName()).log(Level.SEVERE, null, ex);
                Assert.fail(ex.getMessage());
            }
            nuevaLista.add(entity);
        }
        horaHotelContratoHotelLogic.replaceContratosHotel(horaHotel.getId(), nuevaLista);
        List<ContratoHotelEntity> contratoEntities = horaHotelContratoHotelLogic.getContratos(horaHotel.getId());
        for (ContratoHotelEntity aNuevaLista : nuevaLista) {
            Assert.assertTrue(contratoEntities.contains(aNuevaLista));
        }
    }
    
    /**
     * Prueba desasociar un ContratoHotel con una HoraHotel.
     *
     */
    @Test
    public void removeContratoHotelTest() {
        for (ContratoHotelEntity contrato : contratosData) {
            horaHotelContratoHotelLogic.removeContratoHotel(horaHotel.getId(), contrato.getId());
        }
        Assert.assertTrue(horaHotelContratoHotelLogic.getContratos(horaHotel.getId()).isEmpty());
    }
}