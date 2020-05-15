package br.unicamp.ft.e215293.Winding.artist;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.unicamp.ft.e215293.Winding.R;

public class ArtistAdapter extends RecyclerView.Adapter {

    private ArrayList<Artist> artists;

    public ArtistAdapter(ArrayList<Artist> artists) {
        this.artists = artists;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_artists, parent, false
        );

        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (artistOnItemClickListener != null) {
                    TextView txt = v.findViewById(R.id.text_view_artist);
                    artistOnItemClickListener.artistOnItemClickListener(txt.getText().toString());
                }
            }
        });
        final ArtistViewHolder artistViewHolder = new ArtistViewHolder(view);

        return artistViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((ArtistViewHolder) holder).bind(artists.get(position));
        ((ArtistViewHolder) holder).setPosicao(position);
    }

    @Override
    public int getItemCount() {
        return artists.size();
    }

    //Interface OnClick
    public interface ArtistOnItemClickListener {
        void artistOnItemClickListener(String nome);
    }

    private ArtistOnItemClickListener artistOnItemClickListener;

    public void setArtistOnItemClickListener(ArtistOnItemClickListener m) {
        this.artistOnItemClickListener = m;
    }

    class ArtistViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textViewNome;
        private int posicao;

        public ArtistViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            textViewNome = itemView.findViewById(R.id.text_view_artist);
        }

        public void setPosicao(int posicao) {
            this.posicao = posicao;
        }

        public void bind(Artist artist) {
            imageView.setImageResource(artist.getFoto());
            textViewNome.setText(
                    textViewNome.getContext().getResources().getString(
                            R.string.texto_nome,
                            artist.getNome())
            );
        }

        public int getPosicao() {
            return this.posicao;
        }
    }
}
