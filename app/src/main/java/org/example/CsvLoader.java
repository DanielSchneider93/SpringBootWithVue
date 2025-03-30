package org.example;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;

import jakarta.annotation.PostConstruct;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.nio.charset.StandardCharsets;

@Component
public class CsvLoader {
    private static final Logger log = LoggerFactory.getLogger(CsvLoader.class);
    private final PersonRepository personRepository;
    private File csv = null;

    public CsvLoader(PersonRepository personRepository) {
        this.personRepository = personRepository;
    }

    public void loadCsv() {
        try {
            var resource = new ClassPathResource("static/sample-input.csv");
            var file = resource.getFile();
            if (file.exists()) {
                this.csv = file;
            } else {
                log.error("CsvLoader: CSV file nicht gefunden");
            }
        } catch (IOException e) {
            log.error("CsvLoader: Fehler beim Laden der CSV: " + e);
        }
    }

    @PostConstruct
    public void loadCsvData() {
        loadCsv();
        log.info("CsvLoader: Loading CSV Data ...");

        try (var br = new BufferedReader(new InputStreamReader(new FileInputStream(csv), StandardCharsets.UTF_8))) {
            String line;
            var lineCounter = 1;

            while ((line = br.readLine()) != null) {
                String[] parts = line.split(",");

                if (parts.length < 4) {
                    log.error("CsvLoader: Invalid data in line: " + lineCounter);
                    lineCounter++;
                    continue;
                }
                var lastName = parts[0].trim();
                var firstName = parts[1].trim();
                String[] locationData = parts[2].trim().split(" ", 2);

                if (locationData.length < 2) {
                    log.error("CsvLoader: Invalid data for zipcode/city in line: " + lineCounter);
                    lineCounter++;
                    continue;
                }

                var zip = locationData[0].trim();
                var city = locationData[1].trim();
                var colorCode = Long.parseLong(parts[3].trim());
                var color = Color.getColorById(colorCode);
                var person = new Person(firstName, lastName, zip, city, color);
                personRepository.save(person);
                lineCounter++;
            }
        } catch (IOException e) {
            log.error("CsvLoader: Error reading CSV File: " + e);
        }
    }
}