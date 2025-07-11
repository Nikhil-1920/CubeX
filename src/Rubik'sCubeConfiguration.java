import java.util.Arrays;
import java.util.HashMap;
import java.util.Random;


class RubiksConfiguration {

	char positions[] = {0};
	boolean isNull = false;

	// Rubik's Cube Color Notations
	// w stands for white
	// r stands for red
	// b stands for blue
	// o stands for orange
	// g stands for green
	// y stands for yellow

	public RubiksConfiguration() {

		positions = new char[24];
		for(int i = 0; i < 4; i++) { positions[i] = 'w'; }
		for(int i = 4; i < 8; i++) { positions[i] = 'o'; }
 
		for(int i = 8; i < 12; i++)  { positions[i] = 'g'; }
		for(int i = 12; i < 16; i++) { positions[i] = 'r'; }
		for(int i = 16; i < 20; i++) { positions[i] = 'y'; }
		for(int i = 20; i < 24; i++) { positions[i] = 'b'; }

		this.isNull = false;
	}
		
	public RubiksConfiguration(char positions[]) {

		this.positions = positions;
		this.isNull = false;
	}
	
	public RubiksConfiguration(boolean nullConfig) { this.isNull = nullConfig; }
	
	// Rubik's Cube Moves Notations
	// F  - Front-Rotated clockwise
	// F' - Front-Counter clockwise
	// R  - Right clockwise
	// R' - Right-Counter clockwise
	// U  - Up clockwise
	// U' - Up-Counter clockwise
	
	private static int[] F  = {0, 1, 5, 6, 4, 16, 17, 7, 11, 8, 9, 10, 3, 13, 14, 2, 15, 12, 18, 19, 20, 21, 22, 23};
	private static int[] Fi = inversePermutation(F);
	private static int[] U  = {3, 0, 1, 2, 8, 9, 6, 7, 12, 13, 10, 11, 20, 21, 14, 15, 16, 17, 18, 19, 4, 5, 22, 23};
	private static int[] Ui = inversePermutation(U);
	private static int[] R  = {0, 9, 10, 3, 4, 5, 6, 7, 8, 17, 18, 11, 15, 12, 13, 14, 16, 23, 20, 19, 2, 21, 22, 1};
	private static int[] Ri = inversePermutation(R);

	public char[] regularPermutation(int P[]) {

		char newPositions[] = new char[24];
		for(int i = 0; i < 24; i++) { newPositions[i] = positions[P[i]]; }
		return newPositions;
	}

    public static int[] inversePermutation(int P[]) {

		int pLength = P.length;
		int Pi[] = new int[pLength];
		for(int i = 1; i < pLength; i++) { Pi[P[i]] = i; }
		return Pi;
	}

	public HashMap<String, RubiksConfiguration> getReachableStates() {

		HashMap<String, RubiksConfiguration> moves = new HashMap<>();
		addBasicMove("F'", F, moves);
		addBasicMove("F", Fi, moves);
		addBasicMove("U'", U, moves);
		addBasicMove("U", Ui, moves);
		addBasicMove("R'", R, moves);
		addBasicMove("R", Ri, moves);
		return moves;
	}
	
	private void addBasicMove(String name, int P[], HashMap<String, RubiksConfiguration> moves) {

		RubiksConfiguration config = new RubiksConfiguration(regularPermutation(P));
		moves.put(name, config);
	}

	public void moveInSequence(String sequence) {

		if(sequence == null) return;
		String moves[] = sequence.toUpperCase().split(" ");

		for(String move : moves) {

			switch(move) {
				case "F":
				    positions = regularPermutation(F);
				    break;

				case "F'":
				    positions = regularPermutation(Fi);
				    break;

				case "F2":
				    positions = regularPermutation(F);
				    positions = regularPermutation(F);
				    break;

				case "U":
				    positions = regularPermutation(U);
				    break;

				case "U'":
				    positions = regularPermutation(Ui);
				    break;

				case "U2":
				    positions = regularPermutation(U);
				    positions = regularPermutation(U);
				    break;

				case "R":
				    positions = regularPermutation(R);
				    break;

				case "R'":
				    positions = regularPermutation(Ri);
				    break;

				case "R2":
				    positions = regularPermutation(R);
				    positions = regularPermutation(R);
				    break;
			}
		}
	}
    
	public int getRandomWithExclusion(Random rand, int start, int end, int... exclude) {
		int random = start + rand.nextInt(end - start + 1 - exclude.length);
		
		for(int idx : exclude) {
			if(random < idx) { break; }
	        random++;
	    }
		return random;
	}
	
	public String randomize() {

		Random rand = new Random();
		String scramble = "";
		int previousMove = -1;
		String moves[][] = { {"F", "F'", "F2"}, {"U", "U'", "U2"}, {"R", "R'", "R2"} };	  

		for(int i = 0; i < 17; i++) {

			previousMove = getRandomWithExclusion(rand, 0, moves.length - 1, previousMove);
			String shift = moves[previousMove][rand.nextInt(moves[0].length)];
			scramble += shift + " ";
		}
	    
		scramble = scramble.substring(0, scramble.length() - 2);
		moveInSequence(scramble);
		return scramble;
	}

	@Override
	public int hashCode() { return Arrays.toString(this.positions).hashCode(); }

	@Override
	public boolean equals(Object object) {

		if(!object.getClass().getSimpleName().equals("RubiksConfiguration")) { return false; }

		RubiksConfiguration config = (RubiksConfiguration)object;
		if(config.positions.length != this.positions.length) { return false; }

		for(int i = 0; i < this.positions.length; i++) {
			if(this.positions[i] != config.positions[i]) { return false; }
		}
		return true;
	}
}
