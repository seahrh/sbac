package sbac.api;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

@Path("names/search")
public final class NamesSearchResource {
	private static final Logger log = LoggerFactory.getLogger(NamesSearchResource.class);
	
	/**
     * Method handling HTTP GET requests. The returned object will be sent
     * to the client as "text/plain" media type.
     *
     * @return String that will be returned as a text/plain response.
     */
    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response get() {
    	String json = "{\"app\":123}";
    	return Response.status(200).entity(json).build();
    }
}
