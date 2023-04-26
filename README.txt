THIS IS THE README FILE FOR Project 5.

1. Student Name: Xin Zhao

2. All files that I am submitting:

(1)Main.java: execute the scanner and parser.
(2)Scanner.java: scanner implementation to generate the tokens for the input file.
(3)Prog.java: parse and execute overall structure of a program.
(4)Assign.java: parse and execute the assign statement.
(5)Cmpr.java: parse and execute the LESS, LESSEQUAL, EQUAL comparison between expression. 
(6)tester.sh: test the project.
(7)Cond.java: parse and execute the condition statement.
(8)Core.java: score type of tokens that Core language has.
(9)Decl.java: parse and execute a single declaration separated by semicolon.
(10) DeclSeq.java: parse and execute all declarations in a program.
(11)Expr.java: parse and execute expression. 
(12)Factor.java:parse and execute identifier or constant or (expression).
(13)IdList.java:parse and execute the identifier list separated by semicolon.
(14)If.java: parse and execute the if statement of Core language.
(15)In.java: parse and execute the input statement of Core language.
(16)Loop.java: parse and execute the while statement of Core language.
(17) Out.java: parse and execute the output statement of of Core language.
(18)Stmt.java: parse and execute statement sequence of a program. 
(19)StmtSeq..java: parse and execute statement of a program.
(20)Term.java:parse and execute multiplication of a program.
(21)Parser.java:helper method for the semantic checks.
(22)Execute.java:helper method for the execute.
(23)Id.java:parse and execute the identifier.
(24)FuncDecl.java:parse and execute function declaration.
(25)Formals.java:parse and execute list of formal parameters.
(26)FuncCall.java:parse and execute function call.
(27)NewDecl.java:parse and execute declaration of reference variable.
(28)GarbageCollector.java:parse and execute garbage collector.
(29)testerNoGC.sh: test the project without GC.

3.Special features or comments on my project: 
Answer:This project’s implement garbage collector.

4.A description of the overall design of the interpreter, in particular how call stack is implemented. 
Answer: 
I generate the interpreter by add reference variable and implement garbage collector when call functions or if, loop structure. The structure of Call stack is HashMap<String, Stack<Integer>>. Call stack stores the name of variable as as string, and the index position the variable as Stack<Integer> in the heap. When define a reference variable, we push the name of variable as String and null to the stack. New reference variable and statement like “x=define y” change the call stack value of x to the position of heap.
I use the project4 provided by professor and add an execute function to each file. 

5.A brief description of how you tested the interpreter and a list of known remaining bugs.
I test the interpreter by print the number of reference that the string has to check whether I got the right token by using System.out.println(countReference.get(oldPositionInHeap)); from the external structure to internal. I debug the test case one by one.
Known remaining bugs: none.





