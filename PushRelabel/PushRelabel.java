package PushRelabel;
// Send as much flow from s as possible.
// Build a list of all vertices except s and t.
// As long as we have not traversed the entire list:
//        Discharge the current vertex.
//        If the height of the current vertex changed:
//                Move the current vertex to the front of the list
//                Restart the traversal from the front of the list.
public class PushRelabel {
	
    private PushRelabelDataStructure fn;
    
    public PushRelabel(PushRelabelDataStructure flownetwork) {
        fn = flownetwork;
    }

    private int push(PushRelabelDataStructure fn, int from, int to) {
        int flowAmt = Math.min(fn.excess[from], fn.capacities[from][to] - fn.flows[from][to]);
        fn.excess[to] += flowAmt;
        fn.flows[from][to] += flowAmt;
        fn.excess[from] -= flowAmt;
        fn.flows[to][from] -= flowAmt;
        return flowAmt;
    }

    private void relabel(PushRelabelDataStructure fn, int target) {
        int minH = Integer.MAX_VALUE;
        for (int i = 0; i < fn.neighbList[target].length; i++) {
            int myI = fn.neighbList[target][i];
            if (fn.capacities[target][myI] - fn.flows[target][myI] > 0 && fn.height[myI] >= fn.height[target]) {
                if (fn.height[myI] < minH) {
                    minH = fn.height[myI];
                }
            }
        }
        fn.height[target] = minH + 1;
    }

    private int[] discharge(PushRelabelDataStructure fn, int target, int[] currentNeighbList) {
        //while target vertex of discharge has excess
        while (fn.excess[target] > 0) {
            //retrieve the neighbor we are currently inspecting
            int neighbInd = currentNeighbList[target];
            //ensure that we have not looked through all neighbors
            if (neighbInd >= fn.neighbList[target].length) {
                //if we have, then we must relabel and reset our current inspection to the front of the list
                relabel(fn, target);
                currentNeighbList[target] = 0;
            } else {
                //otherwise find the id of the neighbor, check if it is a valid push target and push, if not move to next neighbor
                int neighb = fn.neighbList[target][neighbInd];
                int residualCap = fn.capacities[target][neighb] - fn.flows[target][neighb];
                if (residualCap > 0 && fn.height[target] == (fn.height[neighb] + 1)) {
                    push(fn, target, neighb);
                } else {
                    currentNeighbList[target]++;
                }
            }
        }
        return currentNeighbList;
    }
    
    public int getMaxFlow() {
        this.init();
        //set up the dischargeList, does not include source or sink
        int[] dischargeList = new int[fn.numVertices - 2];
        //each vertex has a current neighbor being inspected
        int[] currentNeighbList = new int[fn.numVertices];

        //Init Discharge List
        int counter = 0;
        for (int i = 0; i < fn.numVertices; i++) {
            if (!(i == fn.src || i == fn.sink)) {
                dischargeList[counter] = i;
                counter++;
            }
        }

        int curVertexIndex = 0;
        //if we get to the end then we are finished
        while (curVertexIndex < dischargeList.length) {
            int curVert = dischargeList[curVertexIndex];
            int curVertHeight = fn.height[curVert];
            discharge(fn, curVert, currentNeighbList);
            if (fn.height[curVert] > curVertHeight) {//curVert was relabeled
                dischargeList = moveIndexToFrontOfAry(dischargeList, curVertexIndex);
                curVertexIndex = 0;
            } else {
                curVertexIndex++;
            }
        }
        //solution equals excess at the sink
        return fn.excess[fn.sink];
    }

    private void init() {
        fn.height[fn.src] = fn.numVertices;//height of source = |V|
        fn.excess[fn.src] = Integer.MAX_VALUE;//temporarily allow source to have infinite excess to utilize push
        int sourceOutflowSum = 0;
        for (int i = 0; i < fn.neighbList[fn.src].length; i++) {
            int vertID = fn.neighbList[fn.src][i];
            sourceOutflowSum += push(fn, fn.src, vertID);
        }
        fn.excess[fn.src] = -sourceOutflowSum;
    }

    private int[] moveIndexToFrontOfAry(int[] list, int targetInd) {
        int vertID = list[targetInd];
        for (int i = targetInd; i > 0; i--) {
            list[i] = list[i - 1];
        }
        list[0] = vertID;
        return list;
    }
}