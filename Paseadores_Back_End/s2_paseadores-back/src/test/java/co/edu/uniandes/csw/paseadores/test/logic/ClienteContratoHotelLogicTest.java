/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.logic;

import co.edu.uniandes.csw.paseadores.ejb.ClienteContratoHotelLogic;
import co.edu.uniandes.csw.paseadores.ejb.ClienteLogic;
import co.edu.uniandes.csw.paseadores.entities.ClienteEntity;
import co.edu.uniandes.csw.paseadores.entities.ContratoHotelEntity;
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
public class ClienteContratoHotelLogicTest {
     
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject 
    private ClienteContratoHotelLogic clienteContratoHotelLogic;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ClienteEntity> data = new ArrayList<ClienteEntity>();

    private List<ContratoHotelEntity> contratoHotelData = new ArrayList();
    
    
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ClienteEntity.class.getPackage())
                .addPackage(ClienteContratoHotelLogic.class.getPackage())
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

   
    private void clearData() {
        
        em.createQuery("delete from ContratoHotelEntity").executeUpdate();
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }

    
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ContratoHotelEntity contratosHotel = factory.manufacturePojo(ContratoHotelEntity.class);
            em.persist(contratosHotel);
            contratoHotelData.add(contratosHotel);
        }
        for (int i = 0; i < 3; i++) {
            ClienteEntity entity = factory.manufacturePojo(ClienteEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                contratoHotelData.get(i).setCliente(entity);
            }
        }
    }
    
    @Test
    public void addClienteTest() {
        ClienteEntity entity = data.get(0);
        ContratoHotelEntity contratoHotelEntity = contratoHotelData.get(1);
        ClienteEntity response = clienteContratoHotelLogic.addCliente(entity.getId(), contratoHotelEntity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(entity.getId(), response.getId());
    }
    
    @Test
    public void getClienteTest() {
        ContratoHotelEntity entity = contratoHotelData.get(0);
        ClienteEntity resultEntity = clienteContratoHotelLogic.getCliente(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getCliente().getId(), resultEntity.getId());
    }
    
    @Test
    public void replaceClienteTest() {
        ClienteEntity clienteEntity = data.get(0);
        clienteContratoHotelLogic.replaceCliente(clienteEntity.getId(),contratoHotelData.get(1).getId());
        clienteEntity = clienteContratoHotelLogic.getCliente(contratoHotelData.get(1).getId());
        Assert.assertTrue(clienteEntity.getContratosHotel().contains(contratoHotelData.get(1)));
    }
    
    @Test
    public void removeContratoHotelTest(){
        clienteContratoHotelLogic.removeContratoHotel(contratoHotelData.get(0).getId());
        Assert.assertNull(clienteContratoHotelLogic.getCliente(contratoHotelData.get(0).getId()));
    }
    
}
