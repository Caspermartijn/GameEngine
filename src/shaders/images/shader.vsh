#version 400 core

in vec2 in_Position;

out vec2 textureCoords;

uniform mat4 matrix;

void main(void) {
	gl_Position = matrix * vec4(in_Position, 0.0f, 1.0f);
	textureCoords = in_Position * vec2(0.5f, -0.5f) + vec2(0.5f, -0.5f);
}