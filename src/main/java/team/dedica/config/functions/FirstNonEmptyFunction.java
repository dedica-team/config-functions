package team.dedica.config.functions;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.stream.Stream;

/// # firstNonEmpty()
///
/// This function accepts a comma separated list of string and returns the first one
/// that is not blank.
///
/// The following call returns "a":
/// ```yaml
///     my-property: "${fn.firstNonEmpty(,a,b)}"
/// ```
///
/// The function is useful when combined with other properties that may
/// be resolved to empty strings:
/// ```yaml
///     release-version: "${fn.firstNonEmpty(${git.tags:}, ${git.commit.id.abbrev:})}"
/// ```
///
/// Do not forget to define fallback values (via ":") for properties that may not exist
/// at all.
/// Otherwise, these properties will not be replaced at all when they are missing, which
/// leads to a placeholder string that is considered "non-empty" and returned.
///
/// If all arguments are empty, then `firstNonEmpty()` will not be resolved at all.
/// To avoid that, add a fallback value to the end:
/// ```yaml
///     release-version: "${fn.firstNonEmpty(${git.tags:}, ${git.commit.id.abbrev:}, unknown)}"
/// ```
/// In the example above, "unknown" is returned if no further suitable release version can be resolved.
class FirstNonEmptyFunction implements ConfigFunction {

    @Nullable
    @Override
    public String call(@NonNull final String argumentLine) {
        return Stream.of(argumentLine.split(","))
            .map(String::trim)
            .filter(argument -> !argument.isBlank())
            .findFirst()
            .orElse(null);
    }
}
