package discordbot;

import java.util.Arrays;

/**
 * A Bean for GSON. Nested under Poll.
 * 
 * @author minerguy31
 *
 */
public class PollChoice {
	private String name;
	private String[] voters = new String[] {};
	
	public PollChoice() {
		
	}
	
	public PollChoice(String name) {
		this.name = name;
	}
	
	public boolean alreadyVoted(String voterid) {
		return Arrays.binarySearch(voters, voterid) >= 0;
	}
	
	public void removeVoter(String voterid) {
		int i = Arrays.binarySearch(voters, voterid);
		if(i < 0)
			return;
		
		String[] newvoters = new String[voters.length - 1];

		System.arraycopy(voters, 0, newvoters, 0, i);
		System.arraycopy(voters, i + 1, newvoters, i, voters.length - i - 1);
		
		voters = newvoters;
	}
	
	public void addVoter(String voterid) {
		int voterpos = -Arrays.binarySearch(voters, voterid) - 1;
		
		if(voterpos < 0)
			return;
		
		String[] newvoters = new String[voters.length + 1];
		System.arraycopy(voters, 0, newvoters, 0, voterpos);
		newvoters[voterpos] = voterid;
		System.arraycopy(voters, voterpos, newvoters, voterpos + 1, voters.length - voterpos);
		
		voters = newvoters;
		
	}
	
	public int getNumVoters() {
		return voters.length;
	}
	
	public String[] getVoters() {
		return voters;
	}
	
	public void setVoters(String[] voters) {
		this.voters = voters;
	}
	
	public String getName() {
		return name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String toString(int optionnum, int totalvotes) {
		final int width = 20;
		StringBuilder sb = new StringBuilder(optionnum + ". [");
		double percent = totalvotes != 0 ? ((double)getNumVoters() / (double)totalvotes) : 0;
		int num = (int) Math.round(percent * width);
		for(int i = 0; i < num; i++)
			sb.append("#");
		for(int i = num; i < width; i++)
			sb.append(" ");
		
		sb.append("] (" + getNumVoters() + " votes) - " + name);
		
		return sb.toString();
	}
}
