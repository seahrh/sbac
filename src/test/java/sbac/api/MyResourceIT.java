package sbac.api;

import static org.testng.Assert.assertEquals;

import org.testng.annotations.Test;

public class MyResourceIT extends ApiTestBase {

	@Test
	public void getItTest() {
		final String expected = "Got it!";
		final String actual = API.path("myresource").request().get(String.class);
		assertEquals(actual, expected);
	}

}
