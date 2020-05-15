package br.unicamp.ft.e215293.Winding.artist;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;

import java.util.ArrayList;

import br.unicamp.ft.e215293.Winding.R;

public class Artist {
    private String nome;
    private int foto;

    public Artist(String nome, int foto) {
        this.nome = nome;
        this.foto = foto;
    }

    public String getNome() {
        return nome;
    }

    public int getFoto() {
        return foto;
    }

    public static Artist[] getArtists(Context context) {
        if (context != null) {
            TypedArray fotos = context.getResources().obtainTypedArray(R.array.fotosArtistas);
            String[] artist = context.getResources().getStringArray(R.array.artists);
            ArrayList<Artist> artists = new ArrayList<>();

            for (int i = 0; i < artist.length; i++) {
                artists.add(new Artist(
                        artist[i],
                        fotos.getResourceId(i, 0)
                ));
            }
            fotos.recycle();
            Artist[] artistsArr = new Artist[artists.size()];
            return artists.toArray(artistsArr);

        }
        return null;
    }

    public static Artist[] getArtistsSearch(Context context, String data) {
        if (context != null) {
            TypedArray fotos = context.getResources().obtainTypedArray(R.array.fotosArtistas);
            String[] artist = context.getResources().getStringArray(R.array.artists);
            ArrayList<Artist> artists = new ArrayList<>();

            for (int i = 0; i < artist.length; i++) {
                Log.i("zipzop", data);
                if (artist[i].toLowerCase().contains(data.toLowerCase())) {
                    artists.add(new Artist(
                            artist[i],
                            fotos.getResourceId(i, 0)
                    ));
                }
            }
            fotos.recycle();
            Artist[] artistsArr = new Artist[artists.size()];
            return artists.toArray(artistsArr);

        }
        return null;
    }
}
