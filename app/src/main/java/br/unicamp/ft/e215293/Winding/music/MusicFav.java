package br.unicamp.ft.e215293.Winding.music;

public class MusicFav {
    private String userId;
    private int musicId;

    public MusicFav() {
    }

    public MusicFav(String userId, int musicId) {
        this.userId = userId;
        this.musicId = musicId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public int getMusicId() {
        return musicId;
    }

    public void setMusicId(int musicId) {
        this.musicId = musicId;
    }
}
