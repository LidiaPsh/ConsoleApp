package test;

import main.Find;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import org.junit.contrib.java.lang.system.SystemErrRule;
import org.junit.contrib.java.lang.system.SystemOutRule;

public class TestFindFile {
    // проверка собственно функции поиска
    @Test
    public void findFile() {
        assertEquals("src\\testDir\\file1.txt",
                Find.findFile("file1.txt","src\\testDir",false));
        assertEquals("src\\testDir\\subdir1\\file2.txt",
                Find.findFile("file2.txt","src\\testDir",true));
        assertEquals("",
                Find.findFile("file2.txt","src\\testDir",false));
    }

   // лог сохраняющий вывод в System.out
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
    // лог сохраняющий вывод в System.err
    @Rule
    public final SystemErrRule systemErrRule = new SystemErrRule().enableLog();

    @Test
    public void TestMain() {
        // тест при правильном поиске
        // очистка логов
        systemOutRule.clearLog();
        systemErrRule.clearLog();
        Find.main("-r -d src\\testDir file1.txt".split(" "));
        assertEquals("src\\testDir\\file1.txt", systemOutRule.getLog().split("\r")[0]);

        // тест при правильном поиске без рекурсии
        // очистка логов
        systemOutRule.clearLog();
        systemErrRule.clearLog();
        Find.main("file1.txt".split(" "));
        assertEquals("File \"file1.txt\" not found", systemOutRule.getLog().split("\r")[0]);

        // тест при правильном поиске с рекурсией
        // очистка логов
        systemOutRule.clearLog();
        systemErrRule.clearLog();
        Find.main("-r file1.txt".split(" "));
        assertEquals(".\\out\\production\\find\\testDir\\file1.txt", systemOutRule.getLog().split("\r")[0]);

        // тест при неправильных параметрах
        systemOutRule.clearLog();
        systemErrRule.clearLog();
        Find.main("-v -d src\\testDir file1.txt".split(" "));
        assertEquals("Illegal Console Argument", systemErrRule.getLog().split("\r")[0]);
    }
}
