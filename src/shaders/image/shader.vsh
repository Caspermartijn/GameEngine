#version 400 core

in vec2 in_Position;

out vec2 textureCoords;

uniform mat4 matrix;

void main(void) {
	gl_Position = matrix * vec4(in_Position, 0.0f, 1.0f);
	textureCoords = vec2((in_Position.x+1.0)/2.0, 1 - (in_Position.y+1.0)/2.0);
}