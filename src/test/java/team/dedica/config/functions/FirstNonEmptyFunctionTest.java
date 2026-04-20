package team.dedica.config.functions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

@DisplayName("firstNonEmpty()")
class FirstNonEmptyFunctionTest {

    /// Used to test [FirstNonEmptyFunction].
    private FunctionsPropertySource functionsPropertySource;

    @BeforeEach
    void setup() {
        functionsPropertySource = new FunctionsPropertySource();
    }

    @Test
    void returnsNullIfAllArgumentStringsAreEmpty() {
        final Object result = functionsPropertySource.getProperty("fn.firstNonEmpty(,,,,,)");

        assertThat(result).isNull();
    }

    @Test
    void returnsNullIfArgumentListIsEmpty() {
        final Object result = functionsPropertySource.getProperty("fn.firstNonEmpty()");

        assertThat(result).isNull();
    }

    @Test
    void returnsNullIfArgumentListContainsOnlyWhitespace() {
        final Object result = functionsPropertySource.getProperty("fn.firstNonEmpty(    )");

        assertThat(result).isNull();
    }

    @Test
    void returnsFirstNonEmptyValue() {
        final Object result = functionsPropertySource.getProperty("fn.firstNonEmpty(,,A,B)");

        assertThat(result)
            .isInstanceOf(String.class)
            .asString()
            .isEqualTo("A");
    }

    @Test
    void stripsWhitespaceFromFirstNonEmptyValue() {
        final Object result = functionsPropertySource.getProperty("fn.firstNonEmpty(,,  A  ,B)");

        assertThat(result)
            .isInstanceOf(String.class)
            .asString()
            .isEqualTo("A");
    }
}
