#version 400 core

in vec2 textureCoords;

out vec4 out_Color;

uniform sampler2D sampler;

void main(void) {
	vec2 tex = clamp(textureCoords, 0.002f, 0.998f);
	out_Color = texture(sampler, tex);
}