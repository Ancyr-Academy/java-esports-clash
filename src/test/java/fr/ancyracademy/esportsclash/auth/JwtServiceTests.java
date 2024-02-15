package fr.ancyracademy.esportsclash.auth;

import fr.ancyracademy.esportsclash.auth.application.services.jwtservice.ConcreteJwtService;
import fr.ancyracademy.esportsclash.auth.domain.model.User;
import org.junit.Assert;
import org.junit.jupiter.api.Test;

public class JwtServiceTests {
  @Test
  void shouldTokenizeTheUser() {
    var jwtService = new ConcreteJwtService(
        "sooper_sekret_please_do_not_share",
        60
    );

    var user = new User("123", "contact@ancyracademy.fr", "azerty");

    var token = jwtService.tokenize(user);
    var authUser = jwtService.parse(token);

    Assert.assertNotNull(authUser);
    Assert.assertEquals(user.getId(), authUser.getId());
    Assert.assertEquals(user.getEmailAddress(), authUser.getEmailAddress());
  }
}
