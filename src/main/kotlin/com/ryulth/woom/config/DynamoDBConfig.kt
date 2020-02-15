package com.ryulth.woom.config

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
@EnableDynamoDBRepositories(basePackages = ["com.ryulth.woom.domain.repository"])
class DynamoDBConfig(
    @Value("\${amazon.dynamodb.endpoint}")
    private val amazonDynamoDBEndpoint: String,
    @Value("\${amazon.dynamodb.region}")
    private val amazonDynamoDbRegion: String,
    @Value("\${amazon.aws.accesskey}")
    private val amazonAWSAccessKey: String,
    @Value("\${amazon.aws.secretkey}")
    private val amazonAWSSecretKey: String
) {

    @Bean
    fun amazonDynamoDB(amazonAWSCredentials: AWSCredentials?): AmazonDynamoDB {
        return AmazonDynamoDBClientBuilder.standard().withCredentials(this.amazonAWSCredentialsProvider())
            .withEndpointConfiguration(EndpointConfiguration(amazonDynamoDBEndpoint, amazonDynamoDbRegion)).build()
    }

    @Bean
    fun amazonAWSCredentials(): AWSCredentials {
        return BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey)
    }

    fun amazonAWSCredentialsProvider(): AWSCredentialsProvider? {
        return AWSStaticCredentialsProvider(amazonAWSCredentials())
    }
}
