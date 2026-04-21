package team.dedica.config.functions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class FunctionsPropertySourceTest {

    /// System under test.
    private FunctionsPropertySource functionsPropertySource;

    @BeforeEach
    void setup() {
        functionsPropertySource = new FunctionsPropertySource();
    }

    @Test
    void returnsNullIfPropertyIsNotAFunctionCall() {
        final Object result = functionsPropertySource.getProperty("unrelated");

        assertThat(result).isNull();
    }

    @Test
    void throwsExceptionIfCalledFunctionIsNotSupported() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> functionsPropertySource.getProperty("fn.unknown()"));
    }

    @Test
    void throwsExceptionIfBracesOfFunctionCallAreMissing() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> functionsPropertySource.getProperty("fn.firstNonEmpty a"));
    }

    @Test
    void throwsExceptionIfBracesOfFunctionCallAreSwapped() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> functionsPropertySource.getProperty("fn.firstNonEmpty)a("));
    }

    @Test
    void worksIfFunctionCallIsSurroundedByWhitespace() {
        final Object result = functionsPropertySource.getProperty("  fn.firstNonEmpty(a)  ");

        assertThat(result).isNotNull();
    }

    @Test
    void throwsExceptionIfFunctionCallIsFollowedByInvalidData() {
        assertThatExceptionOfType(IllegalArgumentException.class)
            .isThrownBy(() -> functionsPropertySource.getProperty("fn.firstNonEmpty(a) this text should not be here"));
    }
}
