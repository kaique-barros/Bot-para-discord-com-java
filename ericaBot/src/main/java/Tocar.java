import net.dv8tion.jda.api.entities.GuildVoiceState;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.interaction.command.SlashCommandInteractionEvent;
import net.dv8tion.jda.api.interactions.commands.OptionType;
import net.dv8tion.jda.api.interactions.commands.build.OptionData;
import java.util.ArrayList;
import java.util.List;

public class Tocar implements ICommand{

    @Override
    public String getName() {
        return "tocar";
    }

    @Override
    public String getDescription() {
        return "Will play a song";
    }

    @Override
    public List<OptionData> getOptions() {
        List<OptionData> options= new ArrayList<>();
        options.add(new OptionData(OptionType.STRING,"Name: ", "Name of the song to play",true));
        return options;
    }

    @Override
    public void execute(SlashCommandInteractionEvent event) {
        Member member= event.getMember();
        GuildVoiceState memberVoiceState=member.getVoiceState();

        if(!memberVoiceState.inAudioChannel()){
            event.reply("É Necessário estar em um canal de voz").queue();
            return;
        }

        Member self= event.getGuild().getSelfMember();
        GuildVoiceState selfVoiceState=self.getVoiceState();

        if(!selfVoiceState.inAudioChannel()){
            event.getGuild().getAudioManager().openAudioConnection(memberVoiceState.getChannel());
        }
        else{
            if(selfVoiceState.getChannel()!=memberVoiceState.getChannel()){
                event.reply("Você precisa estar no mesmo canal de voz que eu");
                return;
            }
        }

        ControlePlayer controlePlayer=ControlePlayer.get();
        event.reply("Tocando...").queue();
        controlePlayer.play(event.getGuild(), event.getOption("Name: ").getAsString());

    }


}
