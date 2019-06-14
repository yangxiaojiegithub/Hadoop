package com.stanlong.convert;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.core.convert.converter.Converter;

public class CustomerDataConvert implements Converter<String, Date>{

	/**
	 * 参数绑定， 日期串转成日期类型
	 */
	@Override
	public Date convert(String source) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		try{
			//如果参数绑定成功，直接返回
			if(null == source || "".equals(source)){
				return null;
			}else{
				return sdf.parse(source);
			}
		}catch(ParseException e){
			e.printStackTrace();
		}
		//如果参数绑定失败，返回空
		return null;
	}

}
