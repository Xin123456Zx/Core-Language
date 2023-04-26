public class NewDecl implements Stmt {

    Id id;

    @Override
    public void parse() {
        Parser.scanner.nextToken();
        this.id = new Id();
        this.id.parse();
     //   Parser.expectedToken(Core.SEMICOLON);
	if(Parser.scanner.currentToken() == Core.SEMICOLON)
	{
		Parser.scanner.nextToken();
	}
	else if (Parser.scanner.currentToken() == Core.ASSIGN) {
			Parser.scanner.nextToken();
			if(Parser.scanner.currentToken() == Core.NEW)
			{
			System.out.println("Error is new keyword used in inappropriate location.");
			System.exit(0);
			}
			else
			{ System.out.println("Error expected SEMICOLON, received ASSIGN."); }	
	
		} 
	else
	{
		 Parser.expectedToken(Core.SEMICOLON);
	
	}
       

    }

    @Override
    public void semantic() {
        this.id.semantic();

    }

    @Override
    public void print(int indent) {

        for (int i = 0; i < indent; i++) {
            System.out.print("\t");
        }
        System.out.print("define");
        this.id.print();
        System.out.println(";");

    }

    @Override
    public void execute() {

        this.id.executeAllocate();
        GarbageCollector.refVariableDeclInScope(this.id.getString());

    }

}
