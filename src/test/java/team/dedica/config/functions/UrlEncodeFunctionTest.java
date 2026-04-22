package team.dedica.config.functions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class UrlEncodeFunctionTest {

    /// Used to test [UrlEncodeFunction].
    private FunctionsPropertySource functionsPropertySource;

    @BeforeEach
    void setup() {
        functionsPropertySource = new FunctionsPropertySource();
    }

    @Test
    void returnsRegularStringUnchanged() {
        final Object result = functionsPropertySource.getProperty("fn.urlEncode(hello)");

        assertThat(result)
            .isInstanceOf(String.class)
            .asString()
            .isEqualTo("hello");
    }

    @Test
    void encodesAtSign() {
        final Object result = functionsPropertySource.getProperty("fn.urlEncode(p@ssword)");

        assertThat(result)
            .isInstanceOf(String.class)
            .asString()
            .isEqualTo("p%40ssword");
    }

    @Test
    void encodesHash() {
        final Object result = functionsPropertySource.getProperty("fn.urlEncode(p#ssword)");

        assertThat(result)
            .isInstanceOf(String.class)
            .asString()
            .isEqualTo("p%23ssword");
    }

    @Test
    void encodesPercent() {
        final Object result = functionsPropertySource.getProperty("fn.urlEncode(100%done)");

        assertThat(result)
            .isInstanceOf(String.class)
            .asString()
            .isEqualTo("100%25done");
    }

    @Test
    void encodesSpaces() {
        final Object result = functionsPropertySource.getProperty("fn.urlEncode(hello world)");

        assertThat(result)
            .isInstanceOf(String.class)
            .asString()
            .isEqualTo("hello+world");
    }

    @Test
    void returnsEmptyStringIfArgumentIsEmpty() {
        final Object result = functionsPropertySource.getProperty("fn.urlEncode()");

        assertThat(result)
            .isInstanceOf(String.class)
            .asString()
            .isEmpty();
    }
}
