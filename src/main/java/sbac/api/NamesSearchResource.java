package sbac.api;

import static sbac.util.StringUtil.trim;

import java.util.List;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import sbac.datastore.NameStore;
import sbac.model.Name;

@Path("names/search/{query: .*}")
public final class NamesSearchResource {
	private static final Logger log = LoggerFactory.getLogger(NamesSearchResource.class);

	/**
	 * Handles the full text search of names. The returned object will be sent
	 * to the client as "application/json" media type.
	 * 
	 * @return String json representing the result set
	 */
	@GET
	@Produces(MediaType.APPLICATION_JSON)
	public Response get(@PathParam("query") String query, @Context HttpServletRequest request) {
		HttpSession session= request.getSession();
		// Monitor api usage by tracking session id
		// Rate limit misbehaving users
		// (Log already records api resource class and timestamp)
		log.info("session id [{}]", session.getId());
		query = trim(query);
		String json = "";
		if (query.isEmpty()) {
			log.error("query string must not be null or empty");
			return Response.status(HttpServletResponse.SC_BAD_REQUEST)
				.entity(json)
				.build();
		}
		List<Name> names = NameStore.search(query);
		log.debug("names [{}]", names);
		json = Name.toPublicJson(names);
		return Response.status(200)
			.entity(json)
			.build();
	}
}
