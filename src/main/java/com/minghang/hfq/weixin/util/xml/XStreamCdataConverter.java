package com.minghang.hfq.weixin.util.xml;

import com.thoughtworks.xstream.converters.basic.StringConverter;

/**
 * Cdata转换
 *
 * @Author: lihong
 * @Date: 2018/7/27
 * @Description
 */
public class XStreamCdataConverter extends StringConverter {

    @Override
    public String toString(Object obj) {
        return Cdata.PREFIX_CDATA + super.toString(obj) + Cdata.SUFFIX_CDATA;
    }

}
