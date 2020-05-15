package br.unicamp.ft.e215293.Winding;

import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.Arrays;

import br.unicamp.ft.e215293.Winding.music.Music;
import br.unicamp.ft.e215293.Winding.music.MusicAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class MusicsFragment extends Fragment {

    private RecyclerView recyclerView;
    private MusicAdapter musicAdapter;

    public MusicsFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_musics, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        assert getArguments() != null;
        String data = getArguments().getString("data");
        if (!data.equals("defaultVal")) {
            musicAdapter = new MusicAdapter(
                    new ArrayList(Arrays.asList(Music.getMusicsSearch(getContext(), data)))
            );
        } else {
            musicAdapter = new MusicAdapter(
                    new ArrayList(Arrays.asList(Music.getMusics(getContext())))
            );
        }
        MusicAdapter.MusicOnItemClickListener listener = new MusicAdapter.MusicOnItemClickListener() {

            @Override
            public void musicOnItemClickListener(String nome, String art) {
                Toast.makeText(getContext(), nome+"|"+art, Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putString("nome", nome);
                bundle.putString("art", art);
                NavController navController = NavHostFragment.findNavController(MusicsFragment.this);
                navController.navigate(R.id.arestaMS, bundle);

            }
        };
        musicAdapter.setMusicOnItemClickListener(listener);

        recyclerView.setAdapter(musicAdapter);
        return view;
    }
}
