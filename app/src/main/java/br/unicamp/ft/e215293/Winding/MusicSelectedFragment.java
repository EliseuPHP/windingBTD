package br.unicamp.ft.e215293.Winding;

import android.media.Image;
import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.Arrays;

import br.unicamp.ft.e215293.Winding.internet.ImageLoadTask;
import br.unicamp.ft.e215293.Winding.internet.LyricsLoadTask;
import br.unicamp.ft.e215293.Winding.music.Music;


/**
 * A simple {@link Fragment} subclass.
 */
public class MusicSelectedFragment extends Fragment {

    public MusicSelectedFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_music_selected, container, false);
        assert getArguments() != null;
        Music music = (Music) getArguments().getSerializable("music");

        ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
        TextView textViewNome = (TextView) view.findViewById(R.id.nome_mus);
        TextView textViewArt = (TextView) view.findViewById(R.id.nome_art);
        TextView textViewLet = (TextView) view.findViewById(R.id.nome_letra);
        new ImageLoadTask(music.getSongArt(), imageView).execute();

        textViewNome.setText(music.getNome());
        textViewArt.setText(music.getArtista());
        new LyricsLoadTask(music.getLetra(), textViewLet).execute();
        return view;
    }
}
