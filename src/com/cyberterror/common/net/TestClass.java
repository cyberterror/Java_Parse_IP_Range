package com.cyberterror.common.net;

/**
 * Created by CYBERTERROR on 30.03.2016.
 *
 * TEST CLASS FOR TARGETRANGE CLASS AND PARSEOCTET CLASS.
 *
 * TO DO LIST
 * 1. implement errors for wrong number formats and ranges (method is written but not implemented)
 * 2. implement CIDR notation
 * 3. implement crazy ranges with " - " like "very complex range 1" - "very complex range 2"
 */
public class TestClass
{

    public static void main(String[] args)
    {
        //check simple octet
        ParseOctet o1 = new ParseOctet("*-1,8,10-12,250-*");
        System.out.println(o1.getOctetParsed());
        //ParseOctet o2 = new ParseOctet("*");
        //ParseOctet o3 = new ParseOctet("3-5");
        //ParseOctet o4 = new ParseOctet("*-10");
        //ParseOctet o5 = new ParseOctet("250-*");

        //check complex octet
//        ParseOctet o1 = new ParseOctet("*-5,8,10-12,250-*");
//        System.out.println(o1.getOctetToParse());
//        System.out.println(o1.getOctetParsed());

//        ParseRange r1 = new ParseRange("1.1.1.0-20");
//        r1.printTargets();
    }
}