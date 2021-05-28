/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.logic;

import co.edu.uniandes.csw.paseadores.ejb.ContratoHotelHoraHotelLogic;
import co.edu.uniandes.csw.paseadores.ejb.HoraHotelLogic;
import co.edu.uniandes.csw.paseadores.entities.ContratoHotelEntity;
import co.edu.uniandes.csw.paseadores.entities.HoraHotelEntity;
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
 * @author Alvaro Plata
 */
@RunWith(Arquillian.class)
public class ContratoHotelHoraHotelLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private ContratoHotelHoraHotelLogic contratoHotelHoraHotelLogic;
    
    @Inject
    private HoraHotelLogic horaHotelLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;
    
    private ContratoHotelEntity contrato;

    private List<HoraHotelEntity> horasData = new ArrayList();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ContratoHotelEntity.class.getPackage())
                .addPackage(ContratoHotelHoraHotelLogic.class.getPackage())
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
        em.createQuery("delete from HoraHotelEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        
        contrato = factory.manufacturePojo(ContratoHotelEntity.class);
        contrato.setId(1L);
        contrato.setHorasHotel(new ArrayList<>());
        em.persist(contrato);
        
        for (int i = 0; i < 3; i++) {
            HoraHotelEntity entity = factory.manufacturePojo(HoraHotelEntity.class);
            entity.setContratosHotel(new ArrayList<>());
            entity.getContratosHotel().add(contrato);
            em.persist(entity);
            horasData.add(entity);
            contrato.getHorasHotel().add(entity);
        }
    }
    
    /**
     * Prueba para asociar una HoraHotel existente a un ContratoHotel.
     */
    @Test
    public void addHoraHotelTest(){
        HoraHotelEntity horaEntity = factory.manufacturePojo(HoraHotelEntity.class);
        horaEntity.setCostoBase(1.0);
        horaEntity.setCupoMaximo(2);
        
        try{
            horaHotelLogic.createHoraHotel(horaEntity);
            HoraHotelEntity response = null;
            response = contratoHotelHoraHotelLogic.addHoraHotel(horaEntity.getId(), contrato.getId());
            Assert.assertNotNull(response);
            Assert.assertEquals(horaEntity.getId(), response.getId());
            Assert.assertEquals(horaEntity.getCostoBase(), response.getCostoBase(), 1);
            Assert.assertEquals(horaEntity.getCupoMaximo(), response.getCupoMaximo());
            Assert.assertEquals(horaEntity.getDia(), response.getDia());
            Assert.assertEquals(horaEntity.getDisponible(), response.getDisponible());
        
            HoraHotelEntity lastHora = contratoHotelHoraHotelLogic.getHoraHotel(contrato.getId(), response.getId());
        
            Assert.assertNotNull(lastHora);
            Assert.assertEquals(lastHora.getId(), response.getId());
            Assert.assertEquals(lastHora.getCostoBase(), response.getCostoBase());
            Assert.assertEquals(lastHora.getCupoMaximo(), response.getCupoMaximo());
            Assert.assertEquals(lastHora.getDia(), response.getDia());
            Assert.assertEquals(lastHora.getDisponible(), response.getDisponible());
        }
        catch(BusinessLogicException b)
        {
            Assert.fail(b.getMessage());
        }
        
        
    }
    
    /**
     * Prueba para consultar las HorasHotel de un ContratoHotel.
     */
    @Test
    public void getHorasHotelTest()
    {
        List<HoraHotelEntity> horasEntities = contratoHotelHoraHotelLogic.getHorasHotel(contrato.getId());
        
        Assert.assertEquals(horasData.size(), horasEntities.size());
        
        for (int i = 0; i < horasData.size(); i++) {
            Assert.assertTrue(horasEntities.contains(horasData.get(0)));
        }
    }
    
    /**
     * Prueba para consultar una horaHotel de un contratoHotel.
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    @Test
    public void getHoraHotelTest() 
    {
        HoraHotelEntity horaEntity = horasData.get(0);
        HoraHotelEntity response = contratoHotelHoraHotelLogic.getHoraHotel(contrato.getId(), horaEntity.getId());
        Assert.assertNotNull(response);

        Assert.assertEquals(horaEntity.getId(), response.getId());
        Assert.assertEquals(horaEntity.getCostoBase(), response.getCostoBase());
        Assert.assertEquals(horaEntity.getCupoMaximo(), response.getCupoMaximo());
        Assert.assertEquals(horaEntity.getDia(), response.getDia());
        Assert.assertEquals(horaEntity.getDisponible(), response.getDisponible());
    }
    
    /**
     * Prueba para actualizar las horasHotel de un ContratoHotel.
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    @Test
    public void replaceHorasHotelTest(){
        List<HoraHotelEntity> nuevaLista = new ArrayList<>();
        for (int i = 0; i < 3; i++) {
            HoraHotelEntity entity = factory.manufacturePojo(HoraHotelEntity.class);
            entity.setCostoBase(1.0);
            entity.setCupoMaximo(2);
            entity.setContratosHotel(new ArrayList<>());
            entity.getContratosHotel().add(contrato);
            try{
                horaHotelLogic.createHoraHotel(entity);
            }
            catch(BusinessLogicException b)
            {
                Assert.fail("No debería mandar excepción: " + b.getMessage());
            }
            nuevaLista.add(entity);
        }
        contratoHotelHoraHotelLogic.replaceHorasHotel(contrato.getId(), nuevaLista);
        List<HoraHotelEntity> horaEntities = contratoHotelHoraHotelLogic.getHorasHotel(contrato.getId());
        for (HoraHotelEntity aNuevaLista : nuevaLista) {
            Assert.assertTrue(horaEntities.contains(aNuevaLista));
        }
    }
    
    /**
     * Prueba desasociar una HoraHotel con un ContratoHotel.
     *
     */
    @Test
    public void removeHoraHotelTest() {
        for (HoraHotelEntity hora : horasData) {
            contratoHotelHoraHotelLogic.removeHoraHotel(contrato.getId(), hora.getId());
        }
        Assert.assertTrue(contratoHotelHoraHotelLogic.getHorasHotel(contrato.getId()).isEmpty());
    }
}
