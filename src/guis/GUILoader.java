package guis;

import java.io.BufferedReader;
import java.io.IOException;

import org.lwjgl.util.vector.Vector2f;
import org.lwjgl.util.vector.Vector4f;

import texts.FontType;
import texts.Fonts;
import utils.SourceFile;

public class GUILoader {

	private static int lineNumber;
	private static Runnable[] runnables;
	private static GUI gui;
	private static Object obj;

	public static GUI loadGUI(SourceFile gui, Runnable... e) {
		try {
			BufferedReader br = gui.openFileReader();
			String line = null;
			runnables = e;
			lineNumber = 0;

			while (true) {
				line = br.readLine();
				lineNumber++;
				if (line == null)
					break;
				line = line.trim();

				if (line.startsWith("//")) {
					continue;
				}
				if (obj == null) {
					if (line.startsWith("font<")) {
						addFont(line);
					} else if (line.startsWith("gui<")) {
						setGUI(line);
					}
					if (gui == null) {
						continue;
					}
					if (line.startsWith("QuadComponent<")) {
						addQuad(line);
					} else if (line.startsWith("TextComponent<")) {
						addText(line);
					} else if (line.startsWith("TextFieldComponent<")) {
						addTextField(line);
					} else if (line.startsWith("ButtonComponent")) {
						addButton(line);
					}
				} else {
					if (line.contains("}")) {
						obj = null;
						continue;
					}
					if (obj instanceof FontType) {
						FontType font = ((FontType) obj);
						String[] att = getAttributes(line);
						if (line.trim().startsWith("width=")) {
							font.setWidth(floatArg(att, 0));
						} else if (line.trim().startsWith("edges=")) {
							font.setSmoothness(floatArg(att, 0));
						}
					}
					if (obj instanceof GUIComponent) {
						GUIComponent c = ((GUIComponent) obj);
						String[] att = getAttributes(line);
						if (line.startsWith("id=")) 
							c.setID(stringArg(att, 0));
						
					}
					if (obj instanceof QuadComponent) {
						QuadComponent q = ((QuadComponent) obj);
						String[] att = getAttributes(line);
						if (line.startsWith("outlineWidth=")) {
							q.setOutlineWidth(floatArg(att, 0));
						} else if (line.startsWith("backgroundColor=")) {
							q.setBackgroundColor(new Vector4f(floatArg(att, 0), floatArg(att, 1), floatArg(att, 2),
									floatArg(att, 3)));
						} else if (line.startsWith("outlineColor=")) {
							q.setOutlineColor(new Vector4f(floatArg(att, 0), floatArg(att, 1), floatArg(att, 2),
									floatArg(att, 3)));
						}
					}
					if (obj instanceof TextComponent) {
						TextComponent c = ((TextComponent) obj);
						String[] att = getAttributes(line);
						if (line.startsWith("color=")) {
							c.setColor(floatArg(att, 0), floatArg(att, 1), floatArg(att, 2), floatArg(att, 3));
						} else if (line.startsWith("position=")) {
							c.setPosition(floatArg(att, 0), floatArg(att, 1));
						}
					}
					if (obj instanceof TextFieldComponent) {
						TextFieldComponent c = ((TextFieldComponent) obj);
						String[] att = getAttributes(line);
						if (line.startsWith("text=")) {
							c.setText(stringArg(att, 0));
						} else if (line.startsWith("textPosition=")) {
							c.setTextPosition(new Vector2f(floatArg(att, 0), floatArg(att, 1)));
						}
					}
					if (obj instanceof ButtonComponent) {
						ButtonComponent c = ((ButtonComponent) obj);
						String[] att = getAttributes(line);
						if (line.startsWith("text=")) {
							c.setText(stringArg(att, 0), stringArg(att, 1), floatArg(att, 2));
						} else if (line.startsWith("textPosition=")) {
							c.setTextPosition(new Vector2f(floatArg(att, 0), floatArg(att, 1)));
						} else if (line.startsWith("clickDelay=")) {
							c.setClickDelay(longArg(att, 0));
						} else if (line.startsWith("clickEvent=")) {
							c.setClickEvent(runnableArg(att, 0));
						}
					}
				}
			}

			br.close();
			runnables = null;
		} catch (IOException e1) {
			System.err.println("Error reading file: " + gui.getPath());
			e1.printStackTrace();
			return null;
		} catch (IllegalArgumentException e2) {
			System.err.println("Exception at " + gui.getPath() + ":" + lineNumber);
			System.err.println(e2.getMessage());
			return null;
		}

		return GUILoader.gui;
	}

	private static void addButton(String line) {
		String[] args = getArguments(line);
		float x = floatArg(args, 0);
		float y = floatArg(args, 1);
		float width = floatArg(args, 2);
		float height = floatArg(args, 3);
		ButtonComponent c = new ButtonComponent(gui, x, y, width, height);
		if (line.contains("{")) {
			obj = c;
		}
	}

