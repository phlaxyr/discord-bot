package discordbot;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.util.Arrays;



import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;

import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

public class MakepollCommand implements BotCommand {

	PollFactory pf = new PollFactory();
	
	public MakepollCommand() {
		if(new File("polls.json").exists())
			try {
				pf.load(new File("polls.json"));
			} catch (JsonSyntaxException | JsonIOException | IOException e) {
				e.printStackTrace();
			}
	}
	
	@Override
	public String getCommand() {
		return "makepoll";
	}

	@Override
	public void run(MessageReceivedEvent e) {
		if(!e.getChannel().getId().equals(BotMain.cfg.adminChannelID()))
			return;
		
		String[] lines = e.getMessage().getContent().split("\n");
		String pollname = lines[0].split(" ", 2) [1];
		String[] options = Arrays.copyOfRange(lines, 1, lines.length);
		
		// Make message in poll channel
		e.getJDA().getTextChannelById(BotMain.cfg.pollsChannelID()).sendMessage("placeholder").queue((m) -> {
			pf.addPoll(new Poll(BotMain.jda, pollname, m.getId(), options));
			
			try {
				pf.save(new File("polls.json"));
				e.getChannel().sendMessage("Poll made successfully!").queue();
			} catch(IOException ex) {
				ex.printStackTrace();
			}
		}, (ex) -> {
			// Error handling
			StringWriter sw = new StringWriter();
			PrintWriter pw = new PrintWriter(sw);
			ex.printStackTrace(pw);
			e.getChannel().sendMessage("Something went wrong making the poll: \n```" + sw.toString() + "```").queue();
			pw.close();
		});
	}

}
