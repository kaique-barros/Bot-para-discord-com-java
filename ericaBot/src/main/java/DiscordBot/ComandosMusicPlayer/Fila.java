package DiscordBot.ComandosMusicPlayer;

import DiscordBot.ControleMusicaServer;
import DiscordBot.ControlePlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrack;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Fila implements ICommand{
    @Override
    public String getName() {
        return "fila";
    }

    @Override
    public String getDescription() {
        return "Mostrar as musicas que estao na fila";
    }

    @Override
    public List<OptionData> getOptions() {
        return null;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Member member= event.getMember();
        GuildVoiceState memberVoiceState=member.getVoiceState();

        if(!memberVoiceState.inAudioChannel()){
            event.reply("Necessario estar em um canal de voz").queue();
            return;
        }

        Member self= event.getGuild().getSelfMember();
        GuildVoiceState selfVoiceState=self.getVoiceState();

        if(!selfVoiceState.inAudioChannel()){
            event.reply("Eu nao estou em um canal de voz").queue();
            return;
        }

        if(selfVoiceState.getChannel() != memberVoiceState.getChannel()) {
            event.reply("Voce nao esta no mesmo canal que eu").queue();
            return;
        }
        ControleMusicaServer controleMusicaServer= ControlePlayer.get().getControleMusicaServer(event.getGuild());
        List<AudioTrack> faixas= new ArrayList<>(controleMusicaServer.getOrganizadorTrack().getQueue());
        EmbedBuilder embedBuilder= new EmbedBuilder();

        embedBuilder.setTitle("Na fila:\n");
        if(faixas.isEmpty()) {
            embedBuilder.setDescription("A fila esta vazia");
        }
        for(int i=0;i<faixas.size();i++){
            AudioTrackInfo info=faixas.get(i).getInfo();
            embedBuilder.addField(i+1+":", info.title, false);

        }
        embedBuilder.setColor(Color.BLUE);
        event.replyEmbeds(embedBuilder.build()).queue();

    }
}
