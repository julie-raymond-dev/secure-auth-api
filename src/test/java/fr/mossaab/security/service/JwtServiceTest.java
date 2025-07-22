package fr.mossaab.security.service;

import fr.mossaab.security.entities.User;
import fr.mossaab.security.enums.Role;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.test.context.ActiveProfiles;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
class JwtServiceTest {

    @Autowired
    private JwtService jwtService;

    @Test
    void generateToken_then_extractUserName_matchesEmail() {
        // given
        UserDetails user = User.builder()
                .id(1L)
                .email("john.doe@example.com")
                .password("password")
                .role(Role.USER)
                .firstname("John")
                .lastname("Doe")
                .build();

        // when
        String token = jwtService.generateToken(user);
        String extracted = jwtService.extractUserName(token);

        // then
        assertThat(extracted).isEqualTo(user.getUsername());
    }

    @Test
    void generatedToken_shouldBeValid_forSameUser() {
        UserDetails user = User.builder()
                .id(2L)
                .email("alice@example.com")
                .password("secret")
                .role(Role.USER)
                .firstname("Alice")
                .lastname("Wonder")
                .build();

        String token = jwtService.generateToken(user);
        boolean valid = jwtService.isTokenValid(token, user);

        assertThat(valid).isTrue();
    }
}
