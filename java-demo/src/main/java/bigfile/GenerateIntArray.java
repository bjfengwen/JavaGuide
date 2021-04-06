package bigfile;

import java.io.File;
import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.nio.channels.FileChannel.MapMode;
import java.util.Random;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * Java NIO(内存映射文件) 与 传统IO write 性能测试
 * 
 * GenerateIntArray生成count个数组,每个数组里面有size个整数,然后将生成的数据写入文件.
 * 整数通过Random.nextInt(total)来生成.
 * 初始化时,数组数据都是0,调用refreshDataArr()可生成新数据填充数组
 * 
 * 生成数据的方法有:
 * (1) refreshDataArr() 使用双重循环生成数据.
 * (2) refreshDataArr_M() 使用count个线程,每个线程中生成size个数据
 * 
 * 将数据写入文件的方法:
 * 
 * (1)writeData2File(File f) 
 *    循环调用RandomAccessFile.writeInt(int)方法,每个数据都写一次,一共写了count*size次
 * (2)writeData2File_B(File f) 
 *    该方法先将coutn个整数转换成字符数组,并将count个字节数组按顺序组合到一个大的字节数组中
 *    然后调用RandomAccessFile.write(byte[] byteArr);方法一次性写入size个整数.
 * (3)writeData2File_M(File f)
 *    该方法启动count个线程,每个线程使用writeData2File_B中的方法,一次性写入size个整数
 *    
 * (4)writeData2FileNIO(File f) NIO 通过Channel 和 Buffer的方式来写文件
 * (5)writeData2FileMap(File f) NIO中通过内存映射文件写 文件
 * (6)writeData2FileNIO_D(File f) NIO中,通过Channel 和 Buffer的方式来写文件,
 *    其中Buffer使用直接分配空间方式allocateDirect分配空间
 * 
 * 由下面的测试结果可知,通过RandomAccessFile.write(byte[] byteArr)写入字节数组的方式一次性写入
 * size个整数时写入速度最快,比一次写入一个整数快了很多.多线程写入时性能提升不大,只有在count不大,但是
 * size巨大时多线程方法写入有一些提升,因为生成count个线程并且要进行线程调度也需要消耗一些系统资源.
 * 多线程方式生成数据,也只有在size特别大(100000),count不是很大时有速度提升.
 * 可能因为测试机器CPU是单核的,对于多线程性能提升不大.
 * 
 * 数据量非常小时,使用单线程一次生成一个数据,以及一次写入一个整数时速度快
 * (因为使用写字节数组方式一次性写入size个整数时需要将整数转换成字节数组,这有一定的开销).
 * 
 * NIO方式使用allocateDirect直接分配buffer空间比传统方式分配buffer空间的性能提升明显.
 * 
 * 使用内存映射文件性能也有提升.
 *
 * 
 * 下面是部分测试数据(耗时单位是 耗时(纳秒)/100000)
 * 
count = 1000, size = 10000 

正在生成数据,请稍后...
refreshDataArr 生成数据成功, 耗时:9977

正在写入数据,请稍后...
数据已写入文件D:\D\test_data.dat\test_data.dat
writeData2File_B写入数据耗时:13006

正在写入数据,请稍后...
数据已写入文件D:\D\test_data.dat\test_data.dat
writeData2File 写入数据耗时:664187

正在写入数据,请稍后...
数据已写入文件D:\D\test_data.dat\test_data.dat
writeData2File_M写入数据耗时:4210

正在写入数据,请稍后...
数据已写入文件D:\D\test_data.dat\test_data.dat
writeData2FileNIO 写入数据耗时:48942

正在写入数据,请稍后...
数据已写入文件D:\D\test_data.dat\test_data.dat
writeData2FileNIO_D 写入数据耗时:15509

正在写入数据,请稍后...
数据已写入文件D:\D\test_data.dat\test_data.dat
writeData2FileMap 写入数据耗时:27390


 *
 */
