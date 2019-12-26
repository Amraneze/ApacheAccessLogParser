#!/usr/bin/env bash

# Run a curl against the Jenkins metrics plugin API to check Jenkins's health

# Get the Metrics API KEY from the first args in docker-compose.yml and read the first line of it
# so we can use it in the curl
METRIC_API_KEY=$(head -n 1 $1)

echo "METRIC_API_KEY: $METRIC_API_KEY"
echo "Jenkins Metrics Healthcheck Url: localhost:8080/metrics/$METRIC_API_KEY/ping"

# We should get a response within 20 seconds
RESPONSE=$(curl -LI localhost:8080/metrics/$METRIC_API_KEY/ping -o /dev/null -w '%{http_code}\n' -s --max-time 20)
STATUS_CODE=$(echo $RESPONSE | awk '{print $1}')

echo "Jenkins Metrics API HTTP status code: $STATUS_CODE"

if [[ "$STATUS_CODE" == "200" ]]; then
    echo "Jenkins is UP and steady"
    exit 0
else
    echo "Jenkins is Down"
    exit 1
fi
