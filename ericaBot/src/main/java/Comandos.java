import net.dv8tion.jda.api.entities.channel.middleman.MessageChannel;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;

public class Comandos extends ListenerAdapter {
    @Override
    public void onMessageReceived(@NotNull MessageReceivedEvent event) {
        if(event.getAuthor().isBot()) return;
        MessageChannel canal = event.getChannel();

        if(event.getAuthor().getId().equals("450117122589458433") || event.getAuthor().getId().equals("815308158893293580")){    //tira isso nn
            canal.sendMessage("não").queue();
            return;
        }

    }
}
