package test;

import main.Find;
import org.junit.Rule;
import org.junit.Test;
import static org.junit.Assert.assertEquals;

import org.junit.contrib.java.lang.system.ExpectedSystemExit;
import org.junit.contrib.java.lang.system.SystemErrRule;
import org.junit.contrib.java.lang.system.SystemOutRule;

import java.io.File;

public class TestFindFile {

    // проверка собственно функции поиска
    @Test
    public void findFile() {
        assertEquals("src" + File.separator + "testDir" + File.separator + "file1.txt",
                Find.findFile("file1.txt","src" + File.separator + "testDir",false));
        assertEquals("src" + File.separator + "testDir" + File.separator + "subdir1" + File.separator + "file2.txt",
                Find.findFile("file2.txt","src" + File.separator + "testDir",true));
        assertEquals("",
                Find.findFile("file2.txt","src" + File.separator + "testDir",false));
    }

   // лог сохраняющий вывод в System.out
    @Rule
    public final SystemOutRule systemOutRule = new SystemOutRule().enableLog();
    // лог сохраняющий вывод в System.err
    @Rule
    public final SystemErrRule systemErrRule = new SystemErrRule().enableLog();
    @Rule
    public final ExpectedSystemExit exit = ExpectedSystemExit.none();
    @Test
    public void TestMain() {
        // тест при правильном поиске
        // очистка логов
        systemOutRule.clearLog();
        systemErrRule.clearLog();
        Find.main("-r -d src\\testDir file1.txt".split(" "));
        assertEquals("[src" + File.separator + "testDir" + File.separator + "file1.txt]", systemOutRule.getLog().split("\r")[0]);

        // тест при правильном поиске без рекурсии
        // очистка логов
        systemOutRule.clearLog();
        systemErrRule.clearLog();
        Find.main("file1.txt".split(" "));
        assertEquals("[]", systemOutRule.getLog().split("\r")[0]);

        // тест при правильном поиске с рекурсией
        // очистка логов
        systemOutRule.clearLog();
        systemErrRule.clearLog();
        Find.main("-r file1.txt".split(" "));
        assertEquals("[." + File.separator + "out" + File.separator + "production" + File.separator + "find" + File.separator + "testDir" + File.separator + "file1.txt, ." + File.separator + "src" + File.separator + "testDir" + File.separator + "file1.txt]", systemOutRule.getLog().split("\r")[0]);

        // тест при неправильных параметрах
        systemOutRule.clearLog();
        systemErrRule.clearLog();
        exit.expectSystemExit();
        Find.main("-v -d src\\testDir file1.txt".split(" "));
        }
}
