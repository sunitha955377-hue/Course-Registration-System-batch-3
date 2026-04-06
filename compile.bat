@echo off
REM Compile all Java files with lib folder in classpath
echo Compiling Java files...
javac -d bin -cp "lib/*:." src/db/*.java src/dao/*.java src/service/*.java src/ui/*.java
if errorlevel 1 (
    echo Compilation failed!
    pause
    exit /b 1
)
echo Compilation successful!
pause
