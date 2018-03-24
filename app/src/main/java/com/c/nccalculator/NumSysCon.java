

package com.c.nccalculator;

import java.util.Arrays;


class NumSysCon  {
    private String onestr="",twostr="",numsysresult="",St1="",St2="",temp[];
    private long i=0,one=0,longResult=0;
    private Float result=0.0f,fone=0.0f;


    String nonfractional(int numsys,boolean decto,String s)
    {
        temp=new String[100];

        if(decto==true)
        {

            if(numsys==2)
            {
                //dec to bin non fractional number
                onestr="";
                onestr=Long.toBinaryString(Long.parseLong(s));
                return onestr;

            }
            else if(numsys==8)
            {
                //dec to oct non fractional number
                onestr="";
                onestr=Long.toOctalString(Long.parseLong(s));
                return onestr;

            }
            else if(numsys==16)
            {
                //dec to hex non fractional number
                onestr="";
                onestr=Long.toHexString(Long.parseLong(s));
                //inbuilt function returns small letters hence replaced with capital letters
                onestr=onestr.toUpperCase();


            }
            return onestr;
        }
        else {

            longResult = 0;
            long radix = s.length() - 1;
            if (numsys == 16) {
                //hex to dec non fractional number
                for (i = 0; i < s.length(); i++) {
                    temp[(int) i] = ReplaceHexAlphabet(String.valueOf(s.charAt((int) i)));
                }

                for (i = 0; i < s.length(); i++) {

                    one = (long) (Long.parseLong(String.valueOf(temp[(int) i])) * Math.pow(numsys, radix));
                    longResult = longResult + one;
                    radix--;
                }
            }else {
                for(i=0;i<s.length();i++)
                {

                    one=(long) (Long.parseLong(String.valueOf(s.charAt((int) i)))*Math.pow(numsys, radix));
                    longResult=longResult+one;
                    radix--;

                }

            }
            Arrays.fill(temp, null);
            return String.valueOf(longResult);
        }
    }
    String Fractional(int numsys,String s)
    {
        //converting to dec fractional numbers
        temp=new String[100];
        one = 0;
        fone = 0.0f;
        result = 0.0f;
        long radix = -1;
        if( numsys==16) {
            //hex to dec fractional number
            for (i = 0; i < s.length(); i++) {
                temp[(int) i] = ReplaceHexAlphabet(String.valueOf(s.charAt((int) i)));
            }

            for (i = 0; i < s.length(); i++) {
                fone = (float) (Long.parseLong(temp[(int) i]) * Math.pow(numsys, radix));
                result = result + fone;
                radix--;
            }
        }else {
            for(i=0;i<s.length();i++)
            {
                fone= (float) (Long.parseLong(String.valueOf(s.charAt((int) i)))*Math.pow(numsys, radix));
                result=result+fone;
                radix--;

            }

        }
            temp=String.valueOf(result).replace("."," ").split(" ");
            St2=temp[1];
        return String.valueOf(St2);
    }
    String ReplaceHexAlphabet(String numsysresult)
    {
        if (numsysresult.contains("A"))
            numsysresult= numsysresult.replace("A", "10");
        else if (numsysresult.contains("B"))
            numsysresult= numsysresult.replace("B", "11");
        else if (numsysresult.contains("C"))
            numsysresult= numsysresult.replace("C", "12");
        else if (numsysresult.contains("D"))
            numsysresult= numsysresult.replace("D", "13");
        else if (numsysresult.contains("E"))
            numsysresult= numsysresult.replace("E", "14");
        else if (numsysresult.contains("F"))
            numsysresult= numsysresult.replace("F", "15");
        return numsysresult;
    }
    String ReplaceHexNumber(String numsysresult)
    {

        if(numsysresult.trim().contains("10"))
            numsysresult = numsysresult.replace("10", "A");
        else if(numsysresult.trim().contains("11"))
            numsysresult = numsysresult.replace("11", "B");
        else if(numsysresult.trim().contains("12"))
            numsysresult = numsysresult.replace("12", "C");
        else if(numsysresult.trim().contains("13"))
            numsysresult = numsysresult.replace("13", "D");
        else if(numsysresult.trim().contains("14"))
            numsysresult = numsysresult.replace("14", "E");
        else if(numsysresult.trim().contains("15"))
            numsysresult = numsysresult.replace("15", "F");
        return numsysresult;

    }
    String toDec(String s,int numsys)
    {
        if(s.contains(".") && !s.endsWith("."))
        {
            temp=s.replace("."," ").split(" ");
            St1=temp[0];
            St2=temp[1];
            onestr=nonfractional(numsys,false, St1);
            twostr=Fractional(numsys, St2);
            numsysresult=onestr+"."+twostr;
        }
        else if(!s.contains("."))
            numsysresult=nonfractional(numsys,false, s);
            numsysresult=zeroEmitter(numsysresult);
        return numsysresult;
    }


    String  decto(String s,int numsys)
    {
        //dec to other all

        long tens=0;
        long aprox;
        if(!s.contains("."))
        {
            //dec to bin,oct,hex non fractional number
            numsysresult=nonfractional(numsys,true,s);

            return numsysresult;
        }
        else if(s.contains(".") && !s.endsWith("."))
        {
            // dec to bin,oct,hex fractional number
            aprox=0;

            temp=s.replace("."," ").split(" ");
            St1=temp[0];
            St2=temp[1];
            onestr= nonfractional(numsys,true,St1);
            twostr=St2;
            numsysresult="";
            do
            {

                for(i=1;i<=twostr.length();i++)
                {
                    tens=(long) (Math.pow(10,i));

                }
                result=(Float.parseFloat(twostr)/tens)*numsys;
                result=Float.parseFloat(zeroEmitter(String.valueOf(result)));
                temp=String.valueOf(result).replace(".", " ").split(" ");
                St1=temp[0];
                St2=temp[1];
                numsysresult=numsysresult+St1;
                twostr=St2;
                if(numsys==16)
                    numsysresult=ReplaceHexNumber(numsysresult);


                aprox++;


            }while(aprox!=4);//approximate run time
            numsysresult=onestr+"."+numsysresult;


            numsysresult=zeroEmitter(numsysresult);
            return numsysresult;
        }
        else

            return null;
    }

    String zeroEmitter(String s)
    {
        int length = s.length();

        if(s.contains(".") && !s.endsWith(".")&& s.endsWith("0"))
        {
            for(int i=s.length()-1;i>=0;i--)
            {

                if(s.charAt(i)=='0' && s.charAt(i-1)!='.')
                    length=length-1;
                else
                    break;
            }

        }

        s=s.substring(0,length);
        return s;
    }
}
