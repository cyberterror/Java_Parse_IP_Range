package com.cyberterror.common.net;

import java.util.TreeSet;

/**
 * Created by CYBERTERROR on 31.03.2016.
 *
 * This class parses the octet (simple or very complex) and return TreeSet representation of this range.
 *
 * For example is you parse octet like "*-5,8,10-12,250-*"
 * you receive TreeSet like:
 * [0, 1, 2, 3, 4, 5, 8, 10, 11, 12, 250, 251, 252, 253, 254, 255]
 *
 * TO DO LIST
 * 1. implement errors for wrong number formats and ranges (methods are written but not implemented, because at the
 * moment I don't know how to do it correct 8P )
 *
 * THE PREP CODE FOR THIS CLASS:
 * //INSTANCE VARIABLES DECLARATION:
 * //DECLARE String "octetToParse". This string we want to parse
 * //DECLARE TreeSet<Integer> "octetParsed" or final product
 *
 * //METHOD DECLARATIONS
 * //DECLARE ParseOctet() constructor. Actually setter for octetToParse string
 * //DECLARE checkThatItIsNumber () the template of code
 * //DECLARE checkThatNumberIsInRange () the template of code
 *
 *
 * //DECLARE parseThisOctet() method. The main method which do most of logic
 *
 * LOGIC OF parseThisOctet():
 *
 * IF octet contains the ",". If so, this octet should be considered as a complex consisting of small segments
 * between "," for example "0,1,3-7,250-*"
 *          SPLIT string with "," into temp array
 *          FOREACH cell DO the [MAIN LOGIC]
 *
 * ELSE it is simple octet range or even number like for example "3-5" "3-*" "*-3" "3" "*"
 *      [MAIN LOGIC]
 *      IF octet contains "-" this means that you operate with ranges like "3-5" "3-*" "*-3"
 *          SPLIT string with "-" into temp1 array with 2 cells actually [0] and [1]
 *          IF first and second [] contains * and *
 *              generate and fill TreeSet<Integer> "octetParsed" with 0..255
 *          ELSE IF first [] contains * you like to parse something like *-number
 *              generate and fill TreeSet<Integer> "octetParsed" with 0..number
 *          ELSE IF second [] contains * you like to parse something like number-255
 *              generate and fill TreeSet<Integer> "octetParsed" with number..255
 *          ELSE
 *              generate and fill TreeSet<Integer> "octetParsed" with number[0]..number[1]
 *      ELSE octet doesn't contains "-" this means that you operate with ranges like "3" "*"
 *          IF octet EQUALS "*"
 *              generate and fill TreeSet<Integer> "octetParsed" with 0..255
 *          ELSE
 *              fill TreeSet<Integer> "octetParsed" with number
 */

public class ParseOctet
{

    private String octetToParse;
    private TreeSet<Integer> octetParsed = new TreeSet<Integer>();

    //accept octet like "0,1,3-7,250-*" or more simple

    public ParseOctet(String parseOctet)
    {
        this.octetToParse = parseOctet;
        parseThisOctet(this.octetToParse); //do the main method [TEMP]
    }

