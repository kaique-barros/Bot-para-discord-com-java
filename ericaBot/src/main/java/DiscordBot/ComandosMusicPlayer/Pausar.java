package DiscordBot.ComandosMusicPlayer;

import DiscordBot.ControleMusicaServer;
import DiscordBot.ControlePlayer;
import DiscordBot.OrganizadorFaixa;
import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;

import java.util.List;

public class Pausar implements ICommand{
    @Override
    public String getName() {
        return "pausar";
    }

    @Override
    public String getDescription() {
        return "Pausar musica atual";
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
        OrganizadorFaixa organizadorFaixa= controleMusicaServer.getOrganizadorTrack();
        boolean isRepeat=controleMusicaServer.getOrganizadorTrack().getIsRepeat();
        controleMusicaServer.getOrganizadorTrack().setRepeat(isRepeat);
        organizadorFaixa.getQueue().clear();
        organizadorFaixa.getPlayer().stopTrack();
        event.reply("Pausado").queue();
    }
}
