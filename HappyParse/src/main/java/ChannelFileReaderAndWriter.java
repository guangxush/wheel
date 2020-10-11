import java.io.IOException;
import java.io.RandomAccessFile;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

/**
 * Java使用Channel读取和写入大文件
 *
 * @author: guangxush
 * @create: 2020/10/11
 */
public class ChannelFileReaderAndWriter {
    private RandomAccessFile inFile = null;
    private RandomAccessFile outFile = null;
    private FileChannel inChannel = null;
    private FileChannel outChannel = null;
    private final ByteBuffer buf = ByteBuffer.allocate(1024);

    /**
     * 写文件
     * @param fileName
     * @throws IOException
     */
    public void doWrite(String fileName) throws IOException {
        outFile = new RandomAccessFile(fileName, "rw");
        outChannel = outFile.getChannel();
        String newData = "New String to write to file... \n";
        buf.clear();
        buf.put(newData.getBytes());
        buf.flip();
        while (buf.hasRemaining()){
            outChannel.write(buf);
        }
        outChannel.close();
        System.out.println("Write Over!\n");
    }

    /**
     * 读文件
     * @param fileName
     * @throws IOException
     */
    public void doRead(String fileName) throws IOException {
        inFile = new RandomAccessFile(fileName, "rw");
        inChannel = inFile.getChannel();
        int bytesRead = inChannel.read(buf);
        while (bytesRead != -1) {
            System.out.println("Read " + bytesRead);
            buf.flip();
            while (buf.hasRemaining()){
                System.out.print((char) buf.get());
            }
            buf.clear();
            bytesRead = inChannel.read(buf);
        }
        inFile.close();
    }

    /**
     * 按照行读文件
     * @param fileName
     * @throws IOException
     */
    public void doReadLine(String fileName) throws IOException {
        inFile = new RandomAccessFile(fileName, "rw");
        int lineNum = 0;
        inFile.seek(lineNum);
        String line = "";
        while((line = inFile.readLine())!=null){
            line =  new String(line.getBytes("UTF-8"), "UTF-8");
            System.out.println(line);
        }
        inFile.close();
    }

    /**
     * 拷贝文件
     * @param sourceFile
     * @param targetFile
     * @throws IOException
     */
    public void doCopy(String sourceFile, String targetFile) throws IOException {
        inFile = new RandomAccessFile(sourceFile, "rw");
        inChannel = inFile.getChannel();
        RandomAccessFile bFile = new RandomAccessFile(targetFile, "rw");
        FileChannel outChannel = bFile.getChannel();
        inChannel.transferTo(0, inChannel.size(), outChannel);
        System.out.println("Copy over");
        inChannel.close();
    }

    /**
     * 倒序拷贝文件
     * @param sourceFile
     * @param targetFile
     * @throws IOException
     */
    public void reverseCopy(String sourceFile, String targetFile) throws IOException {
        inFile = new RandomAccessFile(sourceFile, "r");
        inChannel = inFile.getChannel();
        outFile = new RandomAccessFile(targetFile, "rw");
        outChannel = outFile.getChannel();
        // 文件长度
        long len = inFile.length();
        System.out.println("文件开始指针为"+0);
        //指针是从0到length-1
        long nextEnd =  len - 1;
        String line;
        //seek到最后一个字符
        inFile.seek(nextEnd);
        int c = -1;
        while (nextEnd >= 0) {
            c = inFile.read();
            //只有行与行之间才有\r\n，这表示读到每一行上一行的末尾的\n，而执行完read后，指针指到了这一行的开头字符
            if (c == '\n')
            {
                line =  new String(inFile.readLine().getBytes("UTF-8"), "UTF-8");
                System.out.println(line);
                writeLine(line, outChannel);
            }
            // 当文件指针退至文件开始处，输出第一行
            if (nextEnd == 0) {
                //不需要为下次做准备了，但是因为read()方法指针从0到了1，需重置为0
                inFile.seek(0);
                line =  new String(inFile.readLine().getBytes("UTF-8"), "UTF-8");
                System.out.println(line);
                writeLine(line, outChannel);
            } else {
                //为下一次循环做准备
                inFile.seek(nextEnd-1);
            }
            nextEnd--;
        }
        System.out.println("Reverse Copy over");
        outChannel.close();
        inChannel.close();
    }

    private void writeLine(String line, FileChannel outChannel)throws IOException{
        // 写入文件
        line += "\n";
        buf.clear();
        buf.put(line.getBytes());
        buf.flip();
        while (buf.hasRemaining()){
            outChannel.write(buf);
        }
    }

    public static void main(String[] args) throws IOException {
        ChannelFileReaderAndWriter tool = new ChannelFileReaderAndWriter();
        //tool.doRead("HappyParse/src/main/resources/SourceFile.txt");
        //tool.doWrite("HappyParse/src/main/resources/TargetFile.txt");
        //tool.doCopy("HappyParse/src/main/resources/SourceFile.txt", "HappyParse/src/main/resources/CopyFile.txt");
        // tool.reverseCopy("HappyParse/src/main/resources/SourceFile.txt", "HappyParse/src/main/resources/ReverseCopyFile.txt");
        tool.doReadLine("HappyParse/src/main/resources/SourceFile.txt");
    }
}
