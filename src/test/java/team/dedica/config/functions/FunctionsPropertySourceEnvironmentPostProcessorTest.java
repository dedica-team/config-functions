package team.dedica.config.functions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.SpringApplication;
import org.springframework.mock.env.MockEnvironment;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatCode;
import static org.mockito.Mockito.mock;

class FunctionsPropertySourceEnvironmentPostProcessorTest {

    /// System under test.
    private FunctionsPropertySourceEnvironmentPostProcessor processor;

    @BeforeEach
    void setup() {
        processor = new FunctionsPropertySourceEnvironmentPostProcessor();
    }

    @Test
    void registersPropertySource() {
        final MockEnvironment environment = new MockEnvironment();

        processor.postProcessEnvironment(environment, mock(SpringApplication.class));

        assertThat(environment.getPropertySources())
            .as("%s has not been registered.", FunctionsPropertySource.class.getSimpleName())
            .hasAtLeastOneElementOfType(FunctionsPropertySource.class);
    }

    @Test
    void registersPropertySourceOnlyOnceEvenIfCalledMultipleTimes() {
        final MockEnvironment environment = new MockEnvironment();
        final SpringApplication application = mock(SpringApplication.class);

        processor.postProcessEnvironment(environment, application);
        processor.postProcessEnvironment(environment, application);

        assertThat(environment.getPropertySources())
            .as("%s should be registered exactly once.", FunctionsPropertySource.class.getSimpleName())
            .satisfiesOnlyOnce(propertySource -> assertThat(propertySource).isInstanceOf(FunctionsPropertySource.class));
    }

    @SuppressWarnings({
        // We are currently not interested in the exact order value.
        // It is enough if the call does not fail.
        "ResultOfMethodCallIgnored"
    })
    @Test
    void providesOrder() {
        assertThatCode(() -> processor.getOrder())
            .doesNotThrowAnyException();
    }
}
