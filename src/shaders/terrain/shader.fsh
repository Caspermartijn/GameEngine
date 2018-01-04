#version 400 core

in vec2 pass_textureCoordinates;
in vec3 toLightVector[4];
in vec3 toCameraVector;
in float visibility;
in float render;
in vec3 surfaceNormal;
in vec4 shadowCoords;

out vec4 out_Color;

uniform sampler2D black;
uniform sampler2D red;
uniform sampler2D green;
uniform sampler2D blue;
uniform sampler2D blend;

uniform sampler2D normalBack;
uniform sampler2D normalRed;
uniform sampler2D normalGreen;
uniform sampler2D normalBlue;

uniform sampler2D shadowMap;

uniform vec3 lightColour[4];
uniform vec3 attenuation[4];
uniform float shineDamper;
uniform float reflectivity;
uniform vec3 skyColor;

uniform float tileAm[];

uniform float shadowMapSize;
const int pcfCount = 1;

void main(void) {
	if (render < 0.5) {
		discard;
	}

	vec3 unitVectorToCamera = normalize(toCameraVector);

	vec4 blendMapC = texture(blend, pass_textureCoordinates);
	float backTex = 1 - (blendMapC.r + blendMapC.g + blendMapC.b);

	vec2 tiledCoordsBack = pass_textureCoordinates * tileAm[0];
	vec2 tiledCoordsRed = pass_textureCoordinates * tileAm[1];
	vec2 tiledCoordsGreen = pass_textureCoordinates * tileAm[2];
	vec2 tiledCoordsBlue = pass_textureCoordinates * tileAm[3];
	vec4 backgroundTexC = texture(black, tiledCoordsBack) * backTex;
	vec4 rTex = texture(red, tiledCoordsRed) * blendMapC.r;
	vec4 gTex = texture(green, tiledCoordsGreen) * blendMapC.g;
	vec4 bTex = texture(blue, tiledCoordsBlue) * blendMapC.b;

	vec4 totC = backgroundTexC + rTex + gTex + bTex;

	vec3 unitNormalBlack = normalize(
			(2.0 * texture(normalBack, tiledCoordsBack) - 1).rgb);
	vec3 unitNormalRed = normalize(
			(2.0 * texture(normalRed, tiledCoordsRed) - 1).rgb);
	vec3 unitNormalGreen = normalize(
			(2.0 * texture(normalGreen, tiledCoordsGreen) - 1).rgb);
	vec3 unitNormalBlue = normalize(
			(2.0 * texture(normalBlue, tiledCoordsBlue) - 1).rgb);

	vec3 unitNormal = surfaceNormal;
	float c = backTex;
	int id = 0;

	if (blendMapC.r > c) {
		id = 1;
		c = blendMapC.r;
	}
	if (blendMapC.g > c) {
		id = 2;
		c = blendMapC.g;
	}
	if (blendMapC.b > c) {
		id = 3;
		c = blendMapC.b;
	}
	if (backTex > c) {
		id = 0;
		c = backTex;
	}

	if (id == 0) {
		unitNormal = unitNormalBlack;
	}
	if (id == 1) {
		unitNormal = unitNormalRed;
	}
	if (id == 2) {
		unitNormal = unitNormalGreen;
	}
	if (id == 3) {
		unitNormal = unitNormalBlue;
	}

	vec3 totalDiff = vec3(0.0);
	vec3 totspec = vec3(0.0);

	for (int i = 0; i < 4; i++) {

		float distance = length(toLightVector[i]);
		float attFac = attenuation[i].x + (attenuation[i].y * distance)
				+ (attenuation[i].z * distance * distance);

		vec3 unitLightVector = normalize(toLightVector[i]);
		vec3 lightDirection = -unitLightVector;
		vec3 reflectedLightDirection = reflect(lightDirection, unitNormal);

		float nDotl = dot(unitNormal, unitLightVector);

		float brightness = max(nDotl, 0.2);
		float specularFactor = dot(reflectedLightDirection, unitVectorToCamera);
		specularFactor = max(specularFactor, 0.0);
		float dampedFactor = pow(specularFactor, shineDamper);
		totalDiff = totalDiff + brightness * lightColour[i];
		totspec = totspec + dampedFactor * reflectivity * lightColour[i];
		totalDiff = totalDiff + (brightness * lightColour[i]) / attFac;
		totspec = totspec
				+ (dampedFactor * reflectivity * lightColour[i]) / attFac;
	}

	totalDiff = max(totalDiff, 0.4);

	out_Color = vec4(totalDiff, 1.0) * totC + vec4(totspec, 1.0);
	out_Color = mix(vec4(skyColor, 1.0), out_Color, visibility);
}

