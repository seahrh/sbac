package sbac.api;

import static sbac.util.StringUtil.concat;
import static org.testng.Assert.assertEquals;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import sbac.model.Name;

public class NamesSearchResourceIT extends ApiTestBase {
	private static final String ENDPOINT = "names/search/";

	@Test(dataProvider = "namesThatExistData")
	public void namesThatExist(String query) {
		// Test status code, media type and JSON payload
		final String path = concat(ENDPOINT, query);
		final Response rsp = API.path(path)
			.request()
			.get();
		assertEquals(rsp.getStatus(), HttpServletResponse.SC_OK);
		assertEquals(rsp.getMediaType()
			.toString(), MediaType.APPLICATION_JSON);
		final String json = rsp.readEntity(String.class);
		List<Name> names = Name.fromJson(json);
		// Name should contain stemmed version of query string
		final String stem = query.substring(0, query.length() - 1);
		for (Name n : names) {
			assertEquals(n.name()
				.contains(stem), true);
		}
	}

	@DataProvider
	public Object[][] namesThatExistData() {
		return new Object[][] { { "laura" }, { "mary" }, { "johnny" } };
	}
	
	@Test
	public void multipleTerms() {
		// Query string contains multiple terms
		// and there is at least 1 item in result set.
		final String query = "linda 2003";
		final String path = concat(ENDPOINT, query);
		final Response rsp = API.path(path)
			.request()
			.get();
		assertEquals(rsp.getStatus(), HttpServletResponse.SC_OK);
		assertEquals(rsp.getMediaType()
			.toString(), MediaType.APPLICATION_JSON);
		final String json = rsp.readEntity(String.class);
		List<Name> names = Name.fromJson(json);
		assertEquals(names.isEmpty(), false);
	}

	@Test
	public void emptyQueryString() {
		// Query string is empty, so path is only the endpoint.
		final Response rsp = API.path(ENDPOINT)
			.request()
			.get();
		assertEquals(rsp.getStatus(), HttpServletResponse.SC_BAD_REQUEST);
		assertEquals(rsp.getMediaType()
			.toString(), MediaType.APPLICATION_JSON);
		final String json = rsp.readEntity(String.class);
		assertEquals(json, "");
	}

}
