package ru.hogwarts.school.controller;

import io.swagger.v3.oas.annotations.tags.Tag;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/info")
@Tag(name = "Информация", description = "Методы для получения информации о порте")
public class InfoController {
    private static final Logger logger = LoggerFactory.getLogger(InfoController.class);

    @Value("${server.port}")
    private String serverPort;

    @GetMapping("/port")
    public String getServerPort() {
        logger.info("Вызван метод для получения порта приложения.");
        return "Порт приложения: " + serverPort;
    }

}
