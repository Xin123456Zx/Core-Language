import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Stack;

public class GarbageCollector {

    static void initialGC() {
        heap = new ArrayList<Integer>();
        countReference = new ArrayList<Integer>();
        countTotalSpace = 0;
        varType = new HashMap<String, Stack<Integer>>();
    }

    public static List<Integer> heap;
    public static List<Integer> countReference;
    public static int countTotalSpace;
    public static HashMap<String, Stack<Integer>> varType;

    //when encounter a new int declaration
    static void intVariableDeclInScope(String var) {
        if (!varType.containsKey(var)) {
            varType.put(var, new Stack<Integer>());
        }
        varType.get(var).push(0);
    }

    //when encounter a new reference declaration
    static void refVariableDeclInScope(String var) {
        if (!varType.containsKey(var)) {
            varType.put(var, new Stack<Integer>());
        }
        varType.get(var).push(1);
    }

    //countReference-1 if x refers to other value, when use "new" or "define" in Assign
 	static void subCountReference(String var) {

        Integer oldPositionInHeap = Executor.varGet(var); //get position of var in the heap
	if(oldPositionInHeap!=null)
     {	
        // System.out.println("count reference for"+ var+ "is"+countReference.get(oldPositionInHeap));
        countReference.set(oldPositionInHeap,
                countReference.get(oldPositionInHeap) - 1);
        if (countReference.get(oldPositionInHeap) == 0) {
            countTotalSpace--;
	// System.out.println("CHECK WHETHER IS 0");
	//   if(countTotalSpace>=0)
	//	{
            System.out.println("gc:" + countTotalSpace);
	//	}

        }
     }

 }
	static void IncCountReference(String var) {

        Integer oldPositionInHeap = Executor.varGet(var); //get position of var in the heap
	if(oldPositionInHeap!=null)
	{
        countReference.set(oldPositionInHeap,
               countReference.get(oldPositionInHeap) + 1);
	}
     
    }
    static void subCountReference(String var,HashMap<String, Integer> map) {

        Integer oldPositionInHeap = map.get(var); //get position of var in the heap
	if(oldPositionInHeap!=null)
    {
        countReference.set(oldPositionInHeap,
                countReference.get(oldPositionInHeap) - 1);

        if (countReference.get(oldPositionInHeap) == 0) {
            countTotalSpace--;
	//	  if(countTotalSpace>=0){
            System.out.println("gc:" + countTotalSpace);
          //}

        }
     }

    }

}
