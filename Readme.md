#cst test

## Build and Run
mvn clean install

mvn exec:java -Dexec.args="server csttest.yml"

## Run Mock SQS
docker run -p 9324:9324 -v "$PWD/sqs/queue.conf:/elasticmq.conf" graze/sqs-local

## Mock Jira response
docker run -v "$PWD/mountebank:/imposters" -p 2525:2525 -p 4553:4553 -d expert360/mountebank --configfile /imposters/issues.json --allowInjection

## issues.json file in the above imposter response
Find under resources folder in main folder


## API
To post a message in sqs
Request : http://localhost:8080/api/issue/sum?query=test_query&name=test_name
Response : Message Posted Successfully


Receive the message from sqs - to test
Request : http://localhost:8080/api/issue/receive
Response : {Messages: [{MessageId: dd4ad8a8-9f6a-481e-8237-742aa30bb702,ReceiptHandle: dd4ad8a8-9f6a-481e-8237-742aa30bb702#0cd29334-b156-45ca-8581-a684cb7ed160,MD5OfBody: f825d25a978363a63446219a1804e3f3,Body: SQSMessage(name=test_name, totalPoints=3),Attributes: {},MessageAttributes: {}}]}
