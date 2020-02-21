package com.ryulth.woom.config

import com.ryulth.woom.util.StringUtils.WOOM_PREFIX
import org.eclipse.paho.client.mqttv3.MqttConnectOptions
import org.springframework.beans.factory.annotation.Value
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.integration.annotation.MessagingGateway
import org.springframework.integration.annotation.ServiceActivator
import org.springframework.integration.channel.DirectChannel
import org.springframework.integration.mqtt.core.DefaultMqttPahoClientFactory
import org.springframework.integration.mqtt.core.MqttPahoClientFactory
import org.springframework.integration.mqtt.outbound.MqttPahoMessageHandler
import org.springframework.integration.mqtt.support.MqttHeaders
import org.springframework.messaging.MessageChannel
import org.springframework.messaging.handler.annotation.Header
import org.springframework.messaging.handler.annotation.Payload

@Configuration
class MQTTConfig(
    @Value("\${mqtt.url}")
    val mqttURL: String,
    @Value("\${mqtt.username}")
    val userName: String,
    @Value("\${mqtt.password}")
    val password: String
) {
    companion object {
        fun generateClientId(): String {
            return WOOM_PREFIX + System.nanoTime()
        }
    }

    @Bean
    fun mqttClientFactory(): MqttPahoClientFactory {
        val factory = DefaultMqttPahoClientFactory()
        val mqttConnectOptions = MqttConnectOptions()
        mqttConnectOptions.serverURIs = arrayOf(mqttURL)
        mqttConnectOptions.userName = userName
        mqttConnectOptions.password = password.toCharArray()
        mqttConnectOptions.isCleanSession = true
        mqttConnectOptions.keepAliveInterval = 10
        mqttConnectOptions.connectionTimeout = 60
        factory.connectionOptions = mqttConnectOptions
        return factory
    }

    @Bean
    fun mqttOutboundChannel(): MessageChannel {
        return DirectChannel()
    }

    @Bean
    @ServiceActivator(inputChannel = "mqttOutboundChannel")
    fun mqttOutbound(): MqttPahoMessageHandler {
        val messageHandler = MqttPahoMessageHandler(generateClientId(), mqttClientFactory())
        messageHandler.setAsync(true)
        messageHandler.setDefaultQos(2)
        return messageHandler
    }

    @MessagingGateway(defaultRequestChannel = "mqttOutboundChannel")
    interface MQTTGateway {
        fun publish(@Header(MqttHeaders.TOPIC) topic: String, @Payload payload: String)
    }
}
