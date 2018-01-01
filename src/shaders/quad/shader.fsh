#version 400 core

in vec2 position;

out vec4 outColor;

uniform vec4 dimensions;
uniform vec4 color;
uniform vec4 outlineColor;

void main(void) {
	vec2 frag = position * vec2(0.5, -0.5) + vec2(0.5, 0.5);
	float x1 = dimensions.x;
	float y1 = dimensions.y;
	float x2 = x1 + dimensions.z;
	float y2 = y1 + dimensions.w;
	if (frag.x > x1 && frag.x < x2 && frag.y > y1 && frag.y < y2) {
		outColor = color;
	} else {
		outColor = outlineColor;
	}
}