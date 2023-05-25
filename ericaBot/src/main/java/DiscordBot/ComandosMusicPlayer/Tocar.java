package DiscordBot.ComandosMusicPlayer;

import DiscordBot.ControleMusicaServer;
import DiscordBot.ControlePlayer;
import com.sedmelluq.discord.lavaplayer.track.AudioTrackInfo;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.net.URI;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.List;
import java.util.Timer;
import java.util.TimerTask;

public class Tocar implements ICommand {

    @Override
    public String getName() {
        return "tocar";
    }

    @Override
    public String getDescription() {
        return "Digite uma musica para eu tocar";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> options = new ArrayList<>();
        options.add(new OptionData(OptionType.STRING, "nome", "Nome da musica para tocar", true));
        return options;
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
            event.getGuild().getAudioManager().openAudioConnection(memberVoiceState.getChannel());
        }
        else{
            if(selfVoiceState.getChannel()!=memberVoiceState.getChannel()){
                event.reply("Voce precisa estar no mesmo canal de voz que eu");
                return;
            }
        }

        String name= event.getOption("nome").getAsString();

        try{
            new URI(name);
        }catch(URISyntaxException e){
            name = "ytsearch:" +name;
        }

        ControlePlayer controlePlayer= ControlePlayer.get();
        controlePlayer.play(event.getGuild(),name);
        ControleMusicaServer controleMusicaServer= ControlePlayer.get().getControleMusicaServer(event.getGuild());
        event.reply("Tocando...").queue();


    }


}
