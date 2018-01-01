#version 400 core

in vec2 passTextureCoords;

out vec4 outColor;

uniform vec4 color;
uniform sampler2D sampler;
uniform vec2 fontSettings;

void main(void) {

	float width = fontSettings.x;
	float edge = fontSettings.y;
	
	float distance = 1.0 - texture(sampler, passTextureCoords).a;
	float alpha = 1-smoothstep(width, width+edge, distance);
	alpha = alpha - (color.a - 1.0);

	outColor = vec4(color.rgb, alpha);

}