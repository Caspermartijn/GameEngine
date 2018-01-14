package debug;

import java.util.ArrayList;
import java.util.List;

public class CommandHandler {

	private static List<Command> commands = new ArrayList<Command>();

	public static void addCommand(Command c) {
		commands.add(c);
	}

	public static boolean runCommand(String input) {
		boolean ran = false;
		if (true) {
			for (Command c : commands) {
				boolean b = c.runCommand(input);
				if(b) {
					ran = true;
				}
			}
		}
		return ran;
	}

}
