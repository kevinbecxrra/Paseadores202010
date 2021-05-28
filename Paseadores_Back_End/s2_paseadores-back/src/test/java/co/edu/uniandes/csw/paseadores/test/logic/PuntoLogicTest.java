/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.logic;

import co.edu.uniandes.csw.paseadores.ejb.ContratoPaseoLogic;
import co.edu.uniandes.csw.paseadores.ejb.PuntoLogic;
import co.edu.uniandes.csw.paseadores.entities.ContratoPaseoEntity;
import co.edu.uniandes.csw.paseadores.entities.PuntoEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.PuntoPersistence;
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
 * @author Nicolas Urrego Sandoval <n.urrego at uniandes.edu.co>
 */
@RunWith(Arquillian.class)
public class PuntoLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private PuntoLogic puntoLogic;

    @Inject
    private ContratoPaseoLogic contratoPaseoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ContratoPaseoEntity> contratoPaseoData = new ArrayList<>();

    private List<PuntoEntity> data = new ArrayList<PuntoEntity>();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PuntoEntity.class.getPackage())
                .addPackage(ContratoPaseoEntity.class.getPackage())
                .addPackage(PuntoLogic.class.getPackage())
                .addPackage(PuntoPersistence.class.getPackage())
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
        em.createQuery("delete from PuntoEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            PuntoEntity entity = factory.manufacturePojo(PuntoEntity.class);
            ContratoPaseoEntity contratoPaseoEntity = factory.manufacturePojo(ContratoPaseoEntity.class);
            em.persist(contratoPaseoEntity);
            entity.setContratoPaseo(contratoPaseoEntity);
            contratoPaseoEntity.setSitioEncuentro(entity);
            em.persist(entity);
            data.add(entity);
            contratoPaseoData.add(contratoPaseoEntity);
        }
    }

    /**
     * Prueba para crear un Punto.
     * 
     */
    @Test
    public void createPuntoTest() {
        PuntoEntity newEntity = factory.manufacturePojo(PuntoEntity.class);
        newEntity.setLatitud(4.603028);
        newEntity.setLongitud(-74.065309);
        PuntoEntity result = null;
        try
        {
            result = puntoLogic.createPunto(newEntity);
        }
        catch(BusinessLogicException b)
        {
            Assert.fail("No debería lanzar excepción");
        }
        Assert.assertNotNull(result);
        PuntoEntity entity = em.find(PuntoEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getLatitud(), entity.getLatitud());
        Assert.assertEquals(newEntity.getLongitud(), entity.getLongitud());
        Assert.assertEquals(newEntity.getPosicion(), entity.getPosicion());
    }
    
    /**
     * Prueba para crear un Punto.
     * 
     */
    @Test
    public void createPuntoConLatitudInvalidaTest()
    {
        PuntoEntity noEntity = factory.manufacturePojo(PuntoEntity.class);
        noEntity.setLatitud(0.0);
        noEntity.setLongitud(-74.065309);
        try
        {
            puntoLogic.createPunto(noEntity);
            Assert.fail("Debería lanzar excepción");
        }
        catch(BusinessLogicException b){}
        noEntity.setLatitud(100.0);
        try
        {
            puntoLogic.createPunto(noEntity);
            Assert.fail("Debería lanzar excepción");
        }
        catch(BusinessLogicException b){}
    }
    
    /**
     * Prueba para crear un Punto.
     * 
     */
    @Test
    public void createPuntoConLongitudInvalidaTest()
    {
        PuntoEntity noEntity = factory.manufacturePojo(PuntoEntity.class);
        noEntity.setLatitud(4.603028);
        noEntity.setLongitud(0.0);
        try
        {
            puntoLogic.createPunto(noEntity);
            Assert.fail("Debería lanzar excepción");
        }
        catch(BusinessLogicException b){}
        noEntity.setLongitud(-100.0);
        try
        {
            puntoLogic.createPunto(noEntity);
            Assert.fail("Debería lanzar excepción");
        }
        catch(BusinessLogicException b){}
    }

    /**
     * Prueba para consultar un Punto.
     */
    @Test
    public void getPuntoTest() 
    {
        PuntoEntity entity = data.get(0);
        PuntoEntity resultEntity = puntoLogic.getPunto(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(resultEntity.getId(), entity.getId());
        Assert.assertEquals(resultEntity.getLatitud(), entity.getLatitud());
        Assert.assertEquals(resultEntity.getLongitud(), entity.getLongitud());
        Assert.assertEquals(resultEntity.getPosicion(), entity.getPosicion());
    }
    
    /**
     * Prueba para consultar un Punto.
     */
    @Test
    public void getPuntoByPosicionTest() {
        PuntoEntity entity = data.get(0);
        PuntoEntity resultEntity = puntoLogic.getPuntoByPosicion(entity.getPosicion());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(resultEntity.getId(), entity.getId());
        Assert.assertEquals(resultEntity.getLatitud(), entity.getLatitud());
        Assert.assertEquals(resultEntity.getLongitud(), entity.getLongitud());
        Assert.assertEquals(resultEntity.getPosicion(), entity.getPosicion());
    }

    /**
     * Prueba para actualizar un Punto.
     */
    @Test
    public void updatePuntoTest() {
        PuntoEntity entity = data.get(0);
        PuntoEntity pojoEntity = factory.manufacturePojo(PuntoEntity.class);
        pojoEntity.setId(entity.getId());
        pojoEntity.setLatitud(4.603028);
        pojoEntity.setLongitud(-74.065309);
        try
        {
            puntoLogic.updatePunto(pojoEntity.getId(), pojoEntity);
        }
        catch (BusinessLogicException ex)
        {
            Assert.fail("No debería lanzar excepción");
        }
        PuntoEntity resp = em.find(PuntoEntity.class, entity.getId());
        Assert.assertEquals(pojoEntity.getId(), resp.getId());
        Assert.assertEquals(pojoEntity.getLatitud(), resp.getLatitud());
        Assert.assertEquals(pojoEntity.getLongitud(), resp.getLongitud());
        Assert.assertEquals(pojoEntity.getPosicion(), resp.getPosicion());
        PuntoEntity nonEntity = factory.manufacturePojo(PuntoEntity.class);
        nonEntity.setLatitud(0.0);
        nonEntity.setLongitud(0.0);
        try
        {
            puntoLogic.createPunto(nonEntity);
            Assert.fail("Debería lanzar excepción");
        }
        catch(BusinessLogicException b){}
    }
    
    /**
     * Prueba para update un Punto.
     * 
     */
    @Test
    public void updatePuntoConLatitudInvalidaTest()
    {
        PuntoEntity entity = data.get(0);
        PuntoEntity nonEntity = factory.manufacturePojo(PuntoEntity.class);
        nonEntity.setId(entity.getId());
        nonEntity.setLatitud(0.0);
        nonEntity.setLongitud(-74.065309);
        try
        {
            puntoLogic.updatePunto(nonEntity.getId(), nonEntity);
            Assert.fail("Debería lanzar excepción");
        }
        catch(BusinessLogicException b){}
        nonEntity.setLatitud(100.0);
        try
        {
            puntoLogic.updatePunto(nonEntity.getId(), nonEntity);
            Assert.fail("Debería lanzar excepción");
        }
        catch(BusinessLogicException b){}
    }
    
    /**
     * Prueba para update un Punto.
     * 
     */
    @Test
    public void updatePuntoConLongitudInvalidaTest()
    {
        PuntoEntity entity = data.get(0);
        PuntoEntity nonEntity = factory.manufacturePojo(PuntoEntity.class);
        nonEntity.setId(entity.getId());
        nonEntity.setLatitud(4.603028);
        nonEntity.setLongitud(0.0);
        try
        {
            puntoLogic.updatePunto(nonEntity.getId(), nonEntity);
            Assert.fail("Debería lanzar excepción");
        }
        catch(BusinessLogicException b){}
        nonEntity.setLongitud(-100.0);
        try
        {
            puntoLogic.updatePunto(nonEntity.getId(), nonEntity);
            Assert.fail("Debería lanzar excepción");
        }
        catch(BusinessLogicException b){}
    }

    /**
     * Prueba para add un ContratoPaseo
     */
    @Test
    public void addContratoPaseoTest() throws BusinessLogicException {
        PuntoEntity entity = data.get(0);
        ContratoPaseoEntity contratoPaseoEntity = factory.manufacturePojo(ContratoPaseoEntity.class);
        contratoPaseoEntity = contratoPaseoLogic.createContratoPaseo(contratoPaseoEntity);
        try
        {
            PuntoEntity punto = puntoLogic.addContratoPaseo(entity.getId(), contratoPaseoEntity.getId());
            Assert.assertEquals(contratoPaseoEntity.getId(), punto.getContratoPaseo().getId());
            puntoLogic.removeContratoPaseo(entity.getId());
        }
        catch(BusinessLogicException ble)
        {
            Assert.fail("No debería mandar excepcion");
        }
        contratoPaseoLogic.deleteContratoPaseo(contratoPaseoEntity.getId());
        try
        {
            puntoLogic.addContratoPaseo(data.get(1).getId(), contratoPaseoEntity.getId());
            Assert.fail("Debería mandar excepcion ya que el ContratoPaseo no existe");
        }
        catch(BusinessLogicException ble){}
        try
        {
            PuntoEntity non = factory.manufacturePojo(PuntoEntity.class);
            puntoLogic.addContratoPaseo(non.getId(), contratoPaseoEntity.getId());
            Assert.fail("Debería mandar excepcion ya que el punto no existe");
        }
        catch(BusinessLogicException ble){}
    }
    
    /**
     * Prueba para get un ContratoPaseo
     */
    @Test()
    public void getContratoPaseoTest() {
        PuntoEntity toGetEntity = data.get(0);
        ContratoPaseoEntity getCPE = factory.manufacturePojo(ContratoPaseoEntity.class);
        getCPE = contratoPaseoLogic.createContratoPaseo(getCPE);
        try
        {
            puntoLogic.addContratoPaseo(toGetEntity.getId(), getCPE.getId());
            Assert.assertEquals(getCPE.getId(), puntoLogic.getContratoPaseo(toGetEntity.getId()).getId());
        }
        catch(BusinessLogicException ble)
        {
            Assert.fail("No debería mandar excepcion");
        }
        try
        {
            puntoLogic.removeContratoPaseo(toGetEntity.getId());
            Assert.assertNull(puntoLogic.getContratoPaseo(toGetEntity.getId()));
        }
        catch(BusinessLogicException ble){}
        try
        {
            PuntoEntity nonEntity = factory.manufacturePojo(PuntoEntity.class);
            puntoLogic.getContratoPaseo(nonEntity.getId());
            Assert.fail("Debería mandar excepcion ya que el punto no existe");
        }
        catch(BusinessLogicException ble){}
    }
    
    /**
     * Prueba para update un ContratoPaseo
     */
    @Test
    public void updateContratoPaseoTest() {
        PuntoEntity toUpdateEntity = data.get(0);
        ContratoPaseoEntity updateCPE = factory.manufacturePojo(ContratoPaseoEntity.class);
        updateCPE = contratoPaseoLogic.createContratoPaseo(updateCPE);
        try
        {
            PuntoEntity punto = puntoLogic.updateContratoPaseo(toUpdateEntity.getId(), updateCPE.getId());
            Assert.assertEquals(updateCPE.getId(), punto.getContratoPaseo().getId());
            puntoLogic.removeContratoPaseo(toUpdateEntity.getId());
        }
        catch(BusinessLogicException ble)
        {
            Assert.fail("No debería mandar excepcion");
        }
        contratoPaseoLogic.deleteContratoPaseo(updateCPE.getId());
        try
        {
            puntoLogic.updateContratoPaseo(data.get(1).getId(), updateCPE.getId());
            Assert.fail("Debería mandar excepcion ya que el ContratoPaseo no existe");
        }
        catch(BusinessLogicException ble){}
        try
        {
            PuntoEntity nonEntity = factory.manufacturePojo(PuntoEntity.class);
            puntoLogic.updateContratoPaseo(nonEntity.getId(), updateCPE.getId());
            Assert.fail("Debería mandar excepcion ya que el punto no existe");
        }
        catch(BusinessLogicException ble){}
    }
    
    /**
     * Prueba para remove un ContratoPaseo
     */
    @Test()
    public void removeContratoPaseoTest() {
        PuntoEntity toDoEntity = data.get(0);
        ContratoPaseoEntity deleteCPE = factory.manufacturePojo(ContratoPaseoEntity.class);
        deleteCPE = contratoPaseoLogic.createContratoPaseo(deleteCPE);
        try
        {
            puntoLogic.addContratoPaseo(toDoEntity.getId(), deleteCPE.getId());
            puntoLogic.removeContratoPaseo(toDoEntity.getId());
            Assert.assertNull(puntoLogic.getContratoPaseo(toDoEntity.getId()));
        }
        catch(BusinessLogicException ble)
        {
            Assert.fail("No debería mandar excepcion");
        }
        try
        {
            PuntoEntity nonEntity = factory.manufacturePojo(PuntoEntity.class);
            puntoLogic.getContratoPaseo(nonEntity.getId());
            Assert.fail("Debería mandar excepcion ya que el punto no existe");
        }
        catch(BusinessLogicException ble){}
    }
        
    /**
     * Prueba para eliminar un Punto.
     *
     */
    @Test()
    public void deletePuntoTest() {
        PuntoEntity entity = data.get(2);
        long id = entity.getId();
        puntoLogic.deletePunto(entity.getId());
        Assert.assertNull(puntoLogic.getPunto(id));
    }

}

