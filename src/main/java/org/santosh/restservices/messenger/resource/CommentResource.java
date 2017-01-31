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
import javax.ws.rs.core.Context;
import javax.ws.rs.core.GenericEntity;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;
import javax.ws.rs.core.UriInfo;

import org.santosh.restservices.messenger.model.Comment;
import org.santosh.restservices.messenger.service.CommentService;

@Path("/")
@Produces(MediaType.APPLICATION_JSON)
@Consumes(MediaType.APPLICATION_JSON)
public class CommentResource {

	@GET
	public Response getAllComments(@PathParam("messageId") long messageId) {
		System.out.println("getAllComments is called");
		if (messageId < 0)
			return Response.status(Status.BAD_REQUEST).build();
		List<Comment> allComments = CommentService.INSTANCE.getAllComments(messageId);
		GenericEntity<List<Comment>> comments = new GenericEntity<List<Comment>>(allComments) {};
		return Response.ok(comments).build();
	}

	@GET
	@Path("/{commentId}")
	public Response getComment(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId) {
		System.out.println("getComment is called");
		if (messageId < 0 || commentId < 0)
			return Response.status(Status.BAD_REQUEST).build();
		return Response.ok(CommentService.INSTANCE.getComment(messageId, commentId)).build();
	}

	@POST
	public Response addComment(@PathParam("messageId") long messageId, Comment comment, @Context UriInfo uriInfo) {
		System.out.println("addComment is called");
		if (messageId < 0)
			return Response.status(Status.BAD_REQUEST).build();
		Comment newComment = CommentService.INSTANCE.addComment(messageId, comment);
		URI newCommentUri = uriInfo.getAbsolutePathBuilder().path(String.valueOf(newComment.getId())).build();
		return Response.created(newCommentUri).entity(newComment).build();
	}

	@PUT
	@Path("/{commentId}")
	public Response updateComment(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId,
			Comment comment) {
		System.out.println("updateComment is called");
		if (messageId < 0 || commentId < 0)
			return Response.status(Status.BAD_REQUEST).build();

		comment.setId(commentId);
		return Response.ok(CommentService.INSTANCE.updateComment(messageId, comment)).build();
	}

	@DELETE
	@Path("/{commentId}")
	public Response deleteComment(@PathParam("messageId") long messageId, @PathParam("commentId") long commentId) {
		System.out.println("deleteComment is called");
		if (messageId < 0 || commentId < 0) {
			return Response.status(Status.BAD_REQUEST).build();
		}
		return Response.ok(CommentService.INSTANCE.deleteComment(messageId, commentId)).build();
	}
}
