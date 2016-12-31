package discordbot;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public interface BotCommand {
	
	public String getCommand();
	
	public void run(MessageReceivedEvent e);
	
}