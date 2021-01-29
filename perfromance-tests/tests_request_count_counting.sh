#!/bin/bash

# INFO: after test request count for user "octocat" should be equal exactly to 100 000
# INFO: due to Github access rate limit, use mocked Github provided in tools catalog
#       more details described here: https://docs.github.com/en/rest/overview/resources-in-the-rest-api#rate-limiting
REQUEST_COUNT=100000
USERS_CONCURRENT_COUNT=100
REQUESTED_USER_LOGIN="octocat"

ab -n $REQUEST_COUNT -c $USERS_CONCURRENT_COUNT http://localhost:8080/users/$REQUESTED_USER_LOGIN > result.txt
