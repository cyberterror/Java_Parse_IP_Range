package com.cyberterror.common.net;

import java.util.TreeSet;

/**
 * Created by CYBERTERROR on 30.03.2016.
 *
 * ParseRange is special class that parses the targets ip/range using different approach and return TreeSet with this range
 *
 * Accepts:
 * 1. IPv4 address or octet range addressing
 * "192.168.0.1" or "192.168.0-255.1-254" or "192.168.1.1,3,7"
 * or even something crazy like this:
 * "*-5,8,10-12,250-*.*-5,8,10-12,250-*.*-5,8,10-12,250-*.*-5,8,10-12,250-*"
 *
 * TO DO LIST
 * 1. implement errors for wrong number formats and ranges (methods are written but not implemented)
 * 2. implement CIDR notation
 * 3. implement crazy ranges with " - " like "very complex range 1" - "very complex range 2"
 *
 * PROBLEMS:
 * if you would like to parse whole internet *.*.*.* (~ 4,22 billions) you go into limitations of TreeSet (~2,147 billions)
 * as far as I know at the moment.
 * At the moment i don't know how to solve it :)
 *
 */
public class ParseRange {
    private String target;
    private TreeSet<String> targetRange = new TreeSet<String>();
    private TreeSet<Integer> octet0;
    private TreeSet<Integer> octet1;
    private TreeSet<Integer> octet2;
    private TreeSet<Integer> octet3;

    public ParseRange(String target){
        this.target = target;
        System.out.println(this.target);
        parseTargets(this.target);
    }

    public String getTarget() {
        return target;
    }

    public TreeSet<String> getTargetRange() {
        return targetRange;
    }


    public void parseTargets(String target)
    {
        // ranges like 192.168.0.1 - 192.168.1.50 not supported at the moment
        if (target.contains(" - ")){ System.out.println("Ranges like 192.168.0.1 - 192.168.1.50 not supported at the moment!"); return; }

        String[] segments = target.split("\\.");

        // segments should be in quantity of 4!
        if(segments.length != 4){System.out.println("Wrong format of IP address/range. Should contain 4 segments xxx.xxx.xxx.xxx!"); return;}

        //if any segments contain special symbols like "-", "*" and "," we will operate with range ELSE just 1 IP
        boolean isRange = false;
        for (String segment : segments)
        {
            if (segment.contains("-") || segment.contains("*") || segment.contains(","))
            {
                isRange = true;
                break;
            }
        }

        if (isRange){
            parseTargetRange(segments);
        }
        else
            parseTarget(segments);
    }


    // This method parses one IP and fill the TreeSet<String> targetRange with it
    private void parseTarget(String[] segments)
    {
        System.out.println("It is one IP!");
        targetRange.add(target);
    }


    //основной метод, корый парсит диапазоны и генерирует множество
    private void parseTargetRange(String[] segments)
    {
        System.out.println("It is range of IPs!");
        ParseOctet o0 = new ParseOctet(segments[0]);
        octet0 = o0.getOctetParsed();
        //System.out.println(o0.getOctetParsed());

        ParseOctet o1 = new ParseOctet(segments[1]);
        octet1 = o1.getOctetParsed();
        //System.out.println(o1.getOctetParsed());

        ParseOctet o2 = new ParseOctet(segments[2]);
        octet2 = o2.getOctetParsed();
        //System.out.println(o2.getOctetParsed());

        ParseOctet o3 = new ParseOctet(segments[3]);
        octet3 = o3.getOctetParsed();
        //System.out.println(o3.getOctetParsed());

        for (Integer i0 : o0.getOctetParsed()){
            for (Integer i1 : o1.getOctetParsed()){
                for (Integer i2 : o2.getOctetParsed()){
                    for (Integer i3 : o3.getOctetParsed()){
                        targetRange.add(Integer.toString(i0) +
                                "." + Integer.toString(i1) +
                                "." + Integer.toString(i2) +
                                "." + Integer.toString(i3));
                    }
                }
            }
        }

    }


    public void printTargets(){
        System.out.println(targetRange);
    }
}
