# Java Parse IP Range

This example parses the targets ip/range using different approach and return TreeSet with this range.

Accepts:
 1. IPv4 address or octet range addressing
 "192.168.0.1" or "192.168.0-255.1-254" or "192.168.1.1,3,7"
 or even something crazy like this:
 \*-5,8,10-12,250-\*.\*-5,8,10-12,250-\*.\*-5,8,10-12,250-\*.\*-5,8,10-12,250-\*
 
 TO DO LIST
 1. implement errors for wrong ranges (numbers less more then 255)
 2. implement CIDR notation
 3. implement crazy ranges with " - " like "very complex range 1" - "very complex range 2"
 
 PROBLEMS:
 if you would like to parse whole internet *.*.*.* (~ 4,22 billions) you go into limitations of TreeSet (~2,147 billions)
 as far as I know at the moment.
 At the moment i don't know how to solve it :)
