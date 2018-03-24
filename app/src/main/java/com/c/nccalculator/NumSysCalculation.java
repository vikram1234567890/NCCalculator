
package com.c.nccalculator;


import android.content.Context;
import android.widget.EditText;

import java.util.Arrays;

public class NumSysCalculation  extends NumSysCon{
    private String bin[]=new String[100],dec[]=new String[100],oct[]=new String[100],hex[]=new String[100],stringNumber="",array[]=new String[100],strBin="",strDec="",strOct="",strHex="";
    private int numberCount,j=0;
    private boolean minusChar=false;
Context context;
    public NumSysCalculation(Context context)
    {
        this.context=context;
    }
    private void joiner(String array[], EditText editText)
    {
        stringNumber="";

        for(int i=0;i<array.length;i++)
        {
            if(array[i]!=null)

                stringNumber=stringNumber+array[i];

            else break;
        }
         editText.setText(stringNumber);


    }

    void  nsParser(String s,int spinIndex)
    {
StartActivity startActivity=new StartActivity();
        j=0;
        String temp = "";

        Arrays.fill(array,null);
        for (int i = 0; i < s.length(); i++) {

                if (s.charAt(i) == '-' && s.charAt(i + 1) == '-') {

                    i = i + 2;

                }

            temp = temp + String.valueOf(s.charAt(i));
        }
        s=temp;
        for (int i=0;i<s.length() ;i++)
        {

            if(array[j]!=null) {
                if (s.charAt(i) == '(' || stringNumber.contains("(")) {
                    stringNumber = "";
                    j = j + 1;
                } else if (s.charAt(i) == ')' || stringNumber.contains(")")) {
                    stringNumber = "";
                    j = j + 1;
                } else if (s.charAt(i) == '^' || stringNumber.contains("^")) {
                    stringNumber = "";
                    j = j + 1;
                } else if (s.charAt(i) == '/' || stringNumber.contains("/")) {
                    stringNumber = "";
                    j = j + 1;
                } else if (s.charAt(i) == '*' || stringNumber.contains("*")) {
                    stringNumber = "";
                    j = j + 1;
                } else if (s.charAt(i) == '+' || stringNumber.contains("+")) {
                    stringNumber = "";
                    j = j + 1;
                } else if (i != 0 && s.charAt(i) == '-') {
                    stringNumber = "";
                    j = j + 1;
                }
            }
            for(;j<array.length;)
            {
                stringNumber=stringNumber+String.valueOf(s.charAt(i));
                array[j]=stringNumber;
                break;
            }

        }

        for (int i=0;i<array.length;i++)
        {
            if(array[i]!=null)
            {
                if (spinIndex == 1)
                {
                    dec[i]=array[i];
                    oct[i]=array[i];
                    hex[i]=array[i];
                    if(!array[i].contains("(") && !array[i].contains(")") && !array[i].contains("^") && !array[i].contains("/") && !array[i].contains("*") && !array[i].contains("+") && !array[i].equals("-"))
                    {
                        dec[i]="";
                        oct[i]="";
                        hex[i]="";
                        if (array[i].contains("-"))
                        {
                            array[i]=array[i].replace("-","").trim();
                            minusChar=true;
                        }


                        if (minusChar==true) {
                            minusChar=false;
                            dec[i] ="-"+toDec(array[i],2);
                            oct[i] = "-"+decto(dec[i].replace("-","").trim(), 8);
                            hex[i] = "-"+decto(dec[i].replace("-","").trim(), 16);
                        }
                        else
                        {
                            dec[i] =toDec(array[i],2);
                            oct[i] = decto(dec[i], 8);
                            hex[i] = decto(dec[i], 16);
                        }
                    }

                }
                if (spinIndex ==2)
                {
                    bin[i]=array[i];
                    oct[i]=array[i];
                    hex[i]=array[i];
                    if(!array[i].contains("(") && !array[i].contains(")") && !array[i].contains("^") && !array[i].contains("/") && !array[i].contains("*") && !array[i].contains("+") && !array[i].equals("-"))
                    {

                        bin[i]="";
                        oct[i]="";
                        hex[i]="";
                        if (array[i].contains("-"))
                        {
                            array[i]=array[i].replace("-","").trim();
                            minusChar=true;
                        }

                        if (minusChar==true) {
                            minusChar=false;
                            oct[i] ="-"+decto(array[i],8);
                            bin[i] = "-"+decto(array[i],2);
                            hex[i] = "-"+decto(array[i],16);
                        }
                        else
                        {
                            bin[i]=decto(array[i],2);
                            oct[i]=decto(array[i],8);
                            hex[i]=decto(array[i],16);
                        }


                    }

                }
                if (spinIndex == 3)
                {
                    dec[i]=array[i];
                    bin[i]=array[i];
                    hex[i]=array[i];
                    if(!array[i].contains("(") && !array[i].contains(")") && !array[i].contains("^") && !array[i].contains("/") && !array[i].contains("*") && !array[i].contains("+") && !array[i].equals("-"))
                    {

                        dec[i]="";
                        bin[i]="";
                        hex[i]="";
                        if (array[i].contains("-"))
                        {
                            array[i]=array[i].replace("-","").trim();
                            minusChar=true;
                        }


                        if (minusChar==true) {
                            minusChar=false;
                            dec[i] ="-"+toDec(array[i],8);
                            bin[i] = "-"+decto(dec[i].replace("-","").trim(), 2);
                            hex[i] = "-"+decto(dec[i].replace("-","").trim(), 16);
                        }
                        else
                        {
                            dec[i] =toDec(array[i],8);
                            bin[i]=decto(dec[i],2);
                            hex[i]=decto(dec[i],16);
                        }


                    }

                }
                if (spinIndex == 4)
                {
                    dec[i]=array[i];
                    oct[i]=array[i];
                    bin[i]=array[i];
                    if(!array[i].contains("(") && !array[i].contains(")") && !array[i].contains("^") && !array[i].contains("/") && !array[i].contains("*") && !array[i].contains("+") && !array[i].equals("-"))
                    {

                        dec[i]="";
                        oct[i]="";
                        bin[i]="";
                        if (array[i].contains("-"))
                        {
                            array[i]=array[i].replace("-","").trim();
                            minusChar=true;
                        }


                        if (minusChar==true) {
                            minusChar=false;
                            dec[i] ="-"+toDec(array[i],16);
                            oct[i] = "-"+decto(dec[i].replace("-","").trim(), 8);
                            bin[i] = "-"+decto(dec[i].replace("-","").trim(), 2);
                        }
                        else
                        {
                            dec[i] =toDec(array[i],16);
                            oct[i]=decto(dec[i],8);
                            bin[i]=decto(dec[i],2);
                        }


                    }

                }
            }
            else
                break;
        }
        if(spinIndex==1)//bin
        {
            joiner(dec,startActivity.eText3);
            joiner(oct,startActivity.eText4);
            joiner(hex,startActivity.eText5);
        }
        if(spinIndex==2)//dec
        {
            joiner(bin, StartActivity.eText2);
            joiner(oct,startActivity.eText4);
            joiner(hex,startActivity.eText5);
        }
        if(spinIndex==3)//oct
        {
            joiner(dec,startActivity.eText3);
            joiner(bin, StartActivity.eText2);
            joiner(hex,startActivity.eText5);
        }
        if(spinIndex==4)//hex
        {
            joiner(dec,startActivity.eText3);
            joiner(oct,startActivity.eText4);
            joiner(bin, StartActivity.eText2);
        }

    }

}