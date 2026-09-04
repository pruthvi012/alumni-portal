# Alumni Networking Portal - Build & Run Script (PowerShell)
Write-Host "============================================" -ForegroundColor Cyan
Write-Host "  Alumni Networking Portal - JDBC Project" -ForegroundColor Cyan
Write-Host "============================================" -ForegroundColor Cyan

# 1. Create output directory
if (-not (Test-Path "out")) {
    New-Item -ItemType Directory -Path "out" | Out-Null
}

# 2. Compile
Write-Host "`n[1/2] Compiling Java sources..." -ForegroundColor Yellow
$cp = ".;mysql-connector-j-9.7.0.jar"
$sources = Get-ChildItem -Path "src" -Filter "*.java" -Recurse | ForEach-Object { $_.FullName }

javac -cp $cp -d out $sources

if ($LASTEXITCODE -ne 0) {
    Write-Host "`n[ERROR] Compilation failed. Fix errors above and try again." -ForegroundColor Red
    Read-Host "Press Enter to exit"
    exit
}

Write-Host "[OK] Compilation successful!" -ForegroundColor Green

# 3. Run
Write-Host "`n[2/2] Starting Alumni Portal..." -ForegroundColor Yellow
java -cp ".;mysql-connector-j-9.7.0.jar;out" Main

Read-Host "`nPress Enter to exit"
