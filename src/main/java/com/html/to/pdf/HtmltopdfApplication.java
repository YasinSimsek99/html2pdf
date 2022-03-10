package com.html.to.pdf;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@SpringBootApplication
public class HtmltopdfApplication {

    public static void main(String[] args) {
        createDirectories();
        SpringApplication.run(HtmltopdfApplication.class, args);
    }

    private static void createDirectories() {
        try {
            Files.createDirectories(Paths.get("pdf"));
            Files.createDirectories(Paths.get("zip"));
        } catch (IOException e) {
            e.printStackTrace();
        }

    }

}
