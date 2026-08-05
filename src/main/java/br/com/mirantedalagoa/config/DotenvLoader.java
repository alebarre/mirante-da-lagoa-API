package br.com.mirantedalagoa.config;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;

public final class DotenvLoader {

    private static final Logger logger = LoggerFactory.getLogger(DotenvLoader.class);

    private DotenvLoader() {}

    public static void load() {
        Path envFile = Paths.get(".env");
        if (!Files.exists(envFile)) {
            logger.info("Arquivo .env nao encontrado em {}. Usando variaveis de ambiente do sistema.", envFile.toAbsolutePath());
            return;
        }

        try {
            List<String> lines = Files.readAllLines(envFile);
            for (String line : lines) {
                String trimmed = line.trim();
                if (trimmed.isEmpty() || trimmed.startsWith("#")) {
                    continue;
                }
                int idx = trimmed.indexOf('=');
                if (idx == -1) {
                    continue;
                }
                String key = trimmed.substring(0, idx).trim();
                String value = trimmed.substring(idx + 1).trim();
                // Remove aspas simples ou duplas
                if ((value.startsWith("\"") && value.endsWith("\"")) ||
                    (value.startsWith("'") && value.endsWith("'"))) {
                    value = value.substring(1, value.length() - 1);
                }
                if (System.getProperty(key) == null && System.getenv(key) == null) {
                    System.setProperty(key, value);
                    logger.debug("Carregada variavel .env: {}", key);
                }
            }
            logger.info("Arquivo .env carregado de {}", envFile.toAbsolutePath());
        } catch (IOException e) {
            logger.warn("Falha ao ler .env: {}", e.getMessage());
        }
    }
}
