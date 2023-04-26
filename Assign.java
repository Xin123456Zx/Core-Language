class Assign implements Stmt {
    Id id;
    Id id2;
    Expr expr;
    int option;

    @Override
    public void parse() {
        this.id = new Id();
        this.id.parse();
        Parser.expectedToken(Core.ASSIGN);
        Parser.scanner.nextToken();

        if (Parser.scanner.currentToken() == Core.NEW) {
            this.option = 2;
            Parser.scanner.nextToken();
            this.expr = new Expr();
            this.expr.parse();
            Parser.expectedToken(Core.SEMICOLON);
            Parser.scanner.nextToken();
        } else if (Parser.scanner.currentToken() == Core.DEFINE) {
            this.option = 3;
            Parser.scanner.nextToken();
            Parser.expectedToken(Core.ID);
            this.id2 = new Id();
            this.id2.parse();
            Parser.expectedToken(Core.SEMICOLON);
            Parser.scanner.nextToken();

        } else {
            this.option = 1;
            this.expr = new Expr();
            this.expr.parse();
            Parser.expectedToken(Core.SEMICOLON);
            Parser.scanner.nextToken();
        }

    }

    @Override
    public void semantic() {
        this.id.semantic();
        //correct or not for option1?
        if (this.option == 1) {
            this.expr.semantic();

        } else if (this.option == 2) {
            this.expr.semantic();

        } else if (this.option == 3) {
            this.id2.semantic();

        }
    }

    @Override
    public void print(int indent) {
        for (int i = 0; i < indent; i++) {
            System.out.print("\t");
        }
        this.id.print();
        System.out.print("=");
        if (this.option == 1) {
            this.expr.print();

        } else if (this.option == 2) {
            System.out.print("new");
            this.expr.print();
        } else if (this.option == 3) {

            System.out.print("define");
            this.id2.print();

        }
        System.out.println(";");
    }

    @Override
    public void execute() {
        if (this.option == 1) {
            this.id.executeAssign(this.expr.execute());
        } else if (this.option == 2) {
            //countReference-1 if x refers to other value
            GarbageCollector.subCountReference(this.id.getString());

            //1. insert the value of expression in the heap
            int value = this.expr.execute();//value of expression
            GarbageCollector.heap.add(value);
            GarbageCollector.countTotalSpace++;
            System.out.println("gc:" + GarbageCollector.countTotalSpace);
            //2.change the call stack value of x to the position of heap
            int newPositionInHeap = GarbageCollector.heap.size()-1;
            Executor.varSet(this.id.getString(), newPositionInHeap);
            //3. let the count number++
            GarbageCollector.countReference.add(1);

        } else if (this.option == 3) {
            //countReference-1 if x refers to other value
            GarbageCollector.subCountReference(this.id.getString());
            //2.change the call stack value of id to the position of heap
            Integer newPositionInHeap = Executor.varGet(this.id2.getString()); //    position of x now on the heap
	   Executor.varSet(this.id.getString(), newPositionInHeap);
            //3. let the count number++
	if(newPositionInHeap!=null)
	 {
            GarbageCollector.countReference.set(newPositionInHeap,
                    GarbageCollector.countReference.get(newPositionInHeap) + 1);
	 }

        }

    }
}
