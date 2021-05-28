/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.logic;


import co.edu.uniandes.csw.paseadores.ejb.ClientePerroLogic;
import co.edu.uniandes.csw.paseadores.entities.ClienteEntity;
import co.edu.uniandes.csw.paseadores.entities.PerroEntity;
import co.edu.uniandes.csw.paseadores.persistence.ClientePersistence;
import javax.inject.Inject;
import org.jboss.arquillian.junit.Arquillian;
import org.junit.runner.RunWith;
import java.util.ArrayList;
import java.util.List;
import javax.inject.Inject;
import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.transaction.UserTransaction;
import static org.glassfish.pfl.basic.tools.argparser.ElementParser.factory;
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
 * @author julian Oliveros<je.oliverosf at uniandes.edu.co>
 */
@RunWith(Arquillian.class)
public class ClientePerroLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private ClientePerroLogic perroClienteLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<ClienteEntity> data = new ArrayList<ClienteEntity>();

    private List<PerroEntity> perrosData = new ArrayList();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(ClienteEntity.class.getPackage())
                .addPackage(ClientePerroLogic.class.getPackage())
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
        em.createQuery("delete from PerroEntity").executeUpdate();
        em.createQuery("delete from ClienteEntity").executeUpdate();
    }

    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            PerroEntity perros = factory.manufacturePojo(PerroEntity.class);
            em.persist(perros);
            perrosData.add(perros);
        }
        for (int i = 0; i < 3; i++) {
            ClienteEntity entity = factory.manufacturePojo(ClienteEntity.class);
            em.persist(entity);
            data.add(entity);
            if (i == 0) {
                perrosData.get(i).setCliente(entity); 
            }
        }
    }

    /**
     * Prueba para asociar un Paseos existente a un Recorrido.
     */
    @Test
    public void addCleinteTest() {
        ClienteEntity entity = data.get(0);
        PerroEntity paseoEntity = perrosData.get(1);
        ClienteEntity response = perroClienteLogic.addCliente(entity.getId(),  paseoEntity.getId());

        Assert.assertNotNull(response);
        Assert.assertEquals(entity.getId(), response.getId());
    }

    /**
     * Prueba para consultar un Recorrido.
     */
    @Test
    public void getClienteTest() {
        PerroEntity entity = perrosData.get(0);
        ClienteEntity resultEntity = perroClienteLogic.getCliente(entity.getId());
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
        perroClienteLogic.replaceCliente(perrosData.get(1).getId(), entity.getId());
        entity = perroClienteLogic.getCliente(perrosData.get(1).getId());
        Assert.assertTrue(entity.getPerros().contains(perrosData.get(1)));
    }

    /**
     * Prueba para desasociar un Paseo existente de un Recorrido existente.
     *
     */
    @Test
    public void removePerroTest(){
        perroClienteLogic.removePerro(perrosData.get(0).getId());
        Assert.assertNull(perroClienteLogic.getCliente(perrosData.get(0).getId()));
    }
    
    
}
