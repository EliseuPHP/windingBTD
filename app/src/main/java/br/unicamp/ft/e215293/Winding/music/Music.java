package br.unicamp.ft.e215293.Winding.music;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.Log;

import java.io.Serializable;
import java.util.ArrayList;

import br.unicamp.ft.e215293.Winding.R;

public class Music implements Serializable {
    private int idMusica;
    private String nome;
    private int trackNu;
    private int foto;
    private String songArt;
    private String genero;
    private String artista;
    private int idArtista;
    private String letra;

    public Music(String nome, int trackNu, int foto, String genero, String artista, String letra) {
        this.nome = nome;
        this.trackNu = trackNu;
        this.foto = foto;
        this.genero = genero;
        this.artista = artista;
        this.letra = letra;
    }

    public Music(int idMusica, String nome, String songArt, String letra, int idArtista, String artista) {
        this.idMusica = idMusica;
        this.nome = nome;
        this.songArt = songArt;
        this.letra = letra;
        this.idArtista = idArtista;
        this.artista = artista;
    }

    public String getSongArt() {
        return songArt;
    }

    public void setSongArt(String songArt) {
        this.songArt = songArt;
    }

    public int getIdMusica() {
        return idMusica;
    }

    public void setIdMusica(int idMusica) {
        this.idMusica = idMusica;
    }

    public int getIdArtista() {
        return idArtista;
    }

    public void setIdArtista(int idArtista) {
        this.idArtista = idArtista;
    }

    public String getNome() {
        return nome;
    }

    public int getTrackNu() {
        return trackNu;
    }

    public int getFoto() {
        return foto;
    }

    public String getGenero() {
        return genero;
    }

    public String getLetra() {
        return letra;
    }

    public String getArtista() {
        return artista;
    }

//    public static Music[] getMusics(Context context) {
//        if (context != null) {
//            String[] infos = context.getResources().getStringArray(R.array.musics);
//            String[] genero = context.getResources().getStringArray(R.array.genre);
//            String[] artista = context.getResources().getStringArray(R.array.artists);
//            TypedArray fotos = context.getResources().obtainTypedArray(R.array.fotosAlbum);
//            ArrayList<Music> musics = new ArrayList<>();
//
//            for (String s : infos) {
//                String[] info = s.split(",");
//                musics.add(new Music(
//                        info[0],
//                        Integer.parseInt(info[1]),
//                        fotos.getResourceId(Integer.parseInt(info[2]), 0),
//                        genero[Integer.parseInt(info[3])],
//                        artista[Integer.parseInt(info[4])],
//                        info[5]
//                ));
//            }
//            fotos.recycle();
//            Music[] musicsArr = new Music[infos.length];
//            return musics.toArray(musicsArr);
//
//        }
//        return null;
//    }
//
//    public static Music[] getMusicsSearch(Context context, String data) {
//        if (context != null) {
//            String[] infos = context.getResources().getStringArray(R.array.musics);
//            String[] genero = context.getResources().getStringArray(R.array.genre);
//            String[] artista = context.getResources().getStringArray(R.array.artists);
//            TypedArray fotos = context.getResources().obtainTypedArray(R.array.fotosAlbum);
//            ArrayList<Music> musics = new ArrayList<>();
//
//            for (String s : infos) {
//                String[] info = s.split(",");
//                Log.i("zipzop", "|" + artista[Integer.parseInt(info[4])] + "|" + data);
//                if (artista[Integer.parseInt(info[4])].toLowerCase().contains(data.toLowerCase())) {
//                    //artista
//                    musics.add(new Music(
//                            info[0],
//                            Integer.parseInt(info[1]),
//                            fotos.getResourceId(Integer.parseInt(info[2]), 0),
//                            genero[Integer.parseInt(info[3])],
//                            artista[Integer.parseInt(info[4])],
//                            info[5]
//                    ));
//                } else if (info[0].toLowerCase().contains(data.toLowerCase())) {
//                    //nome da musica
//                    musics.add(new Music(
//                            info[0],
//                            Integer.parseInt(info[1]),
//                            fotos.getResourceId(Integer.parseInt(info[2]), 0),
//                            genero[Integer.parseInt(info[3])],
//                            artista[Integer.parseInt(info[4])],
//                            info[5]
//                    ));
//                } else if (info[5].toLowerCase().contains(data.toLowerCase())) {
//                    //letra da musica
//                    musics.add(new Music(
//                            info[0],
//                            Integer.parseInt(info[1]),
//                            fotos.getResourceId(Integer.parseInt(info[2]), 0),
//                            genero[Integer.parseInt(info[3])],
//                            artista[Integer.parseInt(info[4])],
//                            info[5]
//                    ));
//                }
//            }
//            fotos.recycle();
//            Music[] musicsArr = new Music[musics.size()];
//            return musics.toArray(musicsArr);
//
//        }
//        return null;
//    }
//
//    public static Music getMusicsOne(Context context, String nome, String art) {
//        if (context != null) {
//            String[] infos = context.getResources().getStringArray(R.array.musics);
//            String[] genero = context.getResources().getStringArray(R.array.genre);
//            String[] artista = context.getResources().getStringArray(R.array.artists);
//            String[] letra = context.getResources().getStringArray(R.array.lyrics);
//            TypedArray fotos = context.getResources().obtainTypedArray(R.array.fotosAlbumLarge);
//            ArrayList<Music> musics = new ArrayList<>();
//
//            for (String s : infos) {
//                String[] info = s.split(",");
//                if (artista[Integer.parseInt(info[4])].equals(art)) {
//                    if (info[0].equals(nome)) {
//                        return new Music(
//                                info[0],
//                                Integer.parseInt(info[1]),
//                                fotos.getResourceId(Integer.parseInt(info[2]), 0),
//                                genero[Integer.parseInt(info[3])],
//                                artista[Integer.parseInt(info[4])],
//                                letra[Integer.parseInt(info[5])]);
//                    }
//                    ;
//                }
//
//            }
//        }
//        return null;
//    }

}
