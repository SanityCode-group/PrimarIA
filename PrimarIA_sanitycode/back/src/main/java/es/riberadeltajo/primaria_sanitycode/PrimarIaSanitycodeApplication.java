package es.riberadeltajo.primaria_sanitycode;

import es.riberadeltajo.primaria_sanitycode.repository.CasoClinicoOriginalRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

@SpringBootApplication
public class PrimarIaSanitycodeApplication {

    public static void main(String[] args) {
        SpringApplication.run(PrimarIaSanitycodeApplication.class, args);
    }


    //PROBANDO MUESTRA DE PRIMER CASO CLÍNICO
    /*
    @Bean
    public CommandLineRunner mostrarPrimerCaso(CasoClinicoOriginalRepository repo) {
        return args -> {
            repo.findAll().stream().findFirst().ifPresent(caso -> {
                System.out.println("\n******************************************************");
                System.out.println("Primer caso clínico:");
                System.out.println("ID: " + caso.getId());
                System.out.println("Edad: " + caso.getEdad());
                System.out.println("Sexo: " + caso.getSexo());
                System.out.println("Motivo consulta: " + caso.getMotivo());
                System.out.println("Diagnóstico final: " + caso.getDiagnostico_final());
                System.out.println("******************************************************");
            });
        };
    }
    */
}
