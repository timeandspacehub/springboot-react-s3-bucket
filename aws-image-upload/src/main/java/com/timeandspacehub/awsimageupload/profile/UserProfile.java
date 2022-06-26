package com.timeandspacehub.awsimageupload.profile;

import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

public class UserProfile {

	private UUID userProfileId;
	private String userName;
	private String userProfileImageLink; // S3 Key

	public UserProfile(UUID userProfileId, String userName, String userProfileImageLink) {
		super();
		this.userProfileId = userProfileId;
		this.userName = userName;
		this.userProfileImageLink = userProfileImageLink;
	}

	public UUID getUserProfileId() {
		return userProfileId;
	}

	public void setUserProfileId(UUID userProfileId) {
		this.userProfileId = userProfileId;
	}

	public String getUserName() {
		return userName;
	}

	public void setUserName(String userName) {
		this.userName = userName;
	}

	public Optional<String> getUserProfileImageLink() {
		return Optional.ofNullable(userProfileImageLink);
	}

	public void setUserProfileImageLink(String userProfileImageLink) {
		this.userProfileImageLink = userProfileImageLink;
	}

	@Override
	public int hashCode() {
		return Objects.hash(userName, userProfileId, userProfileImageLink);
	}

	@Override
	public boolean equals(Object obj) {
		if (this == obj)
			return true;
		if (obj == null)
			return false;
		if (getClass() != obj.getClass())
			return false;
		UserProfile that = (UserProfile) obj;
		return Objects.equals(userName, that.userName) && Objects.equals(userProfileId, that.userProfileId)
				&& Objects.equals(userProfileImageLink, that.userProfileImageLink);
	}

}
