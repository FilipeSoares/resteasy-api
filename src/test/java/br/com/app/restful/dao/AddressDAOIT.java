package br.com.app.restful.dao;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import javax.inject.Inject;
import javax.persistence.criteria.Predicate;

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
@ChameleonTarget(value = "wildfly:11.0.0.Final:managed", customProperties = {
		@Property(name = "javaVmArguments", value = "-Xms64m -Xmx512m -Djava.net.preferIPv4Stack=true -Djava.awt.headless=true -Djboss.socket.binding.port-offset=2"),
		@Property(name = "managementPort", value = "9992") })
public class AddressDAOIT {

	@Deployment
	public static WebArchive deploy() {

		File[] archives = Maven.resolver().loadPomFromFile("pom.xml")
				.importDependencies(ScopeType.COMPILE, ScopeType.TEST, ScopeType.PROVIDED, ScopeType.RUNTIME).resolve()
				.withTransitivity().asFile();

		WebArchive war = ShrinkWrap.create(WebArchive.class, "restful-api.war").addPackages(true, "br.com.app.restful")
				.addAsResource("META-INF/persistence.xml").addAsLibraries(archives).addAsWebInfResource(
						new StringAsset("<beans bean-discovery-mode=\"all\" version=\"1.1\"/>"), "beans.xml");

		return war;
	}

	@Inject
	AddressDAO dao;

	private static final String ATTR_STREET = "Robert Trent Jr.";
	private static final String ATTR_DESCRIPTION = "Test Address";
	private static final String ATTR_CITY = "Test City";
	private static final Long ATTR_ZIP = 321564L;
	
    /*
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

	@Test
	@InSequence(3)
	public void listWithCriteria() {

		Long now = System.currentTimeMillis();
		
		Client client = new Client();
		client.setEmail(ATTR_EMAIL);
		client.setName(ATTR_NAME + now);		
		dao.create(client);

		final List<String> fields = Arrays.asList("name", "email");
		final List<Predicate> restrictions = new ArrayList<>();

		Assert.assertNotNull(dao.listWithCriteria(fields, restrictions));
		
	}
	
	@Transactional
	@Test
	@InSequence(4)
	public void find() {
		
		Long now = System.currentTimeMillis();
		
		Client client = new Client();
		client.setEmail(ATTR_EMAIL);
		client.setName(ATTR_NAME + now);
		
		Long id = dao.create(client);
		
		Assert.assertNotNull(dao.find(id));
	}
*/
	
}
