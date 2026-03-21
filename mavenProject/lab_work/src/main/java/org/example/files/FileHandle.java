package org.example.files;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class FileHandle {
    public static void main(String[] args) throws IOException {
          System.out.println("-------------");
          FileOutputStream os = new FileOutputStream("demo.txt");
          int x = 10;
          os.write(x);
          os.close();

         FileInputStream is = new FileInputStream("demo.txt");
         int y = is.read();
         System.out.println(y);
         is.close();

         FileWriter fw = new FileWriter("fw.txt");
         fw.append('a');
         fw.close();

                FileReader fr = new FileReader("fw.txt");
                int output = fr.read();
                System.out.println(output);
                fr.close();

                FileOutputStream fos = new FileOutputStream("objectFile.txt");
                ObjectOutputStream oos = new ObjectOutputStream(fos);

                Student acc = new Student();
                oos.writeObject(acc);
                oos.close();
                fos.close();

                FileInputStream fis = new FileInputStream("objectFile.txt");
                ObjectInputStream ois = new ObjectInputStream(fis);

                // Student acc1 = (Student) ois.readObject();


                 // lab about FOS and FIS
                List<String> names = new ArrayList<String>();
                names.add("John");
                names.add("Jane");
                names.add("Joe");

                OutputStream outputStream = new FileOutputStream("outputStream.txt");
                for(String name: names){
                    outputStream.write(name.getBytes());
                    outputStream.write('\n');
                }
                outputStream.close();

                InputStream inputStream = new FileInputStream("outputStream.txt");
                int fileData;
                String outputData = "";
                while(( fileData = inputStream.read()) != -1) {
                    if(fileData == '\n') {
                        System.out.println(outputData);
                        outputData = "";
                        continue;
                    }
                    outputData += (char)fileData;
                }

                BufferedOutputStream out = new BufferedOutputStream(new FileOutputStream("outputStream.txt"));

                for(String name : names) {
                    out.write(name.getBytes());
                    System.out.println(name.getBytes());
                    out.write('\n');
                }
                out.close();

                BufferedInputStream in = new BufferedInputStream(new FileInputStream("outputStream.txt"));
                // int fileData;
                outputData = "";
                while(( fileData = in.read() ) != -1) {
                    if(fileData == '\n') {
                        System.out.println(outputData);
                        outputData = "";
                    }else{
                        outputData += (char) fileData;

                    }
                }

                int listOfInt[] = new int[]{1, 2, 3, 4, 5};

                // FileWriter fw = new FileWriter("outputChar.txt");
                BufferedWriter bw = new BufferedWriter(new FileWriter("outputChar.txt"));
                for(int i = 0; i < listOfInt.length; i++){
                    bw.write(listOfInt[i]);
                }
                bw.close();

                // FileReader fr = new FileReader("outputChar.txt");

                BufferedReader br = new BufferedReader(new FileReader("outputChar.txt"));

                while((output = br.read()) != -1){
                    System.out.println(output);
                }

    }
}
