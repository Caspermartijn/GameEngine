#version 400 core

in vec2 textureCoords;

out vec4 out_Color;

uniform sampler2D sampler;

void main(void) {
	out_Color = texture(sampler, textureCoords);
}