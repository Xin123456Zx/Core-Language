import java.util.*; 

class FuncCall implements Stmt {
	Id name;
	Formals list;
	
	public void parse() {
		Parser.expectedToken(Core.BEGIN);
		Parser.scanner.nextToken();
		Parser.expectedToken(Core.ID);
		name = new Id();
		name.parse();
		Parser.expectedToken(Core.LPAREN);
		Parser.scanner.nextToken();
		list = new Formals();
		list.parse();
		Parser.expectedToken(Core.RPAREN);
		Parser.scanner.nextToken();
		Parser.expectedToken(Core.SEMICOLON);
		Parser.scanner.nextToken();
	}
	
	public void semantic() {
		if (!Parser.functionDeclaredCheck(name.getString())) {
			System.out.println("ERROR: Calling function " + name.getString() + " but " + name.getString() + " is not declared!");
			System.exit(0);
		}
		List<Id> arguments = list.getListOfId();
		for (Id test : arguments) {
			test.semantic();
		}
	}
	
	public void print(int indent) {
		for (int i=0; i<indent; i++) {
			System.out.print("\t");
		}
		System.out.print("begin ");
		name.print();
		System.out.print("(");
		list.print();
		System.out.println(");");
	}
	
	public void execute() {
		FuncDecl definition = Executor.retrieveFunction(name);
		List<String> formalParameters = definition.getFormalParametersList();
		List<String> actualParameters = list.getListOfStrings();
		if (formalParameters.size() != actualParameters.size()) {
			System.out.println("ERROR: Number of formal parameters does not match number of actual parameters!");
			System.exit(0);
		}
		Executor.pushFrame(formalParameters, actualParameters);
		definition.getBody().execute();
		Executor.popFrame(formalParameters, actualParameters);
	}
}