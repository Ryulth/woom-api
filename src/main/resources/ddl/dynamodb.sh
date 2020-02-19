#!/usr/bin/env bash

aws dynamodb create-table \
    --table-name Post \
    --attribute-definitions \
        AttributeName=id,AttributeType=S \
    --key-schema \
        AttributeName=id,KeyType=HASH \
    --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1

aws dynamodb create-table \
    --table-name PostComment \
    --attribute-definitions \
        AttributeName=id,AttributeType=S \
        AttributeName=postId,AttributeType=S \
    --key-schema \
        AttributeName=id,KeyType=HASH \
        AttributeName=postId,KeyType=RANGE \
    --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1

aws dynamodb create-table \
    --table-name DynamoDBSequence \
    --attribute-definitions \
        AttributeName=id,AttributeType=S \
    --key-schema \
        AttributeName=id,KeyType=HASH \
    --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1

aws dynamodb create-table \
    --table-name ChatRoom \
    --attribute-definitions \
        AttributeName=id,AttributeType=N \
    --key-schema \
        AttributeName=id,KeyType=HASH \
    --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1

aws dynamodb create-table \
    --table-name Message \
    --attribute-definitions \
        AttributeName=id,AttributeType=S \
        AttributeName=chatRoomId,AttributeType=N \
    --key-schema \
        AttributeName=id,KeyType=HASH \
        AttributeName=chatRoomId,KeyType=RANGE \
    --provisioned-throughput ReadCapacityUnits=1,WriteCapacityUnits=1
