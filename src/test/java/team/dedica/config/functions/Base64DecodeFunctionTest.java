package team.dedica.config.functions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

@DisplayName("base64Decode()")
class Base64DecodeFunctionTest {

    /// Used to test [Base64DecodeFunction].
    private FunctionsPropertySource functionsPropertySource;

    @BeforeEach
    void setup() {
        functionsPropertySource = new FunctionsPropertySource();
    }

    @Test
    void decodesBase64EncodedString() {
        final String encoded = Base64.getEncoder().encodeToString("Hello, World!".getBytes(StandardCharsets.UTF_8));

        final Object result = functionsPropertySource.getProperty("fn.base64Decode(" + encoded + ")");

        assertThat(result)
            .isInstanceOf(String.class)
            .asString()
            .isEqualTo("Hello, World!");
    }

    @Test
    void decodesBase64EncodedStringWithSpecialCharacters() {
        final String encoded = Base64.getEncoder().encodeToString("p@ssw#rd!".getBytes(StandardCharsets.UTF_8));

        final Object result = functionsPropertySource.getProperty("fn.base64Decode(" + encoded + ")");

        assertThat(result)
            .isInstanceOf(String.class)
            .asString()
            .isEqualTo("p@ssw#rd!");
    }

    @Test
    void decodesEmptyString() {
        final Object result = functionsPropertySource.getProperty("fn.base64Decode()");

        assertThat(result)
            .isInstanceOf(String.class)
            .asString()
            .isEmpty();
    }

    @Test
    void throwsExceptionForInvalidBase64() {
        assertThatExceptionOfType(Base64DecodeFunction.Base64DecodingException.class)
            .isThrownBy(() -> functionsPropertySource.getProperty("fn.base64Decode(invalid base64!!!)"));
    }
}
