import java.io.*;
import java.util.*;

public class FileIn
{
	private FileInputStream file;
	private String name;
	FileIn(String nom)	
	{
		name = nom;
		try{
		file =new FileInputStream(new File(nom));
		}catch(IOException e){};
	}
	
	public char getC()
	{
		int c;
		try{
			c = file.read();
			return (char)c;
		}catch(IOException e){};
		return (char)-1;
	}
	public void closeF(){try{file.close();}catch(IOException e){};}	
}