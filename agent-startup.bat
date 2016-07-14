set "HOST=your parking management application host"
set "PORT=your parking management application port"
set "PUBLISH_KEY=your_publish_key"
set "SUBSCRIBER_KEY=your_subscriber key"
set "API_KEY=your api key for the parking management end point"
set "ENDPOINT=your parking management application endpoint"
set "CURRENT_DIR=%cd%"

set CLASSPATH=.
set CLASSPATH=%CLASSPATH%;%CURRENT_DIR%\lib\parking-agent-1.0-SNAPSHOT.jar;%CURRENT_DIR%\lib\async-http-client-1.7.17.jar;%CURRENT_DIR%\lib\bcprov-jdk16-1.46.jar;%CURRENT_DIR%\lib\guava-15.0.jar;%CURRENT_DIR%\lib\json-20090211.jar;%CURRENT_DIR%\lib\json-simple-1.1.jar;%CURRENT_DIR%\lib\netty-3.6.6.Final.jar;%CURRENT_DIR%\lib\pubnub-3.7.10.jar;%CURRENT_DIR%\lib\slf4j-api-1.7.2.jar

%JAVA_HOME%\bin\java -agentlib:jdwp=transport=dt_shmem,server=y,suspend=n -Xms128m -Xmx384m -Xnoclassgc -classpath %CLASSPATH% com.parking.agent.ParkingAgentStarter %HOST% %PORT% %PUBLISH_KEY% %SUBSCRIBER_KEY% %API_KEY% %ENDPOINT%