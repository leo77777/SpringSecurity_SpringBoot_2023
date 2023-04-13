package fr.leo.springsecurity;

import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;

import fr.leo.springsecurity.entities.Patient;
import fr.leo.springsecurity.repository.PatientRepository;

import java.util.Date;

@SpringBootApplication
public class SpringSecurity {
    public static void main(String[] args) {
        SpringApplication.run(SpringSecurity.class, args);
    } 

    @Bean
    CommandLineRunner start(PatientRepository patientRepository){
        return args -> {
            patientRepository.save(new Patient(null,"Rere",new Date(),false,41));
            patientRepository.save(new Patient(null,"Vyvy",new Date(),true,95));
            patientRepository.save(new Patient(null,"Lala",new Date(),true,343));
            patientRepository.save(new Patient(null,"cycy",new Date(),false,124));
        };
    }
    
    // Avant on utilisait MD5, mais c'est à oublier !
    @Bean
    PasswordEncoder passwordEncoder() {
    	return new BCryptPasswordEncoder();
    }
}
