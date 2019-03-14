package br.com.app.dao;

import java.io.File;

import javax.inject.Inject;

import org.arquillian.container.chameleon.api.ChameleonTarget;
import org.arquillian.container.chameleon.api.Property;
import org.arquillian.container.chameleon.runner.ArquillianChameleon;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.transaction.api.annotation.Transactional;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.ScopeType;
import org.junit.Assert;
import org.junit.Test;
import org.junit.runner.RunWith;

import br.com.app.restful.dao.ClientDAO;
import br.com.app.restful.model.Client;

@RunWith(ArquillianChameleon.class)
@ChameleonTarget(value="wildfly:11.0.0.Final:managed", customProperties= {
		@Property(name="javaVmArguments", value="-Xms64m -Xmx512m -Djava.net.preferIPv4Stack=true -Djava.awt.headless=true -Djboss.socket.binding.port-offset=2"),
		@Property(name="managementPort", value="9992")
		})
public class ClientDAOIT {
	

	
	@Deployment
	public static WebArchive deploy() {
		
		File[] archives = Maven
				.resolver()
				.loadPomFromFile("pom.xml")
				.importDependencies(ScopeType.COMPILE, ScopeType.TEST, ScopeType.PROVIDED, ScopeType.RUNTIME)
				.resolve()
				.withTransitivity()
				.asFile();
		
		WebArchive war = ShrinkWrap.create(WebArchive.class, "restful-api.war")
		        .addPackages(true, "br.com.app.restful")
		        .addAsResource("META-INF/persistence.xml")
		        .addAsLibraries(archives)
		        .addAsWebInfResource( new StringAsset("<beans bean-discovery-mode=\"all\" version=\"1.1\"/>"), "beans.xml");
		
		return war;
	}
	
	@Inject
	ClientDAO dao;
	
	private static final String ATTR_NAME = "Client";
	private static final String ATTR_EMAIL = "client@test.com";
	
	@Transactional
	@Test
	@InSequence(1)
	public void save() {
		Assert.assertNotNull(dao.create(new Client(ATTR_EMAIL, ATTR_NAME)));
	}
	
	@Test
	@InSequence(2)
	public void list() {
		Assert.assertNotNull(dao.list());
	}
	
	@Transactional
	@Test
	@InSequence(3)
	public void find() {
		
		Long now = System.currentTimeMillis();
		
		Client client = new Client();
		client.setEmail(ATTR_EMAIL);
		client.setName(ATTR_NAME + now);
		
		Long id = dao.create(client);
		
		Assert.assertNotNull(dao.find(id));
	}



}
