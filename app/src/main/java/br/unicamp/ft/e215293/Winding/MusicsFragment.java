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

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;

import br.unicamp.ft.e215293.Winding.internet.JSONReceiver;
import br.unicamp.ft.e215293.Winding.internet.ReceiveJSON;
import br.unicamp.ft.e215293.Winding.music.Music;
import br.unicamp.ft.e215293.Winding.music.MusicAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class MusicsFragment extends Fragment implements JSONReceiver {

    private RecyclerView recyclerView;
    private MusicAdapter musicAdapter;
    private int origin = 0;

    private ArrayList<Music> musicas = new ArrayList<>();

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

        String data;

        assert getArguments() != null;
        origin = getArguments().getInt("origin");
        data = getArguments().getString("data");

        if (origin == 1) {
            getArguments().remove("origin");
            origin = 0;

            if (!data.equals("")) {
                makeACall(data);
                musicAdapter = new MusicAdapter(musicas);
            } else {
                data = "%25";
                makeACall(data);
                musicAdapter = new MusicAdapter(musicas);
            }
        }

        MusicAdapter.MusicOnItemClickListener listener = new MusicAdapter.MusicOnItemClickListener() {

            @Override
            public void musicOnItemClickListener(Music music) {
//                Toast.makeText(getContext(), nome + "|" + art, Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putSerializable("music", music);
                NavController navController = NavHostFragment.findNavController(MusicsFragment.this);
                navController.navigate(R.id.arestaMS, bundle);

            }
        };

        if (musicAdapter == null) {
            data = "%25";
            makeACall(data);
            musicAdapter = new MusicAdapter(musicas);
        }

        musicAdapter.setMusicOnItemClickListener(listener);

        recyclerView.setAdapter(musicAdapter);
        return view;
    }

    private void makeACall(String data){
        System.out.println("*************" + data + "*****************");
        String url = "https://api.genius.com/search?q=" + data + "&per_page=10&page=1&sort=popularity&access_token=MaALaMqzcduGO5dzRrkDUQei8E-rbz2BKNeHhszXdgJbZHzat9IVBbisjWjU8h4n";
        new ReceiveJSON(MusicsFragment.this).execute(url);
    }

    @Override
    public void receiveJSON(JSONObject jsonObject) {
        try {
            JSONObject response = new JSONObject(jsonObject.getString("response"));
            JSONArray hits = response.getJSONArray("hits");

            for (int i = 0; i < hits.length(); i++) {
                JSONObject track = new JSONObject(hits.getString(i));
                JSONObject data = new JSONObject(track.getString("result"));
                JSONObject artist = new JSONObject(data.getString("primary_artist"));

                String nome = data.getString("title");
                String songArt = data.getString("song_art_image_url");
                int idMusica = data.getInt("id");
                String artista = artist.getString("name");
                int idArtist = artist.getInt("id");
                String lPath = "http://genius.com" + data.getString("path");
                Music musica = new Music(idMusica, nome, songArt, lPath, idArtist, artista);
                musicas.add(musica);
                System.out.println(musicas.get(i).getNome());
            }

        } catch (JSONException e) {
            e.printStackTrace();
        }
        refreshData(musicas);
    }

    private void refreshData(ArrayList<Music> data) {
        musicas = new ArrayList<Music>(data);
        musicAdapter.notifyDataSetChanged();
    }
}
