package debug;

import org.lwjgl.util.vector.Vector3f;

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
				}
			}
		});

		Command commands = new Command("commands");
		commands.setCommandHandler(new CommandRunnable() {

			@Override
			public void run(String commandLine, String[] args) {
				Log.append("=========Commands========", false, new Vector3f(1, 0.2f, 0.2f));
					for(Command c  :CommandHandler.getCommands()) {
						Log.append("/" + c.getCommand(), false, new Vector3f(1, 0.2f, 0.2f));
					}
				Log.append("=========Commands========", false, new Vector3f(1, 0.2f, 0.2f));
			}
		});

	}

}
