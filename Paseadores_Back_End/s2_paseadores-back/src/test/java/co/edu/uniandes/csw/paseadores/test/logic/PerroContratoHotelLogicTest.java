/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.logic;

import co.edu.uniandes.csw.paseadores.ejb.PerroContratoHotelLogic;
import co.edu.uniandes.csw.paseadores.entities.ContratoHotelEntity;
import co.edu.uniandes.csw.paseadores.entities.PerroEntity;
import co.edu.uniandes.csw.paseadores.persistence.PerroPersistence;
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
 * @author julian Oliveros<je.oliverosf at uniandes.edu.co>
 */
@RunWith(Arquillian.class)
public class PerroContratoHotelLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
    
    @Inject
    private PerroContratoHotelLogic perroContratoHotelLogic;
    
    @PersistenceContext
    private EntityManager em;

    @Inject
    private UserTransaction utx;
    
    private List<PerroEntity> data =new ArrayList<PerroEntity>();
    
    private List<ContratoHotelEntity> contratosData = new ArrayList();
    
        /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PerroEntity.class.getPackage())
                .addPackage(PerroContratoHotelLogic.class.getPackage())
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
        em.createQuery("delete from ContratoHotelEntity").executeUpdate();
    }
    
        /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        for (int i = 0; i < 3; i++) {
            ContratoHotelEntity contrato = factory.manufacturePojo(ContratoHotelEntity.class);
            
            em.persist(contrato);
            contratosData.add(contrato);
        }
        for (int i = 0; i < 3; i++) {
            PerroEntity entity = factory.manufacturePojo(PerroEntity.class);
            entity.setEdad(5);
            em.persist(entity);
            
            data.add(entity);
            if (i == 0) {
                 contratosData.get(i).setPerro(entity);
                PerroEntity entit = contratosData.get(i).getPerro();
                entit.setEdad(5);
            }
        }
    }
    
            /**
     * Prueba para asociar un contratoPaseo existente a un perro.
     */
    @Test
    public void addPerroTest() {
        
        if(contratosData!=null && contratosData.size()!=0){
            PerroEntity entity = data.get(1);
            ContratoHotelEntity contraEntity = contratosData.get(1);
            PerroEntity response = perroContratoHotelLogic.addPerro(contraEntity.getId(),entity.getId());

            Assert.assertNotNull(response);
            Assert.assertEquals(entity.getId(), response.getId());
        }
    }
    
    /**
     * Prueba para consultar un perro.
     */
    @Test
    public void getPerroTest() {
        
        if(contratosData!=null && contratosData.size()!=0){
            ContratoHotelEntity entity = contratosData.get(0);
            PerroEntity resultEntity = perroContratoHotelLogic.getPerro(entity.getId());
            Assert.assertNotNull(resultEntity);
            Assert.assertEquals(entity.getPerro().getId(), resultEntity.getId());
        }
    }
    
    /**
     * Prueba para remplazar las instancias de contratos paseo asociadas a una instancia
     * de perro.
     */
    
    @Test
    public void replacePerrroTest() {

        
        PerroEntity entity = data.get(2);
        
        perroContratoHotelLogic.replacePerro( entity.getId(), contratosData.get(1).getId());
        
        entity = perroContratoHotelLogic.getPerro(contratosData.get(1).getId());
        Assert.assertTrue(entity.getEstadias().contains(contratosData.get(1)));
        
    }
    
    /**
     * Prueba para desasociar un contrato paseo existente de un perro existente.
     *
     */
    @Test
    public void removeContratoPaseoTest(){
        perroContratoHotelLogic.removeContratoHotel(contratosData.get(0).getId());
        Assert.assertNull(perroContratoHotelLogic.getPerro(contratosData.get(0).getId()));
    }
}
