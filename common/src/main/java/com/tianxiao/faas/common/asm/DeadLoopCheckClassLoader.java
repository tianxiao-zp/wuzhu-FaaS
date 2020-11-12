package com.tianxiao.faas.common.asm;

import jdk.internal.org.objectweb.asm.ClassReader;
import jdk.internal.org.objectweb.asm.ClassVisitor;
import jdk.internal.org.objectweb.asm.ClassWriter;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

/**
 * @author jianmiao.xu
 * @date 2020/10/17
 */
public class DeadLoopCheckClassLoader extends ClassLoader{

    private final static String PATH = "application/target/classes/";
    @Override
    protected Class<?> findClass(String name) throws ClassNotFoundException {
        try {
            // 如果使用原来的类名则会被当前的类加载器加载，直接跳过我们自定义的类加载器
            String path = name.substring(0, name.length() - 5).replace(".", "/");

            // 加载.class字节码，在指定目录下搜索
            File file = new File(PATH + path + ".class");

            BufferedInputStream bi = new BufferedInputStream(
                    new FileInputStream(file));
            byte[] bytecodes = new byte[(int)file.length()];
            bi.read(bytecodes);
            bi.close();
            bytecodes = hackByteCode(bytecodes);

            // defineClass是生成class对象的关键方法
            // 这里特殊处理className，后面说为什么
            String clazzName = name.substring(0, name.length() - 5);
            return defineClass(clazzName, bytecodes, 0, bytecodes.length);
        } catch (Throwable t) {
            t.printStackTrace();
        }
        throw new ClassNotFoundException();
    }

    // 采用字节码增加的方法，将待测试类的字节码文件织入循环检测计数器，实现死循环检测的功能
    private static byte[] hackByteCode(byte[] originCode) throws IOException {
        // 读取原class文件二进制文件
        ClassReader classReader = new ClassReader(originCode);
        ClassWriter classWriter = new ClassWriter(ClassWriter.COMPUTE_MAXS);

        //织入循环检测计数器
        ClassVisitor classVisitor = new LoopCheckClassVisitor(classWriter);
        classReader.accept(classVisitor, ClassReader.SKIP_DEBUG);

        // 重新变异
        byte[] data = classWriter.toByteArray();
        return data;
    }
}