import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;

/**
 * Scanner program (clear of Checkstyle and FindBugs warnings).
 *
 * @author Xin Zhao
 */
class Scanner {
    char rawToken;
    int intToken;
    Core name = Core.ERROR;
    BufferedReader reader = null;
    FileReader inputFile;
    String keywordOrVariable;
    int constValue;

    // Constructor should open the file and find the first token
    Scanner(String filename) {

        try {
            //   this.inputFile = new FileReader(filename);
            this.reader = new BufferedReader(new FileReader(filename));
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        //  this.reader = new BufferedReader(this.inputFile);

 /*
        try {
            //this.rawToken = (char)this.reader.read();
           // this.rawToken = (char) this.intToken;
        } catch (IOException e) {
            e.printStackTrace();
        }
*/
 	nextToken();
    }
    // currentToken should return the current token

    public Core currentToken() {
        return this.name;
    }

    // nextToken should advance the scanner to the next token
    public void nextToken() {

        //  this.reader = new BufferedReader(this.inputFile);
        try {
            this.rawToken = (char) this.reader.read();
	    while(Character.isWhitespace(this.rawToken))
	   {
  		this.rawToken = (char) this.reader.read();
           }
            //special character
            if (this.rawToken == ';') {
                this.name = Core.SEMICOLON;
            } else if (this.rawToken == '(') {
                this.name = Core.LPAREN;
            } else if (this.rawToken == ')') {
                this.name = Core.RPAREN;
            } else if (this.rawToken == ',') {
                this.name = Core.COMMA;
            } else if (this.rawToken == '!') {
                this.name = Core.NEGATION;
            } else if (this.rawToken == '+') {
                this.name = Core.ADD;
            } else if (this.rawToken == '-') {
                this.name = Core.SUB;
            } else if (this.rawToken == '*') {
                this.name = Core.MULT;
            } else if (this.rawToken == '=') {
		this.reader.mark(1);
                if (this.reader.read() == '=') {
                    this.name = Core.EQUAL;
                } else {
                    this.name = Core.ASSIGN;
		     this.reader.reset();
                }
            } else if (this.rawToken == '<') {
		this.reader.mark(1);
                if (this.reader.read() == '=') {
                    this.name = Core.LESSEQUAL;
                } else {
                    this.name = Core.LESS;
		      this.reader.reset();
                }
            }
		else if(this.rawToken=='?')
		{
		  this.name = Core.ERROR;
                 System.out.println("Error is '?' in file.");	
		}
		else if(this.rawToken=='%')
		{
		  this.name = Core.ERROR;
                 System.out.println("Error is '%' in file.");	
		}
		else if(this.rawToken=='_')
		{
		  this.name = Core.ERROR;
                 System.out.println("Error is '_' in file.");	
		}
		else if(this.rawToken==':')
		{
		  this.name = Core.ERROR;
                 System.out.println("Error is ':' in file.");
		}
		else if(this.rawToken=='$')
		{
		  this.name = Core.ERROR;
                 System.out.println("Error is '$' in file.");
		}
//JUST ADD
/**
            else if(this.rawToken==EOF)
            {
 		this.name=Core.EOF;

            }
*/
	   
            //CONST
            else if (Character.isDigit(this.rawToken)) {
               // this.constValue = this.rawToken;
                //  this.constValue = Integer.parseInt(String.valueOf(this.rawToken));
		this.constValue=0;
		// System.out.println(this.rawToken);
		//this.constValue = this.rawToken-'0';
		 //this.constValue =Character.getNumericValue(this.rawToken);
                while (Character.isDigit(this.rawToken)) {
                    this.constValue = this.constValue * 10 + Integer.parseInt(String.valueOf(this.rawToken));
		 this.reader.mark(1);//just add
                    this.rawToken=(char)this.reader.read();
		   if(!Character.isDigit(this.rawToken)) //just add
			{
			  this.reader.reset();   //just add
			}
                }

                if (this.constValue < 1023&&this.constValue >=0) {

		   // System.out.print("constant value is"+constValue);
                    this.name = Core.CONST;


                } else {
		    System.out.println("Error is constant too large in file.");
                    this.name = Core.ERROR;


                }
            }

            //Keyword
            else if (Character.isLetter(this.rawToken)) {
                this.keywordOrVariable ="";
                char x = this.rawToken;
                while (Character.isLetter(x) || Character.isDigit(x)) {
                    //  keywordOrVariable=keywordOrVariable+ Character.toString(this.rawToken);
 		   this.keywordOrVariable = this.keywordOrVariable + x;
                    try {
			this.reader.mark(1);//just add
                        x = (char) this.reader.read();
			if( (!Character.isLetter(x)) && (!Character.isDigit(x)) )
 				{
			      this.reader.reset();
				}
                    } catch (IOException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }
                if (this.keywordOrVariable.equals("program") ) {
                    this.name = Core.PROGRAM;
                } else if (this.keywordOrVariable.equals("begin")) {
                    this.name = Core.BEGIN;
                } else if (this.keywordOrVariable.equals("end")) {
                    this.name = Core.END;
                } else if (this.keywordOrVariable.equals("new")) {
                    this.name = Core.NEW;
                } else if (this.keywordOrVariable.equals("int")) {
                    this.name = Core.INT;
                } else if (this.keywordOrVariable.equals("define")) {
                    this.name = Core.DEFINE;
                } else if (this.keywordOrVariable.equals("endfunc")) {
                    this.name = Core.ENDFUNC;
                } else if (this.keywordOrVariable.equals("class")) {
                    this.name = Core.CLASS;
                } else if (this.keywordOrVariable.equals("extends")) {
                    this.name = Core.EXTENDS;
                } else if (this.keywordOrVariable.equals("endclass")) {
                    this.name = Core.ENDCLASS;
                } else if (this.keywordOrVariable.equals("if")) {
                    this.name = Core.IF;
                } else if (this.keywordOrVariable.equals("then")) {
                    this.name = Core.THEN;
                } else if (this.keywordOrVariable.equals("else")) {
                    this.name = Core.ELSE;
                } else if (this.keywordOrVariable.equals("while")) {
                    this.name = Core.WHILE;
                } else if (this.keywordOrVariable.equals("endwhile")) {
                    this.name = Core.ENDWHILE;
                } else if (this.keywordOrVariable.equals("endif")) {
                    this.name = Core.ENDIF;
                } else if (this.keywordOrVariable.equals("or")) {
                    this.name = Core.OR;
                } else if (this.keywordOrVariable.equals("input")) {
                    this.name = Core.INPUT;
                } else if (this.keywordOrVariable.equals("output")) {
                    this.name = Core.OUTPUT;
                }

                else {
                    this.name = Core.ID;//ID
		    
                }

            }

	   else
	  {

               // System.out.println(this.rawToken);
                //System.out.println("ERROR is "+ this.raw.Token+ " in file");
		this.name = Core.ERROR;
            }

        } 
	catch (Exception e) {

        }

    }

    // If the current token is ID, return the string value of the identifier
    // Otherwise, return value does not matter
    public String getID() {

        return this.keywordOrVariable;

    }

    // If the current token is CONST, return the numerical value of the constant
    // Otherwise, return value does not matter
    public int getCONST() {

        return this.constValue;
    }

}
