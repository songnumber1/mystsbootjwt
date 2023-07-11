package com.stsboot.com.util;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;


public class Util {
	public static boolean IsNullEmpty(String checkData) {
		if (checkData == null || checkData.isEmpty())
			return true;
		else
			return false;
	}

	public static String ObjectToJson(Object object) {
        if (object == null) {
            return null;
        }

		try {
			ObjectMapper mapper = new ObjectMapper();
        	return mapper.writeValueAsString(object);	
		} catch (JsonProcessingException e) {
			return null;
		}
		catch (Exception e) {
			return null;
		}
    }
}
