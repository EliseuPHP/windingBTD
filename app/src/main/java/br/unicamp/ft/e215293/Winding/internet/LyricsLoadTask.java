package br.unicamp.ft.e215293.Winding.internet;

import android.os.AsyncTask;
import android.widget.TextView;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;

public class LyricsLoadTask extends AsyncTask<Void, Void, String> {

    private String url;
    TextView textView;

    public LyricsLoadTask(String url, TextView textView) {
        this.url = url;
        this.textView = textView;
    }

    @Override
    protected String doInBackground(Void... voids) {
        Document document;
        String lyric = "";

        try {
            while (lyric == "") {
                document = Jsoup.connect(url).get();
                document.outputSettings(new Document.OutputSettings().prettyPrint(false));//makes html() preserve linebreaks and spacing
                Elements lyricDiv = document.select("div.lyrics");
                lyricDiv.select("br").append("***");
                String s = lyricDiv.text();
                lyric = s.replace("***", "\n");
                //System.out.println(lyric);
                if (lyric == "") {
                    System.out.println("null lyric");
                } else {
                    System.out.println("lyric found");
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return lyric;
    }

    @Override
    protected void onPostExecute(String string) {
        super.onPostExecute(string);
        textView.setText(string);
    }
}
