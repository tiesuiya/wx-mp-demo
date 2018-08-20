//package com.minghang.hfq.weixin.util.xml;
//
//import com.thoughtworks.xstream.XStream;
//import com.thoughtworks.xstream.core.util.QuickWriter;
//import com.thoughtworks.xstream.io.HierarchicalStreamWriter;
//import com.thoughtworks.xstream.io.xml.PrettyPrintWriter;
//import com.thoughtworks.xstream.io.xml.XppDriver;
//
//import java.io.Writer;
//
///**
// * XStream帮助类
// *
// * @Author: lihong
// * @Date: 2018/7/27
// * @Description
// */
//public final class XStreamUtil {
//
//    private XStreamUtil() {
//    }
//
//    /**
//     * 初始化XStream
//     *
//     * @return 支持CDATA的XStream对象
//     */
//    public static XStream initXSteam(Class<?> clazz) {
//        XStream xStream = new XStream(new XppDriver() {
//            @Override
//            public HierarchicalStreamWriter createWriter(Writer out) {
//                return new PrettyPrintWriter(out, this.getNameCoder()) {
//                    @Override
//                    protected void writeText(QuickWriter writer, String text) {
//                        if (text.startsWith(Cdata.PREFIX_CDATA) && text.endsWith(Cdata.SUFFIX_CDATA)) {
//                            writer.write(text);
//                        } else {
//                            super.writeText(writer, text);
//                        }
//                    }
//                };
//            }
//        });
//        // 配合注解实现自定义节点名
//        xStream.processAnnotations(clazz);
//        return xStream;
//    }
//}
