@echo off
title Generate Public Link
echo Generating public link for your teacher...
echo.
echo [INSTRUCTIONS] 
echo 1. Make sure start_web.bat is ALREADY RUNNING.
echo 2. Wait for the URL to appear below.
echo.
npx localtunnel --port 8080
pause
