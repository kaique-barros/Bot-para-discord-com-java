import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.requests.GatewayIntent;
import javax.security.auth.login.LoginException;

public class Main {
    public static JDA jda;
    public static void main(String[] args) throws LoginException {
        Token tk = new Token();
        jda = JDABuilder.createDefault(tk.getToken())
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();
        Comandos controles=new Comandos();
        controles.add(new Tocar());
        jda.addEventListener(controles);
    }
}
