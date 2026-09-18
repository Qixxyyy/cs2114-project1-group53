$ErrorActionPreference = 'Stop'

$projectRoot = Split-Path -Parent $MyInvocation.MyCommand.Path
Set-Location $projectRoot

$sources = Get-ChildItem -Path 'src/main/java' -Recurse -Filter '*.java' |
    Select-Object -ExpandProperty FullName

if (-not $sources) {
    throw 'No Java source files were found in src/main/java.'
}

Remove-Item -Recurse -Force 'out' -ErrorAction SilentlyContinue
New-Item -ItemType Directory -Force 'out' | Out-Null

javac -d 'out' $sources
if ($LASTEXITCODE -ne 0) {
    throw 'Compilation failed. The application was not started.'
}

java -cp 'out' nutritiontracker.App
