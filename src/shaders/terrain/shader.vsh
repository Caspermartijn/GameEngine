#version 150

in vec3 position;
in vec2 textureCoordinates;
in vec3 normal;

out vec2 pass_textureCoordinates;
out vec3 surfaceNormal;
out vec3 toLightVector[4];
out vec3 toCameraVector;
out float visibility;

out float do_discard;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition[4];

const float density = 0;
const float gradient = 5.0;

uniform vec4 plane;

void main(void){

	vec4 worldPosition = vec4(position,1.0);
	
	gl_ClipDistance[0] = dot(worldPosition, plane);
	
	vec4 positionRelativeToCam = viewMatrix * worldPosition;

	vec4 pposition = projectionMatrix * positionRelativeToCam;
	
	do_discard = 0;
	
	if(worldPosition.x < 1){
		do_discard = 1;
	}
	
	if(worldPosition.z < 1){
		do_discard = 1;
	}
	
	gl_Position = pposition;

	pass_textureCoordinates = textureCoordinates;
	
	surfaceNormal = (vec4(normal,0.0)).xyz;
	for(int i=0;i<4;i++){
		toLightVector[i] = lightPosition[i] - worldPosition.xyz;
	}
	toCameraVector = (inverse(viewMatrix) * vec4(0.0,0.0,0.0,1.0)).xyz - worldPosition.xyz;
	
	float distance = length(positionRelativeToCam.xyz);
	visibility = exp(-pow((distance*density),gradient));
	visibility = clamp(visibility,0.0,1.0);
}