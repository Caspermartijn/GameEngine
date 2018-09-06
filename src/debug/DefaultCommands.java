package debug;

import org.lwjgl.util.vector.Vector3f;

import engine.Display;
import hitbox.HitBoxMaster;
import log.Log;

public class DefaultCommands {

	public static void compile() {

		Command showHitBoxes = new Command("showhitboxes");
		showHitBoxes.setCommandHandler(new CommandRunnable() {

			@Override
			public void run(String commandLine, String[] args) {
				if (args.length < 0) {
					Log.append("Command needs args true or false", false);
				} else {
					try {
						switch (args[0]) {
						case "true":
							HitBoxMaster.renderHitBoxes = true;
							break;
						case "false":
							HitBoxMaster.renderHitBoxes = false;
							break;
						default:
							Log.append("Command needs args true or false", false);
							break;
						}
					} catch (Exception e) {
						Log.append("Command needs args true or false", false);
					}
				}
			}
		});

		Command shutdown = new Command("shutdown");
		shutdown.setCommandHandler(new CommandRunnable() {

			@Override
			public void run(String commandLine, String[] args) {

				Log.append("Shutting down", true);
				try {
					Thread.sleep(1000);
					Display.closeDisplay();
				} catch (InterruptedException e) {
					Log.append("Couldn't close display please try again", true);
				}

			}

		});

		Command commands = new Command("commands");
		commands.setCommandHandler(new CommandRunnable() {

			@Override
			public void run(String commandLine, String[] args) {
				Log.append("=========Commands========", false, new Vector3f(1, 0.2f, 0.2f));
				for (Command c : CommandHandler.getCommands()) {
					Log.append("/" + c.getCommand(), false, new Vector3f(1, 0.2f, 0.2f));
				}
				Log.append("=========Commands========", false, new Vector3f(1, 0.2f, 0.2f));
			}
		});

	}

}
