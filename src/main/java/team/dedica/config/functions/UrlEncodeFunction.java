package team.dedica.config.functions;

import jakarta.annotation.Nonnull;

import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

/// # urlEncode()
///
/// Encodes the argument for safe use in URLs using UTF-8 encoding.
///
/// This is useful when composing URLs from properties that may contain special characters,
/// such as database passwords with `@`, `#`, or `%`:
/// ```yaml
///     jdbc-url: "jdbc:postgresql://user:${fn.urlEncode(${DB_PASSWORD})}@localhost:5432/mydb"
/// ```
class UrlEncodeFunction implements ConfigFunction {

    @Nonnull
    @Override
    public String call(@Nonnull final String argumentLine) {
        return URLEncoder.encode(argumentLine, StandardCharsets.UTF_8);
    }
}
