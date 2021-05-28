/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.test.logic;

import co.edu.uniandes.csw.paseadores.ejb.PagoPaseadorLogic;
import co.edu.uniandes.csw.paseadores.entities.PagoPaseadorEntity;
import co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException;
import co.edu.uniandes.csw.paseadores.persistence.PagoPaseadorPersistence;
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
public class PagoPaseadorLogicTest {
    
    private PodamFactory factory = new PodamFactoryImpl();
     
    @Inject
    private PagoPaseadorLogic pagoPaseadorLogic;
    
    @PersistenceContext
    private EntityManager em;
    
    @Inject
    private UserTransaction utx;

    private List<PagoPaseadorEntity> data = new ArrayList<PagoPaseadorEntity>();
    
    /**
     * @return Devuelve el jar que Arquillian va a desplegar en Payara embebido.
     * El jar contiene las clases, el descriptor de la base de datos y el
     * archivo beans.xml para resolver la inyección de dependencias.
     */
    @Deployment
    public static JavaArchive createDeployment() {
        return ShrinkWrap.create(JavaArchive.class)
                .addPackage(PagoPaseadorEntity.class.getPackage())
                .addPackage(PagoPaseadorLogic.class.getPackage())
                .addPackage(PagoPaseadorPersistence.class.getPackage())
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
        em.createQuery("delete from PagoPaseadorEntity").executeUpdate();
    }
    
    /**
     * Inserta los datos iniciales para el correcto funcionamiento de las
     * pruebas.
     */
    private void insertData() {
        
        for (int i = 0; i < 3; i++) {
            PagoPaseadorEntity pago = factory.manufacturePojo(PagoPaseadorEntity.class);
           
            em.persist(pago);
            data.add(pago);
        }
        

    }

