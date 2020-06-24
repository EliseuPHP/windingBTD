package br.unicamp.ft.e215293.Winding.internet;

import android.os.Bundle;

import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;

import br.unicamp.ft.e215293.Winding.R;
import br.unicamp.ft.e215293.Winding.music.Music;


/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment implements JSONReceiver {

    private View lview;
    private TextView textView;
    private EditText editText;

    public TestFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        if (lview == null) {
            lview = inflater.inflate(R.layout.fragment_test, container, false);
        }

        textView = lview.findViewById(R.id.textView);
        editText = lview.findViewById(R.id.editText);


        lview.findViewById(R.id.btnAction).setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        String url = "https://api.genius.com/search?q=" + editText.getText().toString() + "&access_token=MaALaMqzcduGO5dzRrkDUQei8E-rbz2BKNeHhszXdgJbZHzat9IVBbisjWjU8h4n";
                        new ReceiveJSON(TestFragment.this).execute(url);
                    }
                }
        );


        return lview;
    }

    @Override
    public void receiveJSON(JSONObject jsonObject) {
        ArrayList<Music> musicas = new ArrayList<>();

        try {
            textView.setText("New JSON: \n");
            JSONObject response = new JSONObject(jsonObject.getString("response"));
            JSONArray hits = response.getJSONArray("hits");
//            String lPath = "http://genius.com" + "/Michael-jackson-billie-jean-lyrics";

            for (int i = 0; i < hits.length(); i++) {
                JSONObject track = new JSONObject(hits.getString(i));
                JSONObject data = new JSONObject(track.getString("result"));
                JSONObject artist = new JSONObject(data.getString("primary_artist"));

                String nome = data.getString("title");
                String songArt = data.getString("song_art_image_url");
                String lPath = "http://genius.com" + data.getString("path");
                int idMusica = data.getInt("id");
                String artista = artist.getString("name");
                int idArtist = artist.getInt("id");
                Music musica = new Music(idMusica, nome, songArt, lPath, idArtist, artista);
                musicas.add(musica);
                textView.append("\n ");
//                textView.append(data.getString("track_id") + "\n");
//                textView.append(data.getString("title") + "\n");
//                textView.append(data.getString("artist_name") + "\n");
//                textView.append(data.getString("album_name") + "\n");
//                textView.append(data.getString("album_id") + "\n");
                textView.append(musicas.get(i).getNome() + "\n");
                textView.append(musicas.get(i).getArtista() + "\n");
                textView.append(musicas.get(i).getLetra() + "\n");
                textView.append("\n\n ");
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
