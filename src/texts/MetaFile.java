package texts;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import engine.EngineDisplay;
import utils.SourceFile;

public class MetaFile {

	private static final int PAD_TOP = 0;
	private static final int PAD_LEFT = 1;
	private static final int PAD_BOTTOM = 2;
	private static final int PAD_RIGHT = 3;

	private static final int DESIRED_PADDING = 8;

	private double spaceWidth;
	private Map<Integer, Character> metaData = new HashMap<Integer, Character>();

	protected MetaFile(SourceFile file) {
		readData(file);
	}

	protected double getSpaceWidth() {
		return spaceWidth;
	}

	protected Character getCharacter(int ascii) {
		return metaData.get(ascii);
	}

	private void readData(SourceFile file) {
		BufferedReader br;
		int[] padding = new int[4];
		int paddingWidth = 0;
		int paddingHeight = 0;
		int imageSize = 0;
		double horizontalPerPixelSize = 0.0;
		double verticalPerPixelSize = 0.0;
		double aspectRatio = (double) EngineDisplay.getWidth() / (double) EngineDisplay.getHeight();
		try {
			br = file.openFileReader();

			String line = br.readLine();
			while (line != null) {
				if (line.startsWith("info ")) {
					String paddingString = getValues(line).get("padding");
					String[] paddingValues = paddingString.split(",");
					for (int i = 0; i < paddingValues.length; i++) {
						padding[i] = Integer.valueOf(paddingValues[i]);
					}
					paddingWidth = padding[PAD_LEFT] + padding[PAD_RIGHT];
					paddingHeight = padding[PAD_TOP] + padding[PAD_BOTTOM];
				} else if (line.startsWith("common ")) {
					HashMap<String, String> values = getValues(line);
					imageSize = Integer.valueOf(values.get("scaleW"));
					int lineHeightPixels = Integer.parseInt(values.get("lineHeight")) - paddingHeight;
					verticalPerPixelSize = TextMeshCreator.LINE_HEIGHT / (double) lineHeightPixels;
					horizontalPerPixelSize = verticalPerPixelSize / aspectRatio;
				} else if (line.startsWith("char ")) {
					HashMap<String, String> values = getValues(line);
					int id = Integer.valueOf(values.get("id"));
					if (id == TextMeshCreator.SPACE_ASCII) {
						this.spaceWidth = (Integer.valueOf(values.get("xadvance")) - paddingWidth)
								* horizontalPerPixelSize;
						line = br.readLine();
						continue;
					}
					double xTex = ((double) Integer.parseInt(values.get("x")) + (padding[PAD_LEFT] - DESIRED_PADDING)) / imageSize;
					double yTex = ((double) Integer.parseInt(values.get("y")) + (padding[PAD_TOP] - DESIRED_PADDING)) / imageSize;
					int width = Integer.parseInt(values.get("width")) - (paddingWidth - (2 * DESIRED_PADDING));
					int height = Integer.parseInt(values.get("height")) - ((paddingHeight) - (2 * DESIRED_PADDING));
					double quadWidth = width * horizontalPerPixelSize;
					double quadHeight = height * verticalPerPixelSize;
					double xTexSize = (double) width / imageSize;
					double yTexSize = (double) height / imageSize;
					double xOff = (Integer.parseInt(values.get("xoffset")) + padding[PAD_LEFT] - DESIRED_PADDING)
							* horizontalPerPixelSize;
					double yOff = (Integer.parseInt(values.get("yoffset")) + (padding[PAD_TOP] - DESIRED_PADDING)) * verticalPerPixelSize;
					double xAdvance = (Integer.parseInt(values.get("xadvance")) - paddingWidth) * horizontalPerPixelSize;
					Character c = new Character(id, xTex, yTex, xTexSize, yTexSize, xOff, yOff, quadWidth, quadHeight, xAdvance);
					metaData.put(c.getId(), c);
				} else if (line.startsWith("kernings")) {
					break;
				}

				line = br.readLine();
			}

			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private HashMap<String, String> getValues(String line) {
		HashMap<String, String> values = new HashMap<>();
		String[] valuesArray = line.split(" ");
		for (String value : valuesArray) {
			if (!value.contains("="))
				continue;
			String[] v = value.split("=");
			values.put(v[0], v[1]);
		}
		return values;
	}

	// private boolean processNextLine() {
	// values.clear();
	// String line = null;
	// try {
	// line = reader.readLine();
	// } catch (IOException e1) {
	// }
	// if (line == null) {
	// return false;
	// }
	// for (String part : line.split(SPLITTER)) {
	// String[] valuePairs = part.split("=");
	// if (valuePairs.length == 2) {
	// values.put(valuePairs[0], valuePairs[1]);
	// }
	// }
	// return true;
	// }
	//
	// private int getValueOfVariable(String variable) {
	// return Integer.parseInt(values.get(variable));
	// }
	//
	// private int[] getValuesOfVariable(String variable) {
	// String[] numbers = values.get(variable).split(NUMBER_SEPARATOR);
	// int[] actualValues = new int[numbers.length];
	// for (int i = 0; i < actualValues.length; i++) {
	// actualValues[i] = Integer.parseInt(numbers[i]);
	// }
	// return actualValues;
	// }

	// private void loadPaddingData() {
	// processNextLine();
	// this.padding = getValuesOfVariable("padding");
	// this.paddingWidth = padding[PAD_LEFT] + padding[PAD_RIGHT];
	// this.paddingHeight = padding[PAD_TOP] + padding[PAD_BOTTOM];
	// }
	//
	// private void loadLineSizes() {
	// processNextLine();
	// int lineHeightPixels = getValueOfVariable("lineHeight") - paddingHeight;
	// verticalPerPixelSize = TextMeshCreator.LINE_HEIGHT / (double)
	// lineHeightPixels;
	// horizontalPerPixelSize = verticalPerPixelSize / aspectRatio;
	// }
	//
	// private void loadCharacterData(int imageWidth) {
	// processNextLine();
	// processNextLine();
	// while (processNextLine()) {
	// Character c = loadCharacter(imageWidth);
	// if (c != null) {
	// metaData.put(c.getId(), c);
	// }
	// }
	// }
	//
	// private Character loadCharacter(int imageSize) {
	// int id = getValueOfVariable("id");
	// if (id == TextMeshCreator.SPACE_ASCII) {
	// this.spaceWidth = (getValueOfVariable("xadvance") - paddingWidth) *
	// horizontalPerPixelSize;
	// return null;
	// }
	// double xTex = ((double) getValueOfVariable("x") + (padding[PAD_LEFT] -
	// DESIRED_PADDING)) / imageSize;
	// double yTex = ((double) getValueOfVariable("y") + (padding[PAD_TOP] -
	// DESIRED_PADDING)) / imageSize;
	// int width = getValueOfVariable("width") - (paddingWidth - (2 *
	// DESIRED_PADDING));
	// int height = getValueOfVariable("height") - ((paddingHeight) - (2 *
	// DESIRED_PADDING));
	// double quadWidth = width * horizontalPerPixelSize;
	// double quadHeight = height * verticalPerPixelSize;
	// double xTexSize = (double) width / imageSize;
	// double yTexSize = (double) height / imageSize;
	// double xOff = (getValueOfVariable("xoffset") + padding[PAD_LEFT] -
	// DESIRED_PADDING) * horizontalPerPixelSize;
	// double yOff = (getValueOfVariable("yoffset") + (padding[PAD_TOP] -
	// DESIRED_PADDING)) * verticalPerPixelSize;
	// double xAdvance = (getValueOfVariable("xadvance") - paddingWidth) *
	// horizontalPerPixelSize;
	// return new Character(id, xTex, yTex, xTexSize, yTexSize, xOff, yOff,
	// quadWidth, quadHeight, xAdvance);
	// }
}
