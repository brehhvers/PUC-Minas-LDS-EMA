# Guia de Acesso ao Sistema EMA de Matrículas

## 🔐 Como Funciona a Autenticação

O sistema usa **código de pessoa** (gerado automaticamente) + **senha** para autenticação.

## 👥 Usuários Padrão do Sistema (Criados na Inicialização)

## 🏛️ Secretária Padrão
- **Nome**: Alice  
- **Email**: alice@universidade.edu  
- **Senha**: alice  
- **Código de Pessoa**: Gerado automaticamente na primeira execução  

## 👨‍🏫 Professor Padrão
- **Nome**: Dr. João Silva  
- **Email**: joao.silva@universidade.edu  
- **Senha**: prof123  
- **Código de Pessoa**: Gerado automaticamente  

## 🎓 Aluno Padrão
- **Nome**: Branca Letícia de Barros Motta  
- **Email**: branca.motta@universidade.edu  
- **Senha**: brancamotta  
- **Código de Pessoa**: Gerado automaticamente  

## 📚 Curso Padrão
- **Nome**: Ciência da Computação  
- **Departamento**: ICEI  
- **Carga Horária**: 240 horas  

## 📝 Currículo Padrão
- **Associado ao curso**: Ciência da Computação  
- **Contém as seguintes disciplinas**:  
  - Programação I (Obrigatória, R$150, Professor: Dr. João Silva)  
  - Estruturas de Dados (Obrigatória, R$200, Professor: Dr. João Silva)  
  - Inteligência Artificial (Optativa, R$180, Professor: Dr. João Silva)  
  - Banco de Dados (Obrigatória, R$170, Professor: Dr. João Silva)  
  - Sistemas Operacionais (Obrigatória, R$160, Professor: Dr. João Silva)  
  - Redes de Computadores (Obrigatória, R$180, Professor: Dr. João Silva)  
  - Computação Gráfica (Optativa, R$190, Professor: Dr. João Silva)  
  - Segurança da Informação (Optativa, R$200, Professor: Dr. João Silva)  

## ✅ Observações
- Todos os **códigos de pessoa** e **IDs de disciplinas** são gerados automaticamente.  
- Após a primeira execução, apenas a secretária Alice estará disponível para acesso inicial.  
- Professores, alunos, cursos, currículos e disciplinas podem ser adicionados ou alterados via menus do sistema após login da secretária.  


## 📋 Passo a Passo para Primeiro Acesso

### 1. Execute o Sistema
```powershell
.\run.ps1
```

### 2. Acesse como Secretária
1. Escolha opção `1 - Acesso para secretária`
2. Digite o código de pessoa da Alice (será mostrado no console)
3. Digite a senha: `alice`

### 3. Cadastre Usuários
- **Para criar Professor**: Menu → Gerenciar Usuários → Cadastrar professor
- **Para criar Aluno**: Menu → Gerenciar Usuários → Cadastrar aluno
- **Para criar Disciplinas**: Menu → Gerenciar Disciplinas → Criar nova disciplina
- **Para criar Cursos**: Menu → Gerenciar Cursos → Criar novo curso

### 4. Anote os Códigos
Após cadastrar, anote os códigos de pessoa gerados para fazer login depois.

## 🚀 Fluxo Recomendado para Demonstração

1. **Login como Secretária** → Configurar sistema inicial
2. **Criar algumas disciplinas** (ex: Matemática, História, etc.)
3. **Criar um curso** (ex: Engenharia de Software)
4. **Criar um currículo** para o curso
5. **Cadastrar um professor** e associar disciplinas
6. **Cadastrar um aluno**
7. **Testar login do aluno** → Fazer matrícula
8. **Testar login do professor** → Ver alunos matriculados

## 💡 Dicas Importantes

- Os **códigos de pessoa são únicos** e gerados automaticamente
- A **senha é definida** no momento do cadastro
- **Anote os códigos** pois eles são necessários para login
- **Primeira execução**: Apenas a secretária Alice estará disponível
- **Dados são persistidos** em arquivos na pasta `src/Data/File/`

## 🔧 Solução de Problemas

**"Credenciais inválidas"**: 
- Verifique se digitou o código correto
- Verifique se a senha está correta
- Certifique-se de que o usuário foi cadastrado

**"Usuário não encontrado"**:
- Execute primeiro como secretária para cadastrar usuários
