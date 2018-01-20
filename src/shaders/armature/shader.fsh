#version 150

in vec2 pass_textureCoords;
in vec3 pass_normal;

out vec4 out_Color;

uniform sampler2D diffuseMap;

uniform vec3 lightDirection;

const vec2 lightBias = vec2(0.7, 0.6);

void main(void){
	
	vec4 diffuseColour = texture(diffuseMap, pass_textureCoords);		
	vec3 unitNormal = normalize(pass_normal);
	float diffuseLight = max(dot(-lightDirection, unitNormal), 0.0) * lightBias.x + lightBias.y;
	out_Color = diffuseColour * diffuseLight;
}