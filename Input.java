class Input implements Stmt {
    Id id;

    @Override
    public void parse() {
        Parser.scanner.nextToken();
        this.id = new Id();
        this.id.parse();
        Parser.expectedToken(Core.SEMICOLON);
        Parser.scanner.nextToken();
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
        System.out.print("input ");
        this.id.print();
        System.out.println(";");
    }

    @Override
    public void execute() {

        if (Executor.scanner.currentToken() == Core.CONST) {
            this.id.executeAssign(Executor.scanner.getCONST());
            Executor.scanner.nextToken();
        } else {
            System.out.println("ERROR: Data file out of values!");
            System.exit(0);
        }

    }
}