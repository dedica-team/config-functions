# Changelog

## Upcoming: 1.3.0

Features

Development
- Security: Pin references to Docker images and GitHub Actions

Dependency Updates

## 1.2.0 (2026-05-07)

Features
- Introduced function `base64Decode()`, which decodes a Base64-encoded configuration value ([#16](https://github.com/dedica-team/config-functions/pull/16), [#19](https://github.com/dedica-team/config-functions/pull/19))

Dependency Updates
- Spring Boot Dependencies `3.5.13` → `3.5.14`
- JReleaser (library publishing process) `1.23.0` → `1.24.0`

## 1.1.0 (2026-04-22)

Features
- Introduced function `requireResolved()`, which fails early if an environment variable reference could not be resolved ([#12](https://github.com/dedica-team/config-functions/pull/12))
- Introduced function `urlEncode()`, which encodes a value for safe use in URLs ([#13](https://github.com/dedica-team/config-functions/pull/13))

Development
- Refactoring: Introduced `ConfigFunction` interface as a mechanism to simplify adding new functions ([#10](https://github.com/dedica-team/config-functions/pull/10))

## 1.0.0 (2026-04-20)

Development
- Automated release process ([#7](https://github.com/dedica-team/config-functions/pull/7))
- Introduced `README.md`

Dependency Updates
- Maven `3.9.14` → `3.9.15`
- Maven Flatten Plugin (version replacement for releases) `1.6.0` → `1.7.3`
- Maven Javadoc Plugin (for releases) `3.11.2` → `3.12.0`
- Maven Source Plugin (for releases) `3.3.1` → `3.4.0`
- GitHub Actions
  - Changed Files (changelog check) `47.0.5` → `47.0.6`

## 0.1.0 (2026-04-12)

First release on Maven Central.
