package discordbot;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class ClosepollCommand implements BotCommand {
	PollFactory pf;
	
	public ClosepollCommand(PollFactory pf) {
		this.pf = pf;
	}
	
	@Override
	public String getCommand() {
		return "closepoll";
	}

	@Override
	public void run(MessageReceivedEvent e) {
		if(!e.getChannel().getId().equals(BotMain.cfg.adminChannelID()))
			return;
		
		String[] parts = e.getMessage().getContent().split(" ");
		
		if(parts.length == 1) {
			e.getChannel().sendMessage("Usage: closepoll <poll#>").queue();
			return;
		}
		
		int i;
		Poll p = pf.getPolls()[i = Integer.parseInt(parts[1])];
		p.setIsopen(false);
		p.refresh(i);
		
		e.getChannel().sendMessage("Poll closed!").queue();
	}

}
