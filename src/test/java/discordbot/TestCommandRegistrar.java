package discordbot;

import net.dv8tion.jda.core.entities.impl.MessageImpl;
import net.dv8tion.jda.core.events.message.MessageReceivedEvent;

import org.junit.Test;

import static org.junit.Assert.*;

public class TestCommandRegistrar {
	private boolean ran = false;
	
	@Test
	public void testRegistrar() {
		BotCommand dummycommand = new BotCommand() {

			@Override
			public String getCommand() {
				return "test";
			}

			@Override
			public void run(MessageReceivedEvent e) {
				inj();
			}
			
		};
		
		CommandRegistrar cmdr = new CommandRegistrar("!");
		cmdr.registerCommand(dummycommand);
		
		// Dummy JDA event
		cmdr.onMessageReceived(new MessageReceivedEvent(null, 0, 
				new MessageImpl("", null, false).setContent("!test")));
		
		assertTrue(ran);
	}
	
	private void inj() {
		ran = true;
	}
}