    /**
     * Prueba para crear un PagoPaseador con todo bien
     *
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    @Test
    public void createPagoPaseadorTest() throws BusinessLogicException {
        PagoPaseadorEntity newEntity = factory.manufacturePojo(PagoPaseadorEntity.class);
        PagoPaseadorEntity result = pagoPaseadorLogic.createPagoPaseador(newEntity);
        
        Assert.assertNotNull(result);
        PagoPaseadorEntity entity = em.find(PagoPaseadorEntity.class, result.getId());
        
        Assert.assertEquals(newEntity.getId(), entity.getId());
        Assert.assertEquals(newEntity.getMonto(), entity.getMonto());
        Assert.assertEquals(newEntity.getReferencia(), entity.getReferencia());
        Assert.assertEquals(newEntity.getFechaLimite(), entity.getFechaLimite());
    }
    
     /**
     * Prueba para crear un pago con monto inválido(menor a 0)
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createPagoPaseadorTestConMontoInvalido() throws BusinessLogicException {
        PagoPaseadorEntity newEntity = factory.manufacturePojo(PagoPaseadorEntity.class);
        //newEntity.setEditorial(editorialData.get(0));
        Double monto = -20.56;
        newEntity.setMonto(monto);
        pagoPaseadorLogic.createPagoPaseador(newEntity);
    }
    
    /**
     * Prueba para crear un pago con referencia inválido(referencia que ya existe)
     *
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void createPagoPaseadorTestConReferenciaExistente() throws BusinessLogicException {
        PagoPaseadorEntity newEntity = factory.manufacturePojo(PagoPaseadorEntity.class);
        //newEntity.setEditorial(editorialData.get(0));
        newEntity.setReferencia(data.get(0).getReferencia());
        pagoPaseadorLogic.createPagoPaseador(newEntity);
    }    
    
    /**
     * Prueba para consultar un PagoPaseadorEntity.
     */
    @Test
    public void getPagoPaseadorByReferenciaTest() {
        PagoPaseadorEntity entity = data.get(0);
        PagoPaseadorEntity resultEntity = pagoPaseadorLogic.getPagoByReferencia(entity.getReferencia());
        Assert.assertNotNull(resultEntity);
        
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getMonto(), resultEntity.getMonto());
        Assert.assertEquals(entity.getReferencia(), resultEntity.getReferencia());
        Assert.assertEquals(entity.getFechaLimite(), resultEntity.getFechaLimite());
    }
    
    /**
     * Prueba para consultar un PagoPaseadorEntity por su referencia.
     */
    @Test
    public void getPagoPaseadorByfechaLimiteTest() {
        PagoPaseadorEntity entity = data.get(0);
        PagoPaseadorEntity resultEntity = (PagoPaseadorEntity) pagoPaseadorLogic.getPagoByfechaLimite(entity.getFechaLimite());
        Assert.assertNotNull(resultEntity);
        
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getMonto(), resultEntity.getMonto());
        Assert.assertEquals(entity.getReferencia(), resultEntity.getReferencia());
        Assert.assertEquals(entity.getFechaLimite(), resultEntity.getFechaLimite());
    }
    
    
    /**
     * Prueba para crear un PagoPaseador con todo bien
     *
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    @Test
    public void updatePagoPaseadorTest() throws BusinessLogicException {
        
        PagoPaseadorEntity entity = data.get(0);
        PagoPaseadorEntity newEntity = factory.manufacturePojo(PagoPaseadorEntity.class);
        newEntity.setId(entity.getId());
        pagoPaseadorLogic.updatePago(newEntity.getId(), newEntity);
        PagoPaseadorEntity resp = em.find(PagoPaseadorEntity.class, entity.getId());
        
        Assert.assertEquals(newEntity.getId(), resp.getId());
        Assert.assertEquals(newEntity.getMonto(), resp.getMonto());
        Assert.assertEquals(newEntity.getReferencia(), resp.getReferencia());
        Assert.assertEquals(newEntity.getFechaLimite(), resp.getFechaLimite());
    }
    
     /**
     * Prueba para crear un pago con monto inválido(menor a 0)
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updatePagoPaseadorTestConMontoInvalido() throws BusinessLogicException {
        
        PagoPaseadorEntity entity = data.get(0);
        PagoPaseadorEntity newEntity = factory.manufacturePojo(PagoPaseadorEntity.class);
        Double monto = -20.56;
        newEntity.setMonto(monto);
        newEntity.setId(entity.getId());
        pagoPaseadorLogic.updatePago(newEntity.getId(), newEntity);
        
    }
    
    /**
     * Prueba para crear un pago con referencia inválido(referencia que ya existe)
     *
     * @throws co.edu.uniandes.csw.paseadores.exceptions.BusinessLogicException
     */
    @Test(expected = BusinessLogicException.class)
    public void updatePagoPaseadorTestConReferenciaExistente() throws BusinessLogicException {
        
        PagoPaseadorEntity entity = data.get(0);
        PagoPaseadorEntity newEntity = factory.manufacturePojo(PagoPaseadorEntity.class);
        newEntity.setId(entity.getId());
        newEntity.setReferencia(data.get(1).getReferencia());

        pagoPaseadorLogic.updatePago(newEntity.getId(), newEntity);
    }    
    
    
    /**
     * Prueba para consultar un pagoPaseador por su fecha limite.
     */
    @Test
    public void getPagoPaseadorTest() {
        PagoPaseadorEntity entity = data.get(0);
        PagoPaseadorEntity resultEntity = pagoPaseadorLogic.getPagoPaseador(entity.getId());
        Assert.assertNotNull(resultEntity);
        
        Assert.assertEquals(entity.getId(), resultEntity.getId());
        Assert.assertEquals(entity.getMonto(), resultEntity.getMonto());
        Assert.assertEquals(entity.getReferencia(), resultEntity.getReferencia());
        Assert.assertEquals(entity.getFechaLimite(), resultEntity.getFechaLimite());
    }
    
    /**
     * Prueba para eliminar un PagoPasseador
     */
    @Test
    public void deletePagoPaseadorTest() {
        PagoPaseadorEntity entity = data.get(0);
        pagoPaseadorLogic.deletePagoPaseador(entity.getId());
        PagoPaseadorEntity deleted = em.find(PagoPaseadorEntity.class, entity.getId());
        Assert.assertNull(deleted);
    }
}
