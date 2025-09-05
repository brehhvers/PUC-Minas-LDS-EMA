@echo off
echo ========================================
echo    SISTEMA EMA - INICIALIZANDO DADOS
echo ========================================
echo.

echo Verificando se Java esta instalado...
java -version >nul 2>&1
if %errorlevel% neq 0 (
    echo ERRO: Java nao esta instalado
    echo Configurando Java...
    set "PATH=C:\Program Files\Microsoft\jdk-17.0.16.8-hotspot\bin;%PATH%"
)

echo Compilando o projeto...
javac -cp src -encoding UTF-8 src/App.java
if %errorlevel% neq 0 (
    echo ERRO: Falha na compilacao
    pause
    exit /b 1
)

echo.
echo Verificando se existem dados...
if not exist "src\Data\File\secretaria.txt" (
    echo Criando dados basicos...
    echo.
    
    echo Criando pasta de dados...
    if not exist "src\Data\File" mkdir "src\Data\File"
    
    echo Criando secretaria...
    echo 1^|Alice^|alice@jabberwock^|alice > src\Data\File\secretaria.txt
    
    echo Criando professor...
    echo 1^|Joao Silva^|joao@email.com^|senha123 > src\Data\File\professor.txt
    
    echo Criando aluno...
    echo 1^|Maria Santos^|maria@email.com^|senha456 > src\Data\File\aluno.txt
    
    echo Criando disciplinas...
    echo 1^|Programacao I^|OBRIGATORIA^|ATIVA^|60^|4 > src\Data\File\disciplina.txt
    echo 2^|Estrutura de Dados^|OBRIGATORIA^|ATIVA^|60^|4 >> src\Data\File\disciplina.txt
    echo 3^|Banco de Dados^|OBRIGATORIA^|ATIVA^|60^|4 >> src\Data\File\disciplina.txt
    echo 4^|Engenharia de Software^|OBRIGATORIA^|ATIVA^|60^|4 >> src\Data\File\disciplina.txt
    echo 5^|Inteligencia Artificial^|ELETIVA^|ATIVA^|60^|4 >> src\Data\File\disciplina.txt
    
    echo Criando cursos...
    echo 1^|Ciencia da Computacao^|DCC^|240 > src\Data\File\curso.txt
    echo 2^|Engenharia de Software^|DCC^|240 >> src\Data\File\curso.txt
    
    echo Criando curriculos...
    echo 1^|1^|1 > src\Data\File\curriculo.txt
    echo 2^|1^|2 >> src\Data\File\curriculo.txt
    echo 3^|1^|3 >> src\Data\File\curriculo.txt
    echo 4^|1^|4 >> src\Data\File\curriculo.txt
    echo 5^|2^|1 >> src\Data\File\curriculo.txt
    echo 6^|2^|2 >> src\Data\File\curriculo.txt
    echo 7^|2^|5 >> src\Data\File\curriculo.txt
    
    echo Criando planos de ensino...
    echo 1^|1^|2024^|1^|ATIVO > src\Data\File\planoDeEnsino.txt
    
    echo.
    echo Dados basicos criados com sucesso!
    echo.
    echo CREDENCIAIS PARA TESTE:
    echo - Secretaria: Codigo 1, Senha: alice
    echo - Professor: Codigo 1, Senha: senha123
    echo - Aluno: Codigo 1, Senha: senha456
    echo.
) else (
    echo Dados ja existem, pulando criacao...
    echo.
)

echo Iniciando Sistema EMA...
echo.
echo INSTRUCOES:
echo - Digite 1 para acessar como secretaria
echo - Digite 2 para acessar como professor  
echo - Digite 3 para acessar como aluno
echo - Digite 0 para sair
echo.
java -cp src App

echo.
echo ========================================
echo    SISTEMA EMA ENCERRADO
echo ========================================
pause
