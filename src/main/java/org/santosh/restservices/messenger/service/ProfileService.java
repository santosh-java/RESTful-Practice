package org.santosh.restservices.messenger.service;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.santosh.restservices.messenger.database.DatabaseManager;
import org.santosh.restservices.messenger.model.Profile;

public class ProfileService {
	public static final ProfileService INSTANCE = new ProfileService();
	private static Map<String, Profile> profiles = DatabaseManager.getProfiles();

	static {
		profiles.put("santosh", new Profile(1L, "santosh", "Santosh Giri Govind", "Marthi"));
		profiles.put("bachi", new Profile(2L, "bachi", "Adi Venkata Bhaskar", "Anala"));
		profiles.put("san", new Profile(3L, "san", "Santosh Brahmaji", "Gurajada"));
		profiles.put("raj", new Profile(4L, "raj", "Rajesh Chandra", "Boddeda"));
	}

	private ProfileService() {

	}

	public List<Profile> getAllProfiles() {
		return new ArrayList<>(profiles.values());
	}

	public Profile getProfile(String profileId) {
		return profiles.get(profileId.toLowerCase());
	}

	public Profile updateProfile(Profile profile) {
		if (profile.getProfileName().isEmpty()) {
			return null;
		}
		profiles.put(profile.getProfileName(), profile);
		return profile;
	}

	public Profile addProfile(Profile profile) {
		profile.setId(profiles.size() + 1);
		profiles.put(profile.getProfileName().toLowerCase(), profile);
		return profile;
	}

	public Profile removeProfile(String profileName) {
		return profiles.remove(profileName);
	}
}
