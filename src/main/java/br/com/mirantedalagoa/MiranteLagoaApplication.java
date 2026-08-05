package br.com.mirantedalagoa;

import br.com.mirantedalagoa.config.DotenvLoader;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class MiranteLagoaApplication {
    public static void main(String[] args) {
        DotenvLoader.load();
        SpringApplication.run(MiranteLagoaApplication.class, args);
    }
}
