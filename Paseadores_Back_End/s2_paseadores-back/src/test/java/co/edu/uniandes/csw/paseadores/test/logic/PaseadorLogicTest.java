/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.logic;

import co.edu.uniandes.csw.paseadores.ejb.PaseadorLogic;
import co.edu.uniandes.csw.paseadores.entities.PaseadorEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.PaseadorPersistence;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import org.junit.Before;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;

/**
 *
 * @author Alvaro Plata
 */
@RunWith(Arquillian.class)
public class PaseadorLogicTest {

    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private PaseadorLogic paseadorLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<PaseadorEntity> data = new ArrayList<PaseadorEntity>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PaseadorEntity.class.getPackage())
                .addPackage(PaseadorLogic.class.getPackage())
                .addPackage(PaseadorPersistence.class.getPackage())
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
        em.createQuery("delete from PaseadorEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            PaseadorEntity entity = factory.manufacturePojo(PaseadorEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }
    
    /**
     * Prueba para crear un Paseador.
     *
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException 
     */
    @Test
    public void createPaseadorTest(){
        try{
        PaseadorEntity newEntity = factory.manufacturePojo(PaseadorEntity.class);
        PaseadorEntity result = paseadorLogic.createPaseador(newEntity);
        Assert.assertNotNull(result);
        PaseadorEntity entity = em.find(PaseadorEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getIdentificacion(), entity.getIdentificacion());
        Assert.assertEquals(newEntity.getEps(), entity.getEps());
        Assert.assertEquals(newEntity.getArl(), entity.getArl());
        Assert.assertEquals(newEntity.getCorreo(), entity.getCorreo());
        Assert.assertEquals(newEntity.getTelefono(), entity.getTelefono());
        Assert.assertEquals(newEntity.getCalificacionGlobal(), entity.getCalificacionGlobal());
        Assert.assertEquals(newEntity.getCuentaBancaria(), entity.getCuentaBancaria());
        Assert.assertEquals(newEntity.getNumeroCalificaciones(), entity.getNumeroCalificaciones());
        }
        catch(BusinessLogicException ex){
            Assert.fail(ex.getMessage());
        }
    }
    
    /**
     * Prueba para crear un Paseador con Identificación repetida
     *
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createPaseadorConIdentificacionRepetida() throws BusinessLogicException {
        PaseadorEntity newEntity = factory.manufacturePojo(PaseadorEntity.class);
        newEntity.setIdentificacion(data.get(0).getIdentificacion());
        paseadorLogic.createPaseador(newEntity);
    }
    
    /**
     * Prueba para crear un Paseador con correo repetido
     *
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createPaseadorConCorreoRepetido() throws BusinessLogicException {
        PaseadorEntity newEntity = factory.manufacturePojo(PaseadorEntity.class);
        newEntity.setCorreo(data.get(0).getCorreo());
        paseadorLogic.createPaseador(newEntity);
    }
    
    /**
     * Prueba para crear un Paseador con una calificación incorrecta
     *
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createPaseadorConCalificacionIncorrecta1() throws BusinessLogicException{
        PaseadorEntity newEntity = factory.manufacturePojo(PaseadorEntity.class);
        newEntity.setCalificacionGlobal(-1.0);
        paseadorLogic.createPaseador(newEntity);
        
       
    }
    
    
    /**
     * Prueba para crear un Paseador con una calificación incorrecta
     *
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void createPaseadorConCalificacionIncorrecta2() throws BusinessLogicException{
        PaseadorEntity newEntity = factory.manufacturePojo(PaseadorEntity.class);
        newEntity.setCalificacionGlobal(6.0);
        paseadorLogic.createPaseador(newEntity);
        
        
    }
    
    /**
     * Prueba para consultar la lista de Paseadores.
     */
    @Test
    public void getPaseadoresTest() {
        List<PaseadorEntity> list = paseadorLogic.getPaseadores();
        Assert.assertEquals(data.size(), list.size());
        for (PaseadorEntity entity : list) {
            boolean found = false;
            for (PaseadorEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    /**
     * Prueba para consultar un Paseador.
     */
    @Test
    public void getPaseadorTest() {
        PaseadorEntity entity = data.get(0);
        PaseadorEntity result = paseadorLogic.getPaseador(entity.getId());
        
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getId(), entity.getId());
        Assert.assertEquals(result.getNombre(), entity.getNombre());
        Assert.assertEquals(result.getIdentificacion(), entity.getIdentificacion());
        Assert.assertEquals(result.getEps(), entity.getEps());
        Assert.assertEquals(result.getArl(), entity.getArl());
        Assert.assertEquals(result.getCorreo(), entity.getCorreo());
        Assert.assertEquals(result.getTelefono(), entity.getTelefono());
        Assert.assertEquals(result.getCalificacionGlobal(), entity.getCalificacionGlobal());
        Assert.assertEquals(result.getCuentaBancaria(), entity.getCuentaBancaria());
        Assert.assertEquals(result.getNumeroCalificaciones(), entity.getNumeroCalificaciones());
    }
    
    @Test
    public void getPaseadorByIdentificacionTest()
    {
        PaseadorEntity entity = data.get(0);
        PaseadorEntity result = paseadorLogic.getPaseadorByIdentificacion(entity.getIdentificacion());
        
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getId(), entity.getId());
        Assert.assertEquals(result.getNombre(), entity.getNombre());
        Assert.assertEquals(result.getIdentificacion(), entity.getIdentificacion());
        Assert.assertEquals(result.getEps(), entity.getEps());
        Assert.assertEquals(result.getArl(), entity.getArl());
        Assert.assertEquals(result.getCorreo(), entity.getCorreo());
        Assert.assertEquals(result.getTelefono(), entity.getTelefono());
        Assert.assertEquals(result.getCalificacionGlobal(), entity.getCalificacionGlobal());
        Assert.assertEquals(result.getCuentaBancaria(), entity.getCuentaBancaria());
        Assert.assertEquals(result.getNumeroCalificaciones(), entity.getNumeroCalificaciones());
    }
    
    @Test
    public void getPaseadorByCorreoTest()
    {
        PaseadorEntity entity = data.get(0);
        PaseadorEntity result = paseadorLogic.getPaseadorByCorreo(entity.getCorreo());
        
        Assert.assertNotNull(result);
        Assert.assertEquals(result.getId(), entity.getId());
        Assert.assertEquals(result.getNombre(), entity.getNombre());
        Assert.assertEquals(result.getIdentificacion(), entity.getIdentificacion());
        Assert.assertEquals(result.getEps(), entity.getEps());
        Assert.assertEquals(result.getArl(), entity.getArl());
        Assert.assertEquals(result.getCorreo(), entity.getCorreo());
        Assert.assertEquals(result.getTelefono(), entity.getTelefono());
        Assert.assertEquals(result.getCalificacionGlobal(), entity.getCalificacionGlobal());
        Assert.assertEquals(result.getCuentaBancaria(), entity.getCuentaBancaria());
        Assert.assertEquals(result.getNumeroCalificaciones(), entity.getNumeroCalificaciones());
    }
    
    @Test
    public void getPaseadorByByCalificacionGlobalInRangeTest()
    {
        Double calificacionMedia;
        Double calificacionMayor = data.get(0).getCalificacionGlobal();
        Double calificacionMenor = data.get(0).getCalificacionGlobal();
        int posicionMayor = 0; int posicionMenor = 0;
        for(int i=1; i < data.size(); i++)
        {
            if(calificacionMayor < data.get(i).getCalificacionGlobal())
            {
                calificacionMayor = data.get(i).getCalificacionGlobal();
                posicionMayor = i;
            }
            if(calificacionMenor > data.get(i).getCalificacionGlobal())
            {
                calificacionMenor = data.get(i).getCalificacionGlobal();
                posicionMenor = i;
            }
        }
        
        switch (posicionMayor+posicionMenor) {
            case 1:
                calificacionMedia = data.get(2).getCalificacionGlobal();
                break;
            case 2:
               calificacionMedia = data.get(1).getCalificacionGlobal();
                break;
            default:
                calificacionMedia = data.get(0).getCalificacionGlobal();
                break;
        }
        Assert.assertEquals(0,paseadorLogic.getPaseadorByCalificacionGlobalInRange(calificacionMedia, calificacionMenor).size());
        Assert.assertEquals(0,paseadorLogic.getPaseadorByCalificacionGlobalInRange(calificacionMayor, calificacionMedia).size());
        Assert.assertEquals(0,paseadorLogic.getPaseadorByCalificacionGlobalInRange(calificacionMayor, calificacionMenor).size());
        Assert.assertEquals(1,paseadorLogic.getPaseadorByCalificacionGlobalInRange(calificacionMenor, calificacionMenor).size());
        Assert.assertEquals(1,paseadorLogic.getPaseadorByCalificacionGlobalInRange(calificacionMedia, calificacionMedia).size());
        Assert.assertEquals(1,paseadorLogic.getPaseadorByCalificacionGlobalInRange(calificacionMayor, calificacionMayor).size());
        Assert.assertEquals(2,paseadorLogic.getPaseadorByCalificacionGlobalInRange(calificacionMenor, calificacionMedia).size());
        Assert.assertEquals(2,paseadorLogic.getPaseadorByCalificacionGlobalInRange(calificacionMedia, calificacionMayor).size());
        Assert.assertEquals(3,paseadorLogic.getPaseadorByCalificacionGlobalInRange(calificacionMenor, calificacionMayor).size());
    }
    
    /**
     * Prueba para actualizar un Paseador.
     *
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    @Test
    public void updatePaseadorTest(){
        try{
            PaseadorEntity entity = data.get(0);
            PaseadorEntity pojoEntity = factory.manufacturePojo(PaseadorEntity.class);
            pojoEntity.setId(entity.getId());
            paseadorLogic.updatePaseador(pojoEntity);
            PaseadorEntity result = em.find(PaseadorEntity.class, entity.getId());
        
            Assert.assertNotNull(result);
            Assert.assertEquals(result.getId(), pojoEntity.getId());
            Assert.assertEquals(result.getNombre(), pojoEntity.getNombre());
            Assert.assertEquals(result.getIdentificacion(), pojoEntity.getIdentificacion());
            Assert.assertEquals(result.getEps(), pojoEntity.getEps());
            Assert.assertEquals(result.getArl(), pojoEntity.getArl());
            Assert.assertEquals(result.getCorreo(), pojoEntity.getCorreo());
            Assert.assertEquals(result.getTelefono(), pojoEntity.getTelefono());
            Assert.assertEquals(result.getCalificacionGlobal(), pojoEntity.getCalificacionGlobal());
            Assert.assertEquals(result.getCuentaBancaria(), pojoEntity.getCuentaBancaria());
            Assert.assertEquals(result.getNumeroCalificaciones(), pojoEntity.getNumeroCalificaciones());
        }
        catch(BusinessLogicException ex){
            Assert.fail(ex.getMessage());
        }
    }
    
    /**
     * Prueba para actualizar un Paseador con Identificación repetida
     *
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void updatePaseadorConIdentificacionRepetida() throws BusinessLogicException{
        PaseadorEntity newEntity = factory.manufacturePojo(PaseadorEntity.class);
        newEntity.setIdentificacion(data.get(0).getIdentificacion());
        newEntity.setId(data.get(1).getId());
        paseadorLogic.updatePaseador(newEntity);
    }
    
    /**
     * Prueba para actualizar un Paseador con correo repetido
     *
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void updatePaseadorConCorreoRepetido() throws BusinessLogicException {
        PaseadorEntity newEntity = factory.manufacturePojo(PaseadorEntity.class);
        newEntity.setCorreo(data.get(0).getCorreo());
        newEntity.setId(data.get(1).getId());
        paseadorLogic.updatePaseador(newEntity);
    }
    
    /**
     * Prueba para actualizar un Paseador con una calificación incorrecta
     *
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void updatePaseadorConCalificacionIncorrecta1() throws BusinessLogicException {
        PaseadorEntity newEntity = factory.manufacturePojo(PaseadorEntity.class);
        newEntity.setCalificacionGlobal(-1.0);
        newEntity.setId(data.get(1).getId());
        paseadorLogic.updatePaseador(newEntity);
    }
    
    /**
     * Prueba para actualizar un Paseador con una calificación incorrecta
     *
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException 
     */
    @Test(expected = BusinessLogicException.class)
    public void updatePaseadorConCalificacionIncorrecta2() throws BusinessLogicException {
        PaseadorEntity newEntity = factory.manufacturePojo(PaseadorEntity.class);
        newEntity.setCalificacionGlobal(6.0);
        newEntity.setId(data.get(1).getId());
        paseadorLogic.updatePaseador(newEntity);
    }
    
    /**
     * Prueba para eliminar un Paseador.
     *
     */
    @Test()
    public void deletePaseadorTest() {
        PaseadorEntity entity = data.get(2);
        Long id = entity.getId();
        paseadorLogic.deletePaseador(entity.getId());
        Assert.assertNull(paseadorLogic.getPaseador(id));
    }
    
}
