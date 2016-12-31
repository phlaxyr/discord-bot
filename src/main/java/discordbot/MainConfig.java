package discordbot;

import org.aeonbits.owner.Config;
import org.aeonbits.owner.Config.*;

@LoadPolicy(LoadType.MERGE)
@Sources({"file:bot.properties", "classpath:default.properties"})
public interface MainConfig extends Config {
	String botToken();
	
	@DefaultValue("!")
	String prefix();
}