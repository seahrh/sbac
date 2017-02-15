package sbac.api;

import javax.ws.rs.client.ClientBuilder;
import javax.ws.rs.client.WebTarget;

public class ApiTestBase {
	protected static final WebTarget API = ClientBuilder.newClient().target("http://localhost:8080/api");

}
