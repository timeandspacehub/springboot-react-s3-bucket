package com.timeandspacehub.awsimageupload.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.amazonaws.auth.AWSCredentials;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;

@Configuration
public class AmazonConfig {
	
	@Bean
	public AmazonS3 s3() {
		AWSCredentials awsCredentials = new BasicAWSCredentials("ENTER_YOUR_KEY",
				"ENTER_YOUR_PASSCODE");

		return AmazonS3ClientBuilder.standard()
				.withRegion("us-east-1")
				.withCredentials(new AWSStaticCredentialsProvider(awsCredentials))
				.build();
	}



}
