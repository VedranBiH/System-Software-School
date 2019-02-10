

import java.util.*;
/*Project 3 Pass 1
 * Author:Vedran Pehlivanovic
 * Date:03/25/2015
 * 
 * This program is pass 1 of the assembler. It integrates the hash table from project 1 by storing mneumoics and
 * their formats. Varies methods are used to convert Strings into integers and back into hex strings
 * Checks for literals and errors
 * 
 */


public class project3 {
	


   public static void main(String[] args) throws Exception {
	   
   hashTable table = new hashTable();
   hashTable table1 = new hashTable(1);
   hashTable table2 = new hashTable(2);  
   storeStuff(table,table1,table2);
   	
   }
   
   public static void storeStuff(hashTable table1,hashTable table3,hashTable table4) throws Exception
   {
	   
	   
	   
	   java.io.File table = new java.io.File("file1.txt");
	   
	   Scanner input2 = new Scanner(table);
	   
	   ArrayList<String> reader = new ArrayList<String>();
	   
	   ArrayList<String> label = new ArrayList<String>();
	   
	   ArrayList<String> mneumoics = new ArrayList<String>();
	   
	   ArrayList<String> operands = new ArrayList<String>();
	   
	   ArrayList<String> address = new ArrayList<String>();
	   
	   ArrayList<String> LTORGString = new ArrayList<String>();
	   
	   ArrayList<String> LTORGBytes= new ArrayList<String>();
	   
	   ArrayList<String> symTable = new ArrayList<String>();
	   
	   boolean control;
	   int i = 0;
	   int a = 0;
	   int m = 0;
	   int o = 0;
	   int l = 0;
	   int LTORG = 0;
	   int literal = 0;
	   int append = 0;
	   boolean LTORGcontrol = false;
	   int LTORGend = 0;
	   boolean LTORGinvalid = false;
	   int mulLTORG = 1;
	   
	   while(input2.hasNextLine())
	   {
		   
		   
		   control = true;
		   reader.add(input2.nextLine()); 
		   while(control)
		   {	
			   //if the line starts with a "." just print it and end the loop
			   if(reader.get(i).startsWith("."))
			   	{
				   System.out.println(reader.get(i));
				   control = false;
				   
			   
			   	}
			   //if the line contains "START" 
			   else if(reader.get(i).contains("START"))
			   {
				   //store the address
				   address.add(reader.get(i).substring(18).trim());
				   
				   a++;
				   
				   System.out.print(address.get(a-1));
				   
				   System.out.println("\t" + reader.get(i));
				   
				   //if there is no space in the label column store a label
				if(!reader.get(i).startsWith(" "))
				{
				   label.add(reader.get(i).substring(0,9).trim());
				   
				   table3.setSymTable(label.get(l) + " "  + address.get(a-1));
				   l++;
				   
				}
					control = false;
			   }
			   //if a blank line skip it
			   else if(reader.get(i).startsWith(" ") && reader.get(i).length() == 1)
			   {
				   control = false;
			   }
			   else
			   {
				   //store operands
				   if(reader.get(i).length() > 28)
				   {	if(reader.get(i).substring(18, 28).trim().contains(","))
				   		{
					   		String[] tokens = reader.get(i).substring(18,29).trim().split(",");
					   		operands.add(tokens[0]);
					   		o++;
				   		}
				   		else
				   			{
				   			operands.add(reader.get(i).substring(18,28).trim());
				   			o++;
				   			}
				   }
				   else if(reader.get(i).length() > 15)
				   {	
					   
					   if(reader.get(i).substring(18).trim().contains(","))
					   {
						   String[] tokens = reader.get(i).substring(18).trim().split(",");
						   operands.add(tokens[0]);
						   o++;
					   }
					   	else
					   	{
						   operands.add(reader.get(i).substring(18).trim());
						   o++;
					   	}
				   }
				   
				   //store labels
				   if(reader.get(i).length() > 8 && !reader.get(i).startsWith(" "))
				   {
					   label.add(reader.get(i).substring(0,8).trim());
					   l++;
					   table3.setSymTable(label.get(l-1) + " " + address.get(a-1));
				   }
				   
				   //store mneumoics
				   if(reader.get(i).length() > 17)
				   {
					   mneumoics.add(reader.get(i).substring(10,16).trim());
					   
				   }
				   else
				   {
					   mneumoics.add(reader.get(i).substring(10).trim());
				   }
				   

				   //store literals
				   if(operands.get(o-1).startsWith("=") && reader.get(i).length() > 15 )
				   {
					   if(operands.get(o-1).contains(","))
					   {
						   String[] tokens = operands.get(o-1).split(",");
					   
					   		if(tokens[0].charAt(2) != '\'' || tokens[0].charAt(tokens[0].length()-1) != '\'')
					   		{
				   			
					   			LTORGcontrol = true;
					   			LTORGinvalid = true;
					   			LTORGBytes.add("0");
					   			LTORGString.add(tokens[0]);
					   			literal++;
					   			LTORG++;
					   		}
					   			else
			   						{
				   						LTORGBytes.add(LTORG(tokens[0]));
				   						LTORGString.add(tokens[0]);
				   						literal++;
				   						LTORG++;
				   						LTORGcontrol = true;
			   						}
					   }
					   else if(operands.get(o-1).charAt(2) != '\'' || operands.get(o-1).charAt(operands.get(o-1).length()-1) != '\'')
							   {
						   			
						   			LTORGcontrol = true;
						   			LTORGinvalid = true;
						   			LTORGBytes.add("0");
						   			LTORGString.add(operands.get(o-1));
						   			literal++;
						   			LTORG++;
							   }
					   else
					   {
					   LTORGBytes.add(LTORG(operands.get(o-1)));
					   LTORGString.add(operands.get(o-1));
					   literal++;
					   LTORG++;
					   LTORGcontrol = true;
					   }
				   }
				   
				   //format the Strings into hex integers and add them to the address ArrayList
				   
				   address.add(format(address.get(a-1),mneumoics.get(m),table1,operands.get(o-1)));
				   
  
				   System.out.print(address.get(a-1));
				   
				   a++;
				   
				   System.out.println("\t" + reader.get(i));
				   
				   /*if(table3.getNoLabel() == true)
				   {
					  System.out.println("********** ERROR: Operand not found in symbol table"); 
				   }*/
				   if(LTORGinvalid == true)
				   {
					   System.out.println("********** WARNING: quote missing in operand field");
					   LTORGinvalid = false;
				   }
				  if(table3.getDblLabel() == true)
				   {
					  System.out.println("********** ERROR: Duplicate label found"); 
					  table3.setDblLabel(false);
					  table3.setRemove();
				   }
				   
				   //check if the mnemoic is valid
				   if(table1.isThere(mneumoics.get(m)) == false)
					   System.out.println("********** ERROR: Unsupported opcode found");
				    m++;
				    
				    //print the literals if mneumoics is equal to LTORG
				   if(mneumoics.get(m -1).equals("LTORG"))
				   {
					   LTORGcontrol = false;
					   for(int v = mulLTORG - 1; v < LTORGString.size(); v++)
					   {
						   if(LTORGString.get(v).charAt(2) != '\'' || LTORGString.get(v).charAt(LTORGString.get(v).length()-1) != '\'')
						   {
							   System.out.println(address.get(a-1) + "\t" + LTORGString.get(v)+ "   BYTE\t   " + LTORGString.get(v).substring(1));
							   System.out.println("********** ERROR: Invalid hex digit found in the operand");
						   }
						   //check if the hex is a even number
					   else if(oddOrEven(LTORGString.get(v)) == true)
						   {
						   address.add(format(address.get(a-1),LTORGBytes.get(v)));
						   label.add(LTORGString.get(v));
						   l++;
						   table3.setSymTable(label.get(l-1) + " " + address.get(a-1));
						   System.out.println(address.get(a-1) + "\t" +LTORGString.get(v) + "   BYTE\t   " + LTORGString.get(v).substring(1));
						   a++;
						   LTORGend++;
						   mulLTORG++;
						   }
						   else if(oddOrEven(LTORGString.get(v)) == false)
						   {
							   String zero = "0";
							   address.add(format(address.get(a-1),zero));
							   System.out.println(address.get(a-1) + "\t" +LTORGString.get(v) + "   BYTE\t   " + LTORGString.get(v));
							   System.out.println("********** ERROR: Odd number of X bytes found in operand field ");
							   a++;
							   LTORGend++;
							   mulLTORG++;
							   
						   }
					   }   
	   
				   }
				   //if there is no LTORG mneumoic print the literals after the END mneumoic
				   else if(LTORGcontrol == true)
				   {
					   if(mneumoics.get(m-1).equals("END"))
					   {
						   for(int y = LTORGend; y < LTORGString.size(); y++)
						   {
							   if(oddOrEven(LTORGString.get(y)) == true)
							   {
								   address.add(format(address.get(a-1),LTORGBytes.get(y)));
								   label.add(LTORGString.get(y));
								   l++;
								   table3.setSymTable(label.get(l-1) + " " + address.get(a-1));
								   System.out.println(address.get(a-1) +"\t" +LTORGString.get(y) + "   BYTE\t   " + LTORGString.get(y));
								   a++;
							   }
							   	else if(oddOrEven(LTORGString.get(y)) == false)
							   		{
							   			String zero = "0";
							   			address.add(format(address.get(a-1),zero));
							   			System.out.println(address.get(a-1) + "\t" +LTORGString.get(y) + "   BYTE\t   " + LTORGString.get(y));
							   			System.out.println("********** ERROR: Odd number of X bytes found in operand field ");
							   			a++;
							   		}   
						   }
					   }
				   }
				  
				  
				   control = false;
			   }
		   
   }
		   
		   i++;
}
	   
	   //display the table
	 table3.displayTable();
   }//end storeStuff
   
/*method parameters are String address, String mn, a hashTable, and String reswString
 * it calculates the hex address by calling the hashTable and get the format
 * converts everything into hex strings
*/
public static String format(String address, String mn, hashTable table2,String reswString)
{
	String resw = "3";
	
	
	if(mn.equals("RESW"))
	{
		  int num = Integer.parseInt(address,16);
		  int otherNum = Integer.parseInt(reswString);
		  int reswNum = Integer.parseInt(resw);
		  
		  int finalNum = (otherNum * reswNum) + num;
		  return hex(finalNum);
	}
	else if(mn.equals("RESB"))
	{
		int num = Integer.parseInt(address,16);
		int otherNum = Integer.parseInt(reswString);
		int finalNum = num + otherNum;
		return hex(finalNum);
	}
	else
	{	
		int hexFormat = table2.getFormat(mn);
		int num = Integer.parseInt(address,16);
		int finalnum1 = hexFormat + num;
		return hex(finalnum1);
	}
}

/*Takes in string address, string mn
 * returns it formatted into a string
 * formats everything into hex strings
 */
public static String format(String address, String mn)
{
	int hexFormat = Integer.parseInt(mn);
	int num = Integer.parseInt(address,16);
	int finalnum1 = hexFormat + num;
	return hex(finalnum1);
}

//formats the hex string into a integer
public static String hex(int dec)
{
	String hex = Integer.toHexString(dec);
	return hex;
}

/*Takes in a String Literal
 * checks if it starts with a =X or =C
 * if it starts with =X makes divides it by 2 and returns that value
 */
public static String LTORG(String literal)
{
	
	int numOfBytes;
	String hexNumOfBytes;
	if(literal.startsWith("=X"))
	{
		literal = literal.substring(3, literal.length()-1);
		numOfBytes = literal.length() / 2;
		hexNumOfBytes = Integer.toHexString(numOfBytes);
		return hexNumOfBytes;
	}
	else
	{
		literal = literal.substring(3,literal.length() -1);
		numOfBytes = literal.length();
		hexNumOfBytes = Integer.toHexString(numOfBytes);
		return hexNumOfBytes;
	}
	
}

/*
 * Checks if a literal that starts with =X is odd or even
 */
public static boolean oddOrEven(String literal)
{
	
	if(literal.startsWith("=X"))
		{
	literal = literal.substring(3,literal.length()-1);
	if(literal.length()%2 != 0)
		return false;
		
	else
		return true;
		}
	else
		return true;
	
}

}
