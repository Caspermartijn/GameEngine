package debug;

public class Command {

	private String commandLine;
	private Runnable commandHandler;

	public Command(String commandLine) {
		this.commandLine = commandLine;
	}

	public void setCommandHandler(Runnable runnable) {
		this.commandHandler = runnable;
	}

	public boolean runCommand(String checkCommand) {
		boolean run = false;
		String s = checkCommand.replaceAll("/", "");
		if (s == commandLine) {
			commandHandler.run();
			run = true;
		}
		return run;
	}
}
