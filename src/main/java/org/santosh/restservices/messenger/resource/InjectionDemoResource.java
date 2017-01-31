package org.santosh.restservices.messenger.resource;

import javax.ws.rs.Consumes;
import javax.ws.rs.CookieParam;
import javax.ws.rs.GET;
import javax.ws.rs.HeaderParam;
import javax.ws.rs.MatrixParam;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;

@Path("/injection")
@Consumes(MediaType.TEXT_PLAIN)
@Produces(MediaType.TEXT_PLAIN)
public class InjectionDemoResource {
	@GET
	@Path("annotations")
	public String getParamsUsingAnnotations(@QueryParam("qParam") String qParam, @MatrixParam("mParam") String mParam,
			@HeaderParam("hParam") String hParam, @CookieParam("cParam") String cParam) {

		return "QueryParam :" + qParam + "\n MatrixParam :" + mParam + "\n HeaderParam :" + hParam + "\n CookieParam :"
				+ cParam;
	}

	@GET
	@Path("/context")
	public String getParamsUsingContext() {
		return "Test";
	}
}
