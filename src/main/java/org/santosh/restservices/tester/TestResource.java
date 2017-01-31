package org.santosh.restservices.tester;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("/testResource")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class TestResource {

	@GET
	@Produces(MediaType.TEXT_HTML)
	public String test() {
		return "<h1>This is test!!!</h1>";
	}
}
