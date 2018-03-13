package com.bingosoft.core;

import java.util.Date;

import com.bingosoft.core.mysql.service.impl.OrderNewsServiceImpl;
import com.bingosoft.core.web.service.IWebRestService;
import com.bingosoft.core.web.service.impl.EsbWebserviceImpl;
import com.bingosoft.core.web.service.impl.WebRestServiceImpl;
import com.bingosoft.models.rest.dto.MarkActHandleOutputDto;
import com.bingosoft.models.rest.dto.RestResponseOutputDto;
import com.bingosoft.persist.redis.dao.impl.RedisServiceImpl;
import com.bingosoft.utils.DateUtils;
import com.bingosoft.utils.LuaScriptUtils;

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
    	web.updateMainCharges("13908082769", "ACAZ25126");
//    	for(int index=0;index<=1000;index++)
//    	{
//web.sShortAddMode("13908082769","ACAZ26285");
  //  	web.sOBFreeQry("13548074395");
//    		System.out.println(new Date());
    		//web.sShortAddMode("13880255284","AZ410038");
    	//web.sOBFreeQry("13908082769");
    	//web.sYdscOrdQry("13548074395");
//    		System.out.println(new Date());
//    	}
    	//web.sShortAddMode("15884479767", "ACAZ21698");
    	//web.qryGdWindQpT("20930021087");
    	//web.qryWifiMsgInfo("20930021087");
    	//web.sQryGdWindMsgL("20930021087");
    	//web.qryGdwindInfo("20930021087");
    	//web.sOBFreeQry("13618101332");
    	//for(int index=0;index<3;index++)
//    	RestResponseOutputDto<Object> obj=	web.MarkActHandle("13908082769", "AZ41","AZ410038");
//    	System.out.println(obj.getResMsg());
    	//web.MarkActHandle("13880255284", "AZ41","AZ410038");
//    	RestResponseOutputDto<MarkActHandleOutputDto> dto= web.MarkActHandle("13880613509", "AZ20","AZ203011");
//    	if(dto.getOutData()!=null&&dto.getOutData().getPASS_FLAG().equals("Y"))
//    	{
//    		System.out.println(dto.getOutData().getCREATE_ACCEPT());
//    	}else
//    	{
//    		System.out.println("未加载");
//    	}
    	
//    	System.out.println(DateUtils.getTimeInMillis());
//    	System.out.println(DateUtils.getTimeInMillis()+30000);
//    	OrderNewsServiceImpl service= new OrderNewsServiceImpl();
//    	service.addOrderNews("13548074395", 5);
    	
//    	EsbWebserviceImpl esb=new EsbWebserviceImpl();
//    	esb.sUserOrdQry("13548074395");
//    	esb.sUserGoodsQry("13548074395");
    	
    }
}
