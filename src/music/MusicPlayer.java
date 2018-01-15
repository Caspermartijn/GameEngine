package music;

import audio.SoundSource;
import utils.binary.BinaryReader;
import utils.binary.BinaryWriter;

import java.util.ArrayList;
import java.util.List;

public class MusicPlayer
{
  private float musicVolume = 0.4F;
  private SoundSource source;
  private Playlist currentPlaylist;
  private List<MusicTrack> musicQueue = new ArrayList<MusicTrack>();
  private MusicTrack forcePlay = null;
  private MusicTrack currentlyPlaying = null;
  private boolean fadeOut = false;
  private float fadeFactor = 1.0F;
  private float finalVolume;
  private boolean shuffle;
  
  public MusicPlayer()
  {
    this.source = new SoundSource();
    this.source.loop(false);
    this.source.setUndiminishing();
  }
  
  public void forcePlay(MusicTrack music, boolean fadeOutPrevious)
  {
    this.forcePlay = music;
    if (fadeOutPrevious)
    {
      if (!this.fadeOut) {
        fadeOutCurrentTrack();
      }
    }
    else {
      this.source.stop();
    }
  }
  
  public void setVolume(float volume)
  {
    this.musicVolume = volume;
    if (this.currentlyPlaying != null) {
      this.source.setVolume(this.musicVolume * this.currentlyPlaying.getSound().getVolume());
    }
  }
  
  public float getVolume()
  {
    return this.musicVolume;
  }
  
  public void exportSettings(BinaryWriter writer)
  {
    writer.writeFloat(this.musicVolume);
  }
  
  public void loadSettings(BinaryReader reader)
    throws Exception
  {
    this.musicVolume = reader.readFloat();
  }
  
  public void playMusicPlaylist(Playlist playlist, boolean shuffle)
  {
    this.shuffle = shuffle;
    this.currentPlaylist = playlist;
    fadeOutCurrentTrack();
    this.musicQueue.clear();
    fillQueue();
  }
  
  public MusicTrack getCurrentlyPlaying()
  {
    return this.currentlyPlaying;
  }
  
  public Playlist getPlayList()
  {
    return this.currentPlaylist;
  }
  
  public void update(float delta)
  {
    if (this.fadeOut) {
      updateFadeOut(delta);
    }
    if ((!this.source.isPlaying()) && (!this.musicQueue.isEmpty()))
    {
      this.source.setInactive();
      playNextTrack();
    }
  }
  
  public void cleanUp()
  {
    this.source.delete();
  }
  
  private void updateFadeOut(float delta)
  {
    this.fadeFactor -= delta / 2.0F;
    this.source.setVolume(this.finalVolume * this.fadeFactor);
    if (this.fadeFactor <= 0.0F)
    {
      stopFadeOut();
      this.source.stop();
    }
  }
  
  private void playNextTrack()
  {
    stopFadeOut();
    MusicTrack nextTrack = getNextTrack();
    if (this.musicQueue.isEmpty()) {
      fillQueue();
    }
    this.currentlyPlaying = nextTrack;
    this.source.setVolume(this.musicVolume * this.currentlyPlaying.getSound().getVolume());
    this.source.playSound(this.currentlyPlaying.getSound());
  }
  
  private void stopFadeOut()
  {
    this.fadeOut = false;
    this.fadeFactor = 1.0F;
  }
  
  public void fadeOutCurrentTrack()
  {
    if (this.currentlyPlaying != null)
    {
      this.fadeOut = true;
      this.finalVolume = this.source.getVolume();
    }
  }
  
  private void fillQueue()
  {
    if (this.currentPlaylist == null) {
      return;
    }
    if (this.shuffle) {
      this.musicQueue.addAll(this.currentPlaylist.getShuffledMusicList(this.currentlyPlaying));
    } else {
      this.musicQueue.addAll(this.currentPlaylist.getOrderedTracks());
    }
  }
  
  private MusicTrack getNextTrack()
  {
    if (this.forcePlay != null)
    {
      MusicTrack upNext = this.forcePlay;
      this.forcePlay = null;
      return upNext;
    }
    return (MusicTrack)this.musicQueue.remove(0);
  }
}
