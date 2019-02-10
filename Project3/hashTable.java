

import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Scanner;

public class hashTable {

private boolean dblLabel = false;
private boolean noLabel = false;
private String[] symNumTable = new String[211];	
private int[] numTable = new int[211];
private String[] hashTable = new String[211];
private int key;
private String[] tokens;
ArrayList<String> symTable = new ArrayList<String>();
private int a = 0;	
public hashTable() throws Exception
{
	
	readFile();
}

public hashTable(int z)
{
	
}


private void readFile() throws Exception
{
	int i = 0;
	String[] inputData = new String[211];

	java.io.File file = new java.io.File("file.txt");//Read the data from the file
	Scanner input = new Scanner(file);

	  while(input.hasNextLine())
      {
    	  //Store data into String array
         inputData[i] = input.nextLine();
         
         /*Split first line of data into new String array called tokens
          * if the array size is 2 we will store the data
          * if the array size is 1 we will search for the data
       	*/
         
         tokens = inputData[i].split(" ");
         key = convertToChar(tokens[0]);
         if(tokens.length == 2)
         doStuff();
         else
        	isThere(tokens[0]);
      }
	  i++;
}

private void doStuff(int z)
{

	int key;
    
    
    /*Split first line of data into new String array called tokens
     * if the array size is 2 we will store the data
     * if the array size is 1 we will search for the data
  	*/
    String[] tokens = symTable.get(a).split(" ");
  
  	//hash
    key = convertToChar(tokens[0]);	
  dblLabel = false;
    if(tokens.length == 2)		
    {	
   	 
   	 
   	 boolean control = true;
   	 //while not hashTable is not empty (linear probing)
       while(hashTable[key] != null)
       {
       	 if(hashTable[key].equals(tokens[0]))
            {
       		   dblLabel = true;
            	control = false;
            }
          key++;
          key %= 211;
       
       }
       
      if(control == true){
       hashTable[key]= tokens[0];
       symNumTable[key] = tokens[1];
       dblLabel = false;
       noLabel = false;
      }	
    }
    else
    {
       boolean control = false;
       // look until you find it or you find a null
       while((hashTable[key]!=null) && !hashTable[key].equals(tokens[0]))
       {	
          key++;
          key %= 211;
       
        }
       //check if it was a null (give an error)
       if(hashTable[key] == null)
       {
     	  noLabel = true;
       }
       
       
       else //process the found thing
       {
          noLabel = false;
       	 dblLabel = false;
       }
    
    }
}

private int hashKey(int code)
{
   int arraySize = 211;
   code = code % arraySize;
   return code;

}//end hashKey

private int convertToChar(String inputData)
{
   int hashFcn;
   int number = 0;
   char[] ascii = inputData.toCharArray();
   for(int i = 0; i < ascii.length; i++)
   {
      number = (int)ascii[i] + number;
   }
	
   hashFcn = hashKey(number);

   return hashFcn;
	
}//end convertToChar

private void doStuff()
{
	
		 while(hashTable[key] != null)
         {
         
            key++;
            key %= 211;
         
         }
         
        
         hashTable[key]= tokens[0];
         numTable[key] = Integer.parseInt(tokens[1]);

       
}

	
public boolean isThere(String check)
{
	int newKey = convertToChar(check);
    // look until you find it or you find a null
    while((hashTable[newKey]!=null))
    {	
    	if(hashTable[newKey].equals(check))
    			return true;
    	
       newKey++;
       newKey %= 211;
    
       
    }
       return false;
 
 
 
}

public boolean doubleLabel(String check)
{
	int newKey1 = convertToChar(check);
	boolean control = true;
	while(control)
	{
	   while(hashTable[newKey1] != null)
       {
       	 if(hashTable[newKey1].equals(check))
            {
       		 	control = false;
       		 	return true;
            	
            }
          newKey1++;
          newKey1 %= 211;
       }
	}
	   return false;
}

public int getFormat(String op)
{
	int format;
	int newKey = convertToChar(op);
	 while((hashTable[newKey]!=null) && !hashTable[newKey].equals(op))
	    {	
	       newKey++;
	       newKey %= 211;
	    }
format = numTable[newKey];
return format;
}

public void setSymTable(String labelAddress)
	{
		
		int z = 2;
		symTable.add(labelAddress);
		doStuff(z);
		a++;
	}

public boolean isEmpty()
	{
		if(!symTable.isEmpty())
			return true;
		else
		   return false;
	}

public void displayTable()
	{
		int newKey;
		String table ="Table";
		String label ="Label";
		String address ="Address";
		System.out.format("\n\n\n%-10s%-10s%-10s\n",table,label,address);
		
		for(int i = 0; i < symTable.size(); i++)
		{
			
			String[] tokens1 = symTable.get(i).split(" ");
			newKey = convertToChar(tokens1[0]);
			while(symNumTable[newKey] != null )
			{
				newKey++;
				newKey %= 211;
			}
			System.out.format("%-10s%-10s%-10s\n",newKey,tokens1[0],tokens1[1]);
		}
		
		
	}

public boolean getNoLabel()
{
	return noLabel;
}

public boolean getDblLabel()
{
	return dblLabel;
}

public void setDblLabel(boolean controller)
{
	dblLabel = controller;
}
public void setRemove()
{
	symTable.remove(a-1);
	a--;
}

}//end hashTable

