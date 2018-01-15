package utils.binary;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import org.lwjgl.util.vector.Vector3f;

public class BinaryReader
{
  private DataInputStream reader;
  
  public BinaryReader(File file)
  {
    try
    {
      this.reader = new DataInputStream(new FileInputStream(file));
    }
    catch (Exception e)
    {
      System.err.println("Couldn't open read file " + file);
    }
  }
  
  public int readInt()
    throws Exception
  {
    return this.reader.readInt();
  }
  
  public float readFloat()
    throws Exception
  {
    return this.reader.readFloat();
  }
  
  public boolean readBoolean()
    throws Exception
  {
    return this.reader.readBoolean();
  }
  
  public String readString()
    throws Exception
  {
    return this.reader.readUTF();
  }
  
  public long readLong()
    throws Exception
  {
    return this.reader.readLong();
  }
  
  public short readShort()
    throws Exception
  {
    return this.reader.readShort();
  }
  
  public Vector3f readVector()
    throws Exception
  {
    float x = readFloat();
    float y = readFloat();
    float z = readFloat();
    return new Vector3f(x, y, z);
  }
  
  public void close()
  {
    try
    {
      this.reader.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}
