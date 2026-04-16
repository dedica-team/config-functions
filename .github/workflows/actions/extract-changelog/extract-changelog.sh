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

# Extract the block with the change description (without headline) from the changelog.
# Then reduce the headline level in that block, as they usually appear under the
# "Changelog" top-level and "release name" sub-level heading.
sed -n "/^## .*${VERSION}/,/^## /{ /^## .*${VERSION}/!{ /^## /!p; }; }" "${CHANGELOG}" | sed 's/^###/#/' > "${TARGET_FILE}"

echo "Extracted changes for version '${VERSION}' from '${CHANGELOG}' and copied these into '${TARGET_FILE}'."
