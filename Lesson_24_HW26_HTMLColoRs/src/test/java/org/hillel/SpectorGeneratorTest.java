package org.hillel;

import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class SpectorGeneratorTest
{
    private final SpectorGenerator spectorGenerator = new SpectorGenerator();
    private final String expectedHead = "<!DOCTYPE html>\n" +
            "<html>\n" +
            "\n" +
            "<head>\n" +
            "\t<meta charset=\"UTF-8\">\n" +
            "\t<title>Rainbow</title>\n" +
            "\t<style type=\"text/css\">\n" +
            "\t\tTABLE\n" +
            "\t\t{\n" +
            "\t\tmargin-left: auto;\n" +
            "\t\tmargin-right: auto;\n" +
            "\t\t}\n" +
            "\t</style>\n" +
            "</head>\n" +
            "\n" +
            "<body>\n" +
            "\n" +
            "<h1 align='center'>Rainbow</h1>\n";
    private final String expectedLinesOfColors = "\t<tr>\n" +
            "\t\t<td style='background-color: rgb(255,255,0)'></td>\n" +
            "\t\t<td style='background-color: rgb(0,255,0)'></td>\n" +
            "\t\t<td style='background-color: rgb(0,255,255)'></td>\n" +
            "\t\t<td style='background-color: rgb(0,0,255)'></td>\n" +
            "\t\t<td style='background-color: rgb(255,0,255)'></td>\n" +
            "\t\t<td style='background-color: rgb(255,0,0)'></td>\n" +
            "\t</tr>\n" +
            "\t<tr>\n" +
            "\t\t<td style='background-color: rgb(155,155,0)'></td>\n" +
            "\t\t<td style='background-color: rgb(0,155,0)'></td>\n" +
            "\t\t<td style='background-color: rgb(0,155,155)'></td>\n" +
            "\t\t<td style='background-color: rgb(0,0,155)'></td>\n" +
            "\t\t<td style='background-color: rgb(155,0,155)'></td>\n" +
            "\t\t<td style='background-color: rgb(155,0,0)'></td>\n" +
            "\t</tr>\n" +
            "\t<tr>\n" +
            "\t\t<td style='background-color: rgb(55,55,0)'></td>\n" +
            "\t\t<td style='background-color: rgb(0,55,0)'></td>\n" +
            "\t\t<td style='background-color: rgb(0,55,55)'></td>\n" +
            "\t\t<td style='background-color: rgb(0,0,55)'></td>\n" +
            "\t\t<td style='background-color: rgb(55,0,55)'></td>\n" +
            "\t\t<td style='background-color: rgb(55,0,0)'></td>\n" +
            "\t</tr>\n" +
            "\t<tr>\n" +
            "\t\t<td style='background-color: rgb(0,0,0)'></td>\n" +
            "\t\t<td style='background-color: rgb(0,0,0)'></td>\n" +
            "\t\t<td style='background-color: rgb(0,0,0)'></td>\n" +
            "\t\t<td style='background-color: rgb(0,0,0)'></td>\n" +
            "\t\t<td style='background-color: rgb(0,0,0)'></td>\n" +
            "\t\t<td style='background-color: rgb(0,0,0)'></td>\n" +
            "\t</tr>\n";
    private final String expectedTail = "</body>\n" +
            "\n" +
            "</html>\n";


    @Test
    public void testWriteHead()
    {
        String actual = spectorGenerator.writeHead();
        assertEquals(expectedHead, actual);
    }

    @Test
    public void testWriteTail()
    {
        String actual = spectorGenerator.writeTail();
        assertEquals(expectedTail, actual);
    }

    @Test
    public void testGenerateColor()
    {
        String expected = "\t\t<td style='background-color: rgb(250,0,0)'></td>\n";
        String actual = spectorGenerator.generateColor(267, -123, 0, 5);
        assertEquals(expected, actual);
    }

    @Test
    public void testGenerateLinesOfColors()
    {
        String actual = spectorGenerator.generateLinesOfColors(255, 100);
        assertEquals(expectedLinesOfColors, actual);
    }


    @Test
    public void testGenerate()
    {
        String expectedTableStyle = "\t<table style=' width: 1000px; height: 1000px; border-spacing: 0;'>\n";
        String expectedTableTail = "\t</table>\n";
        String expected = expectedHead + expectedTableStyle + expectedLinesOfColors + expectedTableTail + expectedTail;
        String filePath = "ColorsTest.html";
        String actual = spectorGenerator.generate(255, 100, 1000, 1000, filePath);
        assertEquals(expected, actual);
    }

    @Test
    public void testGenerate_IOException()
    {
        String filePath = "wrong/file/path.html";
        String expected = "class java.io.FileNotFoundException";
        String actual = spectorGenerator.generate(255, 255, 255, 255, filePath);
        assertEquals(expected, actual);
    }

    @Test
    public void main()
    {
        SpectorGenerator.main(null);
    }
}