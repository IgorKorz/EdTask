javac -Xlint -d out -sourcepath src -cp lib\spring-framework-5.1.4.RELEASE\libs\spring-context-5.1.4.RELEASE.jar;lib\spring-framework-5.1.4.RELEASE\libs\spring-core-5.1.4.RELEASE.jar;lib\spring-framework-5.1.4.RELEASE\libs\spring-beans-5.1.4.RELEASE.jar src\com\example\MainClass.java src\com\example\controller\*.java src\com\example\model\*.java src\com\example\view\*.java
java -classpath out;lib\commons-logging-1.2\commons-logging-1.2.jar;lib\spring-framework-5.1.4.RELEASE\libs\spring-context-5.1.4.RELEASE.jar;lib\spring-framework-5.1.4.RELEASE\libs\spring-core-5.1.4.RELEASE.jar;lib\spring-framework-5.1.4.RELEASE\libs\spring-beans-5.1.4.RELEASE.jar;lib\spring-framework-5.1.4.RELEASE\libs\spring-expression-5.1.4.RELEASE.jar com.example.MainClass
pause