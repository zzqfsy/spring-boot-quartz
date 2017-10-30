package com.zzqfsy.config;

import com.alibaba.fastjson.JSON;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpOutputMessage;
import org.springframework.http.converter.HttpMessageNotWritableException;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Created by chenghao on 8/16/16.
 */
public class CustomJacksonMessageConverter extends MappingJackson2HttpMessageConverter {
    private static Logger logger= LoggerFactory.getLogger(CustomJacksonMessageConverter.class);

    @Override
    protected void writeInternal(Object object, Type type, HttpOutputMessage outputMessage) throws IOException, HttpMessageNotWritableException {
        super.writeInternal(object, type, outputMessage);
        logger.info(JSON.toJSONString(object));
    }

}
