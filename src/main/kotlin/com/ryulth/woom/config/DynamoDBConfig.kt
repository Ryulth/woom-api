package com.ryulth.woom.config

import com.amazonaws.auth.AWSCredentials
import com.amazonaws.auth.AWSCredentialsProvider
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.client.builder.AwsClientBuilder.EndpointConfiguration
import com.amazonaws.services.dynamodbv2.AmazonDynamoDB
import com.amazonaws.services.dynamodbv2.AmazonDynamoDBClientBuilder
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapper
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBMapperConfig.TableNameOverride
import com.amazonaws.services.dynamodbv2.datamodeling.DynamoDBTypeConverterFactory
import com.amazonaws.services.dynamodbv2.document.DynamoDB
import org.socialsignin.spring.data.dynamodb.repository.config.EnableDynamoDBRepositories
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.context.annotation.Primary


@Configuration
@EnableDynamoDBRepositories(basePackages = ["com.ryulth.woom.domain.repository.dynamodb"])
class DynamoDBConfig(
    @Value("\${amazon.dynamodb.endpoint}")
    private val amazonDynamoDBEndpoint: String,
    @Value("\${amazon.dynamodb.region}")
    private val amazonDynamoDbRegion: String,
    @Value("\${amazon.aws.accesskey}")
    private val amazonAWSAccessKey: String,
    @Value("\${amazon.aws.secretkey}")
    private val amazonAWSSecretKey: String,
    @Value("\${spring.profiles.active:default}")
    private val activeProfile: String
) {

    @Bean
    fun amazonDynamoDB(): AmazonDynamoDB {
        return AmazonDynamoDBClientBuilder.standard()
                .withCredentials(AWSStaticCredentialsProvider(BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey)))
                .withEndpointConfiguration(EndpointConfiguration(amazonDynamoDBEndpoint, amazonDynamoDbRegion)).build()
    }

    @Bean
    fun dynamoDB(amazonDynamoDB: AmazonDynamoDB): DynamoDB {
        return DynamoDB(amazonDynamoDB)
    }

    @Bean
    fun dynamoDBMapperConfig(): DynamoDBMapperConfig {
        return DynamoDBMapperConfig.builder()
                .withTableNameOverride(TableNameOverride.withTableNamePrefix("${activeProfile}_"))
                .withTypeConverterFactory(DynamoDBTypeConverterFactory.standard()).build()
    }

    @Primary
    @Bean
    fun dynamoDbMapper(amazonDynamoDb: AmazonDynamoDB, dynamoDBMapperConfig: DynamoDBMapperConfig): DynamoDBMapper {
        return DynamoDBMapper(amazonDynamoDb, dynamoDBMapperConfig)
    }

}
