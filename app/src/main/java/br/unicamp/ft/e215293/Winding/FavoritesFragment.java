package br.unicamp.ft.e215293.Winding;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import br.unicamp.ft.e215293.Winding.internet.JSONReceiver;
import br.unicamp.ft.e215293.Winding.internet.ReceiveJSON;
import br.unicamp.ft.e215293.Winding.music.Music;
import br.unicamp.ft.e215293.Winding.music.MusicAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class FavoritesFragment extends Fragment implements JSONReceiver {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;

    private RecyclerView recyclerView;
    private MusicAdapter musicAdapter;
    private int count = 0, quant = 0;

    private DatabaseReference mDatabaseReference;

    private View view;

    private ArrayList<Music> musicas = new ArrayList<>();

    public FavoritesFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_favorites, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getFavorites();

    }

    @Override
    public void onResume() {
        super.onResume();
        count = 0;
        getFavorites();

    }

    private void getFavorites() {
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();
        final String userId = mFirebaseUser.getUid();
        if (mFirebaseUser != null) {
            DatabaseReference myRef = mDatabaseReference.child(userId);
            myRef.addListenerForSingleValueEvent(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    musicas.clear();
                    if (dataSnapshot.getValue() != null) {
                        System.out.println("" + dataSnapshot.getValue());
                        Map<String, Object> td = (HashMap<String, Object>) dataSnapshot.getValue();
                        quant = td.keySet().size();
                        for (String key : td.keySet()) {
                            System.out.println(key);
                            makeACall(key);
                        }

                        musicAdapter = new MusicAdapter(musicas);

                        MusicAdapter.MusicOnItemClickListener listener = new MusicAdapter.MusicOnItemClickListener() {

                            @Override
                            public void musicOnItemClickListener(Music music) {
                                Bundle bundle = new Bundle();
                                bundle.putSerializable("music", music);
                                NavController navController = NavHostFragment.findNavController(FavoritesFragment.this);
                                navController.navigate(R.id.arestaMS, bundle);
                            }
                        };

                        musicAdapter.setMusicOnItemClickListener(listener);

                        recyclerView.setAdapter(musicAdapter);
                    } else {
                        TextView textView = (TextView) view.findViewById(R.id.text_loading);
                        textView.setText(R.string.no_fav);
                    }
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });
        }
    }

    private void makeACall(String data) {
        System.out.println("*************" + data + "*****************");
        String url = "https://api.genius.com/songs/" + data + "?&access_token=MaALaMqzcduGO5dzRrkDUQei8E-rbz2BKNeHhszXdgJbZHzat9IVBbisjWjU8h4n";
        new ReceiveJSON(FavoritesFragment.this).execute(url);
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
            musicas.add(musica);
            System.out.println(musica.getNomeMusica());
        } catch (JSONException e) {
            e.printStackTrace();
        }
        count++;
        if (count == quant) {
            TextView textView = (TextView) view.findViewById(R.id.text_loading);
            textView.setVisibility(View.GONE);
            refreshData(musicas);
        }
    }

    private void refreshData(ArrayList<Music> data) {
        musicas = new ArrayList<Music>(data);
        musicAdapter.notifyDataSetChanged();
    }
}
