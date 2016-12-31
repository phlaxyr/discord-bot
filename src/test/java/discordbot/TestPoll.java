package discordbot;

import java.io.File;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.TemporaryFolder;

import static org.junit.Assert.*;

public class TestPoll {
	@Rule
	public TemporaryFolder tf = new TemporaryFolder();
	
	@Test
	public void testFactorySaveLoad() throws Exception {
		
		File pollsfile = tf.newFile("polls-tfsl.json");
		
		// Write
		PollFactory pf = new PollFactory();
		pf.addPoll(new Poll("poll a", "<msgid>", new String[] {"option a", "option b", "option c"}));
		pf.addVoter("voter", 0, 2);
		pf.save(pollsfile);
		
		// Read
		PollFactory newpf = new PollFactory();
		newpf.load(pollsfile);
		assertTrue(newpf.getPolls()[0].getPollchoices()[1].alreadyVoted("voter"));
	}
	
	@Test
	public void testPollAdd() {
		Poll p = new Poll("", "", new String[]{"option a", "option b", "option c"});
		
		// add to choice a (one-based indexing)
		p.addVoter("test", 1);
		
		assertTrue(p.getPollchoices()[0].alreadyVoted("test"));
		assertFalse(p.getPollchoices()[1].alreadyVoted("test"));

		// add to choce b (one-based indexing)
		p.addVoter("test", 2);
		
		assertFalse(p.getPollchoices()[0].alreadyVoted("test"));
		assertTrue(p.getPollchoices()[1].alreadyVoted("test"));

		// add to choce b (one-based indexing)
		// duplicate checking
		p.addVoter("test", 2);
		
		assertFalse(p.getPollchoices()[0].alreadyVoted("test"));
		assertTrue(p.getPollchoices()[1].alreadyVoted("test"));
		assertEquals(1, p.getPollchoices()[1].getVoters().length);
		
	}
	
	@Test
	public void testAddVoter() {
		// Make sure the binary search works
		
		String[] names = {"test 1", "test 2", "test 3", "test 4", "hello",
				"abcde", "31415"};
		
		PollChoice pc = new PollChoice();
		
		for(String s : names)
			pc.addVoter(s);
		
		Arrays.sort(names);
		
		assertArrayEquals(names, pc.getVoters());

		assertTrue(pc.alreadyVoted("test 1"));
		assertTrue(pc.alreadyVoted("test 2"));
		assertTrue(pc.alreadyVoted("hello"));
		assertFalse(pc.alreadyVoted("hello "));
		assertFalse(pc.alreadyVoted("test 5"));
	}
	
	@Test
	public void testRemoveVoter() {
		// Make sure remove works
		
		String[] names = {"test 1", "test 2", "test 3", "test 4", "hello",
				"abcde", "31415"};
		
		PollChoice pc = new PollChoice();
		
		for(String s : names)
			pc.addVoter(s);
		
		assertTrue(pc.alreadyVoted("test 1"));
		assertTrue(pc.alreadyVoted("test 2"));
		assertTrue(pc.alreadyVoted("hello"));
		assertFalse(pc.alreadyVoted("hello "));
		assertFalse(pc.alreadyVoted("test 5"));
		
		pc.removeVoter("test 1");
		
		assertFalse(pc.alreadyVoted("test 1"));
		assertTrue(pc.alreadyVoted("test 2"));
		assertTrue(pc.alreadyVoted("hello"));
		assertFalse(pc.alreadyVoted("hello "));
		assertFalse(pc.alreadyVoted("test 5"));
	}
}
