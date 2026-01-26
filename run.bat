@echo off
cd /d "%~dp0"
echo Compilation de JAVARENA...
javac -d . -sourcepath "src" "src\Main.java"
if %errorlevel% == 0 (
    echo Compilation reussie !
    echo.
    echo Execution de JAVARENA...
    echo.
    java Main
) else (
    echo Erreur de compilation
    pause
)
