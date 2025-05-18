# 🎵 Spotifei

Projeto desenvolvido em Java com Swing, JDBC e PostgreSQL.  
O **Spotifei** é um sistema completo de gerenciamento de músicas e playlists, permitindo que o usuário:

- Cadastre e faça login
- Busque músicas
- Curta músicas (salvando na playlist "Curtidas")
- Crie playlists personalizadas
- Adicione músicas em qualquer playlist
- Visualize histórico de ações (buscas e curtidas)
- Veja o conteúdo de qualquer playlist criada

---

## 🛠️ Tecnologias Utilizadas

- Java 17+
- Swing (Interface Gráfica - JFrame)
- PostgreSQL (Banco de Dados)
- JDBC (Acesso ao banco)
- Apache NetBeans (IDE usada no projeto)

---

## 📦 Estrutura do Projeto

```bash
src/
├── controller/
│   └── MainController.java
├── dao/
│   ├── ConexaoDAO.java
│   ├── UsuarioDAO.java
│   ├── MusicaDAO.java
│   ├── PlaylistDAO.java
│   └── HistoricoDAO.java
├── model/
│   ├── Usuario.java
│   ├── Musica.java
│   ├── Playlist.java
│   └── Historico.java
└── view/
    ├── TelaLogin.java
    ├── TelaCadastro.java
    ├── TelaPrincipal.java
    ├── TelaBusca.java
    ├── TelaPlaylist.java
    └── TelaHistorico.java
```

---

## 🧠 Funcionalidades

### ✅ Login e Cadastro
- Validação via banco
- Cadastro salvo em `usuarios`

### 🔍 Busca de Músicas
- Pesquisa por nome, artista ou gênero
- Adiciona ação ao histórico

### ❤️ Curtir e Salvar em Playlists
- Músicas curtidas vão para a playlist "Curtidas"
- Usuário escolhe em qual playlist personalizada deseja salvar

### 🗃️ Gerenciar Playlists
- Criação de playlists com nome personalizado
- Prevenção de nomes duplicados
- Visualização de todas as playlists e músicas contidas nelas

### 🕘 Histórico
- Histórico salvo em banco (tabela `historico`)
- Mostra data, ação (buscar/curtir) e nome da música

---

## 🖼️ Capturas de Tela

### 🔐 Tela de Login
![image](https://github.com/user-attachments/assets/c4a3d1b1-8830-43a0-b95d-49e0287953f2)


### 🆕 Tela de Cadastro
![Tela Cadastro](prints/tela-cadastro.png)

### 🏠 Tela Principal
![Tela Principal](prints/tela-principal.png)

### 🔍 Tela de Busca
![Tela Busca](prints/tela-busca.png)

### 🎧 Tela de Playlist
![Tela Playlist](prints/tela-playlist.png)

### 📜 Tela de Histórico
![Tela Histórico](prints/tela-historico.png)



---

## 🗃️ Banco de Dados

Crie o banco `spotifei` e execute os seguintes scripts:

```sql
CREATE TABLE usuarios (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    email VARCHAR(100) UNIQUE,
    senha VARCHAR(100)
);

CREATE TABLE musicas (
    id SERIAL PRIMARY KEY,
    titulo VARCHAR(100),
    artista VARCHAR(100),
    genero VARCHAR(50)
);

CREATE TABLE playlists (
    id SERIAL PRIMARY KEY,
    nome VARCHAR(100),
    usuario_id INTEGER
);

CREATE TABLE playlist_musicas (
    playlist_id INT,
    musica_id INT,
    PRIMARY KEY (playlist_id, musica_id),
    FOREIGN KEY (playlist_id) REFERENCES playlists(id),
    FOREIGN KEY (musica_id) REFERENCES musicas(id)
);

CREATE TABLE historico (
    id SERIAL PRIMARY KEY,
    usuario_id INT,
    musica_id INT,
    acao VARCHAR(10),
    data TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);
```

---

## ▶️ Executando o Projeto

1. Clone o repositório
2. Crie o banco e execute os scripts SQL acima
3. Configure a conexão em `ConexaoDAO.java`
4. Rode o projeto pela classe `MainController.java`

---

## 🙋 Autor

**Pedro Schwank - Ciência da Computação @ FEI**  
📧 [unifpschwank@fei.edu.br]
RA:22.125.074-9
💼 Projeto acadêmico | 2025  
