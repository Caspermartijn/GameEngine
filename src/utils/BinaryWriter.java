package utils;

import java.io.DataOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import org.lwjgl.util.vector.Vector3f;

public class BinaryWriter
{
  private DataOutputStream writer;
  
  public BinaryWriter(File file)
  {
    try
    {
      this.writer = new DataOutputStream(new FileOutputStream(file));
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
  }
  
  public void writeInt(int value)
  {
    try
    {
      this.writer.writeInt(value);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public void writeBoolean(boolean bool)
  {
    try
    {
      this.writer.writeBoolean(bool);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public void writeFloat(float value)
  {
    try
    {
      this.writer.writeFloat(value);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public void writeLong(long value)
  {
    try
    {
      this.writer.writeLong(value);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public void writeShort(short value)
  {
    try
    {
      this.writer.writeShort(value);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public void writeString(String value)
  {
    try
    {
      this.writer.writeUTF(value);
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
  
  public void writeVector(Vector3f vector)
  {
    writeFloat(vector.x);
    writeFloat(vector.y);
    writeFloat(vector.z);
  }
  
  public void close()
  {
    try
    {
      this.writer.close();
    }
    catch (IOException e)
    {
      e.printStackTrace();
    }
  }
}
