package io.stravgui.infrastructure.adapter.out.security;

import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class TokenEncryptionServiceTest {

    private final TokenEncryptionService service =
            new TokenEncryptionService(new EncryptionProperties("test-secret", "5f4dcc3b5aa765d6"));

    @Test
    void encrypt_then_decrypt_returns_the_original_value() {
        String original = "un-access-token-strava-tres-secret";

        String encrypted = service.encrypt(original);
        String decrypted = service.decrypt(encrypted);

        assertThat(decrypted).isEqualTo(original);
    }

    @Test
    void encrypt_produces_a_different_ciphertext_each_time_due_to_random_iv() {
        String original = "meme-valeur";

        String firstEncryption = service.encrypt(original);
        String secondEncryption = service.encrypt(original);

        assertThat(firstEncryption).isNotEqualTo(secondEncryption);
    }

    @Test
    void encrypted_value_does_not_contain_the_plaintext() {
        String secret = "valeur-sensible-a-proteger";

        String encrypted = service.encrypt(secret);

        assertThat(encrypted).doesNotContain(secret);
    }
}