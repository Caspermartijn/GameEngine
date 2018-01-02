#version 400 core

in vec2 inPosition;

out vec2 position;

uniform mat4 transform;

void main(void) {
	gl_Position = transform * vec4(inPosition, 0.0, 1.0);
	position = gl_Position.xy;
}