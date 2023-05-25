package DiscordBot;

import com.sedmelluq.discord.lavaplayer.player.AudioPlayer;
import com.sedmelluq.discord.lavaplayer.track.playback.MutableAudioFrame;
import net.dv8tion.jda.api.audio.AudioSendHandler;
import net.dv8tion.jda.api.entities.Guild;
import org.jetbrains.annotations.Nullable;

import java.nio.Buffer;
import java.nio.ByteBuffer;

public class AudioForwarder implements AudioSendHandler {
    private final AudioPlayer player;
    private final ByteBuffer buffer= ByteBuffer.allocate(1024);
    private  final MutableAudioFrame frame=new MutableAudioFrame();
    private int time;
    private final Guild guild;
    public AudioForwarder(AudioPlayer player,Guild guild ) {
        this.player = player;
        frame.setBuffer(buffer);
        this.guild=guild;
    }

    @Override
    public boolean canProvide() {
        boolean canProvide=player.provide(frame);
        if(!canProvide) {
            time += 20;
            if(time >= 300000) {
                time = 0;
                guild.getAudioManager().closeAudioConnection();
            }
        } else {
            time = 0;
        }
        return canProvide;
    }
    @Nullable
    @Override
    public ByteBuffer provide20MsAudio() {
        return buffer.flip();
    }

    @Override
    public boolean isOpus() {
        return true;
    }
}
