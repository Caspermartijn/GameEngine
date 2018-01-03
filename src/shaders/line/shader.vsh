#version 150

in vec4 in_position;

uniform mat4 viewMatrix;
uniform mat4 transformationMatrix;
uniform mat4 projectionViewMatrix;

void main() {
	vec4 worldPosition = transformationMatrix * in_position;
	gl_Position = projectionViewMatrix * viewMatrix * worldPosition;
}