public class GenerateIntArray
{
  private int     count   = 1000;             // 数组的个数,
  private int     size    = 10;               // 每个数组的元素个数
  private int[][] dataArr;
  private Random  random  = new Random(1000);

  public GenerateIntArray()
  {
    dataArr = new int[count][size];
  }

  public GenerateIntArray(int count, int size)
  {
    this.count = count;
    this.size = size;
    this.dataArr = new int[count][size];
  }

  public int[][] getDataArr()
  {
    return dataArr;
  }

  /**
   * 刷新数组中的数据
   */
  public int[][] refreshDataArr()
  {
    int total = count * size;

    for (int i = 0; i < count; i++)
    {
      for (int j = 0; j < size; j++)
      {
        dataArr[i][j] = random.nextInt(total);
      }
    }

    return dataArr;
  }
  
  private class getIntTask implements Runnable
  {
    private int arrIndex;
    private CountDownLatch latch;
    
    public getIntTask(int arrIndex,CountDownLatch latch)
    {
      this.arrIndex = arrIndex;
      this.latch = latch;
    }
    
    @Override
    public void run()
    {
      int total = count * size;
      for(int i = 0;i < size;i++)
      {
        dataArr[arrIndex][i] = random.nextInt(total);
      }
      latch.countDown();
      
    }
    
  }
  
  

  /**
   * 写数组数据到文件,如果文件已经存在,则会被删除,然后重新生成文件
   * 每次写入数组中的一个数据
   * @param f
   * @throws IOException
   */
  public void writeData2File(File f) throws IOException
  {
    if (null != f && f.exists())
    {
      f.delete();
    }
    RandomAccessFile rf = new RandomAccessFile(f, "rw");
    rf.seek(0);// 每次都从头开始些文件
    for (int i = 0; i < count; i++)
    {
      for (int j = 0; j < size; j++)
      {
        rf.writeInt(dataArr[i][j]);
      }
    }
    
    rf.close();
  }
  
  public void writeData2FileNIO(File f) throws IOException
  {
    if (null != f && f.exists())
    {
      f.delete();
    }
    
  //先生成一个固定尺寸的文件,能够保存所有整数的
    RandomAccessFile rf = new RandomAccessFile(f, "rw");
    rf.setLength(count * size * 4 ); //设置尺寸(一个整型占4字节)
    rf.seek(0);
    //rf.write(1024);//随便写一个,以便保存文件
    rf.close();
    
    rf = new RandomAccessFile(f, "rw");
    FileChannel fc = rf.getChannel();
    ByteBuffer buffer = ByteBuffer.allocate(size * 4);
    
    for (int i = 0; i < count; i++)
    {
      for (int j = 0; j < size; j++)
      {
        //buffer.put(int2byte(dataArr[i][j]));
        buffer.putInt(dataArr[i][j]);
      }
      buffer.rewind();
      fc.write(buffer);
      buffer.rewind();
    }
    
    rf.close();
    fc.close();
  }
  
  public void writeData2FileMap(File f) throws IOException
  {
    if (null != f && f.exists())
    {
      f.delete();
    }
    
  //先生成一个固定尺寸的文件,能够保存所有整数的
    RandomAccessFile rf = new RandomAccessFile(f, "rw");
    rf.setLength(count * size * 4 ); //设置尺寸(一个整型占4字节)
    rf.seek(0);
    //rf.write(1024);//随便写一个,以便保存文件
    rf.close();
    
    rf = new RandomAccessFile(f, "rw");
    FileChannel fc = rf.getChannel();
    
    int iSize = 4 * size;
    
    for (int i = 0; i < count; i++)
    {
      int position = i * size;
      ByteBuffer buffer = fc.map(MapMode.READ_WRITE,position,iSize);
      for (int j = 0; j < size; j++)
      {
        //buffer.put(int2byte(dataArr[i][j]));
        buffer.putInt(dataArr[i][j]);
      }
      buffer.rewind();
      fc.write(buffer);
      buffer.rewind();
    }
    
    rf.close();
    fc.close();
  }
  
