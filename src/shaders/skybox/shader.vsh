#version 400 core

in vec3 position;
out vec3 textureCoords;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform float scale;

void main(void) {

	//gl_Position = projectionMatrix * viewMatrix * vec4(scale * position, 1.0);
	gl_Position = projectionMatrix * vec4(scale * position, 1.0);
	textureCoords = position;

}