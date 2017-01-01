package discordbot;

import java.util.HashMap;
import java.util.Map;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CommandRegistrar extends ListenerAdapter {
	Map<String, BotCommand> commands = new HashMap<>();
	
	private String prefix;
	
	public CommandRegistrar(String prefix) {
		this.prefix = prefix;
	}
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String message = event.getMessage().getRawContent();
		
		// If it doesn't begin with prefix, ignore it
		if(!message.startsWith(prefix))
			return;
		
		// Chop off the prefix and the rest of the command
		String command = message.substring(prefix.length()).split(" ")[0];
		
		commands.get(command).run(event);
	}
	
	public void registerCommand(BotCommand c) {
		commands.put(c.getCommand(), c);
	}
}
