package team.dedica.config.functions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatExceptionOfType;

class RequireResolvedFunctionTest {

    /// Used to test [RequireResolvedFunction].
    private FunctionsPropertySource functionsPropertySource;

    @BeforeEach
    void setup() {
        functionsPropertySource = new FunctionsPropertySource();
    }

    @Test
    void returnsValueIfFullyResolved() {
        final Object result = functionsPropertySource.getProperty("fn.requireResolved(mySecretPassword)");

        assertThat(result)
            .isInstanceOf(String.class)
            .asString()
            .isEqualTo("mySecretPassword");
    }

    @Test
    void returnsValueIfFullyResolvedWithSurroundingText() {
        final Object result = functionsPropertySource.getProperty("fn.requireResolved(jdbc:postgresql://localhost:5432/mydb)");

        assertThat(result)
            .isInstanceOf(String.class)
            .asString()
            .isEqualTo("jdbc:postgresql://localhost:5432/mydb");
    }

    @Test
    void throwsExceptionIfSingleVariableIsUnresolved() {
        assertThatExceptionOfType(RequireResolvedFunction.UnresolvedVariableException.class)
            .isThrownBy(() -> functionsPropertySource.getProperty("fn.requireResolved(${DB_PASSWORD})"))
            .withMessageContaining("${DB_PASSWORD}");
    }

    @Test
    void throwsExceptionIfMultipleVariablesAreUnresolved() {
        assertThatExceptionOfType(RequireResolvedFunction.UnresolvedVariableException.class)
            .isThrownBy(() -> functionsPropertySource.getProperty("fn.requireResolved(jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME})"))
            .withMessageContaining("${DB_HOST}")
            .withMessageContaining("${DB_PORT}")
            .withMessageContaining("${DB_NAME}");
    }

    @Test
    void throwsExceptionIfOnlySomeVariablesAreUnresolved() {
        assertThatExceptionOfType(RequireResolvedFunction.UnresolvedVariableException.class)
            .isThrownBy(() -> functionsPropertySource.getProperty("fn.requireResolved(jdbc:postgresql://localhost:5432/${DB_NAME})"))
            .withMessageContaining("${DB_NAME}");
    }

    @Test
    void returnsEmptyStringIfArgumentIsEmpty() {
        final Object result = functionsPropertySource.getProperty("fn.requireResolved()");

        assertThat(result)
            .isInstanceOf(String.class)
            .asString()
            .isEmpty();
    }
}
