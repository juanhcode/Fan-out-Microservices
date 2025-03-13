# 📌 Microservicios con SNS y SQS (Patrón Fan-Out)

Este proyecto implementa una arquitectura basada en microservicios utilizando **AWS SNS (Simple Notification Service) y SQS (Simple Queue Service)** para lograr un **patrón Fan-Out**.  

## 📖 Descripción  

El patrón **Fan-Out** permite que un mensaje enviado por un microservicio se distribuya a múltiples consumidores sin que el emisor tenga conocimiento de ellos. En este caso, un **Microservicio A** publica un mensaje en un **Topic SNS**, que luego es distribuido a una **cola SQS** a la que están suscritos otros microservicios, como el **Microservicio B**.  

## 🏗️ Flujo de Comunicación  

1. **Microservicio A** envía un mensaje a un **Topic SNS**.  
2. **SNS** distribuye el mensaje a la **cola SQS** suscrita.  
3. **Microservicio B** está suscrito a la cola **SQS** y consume los mensajes periódicamente.  

Este modelo permite desacoplar los productores de los consumidores, aumentando la escalabilidad y resiliencia del sistema.  

## 📌 Tecnologías Utilizadas  

- **Spring Boot** → Framework para desarrollo de microservicios.  
- **AWS SNS** → Servicio de notificación que distribuye mensajes a múltiples suscriptores.  
- **AWS SQS** → Servicio de colas que gestiona y almacena los mensajes en orden.  
- **AWS SDK para Java** → Biblioteca para interactuar con los servicios de AWS.  

## ⚙️ Configuración del Proyecto  

### 1️⃣ Configurar Credenciales de AWS  

Configura tus credenciales de AWS en un **archivo de propiedades o variables de entorno**, evitando incluirlas en el código fuente.  

Ejemplo en `application.properties` o `application.yml`:  
```properties
aws.accessKey=TU_ACCESS_KEY
aws.secretKey=TU_SECRET_KEY
aws.sqs.queueUrl=URL_DE_LA_COLA_SQS
aws.sns.topicArn=ARN_DEL_TOPIC_SNS
```

### 2️⃣ Crear un Topic SNS  

Desde la consola de AWS:  
1. Ir a **SNS → Crear un nuevo Topic**.  
2. Seleccionar **FIFO** si se necesita garantizar el orden.  
3. Guardar el **ARN** del Topic creado.  

### 3️⃣ Crear una Cola SQS  

1. Ir a **SQS → Crear Cola**.  
2. Seleccionar **FIFO** si es necesario mantener el orden de los mensajes.  
3. En la configuración de permisos, suscribir la cola al **Topic SNS** creado.  

### 4️⃣ Suscribir la Cola SQS al Topic SNS  

1. Ir al **Topic SNS** en la consola de AWS.  
2. Agregar una nueva suscripción y seleccionar **SQS** como destino.  
3. Introducir la **URL de la Cola SQS**.  



## 🏆 Beneficios del Patrón Fan-Out  

✅ **Desacoplamiento:** Los microservicios emisores no necesitan conocer a los consumidores.  
✅ **Escalabilidad:** Varios microservicios pueden consumir el mismo mensaje sin impactar al emisor.  
✅ **Resiliencia:** Si un consumidor falla, los mensajes se mantienen en la cola SQS hasta ser procesados.  
✅ **Garantía de Entrega:** SQS almacena los mensajes hasta que sean consumidos exitosamente.  

## 📌 Conclusión  

Este proyecto demuestra cómo implementar **un patrón Fan-Out con AWS SNS y SQS** para comunicar microservicios de manera eficiente y escalable. 🚀  


