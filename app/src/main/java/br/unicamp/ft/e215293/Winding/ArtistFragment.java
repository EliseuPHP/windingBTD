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
import android.widget.ImageView;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

import br.unicamp.ft.e215293.Winding.artist.Artist;
import br.unicamp.ft.e215293.Winding.internet.ImageLoadTask;
import br.unicamp.ft.e215293.Winding.internet.JSONReceiver;
import br.unicamp.ft.e215293.Winding.internet.ReceiveJSON;
import br.unicamp.ft.e215293.Winding.music.Music;
import br.unicamp.ft.e215293.Winding.music.MusicAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistFragment extends Fragment implements JSONReceiver {

    private RecyclerView recyclerView;
    private MusicAdapter musicAdapter;
    private int origin = 0;

    private View view;

    private Artist artista;

    private ArrayList<Music> musicas = new ArrayList<>();

    public ArtistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_artist, container, false);

        assert getArguments() != null;
        artista = (Artist) getArguments().getSerializable("artist");
        origin = getArguments().getInt("origin");

        TextView nome_art = (TextView) view.findViewById(R.id.nome_art);
        TextView desc_art = (TextView) view.findViewById(R.id.desc_art);
        TextView pop_art = (TextView) view.findViewById(R.id.pop_art);
        ImageView imageView = (ImageView) view.findViewById(R.id.image_view);

        nome_art.setText(artista.getNomeArtista());
        if (artista.getDescArtista().equals("")) {
            desc_art.setVisibility(View.GONE);
        } else {
            desc_art.setText(artista.getDescArtista());
        }
        String pop = getContext().getResources().getString(R.string.popular, artista.getNomeArtista());
        pop_art.setText(pop.toUpperCase());
        new ImageLoadTask(artista.getArtArtista(), imageView).execute();

        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setNestedScrollingEnabled(false);

        if (origin == 1) {
            getArguments().remove("origin");
            origin = 0;

            makeACall(artista.getIdArtista());
            musicAdapter = new MusicAdapter(musicas);
        }

        MusicAdapter.MusicOnItemClickListener listener = new MusicAdapter.MusicOnItemClickListener() {

            @Override
            public void musicOnItemClickListener(Music music) {
//                Toast.makeText(getContext(), nome + "|" + art, Toast.LENGTH_SHORT).show();
                Bundle bundle = new Bundle();
                bundle.putSerializable("music", music);
                NavController navController = NavHostFragment.findNavController(ArtistFragment.this);
                navController.navigate(R.id.arestaAM, bundle);

            }
        };
        musicAdapter.setMusicOnItemClickListener(listener);

        recyclerView.setAdapter(musicAdapter);
        return view;
    }

    private void makeACall(int data) {
        System.out.println("*************" + data + "*****************");
        String url = "https://api.genius.com/artists/" + data + "/songs?sort=popularity&per_page=15&access_token=MaALaMqzcduGO5dzRrkDUQei8E-rbz2BKNeHhszXdgJbZHzat9IVBbisjWjU8h4n";
        new ReceiveJSON(ArtistFragment.this).execute(url);
    }

    @Override
    public void receiveJSON(JSONObject jsonObject) {
        try {
            JSONObject response = new JSONObject(jsonObject.getString("response"));
            JSONArray songs = response.getJSONArray("songs");

            for (int i = 0; i < songs.length() && i < 10; i++) {
                JSONObject data = new JSONObject(songs.getString(i));
                JSONObject artist = new JSONObject(data.getString("primary_artist"));

                int idMusica = data.getInt("id");
                String nomeMusica = data.getString("title");
                String songArt = data.getString("song_art_image_url");
                String lPath = "http://genius.com" + data.getString("path");
                String nomeArtista = artist.getString("name");
                int idArtist = artist.getInt("id");
                Music musica = new Music(idMusica, nomeMusica, songArt, lPath, idArtist, nomeArtista);
                musicas.add(musica);
                if (!artista.getNomeArtista().equals(nomeArtista)) {
                    musicas.remove(musica);
                }
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
