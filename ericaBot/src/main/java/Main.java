import net.dv8tion.jda.api.*;
import net.dv8tion.jda.api.requests.GatewayIntent;

import javax.security.auth.login.LoginException;

public class Main {

    public static JDA jda;
    public static void main(String[] args) throws LoginException {
        String token = "MTEwODg1NjQ5MTc5Mzk3MzI0OA.GvRGHT.4KtfXMlyTTulyRV7b8py0x1693woY2Pye3MLtA";
        jda = JDABuilder.createDefault(token)
                .enableIntents(GatewayIntent.MESSAGE_CONTENT)
                .build();

        jda.addEventListener(new Comandos());
    }
}
