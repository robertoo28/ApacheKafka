package com.example.kafkasecondsub;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;

@Service
public class SecondKafkaConsumer {

    // Ruta completa donde se guardará el archivo
    private static final String FILE_PATH = "/app/mensajes.txt";
    @KafkaListener(topics = "another-topic", groupId = "group1")
    public void consumeMessageFromTopic2(String message) {
        System.out.println("Mensaje recibido en 'another-topic': " + message);
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

