# 📚 Sistema de Gerenciamento de Conteúdos e Usuários - Java + HSQLDB

Bem-vindo ao **Sistema de Gerenciamento de Conteúdos e Usuários**! 🎉 Um projeto completo e didático que oferece um conjunto robusto de funcionalidades para gerenciamento de **usuários**, **autenticação**, **criação**, **edição** e **exclusão** de conteúdos, além de funções administrativas poderosas!

## 💡 Visão Geral

Este projeto foi desenvolvido em **Java** com o banco de dados **HSQLDB** e oferece uma interface interativa (TUI - Text User Interface) para o gerenciamento de **conteúdos** e **usuários**. Ideal para quem está aprendendo ou deseja ter um sistema funcional como base para outros projetos!

---

## ⚙️ Funcionalidades

### 🚀 Para Usuários Comuns:
- **Login e Cadastro**: Crie sua conta e faça login para acessar os conteúdos.
- **Criação de Conteúdos**: Escreva e publique conteúdos com descrições detalhadas.
- **Edição e Exclusão**: Edite ou exclua seus próprios conteúdos.
- **Gerenciamento de Perfil**: Atualize seu nome e senha a qualquer momento.

### 👑 Para Administradores:
- **Gerenciamento Completo**: Veja todos os conteúdos e usuários.
- **Edição e Exclusão Global**: Edite e remova conteúdos ou usuários.
- **Funções Avançadas**: Acesse opções especiais como ver senhas e associar conteúdos a usuários.
  
---

## 🛠️ Tecnologias Utilizadas

- **Java**: Linguagem principal do projeto, oferecendo robustez e confiabilidade.
- **HSQLDB**: Banco de dados em memória para rápido desenvolvimento e teste de funcionalidades.
- **JDBC**: Conexão direta entre o Java e o banco de dados.

---

## 🚧 Estrutura do Projeto

📁 src/ ├── 📂 com.mycompany.app/ │ ├── Conteudo.java # Representa o conteúdo criado pelos usuários │ ├── Pessoa.java # Gerencia os dados dos usuários │ ├── ConteudoHSQL.java # Conecta e manipula o banco de dados HSQLDB │ ├── UsuarioHSQL.java # Gerencia a persistência dos usuários │ ├── MenuActions.java # Lógica de interações com o usuário │ ├── TUI.java # Interface textual principal │ ├── UsuarioService.java # Serviço para gerenciar o login e cadastro │ └── Main.java # Ponto de entrada da aplicação

---

## 🏃‍♂️ Como Rodar o Projeto

1. **Clone o repositório**:
   ```bash
   git clone https://github.com/seu-usuario/seu-repositorio.git
---

## 👩‍💻 Demonstração
### 📋 Menu Inicial:
Escolha a opção que você deseja:
[1] Fazer login no sistema
[2] Cadastrar-se
[3] Sair

---

## ✍️ Criar Conteúdo:
Digite o nome do conteúdo: Meu Primeiro Post
Digite a descrição do conteúdo: Um post incrível sobre Java e banco de dados!
Conteúdo criado!

---

## 🔧 Funções Administrativas:
[6] CONSULTAR BANCO DE DADOS

