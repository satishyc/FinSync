package com.finSync.entity.mongoWealth;

import org.springframework.data.annotation.Id;
import org.springframework.data.annotation.TypeAlias;
import org.springframework.data.mongodb.core.mapping.Document;

import java.util.Date;

@Document(collection = "stockPrices")
@TypeAlias("Stock")
public class StockPrice {
    @Id
    private String id;

    private final String name;
    private final Double price;

    public StockPrice(String name, Double price,Date created) {
        this.name = name;
        this.price = price;
    }

    public Double getPrice(){
        return price;
    }
    public String getName() {
        return name;
    }
}
