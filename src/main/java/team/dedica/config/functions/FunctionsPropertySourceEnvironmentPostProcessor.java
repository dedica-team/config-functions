package team.dedica.config.functions;

import jakarta.annotation.Nonnull;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.env.EnvironmentPostProcessor;
import org.springframework.core.Ordered;
import org.springframework.core.env.ConfigurableEnvironment;
import org.springframework.core.env.MutablePropertySources;
import org.springframework.core.env.PropertySource;

/// Registers the [FunctionsPropertySource].
///
/// This processor must be registered as `org.springframework.boot.env.EnvironmentPostProcessor`
/// in `src/main/resources/META-INF/spring.factories` to become active.
///
/// @see FunctionsPropertySource
public class FunctionsPropertySourceEnvironmentPostProcessor implements EnvironmentPostProcessor, Ordered {

    /// The default order of this post-processor.
    private static final int ORDER = Ordered.LOWEST_PRECEDENCE;

    @Override
    public void postProcessEnvironment(
        @Nonnull final ConfigurableEnvironment environment,
        @Nonnull final SpringApplication application
    ) {
        final MutablePropertySources sources = environment.getPropertySources();
        final PropertySource<?> existing = sources.get(FunctionsPropertySource.FUNCTIONS_PROPERTY_SOURCE_NAME);
        if (existing != null) {
            return;
        }
        final FunctionsPropertySource functionsSource = new FunctionsPropertySource();
        sources.addLast(functionsSource);
    }

    @Override
    public int getOrder() {
        return ORDER;
    }
}
