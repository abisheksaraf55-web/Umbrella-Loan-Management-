@rem Gradle startup script for Windows
@if "%DEBUG%" == "" @echo off
@rem Set local scope for variables
setlocal
set DIRNAME=%~dp0
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%

@rem Execute a user defined script before main
if defined APP_HOME (
    call "%APP_HOME%\gradle-user-script.bat" 2>nul
)

set CLASSPATH=%APP_HOME%\gradle\wrapper\gradle-wrapper.jar

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome
set JAVA_EXE=java.exe
goto execute

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

:execute
@rem Setup the command line
set CMD_LINE_ARGS=%*
@rem Execute Gradle
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %GRADLE_OPTS% "-Dorg.gradle.appname=%APP_BASE_NAME%" -classpath "%CLASSPATH%" org.gradle.wrapper.GradleWrapperMain %CMD_LINE_ARGS%
endlocal
