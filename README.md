# Spring Boot Config Functions

Call functions directly in your Spring Boot `application.yaml`.

```yaml
release-version: "${fn.firstNonEmpty(${git.tags:}, ${git.commit.id.abbrev:}, unknown)}"
```

## Getting Started

Add the dependency to your `pom.xml`:

```xml
<dependency>
    <groupId>team.dedica</groupId>
    <artifactId>config-functions</artifactId>
    <version>0.1.0</version>
</dependency>
```

That's it. The library is automatically activated via Spring Boot's `EnvironmentPostProcessor` mechanism.

## Usage

Function calls are prefixed with `fn.` and can be used like any other property placeholder:

```yaml
my-property: "${fn.functionName(arguments)}"
```

You can pass other Spring properties as arguments:

```yaml
my-property: "${fn.functionName(${spring.application.name})}"
```

## Available Functions

### `firstNonEmpty`

Returns the first non-blank value from a comma-separated list of arguments.

```yaml
my-property: "${fn.firstNonEmpty(,a,b)}"  # returns "a"
```

This is useful for defining fallback chains:

```yaml
release-version: "${fn.firstNonEmpty(${git.tags:}, ${git.commit.id.abbrev:}, unknown)}"
```

In this example, the release version is resolved from the following sources in order:

1. The Git tag (if available)
2. The abbreviated commit hash (if available)
3. The literal string `unknown`

### Important: Define fallback values for optional properties

Always use the `:` suffix for properties that may not exist:

```yaml
# Good - empty string fallback if the property is missing
"${fn.firstNonEmpty(${my.optional.property:}, fallback)}"

# Bad - if the property is missing, the literal string "${my.optional.property}" is used,
# which is non-empty and will be returned as-is
"${fn.firstNonEmpty(${my.optional.property}, fallback)}"
```

## Requirements

- Spring Boot 3.x
- Java 17+

## License

[MIT](LICENSE)
