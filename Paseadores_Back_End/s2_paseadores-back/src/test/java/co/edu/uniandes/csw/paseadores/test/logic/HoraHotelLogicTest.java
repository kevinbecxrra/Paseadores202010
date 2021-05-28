/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.logic;

import co.edu.uniandes.csw.paseadores.ejb.HoraHotelLogic;
import co.edu.uniandes.csw.paseadores.entities.HoraHotelEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.HoraHotelPersistence;
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
public class HoraHotelLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private HoraHotelLogic horaHotelLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<HoraHotelEntity> data = new ArrayList<HoraHotelEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(HoraHotelEntity.class.getPackage())
                .addPackage(HoraHotelLogic.class.getPackage())
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
        em.createQuery("delete from HoraHotelEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            HoraHotelEntity paseo = factory.manufacturePojo(HoraHotelEntity.class);
            em.persist(paseo);
            data.add(paseo);
        }
    }

    /**
     * Prueba para crear un HoraHotel.
     *
     */
    @Test
    public void createHoraHotelTest() {
        HoraHotelEntity newEntity = factory.manufacturePojo(HoraHotelEntity.class
        );
        newEntity.setCupoMaximo(20);
        newEntity.setCostoBase(10.000);
        HoraHotelEntity result = null;
        try {
            result = horaHotelLogic.createHoraHotel(newEntity);
        } catch (BusinessLogicException b) {
            Assert.fail("No debería lanzar excepción");
        }
        Assert.assertNotNull(result);
        HoraHotelEntity entity = em.find(HoraHotelEntity.class,
                result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getCostoBase(), entity.getCostoBase());
        Assert.assertEquals(newEntity.getCupoMaximo(), entity.getCupoMaximo());
        Assert.assertEquals(newEntity.getDia(), entity.getDia());
        Assert.assertEquals(newEntity.getDisponible(), entity.getDisponible());

    }

    /**
     * Prueba para crear un HoraHotel.
     *
     */
    @Test
    public void createHoraHotelConCostoBaseInvalidaTest() {
        HoraHotelEntity noEntity = factory.manufacturePojo(HoraHotelEntity.class);
        noEntity.setCostoBase(-10.000);
        try {
            horaHotelLogic.createHoraHotel(noEntity);
            Assert.fail("Debería lanzar excepción");
        } catch (BusinessLogicException b) {
        }
    }

    /**
     * Prueba para crear un HoraHotel.
     *
     */
    @Test
    public void createHoraHotelConCupoMaximoInvalidoTest() {
        HoraHotelEntity noEntity = factory.manufacturePojo(HoraHotelEntity.class
        );
        noEntity.setCupoMaximo(-25);
        try {
            horaHotelLogic.createHoraHotel(noEntity);
            Assert.fail("Debería lanzar excepción");
        } catch (BusinessLogicException b) {
        }
    }

    /**
     * Prueba para consultar un HoraHotel.
     */
    @Test
    public void getHoraHotelTest() {
        HoraHotelEntity entity = data.get(0);
        HoraHotelEntity resultEntity = horaHotelLogic.getHoraHotel(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(resultEntity.getId(), entity.getId());
        Assert.assertEquals(resultEntity.getCostoBase(), entity.getCostoBase());
        Assert.assertEquals(resultEntity.getCupoMaximo(), entity.getCupoMaximo());
        Assert.assertEquals(resultEntity.getDia(), entity.getDia());
        Assert.assertEquals(resultEntity.getDisponible(), entity.getDisponible());

    }

    @Test
    public void getHoraHotelByCostoBaseInRangeTest()
    {
        Double costoMedio;
        Double costoMayor = data.get(0).getCostoBase();
        Double costoMenor = data.get(0).getCostoBase();
        int posicionMayor = 0; int posicionMenor = 0;
        for(int i=1; i < data.size(); i++)
        {
            if(costoMayor < data.get(i).getCostoBase())
            {
                costoMayor = data.get(i).getCostoBase();
                posicionMayor = i;
            }
            if(costoMenor > data.get(i).getCostoBase())
            {
                costoMenor = data.get(i).getCostoBase();
                posicionMenor = i;
            }
        }
        
        switch (posicionMayor+posicionMenor) {
            case 1:
                costoMedio = data.get(2).getCostoBase();
                break;
            case 2:
                costoMedio = data.get(1).getCostoBase();
                break;
            default:
                costoMedio = data.get(0).getCostoBase();
                break;
        }
        Assert.assertEquals(0,horaHotelLogic.getHoraHotelByCostoBaseInRange(costoMedio, costoMenor).size());
        Assert.assertEquals(0,horaHotelLogic.getHoraHotelByCostoBaseInRange(costoMayor, costoMedio).size());
        Assert.assertEquals(0,horaHotelLogic.getHoraHotelByCostoBaseInRange(costoMayor, costoMenor).size());
        Assert.assertEquals(1,horaHotelLogic.getHoraHotelByCostoBaseInRange(costoMenor, costoMenor).size());
        Assert.assertEquals(1,horaHotelLogic.getHoraHotelByCostoBaseInRange(costoMedio, costoMedio).size());
        Assert.assertEquals(1,horaHotelLogic.getHoraHotelByCostoBaseInRange(costoMayor, costoMayor).size());
        Assert.assertEquals(2,horaHotelLogic.getHoraHotelByCostoBaseInRange(costoMenor, costoMedio).size());
        Assert.assertEquals(2,horaHotelLogic.getHoraHotelByCostoBaseInRange(costoMedio, costoMayor).size());
        Assert.assertEquals(3,horaHotelLogic.getHoraHotelByCostoBaseInRange(costoMenor, costoMayor).size());
    }
    
    @Test
    public void getHoraHotelByDiaInRangeTest()
    {
        Date fechaMedia;
        Date fechaMayor = data.get(0).getDia();
        Date fechaMenor = data.get(0).getDia();
        int posicionMayor = 0; int posicionMenor = 0;
        for(int i=1; i < data.size(); i++)
        {
            if(fechaMayor.compareTo(data.get(i).getDia()) < 0)
            {
                fechaMayor = data.get(i).getDia();
                posicionMayor = i;
            }
            if(fechaMenor.compareTo(data.get(i).getDia()) > 0)
            {
                fechaMenor = data.get(i).getDia();
                posicionMenor = i;
            }
        }
        
        switch (posicionMayor+posicionMenor) {
            case 1:
                fechaMedia = data.get(2).getDia();
                break;
            case 2:
                fechaMedia = data.get(1).getDia();
                break;
            default:
                fechaMedia = data.get(0).getDia();
                break;
        }
        Assert.assertEquals(0,horaHotelLogic.getHoraHotelByDiaInRange(fechaMedia, fechaMenor).size());
        Assert.assertEquals(0,horaHotelLogic.getHoraHotelByDiaInRange(fechaMayor, fechaMedia).size());
        Assert.assertEquals(0,horaHotelLogic.getHoraHotelByDiaInRange(fechaMayor, fechaMenor).size());
        Assert.assertEquals(1,horaHotelLogic.getHoraHotelByDiaInRange(fechaMenor, fechaMenor).size());
        Assert.assertEquals(1,horaHotelLogic.getHoraHotelByDiaInRange(fechaMedia, fechaMedia).size());
        Assert.assertEquals(1,horaHotelLogic.getHoraHotelByDiaInRange(fechaMayor, fechaMayor).size());
        Assert.assertEquals(2,horaHotelLogic.getHoraHotelByDiaInRange(fechaMenor, fechaMedia).size());
        Assert.assertEquals(2,horaHotelLogic.getHoraHotelByDiaInRange(fechaMedia, fechaMayor).size());
        Assert.assertEquals(3,horaHotelLogic.getHoraHotelByDiaInRange(fechaMenor, fechaMayor).size());
    }
    
    @Test
    public void updateHoraHotelConCupoMaximoInvalidoTest()
    {
        HoraHotelEntity paseo = data.get(0);
        HoraHotelEntity horaHotelNoExistente = factory.manufacturePojo(HoraHotelEntity.class);
        horaHotelNoExistente.setId(paseo.getId());
        horaHotelNoExistente.setCupoMaximo(-10);
        try
        {
            horaHotelLogic.updateHoraHotel(horaHotelNoExistente);
            Assert.fail("Debería lanzar excepción");
        }
        catch(BusinessLogicException b){}
    }
    
    @Test
    public void updateHoraHotelConCostoBaseInvalidoTest()
    {
        HoraHotelEntity horaHotel = data.get(0);
        HoraHotelEntity horaHotelNoExistente = factory.manufacturePojo(HoraHotelEntity.class);
        horaHotelNoExistente.setId(horaHotel.getId());
        horaHotelNoExistente.setCostoBase(-10000.0);
        try
        {
            horaHotelLogic.updateHoraHotel(horaHotelNoExistente);
            Assert.fail("Debería lanzar excepción");
        }
        catch(BusinessLogicException b){}
    }
    
    @Test
    public void updateHoraHotelTest()
    {
        HoraHotelEntity horaHotel = data.get(0);
        HoraHotelEntity horaHotelNoExistente = factory.manufacturePojo(HoraHotelEntity.class);
        horaHotelNoExistente.setId(horaHotel.getId());
        horaHotelNoExistente.setCupoMaximo(10);
        horaHotelNoExistente.setCostoBase(10000.0);
        HoraHotelEntity paseoRespuesta = null;
        try{
            paseoRespuesta = horaHotelLogic.createHoraHotel(horaHotelNoExistente);
        }
        catch(BusinessLogicException ex)
        {
            Assert.fail("No debería lanzar excepción");
        }
        Assert.assertNotNull(paseoRespuesta);
        HoraHotelEntity paseoEncontrado = em.find(HoraHotelEntity.class, paseoRespuesta.getId());
        Assert.assertEquals(horaHotelNoExistente.getId(), paseoEncontrado.getId());
        Assert.assertEquals(horaHotelNoExistente.getCupoMaximo(), paseoEncontrado.getCupoMaximo());
        Assert.assertEquals(horaHotelNoExistente.getCostoBase(), paseoEncontrado.getCostoBase());
        Assert.assertEquals(horaHotelNoExistente.getDia(), paseoEncontrado.getDia());
        Assert.assertEquals(horaHotelNoExistente.getDisponible(), paseoEncontrado.getDisponible());
    }
    
    @Test()
    public void deleteHoraHotelTest() {
        HoraHotelEntity horaHotel = data.get(2);
        horaHotelLogic.deleteHoraHotel(horaHotel.getId());
        Assert.assertNull(horaHotelLogic.getHoraHotel(horaHotel.getId()));
    }

}
