package com.develop.Microservice.B;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;
import software.amazon.awssdk.auth.credentials.AwsBasicCredentials;
import software.amazon.awssdk.auth.credentials.StaticCredentialsProvider;
import software.amazon.awssdk.regions.Region;
import software.amazon.awssdk.services.sqs.SqsClient;
import software.amazon.awssdk.services.sqs.model.*;

@Service
public class SqsConsumer {

    private final SqsClient sqsClient;
    private final String queueUrl;

    public SqsConsumer(@Value("${aws.accessKey}") String accessKey,
                       @Value("${aws.secretKey}") String secretKey,
                       @Value("${aws.sqs.queueUrl}") String queueUrl) {
        this.queueUrl = queueUrl;
        this.sqsClient = SqsClient.builder()
                .region(Region.US_EAST_1)
                .credentialsProvider(StaticCredentialsProvider.create(AwsBasicCredentials.create(accessKey, secretKey)))
                .build();
    }

    @Scheduled(fixedDelay = 5000)
    public void leerMensajes() {
        System.out.println("🔍 Buscando mensajes en la cola...");
        ReceiveMessageRequest request = ReceiveMessageRequest.builder()
                .queueUrl(queueUrl)
                .maxNumberOfMessages(1)
                .messageAttributeNames("All") // Asegura que obtienes atributos FIFO
                .build();

        sqsClient.receiveMessage(request).messages().forEach(message -> {
            System.out.println("📩 Mensaje recibido: " + message.body());

            // Borrar el mensaje después de procesarlo
            DeleteMessageRequest deleteRequest = DeleteMessageRequest.builder()
                    .queueUrl(queueUrl)
                    .receiptHandle(message.receiptHandle())
                    .build();
            sqsClient.deleteMessage(deleteRequest);
        });
    }
}
