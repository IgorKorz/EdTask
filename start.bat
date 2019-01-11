mkdir out
javac -d out -sourcepath src -cp resources\lib\spring-context-5.1.4.RELEASE.jar;resources\lib\spring-core-5.1.4.RELEASE.jar;resources\lib\spring-beans-5.1.4.RELEASE.jar src\com\example\MainClass.java src\com\example\controller\*.java src\com\example\model\*.java src\com\example\view\*.java
java -classpath out;resources\lib\commons-logging-1.2.jar;resources\lib\spring-context-5.1.4.RELEASE.jar;resources\lib\spring-core-5.1.4.RELEASE.jar;resources\lib\spring-beans-5.1.4.RELEASE.jar;resources\lib\spring-expression-5.1.4.RELEASE.jar com.example.MainClass
pause