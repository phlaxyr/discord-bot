package discordbot;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Type;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonIOException;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonWriter;

public class PollFactory {
	private Poll[] polls = {};
	private transient Gson gson = new GsonBuilder().setPrettyPrinting().create();
	private static final Type jsontype = new TypeToken<PollFactory>(){}.getType();
	
	public PollFactory() {
		
	}
	
	public void save(File pollsfile) throws JsonIOException, IOException {
		JsonWriter jw = new JsonWriter(new FileWriter(pollsfile));
		gson.toJson(this, jsontype, jw);
		jw.flush();
		jw.close();
	}
	
	public void load(File pollsfile) throws JsonSyntaxException, JsonIOException, IOException {
		BufferedReader jr = new BufferedReader(new FileReader(pollsfile));
		
		PollFactory pf = gson.fromJson(jr, PollFactory.class);
		
		this.polls = pf.polls;
		
		jr.close();
	}
	
	public void addPoll(Poll p) {
		Poll[] newpolls = new Poll[polls.length + 1];
		System.arraycopy(polls, 0, newpolls, 0, polls.length);
		newpolls[polls.length] = p;
		polls = newpolls;
		p.refresh(polls.length - 1);
	}
	
	public void addVoter(String voterid, int pollnum, int optionnum) {
		polls[pollnum].addVoter(pollnum, voterid, optionnum);;
	}
	
	// POJO stuff

	public Poll[] getPolls() {
		return polls;
	}

	public void setPolls(Poll[] polls) {
		this.polls = polls;
	}
}
