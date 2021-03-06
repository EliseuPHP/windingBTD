package br.unicamp.ft.e215293.Winding.internet;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.unicamp.ft.e215293.Winding.R;
import br.unicamp.ft.e215293.Winding.music.Music;
import br.unicamp.ft.e215293.Winding.music.MusicFav;


/**
 * A simple {@link Fragment} subclass.
 */
public class TestFragment extends Fragment implements JSONReceiver {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private View lview;
    private TextView textView;
    private EditText editText;
    private DatabaseReference mDatabaseReference;

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

                    }
                }
        );


        return lview;
    }

    @Override
    public void onStart() {
        super.onStart();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        final String userId = mFirebaseUser.getUid();
        if (mFirebaseUser != null) {
            DatabaseReference myRef = mDatabaseReference.child(userId);
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    System.out.println("" + dataSnapshot.getValue());
                    Map<String, Object> td = (HashMap<String, Object>) dataSnapshot.getValue();

                    for (String key : td.keySet()) {
                        System.out.println(key);
                        String url = "https://api.genius.com/songs/" + key + "?&access_token=MaALaMqzcduGO5dzRrkDUQei8E-rbz2BKNeHhszXdgJbZHzat9IVBbisjWjU8h4n";
                        new ReceiveJSON(TestFragment.this).execute(url);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }

    }

    @Override
    public void receiveJSON(JSONObject jsonObject) {
        try {
            JSONObject response = new JSONObject(jsonObject.getString("response"));

            JSONObject data = new JSONObject(response.getString("song"));
            JSONObject artist = new JSONObject(data.getString("primary_artist"));

            String nome = data.getString("title");
            String songArt = data.getString("song_art_image_url");
            int idMusica = data.getInt("id");
            String artista = artist.getString("name");
            int idArtist = artist.getInt("id");
            String lPath = "http://genius.com" + data.getString("path");
            Music musica = new Music(idMusica, nome, songArt, lPath, idArtist, artista);

            textView.append("\n"+nome+"\n\n");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
