package main;

import java.io.File;
/*
Вариант 4 — find

Поиск файла(ов) с заданным в командной строке именем в указанной ключом -d директории,
по умолчанию в текущей директории. Ключ -r указывает на необходимость поиска также во всех поддиректориях.

Command Line: find [-r] [-d directory] filename.txt

Кроме самой программы, следует написать автоматические тесты к ней.
 */

public class Find {
    public static void main(String[] args) {
        if (args.length < 1 || args.length > 4) {
            System.err.println("Illegal Console Argument");
            return;
        }
        int i = 0;
        boolean flagRec = false;
        if (args[i].equals("-r")) {
            flagRec = true;
            i++;
        }
        String directory = ".";
        if (args[i].equals("-d")) {
            if (args.length < i + 3) {
                System.err.println("Illegal Console Argument");
                return;
            }
            directory = args[i + 1];
            i += 2;
        }
        int length = args[i].length();
        if (length < 5) {
            System.err.println("Illegal Console Argument");
            return;
        }
        String filename = "";
        if (!args[i].endsWith(".txt")) {
            System.err.println("Illegal Console Argument");
            return;
        } else {
            filename = args[i];
        }

        String result = findFile(filename, directory, flagRec);
        if (!result.equals("")) System.out.println(result);
                     else System.out.println("File \""+filename+ "\" not found");
    }

    public static String findFile(String fileName, String directory, boolean flagRec) {
        File dir = new File(directory);
        File[] filesInDirectory = dir.listFiles();
        if (filesInDirectory == null) return "";
        for (File i : filesInDirectory) {
            if (i.isFile())
              if (fileName.equals(i.getName()))
                return directory + File.separator + fileName;
        }
        if (flagRec) {
            for (File i : filesInDirectory) {
                if (i.isDirectory()) {
                    String res = findFile(fileName, directory + File.separator + i.getName(), flagRec);
                    if (!res.equals("")) return res;
                }
            }
        }
        return "";
    }
}



