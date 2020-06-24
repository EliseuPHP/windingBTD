package br.unicamp.ft.e215293.Winding.music;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import br.unicamp.ft.e215293.Winding.R;
import br.unicamp.ft.e215293.Winding.internet.ImageLoadTask;

public class MusicAdapter extends RecyclerView.Adapter {

    private ArrayList<Music> musics;

    public MusicAdapter(ArrayList<Music> musics) {
        this.musics = musics;
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.adapter_layout, parent, false
        );
        final MusicViewHolder musicViewHolder = new MusicViewHolder(view);

        view.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (musicOnItemClickListener != null) {

                    TextView nome = v.findViewById(R.id.text_view_nome);
                    TextView artist = v.findViewById(R.id.text_view_art);
                    musicOnItemClickListener.musicOnItemClickListener(musics.get(musicViewHolder.getPosicao()));

                }
            }
        });

        return musicViewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {
        ((MusicViewHolder) holder).bind(musics.get(position));
        ((MusicViewHolder) holder).setPosicao(position);
    }

    @Override
    public int getItemCount() {
        return musics.size();
    }

    //Interface OnClick
    public interface MusicOnItemClickListener {
        void musicOnItemClickListener(Music music);
    }

    private MusicOnItemClickListener musicOnItemClickListener;

    public void setMusicOnItemClickListener(MusicOnItemClickListener m) {
        this.musicOnItemClickListener = m;
    }

    class MusicViewHolder extends RecyclerView.ViewHolder {

        private ImageView imageView;
        private TextView textViewNome;
        //private TextView textViewTrack;
//        private TextView textViewGen;
        private TextView textViewArt;
        private int posicao;

        public MusicViewHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.image_view);
            textViewNome = itemView.findViewById(R.id.text_view_nome);
            //textViewTrack = itemView.findViewById(R.id.text_view_track);
//            textViewGen = itemView.findViewById(R.id.text_view_gen);
            textViewArt = itemView.findViewById(R.id.text_view_art);
        }

        public void setPosicao(int posicao) {
            this.posicao = posicao;
        }

        public void bind(Music music) {
            new ImageLoadTask(music.getSongArt(), imageView).execute();
//            imageView.setImageResource(music.getFoto());
            textViewNome.setText(
                    textViewNome.getContext().getResources().getString(
                            R.string.texto_nome,
                            music.getNome())
            );
//            textViewTrack.setText(
//                    textViewNome.getContext().getResources().getString(
//                            R.string.texto_track,
//                            music.getTrackNu())
//            );
            textViewArt.setText(
                    textViewNome.getContext().getResources().getString(
                            R.string.texto_gen,
                            music.getArtista())
            );

        }

        public int getPosicao() {
            return this.posicao;
        }
    }

}
