@echo off
title Alumni Portal Web Server
cd web
echo Starting server on http://localhost:8080...
echo.
echo [IMPORTANT] KEEP THIS WINDOW OPEN WHILE SHOWING THE TEACHER!
echo.
npx serve -l 8080
pause
