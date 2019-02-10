import java.io.*;
import java.util.*;



public class p1 {
	


   public static void main(String[] args) throws FileNotFoundException {
      int[] numTable = new int[50];
      String[] hashTable = new String[50];
      String[] inputData = new String[50];
   	
      java.io.File file = new java.io.File(args[0]);//Read the data from the file
      Scanner input = new Scanner(file);
       
      int key;
      int i=0;
      
      //read text file until no lines left
      while(input.hasNextLine())
      {
    	  //Store data into String array
         inputData[i] = input.nextLine();
         
         /*Split first line of data into new String array called tokens
          * if the array size is 2 we will store the data
          * if the array size is 1 we will search for the data
       	*/
         String[] tokens = inputData[i].split(" ");
       
       	//hash
         key = convertToChar(tokens[0]);	
       
         if(tokens.length == 2)		
         {	
        	 
        	 
        	 boolean control = true;
        	 //while not hashTable is not empty (linear probing)
            while(hashTable[key] != null)
            {
            	 if(hashTable[key].equals(tokens[0]))
                 {
                 	System.out.println("ERROR " + tokens[0] + " already exists at location " + key);
                 	control = false;
                 }
               key++;
               key %= 50;
            
            }
            
           if(control == true){
            hashTable[key]= tokens[0];
            numTable[key] = Integer.parseInt(tokens[1]);
            System.out.println(tokens[0] +" " + numTable[key] + " was stored in location " + key);
           }	
         }
         else
         {
            boolean control = false;
            // look until you find it or you find a null
            while((hashTable[key]!=null) && !hashTable[key].equals(tokens[0]))
            {	
               key++;
               key %= 50;
            
               
            }
            //check if it was a null (give an error)
            if(hashTable[key] == null)
            {
               System.out.println("ERROR " + tokens[0] + " not found");
            }
            
            
            else //process the found thing
               System.out.println(tokens[0] + " was found at location " + key + " with the value of " + 
                  numTable[key]);
         
         
         }
       	
      
         i++;
      	
      }
      input.close();    
   
   	
   }

   public static int hashKey(int code)
   {
      int arraySize = 50;
      code = code % arraySize;
      return code;
   
   }
   public static int convertToChar(String inputData)
   {
      int hashFcn;
      int number = 0;
      char[] ascii = inputData.toCharArray();
      for(int i = 0; i < ascii.length; i++)
      {
         number = (int)ascii[i] + number;
      }
   	
      hashFcn = 	hashKey(number);
   
      return hashFcn;
   	
   }

}
