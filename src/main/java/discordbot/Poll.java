package discordbot;

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
	
	public Poll() {
		
	}
	
	public Poll(String name, String updatemsgid, String[] choicenames) {
		this.name = name;
		this.updmsgid = updatemsgid;
		
		// Populate pollchoices
		pollchoices = new PollChoice[choicenames.length];
		for (int i = 0; i < choicenames.length; i++) {
			String cname = choicenames[i];
			pollchoices[i] = new PollChoice(cname);
		}
		
	}
	
	public void addVoter(String playerid, int optionnum) {
		if(optionnum > pollchoices.length || optionnum <= 0)
			throw new ArrayIndexOutOfBoundsException("Invalid option number");
		
		// Reset votes for that person so it doesn't duplicate
		removeAllVotesFor(playerid);
		
		// Shift array indices down to zero-based
		optionnum--;
		
		pollchoices[optionnum].addVoter(playerid);
	}
	
	public void removeAllVotesFor(String playerid) {
		for(PollChoice p : pollchoices) {
			p.removeVoter(playerid);
		}
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
