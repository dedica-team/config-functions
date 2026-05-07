package team.dedica.config.functions;

import jakarta.annotation.Nonnull;

import java.nio.charset.StandardCharsets;
import java.util.Base64;

/// # base64Decode()
///
/// Decodes a Base64-encoded argument and returns the result.
///
/// This is useful when configuration values are passed as Base64-encoded strings,
/// for example SSL certificates or keys provided via environment variables:
/// ```yaml
///     ssl-certificate: "${fn.base64Decode(${SSL_CERT_BASE64})}"
/// ```
class Base64DecodeFunction implements ConfigFunction {

    @Nonnull
    @Override
    public String call(@Nonnull final String argumentLine) {
        try {
            return new String(Base64.getMimeDecoder().decode(argumentLine), StandardCharsets.UTF_8);
        } catch (Exception e) {
            throw new Base64DecodingException(argumentLine, e);
        }
    }

    /// Used when a value cannot be decoded as base64.
    public static class Base64DecodingException extends IllegalArgumentException {

        public Base64DecodingException(@Nonnull final String value, @Nonnull final Throwable cause) {
            super(
                """
                A value could not be decoded as base64.
                Reason: %s
                Value:
                %s
                """.formatted(cause.getMessage(), value),
                cause
            );
        }
    }
}
