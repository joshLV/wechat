package com.bingosoft.utils.crypt;

import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;
import javax.crypto.spec.IvParameterSpec;

public class TokenUtils {
	private byte[] desKey;
	public static String key = "a9!n%v*m";
//	public static String key = "yxzsKey";

	// 解密数据
	public static String decrypt(String message) throws Exception {

		byte[] bytesrc = convertHexString(message);
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");
		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));
		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));

		cipher.init(Cipher.DECRYPT_MODE, secretKey, iv);

		byte[] retByte = cipher.doFinal(bytesrc);
		return java.net.URLDecoder.decode(new String(retByte), "utf-8");
	}
    //加密数据
	public static String encrypt(String message) throws Exception {
		message = java.net.URLEncoder.encode(message, "utf-8");
		Cipher cipher = Cipher.getInstance("DES/CBC/PKCS5Padding");

		DESKeySpec desKeySpec = new DESKeySpec(key.getBytes("UTF-8"));

		SecretKeyFactory keyFactory = SecretKeyFactory.getInstance("DES");
		SecretKey secretKey = keyFactory.generateSecret(desKeySpec);
		IvParameterSpec iv = new IvParameterSpec(key.getBytes("UTF-8"));
		cipher.init(Cipher.ENCRYPT_MODE, secretKey, iv);

		return toHexString(cipher.doFinal(message.getBytes("UTF-8"))).toUpperCase();
	}

	public static byte[] convertHexString(String ss) {
		byte digest[] = new byte[ss.length() / 2];
		for (int i = 0; i < digest.length; i++) {
			String byteString = ss.substring(2 * i, 2 * i + 2);
			int byteValue = Integer.parseInt(byteString, 16);
			digest[i] = (byte) byteValue;
		}

		return digest;
	}

	//public static void main(String[] args) throws Exception {
		// String key = "11111111";
		//String value = "我们都是中国人1234zxcvZXCC??/@#$%^&*(!";
		//String jiami = java.net.URLEncoder.encode(value, "utf-8").toLowerCase();
		//System.out.println("加密数据:" + jiami);
		//String a = toHexString(encrypt(jiami)).toUpperCase();
		//System.out.println("加密后的数据为:" + a);
		//String b = java.net.URLDecoder.decode(decrypt(a), "utf-8");
		//System.out.println("解密后的数据:" + b);
//		String value = "1234qwer我爱你？！@#￥%";
//		System.out.println("加密数据:" + value);
		//String str = "loginNo=ai_zhanbinqian&pwd=2n5nl2&op_time=20160727100222&tel=13408682148&groupName=全省&groupId=1";
		//String a = encrypt(str);
		//System.out.println("加密后的数据为:" + a);
		//String b = decrypt("17F197DE3F52A96A42B6D17D5556D09B1FEB1C35F1B6828C63BDDD717817B629EF37788C762AA7F7C92BCC08EC30520B1DC6AD105C0CB8B12DADD282FFAB5E38109CA70B48AFCF6ED1E8C4C48AA3DF0D894D099FE7528339FB2A57722CDE8EECE8DAFCEF1D363512B13556EE1754819692D8014C8288AC194645D642ACA8A06F36035BA8DFC31B67");
		//System.out.println("解密后的数据:" + b);
		//String c = decrypt("5AB34A6350BB7488");
		//System.out.println("解密后的数据:" + c);
		/**
		 * jdbc读取数据库
		 */
		  
//		  Connection conn=null;
//		  Statement st=null;
//		  String sql;
//		  Class.forName("com.ibm.db2.jcc.DB2Driver").newInstance();
//		  conn=DriverManager.getConnection("jdbc:db2://10.109.2.218:50000/sccrm","db2inst1","db2inst1");
//		  st=conn.createStatement();
//		  sql="select login_no,password from power_user_info where password is not null and login_no='songlj1'";
//		  ResultSet rs=st.executeQuery(sql);
//		  while(rs.next()){
//			  System.out.println(rs.getString("login_no")+"密码="+decrypt(rs.getString("password")));
//		  }
		  
	//}

	public static String toHexString(byte b[]) {
		StringBuffer hexString = new StringBuffer();
		for (int i = 0; i < b.length; i++) {
			String plainText = Integer.toHexString(0xff & b[i]);
			if (plainText.length() < 2)
				plainText = "0" + plainText;
			hexString.append(plainText);
		}

		return hexString.toString();
	}
}
