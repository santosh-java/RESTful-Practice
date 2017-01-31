package org.santosh.restservices.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.santosh.restservices.messenger.database.DatabaseManager;
import org.santosh.restservices.messenger.exception.DataNotFoundException;
import org.santosh.restservices.messenger.model.Message;

public class MessageService {
	public static final MessageService INSTANCE = new MessageService();
	private static Map<Long, Message> messages = DatabaseManager.getMessages();

	static {
		messages.put(1L, new Message(1L, "Hello World!!!", "Santosh"));
		messages.put(2L, new Message(2L, "Hello Jersey!!!", "Santosh"));
		messages.put(3L, new Message(3L, "Hello RESTful services!!!", "Santosh1"));
	}

	private MessageService() {

	}

	public List<Message> getAllMessages() {
		ArrayList<Message> result = new ArrayList<Message>(messages.values());
		if (result.size() <= 0)
			throw new DataNotFoundException("There are no messages posted at all");
		return result;
	}

	public List<Message> getAllMessagesForYear(int year) {
		List<Message> result = new ArrayList<>();
		Calendar c = Calendar.getInstance();
		for (Message m : messages.values()) {
			c.setTime(m.getCreated());
			if (c.get(Calendar.YEAR) == year) {
				result.add(m);
			}
		}
		if (result.size() <= 0)
			throw new DataNotFoundException("No messages were posted in year " + year);
		return result;
	}

	public List<Message> getAllMessagesPaginated(int start, int size) {
		List<Message> result = new ArrayList<>(messages.values());

		if (result.size() <= 0)
			throw new DataNotFoundException("No messages were posted");

		if (start + size > result.size())
			return result;
		return result.subList(start, start + size);
	}

	public Message getMessage(long id) {
		Message message = messages.get(id);
		if (message == null)
			throw new DataNotFoundException("Message with id " + id + " not found.");
		return message;
	}

	public Message addMessage(Message message) {
		message.setId(messages.size() + 1);
		messages.put(message.getId(), message);
		return message;
	}

	public Message updateMessage(Message message) {
		if (message.getId() <= 0)
			throw new DataNotFoundException("Invalid message id " + message.getId() + " provided to update");

		if (!messages.containsKey(message.getId()))
			throw new DataNotFoundException("Message with id " + message.getId() + " not found to update.");

		messages.put(message.getId(), message);
		return message;
	}

	public Message removeMessage(long id) {
		Message removedMessage = messages.remove(id);

		if (removedMessage == null)
			throw new DataNotFoundException("Message with id " + id + " not found.");
		
		return removedMessage;
	}
}
