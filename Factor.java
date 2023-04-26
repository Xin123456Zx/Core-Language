class Factor {
    Id id;
    int constant;
    Expr expr;

    void parse() {
        if (Parser.scanner.currentToken() == Core.ID) {
            this.id = new Id();
            this.id.parse();
        } else if (Parser.scanner.currentToken() == Core.CONST) {
            this.constant = Parser.scanner.getCONST();
            Parser.scanner.nextToken();
        } else if (Parser.scanner.currentToken() == Core.LPAREN) {
            Parser.scanner.nextToken();
            this.expr = new Expr();
            this.expr.parse();
            Parser.expectedToken(Core.RPAREN);
            Parser.scanner.nextToken();
        } else {
            System.out.println("ERROR: Expected ID, CONST, or LPAREN, recieved "
                    + Parser.scanner.currentToken());
            System.exit(0);
        }
    }

    void semantic() {
        if (this.id != null) {
            this.id.semantic();
        } else if (this.expr != null) {
            this.expr.semantic();
        }
    }

    void print() {
        if (this.id != null) {
            this.id.print();
        } else if (this.expr != null) {
            System.out.print("(");
            this.expr.print();
            System.out.print(")");
        } else {
            System.out.print(this.constant);
        }
    }

    //Returns the int value of the factor
    int execute() {
        int value = 0;
        if (this.id != null) {
            if (GarbageCollector.varType.get(this.id.getString()).peek() == 0) {
                value = this.id.executeValue();
            } else if (GarbageCollector.varType.get(this.id.getString())
                    .peek() == 1)

            {
                int oldPositionInHeap = Executor.varGet(this.id.getString());
                value = GarbageCollector.heap.get(oldPositionInHeap);
            }

        } else if (this.expr != null) {
            value = this.expr.execute();
        } else {
            value = this.constant;
        }
        return value;
    }
}