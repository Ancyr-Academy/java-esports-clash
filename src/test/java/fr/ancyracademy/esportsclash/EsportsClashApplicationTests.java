package fr.ancyracademy.esportsclash;

import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.context.annotation.Import;

@SpringBootTest
@Import(PostgreSQLTestConfiguration.class)
class EsportsClashApplicationTests {
  @Test
  void contextLoads() {
  }
}
