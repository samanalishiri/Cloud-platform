## Description
This tutorial developed around the AWS cloud platform. 
I used pure Java, so I used Java service provider for satisfying dependency.

It is included:
- Load configuration as a `hello world` program
- Bucket tutorial
- Message queue (SQS) tutorial

## Prerequisites
- Java 11
- maven 3
- AWS SDK

### Set up the system
1- Create account in AWS

2- Create a user in order to develop

- Save aws_access_key_id
- Save aws_secret_access_key

3- Install AWS SDK and type "aws configure" command in the terminal:

    AWS Access Key ID [None]: your aws_access_key_id
    AWS Secret Access Key [None]: your aws_secret_access_key
    Default region name [None]: your-region or leave it
    Default output format [None]: json

4- run `CredentialsUtilsTest` unit test

## Build
    mvn dependency:tree
    mvn clean package -DskipTests=true

## Test
    mvn test


