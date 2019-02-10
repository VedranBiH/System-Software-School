
/*
This program creates the .lst section
*/
import java.io.FileNotFoundException;
import java.util.ArrayList;

public class lstFile {
	
	private ArrayList<String> address = new ArrayList<String>();
	private ArrayList<String> operands = new ArrayList<String>();
	private ArrayList<String> mneumoics = new ArrayList<String>();
	private ArrayList<String> opCodes = new ArrayList<String>();
	private ArrayList<String> lstfiles = new ArrayList<String>();
	private ArrayList<String> reader = new ArrayList<String>();
	private ArrayList<String> LTORGString = new ArrayList<String>();
	private String args;
	public lstFile(ArrayList<String> address, ArrayList<String> operands, ArrayList<String> mneumoics, ArrayList<String> opCodes,ArrayList<String> reader,String args,ArrayList<String> LTORGString) throws FileNotFoundException
	{
		this.args = args;
		this.address = address;
		this.operands = operands;
		this.mneumoics = mneumoics;
		this.opCodes = opCodes;
		this.reader = reader;
		this.LTORGString = LTORGString;
		
		createLstFiles();
		createFile lstFile = new createFile(args,lstfiles,".lst");
	}
	
	
	private void createLstFiles()
	{
		int number = 1;
		//System.out.println("\n\n");
	address.add(1, address.get(0));	
	int m = 0;
	int o = 0;
	int a = 0;
	String holder = "";
		
		for(int i = 0; i < reader.size(); i++)
		{
			String lineNumber = Integer.toString(number);
			
			
			
			if(reader.get(i).startsWith("."))
					{
						lstfiles.add(reader.get(i));
					}
			else if(reader.get(i).contains(mneumoics.get(m)) && check(mneumoics.get(m)) == false)
			{
				while(lineNumber.length() < 3)
				{
					lineNumber = "0" + lineNumber;
				}
				if(!opCodes.get(o).contains("NULLERINO") && !opCodes.get(o).contains("ERROR") && !opCodes.get(o).contains("vedran") && !opCodes.get(o).contains("LTORG"))
				{
				lstfiles.add(String.format("%-5s",lineNumber) + String.format("%-5s",address.get(a).toUpperCase()) + String.format("%-10s",opCodes.get(o)) 
				+ String.format("%-15s",reader.get(i)));
				m++;
				o++;
				a++;
				number++;
				}
				else if(opCodes.get(o).contains("NULLERINO"))
				{	
					
					lstfiles.add(String.format("%-5s",lineNumber)  + String.format("%-5s",address.get(a).toUpperCase()) 
							+ String.format("%-10s",holder) + String.format("%-15s",reader.get(i)));
					lstfiles.add(String.format("%-5s", "********** ERROR: Unsupported opcode found in statement"));
					
					o++;
					m++;
					a++;
					number++;
				}
				else if(opCodes.get(o).contains("Error"))
				{
					
					lstfiles.add(String.format("%-5s",lineNumber)  + String.format("%-5s",address.get(a).toUpperCase()) 
							+ String.format("%-10s",holder) + String.format("%-15s",reader.get(i)));
					lstfiles.add(String.format("%-5s", "********** ERROR: Displacement out of range for PC relative"));
					o++;
					m++;
					number++;
					a++;
				}
				else if(opCodes.get(o).contains("vedran"))
				{
					lstfiles.add(String.format("%-5s",lineNumber)  + String.format("%-5s",address.get(a).toUpperCase()) 
							+ String.format("%-10s",holder) + String.format("%-15s",reader.get(i)));
					lstfiles.add(String.format("%-5s", "********** ERROR: Operand not found in symbol table"));
					o++;
					m++;
					number++;
					a++;
				}
				
				else
				{
					a++;
					o++;
					m++;
					number++;
				}
			}
			else if(reader.get(i).contains("LTORG"))
					{
					
					int LTORGNumber = 1;
					String LTORGLineNumber = Integer.toString(LTORGNumber);
					while(LTORGLineNumber.length() < 2)
					{
						LTORGLineNumber = "0" + LTORGLineNumber;
					}
					
					LTORGLineNumber = "+" + LTORGLineNumber + "+";
					
					while(lineNumber.length() < 3)
						{
							lineNumber = "0" + lineNumber;
						}
					
					
						lstfiles.add(String.format("%-5s", lineNumber) + String.format("%-15s", address.get(a).toUpperCase()) 
								+ String.format("%-1s",reader.get(i)));
						a++;
						for(int y = 0; y < LTORGString.size(); y++)
						{
							if(project4.oddOrEven(LTORGString.get(y)) == true)
							{
								lstfiles.add(String.format("%-5s",LTORGLineNumber) + String.format("%-5s",address.get(a).toUpperCase()) + String.format("%-10s",opCodes.get(o)) + String.format("%-10s",LTORGString.get(y)) 
								+ String.format("%-5s","BYTE") + String.format("%11s",LTORGString.get(y)));
								a++;
								o++;
								LTORGNumber++;
							}
							else
							{
								a++;
								lstfiles.add(String.format("%-5s",LTORGLineNumber) + String.format("%-5s",address.get(a+1).toUpperCase()) + String.format("%-10s",opCodes.get(o)) + String.format("%-10s",LTORGString.get(y)) 
										+ String.format("%-5s","BYTE") + String.format("%11s",LTORGString.get(y)));
								lstfiles.add("********** ERROR: Odd number of X bytes found in operand field ");
							}
						}
						m++;
						number++;
					}
			else{
				while(lineNumber.length() < 3)
				{
					lineNumber = "0" + lineNumber;
				}
			
				lstfiles.add(String.format("%-5s",lineNumber)  + String.format("%-5s",address.get(a).toUpperCase()) 
				+ String.format("%-10s",holder) + String.format("%-15s",reader.get(i)));
				m++;
				number++;
				a++;
			}
		
		}
	}
	
	private boolean check(String checker)
	{
		switch(checker){
		case "BASE":
		case "START":
		case "RESB":
		case "RESW":
		case "END":
		case "LTORG":
			break;
			default:
				return false;
		}
		return true;
	}
	
	public void display()
	{
		for(int i = 0; i < lstfiles.size(); i++ )
		{
			System.out.println(lstfiles.get(i));
		}
	}

}
