package br.com.alura.fipetable;

import br.com.alura.fipetable.main.Main;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class FipetableApplication implements CommandLineRunner {

	public static void main(String[] args) {
		SpringApplication.run(FipetableApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		Main main = new Main();
		main.displayMenu();
	}
}
