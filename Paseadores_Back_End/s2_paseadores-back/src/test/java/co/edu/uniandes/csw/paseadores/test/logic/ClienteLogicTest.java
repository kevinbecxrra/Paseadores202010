/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.logic;

import co.edu.uniandes.csw.paseadores.ejb.ClienteLogic;
import co.edu.uniandes.csw.paseadores.entities.ClienteEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.ClientePersistence;
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
 * @author Edgar Camilo Diaz Suarez
 */
@RunWith(Arquillian.class)
public class ClienteLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private ClienteLogic clienteLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ClienteEntity> data = new ArrayList<ClienteEntity>();

    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ClienteEntity.class.getPackage())
                .addPackage(ClienteLogic.class.getPackage())
                .addPackage(ClientePersistence.class.getPackage())
                .addAsManifestResource("META-INF/persistence.xml", "persistence.xml")
                .addAsManifestResource("META-INF/beans.xml", "beans.xml");
    }
    
    
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
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ClienteEntity entity = factory.manufacturePojo(ClienteEntity.class);
            em.persist(entity);
            data.add(entity);
        }
    }

    @Test
    public void createClienteTest() throws BusinessLogicException { 
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        ClienteEntity result = clienteLogic.createCliente(newEntity);
        Assert.assertNotNull(result);
        ClienteEntity entity = em.find(ClienteEntity.class, result.getId());
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getCorreo(),entity.getCorreo());
        Assert.assertEquals(newEntity.getIdentificacion(), entity.getIdentificacion());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getTelefono(), entity.getTelefono());
    }
    
    @Test(expected = BusinessLogicException.class)
    public void createClienteConIdentificacionRepetida() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setIdentificacion(data.get(0).getIdentificacion());
        clienteLogic.createCliente(newEntity);
    }

    @Test(expected = BusinessLogicException.class)
    public void createClienteConCorreoRepetido() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setCorreo(data.get(0).getCorreo());
        clienteLogic.createCliente(newEntity);
    }
    
    @Test
    public void getClienteTest() {
        ClienteEntity entity = data.get(0);
        ClienteEntity resultEntity = clienteLogic.getCliente(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(resultEntity.getId(), entity.getId());
        Assert.assertEquals(resultEntity.getCorreo(),entity.getCorreo());
        Assert.assertEquals(resultEntity.getIdentificacion(), entity.getIdentificacion());
        Assert.assertEquals(resultEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(resultEntity.getTelefono(), entity.getTelefono());
    }
    
    @Test
    public void getClientesTest() {
        List<ClienteEntity> list = clienteLogic.getClientes();
        Assert.assertEquals(data.size(), list.size());
        for (ClienteEntity entity : list) {
            boolean found = false;
            for (ClienteEntity storedEntity : data) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                    Assert.assertEquals(storedEntity.getCorreo(),entity.getCorreo());
                    Assert.assertEquals(storedEntity.getIdentificacion(), entity.getIdentificacion());
                    Assert.assertEquals(storedEntity.getNombre(), entity.getNombre());
                    Assert.assertEquals(storedEntity.getTelefono(), entity.getTelefono());
                }
            }
            Assert.assertTrue(found);
        }
       }
    @Test
    public void getClienteByCorreoTest(){
         
        ClienteEntity entity = data.get(0);
        ClienteEntity resultEntity = clienteLogic.getClienteByCorreo(entity.getCorreo());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(resultEntity.getId(), entity.getId());
        Assert.assertEquals(resultEntity.getCorreo(), entity.getCorreo());
        Assert.assertEquals(resultEntity.getIdentificacion(), entity.getIdentificacion());
        Assert.assertEquals(resultEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(resultEntity.getTelefono(), entity.getTelefono());
 
     }
    
    @Test
    public void getClienteByIdentificacionTest(){
         
        ClienteEntity entity = data.get(0);
        ClienteEntity resultEntity = clienteLogic.getClienteByIdentificacion(entity.getIdentificacion());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(resultEntity.getId(), entity.getId());
        Assert.assertEquals(resultEntity.getCorreo(), entity.getCorreo());
        Assert.assertEquals(resultEntity.getIdentificacion(), entity.getIdentificacion());
        Assert.assertEquals(resultEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(resultEntity.getTelefono(), entity.getTelefono());
 
    }
    
  
     @Test
    public void updateClienteTest() {
        ClienteEntity entity = data.get(0);
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);

        newEntity.setId(entity.getId());
        newEntity.setCorreo(entity.getCorreo());
        newEntity.setIdentificacion(entity.getIdentificacion());
        newEntity.setNombre(entity.getNombre());
        newEntity.setTelefono(entity.getTelefono());
       try{
        clienteLogic.updateCliente(newEntity);
       }
       catch(BusinessLogicException a)
       { 
       }
        ClienteEntity resp = em.find(ClienteEntity.class, entity.getId());

        Assert.assertEquals(resp.getNombre(), newEntity.getNombre());
        Assert.assertEquals(resp.getIdentificacion(), newEntity.getIdentificacion());
        Assert.assertEquals(resp.getCorreo(), newEntity.getCorreo());
        Assert.assertEquals(resp.getTelefono(), newEntity.getTelefono());
    }
    
    @Test(expected = BusinessLogicException.class)
    public void updateClienteConIdentificacionRepetida() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setIdentificacion(data.get(0).getIdentificacion());
        newEntity.setId(data.get(1).getId());
        clienteLogic.updateCliente( newEntity);
    }
    
    @Test(expected = BusinessLogicException.class)
    public void updatePaseadorConCorreoRepetida() throws BusinessLogicException {
        ClienteEntity newEntity = factory.manufacturePojo(ClienteEntity.class);
        newEntity.setCorreo(data.get(0).getCorreo());
        newEntity.setId(data.get(1).getId());
        clienteLogic.updateCliente(newEntity);
    }
    
    @Test
    public void deleteClienteTest() {
        ClienteEntity entity = data.get(1);
        clienteLogic.deleteCliente(entity.getId());
        ClienteEntity deleted = em.find(ClienteEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}

