import DiscordBot.ComandosMusicPlayer.*;
import DiscordBot.Comandos;
import net.dv8tion.jda.api.*;

import javax.security.auth.login.LoginException;
import static net.dv8tion.jda.api.requests.GatewayIntent.MESSAGE_CONTENT;

public class Main {
    public static JDA jda;

    public static void main(String[] args) throws LoginException {
        Token tk = new Token();
        jda = JDABuilder.createDefault(tk.getToken())
                .enableIntents(MESSAGE_CONTENT)
                .build();
        Comandos controles = new Comandos();
        controles.add(new Tocar());
        controles.add(new Pular());
        controles.add(new Repetir());
        controles.add(new Pausar());
        controles.add(new MusicaAtual());
        controles.add(new Fila());
        jda.addEventListener(controles);
    }
}
