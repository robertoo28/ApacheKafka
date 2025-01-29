package com.example.kafkasubscriber;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class KafkaConsumerService {

    // Ruta completa donde se guardará el archivo
    private static final String FILE_PATH = "/app/mensajes.txt";

    @KafkaListener(topics = "my-topic", groupId = "${spring.kafka.consumer.group-id}")
    public void consumeMessage(String message) {
        System.out.println("Mensaje recibido en suscriptor: " + message);
        writeMessageToFile(message);
    }
    private void writeMessageToFile(String message) {
        try {
            // Archivo en la ubicación especificada
            File file = new File(FILE_PATH);

            // Crea el archivo si no existe
            if (!file.exists()) {
                file.createNewFile();
            }

            // Escribe el mensaje en el archivo (modo append)
            try (BufferedWriter writer = new BufferedWriter(new FileWriter(file, true))) {
                writer.write(message);
                writer.newLine(); // Salto de línea para cada mensaje
            }

            System.out.println("Mensaje guardado en el archivo: " + FILE_PATH);

        } catch (IOException e) {
            System.err.println("Error al escribir el mensaje en el archivo: " + e.getMessage());
        }
    }
}
