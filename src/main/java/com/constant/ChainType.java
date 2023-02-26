package com.constant;

import com.exp.collections.CommonsCollections1;
import com.exp.collections.CommonsCollections2;
import com.exp.collections.CommonsCollections3;
import com.exp.collections.CommonsCollections4;
import com.exp.collections.CommonsCollections5;
import com.exp.collections.CommonsCollections6;
import com.exp.collections.CommonsCollections7;

public enum ChainType {
    CommonsCollections1(1, new CommonsCollections1()),
    CommonsCollections2(2, new CommonsCollections2()),
    CommonsCollections3(3,new CommonsCollections3()),
    CommonsCollections4(4,new CommonsCollections4()),
    CommonsCollections5(5,new CommonsCollections5()),
    CommonsCollections6(6,new CommonsCollections6()),
    CommonsCollections7(7,new CommonsCollections7());
    private int id;
    private PayloadGenerator payloadGenerator;


    ChainType(int id, PayloadGenerator payloadGenerator) {
        this.id = id;
        this.payloadGenerator = payloadGenerator;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public PayloadGenerator getPayloadGenerator() {
        return payloadGenerator;
    }

    public void setPayloadGenerator(PayloadGenerator payloadGenerator) {
        this.payloadGenerator = payloadGenerator;
    }
}
