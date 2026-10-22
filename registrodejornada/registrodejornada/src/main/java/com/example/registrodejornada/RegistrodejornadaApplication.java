package com.example.registrodejornada;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Contact;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.annotations.info.License;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@OpenAPIDefinition(info =
@Info(description = "API REST para gestionar departamentos, empleados y el registro de jornada (fichajes)",
		version = "1.0",
		contact = @Contact(name = "torresargon25-cmd"),
		license = @License(name = "CC BY"),
		title = "API de registro de jornada"
)
)
@SpringBootApplication
public class RegistrodejornadaApplication {

	public static void main(String[] args) {
		SpringApplication.run(RegistrodejornadaApplication.class, args);
	}

}
