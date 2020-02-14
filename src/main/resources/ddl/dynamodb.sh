#!/usr/bin/env bash

aws dynamodb create-table \
    --table-name Post \
    --attribute-definitions \
        AttributeName=id,AttributeType=S \
        AttributeName=createdAt,AttributeType=S \
    --key-schema \
        AttributeName=id,KeyType=HASH \
        AttributeName=createdAt,KeyType=RANGE \
    --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1