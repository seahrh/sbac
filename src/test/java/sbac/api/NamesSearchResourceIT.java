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
		String stem;
		for (Name n : names) {
			stem = query.substring(0, query.length() - 1);
			assertEquals(n.name().contains(stem), true);
		}
	}

	@DataProvider
	public Object[][] namesThatExistData() {
		return new Object[][] { { "laura" }, { "mary" }, { "johnny" } };
	}

}
