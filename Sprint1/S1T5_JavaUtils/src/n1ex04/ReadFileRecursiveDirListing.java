package n1ex04;

import java.io.*;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.text.SimpleDateFormat;
import java.util.Arrays;
import java.util.Scanner;

public class ReadFileRecursiveDirListing {
    static SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
    static int dirLev = 0;

    public static void main(String[] args) {
        if (args.length>0){
            File dir;
            String path, savePath;
            boolean isDir = false;

            do {
                path = args[0];
                dir = new File(path);

                if (!dir.exists() || !dir.isDirectory()) {
                    System.out.println("Path given is not directory.");
                } else {
                    isDir = true;
                    try {
                        PrintWriter writer = new PrintWriter(new FileWriter("DirectoryListing.txt"));
                        System.out.println("Saving directory content listing in txt file...");
                        getRecursiveTxtListContent(dir, "", writer);

                        writer.close();
                        System.out.println("Content saved in DirectoryListing.txt");
                    } catch (IOException e){
                        System.out.println("Error." + e.getMessage());
                    }
                }
            } while (!isDir);
        } else{
            System.out.println("Use: ReadFileRecursiveDirListing.java directory_path");
        }

    }


    public static void getRecursiveTxtListContent(File dir, String tab, PrintWriter writer){
        File [] content = dir.listFiles();
        if (content == null){
            System.out.println("Directory is empty.");
        } else {
            Arrays.sort(content);
            for (File s : content) {
                for (int i = 0; i < dirLev; i++){
                    writer.write(tab);
                }
                if (s.isDirectory()) {
                    dirLev++;
                    writer.write(String.format("%s %40s" , "(D)" + s.getName(), "\t\t\tLast modification: " + getModDate(s) + "\n"));
                    getRecursiveTxtListContent(s, "\t", writer);

                } else {
                    writer.write(String.format("%s %40s", "(F)" + s.getName(), "\t\t\tLast modification: " + getModDate(s) + "\n"));

                    if (s.getName().endsWith(".txt")){
                        if (s.length() != 0){
                            System.out.println("Content of file: " + s.getName());
                        }
                        readFile(s, writer);
                    }
                }
            }
            dirLev--;
        }
    }

    public static String getModDate(File file){
        long datetime = file.lastModified();
        return dateFormat.format(datetime);
    }

    public static void readFile(File s, Writer writer){
        Charset charset = StandardCharsets.US_ASCII;
        try (BufferedReader reader = Files.newBufferedReader(s.toPath(), charset)) {
            String line = null;
            while ((line = reader.readLine()) != null) {
                System.out.println("\t" + line);
            }
        } catch (IOException x) {
            System.err.format("IOException: " + x.getMessage());
        }
    }
}

