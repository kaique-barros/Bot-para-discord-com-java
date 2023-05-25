package DiscordBot;

import com.sedmelluq.discord.lavaplayer.player.AudioLoadResultHandler;
import com.sedmelluq.discord.lavaplayer.player.AudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.player.DefaultAudioPlayerManager;
import com.sedmelluq.discord.lavaplayer.source.AudioSourceManagers;
import com.sedmelluq.discord.lavaplayer.tools.FriendlyException;
import com.sedmelluq.discord.lavaplayer.track.AudioPlaylist;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import net.dv8tion.jda.api.entities.Guild;

import java.util.HashMap;
import java.util.Map;

public class ControlePlayer {
    private static ControlePlayer INSTANCE;
    private final Map<Long, ControleMusicaServer> controleMusicaServer = new HashMap<>();
    private final AudioPlayerManager audioPlayerManager = new DefaultAudioPlayerManager();

    private ControlePlayer() {
        AudioSourceManagers.registerRemoteSources(audioPlayerManager);
        AudioSourceManagers.registerLocalSource(audioPlayerManager);
    }


    public static ControlePlayer get() {
        if (INSTANCE == null)
            INSTANCE = new ControlePlayer();

        return INSTANCE;
    }

    public ControleMusicaServer getControleMusicaServer(Guild guild) {
        return controleMusicaServer.computeIfAbsent(guild.getIdLong(), (guildId) -> {
            ControleMusicaServer controleMusica = new ControleMusicaServer(audioPlayerManager,guild);
            guild.getAudioManager().setSendingHandler(controleMusica.getAudioForwarder());

            return controleMusica;
        });
    }

    public void play(Guild guild, String UrlMusica) {
        ControleMusicaServer controleMusicaServer = getControleMusicaServer(guild);
        audioPlayerManager.loadItemOrdered(controleMusicaServer, UrlMusica, new AudioLoadResultHandler() {
            @Override
            public void trackLoaded(AudioTrack track) {
                controleMusicaServer.getOrganizadorTrack().queue(track);
            }

            @Override
            public void playlistLoaded(AudioPlaylist playlist) {
                controleMusicaServer.getOrganizadorTrack().queue(playlist.getTracks().get(0));
            }

            @Override
            public void noMatches() {

            }

            @Override
            public void loadFailed(FriendlyException exception) {

            }
        });
    }

}
