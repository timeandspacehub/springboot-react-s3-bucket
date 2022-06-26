package com.timeandspacehub.awsimageupload.datastore;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import org.springframework.stereotype.Repository;

import com.timeandspacehub.awsimageupload.profile.UserProfile;

@Repository
public class FakeUserProfileDataStore {
	private static final List<UserProfile> USER_PROFILES = new ArrayList<>();

	static {
		USER_PROFILES.add(new UserProfile(UUID.fromString("c46fc84d-9d5c-4874-a197-16a5caf41785"), "janetjones", null));
		USER_PROFILES
				.add(new UserProfile(UUID.fromString("8db7279f-57f0-45e3-b9c7-573aa1414456"), "antoniojunior", null));
	}

	public List<UserProfile> getUserProfiles() {
		return USER_PROFILES;
	}

}
