import net.dv8tion.jda.api.*;

import javax.security.auth.login.LoginException;

public class Main {
    public static void main(String[] args) throws LoginException {
        String token = "";
        JDA jda = JDABuilder.createDefault(token).build();
        jda.addEventListener(new Listeners());
    }
}
