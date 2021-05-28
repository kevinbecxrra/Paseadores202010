/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.logic;

import co.edu.uniandes.csw.paseadores.ejb.ClientePagoClienteLogic;
import co.edu.uniandes.csw.paseadores.entities.ClienteEntity;
import co.edu.uniandes.csw.paseadores.entities.PagoClienteEntity;
import co.edu.uniandes.csw.paseadores.persistence.ClientePersistence;

import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import uk.co.jemos.podam.api.PodamFactory;
import uk.co.jemos.podam.api.PodamFactoryImpl;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.JavaArchive;

/**
 *
 * @author julian Oliveros<je.oliverosf at uniandes.edu.co>
 */
@RunWith(Arquillian.class)
public class ClientePagoClienteLogicTest {
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ClientePagoClienteLogic pagoClienteClienteLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ClienteEntity> data = new ArrayList<ClienteEntity>();

    private List<PagoClienteEntity> pagosClienteData = new ArrayList();

     
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ClienteEntity.class.getPackage())
                .addPackage(ClientePagoClienteLogic.class.getPackage())
                .addPackage(ClientePersistence.class.getPackage())
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
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            PagoClienteEntity pagos = factory.manufacturePojo(PagoClienteEntity.class);
            em.persist(pagos);
            pagosClienteData.add(pagos);
        }
        for (int i = 0; i < 3; i++) {
            ClienteEntity entity = factory.manufacturePojo(ClienteEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                pagosClienteData.get(i).setCliente(entity); 
            }
        }
    }

    /**
     * Prueba para asociar un Paseos existente a un Recorrido.
     */
    @Test
    public void addCleinteTest() {
        ClienteEntity entity = data.get(0);
        PagoClienteEntity paseoEntity = pagosClienteData.get(1);
        ClienteEntity response = pagoClienteClienteLogic.addCliente(entity.getId(),  paseoEntity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(entity.getId(), response.getId());
    }

    /**
     * Prueba para consultar un Recorrido.
     */
    @Test
    public void getClienteTest() {
        PagoClienteEntity entity = pagosClienteData.get(0);
        ClienteEntity resultEntity = pagoClienteClienteLogic.getCliente(entity.getId());
        Assert.assertNotNull(resultEntity);
        Assert.assertEquals(entity.getCliente().getId(), resultEntity.getId());
    }

    /**
     * Prueba para remplazar las instancias de Paseos asociadas a una instancia
     * de Recorrido.
     */
    @Test
    public void replaceClienteTest() {
        ClienteEntity entity = data.get(0);
        pagoClienteClienteLogic.replaceCliente(pagosClienteData.get(1).getId(), entity.getId());
        entity = pagoClienteClienteLogic.getCliente(pagosClienteData.get(1).getId());
        Assert.assertTrue(entity.getPagos().contains(pagosClienteData.get(1)));
    }

    /**
     * Prueba para desasociar un Paseo existente de un Recorrido existente.
     *
     */
    @Test
    public void removePagoClienteTest(){
        pagoClienteClienteLogic.removePagoCliente(pagosClienteData.get(0).getId());
        Assert.assertNull(pagoClienteClienteLogic.getCliente(pagosClienteData.get(0).getId()));
    }
    
}
