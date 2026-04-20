package team.dedica.config.functions;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Nested;
import org.junit.jupiter.api.Test;

import static org.assertj.core.api.Assertions.assertThat;

class ConfigFunctionTest {

    @DisplayName("firstNonEmpty([...])")
    @Nested
    class NameTests {

        @DisplayName("Default Behavior")
        @Nested
        class DefaultBehaviorTests {

            @Test
            void removesFunctionSuffix() {
                final ConfigFunction function = new GreetFunction();

                final String name = function.name();

                assertThat(name).doesNotEndWithIgnoringCase("function");
            }

            @Test
            void convertsFirstCharacterToLowerCase() {
                final ConfigFunction function = new GreetFunction();

                final String name = function.name();

                assertThat(name).isEqualTo("greet");
            }

            @Test
            void worksWithClassWithoutFunctionSuffix() {
                final ConfigFunction function = new Greet();

                final String name = function.name();

                assertThat(name).isEqualTo("greet");
            }

            private static class Greet implements ConfigFunction {
                @Nullable
                @Override
                public Object call(@Nonnull final String argumentLine) {
                    return "Greetings!";
                }
            }

            private static class GreetFunction implements ConfigFunction {

                @Nullable
                @Override
                public Object call(@Nonnull final String argumentLine) {
                    return "Hello!";
                }
            }
        }

        /// Shows how the function name can be overridden, which allows (in theory) even the usage of
        /// anonymous [ConfigFunction] classes.
        ///
        /// That does not imply that it is a good idea to do that.
        @Test
        void canBeOverridden() {
            final ConfigFunction anonymousFunction = new ConfigFunction() {
                @Nullable
                @Override
                public Object call(@Nonnull final String argumentLine) {
                    return null;
                }

                @Override
                public String name() {
                    return "myAnonymousFunction";
                }
            };

            final String name = anonymousFunction.name();

            assertThat(name).isEqualTo("myAnonymousFunction");
        }
    }
}
