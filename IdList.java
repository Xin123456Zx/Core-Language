class IdList {
    Id id;
    IdList list;

    void parse() {
        this.id = new Id();
        this.id.parse();
        if (Parser.scanner.currentToken() == Core.COMMA) {
            Parser.scanner.nextToken();
            this.list = new IdList();
            this.list.parse();
        }
    }

    void semantic() {
        this.id.doublyDeclared();
        this.id.addToScope();
        if (this.list != null) {
            this.list.semantic();
        }
    }

    void print() {
        this.id.print();
        if (this.list != null) {
            System.out.print(",");
            this.list.print();
        }
    }

    void execute() {
        this.id.executeAllocate();
        if (this.list != null) {
            this.list.execute();
        }
      

    }
}