  public void writeData2FileNIO_D(File f) throws IOException
  {
    if (null != f && f.exists())
    {
      f.delete();
    }
    RandomAccessFile rf = new RandomAccessFile(f, "rw");
    FileChannel fc = rf.getChannel();
    ByteBuffer buffer = ByteBuffer.allocateDirect(size * 4);
    
    for (int i = 0; i < count; i++)
    {
      for (int j = 0; j < size; j++)
      {
        buffer.putInt(dataArr[i][j]);
      }
      buffer.rewind();
      fc.write(buffer);
      buffer.rewind();
    }
    
    rf.close();
    fc.close();
  }

  /**
   * 写数据时,现将整数转换成字节数据保存,然后一次性写入字节数组到文件,
   * 避免频繁写入.
   * @param f
   * @throws IOException
   */
  public void writeData2File_B(File f) throws IOException
  {
    if (null != f && f.exists())
    {
      f.delete();
    }
    RandomAccessFile rf = new RandomAccessFile(f, "rw");
    rf.seek(0);// 每次都从头开始些文件
    for (int i = 0; i < count; i++)
    {
      byte[] byteArr = new byte[4 * size];
      int iTmp = 0;
      for (int j = 0; j < size; j++)
      {
        byte[] tmpBytes = int2byte(dataArr[i][j]);
        byteArr[iTmp++] = tmpBytes[3];
        byteArr[iTmp++] = tmpBytes[2];
        byteArr[iTmp++] = tmpBytes[1];
        byteArr[iTmp++] = tmpBytes[0];
      }
      rf.write(byteArr);
    }
    rf.close();
  }
  
  /**
   * 多线程方式同时同时写文件
   * @param f
   * @throws IOException
   */
  
  class WriteTask implements Runnable
  {

    private File f;
    private int dataIndex;
    
    public WriteTask(File f,int dataIndex)
    {
      this.f = f;
      this.dataIndex = dataIndex;
    }
    
    @Override
    public void run()
    {
      try
      {
        RandomAccessFile rf = new RandomAccessFile(f, "rw");
        rf.skipBytes(dataIndex * size * 4 );
        byte[] byteArr = new byte[4 * size];
        int iTmp = 0;
        for (int j = 0; j < size; j++)
        {
          byte[] tmpBytes = int2byte(dataArr[dataIndex][j]);
          byteArr[iTmp++] = tmpBytes[3];
          byteArr[iTmp++] = tmpBytes[2];
          byteArr[iTmp++] = tmpBytes[1];
          byteArr[iTmp++] = tmpBytes[0];
        }
        rf.write(byteArr);
        rf.close();
      }
      catch (Exception e)
      {
        e.printStackTrace();
      }
    }
  }
  
  public void writeData2File_M(File f) throws IOException
  {
    if (null != f && f.exists())
    {
      f.delete();
    }
    
    //先生成一个固定尺寸的文件,能够保存所有整数的
    RandomAccessFile rf = new RandomAccessFile(f, "rw");
    rf.setLength(count * size * 4 ); //设置尺寸(一个整型占4字节)
    rf.seek(0);
    //rf.write(1024);//随便写一个,以便保存文件
    rf.close();
    
    ExecutorService exec = Executors.newCachedThreadPool();
    for(int i=0;i<count;i++)
    {
      exec.execute(new WriteTask(f,i));
    }
    exec.shutdown();
  }

  // 将二进制数转换成字节数组
  private byte[] int2byte(int res)
  {
    byte[] targets = new byte[4];

    targets[0] = (byte) (res & 0xff);// 最低位
    targets[1] = (byte) ((res >> 8) & 0xff);// 次低位
    targets[2] = (byte) ((res >> 16) & 0xff);// 次高位
    targets[3] = (byte) (res >>> 24);// 最高位,无符号右移
    return targets;
  }

