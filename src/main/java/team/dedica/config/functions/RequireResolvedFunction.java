package team.dedica.config.functions;

import jakarta.annotation.Nonnull;

import java.util.List;
import java.util.regex.MatchResult;
import java.util.regex.Pattern;

/// # requireResolved()
///
/// This function checks that all property placeholders in the argument have been resolved.
/// If unresolved placeholders are detected, an [IllegalArgumentException] is thrown.
///
/// Example usage:
/// ```yaml
///     db-password: "${fn.requireResolved(${DB_PASSWORD})}"
/// ```
///
/// If `DB_PASSWORD` is set, the resolved value is returned as-is.
/// If `DB_PASSWORD` is not set, Spring will pass the literal string `${DB_PASSWORD}` through,
/// and this function will throw an error:
/// ```
///     The following variables have not been resolved: ${DB_PASSWORD}
/// ```
///
/// Multiple variables can be used:
/// ```yaml
///     jdbc-url: "${fn.requireResolved(jdbc:postgresql://${DB_HOST}:${DB_PORT}/${DB_NAME})}"
/// ```
/// If any of them are unresolved, all unresolved variables are listed in the error message.
class RequireResolvedFunction implements ConfigFunction {

    /// Pattern to detect unresolved property placeholders like `${SOME_VARIABLE}`.
    private static final Pattern UNRESOLVED_PLACEHOLDER_PATTERN = Pattern.compile("\\$\\{[^}]+}");

    @Nonnull
    @Override
    public String call(@Nonnull final String argumentLine) {
        final List<String> unresolvedPlaceholders = UNRESOLVED_PLACEHOLDER_PATTERN.matcher(argumentLine)
            .results()
            .map(MatchResult::group)
            .toList();

        if (!unresolvedPlaceholders.isEmpty()) {
            throw new IllegalArgumentException(
                "The following variables have not been resolved: " + String.join(", ", unresolvedPlaceholders)
            );
        }

        return argumentLine;
    }
}
