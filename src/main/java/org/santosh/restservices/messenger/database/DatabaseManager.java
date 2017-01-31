package org.santosh.restservices.messenger.database;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import org.santosh.restservices.messenger.model.Message;
import org.santosh.restservices.messenger.model.Profile;

public class DatabaseManager {
	private static Map<Long, Message> messages = new ConcurrentHashMap<>();
	private static Map<String, Profile> profiles = new ConcurrentHashMap<>();

	public static Map<Long, Message> getMessages() {
		return messages;
	}

	public static Map<String, Profile> getProfiles() {
		return profiles;
	}
}
