package org.scmobile.test;

import com.bingosoft.core.web.service.impl.WebRestServiceImpl;

/**
 * Hello world!
 *
 */
public class App 
{
    public static void main( String[] args )
    {WebRestServiceImpl web=new WebRestServiceImpl();
    	for(int index=0;index<1000;index++)
    	{
    		web.sOBFreeQry("13548074395");
    		web.sShortAddMode("13908082769","ACAZ21");
    	}
        //System.out.println( "Hello World!" );
    }
}
