package DiscordBot;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import net.dv8tion.jda.api.entities.Guild;

public class ControleMusicaServer {
    private final OrganizadorFaixa organizadorFaixa;
    private final AudioForwarder audioForwarder;

    public ControleMusicaServer(AudioPlayerManager manager, Guild guild){
       AudioPlayer player =manager.createPlayer();
       organizadorFaixa =new OrganizadorFaixa(player);
       player.addListener(organizadorFaixa);
       audioForwarder= new AudioForwarder(player,guild);
    }

    public OrganizadorFaixa getOrganizadorTrack() {
        return organizadorFaixa;
    }

    public AudioForwarder getAudioForwarder() {
        return audioForwarder;
    }
}
