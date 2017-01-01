package discordbot;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import javax.security.auth.login.LoginException;

import org.aeonbits.owner.ConfigFactory;
import org.apache.commons.io.FileUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import net.dv8tion.jda.core.AccountType;
import net.dv8tion.jda.core.JDA;
import net.dv8tion.jda.core.JDABuilder;
import net.dv8tion.jda.core.exceptions.RateLimitedException;

public class BotMain {
	static JDA jda;
	public static Logger log = LoggerFactory.getLogger("main");
	public static MainConfig cfg;
	private static CommandRegistrar register;
	
	@CoverageIgnore
	public static void main(String[] args) {
		// Default config file creation
		if(!new File("bot.properties").exists()) {
			URL cfgurl = BotMain.class.getResource("/default.properties");
			try {
				FileUtils.copyURLToFile(cfgurl, new File("bot.properties"));
			} catch (IOException e) {
				log.error("Couldn't make a config file!", e);
				return;
			}
			
			log.error("No config file present; a default one was made. "); 
			return;
		}
		
		cfg = ConfigFactory.create(MainConfig.class);
		
		try {
			jda = new JDABuilder(AccountType.BOT).setToken(cfg.botToken()).buildBlocking().asBot().getJDA();
		} catch (IllegalArgumentException
				| InterruptedException | RateLimitedException e) {
			log.error("Something went wrong instantating JDA", e);
			return;
		} catch (LoginException e) {
			log.error("Please verify that the bot token in the config file is correct. ");
		}
		
		register = new CommandRegistrar(cfg.prefix());
		jda.addEventListener(register);
		
		registerCommands();
	}
	
	@CoverageIgnore
	private static void registerCommands() {
		// Ping
		register.registerCommand(new PingCommand());
		
		// Poll system
		MakepollCommand mpc;
		register.registerCommand(mpc = new MakepollCommand());
		register.registerCommand(new VoteCommand(mpc.pf));
		register.registerCommand(new ClosepollCommand(mpc.pf));
	}

}
