package com.bingosoft.core;

import com.bingosoft.core.mysql.service.impl.OrderNewsServiceImpl;
import com.bingosoft.core.web.service.IWebRestService;
import com.bingosoft.core.web.service.impl.WebRestServiceImpl;

import junit.framework.Test;
import junit.framework.TestCase;
import junit.framework.TestSuite;

/**
 * Unit test for simple App.
 */
public class AppTest 
    extends TestCase
{
    /**
     * Create the test case
     *
     * @param testName name of the test case
     */
    public AppTest( String testName )
    {
        super( testName );
    }

    /**
     * @return the suite of tests being tested
     */
    public static Test suite()
    {
        return new TestSuite( AppTest.class );
    }

    /**
     * Rigourous Test :-)
     */
    public void testApp()
    {
    	WebRestServiceImpl web=new WebRestServiceImpl();
    	web.sPFeeQuery("sOBFreeQry");
//    	OrderNewsServiceImpl service= new OrderNewsServiceImpl();
//    	service.addOrderNews("13548074395", 5);
    }
}
