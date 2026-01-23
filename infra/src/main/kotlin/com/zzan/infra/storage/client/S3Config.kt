package com.zzan.infra.storage.client

import org.springframework.boot.context.properties.EnableConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider
import software.amazon.awssdk.regions.Region
import software.amazon.awssdk.services.s3.S3Client
import software.amazon.awssdk.services.s3.presigner.S3Presigner

@Configuration
@EnableConfigurationProperties(S3Properties::class)
class S3Config(
    private val s3Properties: S3Properties
) {
    @Bean
    fun s3Client(): S3Client {
        return S3Client.builder()
            .region(Region.of(s3Properties.region.static))
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(
                        s3Properties.credentials.accessKey,
                        s3Properties.credentials.secretKey
                    )
                )
            )
            .build()
    }

    @Bean
    fun s3Presigner(): S3Presigner {
        return S3Presigner.builder()
            .region(Region.of(s3Properties.region.static))
            .credentialsProvider(
                StaticCredentialsProvider.create(
                    AwsBasicCredentials.create(
                        s3Properties.credentials.accessKey,
                        s3Properties.credentials.secretKey
                    )
                )
            )
            .build()
    }
}
