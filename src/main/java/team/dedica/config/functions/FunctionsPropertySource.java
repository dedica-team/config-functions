package team.dedica.config.functions;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;
import org.springframework.core.env.PropertySource;

import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.function.Function;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

/// Adds support for simple calls to prepared functions in `application.yaml` configuration files.
///
/// Function calls are prefixed with `fn.` and can be used like properties in the `application.yaml`
/// config file:
/// ```yaml
///     my-property: "${fn.functionName(arguments)}"
/// ```
/// It is also possible to pass other properties as arguments:
/// ```yaml
///     my-property: "${fn.functionName(${spring.application.name})}"
/// ```
///
/// @see #functions
/// @see ConfigFunction
public class FunctionsPropertySource extends PropertySource<Object> {

    /// Name of the [PropertySource].
    public static final String FUNCTIONS_PROPERTY_SOURCE_NAME = "config.functions";

    /// Prefix that must be used for function calls.
    ///
    /// If the prefix is omitted, then this [PropertySource] will not even try to interpret
    /// a property as function call.
    private static final String PREFIX = "fn.";

    /// Regexp group that holds the function name.
    private static final String GROUP_NAME = "name";

    /// Regexp group that holds the argument line.
    private static final String GROUP_ARGUMENTS = "arguments";

    /// Used to match function calls and extract relevant data.
    private static final Pattern FUNCTION_CALL_PATTERN = Pattern.compile(
        Pattern.quote(PREFIX) + "(?<" + GROUP_NAME + ">[a-zA-Z0-9]+)\\((?<" + GROUP_ARGUMENTS + ">.*)\\)",
        // The "." also accepts newlines, which allows multiline arguments, e.g. for base64 encoded values.
        Pattern.DOTALL
    );

    private static final String FUNCTION_CALL_EXAMPLE = PREFIX + "functionName(my arguments)";

    /// Contains all supported functions.
    ///
    /// The key is the name of the function (without prefix) that can be used in the config file.
    /// The value is a [ConfigFunction] that receives the argument line and returns the result
    /// of the function call.
    @Nonnull
    private final Map<String, ConfigFunction> functions = register(
        new Base64DecodeFunction(),
        new FirstNonEmptyFunction(),
        new RequireResolvedFunction(),
        new UrlEncodeFunction()
    );

    /// Makes the given [config functions][ConfigFunction] available.
    @Nonnull
    private static Map<String, ConfigFunction> register(@Nonnull final ConfigFunction... functions) {
        return Arrays.stream(functions)
            .collect(Collectors.toUnmodifiableMap(
                ConfigFunction::name,
                Function.identity()
            ));
    }

    /// Creates the source with the default name ([#FUNCTIONS_PROPERTY_SOURCE_NAME]).
    public FunctionsPropertySource() {
        this(FUNCTIONS_PROPERTY_SOURCE_NAME);
    }

    /// Creates the source with the provided `name`.
    ///
    /// @param name The name of the property source.
    public FunctionsPropertySource(@Nonnull final String name) {
        super(Objects.requireNonNull(name, "name"), new Object());
    }

    @Nullable
    @Override
    public Object getProperty(@Nonnull final String propertyName) {
        Objects.requireNonNull(propertyName, "propertyName");

        final String propertyNameWithoutWhitespace = propertyName.trim();
        if (!propertyNameWithoutWhitespace.startsWith(PREFIX)) {
            // This property source is not responsible at all.
            return null;
        }

        final Matcher matcher = FUNCTION_CALL_PATTERN.matcher(propertyNameWithoutWhitespace);
        if (!matcher.matches()) {
            final String message = """
                                   Invalid function call '%s': Function call must look like %s.
                                   The following functions are supported: %s
                                   """
                .formatted(
                    propertyNameWithoutWhitespace,
                    FUNCTION_CALL_EXAMPLE,
                    String.join(", ", functions.keySet())
                );
            throw new IllegalArgumentException(message);
        }

        final String functionName = matcher.group(GROUP_NAME);
        if (!functions.containsKey(functionName)) {
            final String message = """
                                   The function '%s' is not available.
                                   The following functions are supported: %s
                                   """
                .formatted(
                    functionName,
                    String.join(", ", functions.keySet())
                );
            throw new IllegalArgumentException(message);
        }

        final String arguments = matcher.group(GROUP_ARGUMENTS);
        return functions.get(functionName).call(arguments);
    }
}
