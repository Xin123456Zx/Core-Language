import java.util.HashMap;
import java.util.List;
import java.util.Stack;

class Executor {
    //data file is stored here as a static field so it is avaiable to the Input execute method
    public static Scanner scanner;

    //Called from Program class to initialize all data structures
    static void initExecutor() {
        variables = new Stack<Stack<HashMap<String, Integer>>>();
        globalVars = new HashMap<String, Integer>();
        funcMap = new HashMap<String, FuncDecl>();
    }

    /*
     *
     * These are the data structures and helper functions to handle variables
     *
     */

    //gobalVars is a data structure to store the global variable values
    public static HashMap<String, Integer> globalVars;

    //variables represents the call stack
    public static Stack<Stack<HashMap<String, Integer>>> variables;

    //Push a nested scope onto the current frame, handles new nested scope for If or Loop statements
    static void pushScope() {
        variables.peek().push(new HashMap<String, Integer>());
    }

    //Pop a nested scope off of the current frame
    //correct???
    static void popScope() {
        HashMap<String, Integer> map = variables.peek().pop();
        for (String key : map.keySet()) {
            if (GarbageCollector.varType.get(key).peek() == 1) {
                GarbageCollector.subCountReference(key,map);

            }
	    GarbageCollector.varType.get(key).pop();
        }

    }

    //Called from Id class to handle assigning variables, handles new nested scope for If or Loop statements
    static void varSet(String x, Integer value) {
        if (!variables.peek().empty()) {
            HashMap<String, Integer> temp = variables.peek().pop();
            if (temp.containsKey(x)) 
	{
                temp.put(x, value);
            } else {
                varSet(x, value);
            }
            variables.peek().push(temp);
        } else {
            // If we get here, it must be a global variable
            globalVars.put(x, value);
        }
    }

    //Called from Id class to handle fetching the value of a variable
    static Integer varGet(String x) {
        Integer value = null;
        if (!variables.peek().empty()) {
            HashMap<String, Integer> temp = variables.peek().pop();
            if (temp.containsKey(x)) {
                value = temp.get(x);
            } else {
                value = varGet(x);
            }
            variables.peek().push(temp);
        } else {
            value = globalVars.get(x);
        }
        return value;
    }

    //Called from Id class to handle declaring a variable
    static void varInit(String x) {
        //Put null here so we can tell later if variable is uninitialized
        //If variables.peek() is null, initializing a global variable
        if (variables.size() == 0) {
            globalVars.put(x, null);
        } else {
            variables.peek().peek().put(x, null);
        }
    }

    /*
     *
     * These are the data structures and helper functions to handle function
     * calls
     *
     */

    //funcMap is a data structure to associate funciton names with definition, used in FuncDecl and FuncCall
    public static HashMap<String, FuncDecl> funcMap;

    //Called from FuncDecl to add function definition to funcMap
    static void registerFunction(Id id, FuncDecl temp) {
        funcMap.put(id.getString(), temp);
    }

    //Called from FuncCall to retrieve definition of called function
    static FuncDecl retrieveFunction(Id id) {
        return funcMap.get(id.getString());
    }

    /*
     *
     * These are the helper functions to handle maintaining the call stack
     *
     */

    //Called to push a new frame onto the call stack
    static void pushFrame() {
        variables.push(new Stack<HashMap<String, Integer>>());
        variables.peek().push(new HashMap<String, Integer>());
    }

    //Called to pop a frame off the call stack
    static void popFrame() {
        Stack<HashMap<String, Integer>> frame = variables.pop();
        for (String key : frame.peek().keySet()) {
            if (GarbageCollector.varType.get(key).peek() == 1) {
     
 	    
		GarbageCollector.subCountReference(key,frame.peek());

            }
		GarbageCollector.varType.get(key).pop();
        }

    }

    //Called to push a new frame onto the call stack and pass parameters
    static void pushFrame(List<String> formals, List<String> arguments) {
        Stack<HashMap<String, Integer>> newFrame = new Stack<HashMap<String, Integer>>();
        newFrame.push(new HashMap<String, Integer>());
        for (int i = 0; i < formals.size(); i++) {
            newFrame.peek().put(formals.get(i), varGet(arguments.get(i)));
 		if (GarbageCollector.varType.get(arguments.get(i)).peek() == 0) {
                GarbageCollector.intVariableDeclInScope(formals.get(i));
            } else if (GarbageCollector.varType.get(arguments.get(i))
                    .peek() == 1)

            {
             //let counnntreference of actual para++
		
		 GarbageCollector.refVariableDeclInScope(formals.get(i));
		 GarbageCollector.IncCountReference(arguments.get(i));
            }
		
        }
        variables.push(newFrame);
    }

    //Called to pop a frame off the call stack and pass back parameters
    static void popFrame(List<String> formals, List<String> arguments) {
        Stack<HashMap<String, Integer>> oldFrame = variables.pop();
	for (int i = 0; i < formals.size(); i++) {
	   int value=varGet(arguments.get(i));
           varSet(arguments.get(i), oldFrame.peek().get(formals.get(i)));
	   HashMap<String, Integer> temp=new HashMap<String, Integer>();	
            if (GarbageCollector.varType.get(arguments.get(i)).peek()== 1) {
     		temp.put(arguments.get(i),value);
		GarbageCollector.IncCountReference(arguments.get(i));
		GarbageCollector.subCountReference(arguments.get(i),temp);

            }
        }
	 for (String key : oldFrame.peek().keySet()) {
            if (GarbageCollector.varType.get(key).peek() == 1) {
     
		GarbageCollector.subCountReference(key,oldFrame.peek());

            }

		GarbageCollector.varType.get(key).pop();
        }
        
	

    }
}
