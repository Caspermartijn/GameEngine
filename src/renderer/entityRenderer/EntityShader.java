package renderer.entityRenderer;

import java.util.List;

import entities.Light;
import shaders.shaderObjects.*;
import utils.SourceFile;

public class EntityShader extends ShaderProgram {

	private static final SourceFile VERTEX_FILE = new SourceFile("/shaders/entity/vertex.fragx");
	private static final SourceFile FRAGMENT_FILE = new SourceFile("/shaders/entity/fragment.fragx");

	public UniformVec2 offset = new UniformVec2("offset");
	public UniformMat4 transformationMatrix = new UniformMat4("transformationMatrix");
	public UniformMat4 projectionMatrix = new UniformMat4("projectionMatrix");
	public UniformMat4 viewMatrix = new UniformMat4("viewMatrix");
	public UniformVec3[] lightPosition = new UniformVec3[4];
	public UniformVec3[] lightColour = new UniformVec3[4];
	public UniformVec3[] attenuation = new UniformVec3[4];
	public UniformFloat numberOfRows = new UniformFloat("numberOfRows");
	public UniformVec4 plane = new UniformVec4("plane");
	public UniformVec3 skyColour = new UniformVec3("skyColour");
	public UniformFloat shineDamper = new UniformFloat("shineDamper");
	public UniformFloat reflectivity = new UniformFloat("reflectivity");
	public UniformBoolean useFakeLighting = new UniformBoolean("useFakeLighting");

	public EntityShader() {
		super(ShaderProgram.newShaderProgram(VERTEX_FILE, FRAGMENT_FILE).addInput(0, "position")
				.addInput(1, "textureCoordinates").addInput(2, "normal").addOutput(0, "outColor"));
		initLights();
		super.storeAllUniformLocations(offset, transformationMatrix, projectionMatrix, viewMatrix, lightPosition[0],
				lightPosition[1], lightPosition[2], lightPosition[3], lightColour[0], lightColour[1], lightColour[2],
				lightColour[3], attenuation[0], attenuation[1], attenuation[2], attenuation[3], numberOfRows, plane,
				skyColour, shineDamper, reflectivity, useFakeLighting);
	}

	private void initLights() {
		for (int i = 0; i < 4; i++) {
			lightPosition[i] = new UniformVec3("lightPosition[" + i + "]");
			lightColour[i] = new UniformVec3("lightColour[" + i + "]");
			attenuation[i] = new UniformVec3("attenuation[" + i + "]");
		}
	}

	public void loadLights(List<Light> lights) {
		for (int i = 0; i < 4; i++) {
			
			if(lights.size() < i){
				
			}else{
				
			}
	
		
		}
	}

}
