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

import br.unicamp.ft.e215293.Winding.artist.Artist;
import br.unicamp.ft.e215293.Winding.artist.ArtistAdapter;


/**
 * A simple {@link Fragment} subclass.
 */
public class ArtistFragment extends Fragment {

    private RecyclerView recyclerView;
    private ArtistAdapter artistAdapter;

    public ArtistFragment() {
        // Required empty public constructor
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_artist, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        assert getArguments() != null;
        String data = getArguments().getString("data");
        assert data != null;
        if (!data.equals("defaultVal")) {
            artistAdapter = new ArtistAdapter(
                    new ArrayList(Arrays.asList(Artist.getArtistsSearch(getContext(), data)))
            );
        } else {
            artistAdapter = new ArtistAdapter(
                    new ArrayList(Arrays.asList(Artist.getArtists(getContext())))
            );
        }

        ArtistAdapter.ArtistOnItemClickListener listener = new ArtistAdapter.ArtistOnItemClickListener() {

            @Override
            public void artistOnItemClickListener(String nome) {
                Bundle bundle = new Bundle();
                bundle.putString("data", nome);
                //Toast.makeText(getContext(), nome, Toast.LENGTH_SHORT).show();
                NavController navController = NavHostFragment.findNavController(ArtistFragment.this);
                navController.navigate(R.id.aresta, bundle);
            }
        };
        artistAdapter.setArtistOnItemClickListener(listener);

        recyclerView.setAdapter(artistAdapter);
        return view;
    }
}
