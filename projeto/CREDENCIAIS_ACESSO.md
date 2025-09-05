# 🔐 Credenciais de Acesso ao Sistema EMA

## 👤 Usuários Disponíveis para Login

### 🏛️ SECRETÁRIA (Administradora Principal)
```
👤 Nome: Alice
🔢 Código de Pessoa: [gerado automaticamente na primeira execução]
🔑 Senha: alice
📧 Email: alice@jabberwock
```

### 🚀 COMO DESCOBRIR O CÓDIGO DA SECRETÁRIA

1. **Execute o sistema**:
   ```powershell
   .\run.ps1
   ```

2. **Na primeira execução**, o sistema mostrará:
   ```
   === SISTEMA INICIALIZADO ===
   Secretária padrão criada:
   Nome: Alice
   Código de Pessoa: [NÚMERO]  ← ANOTE ESTE NÚMERO!
   Senha: alice
   ===============================
   ```

3. **Use essas credenciais**:
   - Escolha opção `1 - Acesso para secretária`
   - Digite o código mostrado
   - Digite a senha: `alice`

## 📋 PRIMEIROS PASSOS APÓS LOGIN

### Como Secretária Alice:
1. **Criar Disciplinas**: Menu → Gerenciar Disciplinas → Criar nova disciplina
2. **Criar Cursos**: Menu → Gerenciar Cursos → Criar novo curso  
3. **Cadastrar Professor**: Menu → Gerenciar Usuários → Cadastrar professor
4. **Cadastrar Aluno**: Menu → Gerenciar Usuários → Cadastrar aluno

### ⚠️ IMPORTANTE: 
- **ANOTE** os códigos de pessoa que são gerados para cada usuário
- **Esses códigos** serão necessários para fazer login posteriormente
- **As senhas** são definidas no momento do cadastro

## 🎯 EXEMPLO DE FLUXO COMPLETO

1. **Login Alice** → Cadastrar alguns usuários
2. **Criar dados básicos** (disciplinas, cursos)
3. **Sair e testar login** de professor/aluno
4. **Demonstrar funcionalidades** de cada perfil

## 🔧 Solução de Problemas

**"Credenciais inválidas"**:
- Verifique se o código está correto
- Verifique se a senha está correta  
- Certifique-se de ter criado o usuário primeiro

**Sistema não inicializa**:
- Execute `.\run.ps1` na raiz do projeto
- Certifique-se de que o Java está instalado
