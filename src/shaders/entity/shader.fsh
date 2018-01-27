#version 400 core

in vec2 pass_textureCoordinates;
in vec3 surfaceNormal;
in vec3 toLightVector;
in vec3 toCameraVector;

out vec4 out_Color;

uniform sampler2D modelTexture;
uniform sampler2D colorMap;

uniform vec3 lightColour;
uniform vec3 colorMapOffsetColor;
uniform float shineDamper;
uniform float reflectivity;

void main(void){

	vec3 unitNormal = normalize(surfaceNormal);
	vec3 unitLightVector = normalize(toLightVector);
	
	float nDotl = dot(unitNormal,unitLightVector);
	float brightness = max(nDotl,0.2);
	vec3 diffuse = brightness * lightColour;
	
	vec3 unitVectorToCamera = normalize(toCameraVector);
	vec3 lightDirection = -unitLightVector;
	vec3 reflectedLightDirection = reflect(lightDirection,unitNormal);
	
	float specularFactor = dot(reflectedLightDirection , unitVectorToCamera);
	specularFactor = max(specularFactor,0.0);
	float dampedFactor = pow(specularFactor,shineDamper);
	vec3 finalSpecular = dampedFactor * reflectivity * lightColour;
	
	vec4 vertexColorPre = texture(modelTexture, pass_textureCoordinates);
	
	vec4 colorMapColor = texture(colorMap, pass_textureCoordinates);
	
	if(colorMapColor.x > 0.5 && colorMapColor.y > 0.5 && colorMapColor.z > 0.5 ){
		vertexColorPre = vec4(colorMapOffsetColor.xyz, 0.7);
	}
	
	out_Color =  vec4(diffuse,1.0) * vertexColorPre + vec4(finalSpecular,1.0);

}