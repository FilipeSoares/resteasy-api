package br.com.app.restful.resources;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.anyOf;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;

import java.io.File;
import java.net.URISyntaxException;
import java.net.URL;

import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.arquillian.container.chameleon.api.ChameleonTarget;
import org.arquillian.container.chameleon.api.Property;
import org.arquillian.container.chameleon.runner.ArquillianChameleon;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.container.test.api.RunAsClient;
import org.jboss.arquillian.junit.InSequence;
import org.jboss.arquillian.test.api.ArquillianResource;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.StringAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.jboss.shrinkwrap.resolver.api.maven.ScopeType;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import com.google.gson.GsonBuilder;

import br.com.app.restful.model.Client;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.specification.RequestSpecification;

@RunWith(ArquillianChameleon.class)
@ChameleonTarget(value="wildfly:11.0.0.Final:managed", customProperties= {
		@Property(name="javaVmArguments", value="-Xms64m -Xmx512m -Djava.net.preferIPv4Stack=true -Djava.awt.headless=true -Djboss.socket.binding.port-offset=2"),
		@Property(name="managementPort", value="9992")
		})
public class ClientResourceIT {
	
	@ArquillianResource
	private URL url;
	private RequestSpecification requestSpecification;
	
	private static final String RESOURCE = "clients";
	
	private static final String NAME = "Test Client";
	private static final String EMAIL = "@test.com";
	private static final String LOCATION_HEADER = "Location";
	
	private static final boolean printLog = false;
	
	@Deployment
	public static WebArchive deploy() {

		File[] archives = Maven.resolver().loadPomFromFile("pom.xml")
				.importDependencies(ScopeType.COMPILE, ScopeType.TEST, ScopeType.PROVIDED, ScopeType.RUNTIME).resolve()
				.withTransitivity().asFile();

		WebArchive war = ShrinkWrap
							.create(WebArchive.class, "restful-api.war")
							.addPackages(true, "br.com.app.restful")
							.addAsResource("META-INF/persistence.xml")
							.addAsLibraries(archives)
							.addAsWebInfResource("jboss-web.xml")
							.addAsWebInfResource(new StringAsset("<beans bean-discovery-mode=\"all\" version=\"1.1\"/>"), "beans.xml");

		return war;
	}
	
	@Before
	public void setup() throws URISyntaxException {
		
		final RequestSpecBuilder request = new RequestSpecBuilder();
        request.setBaseUri(url.toURI())
        		.setBasePath(RESOURCE)
        		.setAccept(MediaType.APPLICATION_JSON)
        		.setContentType(ContentType.JSON);
        
        this.requestSpecification = request.build();
		
	}
	
	@Test
	@RunAsClient
	@InSequence(1)
	public void create() {
		given(requestSpecification)
				.body(loadBody())
				.when().post()
				.then()
					.assertThat()
					.header(LOCATION_HEADER, notNullValue())
					.statusCode(is(Response.Status.CREATED.getStatusCode()));
		
	}
	
	@Test
	@RunAsClient
	@InSequence(2)
	public void update() {
		
		String location = given(requestSpecification)
				.body(loadBody())
				.when().post()
				.then()
					.assertThat()
					.header(LOCATION_HEADER, notNullValue()).statusCode(is(Response.Status.CREATED.getStatusCode()))
				.extract().header(LOCATION_HEADER);
		
		given(requestSpecification)
			.pathParam("id", extractIdentity(location))
			.body(new GsonBuilder().create().toJson(new Client(EMAIL, NAME + System.currentTimeMillis())))
			.when().put("/{id}")
			.then()
				.assertThat().statusCode(is(Response.Status.OK.getStatusCode()));
		
	}
	
	@Test
	@RunAsClient
	@InSequence(3)
	public void list() {
		
		given(requestSpecification)
			.when()
				.get()
			.then()
				.assertThat().statusCode(anyOf(is(Response.Status.OK.getStatusCode()), is(Response.Status.PARTIAL_CONTENT.getStatusCode())));
		
	}
	
	@Test
	@RunAsClient
	@InSequence(4)
	public void get() {
		
		String location = given(requestSpecification)
				.body(loadBody())
				.when().post()
				.then()
					.assertThat()
					.header(LOCATION_HEADER, notNullValue()).statusCode(is(Response.Status.CREATED.getStatusCode()))
				.extract().header(LOCATION_HEADER);
		
		given(requestSpecification)
			.pathParam("id", extractIdentity(location))
			.when().get("/{id}")
			.then()
				.assertThat().statusCode(is(Response.Status.OK.getStatusCode()))
				.assertThat().body(notNullValue());
		
	}
	
	@Test
	@RunAsClient
	@InSequence(5)
	public void remove() {
		
		String location = given(requestSpecification)
				.body(loadBody())
				.when().post()
				.then()
					.assertThat()
					.header(LOCATION_HEADER, notNullValue()).statusCode(is(Response.Status.CREATED.getStatusCode()))
				.extract().header(LOCATION_HEADER);
		
		given(requestSpecification)
			.pathParam("id", extractIdentity(location))
			.when().delete("/{id}")
			.then()
				.assertThat().statusCode(is(Response.Status.NO_CONTENT.getStatusCode()));
		
	}
	
	@Test
	@RunAsClient
	@InSequence(6)
	public void notFound() {
		
		given(requestSpecification)
			.pathParam("id", String.valueOf(System.currentTimeMillis()))
			.when().get("/{id}")
			.then()
				.assertThat().statusCode(is(Response.Status.NOT_FOUND.getStatusCode()))
				.assertThat().body(notNullValue());
		
		given(requestSpecification)
			.pathParam("id", String.valueOf(System.currentTimeMillis()))
			.body(loadBody())
			.when().log().all(printLog).put("/{id}")
			.then().log().all(printLog)
				.assertThat().statusCode(is(Response.Status.NOT_FOUND.getStatusCode()));
		
		given(requestSpecification)
		.pathParam("id", String.valueOf(System.currentTimeMillis()))
		.when().log().all(printLog).delete("/{id}")
		.then().log().all(printLog)
			.assertThat().statusCode(is(Response.Status.NOT_FOUND.getStatusCode()));
		
	}
	
	@Test
	@RunAsClient
	@InSequence(7)
	public void conflict() throws URISyntaxException {
		
		String body = loadBody();
		
		// Create Client		
		String location = given(requestSpecification)
				.body(body)
				.when().post()
				.then()
					.assertThat()
					.header(LOCATION_HEADER, notNullValue()).statusCode(is(Response.Status.CREATED.getStatusCode()))
					.extract().header(LOCATION_HEADER);
		
		// Create Duplicate Client		
		given(requestSpecification)
			.body(body)
			.when().post()
			.then()
				.assertThat().statusCode(is(Response.Status.CONFLICT.getStatusCode()));
		
		// Update Client
		given(requestSpecification)
			.pathParam("id", extractIdentity(location))
			.body(body.replaceFirst("name:"+NAME, "name:update"))
			.when().log().all(printLog).put("/{id}")
			.then().log().all(printLog)
			.assertThat().statusCode(is(Response.Status.CONFLICT.getStatusCode()));
		
		
	}
	
	private static String loadBody() {
		Long now = System.currentTimeMillis();
        
        Client client = new Client();
        client.setEmail(now + EMAIL);
        client.setName(NAME + " " + now);
		
		return new GsonBuilder().create().toJson(client);
	}
	
	private static String extractIdentity(String location){
		return location.split(RESOURCE+"/")[1];
	}

}