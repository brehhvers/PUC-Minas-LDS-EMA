#!/bin/bash

echo "========================================"
echo "   SISTEMA EMA - INICIALIZANDO DADOS"
echo "========================================"
echo

echo "Verificando se Java esta instalado..."
if ! command -v java &> /dev/null; then
    echo "ERRO: Java nao esta instalado ou nao esta no PATH"
    echo
    echo "Para instalar o Java:"
    echo "1. Ubuntu/Debian: sudo apt install openjdk-17-jdk"
    echo "2. macOS: brew install openjdk@17"
    echo "3. Ou baixe de: https://adoptium.net/"
    echo
    exit 1
fi

echo "Java encontrado!"
echo

echo "Compilando o projeto..."
javac -cp src -encoding UTF-8 src/App.java

if [ $? -ne 0 ]; then
    echo "ERRO: Falha na compilacao"
    echo "Verifique se todos os arquivos estao presentes"
    exit 1
fi

echo
echo "Verificando se existem dados..."
if [ ! -f "src/Data/File/secretaria.txt" ]; then
    echo "Criando dados basicos..."
    echo
    
    echo "Criando pasta de dados..."
    mkdir -p "src/Data/File"
    
    echo "Criando secretaria..."
    echo "1|seubarriga|seubarriga@email.com|seubarriga" > src/Data/File/secretaria.txt
    
    echo "Criando professor..."
    echo "1|Joao Silva|joao@email.com|senha123" > src/Data/File/professor.txt
    
    echo "Criando aluno..."
    echo "1|Maria Santos|maria@email.com|senha456" > src/Data/File/aluno.txt
    
    echo "Criando disciplinas..."
    echo "1|Programacao I|OBRIGATORIA|ATIVA|60|4" > src/Data/File/disciplina.txt
    echo "2|Estrutura de Dados|OBRIGATORIA|ATIVA|60|4" >> src/Data/File/disciplina.txt
    echo "3|Banco de Dados|OBRIGATORIA|ATIVA|60|4" >> src/Data/File/disciplina.txt
    echo "4|Engenharia de Software|OBRIGATORIA|ATIVA|60|4" >> src/Data/File/disciplina.txt
    echo "5|Inteligencia Artificial|ELETIVA|ATIVA|60|4" >> src/Data/File/disciplina.txt
    
    echo "Criando cursos..."
    echo "1|Ciencia da Computacao|DCC|240" > src/Data/File/curso.txt
    echo "2|Engenharia de Software|DCC|240" >> src/Data/File/curso.txt
    
    echo "Criando curriculos..."
    echo "1|1|1" > src/Data/File/curriculo.txt
    echo "2|1|2" >> src/Data/File/curriculo.txt
    echo "3|1|3" >> src/Data/File/curriculo.txt
    echo "4|1|4" >> src/Data/File/curriculo.txt
    echo "5|2|1" >> src/Data/File/curriculo.txt
    echo "6|2|2" >> src/Data/File/curriculo.txt
    echo "7|2|5" >> src/Data/File/curriculo.txt
    
    echo "Criando planos de ensino..."
    echo "1|1|2024|1|ATIVO" > src/Data/File/planoDeEnsino.txt
    
    echo
    echo "Dados basicos criados com sucesso!"
    echo
    echo "CREDENCIAIS PARA TESTE:"
    echo "- Secretaria: seubarriga (código gerado automaticamente, senha: seubarriga)"
    echo "- Professor: João Silva (código gerado automaticamente, senha: senha123)"
    echo "- Aluno: Maria Santos (código gerado automaticamente, senha: senha456)"
    echo
else
    echo "Dados ja existem, pulando criacao..."
    echo
fi

echo "Iniciando Sistema EMA..."
echo
echo "INSTRUCOES:"
echo "- Digite 1 para acessar como secretaria"
echo "- Digite 2 para acessar como professor"
echo "- Digite 3 para acessar como aluno"
echo "- Digite 0 para sair"
echo
java -cp src App

echo
echo "========================================"
echo "   SISTEMA EMA ENCERRADO"
echo "========================================"
