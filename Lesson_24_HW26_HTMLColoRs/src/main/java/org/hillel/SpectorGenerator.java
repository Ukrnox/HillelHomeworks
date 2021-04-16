package org.hillel;

import java.io.BufferedWriter;
import java.io.FileWriter;
import java.io.IOException;

public class SpectorGenerator
{
    public static void main(String[] args)
    {
        new SpectorGenerator().generate(2, 5, 1000, 1000,"Colors.html");
    }

    String writeHead()
    {
        return "<!DOCTYPE html>\n" +
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
    }

    String writeTail()
    {
        return "</body>\n" +
                "\n" +
                "</html>\n";
    }

    String generateColor(int red, int green, int blue, int colorDimmer)
    {
        red = Math.min(red, 255);
        green = Math.min(green, 255);
        blue = Math.min(blue, 255);
        red = Math.max(red - colorDimmer, 0);
        green = Math.max(green - colorDimmer, 0);
        blue = Math.max(blue - colorDimmer, 0);
        return String.format("\t\t<td style='background-color: rgb(%d,%d,%d)'></td>\n", red, green, blue);
    }

    String generateLinesOfColors(int colorStep, int colorDimmerStep)
    {
        StringBuilder sb = new StringBuilder();
        int red = 255;
        int green = 0;
        int blue = 0;
        for (int colorDimmer = 0; colorDimmer - colorDimmerStep < 255; colorDimmer += colorDimmerStep)
        // "colorDimmer - colorDimmerStep < 255" - такая логика позволяет вывести черный цвет в конце
        // если пользователь введет высокий шаг затемнения (colorDimmerStep) (например 60)
        {
            sb.append("\t<tr>\n");
            while (green < 255)
            {
                green += colorStep;
                sb.append(generateColor(red, green, blue, colorDimmer));
            }
            while (red > 0)
            {
                red -= colorStep;
                sb.append(generateColor(red, green, blue, colorDimmer));
            }
            while (blue < 255)
            {
                blue += colorStep;
                sb.append(generateColor(red, green, blue, colorDimmer));
            }
            while (green > 0)
            {
                green -= colorStep;
                sb.append(generateColor(red, green, blue, colorDimmer));
            }
            while (red < 255)
            {
                red += colorStep;
                sb.append(generateColor(red, green, blue, colorDimmer));
            }
            while (blue > 0)
            {
                blue -= colorStep;
                sb.append(generateColor(red, green, blue, colorDimmer));
            }
            sb.append("\t</tr>\n");
        }
        return sb.toString();
    }

    public String generate(int colorStep, int colorDimmerStep, int tableStyleWidth, int tableStyleHeight, String filePath)
    {
        StringBuilder sb = new StringBuilder();
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(filePath)))
        {
            sb.append(writeHead());
            sb.append(String.format("\t<table style=' width: %dpx; height: %dpx; border-spacing: 0;'>\n", tableStyleWidth, tableStyleHeight));
            sb.append(generateLinesOfColors(colorStep, colorDimmerStep));
            sb.append("\t</table>\n");
            sb.append(writeTail());
            bw.write(sb.toString());
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return e.getClass().toString();
        }
        return sb.toString();
    }
}