    private void parseThisOctet(String octetToParse)
    {
        if (octetToParse.contains(","))
        {// "1,3,5" "1-3,5" "1-3,5,7-*"
            String[] temp1 = octetToParse.split(",");
            for(String s1 : temp1)
            {
                if(s1.contains("-"))
                { // "3-5" "3-*" "*-10" "*-*"
                    String[] temp2 = s1.split("-");
                    if (temp2[0].equals("*") && temp2[1].equals("*"))
                    {// "*-*"
                        for (int i = 0; i < 256; i++)
                        {
                            octetParsed.add(i);
                        }
                    }
                    else if (temp2[0].equals("*"))
                    {// "*-5"
                        try
                        {
                            for (int i = 0; i < Integer.parseInt(temp2[1])+1; i++)
                            {
                                octetParsed.add(i);
                            }
                        }
                        catch (NumberFormatException e)
                        {
                            System.out.println("Wrong format of IP address! Segments of IP can consist only in form of integer positive numbers!");
                        }
                    }
                    else if (temp2[1].equals("*"))
                    {// "5-*"
                        try
                        {
                            for (int i = Integer.parseInt(temp2[0]); i < 256; i++)
                            {
                                octetParsed.add(i);
                            }
                        }
                        catch (NumberFormatException e)
                        {
                            System.out.println("Wrong format of IP address! Segments of IP can consist only in form of integer positive numbers!");
                        }
                    }
                    else
                    {// "1-5"
                        try
                        {
                            for (int i = Integer.parseInt(temp2[0]); i < Integer.parseInt(temp2[1])+1; i++)
                            {
                                octetParsed.add(i);
                            }
                        }
                        catch (NumberFormatException e)
                        {
                            System.out.println("Wrong format of IP address! Segments of IP can consist only in form of integer positive numbers!");
                        }
                    }
                }
                else
                {// "3" "*"
                    if (s1.equals("*"))
                    {// "*"
                        for (int i = 0; i < 256; i++)
                        {
                            octetParsed.add(i);
                        }
                    }
                    else
                    {// "3"
                        try
                        {
                            octetParsed.add(Integer.parseInt(s1));
                        }
                        catch (NumberFormatException e)
                        {
                            System.out.println("Wrong format of IP address! Segments of IP can consist only in form of integer positive numbers!");
                        }
                    }
                }
            }
        }


        else
        {
            if(octetToParse.contains("-"))
            { // "3-5" "3-*" "*-10" "*-*"
                String[] temp1 = octetToParse.split("-");
                if (temp1[0].equals("*") && temp1[1].equals("*"))
                {// "*-*"
                    for (int i = 0; i < 256; i++)
                    {
                        octetParsed.add(i);
                    }
                }
                else if (temp1[0].equals("*"))
                {// "*-5"
                    try
                    {
                        for (int i = 0; i < Integer.parseInt(temp1[1])+1; i++)
                        {
                            octetParsed.add(i);
                        }
                    }
                    catch (NumberFormatException e)
                    {
                        System.out.println("Wrong format of IP address! Segments of IP can consist only in form of integer positive numbers!");
                    }

                }
                else if (temp1[1].equals("*"))
                {// "5-*"
                    try
                    {
                        for (int i = Integer.parseInt(temp1[0]); i < 256; i++)
                        {
                            octetParsed.add(i);
                        }
                    }
                    catch (NumberFormatException e){
                        System.out.println("Wrong format of IP address! Segments of IP can consist only in form of integer positive numbers!");
                    }
                }
                else
                {// "1-5"
                    try
                    {
                        for (int i = Integer.parseInt(temp1[0]); i < Integer.parseInt(temp1[1])+1; i++)
                        {
                            octetParsed.add(i);
                        }
                    }
                    catch (NumberFormatException e)
                    {
                        System.out.println("Wrong format of IP address! Segments of IP can consist only in form of integer positive numbers!");
                    }
                }
            }
            else
            {// "3" "*"
                if (octetToParse.equals("*"))
                {// "*"
                    for (int i = 0; i < 256; i++)
                    {
                        octetParsed.add(i);
                    }
                }
                else
                {// "3"
                    try {
                        octetParsed.add(Integer.parseInt(octetToParse));
                    }
                    catch (NumberFormatException e)
                    {
                        System.out.println("Wrong format of IP address! Segments of IP can consist only in form of integer positive numbers!");
                    }
                }
            }
        }
    }


    //NOT IMPLEMENTED!!!!
    public boolean checkThatItIsNumber (String s){
        boolean check = true;
        for (int i = 0; i < s.length(); i++)
        {
            char c = s.charAt(i);
            if (!(c >= '0' && c <= '9'))
            {
                System.out.println("Wrong format of IP address! Segments of IP can consist only integer numbers!");
                check = false;
            }
        }
        return check;
    }


    //NOT IMPLEMENTED!!!!
    public boolean checkThatNumberIsInRange (String s){
        boolean check = true;
        int number = Integer.parseInt(s);
        if ((number > 255) || (number < 0))
        {
            System.out.println("Segments of IP can consist only integer numbers from 0 to 255!");
            check = false;
        }
        return check;
    }


    public String getOctetToParse() {
        return octetToParse;
    }


    public TreeSet<Integer> getOctetParsed() {
        return octetParsed;
    }
}
