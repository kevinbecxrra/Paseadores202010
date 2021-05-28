/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package co.edu.uniandes.csw.paseadores.tests.postman;

import co.edu.uniandes.csw.paseadores.dtos.PaseadorDTO;
import co.edu.uniandes.csw.paseadores.mappers.BusinessLogicExceptionMapper;
import co.edu.uniandes.csw.paseadores.resources.RestConfig;
import co.edu.uniandes.csw.postman.tests.PostmanTestBuilder;
import java.io.File;
import java.io.IOException;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

/**
 *
 * @author Daniel Mateo Guatibonza Solano
 */
@RunWith(Arquillian.class)
public class PaseadorHorarioIT {
    
    private static final String COLLECTION = "PaseadorHorario-Tests.postman_collection";

    @Deployment(testable = true)
    public static WebArchive createDeployment() {
        return ShrinkWrap.create(WebArchive.class, "s2_paseadores-api.war")//War del modulo api
                // Se agrega las dependencias
                .addAsLibraries(Maven.resolver().loadPomFromFile("pom.xml")
                        .importRuntimeDependencies().resolve()
                        .withTransitivity().asFile())
                // Se agregan los compilados de los paquetes de servicios
                .addPackage(RestConfig.class.getPackage()) //No importa cual recurso usar, lo importante es agregar el paquet
                .addPackage(PaseadorDTO.class.getPackage()) //No importa cual dto usar, lo importante es agregar el paquete.
                .addPackage(BusinessLogicExceptionMapper.class.getPackage())
                // El archivo que contiene la configuracion a la base de datos.
                .addAsResource("META-INF/persistence.xml", "META-INF/persistence.xml")
                // El archivo beans.xml es necesario para injeccion de dependencias.
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/beans.xml"))
                // El archivo web.xml es necesario para el despliegue de los servlets
                .setWebXML(new File("src/main/webapp/WEB-INF/web.xml"))
                .addAsWebInfResource(new File("src/main/webapp/WEB-INF/glassfish-resources.xml"));
    }

    @Test
    @RunAsClient
    public void postman() throws IOException {
        PostmanTestBuilder tp = new PostmanTestBuilder();
        tp.setTestWithoutLogin(COLLECTION, "Entorno-IT.postman_environment");
        String desiredResult = "0";
        if( tp.getAssertions_failed() != null)
            Assert.assertEquals("Error en Assertions de: " + COLLECTION, desiredResult,tp.getAssertions_failed());
        if( tp.getIterations_failed() != null)
            Assert.assertEquals("Error en Iterations de: " + COLLECTION, desiredResult,tp.getIterations_failed());
        if ( tp.getPrerequest_scripts_failed() != null)
            Assert.assertEquals("Error en Scripts de: " + COLLECTION, desiredResult,tp.getPrerequest_scripts_failed());
        if( tp.getRequests_failed() != null)
            Assert.assertEquals("Error en Requests de: " + COLLECTION, desiredResult,tp.getRequests_failed());
        if( tp.getTest_scripts_failed() != null)
            Assert.assertEquals(desiredResult,tp.getTest_scripts_failed());
    }
}
