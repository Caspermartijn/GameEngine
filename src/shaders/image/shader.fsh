#version 400 core

in vec2 textureCoords;

out vec4 out_Color;

uniform sampler2D sampler;
uniform vec3 color_override;
uniform vec3[] color_override_picker;

void main(void) {
	vec2 tex = clamp(textureCoords, 0.002f, 0.998f);
	out_Color = texture(sampler, tex);
	
	float alpha = out_Color.w;
	
	if(color_override.x>0){
		if(out_Color.x > color_override_picker[0].x && out_Color.z > color_override_picker[0].z && out_Color.y > color_override_picker[0].y){
			if(out_Color.x < color_override_picker[1].x && out_Color.z < color_override_picker[1].z && out_Color.y < color_override_picker[1].y){
				out_Color = mix( out_Color, vec4(color_override.xyz,alpha) , 1);
			}
		}
	}
}