  public static void main(String[] args)
  {
    int count = 1000;
    int size = 10000;
    boolean bPrintData = false; //是否打印生成的数组,当数据量大是不打印,只在小数据量时打印以便测试
    
    System.out.printf("count = %d, size = %d \n\n",count,size);
    
    GenerateIntArray generator = new GenerateIntArray(count, size);

    File f;

    try
    {
      f = new File("D:\\D\\test_data.dat");
      
      System.out.println("正在生成数据,请稍后...");
      long startTmie = System.nanoTime();
      generator.refreshDataArr();
      long totalTime = (System.nanoTime() - startTmie)/ 100000;
      System.out.println("refreshDataArr 生成数据成功, 耗时:" + totalTime);
      System.out.println();
      
      System.out.println("正在写入数据,请稍后...");
      startTmie = System.nanoTime();
      generator.writeData2File_B(f);
      totalTime = (System.nanoTime() - startTmie)/ 100000;;
      System.out.println("数据已写入文件" + f.getPath() + File.separator + f.getName());
      System.out.println("writeData2File_B写入数据耗时:" + totalTime);
      System.out.println();
      
      System.out.println("正在写入数据,请稍后...");
      startTmie = System.nanoTime();
      //generator.writeData2File(f);//耗时太长
      totalTime = (System.nanoTime() - startTmie)/ 100000;
      System.out.println("数据已写入文件" + f.getPath() + File.separator + f.getName());
      System.out.println("writeData2File 写入数据耗时:" + totalTime);
      System.out.println();
      
      System.out.println("正在写入数据,请稍后...");
      startTmie = System.nanoTime();
      generator.writeData2File_M(f);
      totalTime = (System.nanoTime() - startTmie)/ 100000;;
      System.out.println("数据已写入文件" + f.getPath() + File.separator + f.getName());
      System.out.println("writeData2File_M写入数据耗时:" + totalTime);
      System.out.println();
      
      System.out.println("正在写入数据,请稍后...");
      startTmie = System.nanoTime();
      generator.writeData2FileNIO(f);
      totalTime = (System.nanoTime() - startTmie)/ 100000;
      System.out.println("数据已写入文件" + f.getPath() + File.separator + f.getName());
      System.out.println("writeData2FileNIO 写入数据耗时:" + totalTime);
      System.out.println();
      
      System.out.println("正在写入数据,请稍后...");
      startTmie = System.nanoTime();
      generator.writeData2FileNIO_D(f);
      totalTime = (System.nanoTime() - startTmie)/ 100000;
      System.out.println("数据已写入文件" + f.getPath() + File.separator + f.getName());
      System.out.println("writeData2FileNIO_D 写入数据耗时:" + totalTime);
      System.out.println();
      
      System.out.println("正在写入数据,请稍后...");
      startTmie = System.nanoTime();
      generator.writeData2FileMap(f);
      totalTime = (System.nanoTime() - startTmie)/ 100000;
      System.out.println("数据已写入文件" + f.getPath() + File.separator + f.getName());
      System.out.println("writeData2FileMap 写入数据耗时:" + totalTime);
      System.out.println();
      
      if(bPrintData)
      {
        System.out.println("原始数组中生成的数据...");
        int[][] intArr = generator.getDataArr();
        for (int i = 0; i < count; i++)
        {
          for (int j = 0; j < size; j++)
          {
            System.out.printf("%d ", intArr[i][j]);
          }
          System.out.println();
        }
        
        System.out.println("从文件中读取出来的数据...");
        RandomAccessFile rf = new RandomAccessFile(f, "r");
        rf.seek(0);
        int iline = 1;
        while (true)
        {
          System.out.printf("%d ",rf.readInt());
          if(iline % size == 0)
          {
            System.out.println();
          }
          iline ++;
          // 判断已经到文件尾了
          if (rf.getFilePointer() >= rf.length() - 1)
          {
            break;
          }
          
        }
        rf.close();
      }

      
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }

  }

}
