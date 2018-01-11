#version 400 core 
in vec2 inPosition; 
in vec2 inTextureCoords;
out vec2 passTextureCoords; 
uniform vec2 translation; 

void main(void) {
gl_Position = vec4(inPosition + translation * vec2(2.0, -2.0), 0.0, 1.0);
passTextureCoords = inTextureCoords;
}