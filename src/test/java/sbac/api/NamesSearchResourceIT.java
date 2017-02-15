package sbac.api;

import static org.testng.Assert.assertEquals;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.testng.annotations.Test;

public class NamesSearchResourceIT extends ApiTestBase {
	private static final String ENDPOINT = "names/search/";
	
	@Test
	public void getTest() {
		// Test status code, media type and JSON payload
		final String ePayload = "{\"app\":123}";
		final Response rsp = API.path(ENDPOINT + "laura 2003")
			.request()
			.get();
		assertEquals(rsp.getStatus(), HttpServletResponse.SC_OK);
		assertEquals(rsp.getMediaType()
			.toString(), MediaType.APPLICATION_JSON);
		assertEquals(rsp.readEntity(String.class), ePayload);
	}

}
