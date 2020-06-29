package br.unicamp.ft.e215293.Winding.music;

import java.io.Serializable;

public class Music implements Serializable {
    private int idMusica;
    private String nomeMusica;
    private String songArt;
    private String letra;

    private int idArtista;
    private String nomeArtista;

    public Music(int idMusica, String nomeMusica, String songArt, String letra, int idArtista, String nomeArtista) {
        this.idMusica = idMusica;
        this.nomeMusica = nomeMusica;
        this.songArt = songArt;
        this.letra = letra;
        this.idArtista = idArtista;
        this.nomeArtista = nomeArtista;
    }

    public int getIdMusica() {
        return idMusica;
    }

    public void setIdMusica(int idMusica) {
        this.idMusica = idMusica;
    }

    public String getNomeMusica() {
        return nomeMusica;
    }

    public void setNomeMusica(String nomeMusica) {
        this.nomeMusica = nomeMusica;
    }

    public String getSongArt() {
        return songArt;
    }

    public void setSongArt(String songArt) {
        this.songArt = songArt;
    }

    public String getLetra() {
        return letra;
    }

    public void setLetra(String letra) {
        this.letra = letra;
    }

    public int getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(int idArtista) {
        this.idArtista = idArtista;
    }

    public String getNomeArtista() {
        return nomeArtista;
    }

    public void setNomeArtista(String nomeArtista) {
        this.nomeArtista = nomeArtista;
    }
}
