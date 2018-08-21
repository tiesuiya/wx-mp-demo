package com.minghang.hfq.weixin.util;

import com.thoughtworks.xstream.converters.basic.StringConverter;

/**
 * Cdata转换
 *
 * @Author: lihong
 * @Date: 2018/7/27
 * @Description
 */
public class CdataConverter extends StringConverter {
    /**
     * CDATA头
     */
    public static final String PREFIX_CDATA = "<![CDATA[";

    /**
     * CDATA尾
     */
    public static final String SUFFIX_CDATA = "]]>";

    /**
     * Object to XML时为内容添加CDATA标记
     * XStreamConverter(value = CdataConverter.class)
     */
    @Override
    public String toString(Object obj) {
        return CdataConverter.PREFIX_CDATA + super.toString(obj) + CdataConverter.SUFFIX_CDATA;
    }

}
