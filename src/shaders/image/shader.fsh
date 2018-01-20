#version 400 core

in vec2 textureCoords;

out vec4 out_Color;

uniform sampler2D sampler;
uniform vec3 color_override;

void main(void) {
	vec2 tex = clamp(textureCoords, 0.002f, 0.998f);
	out_Color = texture(sampler, tex);
	
	float alpha = out_Color.w;
	
	if(color_override.x>0){
		out_Color = mix( out_Color, vec4(color_override.xyz,alpha) , 1);
	}
}