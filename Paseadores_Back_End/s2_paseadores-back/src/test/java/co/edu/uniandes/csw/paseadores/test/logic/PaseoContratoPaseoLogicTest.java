/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.logic;

import co.edu.uniandes.csw.paseadores.ejb.PaseoContratoPaseoLogic;
import co.edu.uniandes.csw.paseadores.entities.ContratoPaseoEntity;
import co.edu.uniandes.csw.paseadores.entities.PaseoEntity;
import co.edu.uniandes.csw.paseadores.persistence.PaseoPersistence;
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
public class PaseoContratoPaseoLogicTest {
        
    private PodamFactory factory = new PodamFactoryImpl();

    @Inject
    private PaseoContratoPaseoLogic paseoContratoPaseoLogic;

    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;

    private List<PaseoEntity> paseosData = new ArrayList<PaseoEntity>();

    private List<ContratoPaseoEntity> contratoPaseosData = new ArrayList();

    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PaseoEntity.class.getPackage())
                .addPackage(PaseoContratoPaseoLogic.class.getPackage())
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
        em.createQuery("delete from ContratoPaseoEntity").executeUpdate();
        em.createQuery("delete from PaseoEntity").executeUpdate();
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
            PaseoEntity paseo = factory.manufacturePojo(PaseoEntity.class);
            em.persist(paseo);
            paseosData.add(paseo);
            if (i == 0) {
                contratoPaseosData.get(i).setPaseo(paseo);
            }
        }
    }

    /**
     * Prueba para asociar un ContratoPaseos existente a un Paseo.
     */
    @Test
    public void addPaseoTest() {
        PaseoEntity paseo = paseosData.get(0);
        ContratoPaseoEntity contratoPaseoEntity = contratoPaseosData.get(1);
        PaseoEntity paseoAgregado = paseoContratoPaseoLogic.addPaseo(paseo.getId(), contratoPaseoEntity.getId());

        Assert.assertNotNull(paseoAgregado);
        Assert.assertEquals(paseo.getId(), paseoAgregado.getId());
    }

    /**
     * Prueba para consultar un Paseo.
     */
    @Test
    public void getPaseoTest() {
        ContratoPaseoEntity contratoPaseo = contratoPaseosData.get(0);
        PaseoEntity paseoBuscado = paseoContratoPaseoLogic.getPaseo(contratoPaseo.getId());
        Assert.assertNotNull(paseoBuscado);
        Assert.assertEquals(contratoPaseo.getPaseo().getId(), paseoBuscado.getId());
    }

    /**
     * Prueba para remplazar las instancias de ContratoPaseos asociadas a una instancia
     * de Paseo.
     */
    @Test
    public void replacePaseoTest() {
        PaseoEntity paseoEntity = paseosData.get(0);
        paseoContratoPaseoLogic.replacePaseo(paseoEntity.getId(),contratoPaseosData.get(1).getId());
        paseoEntity = paseoContratoPaseoLogic.getPaseo(contratoPaseosData.get(1).getId());
        Assert.assertTrue(paseoEntity.getContratosPaseo().contains(contratoPaseosData.get(1)));
    }

    /**
     * Prueba para desasociar un ContratoPaseo existente de un Paseo existente.
     *
     */
    @Test
    public void removeContratoPaseoTest(){
        paseoContratoPaseoLogic.removeContratoPaseo(contratoPaseosData.get(0).getId());
        Assert.assertNull(paseoContratoPaseoLogic.getPaseo(contratoPaseosData.get(0).getId()));
    }

}


