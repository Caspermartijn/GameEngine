package shaders;

public class Shader {

	public static String[] getFragmentFileSkybox() {
		String[] returnSS = new String[] { "#version 400 core",

				"in vec3 textureCoords;", "out vec4 out_Color;", "uniform samplerCube cubeMap;",

				"void main(void) {", "out_Color = texture(cubeMap, textureCoords);", "}" };

		return returnSS;
	}

	public static String[] vertexShaderQuad() {
		String[] returnSS = new String[] { "#version 400 core", "in vec2 inPosition;", "out vec2 position;",
				"uniform mat4 transform;", "void main(void) {", "gl_Position = transform * vec4(inPosition, 0.0, 1.0);",
				"position = gl_Position.xy;", "}" };
		return returnSS;
	}

	public static String[] getVertexFileLine() {
		String[] returnSS = new String[] { "#version 150", "in vec4 in_position;", "uniform mat4 viewMatrix;",
				"uniform mat4 transformationMatrix;", "uniform mat4 projectionViewMatrix;", "void main() {",
				"vec4 worldPosition = transformationMatrix * in_position;",
				"gl_Position = projectionViewMatrix * viewMatrix * worldPosition;", "}" };
		return returnSS;
	}

	public static String[] getFragmentFileLine() {
		String[] returnSS = new String[] { "#version 150", "out vec4 out_Color;", "uniform vec4 color;",
				"void main() {", "out_Color = color;", "}" };
		return returnSS;
	}

	public static String[] getVertexFileFont() {
		String[] returnSS = new String[] { "#version 400 core", "in vec2 inPosition;", "in vec2 inTextureCoords;",
				"out vec2 passTextureCoords;", "uniform vec2 translation;", "void main(void) {",
				"	gl_Position = vec4(inPosition + translation * vec2(2.0, -2.0), 0.0, 1.0);",
				"	passTextureCoords = inTextureCoords;", "}" };
		return returnSS;
	}

	public static String[] getFragmentFileHud() {
		String[] returnSS = new String[] { "#version 140", "in vec2 textureCoords;", "out vec4 out_Color;",
				"uniform sampler2D guiTexture;", "void main(void){",
				"out_Color = texture(guiTexture,vec2(textureCoords.x, textureCoords.y));", "}" };
		return returnSS;
	}

	public static String[] getVertexFileHud() {
		String[] returnSS = new String[] { "#version 140", "in vec2 textureCoords;", "out vec4 out_Color;",
				"uniform sampler2D guiTexture;", "void main(void){",
				"out_Color = texture(guiTexture,vec2(textureCoords.x, textureCoords.y));", "}" };
		return returnSS;
	}

	public static String[] vertexShaderHud() {
		String[] returnSS = new String[] { "#version 140",

				"in vec2 position;",

				"out vec2 textureCoords;",

				"uniform mat4 transformationMatrix;",

				"void main(void){",
				
				"gl_Position = transformationMatrix * vec4(position, 0.0, 1.0);",
				"textureCoords = vec2((position.x+1.0)/2.0, 1 - (position.y+1.0)/2.0);", 
				
				"}" 
				
		};
		return returnSS;
	}
}
