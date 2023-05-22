import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;

public class ControleMusicaServer {
    private OrganizadorTrack organizadorTrack;
    private AudioForwarder audioForwarder;

    public ControleMusicaServer(AudioPlayerManager manager){
       AudioPlayer player =manager.createPlayer();
       organizadorTrack=new OrganizadorTrack(player);
       player.addListener(organizadorTrack);
       audioForwarder= new AudioForwarder(player);
    }

    public OrganizadorTrack getOrganizadorTrack() {
        return organizadorTrack;
    }

    public AudioForwarder getAudioForwarder() {
        return audioForwarder;
    }
}
