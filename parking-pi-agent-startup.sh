#!/usr/bin/env bash
HOST=daeqasus12
PORT=5555
PUBLISH_KEY=pub-c-b11b8c65-22c5-4bc7-84d2-dd41949dbc58
SUBSCRIBER_KEY=sub-c-313d4a34-2ef7-11e6-b700-0619f8945a4f
API_KEY=b082de9b-aa0d-49f6-8673-088384c5931f
ENDPOINT=/gateway/ParkingManagementAPI/parkingmgmt/locations

CLASSPATH=".:/lib/parking-agent-1.0-SNAPSHOT.jar:/lib/async-http-client-1.7.17.jar;/lib/bcprov-jdk16-1.46.jar;/lib/guava-15.0.jar;/lib/json-20090211.jar;/lib/json-simple-1.1.jar;/lib/netty-3.6.6.Final.jar;/lib/pubnub-3.7.10.jar;/lib/slf4j-api-1.7.2.jar"

java -agentlib:jdwp=transport=dt_shmem,server=y,suspend=n -Xms128m -Xmx384m -Xnoclassgc -classpath ${CLASSPATH} com.parking.agent.ParkingAgentStarter ${HOST} ${PORT} ${PUBLISH_KEY} ${SUBSCRIBER_KEY} ${API_KEY} ${ENDPOINT}