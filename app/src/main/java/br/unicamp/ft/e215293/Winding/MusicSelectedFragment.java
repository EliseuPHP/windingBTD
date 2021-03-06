package br.unicamp.ft.e215293.Winding;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
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

import br.unicamp.ft.e215293.Winding.artist.Artist;
import br.unicamp.ft.e215293.Winding.internet.ImageLoadTask;
import br.unicamp.ft.e215293.Winding.internet.JSONReceiver;
import br.unicamp.ft.e215293.Winding.internet.LyricsLoadTask;
import br.unicamp.ft.e215293.Winding.internet.ReceiveJSON;
import br.unicamp.ft.e215293.Winding.music.Music;
import br.unicamp.ft.e215293.Winding.music.MusicFav;


/**
 * A simple {@link Fragment} subclass.
 */
public class MusicSelectedFragment extends Fragment implements JSONReceiver {

    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private View view;
    private Music music;
    private DatabaseReference mDatabaseReference;
    private Bundle bundle = new Bundle();

    public MusicSelectedFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        view = inflater.inflate(R.layout.fragment_music_selected, container, false);
        assert getArguments() != null;
        music = (Music) getArguments().getSerializable("music");
        ImageView imageView = (ImageView) view.findViewById(R.id.image_view);
        TextView textViewNome = (TextView) view.findViewById(R.id.nome_mus);
        TextView textViewArt = (TextView) view.findViewById(R.id.nome_art);
        TextView textViewLet = (TextView) view.findViewById(R.id.nome_letra);
        new ImageLoadTask(music.getSongArt(), imageView).execute();

        textViewNome.setText(music.getNomeMusica());
        textViewArt.setText(music.getNomeArtista());
        textViewArt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //criar artista
                makeACall(music.getIdArtista());
            }
        });
        new LyricsLoadTask(music.getLetra(), textViewLet).execute();
        return view;
    }

    @Override
    public void onStart() {
        super.onStart();

        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();
        mDatabaseReference = FirebaseDatabase.getInstance().getReference();

        if (mFirebaseUser != null) {
            final TextView icon_fav = (TextView) view.findViewById(R.id.icon_fav);
            icon_fav.setVisibility(View.VISIBLE);
            final String userId = mFirebaseUser.getUid();
            final int musicId = music.getIdMusica();
            updUiDatabase(userId, musicId, icon_fav, 0);
            icon_fav.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    updUiDatabase(userId, musicId, icon_fav, 1);
                }
            });
        } else {
            TextView icon_fav = (TextView) view.findViewById(R.id.icon_fav);
            icon_fav.setVisibility(View.GONE);
        }

    }

    private void updUiDatabase(final String userId, final int musicId, final TextView icon_fav, final int click) {
        DatabaseReference myRef = mDatabaseReference.child(userId).child(String.valueOf(musicId));
        myRef.addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                System.out.println("" + dataSnapshot);
                if (dataSnapshot.getValue() == null) {
                    if (click == 1) {
                        System.out.println(musicId + " added to favorites");
                        MusicFav musicFav = new MusicFav(userId, musicId);
                        mDatabaseReference.child(userId).child(String.valueOf(musicId)).setValue(musicFav);
                        icon_fav.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_red, 0, 0, 0);
                    } else {
                        icon_fav.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart, 0, 0, 0);
                    }
                } else {
                    if (click == 1) {
                        System.out.println("deleting" + musicId);
                        mDatabaseReference.child(userId).child(String.valueOf(musicId)).setValue(null);
                        icon_fav.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart, 0, 0, 0);
                    } else {
                        icon_fav.setCompoundDrawablesWithIntrinsicBounds(R.drawable.ic_heart_red, 0, 0, 0);
                    }
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void makeACall(int data) {
        System.out.println("*************" + data + "*****************");
        String url = "https://api.genius.com/artists/" + data + "?&access_token=MaALaMqzcduGO5dzRrkDUQei8E-rbz2BKNeHhszXdgJbZHzat9IVBbisjWjU8h4n";
        new ReceiveJSON(MusicSelectedFragment.this).execute(url);
    }

    @Override
    public void receiveJSON(JSONObject jsonObject) {
        try {
            JSONObject response = new JSONObject(jsonObject.getString("response"));

            JSONObject artist = new JSONObject(response.getString("artist"));

            int idArtista = artist.getInt("id");
            String nomeArtista = artist.getString("name");
            String artArtista = artist.getString("image_url");

            JSONObject description = new JSONObject(artist.getString("description"));
            JSONObject dom = new JSONObject(description.getString("dom"));
            JSONArray children1 = dom.getJSONArray("children");
            JSONObject children2 = new JSONObject(children1.getString(0));
            JSONArray children3 = new JSONArray(children2.getString("children"));
            String desc = children2.toString();
            String descArtista = "";
            desc = desc.replace("{", "¨");
            desc = desc.replace("}", "¨");
            desc = desc.replace("[", "¨");
            desc = desc.replace("]", "¨");
            String[] parts = desc.split("¨");
            for (String i : parts) {
                System.out.println(i+"*****");
                if (!i.contains("\"tag\"")&&!i.contains("\"children\"")&&!i.contains("http")&&!i.contains("data\":")&&!i.contains("api_path")) {
                    descArtista += i;
                }
            }
            descArtista = descArtista.replace(",\"","");
            descArtista = descArtista.replace("\",","");
            descArtista = descArtista.replace("\"","");
            descArtista = descArtista.replace("\\","");

            Artist artista = new Artist(idArtista, nomeArtista, artArtista, descArtista);
            bundle.putSerializable("artist", artista);
            bundle.putInt("origin",1);
            NavController navController = NavHostFragment.findNavController(MusicSelectedFragment.this);
            navController.navigate(R.id.arestaMA, bundle);

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
