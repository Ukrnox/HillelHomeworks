package org.hillel;

import java.util.HashMap;

public class IntToRoman
{
    private static String lessThenTen(int num)
    {
        String result = "";
        switch (num)
        {
            case 1:
                result = "I";
                break;
            case 2:
                result = "II";
                break;
            case 3:
                result = "III";
                break;
            case 4:
                result = "IV";
                break;
            case 5:
                result = "V";
                break;
            case 6:
                result = "VI";
                break;
            case 7:
                result = "VII";
                break;
            case 8:
                result = "VIII";
                break;
            case 9:
                result = "IX";
                break;
        }
        return result;
    }

    public static String intToRoman(int num)
    {
        if (num > 3999)
        {
            throw new IllegalArgumentException("No numbers bigger than 3999!");
        }

        StringBuilder sb = new StringBuilder();

        while (num >= 1000)
        {
            num -= 1000;
            sb.append("M");
        }
        if (num >= 900)
        {
            num -= 900;
            sb.append("CM");
        }
        while (num >= 500)
        {
            num -= 500;
            sb.append("D");
        }
        if (num >= 400)
        {
            num -= 400;
            sb.append("CD");
        }
        while (num >= 100)
        {
            num -= 100;
            sb.append("C");
        }
        if (num >= 90)
        {
            num -= 90;
            sb.append("XC");
        }
        while (num >= 50)
        {
            num -= 50;
            sb.append("L");
        }
        if (num >= 40)
        {
            num -= 40;
            sb.append("XL");
        }
        while (num >= 10)
        {
            num -= 10;
            sb.append("X");
        }
        return sb.append(lessThenTen(num)).toString();
    }

    private static int checkNumForIntToRoman2(int num)
    {
        int result;
        if (num >= 1000)
        {
            result = 1000;
        }
        else if (num >= 900)
        {
            result = 900;
        }
        else if (num >= 500)
        {
            result = 500;
        }
        else if (num >= 400)
        {
            result = 400;
        }
        else if (num >= 100)
        {
            result = 100;
        }
        else if (num >= 90)
        {
            result = 90;
        }
        else if (num >= 50)
        {
            result = 50;
        }
        else if (num >= 40)
        {
            result = 40;
        }
        else
        {
            result = Math.min(num, 10);
        }
        return result;
    }

    public static String intToRoman2(int num)
    {
        if (num > 3999)
        {
            throw new IllegalArgumentException("No numbers bigger than 3999!");
        }
        HashMap<Integer, String> romeMap = new HashMap<>();
        romeMap.put(1, "I");
        romeMap.put(2, "II");
        romeMap.put(3, "III");
        romeMap.put(4, "IV");
        romeMap.put(5, "V");
        romeMap.put(6, "VI");
        romeMap.put(7, "VII");
        romeMap.put(8, "VIII");
        romeMap.put(9, "IX");
        romeMap.put(10, "X");
        romeMap.put(40, "XL");
        romeMap.put(50, "L");
        romeMap.put(90, "XC");
        romeMap.put(100, "C");
        romeMap.put(400, "CD");
        romeMap.put(500, "D");
        romeMap.put(900, "CM");
        romeMap.put(1000, "M");

        StringBuilder sb = new StringBuilder();
        int checkNum = checkNumForIntToRoman2(num);
        while (num > 0)
        {
            sb.append(romeMap.get(checkNum));
            num -= checkNum;
            checkNum = checkNumForIntToRoman2(num);
        }
        return sb.toString();
    }

    public static int romanToInt(String romanNum)
    {
        int result = 0;
        char[] romanArray = romanNum.toCharArray();
        HashMap<Character, Integer> romeMap = new HashMap<>();
        romeMap.put('I', 1);
        romeMap.put('V', 5);
        romeMap.put('X', 10);
        romeMap.put('L', 50);
        romeMap.put('C', 100);
        romeMap.put('D', 500);
        romeMap.put('M', 1000);
        boolean checkNextPosition;
        for (int i = 0; i < romanArray.length; i++)
        {
            checkNextPosition = i + 1 < romanArray.length;
            if (checkNextPosition && romanArray[i] == 'I' && romanArray[i + 1] == 'V')
            {
                result += 4;
                i++;
            }
            else if (checkNextPosition && romanArray[i] == 'I' && romanArray[i + 1] == 'X')
            {
                result += 9;
                i++;
            }
            else if (checkNextPosition && romanArray[i] == 'X' && romanArray[i + 1] == 'L')
            {
                result += 40;
                i++;
            }
            else if (checkNextPosition && romanArray[i] == 'X' && romanArray[i + 1] == 'C')
            {
                result += 90;
                i++;
            }
            else if (checkNextPosition && romanArray[i] == 'C' && romanArray[i + 1] == 'D')
            {
                result += 400;
                i++;
            }
            else if (checkNextPosition && romanArray[i] == 'C' && romanArray[i + 1] == 'M')
            {
                result += 900;
                i++;
            }
            else
            {
                result += romeMap.get(romanArray[i]);
            }
        }
        return result;
    }
}
