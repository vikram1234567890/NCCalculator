package com.c.nccalculator;

import android.app.Activity;
import android.content.Context;
import android.widget.Toast;

import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.StringTokenizer;


public class Calculation extends StartActivity{

    int j=0,operCount=0,numberCount=0,openBracket=0,closedBracket=0,count=0;
    long fact=1,factnum=0;

    Double num1=0.0,num2=0.0,result=0.0,temp=0.0;
    String array[]=new String[100],tempArray[]=new String[100],bracketArray[]=new String[100],bracketStorage="",stringNumber="";
    String symbols[]={"^","/","*","+","-","(",")"};



    private void brackets(String array[])
    {
        Arrays.fill(tempArray,null);
        Arrays.fill(bracketArray,null);
        stringNumber="";
        int bracketPosition=0;

        for (int i=0;i<array.length;i++)
        {

            if(array[i].contains(")"))
            {
                closedBracket=i-1;
                array[i]="";

                for (int i1=i; i1>=0; i1--) {

                    if (array[i1].contains("(")) {
                        openBracket = i1 + 1;
                        array[i1] = "";
                        bracketPosition = i1;

                        break;
                    }

                }

                break;
            }
        }

        for(int i=openBracket;i<=closedBracket;i++)
        {

            for(int k=0;k<bracketArray.length;k++)
            {
                if(bracketArray[k]==null)
                {
                    bracketArray[k]=array[i];

                    break;
                }
            }
            stringNumber=stringNumber+array[i];

            array[i]="";
        }

        for (int i=0;i<array.length;i++)
        {
            if(array[i]!=null)
                tempArray[i]=array[i];//store array in temporary area to avoid loss of data
            else
                break;
        }

        operMatcher(stringNumber, bracketArray);
        Arrays.fill(array,null);
        for (int i=0;i<array.length;i++)
        {
            if(i==bracketPosition)
            {
                array[bracketPosition]=bracketStorage;
            }
            else
                array[i]=tempArray[i];

        }

        joiner(array);
    }
    private void power(String array[])
    {

        for (int i=0;i<array.length;i++)
        {
            if(array[i].contains("^"))
            {
                num1=preProcessor(array[i-1]);
                array[i-1]="";
                num2=preProcessor(array[i+1]);
                array[i+1]="";
                result=Math.pow(num1, num2);
                array[i]=fmt(String.valueOf(result));
                break;
            }
        }
        joiner(array);
    }
    private void div(String array[])
    {

        for (int i=0;i<array.length;i++)
        {
            if(array[i].contains("/"))
            {
                num1=preProcessor(array[i-1]);
                array[i-1]="";
                num2=preProcessor(array[i+1]);
                array[i+1]="";
                result=num1/num2;
                array[i]=fmt(String.valueOf(result));
                break;
            }
        }

        joiner(array);
    }

    private void mul(String array[])
    {

        for (int i=0;i<array.length;i++)
        {
            if(array[i].contains("*"))
            {
                num1=preProcessor(array[i-1]);
                array[i-1]="";
                num2=preProcessor(array[i+1]);
                array[i+1]="";
                result=num1*num2;
                array[i]=fmt(String.valueOf(result));

                break;
            }
        }
        joiner(array);
    }

    private void add(String array[])
    {

        for (int i=0;i<array.length;i++)
        {

            if(array[i].contains("+"))
            {
                num1=preProcessor(array[i-1]);
                array[i-1]="";
                num2=preProcessor(array[i+1]);
                array[i+1]="";
                result=num1+num2;
                if(i>=2 && num1<num2 && String.valueOf(num1).contains("-") && !String.valueOf(num2).contains("-") && array[i-2]!="(" && array[i-2]!=")" &&array[i-2]!="^" && array[i-2]!="/" &&array[i-2]!="*" &&array[i-2]!="+" &&array[i-2]!="-" )
                {
                    array[i]="+"+fmt(String.valueOf(result));
                }
                else
                    array[i]=fmt(String.valueOf(result));

                break;
            }
        }

        joiner(array);
    }
    private void sub(String array[])
    {

        for (int i=0;i<array.length;)
        {
            num1=preProcessor(array[i]);
            array[i]="";
            num2=preProcessor(array[i+1]);
            array[i+1]="";
            result=num1+num2;
            array[i]=fmt(String.valueOf(result));
            break;
        }

        joiner(array);
    }

