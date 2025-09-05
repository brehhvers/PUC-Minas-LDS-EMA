# Script para compilar e executar o Sistema EMA

Write-Host "=== Compilando Sistema EMA de Matrículas ===" -ForegroundColor Green

# Navegar para o diretório de implementação
Set-Location "c:\Users\dtiDigital\PUC Minas\PUC-Minas-LDS-EMA\implementação"

# Compilar
Write-Host "Compilando..." -ForegroundColor Yellow
& "C:\Program Files\Java\jdk-24\bin\javac.exe" -d bin -cp src src/App.java src/Business/*.java src/Business/Pessoa/*.java src/Data/DAO/*.java src/Enum/*.java src/Interface/*.java src/Utils/Identificador/*.java src/Utils/Notificador/*.java src/View/*.java

if ($LASTEXITCODE -eq 0) {
    Write-Host "Compilação bem-sucedida!" -ForegroundColor Green
    Write-Host "" 
    Write-Host "=== CREDENCIAIS DE ACESSO ===" -ForegroundColor Cyan
    Write-Host "SECRETÁRIA:" -ForegroundColor Yellow
    Write-Host "- Senha: alice" -ForegroundColor White
    Write-Host "- Código será mostrado na primeira execução" -ForegroundColor White
    Write-Host "==============================" -ForegroundColor Cyan
    Write-Host ""
    
    # Executar
    Write-Host "Iniciando sistema..." -ForegroundColor Yellow
    Set-Location bin
    & "C:\Program Files\Java\jdk-24\bin\java.exe" App
} else {
    Write-Host "Erro na compilação!" -ForegroundColor Red
    exit 1
}
