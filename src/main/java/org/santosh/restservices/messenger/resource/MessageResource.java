package org.santosh.restservices.messenger.resource;

import java.net.URI;
import java.util.List;

import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.santosh.restservices.messenger.model.Message;
import org.santosh.restservices.messenger.service.MessageService;

@Path("/messages")
@Consumes(MediaType.APPLICATION_JSON)
@Produces(MediaType.APPLICATION_JSON)
public class MessageResource {

	@GET
	public Response getMessages(@QueryParam("year") int year, @QueryParam("start") int start,
			@QueryParam("size") int size) {

		System.out.println("getMessages is called");
		List<Message> result = null;

		if (year > 0) {
			result = MessageService.INSTANCE.getAllMessagesForYear(year);
		} else if (start >= 0 && size > 0) {
			result = MessageService.INSTANCE.getAllMessagesPaginated(start, size);
		} else {
			result = MessageService.INSTANCE.getAllMessages();
		}

		if (result != null && result.size() > 0) {
			GenericEntity<List<Message>> ge = new GenericEntity<List<Message>>(result) {
			};
			return Response.ok(ge).build();
		} else
			return Response.status(Status.NOT_FOUND).build();
	}

	@POST
	public Response addMessage(Message message, @Context UriInfo uriInfo) {
		System.out.println("addMessage is called");
		Message newMessage = MessageService.INSTANCE.addMessage(message);
		URI newMessageURI = uriInfo.getAbsolutePathBuilder().path(String.valueOf(newMessage.getId())).build();
		return Response.created(newMessageURI).entity(newMessage).build();
	}

	@PUT
	@Path("/{messageId}")
	public Response updateMessage(@PathParam("messageId") long id, Message message) {
		System.out.println("updateMessage is called");
		message.setId(id);
		return Response.ok(MessageService.INSTANCE.updateMessage(message)).build();
	}

	@GET
	@Path("/{messageId}")
	public Response getMessage(@PathParam("messageId") long id, @Context UriInfo uriInfo) {
		System.out.println("getMessage is called");
		Message message = MessageService.INSTANCE.getMessage(id);
		if(message.getLinks().size() == 0)
		{
			message.addLink(getURIForSelf(uriInfo, message), "self");
			message.addLink(getURIForProfile(uriInfo, message), "profile");
			message.addLink(getURIForComments(uriInfo, message), "comments");
		}
		return Response.ok(message).build();
	}

	private String getURIForComments(UriInfo uriInfo, Message message) {
		String url = uriInfo.getBaseUriBuilder()
				.path(MessageResource.class)
				.path(MessageResource.class, "getCommentResource")
				.path(CommentResource.class)
				.resolveTemplate("messageId", message.getId())
				.build().toString();
		return url;
	}

	private String getURIForProfile(UriInfo uriInfo, Message message) {
		String url = uriInfo.getBaseUriBuilder().path(ProfileResource.class).path(message.getAuthor()).build()
				.toString();
		return url;
	}

	private String getURIForSelf(UriInfo uriInfo, Message message) {
		String url = uriInfo.getBaseUriBuilder().path(MessageResource.class).path(Long.toString(message.getId()))
				.build().toString();
		return url;
	}

	@DELETE
	@Path("/{messageId}")
	public Response deleteMessage(@PathParam("messageId") long id) {
		System.out.println("deleteMessage is called");
		return Response.ok(MessageService.INSTANCE.removeMessage(id)).build();
	}

	// START of SUBResources for Messages
	@Path("/{messageId}/comments")
	public CommentResource getCommentResource() {
		return new CommentResource();
	}
	// END of SUBResources for Messages
}
