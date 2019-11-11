package file;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;

public class CFile
{
	private static File mFile = null;
	
	public void write(String data, String filename)
	{
		OutputStream lOutputStream = null;
		mFile = new File(filename);
		
		try
		{
			lOutputStream = new FileOutputStream(mFile);
			lOutputStream.write(data.getBytes(), 0, data.length());
		} 
		catch (FileNotFoundException e)
		{
			e.printStackTrace();
		}
		catch (IOException e)
		{
			e.printStackTrace();
		}
		finally
		{
			try
			{
				lOutputStream.close();
			}
			catch (IOException e)
			{
				e.printStackTrace();
			}
		}
	}
}
















