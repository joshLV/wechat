package com.bingosoft.core;

import java.util.Date;

import com.bingosoft.core.mysql.service.impl.OrderNewsServiceImpl;
import com.bingosoft.core.web.service.IWebRestService;
import com.bingosoft.core.web.service.impl.CsfWebServiceImpl;
import com.bingosoft.core.web.service.impl.EsbWebserviceImpl;
import com.bingosoft.core.web.service.impl.WebRestServiceImpl;
import com.bingosoft.models.csf.OfferItemOutputDto;
import com.bingosoft.models.rest.dto.MarkActHandleOutputDto;
import com.bingosoft.models.rest.dto.RestResponseOutputDto;
import com.bingosoft.persist.redis.dao.impl.RedisServiceImpl;
import com.bingosoft.utils.DateUtils;
import com.bingosoft.utils.LuaScriptUtils;
import com.bingosoft.utils.crypt.TokenUtils;

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
    	//WebRestServiceImpl web=new WebRestServiceImpl();
    	//web.updateMainCharges("13908082769", "ACAZ25126");
    	CsfWebServiceImpl impl=new CsfWebServiceImpl();
    	//impl.CSCHTSubmitOffersOrder("13908082769", "ACAZ29271");
    	//impl.subscribeInfoCentral("13908082769", "ACAZ29271");
    	//impl.CSCHTSubmitCampaignsOrder("13908082769", "AZ205010");
    	//impl.smsCentral("13548074395", "this message for test");
    	//OfferItemOutputDto dto=impl.subscribeInfoCentral("13908082769","ACAZ29271");
    	//System.out.println(dto.getOfferName());
    	//impl.flowAdditionalDeal();
    	//impl.CSCHQFareBalance();
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
    //	web.MarkActHandle("13908082769", "AEC9","AEC90216");
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
    	try {
			//System.out.println(TokenUtils.decrypt("26F86C8245045FC656E3EE6B156D5494F169C7AE756320DE625C91AF0769151E7993AF4E6BAD8E33EB1166282DDC20200749BBFE436A51F8F5638FABC3E5095C45DF0FA7C652C30AEEEBD49591B733E68A8B69C183D7B57C28EBB23182FF9BE35180E59785EAEBB24BF8D925A0ABF268EECDBC4C800E262AE7B3C9A8CB4FF16166DD8D51CDD3631C3756B53992CD99016B58B395BA7FEF7A6F4BD508C4C213D8C1CED421F62363984100FB4DEDBE26A8CB067648DFE7EF446416E7BB7D2A26224D01E5A4C7AAE9DFA4B74971883E28CC99F579EFCB5512AED1D6196F5F5FFDEE9699819DC29A5CDE4D6881FA9D1E8A2F2465086342798CDCFF408D25D1FE39FE71223B3513BC2EA21CE112C438F2C2D182D8D436985E4EF734B70A648929EBFEE5E540F81365BD6F5E6C8D0A2C0805FF3A16F35F310886C4E5874E40DDF1B97751EBFC529E7D46A7E316A8A70F71C36975961BA4CBE32E48AD0DF3CD6FFCBE08CAE8FE1EF8A07D863DD8D877863F4CA196883E423B55F6C41E2188DD53601A473676DADC4FFABDA5C6BD7D868ECA495B92F6D3213C224A11D73797E1312D98AA61758ED38D4CD1A63D6FA50ED3310530C000A00AE7EFE8FA"));
    		System.out.println(TokenUtils.decrypt("26F86C8245045FC656E3EE6B156D5494F169C7AE756320DE625C91AF0769151E7993AF4E6BAD8E33EB1166282DDC20200749BBFE436A51F8F5638FABC3E5095C45DF0FA7C652C30AE2F30172DDAA91BC4143CEE3547A680C43703ADF5FACDF89C1E7A2C77AF23D6D7302D3E01E3F8E5CF6C68E7EFC377DF2F767D839EE47CD302128541E5E86FC41A7E12F4BDE835F12A4375A6B39A2886A99EF39E80E055EC476A0BEF387D11E2BA8A54240CE4EB46A78C8CE9DE4421FDD4FC462AC7CB0E1948440C0FEE25A0297A3D2FF3312513A46AD009E10B181B0B6A58597986637DF07A815424EE6457537B31C54B491E25EA9CE1BEEC449A2A671EA70D4847D7630EF5A95E1492939902E5E91A017C7EC3D5D78942C1D383EECCED1691081F0E7B7AAE8D44396670C480E4D7235F22C961455B6FE7A88C0D5B9EBBA120665989134F1931CF82685A639331190FE19619C949ADB2EC2C3643ED4A3AA5900F286E0AF356918EEAB66D31531551CCA2390D0047E3D90E7925D7B0683779829DC6D1820207E039CE49485A46049416536F28A4585FB2A61E8A82C3528CB4FB8D5C82A8802DE3747B4A67756BE3757EB334442C8A1ABB1CBBB69F9F08C"));
			System.out.println(TokenUtils.encrypt("{\"openId\":\"oJAS9uLyyJsf2YLz3T1HJSo9t4z8\",\"userId\":\"13880255284\",\"phoenNo\":\"13880255284\",\"headImg\":\"http://wx.qlogo.cn/mmopen/BjicoCB8POcPTmeKEqyAGpibNDWCCOCQxYaELMvDInr8Kk0zk5eicReNshGypwbKjkJ1uTdZoPWzibyIH7NibFyaqIg/0\",\"userName\":\"與夢同荇\",\"time\":\"Apr 24, 2018 10:19:31 PM\",\"subscribeTime\":\"1498018759\"}"));
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
    }
}
