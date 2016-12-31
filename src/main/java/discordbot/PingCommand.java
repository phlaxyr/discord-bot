package discordbot;

import net.dv8tion.jda.core.entities.ChannelType;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class PingCommand implements BotCommand {

	@Override
	public String getCommand() {
		return "ping";
	}

	@Override
	public void run(MessageReceivedEvent e) {
		if(e.isFromType(ChannelType.TEXT)) {
			BotMain.log.info("supposedly replying");
			e.getTextChannel().sendMessage("Pong!").queue();
		}
	}

}
