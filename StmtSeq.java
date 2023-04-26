class StmtSeq {
    Stmt stmt;
    StmtSeq ss;

    void parse() {
        if (Parser.scanner.currentToken() == Core.ID) {
            this.stmt = new Assign();
        } else if (Parser.scanner.currentToken() == Core.INPUT) {
            this.stmt = new Input();
        } else if (Parser.scanner.currentToken() == Core.OUTPUT) {
            this.stmt = new Output();
        } else if (Parser.scanner.currentToken() == Core.IF) {
            this.stmt = new If();
        } else if (Parser.scanner.currentToken() == Core.WHILE) {
            this.stmt = new Loop();
        } else if (Parser.scanner.currentToken() == Core.INT) {
            this.stmt = new Decl();
        } else if (Parser.scanner.currentToken() == Core.BEGIN) {
            this.stmt = new FuncCall();
        } else if (Parser.scanner.currentToken() == Core.DEFINE) {
            this.stmt = new NewDecl();

        }

        else {
            System.out.println("ERROR: Bad start to statement: "
                    + Parser.scanner.currentToken());
            System.exit(0);
        }
        this.stmt.parse();
        if ((Parser.scanner.currentToken() != Core.END)
                && (Parser.scanner.currentToken() != Core.ENDIF)
                && (Parser.scanner.currentToken() != Core.ENDWHILE)
                && (Parser.scanner.currentToken() != Core.ELSE)
                && (Parser.scanner.currentToken() != Core.ENDFUNC)) {
            this.ss = new StmtSeq();
            this.ss.parse();
        }
    }

    void semantic() {
        this.stmt.semantic();
        if (this.ss != null) {
            this.ss.semantic();
        }
    }

    void print(int indent) {
        this.stmt.print(indent);
        if (this.ss != null) {
            this.ss.print(indent);
        }
    }

    void execute() {
        this.stmt.execute();
        if (this.ss != null) {
            this.ss.execute();
        }
    }
}