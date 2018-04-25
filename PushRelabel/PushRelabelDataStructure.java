package PushRelabel;

public class PushRelabelDataStructure {
	
	    public int src, sink, numVertices;
	    
	    public int[][] capacities,flows,neighbList;
	   
	    public int[] excess, height;

	    public PushRelabelDataStructure(int n, int source, int snk, int[][] caps){
	        numVertices = n;
	        src = source;
	        sink = snk;
	        capacities = caps;
	        flows = new int[n][n];
	        excess = new int[n];
	        height = new int[n];
	        neighbList = buildNeighborList(n, caps);
	    }
	    
	    private int[][] buildNeighborList(int n, int[][] caps){
	        int[] numNeighbs = new int[n];//temp array
	        int[][] tempNeighbList = new int[n][];//create neighbor array from edge capacities
	        //first count number of neighbors for each vertex
	        for(int i=0;i<caps.length;i++){
	            for(int j=0;j<caps[i].length;j++){
	                if(caps[i][j] > 0){
	                    numNeighbs[i]++;
	                    numNeighbs[j]++;
	                }
	            }
	        }
	        //allocate capacities
	        for(int i=0; i<numNeighbs.length;i++){
	            tempNeighbList[i] = new int[numNeighbs[i]];
	        }
	        int[] counter = new int[n];//temp array
	        //store neighbors of each vertex
	        for(int i=0;i<caps.length;i++){
	            for(int j=0;j<caps[i].length;j++){
	                if(caps[i][j] > 0){
	                    tempNeighbList[i][counter[i]] = j;
	                    tempNeighbList[j][counter[j]] = i;
	                    counter[i]++;
	                    counter[j]++;
	                }
	            }
	        }
	        return tempNeighbList;
	    }
	}