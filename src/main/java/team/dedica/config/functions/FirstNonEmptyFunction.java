package team.dedica.config.functions;

import org.springframework.lang.NonNull;
import org.springframework.lang.Nullable;

import java.util.stream.Stream;

public class FirstNonEmptyFunction implements ConfigFunction {

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
