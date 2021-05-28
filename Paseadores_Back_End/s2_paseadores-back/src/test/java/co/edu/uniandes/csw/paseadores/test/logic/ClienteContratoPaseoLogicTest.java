/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.logic;

import co.edu.uniandes.csw.paseadores.ejb.ClienteContratoPaseoLogic;
import co.edu.uniandes.csw.paseadores.entities.ContratoPaseoEntity;
import co.edu.uniandes.csw.paseadores.entities.ClienteEntity;
import co.edu.uniandes.csw.paseadores.persistence.ClientePersistence;
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
 * @author Daniel Mateo Guatibonza Solano
 */
@RunWith(Arquillian.class)
public class ClienteContratoPaseoLogicTest {
        
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private ClienteContratoPaseoLogic clienteContratoPaseoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ClienteEntity> clientesData = new ArrayList<ClienteEntity>();

    private List<ContratoPaseoEntity> contratoPaseosData = new ArrayList();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ClienteEntity.class.getPackage())
                .addPackage(ClienteContratoPaseoLogic.class.getPackage())
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
        em.createQuery("delete from ContratoPaseoEntity").executeUpdate();
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ContratoPaseoEntity contratoPaseo = factory.manufacturePojo(ContratoPaseoEntity.class);
            em.persist(contratoPaseo);
            contratoPaseosData.add(contratoPaseo);
        }
        for (int i = 0; i < 3; i++) {
            ClienteEntity cliente = factory.manufacturePojo(ClienteEntity.class);
            em.persist(cliente);
            clientesData.add(cliente);
            if (i == 0) {
                contratoPaseosData.get(i).setCliente(cliente);
            }
        }
    }

    /**
     * Prueba para asociar un ContratoPaseos existente a un Cliente.
     */
    @Test
    public void addClienteTest() {
        ClienteEntity cliente = clientesData.get(0);
        ContratoPaseoEntity contratoPaseoEntity = contratoPaseosData.get(1);
        ClienteEntity clienteAgregado = clienteContratoPaseoLogic.addCliente(cliente.getId(), contratoPaseoEntity.getId());

        Assert.assertNotNull(clienteAgregado);
        Assert.assertEquals(cliente.getId(), clienteAgregado.getId());
    }

    /**
     * Prueba para consultar un Cliente.
     */
    @Test
    public void getClienteTest() {
        ContratoPaseoEntity contratoPaseo = contratoPaseosData.get(0);
        ClienteEntity clienteBuscado = clienteContratoPaseoLogic.getCliente(contratoPaseo.getId());
        Assert.assertNotNull(clienteBuscado);
        Assert.assertEquals(contratoPaseo.getCliente().getId(), clienteBuscado.getId());
    }

    /**
     * Prueba para remplazar las instancias de ContratoPaseos asociadas a una instancia
     * de Cliente.
     */
    @Test
    public void replaceClienteTest() {
        ClienteEntity clienteEntity = clientesData.get(0);
        clienteContratoPaseoLogic.replaceCliente(clienteEntity.getId(),contratoPaseosData.get(1).getId());
        clienteEntity = clienteContratoPaseoLogic.getCliente(contratoPaseosData.get(1).getId());
        Assert.assertTrue(clienteEntity.getContratosPaseo().contains(contratoPaseosData.get(1)));
    }

    /**
     * Prueba para desasociar un ContratoPaseo existente de un Cliente existente.
     *
     */
    @Test
    public void removeContratoPaseoTest(){
        clienteContratoPaseoLogic.removeContratoPaseo(contratoPaseosData.get(0).getId());
        Assert.assertNull(clienteContratoPaseoLogic.getCliente(contratoPaseosData.get(0).getId()));
    }

}