    private Double preProcessor(String str)
    {
        temp=0.0;
        str=str.trim();

        if(str.charAt(0)=='-')
        {
            str=str.replace("-", "");
            if(str.contains("√"))
            {
                temp=-sqroot(str);
            }
            else if(str.contains("!"))
            {
                temp= Double.valueOf(-facto(str));
            }
            else
                temp=-Double.parseDouble(str);
        }
        else
        {
            if(str.contains("√"))
            {
                temp=sqroot(str);
            }
            else if(str.contains("!"))
            {
                temp= Double.valueOf(facto(str));
            }
            else
                temp=Double.parseDouble(str);
        }
        return temp;
    }
    long facto(String result)
    {

        fact=1;
        char subch=0;
        if(result.charAt(0)=='-')
        {
            result=result.replace("-","");
            subch='-';
        }
        if (result.endsWith("!")) {
            factnum = Integer.parseInt(result.replace("!", ""));
            for (long i = 1; i <= factnum; i++) {
                fact = fact * i;
            }
            if (subch == '-')
                fact = -fact;
            return fact;
        }
        else return Long.parseLong(result);


    }
    Double sqroot(String result)
    {
        temp=0.0;
        char subch=0;
        if(result.charAt(0)=='-')
        {
            result=result.replace("-","");
            subch='-';
        }
        if(result.charAt(0)=='-')
            result=result.replace("-","");
        temp=Double.parseDouble(result.replace("√",""));
        temp=(Double) Math.sqrt(temp);
        if(subch=='-')
            temp=-temp;
        return temp;
    }
    private String[] check(String array[]){
        String temp[]=new String[array.length];
        boolean status=false;

        for(int i=0;i<array.length ;i++)
        {
            if( array[i]!=null && array[i].trim().length()!=0)
            {
                for(int i1=0;i1<array.length ;i1++)
                {
                    if( temp[i1]==null )
                    {
                        temp[i1]=array[i];

                        break;
                    }

                }
            }

        }

        for(int i=0;i<temp.length ;i++)
        {

            for(int j=0;j<symbols.length;j++)
            {

                if(i+1<temp.length && temp[i]!=null && temp[i+1]!=null  && temp[i].trim().length() != 0 && temp[i+1].trim().length()!=0 )
                {
                    if( !temp[i].contains(symbols[j]) && !temp[i+1].contains(symbols[j]))
                    {
                        status=true;
                    }
                    else
                    {
                        status=false;
                        break;
                    }

                }
            }
            if(status==true && i+1<temp.length && temp[i+1]!=null)
            {
                temp[i+1]="+"+temp[i+1];

            }
        }
        return temp;
    }
    private void joiner(String array[])
    {

        stringNumber="";

        array=check(array);

        for(int i=0;i<array.length;i++)
        {



            if(array[i]!=null)
            {
                stringNumber=stringNumber+array[i];

            }
            else break;
        }

        parser(stringNumber);
    }

    private String fmt(String s)
    {

        if(s.endsWith("0") && s.charAt(s.length()-2)=='.')
        {
            s=s.substring(0,s.length()-2);
        }

        return s;
    }

    private void operMatcher(String s,String array[])
    {

        if(s.contains("-"))
        {
            count=0;
            count=s.length()-s.replace("-","").length();

        }

        if(s.contains("(") && s.contains(")"))
            brackets(array);
        else if(s.contains("^"))
            power(array);
        else if(s.contains("/"))
            div(array);
        else if(s.contains("*"))
            mul(array);
        else if(s.contains("+"))
            add(array);
        else if(s.contains("-") && (count>1 || (s.charAt(0)!='-' && count>=1)))
            sub(array);
        else
            output(s);
    }
    void output(String s)
    {
        bracketStorage=s;

        if(spinindex==0 || spinindex ==2) {
            int count4 = 0;
            if (s.contains("-")) {
                count4 = s.length() - s.replace("-", "").length();
            }
            if (s.contains("!") && !s.contains("(") && !s.contains(")") && !s.contains("^") && !s.contains("/") && !s.contains("*") && !s.contains("+") && (s.charAt(0) == '-' || count4 <= 1))

                eText1.setText(lengthCutter(s));
            else
                // eText1.setText(df.format(Double.parseDouble(s)));
                eText1.setText(lengthCutter(s));
        }

        else
            eText3.setText(lengthCutter(s));



    }



    private String lengthCutter(String s)
    {

        String s1="";
        DecimalFormat d=new DecimalFormat("0.#");
        d.setMaximumFractionDigits(8);

        s=d.format(Double.parseDouble(s));
        try {

            for(int i=0;i<s.length();i++)
            {
                s1=s1+String.valueOf(s.charAt(i));
                if(s.charAt(i)=='.')
                {i++;
                    for(int i1=i;i1<i+4;i1++)
                    {
                        s1=s1+String.valueOf(s.charAt(i1));
                    }
                    break;
                }

            }

        } catch (Exception e) {
            // TODO: handle exception
        }
        return s1;

    }
    void parser(String s) {
        operCount = 0;
        numberCount = 0;
        openBracket = 0;
        closedBracket = 0;
        stringNumber = "";
        j = 0;
        Arrays.fill(array, null);
        String temp = "";
        for (int i = 0; i < s.length(); i++) {
            if (s.charAt(i) == '-' && s.charAt(i + 1) == '-') {
                temp = temp + "+";
                i = i + 2;

            }
            temp = temp + String.valueOf(s.charAt(i));
        }
        s = temp;
        for (int i = 0; i < s.length(); i++) {

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
            for (; j < array.length; ) {
                stringNumber = stringNumber + String.valueOf(s.charAt(i));
                array[j] = stringNumber;

                break;
            }


        }
        for(int i=0;i<array.length;i++)
        {

            if(array[i]!=null)
            {
                if(array[i].contains("("))
                    openBracket++;
                else if(array[i].contains(")"))
                    closedBracket++;
                else
                    numberCount++;
            }
            else
                break;

        }


        if( openBracket==closedBracket)
        {

            operMatcher(s, array);

        }


    }

}
