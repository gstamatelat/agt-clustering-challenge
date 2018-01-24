@ECHO OFF
SET BASEDIR=%~dp0
java -cp "%BASEDIR%lib\${artifactId}-${version}.jar" VerifyPartition %*
