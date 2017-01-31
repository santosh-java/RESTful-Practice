package org.santosh.restservices.messenger.resource;

import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import org.santosh.restservices.messenger.model.Profile;
import org.santosh.restservices.messenger.service.ProfileService;

@Path("/profiles")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class ProfileResource {

	@GET
	public Response getProfiles() {
		List<Profile> allProfiles = ProfileService.INSTANCE.getAllProfiles();
		Response response = null;
		GenericEntity<List<Profile>> result;
		if (allProfiles != null && allProfiles.size() > 0) {
			result = new GenericEntity<List<Profile>>(allProfiles) {
			};
			response = Response.ok(result).build();
		} else
			response = Response.status(Status.NO_CONTENT).build();
		return response;
	}

	@GET
	@Path("/{profileName}")
	public Response getProfile(@PathParam("profileName") String profileName) {
		Profile profile = ProfileService.INSTANCE.getProfile(profileName);
		if (profile != null)
			return Response.ok(profile).build();
		else
			return Response.status(Status.NOT_FOUND).build();
	}

	@POST
	public Response addProfile(Profile profile) {
		Profile newProfile = ProfileService.INSTANCE.addProfile(profile);
		if (newProfile != null)
			return Response.ok(newProfile).build();
		else
			return Response.status(Status.INTERNAL_SERVER_ERROR).build();
	}

	@PUT
	@Path("/{profileName}")
	public Response updateProfile(@PathParam("profileName") String profileName, Profile profile) {
		profile.setProfileName(profileName);
		Profile updateProfile = ProfileService.INSTANCE.updateProfile(profile);
		if (updateProfile != null)
			return Response.ok(updateProfile).build();
		else
			return Response.status(Status.NOT_MODIFIED).entity(profile).build();
	}

	@DELETE
	@Path("/{profileName}")
	public Response deleteProfile(@PathParam("profileName") String profileName) {
		Profile removedProfile = ProfileService.INSTANCE.removeProfile(profileName);
		if (removedProfile != null)
			return Response.ok(removedProfile).build();
		else
			return Response.status(Status.NOT_FOUND).build();
	}
}
