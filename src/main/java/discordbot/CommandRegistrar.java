package discordbot;

import java.util.HashMap;
import java.util.Map;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;
import net.dv8tion.jda.core.hooks.ListenerAdapter;

public class CommandRegistrar extends ListenerAdapter {
	Map<String, BotCommand> commands = new HashMap<>();
	
	@Override
	public void onMessageReceived(MessageReceivedEvent event) {
		String message = event.getMessage().getRawContent();
		
		// If it doesn't begin with prefix, ignore it
		if(!message.startsWith(BotMain.cfg.prefix()))
			return;
		
		// Chop off the prefix and the rest of the command
		String command = message.substring(BotMain.cfg.prefix().length()).split(" ")[0];

		BotMain.log.info(command);
		BotMain.log.info(commands.toString());
		commands.get(command).run(event);
	}
	
	public void registerCommand(BotCommand c) {
		commands.put(c.getCommand(), c);
	}
}
