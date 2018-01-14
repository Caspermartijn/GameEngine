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
		String[] ss = input.split("\\s+");
		String commandLine = ss[0];
		String[] args = new String[ss.length - 1];
		if (true) {
			int i = 0;
			for (String s : ss) {
				if(s != commandLine) {
					args[i] = s;
					i++;
				}
			}

			for (Command c : commands) {
				boolean b = c.runCommand(commandLine, args);
				if (b) {
					ran = true;
				}
			}
		}
		return ran;
	}

	public static List<Command> getCommands() {
		return commands;
	}

}
