package br.unicamp.ft.e215293.Winding.artist;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

import br.unicamp.ft.e215293.Winding.R;

public class Artist implements Serializable {

    private int idArtista;
    private String nomeArtista;
    private String artArtista;
    private String descArtista;

    public Artist(int idArtista, String nomeArtista, String artArtista, String descArtista) {
        this.idArtista = idArtista;
        this.nomeArtista = nomeArtista;
        this.artArtista = artArtista;
        this.descArtista = descArtista;
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

    public String getArtArtista() {
        return artArtista;
    }

    public void setArtArtista(String artArtista) {
        this.artArtista = artArtista;
    }

    public String getDescArtista() {
        return descArtista;
    }

    public void setDescArtista(String descArtista) {
        this.descArtista = descArtista;
    }

}
