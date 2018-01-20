#version 150

const int MAX_JOINTS = 50;
const int MAX_WEIGHTS = 3;

in vec3 in_Position;
in vec2 in_TextureCoords;
in vec3 in_Normal;
in ivec3 in_JointIndices;
in vec3 in_Weights;

out vec2 pass_textureCoords;
out vec3 pass_normal;

uniform mat4 jointTransforms[MAX_JOINTS];
uniform mat4 projectionViewMatrix;
uniform mat4 modelMatrix;

void main(void){
	float m = in_Weights[0] + in_Weights[1] + in_Weights[2];
	vec3 weights = in_Weights / m;

	vec4 totalLocalPos = vec4(0.0);
	vec4 totalNormal = vec4(0.0);
	for(int i=0;i<MAX_WEIGHTS;i++){
		vec4 localPosition = jointTransforms[in_JointIndices[i]] * vec4(in_Position, 1.0);
		totalLocalPos += localPosition * weights[i];
		
		vec4 worldNormal = jointTransforms[in_JointIndices[i]] * vec4(in_Normal, 0.0);
		totalNormal += worldNormal * weights[i];
	}
	
	gl_Position = projectionViewMatrix * modelMatrix * vec4(totalLocalPos.xyz, 1.0);
	pass_normal = (modelMatrix * totalNormal).xyz;
	
	pass_textureCoords = in_TextureCoords;

}