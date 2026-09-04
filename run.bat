@echo off
title Alumni Networking Portal - Build & Run

echo ============================================
echo   Alumni Networking Portal - JDBC Project
echo ============================================

REM ── Step 1: Create output directory ──────────
if not exist out mkdir out

REM ── Step 2: Compile all Java source files ────
echo.
echo [1/2] Compiling Java sources...
javac -cp ".;mysql-connector-j-9.7.0.jar" -d out src\db\DBConnection.java src\model\Alumni.java src\model\Job.java src\model\Mentor.java src\service\AlumniService.java src\service\JobService.java src\service\MentorService.java src\Main.java

IF %ERRORLEVEL% NEQ 0 (
    echo.
    echo [ERROR] Compilation failed. Fix errors above and try again.
    pause
    exit /b 1
)

echo [OK] Compilation successful!

REM ── Step 3: Run the application ──────────────
echo.
echo [2/2] Starting Alumni Portal...
echo.
java -cp ".;mysql-connector-j-9.7.0.jar;out" Main

pause

