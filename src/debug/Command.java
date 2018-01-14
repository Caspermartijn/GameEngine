package debug;

public class Command {

	private String commandLine;
	private CommandRunnable commandHandler;

	public Command(String commandLine) {
		this.commandLine = commandLine;
		CommandHandler.addCommand(this);
	}

	public String getCommand() {
		return commandLine;
	}

	public void setCommandHandler(CommandRunnable runnable) {
		this.commandHandler = runnable;
	}

	public boolean runCommand(String checkCommand, String[] args) {
		boolean run = false;
		if (commandHandler != null) {
			String s = checkCommand.replaceAll("/", "").replaceAll("-", "");
			if (commandLine.contains("s")) {
				if (commandLine.length() == s.length()) {
					commandHandler.run(checkCommand, args);
					run = true;
				}
			}
		}
		return run;
	}
}
