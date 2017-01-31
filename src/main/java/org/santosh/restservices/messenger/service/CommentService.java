package org.santosh.restservices.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.santosh.restservices.messenger.database.DatabaseManager;
import org.santosh.restservices.messenger.exception.DataNotFoundException;
import org.santosh.restservices.messenger.model.Comment;
import org.santosh.restservices.messenger.model.Message;

public class CommentService {
	public static final CommentService INSTANCE = new CommentService();
	private Map<Long, Message> messages = DatabaseManager.getMessages();

	private CommentService() {

	}

	public List<Comment> getAllComments(long messageId) {
		Message message = messages.get(messageId);
		if (message != null) {
			Map<Long, Comment> comments = message.getComments();
			return new ArrayList<Comment>(comments.values());
		} else {
			throw new DataNotFoundException("No message with " + messageId + " is found.");
		}
	}

	public Comment getComment(long messageId, long commentId) {
		Message message = messages.get(messageId);
		if (message != null) {
			Comment comment = message.getComments().get(commentId);
			if (comment == null) {
				throw new DataNotFoundException(
						"No comment with " + commentId + " is found for message with " + messageId);
			}
			return comment;
		} else {
			throw new DataNotFoundException("No message with " + messageId + " is found.");
		}
	}

	public Comment addComment(long messageId, Comment comment) {
		Message message = messages.get(messageId);
		if (message != null) {
			Map<Long, Comment> comments = message.getComments();
			comment.setId(comments.size() + 1);
			comments.put(comment.getId(), comment);
			return comment;
		} else {
			throw new DataNotFoundException("No message with " + messageId + " is found.");
		}
	}

	public Comment updateComment(long messageId, Comment comment) {
		Message message = messages.get(messageId);
		if (message != null && comment.getId() > 0) {
			message.getComments().put(comment.getId(), comment);
			return comment;
		} else if (message == null) {
			throw new DataNotFoundException("No message with " + messageId + " is found.");
		} else {
			throw new DataNotFoundException(
					"No comment with " + comment.getId() + " is found for message with " + messageId);
		}
	}

	public Comment deleteComment(long messageId, long commentId) {
		Message message = messages.get(messageId);
		if (message != null) {
			Comment comment = message.getComments().remove(commentId);
			if (comment == null) {
				throw new DataNotFoundException(
						"No comment with " + commentId + " is found for message with " + messageId);
			}

			return comment;
		} else {
			throw new DataNotFoundException("No comment with " + commentId + " is found for message with " + messageId);
		}
	}
}
