package net.xdclass.order_service.utils;


import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.IOException;

/**
 *
 */
public class JasonUtils {


    private static final ObjectMapper objectMapper = new ObjectMapper();
    static{
        objectMapper.configure(JsonGenerator.Feature.QUOTE_FIELD_NAMES,false);
    }

    /**
     *
     * @param str
     * @return
     */
    public static JsonNode str2JsonNode(String str){
        try {
            return objectMapper.readTree(str);
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }



}
