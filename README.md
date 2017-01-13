# About the cash register

The Cash register application was the final exam for my **Introductory Programming** class during the Autumn 2016 semester at the *IT University of Copenhagen*. 

The task had to be finished in 24 hours and a mandatory oral examination was carried out one month later to ensure I both understand the solution and that I have developed it myself.

The **description** of the exam assignment is in the following paragraph.

The version submitted at the end of the exam is seen in [the initial commit](https://github.com/dirtydeeds91/CashRegister/commit/c6bc49eaa697968f42f556b802062ab75aa6aa85). The submitted solution features the mandatory task plus all of the mentioned extensions.

**Course information and curriculum** is at the end of the readme.


# Cash register description

You must write at program in Java called CashRegister, which can be used to tally up items at a danish grocery store checkout line. As a minimum, the program should be able to tell what the total cost the items purchased is.

The items for sale are described in a comma separated file called [prices.txt](https://github.com/dirtydeeds91/CashRegister/blob/master/data/prices.txt), using the following format for each line:
```
<barcode>,<category>,<name>,<kr>,<øre>
```

As an example, 1L of milk could be represented in the file as:
```
0 580524 463272,MEJERI,SKUMMETMÆLK,5,95
```

In this example, the bar code is `0 580524 463272`. The category is `MEJERI`. The name is `SKUMMETMÆLK`. The price is `DKK 5,95`.

The items of a purchase are given as individual bar code values, with one bar code per line. For instance, someone buying two 1 L bottles of milk and a packet of oats would be given as a file (e.g., called [bar1.txt](https://github.com/dirtydeeds91/CashRegister/blob/master/data/bar1.txt)) with the contents:
```
0 580524 463272
0 580524 463272
1 173648 738266
```
where the last bar code is for the oats (which costs 11,95). For this purchase, the total price is 5,95 + 5,95 + 11,95 = 23,85. One way to report this could simply be to the command line:
```
$ java -jar CashRegister.jar bar1.txt
TOTAL 23,85
```

**Extensions to the program:**
* Display an itemized receipt, which could in theory be printed on receipt paper. The item-names are left-aligned and the item-prices are right-aligned. One such receipt could be:
```
$ java -jar CashRegister.jar bar1.txt
SKUMMETMÆLK                      5,95
SKUMMETMÆLK                      5,95
HAVREGRYN                       11,95

TOTAL 23,85
```
Hint: `String.format("%10s", "NAME")` results in the string "      NAME", which is 10 characters long and NAME is right-aligned. Similarly, `String.format("%-10s", "NAME")` results in "NAME      ", where NAME is left aligned.

* Compact multiple identical items into a "multi-item" expression on the receipt. This should work even if the multiple items are mixed in with the other items (i.e., the identical bar codes do not show up right after eachother in the input file).
```
$ java -jar CashRegister.jar bar1.txt
SKUMMETMÆLK                      
  2 x 5,95                      11,90
HAVREGRYN                       11,95

TOTAL 23,85
```

* Item discounts. Some items are on sale, while other items have a bulk purchase discount. This information is given as a comma separated file (called [discounts.txt](https://github.com/dirtydeeds91/CashRegister/blob/master/data/discounts.txt)) using the format of each line:
```
<barcode>,<limit>,<kr>,<øre>
```
where barcode is the barcode of the item for sale, limit is how many of the item must be purchased to trigger the discount, and finally that the discounted per-item price would be <kr>,<øre>. As an example, say that oats were on discount at a price of DKK 10,00, as long as you bought at least two. This would show up in [discounts.txt](https://github.com/dirtydeeds91/CashRegister/blob/master/data/discounts.txt) as a line:
```
1 173648 738266,2,10,0
```

This also allows for single item discounts, simply by having limit be 1. Let's say that [bar2.txt](https://github.com/dirtydeeds91/CashRegister/blob/master/data/bar2.txt) contains the following bar codes (same as [bar1.txt](https://github.com/dirtydeeds91/CashRegister/blob/master/data/bar1.txt), but with 3 boxes of oats):
```
0 580524 463272
1 173648 738266
1 173648 738266
0 580524 463272
1 173648 738266
```

then running the program should say something like:
```
SKUMMETMÆLK
  2 x 5,95                      11,90
HAVREGRYN
  3 x 11,95                     35,85
RABAT                            5,85-

TOTAL                           41,90
```
Notice that the '-' sign is on the right instead of the left, as is customary on such receipts.

* Display additional information at the bottom of the receipt. E.g., the danish sales tax is 25% on top of all prices (i.e. 20% of the price you pay), and this is typically shown at the bottom of the receipt. Furthermore, some grocery stores have a special discount system, where you get "mærker" for every purchase of DKK 50. This would also show up at the bottom of the receipt:
```
...
TOTAL                          101,90

KØBET HAR UDLØST 2 MÆRKER

MOMS UDGØR                      20,38
```

* Sort the items into categories, so there is a section with all the items from each category. Again, this should work independently of the order in which the barcodes are scanned. It could look like this:
```
         * FRUGT & GRØNT *
KARTOFLER OVN & MOS             18,00
ØKO GULERØDDER                  10,00

            * SLAGTER *
HK SVIN/KALV                    21,95
HAMBURGERRYG                    13,95
RABAT                            3,95-

             * MEJERI *
FRILANDSÆG                      25,95
SKUMMETMÆLK
  3 x 5,95                      17,85
SKYR YOGHURT
  2 x 22,75                     45,50
RABAT                           19,50-
KÆRGÅRDEN                       14,95
VORES PISKEFLØDE                16,50

         * ØVR. FØDEVARER *
BØNNEKAFFE                      55,50
KERNEBRØD                       20,75
BLØD NUGAT 150G                 26,25
GRØNLANGKÅL                     28,00
RABAT                           10,00-
```

* Prepare a GUI for the program. There are many possible extensions to the GUI. Here are a few ideas:
  * Allow the user to add and remove items from the receipt, without requiring the user to remember bar codes.
  * Keep a running tally of the contents of the cash register (i.e., the grand total of all receipts that have been processed).
  * Maintain a full history of all receipts, so they can be recalled for later use.
  * Allow the user to add new items to the [prices.txt](https://github.com/dirtydeeds91/CashRegister/blob/master/data/prices.txt) file
  * Allow the user to edit the discounts
  * Allow the user to load and save [discounts.txt](https://github.com/dirtydeeds91/CashRegister/blob/master/data/discounts.txt) files

**Files for testing your solution:**

[prices.txt](https://github.com/dirtydeeds91/CashRegister/blob/master/data/prices.txt) and [discounts.txt](https://github.com/dirtydeeds91/CashRegister/blob/master/data/discounts.txt)
[bar1.txt](https://github.com/dirtydeeds91/CashRegister/blob/master/data/bar1.txt) and a matching [receipt1.txt](https://github.com/dirtydeeds91/CashRegister/blob/master/data/receipt1.txt)
[bar2.txt](https://github.com/dirtydeeds91/CashRegister/blob/master/data/bar2.txt) and a matching [receipt2.txt](https://github.com/dirtydeeds91/CashRegister/blob/master/data/receipt2.txt)
[bar3.txt](https://github.com/dirtydeeds91/CashRegister/blob/master/data/bar3.txt) and a matching [receipt3.txt](https://github.com/dirtydeeds91/CashRegister/blob/master/data/receipt3.txt)
[bar4.txt](https://github.com/dirtydeeds91/CashRegister/blob/master/data/bar4.txt) and a matching [receipt4.txt](https://github.com/dirtydeeds91/CashRegister/blob/master/data/receipt4.txt)
[bar5.txt](https://github.com/dirtydeeds91/CashRegister/blob/master/data/bar5.txt) and a matching [receipt5.txt](https://github.com/dirtydeeds91/CashRegister/blob/master/data/receipt5.txt)

# Course description
The course manager and teacher was *Troels Bjerre Lund*, an Assistant Professor at ITU.

The main focus of the course was to learn the basics of object oriented programming with Java. The course curriculum included the following lectures:
* Types of data
* Conditionals and loops
* Arrays
* Input and output
* Functions
* Libraries
* Using data types
* Designing and creating data types
* Object oriented programming
* Generics
* Iterators
* Interfaces
* Exceptions
* GUI programming with Swing
* Event driven programming
* Class inheritance and abstract classes
* Recursion
* Sorting and searching using recursion
* The Model-View-Controller design pattern
* The Observer design pattern

The mandatory activities included 11 exercises, which needed to be handed in and approved by the TA's in order to register for this exam. My solutions to those, plus a description of each exercise, will be uploaded in a new repository (soon).
