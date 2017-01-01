package discordbot;

import net.dv8tion.jda.core.JDA;

/**
 * A Bean for GSON
 * 
 * @author minerguy31
 *
 */
public class Poll {
	private String name;
	private boolean isopen = true;
	private PollChoice[] pollchoices;
	private String updmsgid;
	
	private transient JDA jda;
	
	public Poll() {
		jda = BotMain.jda;
	}
	
	public Poll(JDA jda, String name, String updatemsgid, String[] choicenames) {
		this.name = name;
		this.updmsgid = updatemsgid;
		this.jda = jda;
		
		// Populate pollchoices
		pollchoices = new PollChoice[choicenames.length];
		for (int i = 0; i < choicenames.length; i++) {
			String cname = choicenames[i];
			pollchoices[i] = new PollChoice(cname);
		}
	}
	
	public void addVoter(int pollnum, String playerid, int optionnum) {
		if(optionnum > pollchoices.length || optionnum <= 0)
			throw new ArrayIndexOutOfBoundsException("Invalid option number");
		
		// Reset votes for that person so it doesn't duplicate
		removeAllVotesFor(playerid);
		
		// Shift array indices down to zero-based
		optionnum--;
		
		pollchoices[optionnum].addVoter(playerid);

		refresh(pollnum);
	}
	
	public void refresh(int pollnum) {
		// Probably in test
		if(jda == null)
			return;
		
		jda.getTextChannelById(BotMain.cfg.pollsChannelID()).editMessageById(updmsgid, toString(pollnum)).queue();
		
	}
	
	public void removeAllVotesFor(String playerid) {
		for(PollChoice p : pollchoices) {
			p.removeVoter(playerid);
		}
	}
	
	public int getNumVotes() {
		int total = 0;
		
		for(PollChoice choice : pollchoices) {
			total += choice.getNumVoters();
		}
		
		return total;
	}
	
	@CoverageIgnore
	public String toString(int pollnum) {
		int total = getNumVotes();
		
		StringBuilder sb = new StringBuilder("Poll #" + pollnum + " - **" + name + "** (`" + (isopen ? "OPEN" : "CLOSED") + "`)\n```\n");
		
		int n = 0;
		for(PollChoice choice : pollchoices) {
			n++;
			sb.append(choice.toString(n, total));
			sb.append('\n');
		}
		
		sb.append("```");
		
		return sb.toString();
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public boolean isIsopen() {
		return isopen;
	}
	
	public void setIsopen(boolean isopen) {
		this.isopen = isopen;
	}
	
	public PollChoice[] getPollchoices() {
		return pollchoices;
	}
	
	public void setPollchoices(PollChoice[] pollchoices) {
		this.pollchoices = pollchoices;
	}

	public String getUpdmsgid() {
		return updmsgid;
	}

	public void setUpdmsgid(String updmsgid) {
		this.updmsgid = updmsgid;
	}
}
