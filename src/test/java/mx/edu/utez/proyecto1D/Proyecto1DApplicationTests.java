package mx.edu.utez.proyecto1D;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

// Usa el perfil h2 para que el test no necesite MySQL.
@SpringBootTest
@ActiveProfiles("h2")
class Proyecto1DApplicationTests {

	@Test
	void contextLoads() {
	}

}
