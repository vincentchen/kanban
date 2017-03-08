package com.idealism.kanban.util;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

public class SerializeTool {

	public final static String CHARSET_ISO88591 = "iso-8859-1";

	/**
	 * @Title: serialize
	 * @Description: java对象序列化 <br>
	 *               eg:<br>
	 *               Map<String,String> map = new HashMap<String,String>();<br>
	 *               map.put("test","序列化");<br>
	 *               String serializedMapStr=SerializeUtil.serialize(map);
	 * @param original
	 *            要进行序列化的java对象
	 * @return String 序列化的后的值
	 * @throws IOException
	 * 
	 * 
	 */
	public static String serialize(Object original) {
		if (null == original)
			return null;
		ByteArrayOutputStream baos = new ByteArrayOutputStream();
		ObjectOutputStream oos;
		String ObjBody = null;
		try {
			oos = new ObjectOutputStream(baos);
			oos.writeObject(original);
			oos.close();
			baos.close();
			byte[] str = baos.toByteArray();
			ObjBody = URLEncoder.encode(new Encryption().encryptString(new String(str)), CHARSET_ISO88591);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return ObjBody;
	}

	/**
	 * @Title: deserialize
	 * @Description: 序列化的String对象的反序列化<br>
	 *               需要自己进行强制转换得到最终类型<br>
	 *               eg:<br>
	 *               Map newmap =
	 *               (Map)SerializeUtil.deserialize(serializedMapStr);
	 * @param serializedstr
	 *            经序列化处理过的信息
	 * @return Object 反序列化后生成的Object。<br>
	 * @throws IOException
	 * @throws UnsupportedEncodingException
	 * @throws ClassNotFoundException
	 * 
	 * 
	 */
	public static Object deserialize(String serializedstr) {
		if (null == serializedstr)
			return null;
		String str;
		ObjectInputStream ois;
		Object obj = null;
		try {
			str = new Encryption().decryptString(URLDecoder.decode(serializedstr, CHARSET_ISO88591));
			ois = new ObjectInputStream(new ByteArrayInputStream(str.getBytes()));
			obj = ois.readObject();
			ois.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return obj;
	}
}