# Configuração do Java para o Projeto

## Problema Identificado
O Java não está instalado ou não está configurado no PATH do sistema.

## Solução Rápida

### 1. Instalar Java JDK
1. Acesse: https://www.oracle.com/java/technologies/downloads/
2. Baixe o Java JDK 11 ou superior para Windows
3. Execute o instalador

### 2. Configurar PATH (se necessário)
1. Abra "Variáveis de Ambiente" no Windows
2. Adicione o caminho do Java bin ao PATH:
   - Exemplo: `C:\Program Files\Java\jdk-17\bin`

### 3. Verificar Instalação
Execute no terminal:
```
java -version
javac -version
```

## Compilação e Execução do Projeto

### Compilar
```powershell
cd "c:\Users\dtiDigital\PUC Minas\PUC-Minas-LDS-EMA\implementação"
& "C:\Program Files\Java\jdk-24\bin\javac.exe" -d bin -cp src src\*.java src\Business\*.java src\Business\Pessoa\*.java src\Data\DAO\*.java src\Enum\*.java src\Interface\*.java src\Utils\Identificador\*.java src\Utils\Notificador\*.java src\View\*.java
```

### Executar
```powershell
cd bin
& "C:\Program Files\Java\jdk-24\bin\java.exe" App
```

### Script de Compilação e Execução
Criado arquivo `run.ps1` para facilitar a execução.

## Status Atual
- ✅ Interface visual (linha de comando) implementada
- ✅ Persistência em arquivos implementada  
- ✅ Java configurado e funcionando
- ✅ **SISTEMA TESTADO E FUNCIONANDO**
- ⚠️ Necessário criar usuários iniciais para teste completo
