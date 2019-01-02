import java.io.*;
import java.util.*;

public class FileOut
{
	private FileOutputStream file;
	FileOut(String nom)
	{
		try{
			file =new FileOutputStream(new File(nom));
		}catch(IOException e){};
	}
	
	public void putC(char c)
	{
		try{
			file.write(c);
		}catch(IOException e){};
	}
	public void closeF(){try{file.close();}catch(IOException e){};}	
}

