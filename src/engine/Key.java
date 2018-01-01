package engine;

public class Key {

	private int code;
	private char character;
	private char shiftCharacter;
	private char altCharacter;

	public Key(int code, char character, char shiftCharacter, char altCharacter) {
		super();
		this.code = code;
		this.character = character;
		this.shiftCharacter = shiftCharacter;
		this.altCharacter = altCharacter;
	}
	
	public Key(int code, char character, char shiftCharacter) {
		super();
		this.code = code;
		this.character = character;
		this.shiftCharacter = shiftCharacter;
		this.altCharacter = character;
	}
	
	public Key(int code, char character) {
		super();
		this.code = code;
		this.character = character;
		this.shiftCharacter = character;
		this.altCharacter = character;
	}

	public int getCode() {
		return code;
	}

	public char getCharacter() {
		return character;
	}

	public char getShiftCharacter() {
		return shiftCharacter;
	}

	public char getAltCharacter() {
		return altCharacter;
	}

}
