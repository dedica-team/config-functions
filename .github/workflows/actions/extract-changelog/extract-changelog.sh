#!/usr/bin/env sh

# Extracts changes for a specific version from the changelog.

set -e

CHANGELOG=${CHANGELOG:-CHANGELOG.md}
TARGET_FILE=${TARGET_FILE:-RELEASE_NOTES.md}

if [ -z "${VERSION}" ]; then
  echo "Must provide the VERSION whose changes should be pulled from the changelog."
  exit 1
fi

if [ ! -f "${CHANGELOG}" ]; then
  echo "Changelog file '${CHANGELOG}' does not exist."
  exit 1
fi

sed -n "/^## .*${VERSION}/,/^## /{ /^## .*${VERSION}/{ s/^## .* \(${VERSION}\)/## \1/; p; }; /^## /!p; }" "${CHANGELOG}" | sed 's/^##/#/' > "${TARGET_FILE}"

echo "Extracted changes for version '${VERSION}' from '${CHANGELOG}' and copied these into '${TARGET_FILE}'."
