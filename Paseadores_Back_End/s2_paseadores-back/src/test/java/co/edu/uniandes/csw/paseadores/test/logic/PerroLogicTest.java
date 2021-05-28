/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.logic;

import co.edu.uniandes.csw.paseadores.ejb.PerroLogic;
import co.edu.uniandes.csw.paseadores.entities.ClienteEntity;
import co.edu.uniandes.csw.paseadores.entities.ContratoHotelEntity;
import co.edu.uniandes.csw.paseadores.entities.ContratoPaseoEntity;
import co.edu.uniandes.csw.paseadores.entities.PerroEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.PerroPersistence;
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
public class PerroLogicTest {
    
    
    private PodamFactory factory = new PodamFactoryImpl();
     
    @Inject
    private PerroLogic perroLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;

    private  List<PerroEntity> Perrodata = new ArrayList<PerroEntity>();
    
    private  List<ClienteEntity> ClienteData = new ArrayList();
   
    private  List<ContratoPaseoEntity> contratoPaseoData = new ArrayList();
    
    private  List<ContratoHotelEntity> contratoHotelData = new ArrayList();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PerroEntity.class.getPackage())
                .addPackage(PerroLogic.class.getPackage())
                .addPackage(PerroPersistence.class.getPackage())
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
        em.createQuery("delete from PerroEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        
        for (int i = 0; i < 3; i++) {
            ClienteEntity editorial = factory.manufacturePojo(ClienteEntity.class);
            em.persist(editorial);
            ClienteData.add(editorial);
        }
        
        for (int i = 0; i < 3; i++) {
            ContratoPaseoEntity paseo = factory.manufacturePojo(ContratoPaseoEntity.class);
            em.persist(paseo);
            contratoPaseoData.add(paseo);
        }
        
                for (int i = 0; i < 3; i++) {
            ContratoHotelEntity editorial = factory.manufacturePojo(ContratoHotelEntity.class);
            em.persist(editorial);
            contratoHotelData.add(editorial);
        }
               
        for (int i = 0; i < 3; i++) {
            PerroEntity perro = factory.manufacturePojo(PerroEntity.class);
            //perro.setCliente(ClienteData.get(0));
            //perro.setEstadias((List<ContratoHotelEntity>) contratoHotelData.get(0));
            //perro.setPaseos((List<ContratoPaseoEntity>) contratoPaseoData.get(0));
            em.persist(perro);
            Perrodata.add(perro);
        }

    }
    
    
    /**
     * Prueba para crear un Perro con todo bien
     *
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    @Test
    public void createPerroTest() throws BusinessLogicException {
        
        PerroEntity newEntity = factory.manufacturePojo(PerroEntity.class);
        int ed = newEntity.getEdad();
        
        if(ed<0){
            newEntity.setEdad(ed*-1);
        }
        
        PerroEntity result = perroLogic.createPerro(newEntity);
        int edad =result.getEdad();
         if(edad<0)
        {
            result.setEdad(edad*-1);
        }
        


        
        Assert.assertNotNull(result);
        PerroEntity entity = em.find(PerroEntity.class, result.getId());
        int eda =entity.getEdad();
        if(eda<0)
        {
            entity.setEdad(eda*-1);
        }
        
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getIdPerro(), entity.getIdPerro());
        Assert.assertEquals(newEntity.getNombre(), entity.getNombre());
        Assert.assertEquals(newEntity.getEdad(), entity.getEdad());
        Assert.assertEquals(newEntity.getRaza(), entity.getRaza());
    }
    
     /**
     * Prueba para crear un perro con edad inválido(menor a 0)
     *
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createPerroTestConEdadInvalido() throws BusinessLogicException {
        PerroEntity newEntity = factory.manufacturePojo(PerroEntity.class);
        //newEntity.setEditorial(editorialData.get(0));
        newEntity.setEdad(-19);
        perroLogic.createPerro(newEntity);
        //Assert.assertEquals(BusinessLogicException.class, BusinessLogicException.class);
    }
    
    /**
     * Prueba para crear un perro con idPerro inválido(idPerro que ya existe)
     *
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createPerroTestConIdPerroExistente() throws BusinessLogicException {
        PerroEntity newEntity = factory.manufacturePojo(PerroEntity.class);
        //newEntity.setEditorial(editorialData.get(0));
        newEntity.setIdPerro(Perrodata.get(0).getIdPerro());
        perroLogic.createPerro(newEntity);
        
        //Assert.assertEquals(BusinessLogicException.class, BusinessLogicException.class);
    }

    /**
     * Prueba para consultar la lista de Perros.
     */
    @Test
    public void getPerrosTest() {
        List<PerroEntity> list = perroLogic.getPerros();
        Assert.assertEquals(Perrodata.size(), list.size());
        for (PerroEntity entity : list) {
            boolean found = false;
            for (PerroEntity storedEntity : Perrodata) {
                if (entity.getId().equals(storedEntity.getId())) {
                    found = true;
                }
            }
            Assert.assertTrue(found);
        }
    }
    
    
    /**
     * Prueba para consultar un PerroEntity.
     */
    @Test
    public void getPerroByIDPerroTest() {
        PerroEntity entity = Perrodata.get(0);
        
        PerroEntity resultEntity = perroLogic.getPerroByIDPerro(entity.getIdPerro());
        Assert.assertNotNull(resultEntity);
        
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getIdPerro(), resultEntity.getIdPerro());
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
        Assert.assertEquals(entity.getEdad(), resultEntity.getEdad());
        Assert.assertEquals(entity.getRaza(), resultEntity.getRaza());
    }
    
    /**
     * Prueba para consultar un Perro.
     */
    @Test
    public void getPerroTest() {
        
        PerroEntity entity = Perrodata.get(0);
        PerroEntity resultEntity = perroLogic.getPerro(entity.getId());
        Assert.assertNotNull(resultEntity);
        
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getIdPerro(), resultEntity.getIdPerro());
        Assert.assertEquals(entity.getNombre(), resultEntity.getNombre());
        Assert.assertEquals(entity.getEdad(), resultEntity.getEdad());
        Assert.assertEquals(entity.getRaza(), resultEntity.getRaza());
    }
    
    
    /**
     * Prueba para actualizar un Perro con todo bien
     *
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    @Test
    public void UpdatePerroTest() throws BusinessLogicException {

        PerroEntity entity = Perrodata.get(0);
        int edada =entity.getEdad();
        if(edada<0)
        {
            entity.setEdad(edada*-1);
        }
        
        PerroEntity newEntity = factory.manufacturePojo(PerroEntity.class);
        
        int e = newEntity.getEdad();
        if(e<0)
        {
            newEntity.setEdad(e*-1);
        }
        
        newEntity.setId(entity.getId());
        perroLogic.updatePerro(newEntity.getId(), newEntity);
        
        
        PerroEntity resp = em.find(PerroEntity.class, entity.getId());
        
        int eda =resp.getEdad();
        if(eda<0)
        {
            resp.setEdad(eda*-1);
        }
        
        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getIdPerro(), resp.getIdPerro());
        Assert.assertEquals(newEntity.getNombre(), resp.getNombre());
        Assert.assertEquals(newEntity.getEdad(), resp.getEdad());
        Assert.assertEquals(newEntity.getRaza(), resp.getRaza());
    }
    
     /**
     * Prueba para actualizar un perro con edad inválido(menor a 0)
     *
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updatePerroTestConEdadInvalido() throws BusinessLogicException {
        
        PerroEntity entity = Perrodata.get(0);
        PerroEntity newEntity = factory.manufacturePojo(PerroEntity.class);
        newEntity.setEdad(-19);
        newEntity.setId(entity.getId());
        perroLogic.updatePerro(newEntity.getId(), newEntity);
    }
    /**
     * Prueba para actualizar un perro con idPerro inválido(idPerro que ya existe.
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void UpdatePerroTestConIdPerroExistente() throws BusinessLogicException {
        
        PerroEntity entity = Perrodata.get(0);
        
        PerroEntity newEntity = factory.manufacturePojo(PerroEntity.class);
        newEntity.setId(entity.getId());
        
        newEntity.setIdPerro(Perrodata.get(1).getIdPerro());
        perroLogic.updatePerro(newEntity.getId(),newEntity);
        
        
    }
    
    /**
     * Prueba para eliminar un Perro
     */
    @Test
    public void deletePerroTest() {
        PerroEntity entity = Perrodata.get(2);
        perroLogic.deletePerro(entity.getId());
        PerroEntity deleted = em.find(PerroEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
    
}
