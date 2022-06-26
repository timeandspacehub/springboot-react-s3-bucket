package com.timeandspacehub.awsimageupload.profile;

import java.io.IOException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;

import org.apache.http.entity.ContentType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import com.timeandspacehub.awsimageupload.bucket.BucketName;
import com.timeandspacehub.awsimageupload.filestore.FileStore;

/**
 * This is where all the business logic happens.
 * 
 * @author nijjwalshrestha
 *
 */
@Service
public class UserProfileService {

	private final UserProfileDataAccessService userProfileDataAccessService;
	private final FileStore fileStore;

	@Autowired
	public UserProfileService(UserProfileDataAccessService userProfileDataAccessService, FileStore fileStore) {
		super();
		this.userProfileDataAccessService = userProfileDataAccessService;
		this.fileStore = fileStore;
	}

	List<UserProfile> getUserProfiles() {
		return userProfileDataAccessService.getUserProfiles();
	}

	public void uploadUserProfileImage(UUID userProfileId, MultipartFile file) {

		// 1. check if the image is not empty

		isFileEmpty(file);

		// 2. If the file is an image
		isImage(file);

		// 3. check whether the user exists in our database FakeUserProfileDataStore
		UserProfile user = getUserProfileOrThrow(userProfileId);

		// 4. Grab some metadata from file
		Map<String, String> metadata = extractMetadata(file);

		// 5. Store the image in s3 bucket and update database with s3 image and link
		String path = String.format("%s/%s", BucketName.PROFILE_IMAGE.getBucketName(), user.getUserProfileId());
		String filename = String.format("%s-%s", file.getOriginalFilename(), UUID.randomUUID());

		try {
			fileStore.save(path, filename, Optional.of(metadata), file.getInputStream());
			user.setUserProfileImageLink(filename);
		} catch (IOException e) {
			throw new IllegalStateException(e);
		}

	}

	public byte[] downloadUserProfileImage(UUID userProfileId) {
		UserProfile user = getUserProfileOrThrow(userProfileId);
		String path = String.format("%s/%s", 
				BucketName.PROFILE_IMAGE.getBucketName(), 
				user.getUserProfileId());

		return user.getUserProfileImageLink()
				.map(key -> fileStore.download(path, key))
				.orElse(new byte[0]);

	}

	private Map<String, String> extractMetadata(MultipartFile file) {
		Map<String, String> metadata = new HashMap<>();
		metadata.put("Content-Type", String.valueOf(file.getContentType()));
		metadata.put("Content-Length", String.valueOf(file.getSize()));
		return metadata;
	}

	private UserProfile getUserProfileOrThrow(UUID userProfileId) {
		return userProfileDataAccessService.getUserProfiles().stream()
				.filter(userProfile -> userProfile.getUserProfileId().equals(userProfileId)).findFirst().orElseThrow(
						() -> new IllegalStateException(String.format("User profile %s not found", userProfileId)));
	}

	private void isImage(MultipartFile file) {
		if (!Arrays.asList(ContentType.IMAGE_JPEG.getMimeType(), ContentType.IMAGE_PNG.getMimeType(),
				ContentType.IMAGE_GIF.getMimeType()).contains(file.getContentType())) {

			// Unchecked exception so no need to handle it.
			throw new IllegalStateException("File must be an image [" + file.getContentType() + "]");
		}
	}

	private void isFileEmpty(MultipartFile file) {
		if (file.isEmpty()) {

			//// Unchecked exception so no need to handle it.
			throw new IllegalStateException("Cannot upload empty file [" + file.getSize() + "]");
		}
	}

}
