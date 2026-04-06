@echo off
REM Run the application with lib folder in classpath
echo Starting application...
java -cp "bin;lib/*:." ui.AppLauncher
pause
