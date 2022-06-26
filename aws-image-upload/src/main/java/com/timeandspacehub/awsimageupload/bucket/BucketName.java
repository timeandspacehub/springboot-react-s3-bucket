package com.timeandspacehub.awsimageupload.bucket;

public enum BucketName {

	PROFILE_IMAGE("ENTER_YOUR_BUCKET_NAME");

	private final String bucketName;

	private BucketName(String bucketName) {
		this.bucketName = bucketName;
	}

	public String getBucketName() {
		return bucketName;
	}

}
