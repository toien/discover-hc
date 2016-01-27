package com.toien.discover.hc.util;

import java.io.IOException;
import java.io.InputStream;

import com.fasterxml.jackson.annotation.JsonInclude.Include;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

/**
 * JSON util class
 * 
 * @author goku
 *
 */
public class JsonUtil {

	private static final ObjectMapper MAPPER;
	private static final ObjectMapper IGNORE_NULL_MAPPER;

	static {
		MAPPER = new ObjectMapper();
		IGNORE_NULL_MAPPER = new ObjectMapper();
		IGNORE_NULL_MAPPER.setSerializationInclusion(Include.NON_NULL);
	}

	/**
	 * 将 java 对象转换为 JSON 字符串，如果传入的是 String 类型，则返回自身
	 * 
	 * @param object
	 *            待转换的 java 对象
	 * @param ignoreNull
	 *            boolean 类型，是否忽略值为 null 的属性
	 * @return
	 */
	public static String toJson(Object object, boolean ignoreNull) {
		if (object == null) {
			return null;
		}

		if (object instanceof String) {
			return (String) object;
		}

		String result = null;
		try {
			if (ignoreNull) {
				return IGNORE_NULL_MAPPER.writeValueAsString(object);
			} else {
				return MAPPER.writeValueAsString(object);
			}
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		return result;
	}

	/**
	 * 将 JSON 字符串转换为 java 对象
	 * 
	 * @param json
	 *            待转换的 JSON 字符
	 * @param clazz
	 *            目标对象的类型
	 * @return
	 */
	public static <T> T fromJson(String json, Class<T> clazz) {
		T result = null;
		try {
			result = MAPPER.readValue(json, clazz);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

	/**
	 * 将 InpueStream 中的 JSON 内容转换为 java 对象
	 * 
	 * @param input
	 *            输入流
	 * @param clazz
	 *            目标对象的类型
	 * @return
	 */
	public static <T> T fromJson(InputStream input, Class<T> clazz) {
		T result = null;
		try {
			result = MAPPER.readValue(input, clazz);
		} catch (IOException e) {
			e.printStackTrace();
		}

		return result;
	}

}
