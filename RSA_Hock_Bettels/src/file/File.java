package file;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardOpenOption;
import java.util.List;

public class File {

	//Right now some testvalues
	private String mFileName = "test.txt";
	private String mFileDirectory = "C:\\Users\\const\\Documents";
	private Path mPath;

	public static void main(String[] args)
	{
		File lSampleFile = new File();
		lSampleFile.setPath(lSampleFile.getFileDirectory(), lSampleFile.getFileName());
		String text = "This is some testing stuff";
		lSampleFile.write(lSampleFile.getPath(), text);
		lSampleFile.read(lSampleFile.getPath());

	}

	public File() {}

	public String getFileName()
	{
		return mFileName;
	}
	public void setFileName(String aFileName)
	{
		this.mFileName = aFileName;
	}
	public void write(Path aPath, String aInput)
	{
		try
		{
			Files.write(aPath, aInput.getBytes(), StandardOpenOption.APPEND);
		} 
		catch (IOException e)
		{
			System.out.println("File not found or bad input");
		}
	}
	//Currently void, may wanna change that later TODO
	public void read(Path aPath)
	{
		try
		{
			List<String> lList = Files.readAllLines(aPath);
			lList.forEach(line -> System.out.println(line));
		}
		catch (IOException e)
		{
			System.out.println("Couldn't read File");
		}
	}
	public String getFileDirectory()
	{
		return mFileDirectory;
	}
	public void setFileDirectory(String aFileDirectory)
	{
		this.mFileDirectory = aFileDirectory;
	}
	public Path getPath()
	{
		return mPath;
	}
	public void setPath(String aFileDirectory, String aFileName)
	{
		mPath = Paths.get(aFileDirectory, aFileName);
	}


}