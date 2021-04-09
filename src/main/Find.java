package main;

import java.awt.*;
import java.io.File;
import java.util.ArrayList;
/*
Вариант 4 — find

Поиск файла(ов) с заданным в командной строке именем в указанной ключом -d директории,
по умолчанию в текущей директории. Ключ -r указывает на необходимость поиска также во всех поддиректориях.

Command Line: [-r] [-d directory] filename.txt

Кроме самой программы, следует написать автоматические тесты к ней.
 */

public class Find {
    public static void main(String[] args) {
        if (args.length < 1 || args.length > 4) {
            System.exit(1);
        }
        int i = 0;
        boolean flagRec = false;
        String directory = ".";
        String filename = "";

        for(; i < args.length; i++) {
            if (args[i].equals("-r")) {
                flagRec = true;
            } else if (args[i].equals("-d")) {
                if (args.length < i + 3) {
                    System.exit(1);
                }
                directory = args[i + 1];
                i++;
            } else {
                int length = args[i].length();
                if (length < 5) {
                    System.exit(1);
                }
                filename = args[i];
            }
        }

        ArrayList<String> result = findFile(filename, directory, flagRec);
        if (!result.equals("")) System.out.println(result);
                     else System.out.println("File \""+filename+ "\" not found");
    }

    public static ArrayList<String> findFile(String fileName, String directory, boolean flagRec) {
        ArrayList<String> paths = new ArrayList<>();
        File dir = new File(directory);
        File[] filesInDirectory = dir.listFiles();
        if (filesInDirectory == null) return paths;
        for (File i : filesInDirectory) {
            if (i.isFile())
              if (fileName.equals(i.getName())) {
                  paths.add(i.getPath());
              }
        }
        if (flagRec) {
            for (File i : filesInDirectory) {
                if (i.isDirectory()) {
                    ArrayList<String> res = findFile(fileName, directory + File.separator + i.getName(), flagRec);
                    if (res.size() > 0){
                        paths.addAll(res);
                    }
                }
            }
        }
        return paths;
    }
}



