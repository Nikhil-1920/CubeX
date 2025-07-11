import java.util.ArrayDeque;
import java.util.HashMap;
import java.util.Map.Entry;


public class Main {
	
	public static final int GODS_NUMBER = 14;
	public static void main(String[] args) { new RubiksCubeUI(); }
	
    // Implementing a 2-way Breadth-First-Search (BFS) on the RubiksConfiguration 
	// Implicit graph returns the shortest path which is under 20-moves
	// Diameter for (2x2x2 Rubik's Cube) is God Number = 14

	public static String solve(RubiksConfiguration state) {

	    HashMap<RubiksConfiguration, String> fdParents = new HashMap<RubiksConfiguration, String>();
	    HashMap<RubiksConfiguration, String> bdParents = new HashMap<RubiksConfiguration, String>();

	    ArrayDeque<RubiksConfiguration> fqueue = new ArrayDeque<RubiksConfiguration>();
	    ArrayDeque<RubiksConfiguration> bqueue = new ArrayDeque<RubiksConfiguration>();
	    RubiksConfiguration src = state, end = new RubiksConfiguration();

	    fdParents.put(end, null);
	    bdParents.put(src, null);
	    fqueue.add(end);
	    fqueue.add(new RubiksConfiguration(true));
	    bqueue.add(src);
	    
	    // Checking if Rubik's Cube is already solved
	    if(end.equals(src)) { return "ALREADY SOLVED."; }
	        
	    // BFS visiting from each ends of graph
	    int graphRadius = GODS_NUMBER / 2;
	    for(int i = 0; i <= graphRadius; i++) {

	        while(true) {

	            end = fqueue.remove();
	            if(end.isNull) {
	                fqueue.add(new RubiksConfiguration(true));
	                break;
	            }
	            
	            for(Entry<String, RubiksConfiguration> move : end.getReachableStates().entrySet()) {

	                if(!fdParents.containsKey(move.getValue())) {
				
	                    fdParents.put(move.getValue(), move.getKey()); 
	                    fqueue.add(move.getValue());
	                }
	            }
	            
	            src = bqueue.remove();

	            for(Entry<String, RubiksConfiguration> move : src.getReachableStates().entrySet()) {

					if(!bdParents.containsKey(move.getValue())) {

						bdParents.put(move.getValue(), move.getKey()); 
						bqueue.add(move.getValue());
					}

					// Same state found in search from both ends
					if(fdParents.containsKey(move.getValue())) {

						String endpath = path(move.getValue(), fdParents);
						String srcpath = path(move.getValue(), bdParents);
						srcpath = reverse(srcpath);
						String solutionPath = srcpath + " " + endpath;
						return solutionPath.replaceAll("(([RUF])'?) \\1", "$22");
					}
	            }
	        }
        }
		
	    return "NO SOLUTION POSSIBLE!! IMPOSSIBLE CONFIGURATION!!";
	}
	
	private static String reverse(String path) {

		path += " ";
		String reverse = "";
	    
		for(int i = 0; i < path.length(); i++) {

			if(path.charAt(i) == ' ') { reverse += "' "; }
			else if(path.charAt(i) != '\'') { reverse += path.charAt(i); }
			else { reverse += " "; i++; }
		}
        
		String str[] = reverse.split(" ");
		for(int i = 0; i < str.length / 2; i++) {

		    String temp = str[i];
		    str[i] = str[str.length - 1 - i];
		    str[str.length - 1 - i] = temp;
		}
        
        return String.join(" ", str);
	}
	
	private static String path(RubiksConfiguration state, HashMap<RubiksConfiguration, String> parents) {

		String path = parents.get(state);
		RubiksConfiguration next = new RubiksConfiguration(state.positions);
		next.moveInSequence(path);
        
		while (parents.get(next) != null) {
			path += " " + parents.get(next);
			next = new RubiksConfiguration(state.positions);
			next.moveInSequence(path);
		}
        return path;
	}
}	
