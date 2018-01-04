#version 400 core

in vec3 position;
in vec2 textureCoordinates;
in vec3 normal;
in vec3 tangent;

out vec2 pass_textureCoordinates;
out vec3 toLightVector[4];
out vec3 toCameraVector;
out float visibility;
out float render;
out vec3 surfaceNormal;
out vec4 shadowCoords;

uniform mat4 projectionMatrix;
uniform mat4 viewMatrix;
uniform vec3 lightPosition[4];
uniform float renderDistance;

uniform mat4 toShadowMapSpace;

uniform float numberOfRows;
uniform vec2 offset;

uniform int maxLights;

uniform float density = 0.0035;
uniform float gradient = 5f;
uniform float shadowDistance;

const float transitionDistance = 10.0;

void main(void) {
	vec4 worldPosition = vec4(position, 1.0);
	shadowCoords = toShadowMapSpace * worldPosition;

	vec4 positionRelativeToCam = viewMatrix * worldPosition;
	gl_Position = projectionMatrix * viewMatrix * worldPosition;
	vec2 texCoord = vec2(textureCoordinates.x, textureCoordinates.y - 1);
	pass_textureCoordinates = (texCoord / numberOfRows)
			+ vec2((offset.x), offset.y);

	mat4 modelViewMatrix = viewMatrix;
	vec4 posRelCam = modelViewMatrix * vec4(position, 1.0);
	vec3 preNorm = (modelViewMatrix * vec4(normal, 0.0)).xyz;

	surfaceNormal = (vec4(normal, 0.0)).xyz;

	vec3 norm = normalize(preNorm);
	vec3 tang = normalize((modelViewMatrix * vec4(tangent, 0.0)).xyz);
	vec3 bitang = normalize(cross(norm, tang));
	mat3 toTangentSpace = mat3(tang.x, bitang.x, norm.x, tang.y, bitang.y,
			norm.y, tang.z, bitang.z, norm.z);

	toCameraVector = toTangentSpace * (-posRelCam.xyz);

	for (int i = 0; i < 4; i++) {
		toLightVector[i] = toTangentSpace * (lightPosition[i] - posRelCam.xyz);
	}

	float distance = length(positionRelativeToCam.xyz);
	if (distance > renderDistance) {
		render = 0;
	} else {
		render = 1;
	}

	visibility = exp(-pow((distance * density), gradient));
	visibility = clamp(visibility, 0.0, 1.0);

	distance = distance - (shadowDistance - transitionDistance);
	distance = distance / transitionDistance;

}
