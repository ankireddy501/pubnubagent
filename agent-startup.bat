set "HOST=daeapiportal04.eur.ad.sag"
set "PORT=5555"
set "PUBLISH_KEY=pub-c-b11b8c65-22c5-4bc7-84d2-dd41949dbc58"
set "SUBSCRIBER_KEY=sub-c-313d4a34-2ef7-11e6-b700-0619f8945a4f"
set "API_KEY=71c15220-45b9-11e6-bbcf-a7281a25b9e3"
set "ENDPOINT=/ws/Parking_Management_API/1.0/parkingmgmt/locations"
set "CURRENT_DIR=%cd%"

set CLASSPATH=.
set CLASSPATH=%CLASSPATH%;%CURRENT_DIR%\lib\parking-agent-1.0-SNAPSHOT.jar;%CURRENT_DIR%\lib\async-http-client-1.7.17.jar;%CURRENT_DIR%\lib\bcprov-jdk16-1.46.jar;%CURRENT_DIR%\lib\guava-15.0.jar;%CURRENT_DIR%\lib\json-20090211.jar;%CURRENT_DIR%\lib\json-simple-1.1.jar;%CURRENT_DIR%\lib\netty-3.6.6.Final.jar;%CURRENT_DIR%\lib\pubnub-3.7.10.jar;%CURRENT_DIR%\lib\slf4j-api-1.7.2.jar

%JAVA_HOME%\bin\java -agentlib:jdwp=transport=dt_shmem,server=y,suspend=n -Xms128m -Xmx384m -Xnoclassgc -classpath %CLASSPATH% com.parking.agent.ParkingAgentStarter %HOST% %PORT% %PUBLISH_KEY% %SUBSCRIBER_KEY% %API_KEY% %ENDPOINT%