#!/usr/bin/env sh

# Checks if the changelog has been updated (or if it should be updated) and
# fails, if a changelog entry is missing.
#
# Required environment variables:
# - CHANGELOG_UPDATED: Was the changelog updated? Accepts "true" or "false" as value.
# - HAS_SKIP_LABEL: Is the skip label assigned to the Pull Request? Accepts "true" or "false" as value.
# - CHANGELOG_FILE: Name of the changelog file.
# - SKIP_LABEL: Name of the label that can be used to skip the changelog check.

set -e

if [ "${CHANGELOG_UPDATED}" = "true" ]; then
  echo "
  The changelog has been updated.
  ";
  exit 0;
fi

if [ "${HAS_SKIP_LABEL}" = "true" ]; then
  echo "
  The changelog check is disabled as the Pull Request is labelled with '${SKIP_LABEL}'.
  Remove that label to activate the changelog check.
  ";
  exit 0;
fi

echo "
Did you forget to update the changelog?

Add a short description of your feature or fix to the ${CHANGELOG_FILE} file to let others know what
you have done.
E.g. the following information might help your colleagues in the future:
  - What is the observable effect of your change?
  - What part of the application (business-wise, not technically) is affected by your change?
  - How is the development workflow affected by this change?

If your Merge Request does not deserve an extra changelog entry, then you can ignore this warning.
Add the label '${SKIP_LABEL}' to your Pull Request to get rid of this warning.
";

exit 1;
