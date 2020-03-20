package com.ryulth.woom.config

import com.amazonaws.auth.AWS4Signer
import com.amazonaws.auth.AWSStaticCredentialsProvider
import com.amazonaws.auth.BasicAWSCredentials
import com.amazonaws.http.AWSRequestSigningApacheInterceptor
import org.apache.http.HttpHost
import org.apache.http.HttpRequestInterceptor
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder
import org.elasticsearch.client.RestClient
import org.elasticsearch.client.RestHighLevelClient
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration

@Configuration
class ElasticsearchConfig(
    @Value("\${elasticsearch.url}")
    private val elasticsearchUrl: String,
    @Value("\${amazon.aws.region}")
    private val amazonAWSRegion: String,
    @Value("\${amazon.aws.accesskey}")
    private val amazonAWSAccessKey: String,
    @Value("\${amazon.aws.secretkey}")
    private val amazonAWSSecretKey: String
) {
    companion object {
        const val AUTHORIZATION = "Authorization"
        const val SERVICE_NAME = "es"
    }

    @Bean("elasticsearchClient")
    fun elasticsearchClient(): RestHighLevelClient {
        val signer = AWS4Signer()
        val credentialsProvider = AWSStaticCredentialsProvider(BasicAWSCredentials(amazonAWSAccessKey, amazonAWSSecretKey))
        signer.serviceName = SERVICE_NAME
        signer.regionName = amazonAWSRegion
        val interceptor: HttpRequestInterceptor = AWSRequestSigningApacheInterceptor(SERVICE_NAME, signer, credentialsProvider)
        return RestHighLevelClient(RestClient.builder(HttpHost.create(elasticsearchUrl))
                .setHttpClientConfigCallback { httpClientBuilder: HttpAsyncClientBuilder ->
                    httpClientBuilder.addInterceptorLast(interceptor)
                })
    }
//    private fun awsAuthorization(): String {
//
//        val credentialScope = "$dateStamp/$amazonAWSRegion/es/aws4_request"
//        val signature = getSignatureKey(amazonAWSAccessKey, dateStamp, amazonAWSRegion)
//        val signedHeaders = "host;x-amz-date"
//        return "AWS4-HMAC-SHA256 Credential=${amazonAWSAccessKey}/$credentialScope, SignedHeaders=$signedHeaders, Signature=$signature"
//    }
}
