#!/bin/bash
schemaregistry="http://kafka.virtualandemo.com:8081"
tmpfile=$(mktemp)

topic=dev01.userCreated-topic
export SCHEMA=$(cat src/main/resources/avro/user.avsc)
echo '{"schema":""}' | jq --arg schema "$SCHEMA" '.schema = $schema' \
   > $tmpfile
curl -X POST -H "Content-Type: application/vnd.schemaregistry.v1+json" \
    --data @$tmpfile ${schemaregistry}/subjects/${topic}-value/versions