	private static void addTextField(String line) {
		String[] args = getArguments(line);
		float x = floatArg(args, 0);
		float y = floatArg(args, 1);
		float width = floatArg(args, 2);
		float height = floatArg(args, 3);
		TextFieldComponent c = new TextFieldComponent(gui, x, y, width, height, stringArg(args, 4), floatArg(args, 5),
				boolArg(args, 6));
		if (line.contains("{")) {
			obj = c;
		}
	}

	private static void addText(String line) {
		String[] args = getArguments(line);
		TextComponent c = new TextComponent(gui, stringArg(args, 0), stringArg(args, 1), floatArg(args, 2),
				floatArg(args, 3), boolArg(args, 4));
		if (line.contains("{")) {
			obj = c;
		}
	}

	private static void setGUI(String line) {
		String[] args = getArguments(line);
		float x = floatArg(args, 0);
		float y = floatArg(args, 1);
		gui = new GUI();
		gui.setPosition(x, y);
	}

	private static void addQuad(String line) {
		String[] args = getArguments(line);
		float x = floatArg(args, 0);
		float y = floatArg(args, 1);
		float width = floatArg(args, 2);
		float height = floatArg(args, 3);
		QuadComponent c = new QuadComponent(gui, x, y, width, height);
		if (line.contains("{")) {
			obj = c;
		}
	}

	private static void addFont(String line) {
		String[] arguments = getArguments(line);
		if (Fonts.getFont(stringArg(arguments, 0)) != null)
			return;
		Fonts.addFont(stringArg(arguments, 0), new SourceFile(stringArg(arguments, 1)),
				new SourceFile(stringArg(arguments, 2)));
		if (line.contains("{")) {
			obj = Fonts.getFont(stringArg(arguments, 0));
		}
	}

	private static String[] getAttributes(String line) {
		String part = getPart(line, "=", ";");
		String[] attributes = part.split(",");
		for (String a : attributes) {
			a = a.trim();
		}
		return attributes;
	}

	private static void testArg(String[] arguments, int argumentNumber) {
		try {
			if (arguments[argumentNumber].trim().isEmpty())
				throw new IllegalArgumentException("Argument " + argumentNumber + " not found!");
		} catch (ArrayIndexOutOfBoundsException e) {
			throw new IllegalArgumentException("Argument " + argumentNumber + " not found!");
		}
	}

	private static Runnable runnableArg(String[] args, int argNumber) {
		testArg(args, argNumber);
		try {
			int i = Integer.parseInt(args[argNumber].trim().substring(8))-1;
			if (runnables[i] != null)
				return runnables[i];
			else
				throw new Exception();
		} catch (Exception e) {
			e.printStackTrace();
			throw new IllegalArgumentException("Cannot find Runnable for argument " + argNumber);
		}
	}

	private static boolean boolArg(String[] args, int argNumber) {
		testArg(args, argNumber);
		String arg = args[argNumber].trim();
		if (!arg.equals("false") && !arg.equals("true")) {
			throw new IllegalArgumentException("Cannot cast argument " + argNumber + " to boolean: " + args[argNumber]);
		} else {
			return arg.equals("true") ? true : false;
		}
	}

	private static float floatArg(String[] arguments, int argumentNumber) {
		testArg(arguments, argumentNumber);
		float f = 0;
		try {
			f = Float.parseFloat(arguments[argumentNumber]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(
					"Cannot cast argument " + argumentNumber + " to float: " + arguments[argumentNumber]);
		}
		return f;
	}

	private static long longArg(String[] arguments, int argumentNumber) {
		testArg(arguments, argumentNumber);
		long f = 0;
		try {
			f = Long.parseLong(arguments[argumentNumber]);
		} catch (NumberFormatException e) {
			throw new IllegalArgumentException(
					"Cannot cast argument " + argumentNumber + " to long: " + arguments[argumentNumber]);
		}
		return f;
	}

	private static String stringArg(String[] arguments, int argumentNumber) {
		testArg(arguments, argumentNumber);
		boolean error = false;
		String argument = arguments[argumentNumber];
		if (!argument.contains("\"")) {
			error = true;
		} else if (argument.indexOf("\"") == argument.lastIndexOf("\"")) {
			error = true;
		}

		if (error) {
			throw new IllegalArgumentException("Cannot cast argument " + argumentNumber + " to string: " + argument);
		}
		String arg = arguments[argumentNumber].trim();
		return arg.substring(1, arg.length() - 1);
	}

	private static String[] getArguments(String line) {
		String part = getPart(line, "<", ">");
		String[] arguments = part.split(",");
		for (String a : arguments) {
			a = a.trim();
		}
		return arguments;
	}

	private static String getPart(String string, String start, String end) {
		int startPosition = string.indexOf(start);
		if (startPosition == -1)
			throw new IllegalArgumentException(start + " was expected but not found!");

		startPosition = startPosition + start.length();
		int endPosition = string.substring(startPosition).indexOf(end);
		if (endPosition == -1)
			throw new IllegalArgumentException(end + " was expected but not found!");

		return string.substring(startPosition, startPosition + endPosition);
	}

}
