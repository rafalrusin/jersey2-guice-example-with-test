package example.jersey;

import javax.inject.Inject;
import javax.ws.rs.*;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.UriInfo;

import com.google.inject.servlet.RequestScoped;

import example.guice.CounterService;

@RequestScoped
@Path("counter")
public class CounterResource {

	private CounterService service;

	@Inject
	public CounterResource(CounterService service) {
		this.service = service;
	}

	@GET
	@Produces(MediaType.TEXT_PLAIN)
	public String getIt(@Context UriInfo uriInfo) {
		return "" + service.get();
	}

    @POST
    @Consumes(MediaType.TEXT_PLAIN)
    @Produces(MediaType.TEXT_PLAIN)
    public String increase(String delta) {
        return "" + service.increase(Integer.parseInt(delta));
    }
}