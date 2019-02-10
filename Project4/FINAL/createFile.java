
/*
*This class creates the two files .lst and/or .obj
*
*/

import java.io.FileNotFoundException;
import java.util.ArrayList;

public class createFile {
	
private String nameOfFile;
private String extension;
private java.io.File file;
private ArrayList<String> objFile;

public createFile(String nameOfFile,ArrayList<String> objFile,String extension) throws FileNotFoundException{
	
	this.extension = extension;
	this.nameOfFile = nameOfFile;
	nameOfFile = nameOfFile + extension;
	file = new java.io.File(nameOfFile);
	this.objFile = objFile;
	create();
	
	
}

private void create() throws FileNotFoundException
{
	try(java.io.PrintWriter output = new java.io.PrintWriter(file);)
	{
		for(int i = 0; i < objFile.size(); i++)
		{
			output.println(objFile.get(i).toUpperCase());
		}
	}
}

}
