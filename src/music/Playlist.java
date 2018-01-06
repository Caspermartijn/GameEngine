package music;

import utils.maths.Maths;

import java.util.ArrayList;
import java.util.List;

public class Playlist
{
  private List<MusicTrack> musics = new ArrayList<MusicTrack>();
  
  public void addMusic(MusicTrack music)
  {
    this.musics.add(music);
  }
  
  public void clear()
  {
    this.musics.clear();
  }
  
  public boolean isLoaded()
  {
    for (MusicTrack sound : this.musics) {
      if (!sound.getSound().isLoaded()) {
        return false;
      }
    }
    return true;
  }
  
  public List<MusicTrack> getOrderedTracks()
  {
    return this.musics;
  }
  
  protected List<MusicTrack> getShuffledMusicList(MusicTrack previouslyPlayed)
  {
    List<MusicTrack> tempList = new ArrayList<MusicTrack>();
    tempList.addAll(this.musics);
    List<MusicTrack> shuffledList = new ArrayList<MusicTrack>();
    while (!tempList.isEmpty())
    {
      MusicTrack track = removeRandomTrackFromList(tempList);
      if (!track.isLocked()) {
        shuffledList.add(track);
      }
    }
    ensurePreviousTrackNotRepeated(shuffledList, previouslyPlayed);
    return shuffledList;
  }
  
  private MusicTrack removeRandomTrackFromList(List<MusicTrack> listOfMusic)
  {
    int index = Maths.RANDOM.nextInt(listOfMusic.size());
    return (MusicTrack)listOfMusic.remove(index);
  }
  
  private void ensurePreviousTrackNotRepeated(List<MusicTrack> newPlaylist, MusicTrack previouslyPlayed)
  {
    if ((!newPlaylist.isEmpty()) && (newPlaylist.get(0) == previouslyPlayed))
    {
      MusicTrack track = (MusicTrack)newPlaylist.remove(0);
      newPlaylist.add(track);
    }
  }
}
