package team.dedica.config.functions;

import jakarta.annotation.Nonnull;
import jakarta.annotation.Nullable;

/// Implemented by functions that are supported in config files.
interface ConfigFunction {

    /// Calls the function with the given input.
    ///
    /// @param argumentLine The raw argument line, e.g. "a, b, c".
    @Nullable
    Object call(@Nonnull String argumentLine);

    /// The name of the function, which is used to reference it from config files.
    ///
    /// Per deafult, the name is derived from the class name:
    /// - The "Function"-Suffix (if it exists) is removed.
    /// - The first character is lower-cased.
    ///
    /// For more complex function name requirements, this method should be overridden.
    default String name() {
        String functionName = getClass().getSimpleName();

        final String suffixToBeRemoved = "Function";
        if (functionName.endsWith(suffixToBeRemoved)) {
            functionName = functionName.substring(0, functionName.length() - suffixToBeRemoved.length());
        }

        // Convert first character to lower case.
        functionName = Character.toLowerCase(functionName.charAt(0)) + functionName.substring(1);

        return functionName;
    }
}
