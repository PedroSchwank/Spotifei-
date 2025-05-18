package model;

public class Playlist {
    private int id;
    private String nome;
    private int usuarioId;

    public Playlist() {}

    public Playlist(String nome, int usuarioId) {
        this.nome = nome;
        this.usuarioId = usuarioId;
    }

    public int getId() { return id; }
    public void setId(int id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getUsuarioId() { return usuarioId; }
    public void setUsuarioId(int usuarioId) { this.usuarioId = usuarioId; }
}
