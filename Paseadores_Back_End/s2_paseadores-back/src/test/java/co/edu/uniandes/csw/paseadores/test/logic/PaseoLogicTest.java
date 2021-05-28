/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.logic;

import co.edu.uniandes.csw.paseadores.ejb.PaseoLogic;
import co.edu.uniandes.csw.paseadores.entities.HorarioEntity;
import co.edu.uniandes.csw.paseadores.entities.PaseoEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.PaseoPersistence;
import java.util.Date;
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
public class PaseoLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private PaseoLogic paseoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<PaseoEntity> paseoData = new ArrayList<>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PaseoEntity.class.getPackage())
                .addPackage(PaseoLogic.class.getPackage())
                .addPackage(PaseoPersistence.class.getPackage())
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
        em.createQuery("delete from PaseoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            PaseoEntity paseo = factory.manufacturePojo(PaseoEntity.class);
            em.persist(paseo);
            paseoData.add(paseo);
        }
    }
    
    @Test
    public void createPaseoConCupoMaximoInvalidoTest()
    {
        PaseoEntity nuevoPaseo = factory.manufacturePojo(PaseoEntity.class);
        nuevoPaseo.setCupoMaximo(-10);
        try
        {
            paseoLogic.createPaseo(nuevoPaseo);
            Assert.fail("Debería lanzar excepción");
        }
        catch(BusinessLogicException b){}
    }
    
    @Test
    public void createPaseoConCostoInvalidoTest()
    {
        PaseoEntity nuevoPaseo = factory.manufacturePojo(PaseoEntity.class);
        nuevoPaseo.setCosto(-10000.0);
        try
        {
            paseoLogic.createPaseo(nuevoPaseo);
            Assert.fail("Debería lanzar excepción");
        }
        catch(BusinessLogicException b){}
    }
    
    @Test
    public void createPaseoConHorariosInvalidos()
    {
        PaseoEntity nuevoPaseo = factory.manufacturePojo(PaseoEntity.class);
        nuevoPaseo.setHorario1(null);
        nuevoPaseo.setHorario2(factory.manufacturePojo(HorarioEntity.class));
        try
        {
            paseoLogic.createPaseo(nuevoPaseo);
            Assert.fail("Debería lanzar excepción");
        }
        catch(BusinessLogicException b){}
    }
    
    @Test
    public void createPaseoTest()
    {
        PaseoEntity nuevoPaseo = factory.manufacturePojo(PaseoEntity.class);
        nuevoPaseo.setCupoMaximo(10);
        nuevoPaseo.setCosto(10000.0);
        PaseoEntity paseoRespuesta = null;
        try{
            paseoRespuesta = paseoLogic.createPaseo(nuevoPaseo);
        }
        catch(BusinessLogicException ex)
        {
            Assert.fail("No debería lanzar excepción");
        }
        Assert.assertNotNull(paseoRespuesta);
        PaseoEntity paseoEncontrado = em.find(PaseoEntity.class, paseoRespuesta.getId());
        Assert.assertEquals(nuevoPaseo.getId(), paseoEncontrado.getId());
        Assert.assertEquals(nuevoPaseo.getCupoMaximo(), paseoEncontrado.getCupoMaximo());
        Assert.assertEquals(nuevoPaseo.getCosto(), paseoEncontrado.getCosto());
        Assert.assertEquals(nuevoPaseo.getDuracion(), paseoEncontrado.getDuracion());
        Assert.assertEquals(nuevoPaseo.getDisponible(), paseoEncontrado.getDisponible());
        Assert.assertEquals(nuevoPaseo.getHoraInicio(), paseoEncontrado.getHoraInicio());
    }
    
    @Test
    public void getPaseoTest()
    {        
        PaseoEntity paseo = paseoData.get(0);
        PaseoEntity paseoResultado = paseoLogic.getPaseo(paseo.getId());
        Assert.assertNotNull(paseoResultado);
        Assert.assertEquals(paseoResultado.getId(), paseo.getId());
        Assert.assertEquals(paseoResultado.getCupoMaximo(), paseo.getCupoMaximo());
        Assert.assertEquals(paseoResultado.getCosto(), paseo.getCosto());
        Assert.assertEquals(paseoResultado.getDuracion(), paseo.getDuracion());
        Assert.assertEquals(paseoResultado.getDisponible(), paseo.getDisponible());
        Assert.assertEquals(paseoResultado.getHoraInicio(), paseo.getHoraInicio());
        
        paseo = factory.manufacturePojo(PaseoEntity.class);
        paseoResultado = paseoLogic.getPaseo(paseo.getId());
        Assert.assertNull(paseoResultado);
    }
    
    @Test
    public void getAllPaseosTest()
    {
        List<PaseoEntity> listaPaseos = paseoLogic.getAllPaseos();
        Assert.assertEquals(listaPaseos.size(), paseoData.size());
        for(PaseoEntity paseoBuscado: listaPaseos)
        {
            boolean existe = false;
            for(PaseoEntity paseoDatos: paseoData)
            {
                if(paseoBuscado.getId().equals(paseoDatos.getId()))
                {
                    Assert.assertEquals(paseoBuscado.getCupoMaximo(), paseoDatos.getCupoMaximo());
                    Assert.assertEquals(paseoBuscado.getCosto(), paseoDatos.getCosto());
                    Assert.assertEquals(paseoBuscado.getDuracion(), paseoDatos.getDuracion());
                    Assert.assertEquals(paseoBuscado.getDisponible(), paseoDatos.getDisponible());
                    Assert.assertEquals(paseoBuscado.getHoraInicio(), paseoDatos.getHoraInicio());
                    existe = true;
                }
            }
            Assert.assertTrue(existe);
        }
    }
    
    @Test
    public void getPaseosByCostoInRangeTest()
    {
        Double costoMedio;
        Double costoMayor = paseoData.get(0).getCosto();
        Double costoMenor = paseoData.get(0).getCosto();
        int posicionMayor = 0; int posicionMenor = 0;
        for(int i=1; i < paseoData.size(); i++)
        {
            if(costoMayor < paseoData.get(i).getCosto())
            {
                costoMayor = paseoData.get(i).getCosto();
                posicionMayor = i;
            }
            if(costoMenor > paseoData.get(i).getCosto())
            {
                costoMenor = paseoData.get(i).getCosto();
                posicionMenor = i;
            }
        }
        
        switch (posicionMayor+posicionMenor) {
            case 1:
                costoMedio = paseoData.get(2).getCosto();
                break;
            case 2:
                costoMedio = paseoData.get(1).getCosto();
                break;
            default:
                costoMedio = paseoData.get(0).getCosto();
                break;
        }
        Assert.assertEquals(0,paseoLogic.getPaseosByCostoInRange(costoMedio, costoMenor).size());
        Assert.assertEquals(0,paseoLogic.getPaseosByCostoInRange(costoMayor, costoMedio).size());
        Assert.assertEquals(0,paseoLogic.getPaseosByCostoInRange(costoMayor, costoMenor).size());
        Assert.assertEquals(1,paseoLogic.getPaseosByCostoInRange(costoMenor, costoMenor).size());
        Assert.assertEquals(1,paseoLogic.getPaseosByCostoInRange(costoMedio, costoMedio).size());
        Assert.assertEquals(1,paseoLogic.getPaseosByCostoInRange(costoMayor, costoMayor).size());
        Assert.assertEquals(2,paseoLogic.getPaseosByCostoInRange(costoMenor, costoMedio).size());
        Assert.assertEquals(2,paseoLogic.getPaseosByCostoInRange(costoMedio, costoMayor).size());
        Assert.assertEquals(3,paseoLogic.getPaseosByCostoInRange(costoMenor, costoMayor).size());
    }
    
    @Test
    public void getPaseosByHoraInicioInRangeTest()
    {
        Date fechaMedia;
        Date fechaMayor = paseoData.get(0).getHoraInicio();
        Date fechaMenor = paseoData.get(0).getHoraInicio();
        int posicionMayor = 0; int posicionMenor = 0;
        for(int i=1; i < paseoData.size(); i++)
        {
            if(fechaMayor.compareTo(paseoData.get(i).getHoraInicio()) < 0)
            {
                fechaMayor = paseoData.get(i).getHoraInicio();
                posicionMayor = i;
            }
            if(fechaMenor.compareTo(paseoData.get(i).getHoraInicio()) > 0)
            {
                fechaMenor = paseoData.get(i).getHoraInicio();
                posicionMenor = i;
            }
        }
        
        switch (posicionMayor+posicionMenor) {
            case 1:
                fechaMedia = paseoData.get(2).getHoraInicio();
                break;
            case 2:
                fechaMedia = paseoData.get(1).getHoraInicio();
                break;
            default:
                fechaMedia = paseoData.get(0).getHoraInicio();
                break;
        }
        Assert.assertEquals(0,paseoLogic.getPaseosByHoraInicioInRange(fechaMedia, fechaMenor).size());
        Assert.assertEquals(0,paseoLogic.getPaseosByHoraInicioInRange(fechaMayor, fechaMedia).size());
        Assert.assertEquals(0,paseoLogic.getPaseosByHoraInicioInRange(fechaMayor, fechaMenor).size());
        Assert.assertEquals(1,paseoLogic.getPaseosByHoraInicioInRange(fechaMenor, fechaMenor).size());
        Assert.assertEquals(1,paseoLogic.getPaseosByHoraInicioInRange(fechaMedia, fechaMedia).size());
        Assert.assertEquals(1,paseoLogic.getPaseosByHoraInicioInRange(fechaMayor, fechaMayor).size());
        Assert.assertEquals(2,paseoLogic.getPaseosByHoraInicioInRange(fechaMenor, fechaMedia).size());
        Assert.assertEquals(2,paseoLogic.getPaseosByHoraInicioInRange(fechaMedia, fechaMayor).size());
        Assert.assertEquals(3,paseoLogic.getPaseosByHoraInicioInRange(fechaMenor, fechaMayor).size());
    }
    
    @Test
    public void updatePaseoConCupoMaximoInvalidoTest()
    {
        PaseoEntity paseo = paseoData.get(0);
        PaseoEntity paseoNoExistente = factory.manufacturePojo(PaseoEntity.class);
        paseoNoExistente.setId(paseo.getId());
        paseoNoExistente.setCupoMaximo(-10);
        try
        {
            paseoLogic.updatePaseo(paseoNoExistente);
            Assert.fail("Debería lanzar excepción");
        }
        catch(BusinessLogicException b){}
    }
    
    @Test
    public void updatePaseoConCostoInvalidoTest()
    {
        PaseoEntity paseo = paseoData.get(0);
        PaseoEntity paseoNoExistente = factory.manufacturePojo(PaseoEntity.class);
        paseoNoExistente.setId(paseo.getId());
        paseoNoExistente.setCosto(-10000.0);
        try
        {
            paseoLogic.updatePaseo(paseoNoExistente);
            Assert.fail("Debería lanzar excepción");
        }
        catch(BusinessLogicException b){}
    }
    
    @Test
    public void updatePaseoConHorariosInvalidosTest()
    {
        PaseoEntity paseo = paseoData.get(0);
        PaseoEntity paseoNoExistente = factory.manufacturePojo(PaseoEntity.class);
        paseoNoExistente.setId(paseo.getId());
        paseoNoExistente.setHorario1(null);
        paseoNoExistente.setHorario2(factory.manufacturePojo(HorarioEntity.class));
        try
        {
            paseoLogic.updatePaseo(paseoNoExistente);
            Assert.fail("Debería lanzar excepción");
        }
        catch(BusinessLogicException b){}
    }
    
    @Test
    public void updatePaseoTest()
    {
        PaseoEntity paseo = paseoData.get(0);
        PaseoEntity paseoNoExistente = factory.manufacturePojo(PaseoEntity.class);
        paseoNoExistente.setId(paseo.getId());
        paseoNoExistente.setCupoMaximo(10);
        paseoNoExistente.setCosto(10000.0);
        PaseoEntity paseoRespuesta = null;
        try{
            paseoRespuesta = paseoLogic.createPaseo(paseoNoExistente);
        }
        catch(BusinessLogicException ex)
        {
            Assert.fail("No debería lanzar excepción");
        }
        Assert.assertNotNull(paseoRespuesta);
        PaseoEntity paseoEncontrado = em.find(PaseoEntity.class, paseoRespuesta.getId());
        Assert.assertEquals(paseoNoExistente.getId(), paseoEncontrado.getId());
        Assert.assertEquals(paseoNoExistente.getCupoMaximo(), paseoEncontrado.getCupoMaximo());
        Assert.assertEquals(paseoNoExistente.getCosto(), paseoEncontrado.getCosto());
        Assert.assertEquals(paseoNoExistente.getDuracion(), paseoEncontrado.getDuracion());
        Assert.assertEquals(paseoNoExistente.getDisponible(), paseoEncontrado.getDisponible());
        Assert.assertEquals(paseoNoExistente.getHoraInicio(), paseoEncontrado.getHoraInicio());
    }
    
    @Test()
    public void deletePaseoTest() {
        PaseoEntity paseo = paseoData.get(2);
        paseoLogic.deletePaseo(paseo.getId());
        Assert.assertNull(paseoLogic.getPaseo(paseo.getId()));
    }
}
