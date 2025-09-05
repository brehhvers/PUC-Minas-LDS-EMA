# Guia de Acesso ao Sistema EMA de Matrículas

## 🔐 Como Funciona a Autenticação

O sistema usa **código de pessoa** (gerado automaticamente) + **senha** para autenticação.

## 👥 Usuários Padrão do Sistema

### 🏛️ Secretária (Administradora)
- **Código de Pessoa**: Veja na primeira execução (será exibido)
- **Senha**: `alice`
- **Nome**: Alice
- **Funcionalidades**: Gerenciar todo o sistema

### 👨‍🏫 Professor (Será criado pela secretária)
- A secretária precisa cadastrar professores primeiro
- **Acesso**: Menu Secretaria → Gerenciar Usuários → Cadastrar professor

### 🎓 Aluno (Será criado pela secretária)  
- A secretária precisa cadastrar alunos primeiro
- **Acesso**: Menu Secretaria → Gerenciar Usuários → Cadastrar aluno

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
