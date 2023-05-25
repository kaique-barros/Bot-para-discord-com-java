package DiscordBot.ComandosMusicPlayer;

import DiscordBot.ControleMusicaServer;
import DiscordBot.ControlePlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

public class MusicaAtual implements ICommand {
    @Override
    public String getName() {
        return "musica";
    }

    @Override
    public String getDescription() {
        return "Informacoes da musica atual";
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
            event.reply("Eu não estou em um canal de voz").queue();
            return;
        }

        if(selfVoiceState.getChannel() != memberVoiceState.getChannel()) {
            event.reply("Voce não esta no mesmo canal que eu").queue();
            return;
        }

        ControleMusicaServer controleMusicaServer= ControlePlayer.get().getControleMusicaServer(event.getGuild());

        if (controleMusicaServer.getOrganizadorTrack().getPlayer().getPlayingTrack()==null){
            event.reply("Não esta tocando nenhuma musica agora").queue();
            return;
        }
        AudioTrackInfo info= controleMusicaServer.getOrganizadorTrack().getPlayer().getPlayingTrack().getInfo();
        EmbedBuilder embedBuilder=new EmbedBuilder();
        embedBuilder.setTitle("Tocando no momento");
        embedBuilder.setDescription("**Nome:** `"+info.title+"`");
        embedBuilder.appendDescription("\n**Autor:** `" + info.author + "`");
        embedBuilder.appendDescription("\n**URL:** `" + info.uri + "`");
        event.replyEmbeds(embedBuilder.build()).queue();

    }
}
