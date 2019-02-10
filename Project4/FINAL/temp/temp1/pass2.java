
/*
This class figures out the opcodes for pass2
*/

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class pass2 {
	
private hashTable symTable;
private ArrayList<String> operands = new ArrayList<String>();
private ArrayList<String> NI = new ArrayList<String>();
private ArrayList<String> XBPE = new ArrayList<String>();
private ArrayList<String> newAddress = new ArrayList<String>();
private ArrayList<String> mneumoics = new ArrayList<String>();
private ArrayList<String> address = new ArrayList<String>();
private ArrayList<String> reader = new ArrayList<String>();
private ArrayList<String> codes = new ArrayList<String>();
private ArrayList<String> LTORGString = new ArrayList<String>();
private hashTable table;
private int a = 0;
private boolean baseChecker = false;
private String baseAddress;
private boolean PCOutOfRange;
private String endAddress;
private String args;
private lstFile lst;
private boolean doNotCreateFile = false;


public pass2(hashTable symTable, ArrayList<String> mneumoics,hashTable table,ArrayList<String> operands,ArrayList<String> address,String args,ArrayList<String> reader,ArrayList<String> LTORGString) throws FileNotFoundException
	{
	
		this.address = address;
		this.table = table;
		this.mneumoics = mneumoics;
		this.symTable = symTable;
		this.operands = operands;
		this.args = args;
		this.reader = reader;
		this.LTORGString = LTORGString;
		checkForBase();
		findEndAddress();
		figureOutAddress();
		if(doNotCreateFile == false)
		{
		createFile obj = new createFile(args,newAddress,".obj");
		}
		lst = new lstFile(address,operands,mneumoics,codes,reader,args,LTORGString);
		
	}

private String figureOutNI(String mneumoics,String operands)
{
		if(table.isThere(mneumoics) == true)
		{
		String holder;
		
		if(operands.contains("@"))
				{
					String op2 = "2";
					holder = project4.format(table.getOpcode(mneumoics), op2);
				}
		else if(operands.contains("#"))
				{
					String op1 = "1";
					holder = project4.format(table.getOpcode(mneumoics), op1);
				}
		else
				{
					String op3 = "3";
					holder = project4.format(table.getOpcode(mneumoics), op3);
				}
		if(holder.length() == 1)
			return ("0" + holder);
		else
			return (holder);
		}	
		
		else
		{
			doNotCreateFile = true;
			return "NULLERINO";
		}
}

private String figureOutXBPE(String mneumoics,String operands)
{
		if(check(mneumoics) == false)
		{
			if(mneumoics.contains("+"))
			{
				if(operands.contains(","))
				{
					return "9";
				}
				else
					return "1";
			}
			else if(PCOutOfRange == true)
			{
				if(baseChecker = true)
				{
					return "C";
				}
				else
				return "4";
			}
			else
				return "2";
		}
		else
			return null;
}

private void figureOutAddress()
{	
	int o = 0;
	
	String blah = "";
	if(address.get(0).length() < 6)
	{
		 blah = address.get(0);
		while(blah.length()< 6)
		{
			blah = "0" + blah;
		}
	}
	newAddress.add(blah);
	newAddress.add("000000");
	
	for(int i = 0; i < mneumoics.size();i++)
	{
		PCOutOfRange = false;
		
		if(mneumoics.get(i).equals("END"))
		{
			break;
		}
		if(mneumoics.get(i).equals("RSUB"))
		{
			newAddress.add("4F0000");
			codes.add("4F0000");
			
		}
		
		else if(table.getFormat(mneumoics.get(i)) == 2)
		{
			String opCode = table.getOpcode(mneumoics.get(i));
			String[] tokens = operands.get(i).split(",");
			String lastPart = Integer.toString(table.getFormat(tokens[0])) + Integer.toString(table.getFormat(tokens[1]));
			newAddress.add(opCode+lastPart);
			codes.add(opCode + lastPart);
		}
		else if(operands.get(i).contains("#") && operands.get(i).substring(1).matches(".*[a-zA-Z].*") == false)
		{
			String checker = operands.get(i).substring(1);
			
				checker = operands.get(i).substring(1);
				int hexChecker = Integer.parseInt(checker);
				checker = Integer.toHexString(hexChecker);
				while(checker.length() < 4)
				{
					checker = "0" + checker;
				}
				newAddress.add(figureOutNI(mneumoics.get(i),operands.get(o)) + checker);
				codes.add(figureOutNI(mneumoics.get(i),operands.get(o)) + checker);
			
		}
		
		else if(mneumoics.get(i).equals("WORD"))
			{

				int hexConvt = Integer.parseInt(operands.get(o));
				String holder = Integer.toHexString(hexConvt);
				
				if(holder.length() < 6)
				{
					while(holder.length() < 6)
					{
						holder = "0" + holder;
					}
				}
				
				newAddress.add(holder);
				codes.add((holder.toUpperCase()));
				//a++;
			}
		else if(mneumoics.get(i).equals("RESW") || mneumoics.get(i).equals("RESB"))
		{
			newAddress.add("!");
			newAddress.add(formatREWS(address.get(a+1)));
			newAddress.add("000000");
		}
		
		else if(!(mneumoics.get(i).equals("BASE")) && !(mneumoics.get(i).equals("END")) && !(mneumoics.get(i).equals("START")) && !(mneumoics.get(i).equals("LTORG")))
		{
			if(mneumoics.get(i).contains("+"))
			{
				getAddress(operands.get(o));
				
				if(!figureOutNI(mneumoics.get(i),operands.get(o)).equals("NULLERINO"))
				{
				newAddress.add(figureOutNI(mneumoics.get(i),operands.get(o)) + 
						figureOutXBPE(mneumoics.get(i),operands.get(o)) 
						+ getAddress(operands.get(o)));
				
				
				codes.add(figureOutNI(mneumoics.get(i),operands.get(o)).toUpperCase() + 
						figureOutXBPE(mneumoics.get(i),operands.get(o)) 
						+ getAddress(operands.get(o)).toUpperCase());
				}
			}
			else
			{	
				
				if(operands.get(o).contains(","))
				{
					String[] tokens = operands.get(o).split(",");
					getPCAddress(tokens[0],address.get(a+1));
					

					if(!figureOutNI(mneumoics.get(i),tokens[0]).equals("NULLERINO") || !getPCAddress(tokens[0],address.get(a+1)).equals("ERROR"))
					{
					newAddress.add(figureOutNI(mneumoics.get(i),operands.get(o)) + 
							figureOutXBPE(mneumoics.get(i),operands.get(o)) +
							getPCAddress(tokens[0],address.get(a+1)));
					
					codes.add(figureOutNI(mneumoics.get(i),operands.get(o)).toUpperCase() + 
							figureOutXBPE(mneumoics.get(i),operands.get(o)) +
							getPCAddress(tokens[0],address.get(a+1)).toUpperCase());
					}
					
				}
				else
				{
	
				getPCAddress(operands.get(o),address.get(a+1));
				

				if(!figureOutNI(mneumoics.get(i),operands.get(o)).equals("NULLERINO") || !getPCAddress(operands.get(o),address.get(a+1)).equals("ERROR"))
				{
				newAddress.add(figureOutNI(mneumoics.get(i),operands.get(o)) + 
						figureOutXBPE(mneumoics.get(i),operands.get(o)) +
						getPCAddress(operands.get(o),address.get(a+1)));
				
				codes.add(figureOutNI(mneumoics.get(i),operands.get(o)).toUpperCase() + 
						figureOutXBPE(mneumoics.get(i),operands.get(o)) +
						getPCAddress(operands.get(o),address.get(a+1)).toUpperCase());
				}
				}
			}
			
		}
		if(mneumoics.get(i).equals("START"))
		{
			o++;
		}
		else if(mneumoics.get(i).equals("LTORG"))
		{
			for(int y = 0; y < LTORGString.size(); y++)
			{
				if(project4.oddOrEven(LTORGString.get(y)) == true)
				{
					if(LTORGString.get(y).startsWith("=C"))
					{
						newAddress.add(getCharValue(LTORGString.get(y).substring(3, LTORGString.get(y).length()-1)));
						codes.add(getCharValue(LTORGString.get(y).substring(3, LTORGString.get(y).length()-1)));
					}
					else
					{
						newAddress.add(LTORGString.get(0).substring(3, LTORGString.get(0).length()-1).trim());
						codes.add(LTORGString.get(0).substring(3, LTORGString.get(0).length()-1).trim());
					}
				}
				else
				{
					doNotCreateFile = true;
				}
			}
		}
		else
		{
			o++;
			a++;
		}
	}
	newAddress.add("!");
}


private boolean check(String checker)
{
	switch(checker){
	case "BASE":
	case "WORD":
	case "RESB":
	case "RESW":
	case "END":
	case "LTORG":
	case "START":
		break;
		default:
			return false;
	}
	return true;
}

private String getAddress(String label)
{
	String result = label.replaceAll("[^\\w\\s]","");
	for(int i = 0; i < symTable.getSymTable().size(); i++)
	{
	String[] tokens = symTable.getSymTable().get(i).split(" ");
	if(result.equals(tokens[0]))
		{
		
		return formatExtended(tokens[1]);
		
		}
	}
	return null;
}

private String getPCAddress(String label,String counter)
{
	
	String labelAddress = null;
	String address;
	if(!label.startsWith("="))
	label = label.replaceAll("[^\\w\\s]","");

	for(int i = 0; i < symTable.getSymTable().size(); i++)
	{
	String[] tokens = symTable.getSymTable().get(i).split(" ");
	if(label.equals(tokens[0]))
	{
	labelAddress = tokens[1];
	break;
	}
	else if(i ==  symTable.getSymTable().size()-1 && !label.equals(tokens[0]))
	{
      doNotCreateFile = true;
		return "vedran";
	}
	}
	int hexLabel = Integer.parseInt(labelAddress,16);
	int hexPC = Integer.parseInt(counter,16);
	int finalAddress = hexLabel - hexPC;
	if(checkForPCRange(finalAddress) == false)
	{
	address = Integer.toHexString(finalAddress);
	PCOutOfRange = false;
	return formatPC(address);
	}
	else if (baseChecker == true && checkForPCRange(finalAddress) == true)
	{
		int LabelBaseAddress = Integer.parseInt(labelAddress,16);
		int hexBase = Integer.parseInt(baseAddress,16);
		int finalBaseAddress = LabelBaseAddress - hexLabel;
		address = Integer.toHexString(finalBaseAddress);
		PCOutOfRange = true;
		return formatPC(address);
	}
	else
	{
		PCOutOfRange = false;
		return "Error";
	}
}

private boolean figureOutB(String label, String counter)
{
	String labelAddress = null;
	String address;
	
	

	for(int i = 0; i < symTable.getSymTable().size(); i++)
	{
	String[] tokens = symTable.getSymTable().get(i).split(" ");
	if(label.equals(tokens[0]))
	labelAddress = tokens[1];
	}
	int hexLabel = Integer.parseInt(labelAddress,16);
	int hexPC = Integer.parseInt(counter,16);
	int finalAddress = hexLabel - hexPC;
	if(checkForPCRange(finalAddress) == false)
	{
	address = Integer.toHexString(finalAddress);
	return false;
	}
	else if (baseChecker == true && checkForPCRange(finalAddress) == true)
	{
		int LabelBaseAddress = Integer.parseInt(labelAddress,16);
		int hexBase = Integer.parseInt(baseAddress,16);
		int finalBaseAddress = LabelBaseAddress - hexBase;
		address = Integer.toHexString(finalBaseAddress);
		return true;
	}
	else
		return false;

}

private String formatExtended(String x)
{
	if(x.length() < 5)
	{
		while(x.length()<5)
		{
			x = "0" + x;
		}
	}
	return x;
}

private String formatPC(String x)
{
	if(x.length()<3)
	{
		while(x.length()<3)
		{
			x = "0" + x;
		}
	}
	else if(x.length() > 3)
	{
		x = x.substring(x.length()-3);
	}
	return x;
}

private void checkForBase()
{
	for(int i = 0; i < mneumoics.size() ;i++)
	{
		if(mneumoics.get(i).equals("BASE"))
		{			
			baseChecker = true;
			baseAddress = getAddress(operands.get(i));
		}
	}
}

private boolean checkForPCRange(int address)
{
	int max = 2047; 
	int min = -2048;

	
	if(address > max || address < min)
	{
		return true;
	}
	else
		return false;
}

private void findEndAddress()
{

	if(reader.get(reader.size()-1).length() > 19)
	{
		endAddress = getAddress(operands.get(operands.size()-1));
		while(endAddress.length() < 6)
		{
			endAddress = "0" + endAddress;
		}
	}
	else
	{
		endAddress = address.get(0);
		if(endAddress.length() < 6)
		{
			while(endAddress.length() < 6)
			{
				endAddress = "0" + endAddress;
			}
		}
	}
	
	/*for(int i = 0; i < mneumoics.size(); i++)
	{
		if(mneumoics.get(i).equals("END"))
		{
			if(operands.get(operands.size()-1) != null)
			{
				endAddress = getAddress(operands.get(operands.size()-1));
				if(endAddress.length() < 6)
				{
					while(endAddress.length() < 6)
					{
						endAddress = "0" + endAddress;
					}
				}
			}
			else
			{
				endAddress = newAddress.get(0);
				if(endAddress.length() < 6)
				{
					while(endAddress.length() < 6)
					{
						endAddress = "0" + endAddress;
					}
				}
			}
		}
	}*/
}

public void display()
{
	//System.out.println(newAddress.toString());
	
	for(int j = newAddress.size() - 1; j < newAddress.size(); j-- )
	{
		if(newAddress.get(j).equals("000000"))
		{
			newAddress.add(j, endAddress);
			newAddress.remove(j+1);
			break;
		}
	}
	
	for(int i = 0; i < newAddress.size(); i++)
	{
		System.out.println(newAddress.get(i).toUpperCase());
	}
	
	//lst.display();
	
			
}

private String formatREWS(String x)
{
	while(x.length() < 6)
	{
		x = "0" + x;
	}
	
	return x;
}

private String getCharValue(String x)
{
	String y = "";
	char[] values = x.toCharArray();
	for(int i = 0; i < values.length; i++)
	{
		y = y + Integer.toHexString((int)values[i]);
	}
	return y;
}



}//end pass2
