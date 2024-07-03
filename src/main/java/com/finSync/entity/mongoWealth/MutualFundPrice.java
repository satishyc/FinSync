package com.finSync.entity.mongoWealth;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "mutualFundPrices")
@TypeAlias("MutualFund")
public class MutualFundPrice {
    @Id
    private String id;

    private final String name;
    private final Double nav;

    public MutualFundPrice(String name, Double nav,String navDate,Date created) {
        this.name = name;
        this.nav = nav;
    }
    public Double getNav(){
        return nav;
    }
    public String getName() {
        return name;
    }
}
