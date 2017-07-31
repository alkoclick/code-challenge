package app;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.http.MediaType;

import control.ScanControllers;

@SpringBootApplication(scanBasePackageClasses = { ScanControllers.class })
public class Application {
	public static final MediaType CONTENT_TYPE = MediaType.APPLICATION_JSON_UTF8;

	public static void main(String[] args) {
		SpringApplication.run(Application.class, args);
	}
}