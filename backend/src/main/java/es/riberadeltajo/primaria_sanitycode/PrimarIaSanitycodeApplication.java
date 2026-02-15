package es.riberadeltajo.primaria_sanitycode;

import es.riberadeltajo.primaria_sanitycode.repository.CasoClinicoMuestraRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PrimarIaSanitycodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrimarIaSanitycodeApplication.class, args);
    }

    // PROBANDO MUESTRA DE CASO CLÍNICO POR CONSOLA
    @Bean
    public CommandLineRunner mostrarCasoAleatorio(CasoClinicoMuestraRepository repo) {
        return args -> {

            System.out.println(">>> RUNNER INICIADO <<<");

            long total = repo.count();
            System.out.println(">>> TOTAL REGISTROS EN MUESTRA: " + total);

            System.out.println(">>> OBTENIENDO CASO ALEATORIO <<<");
            var caso = repo.findRandomCasoClinico();

            if (caso == null) {
                System.out.println("⚠ No se encontró ningún caso clínico en la tabla de muestra.");
                return;
            }

            System.out.println("\n******************************************************");
            System.out.println("Caso clínico aleatorio (MUESTRA):");
            System.out.println("ID: " + caso.getId());
            System.out.println("Edad: " + caso.getCasoOriginal().getEdad());
            System.out.println("Sexo: " + caso.getCasoOriginal().getSexo());
            System.out.println("Motivo consulta: " + caso.getCasoOriginal().getMotivo());
            System.out.println("Diagnóstico final: " + caso.getCasoOriginal().getDiagnostico_final());
            System.out.println("******************************************************");
        };
    }


}
