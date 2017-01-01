package discordbot;

import java.io.File;
import java.io.IOException;

import com.google.gson.JsonIOException;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class VoteCommand implements BotCommand {

	PollFactory pf;
	
	public VoteCommand(PollFactory pf) {
		this.pf = pf;
	}
	
	@Override
	public String getCommand() {
		return "vote";
	}

	@Override
	public void run(MessageReceivedEvent e) {
		String userid = e.getAuthor().getId();
		
		String[] parts = e.getMessage().getContent().split(" ");
		
		if(parts.length == 1) {
			e.getChannel().sendMessage("Usage: !vote <poll#> <option#>").queue();
			return;
		}
		
		int pollnum = Integer.parseInt(parts[1]);		
		int optnum = Integer.parseInt(parts[2]);
		
		pf.addVoter(userid, pollnum, optnum);
		try {
			pf.save(new File("polls.json"));
			e.getChannel().sendMessage("Vote successfully counted!").queue();
		} catch (JsonIOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		} catch (IOException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
	}

}
