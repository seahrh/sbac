package sbac.api;

import static sbac.util.StringUtil.trim;

import java.util.List;

import javax.servlet.http.HttpServletResponse;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sbac.datastore.NameStore;
import sbac.model.Name;

@Path("names/search/{query}")
public final class NamesSearchResource {
	private static final Logger log = LoggerFactory.getLogger(NamesSearchResource.class);

	/**
	 * Method handling HTTP GET requests. The returned object will be sent to
	 * the client as "text/plain" media type.
	 * 
	 * @return String that will be returned as a text/plain response.
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("query") String query) {
		query = trim(query);
		String json = "";
		if (query.isEmpty()) {
			log.error("query string must not be null or empty");
			return Response.status(HttpServletResponse.SC_BAD_REQUEST)
				.entity(json)
				.build();
		}
		List<Name> names = NameStore.search(query);
		log.info("names [{}]", names);
		json = Name.toPublicJson(names);
		return Response.status(200)
			.entity(json)
			.build();
	}
}
