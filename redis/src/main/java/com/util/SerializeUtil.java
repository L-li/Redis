package com.util;


import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class SerializeUtil {

    // 序列化 object 对象
    public static byte[] serialize(Object obj) {
        byte[] bytes = null;
        ByteArrayOutputStream bos = new ByteArrayOutputStream();
        ObjectOutputStream oos = null;
        try {
            oos = new ObjectOutputStream(bos);
            oos.writeObject(obj);
            oos.flush();
            bytes = bos.toByteArray();
        } catch (IOException ex) {
            ex.printStackTrace();
        }finally {
            close(oos);
            close(bos);
        }
        return bytes;
    }

    // 反序列化 object 对象
    public static Object unserialize(byte[] bytes) {
        if (null == bytes) {
            return null;
        }
        Object obj = null;
        ByteArrayInputStream bis = null;
        ObjectInputStream ois = null;
        try {
            bis = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bis);
            obj = ois.readObject();
        } catch (IOException ex) {
            ex.printStackTrace();
        } catch (ClassNotFoundException ex) {
            ex.printStackTrace();
        }finally {
            close(ois);
            close(bis);
        }
        return obj;
    }

    // 序列化 list 集合
    public static byte[] serializeList(List<?> list) {

        if (list == null || list.size() == 0) {
            return null;
        }
        ObjectOutputStream oos = null;
        ByteArrayOutputStream bos = null;
        byte[] bytes = null;
        try {
            bos = new ByteArrayOutputStream();
            oos = new ObjectOutputStream(bos);
            for (Object obj : list) {
                oos.writeObject(obj);
            }
            bytes = bos.toByteArray();
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(oos);
            close(bos);
        }
        return bytes;
    }

    // 反序列化 list 集合
    public static List<?> unserializeList(byte[] bytes) {
        if (bytes == null) {
            return null;
        }
        List<Object> list = new ArrayList<Object>();
        ByteArrayInputStream bais = null;
        ObjectInputStream ois = null;
        try {
            // 反序列化
            bais = new ByteArrayInputStream(bytes);
            ois = new ObjectInputStream(bais);
            while (bais.available() > 0) {
                Object obj = (Object) ois.readObject();
                if (obj == null) {
                    break;
                }
                list.add(obj);
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            close(bais);
            close(ois);
        }
        return list;
    }

    // 关闭io流对象
    public static void close(Closeable io) {
        if (io != null) {
            try {
                io.close();
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}