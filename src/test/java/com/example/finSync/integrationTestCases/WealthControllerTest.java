package com.example.finSync.integrationTestCases;

import com.example.finSync.entity.mongoWealth.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.lang.Assert;
import jakarta.annotation.PostConstruct;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.Date;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class WealthControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    BankDetailsRepository bankDetailsRepository;
    @Autowired
    MutualFundPriceRepository mutualFundPriceRepository;
    @Autowired
    StockPriceRepository stockPriceRepository;

    @Autowired
    ObjectMapper objectMapper;
    @PostConstruct
    public void init() {
        bankDetailsRepository.deleteAll();
        BankDetails details = new BankDetails("SBI","StateBank Of India");
        BankDetails details1 = new BankDetails("HDFC","Housing Development Finance Corporation");
        bankDetailsRepository.save(details1);
        bankDetailsRepository.save(details);
        mutualFundPriceRepository.deleteAll();
        MutualFundPrice mf1 = new MutualFundPrice("JM Flexicap Fund Flexi Cap",113.2232,"Jun 18, 2024",new Date());
        MutualFundPrice mf2 = new MutualFundPrice("Axis Overnight Fund Overnight",1284.6086,"Jun 18, 2024",new Date());
        mutualFundPriceRepository.save(mf1);
        mutualFundPriceRepository.save(mf2);
        stockPriceRepository.deleteAll();
        StockPrice sp1 = new StockPrice("Dynamatic Tech.",113.12,new Date());
        StockPrice sp2 = new StockPrice("SBI",900.123,new Date());
        stockPriceRepository.save(sp1);
        stockPriceRepository.save(sp2);
    }
    @Test
    public void testBankDetailsApi() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/bankNames").
                contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(contentAsString);
        Assert.isTrue(jsonNode.size()==2);
    }
    @Test
    public void testMutualFundDetailsApi() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/mutualFundNames").
                contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(contentAsString);
        Assert.isTrue(jsonNode.size()==2);
    }
    @Test
    public void testStockPriceDetailsApi() throws Exception {
        MvcResult mvcResult = mockMvc.perform(get("/stockNames").
                contentType(MediaType.APPLICATION_JSON)
        ).andExpect(status().isOk()).andReturn();
        String contentAsString = mvcResult.getResponse().getContentAsString();
        JsonNode jsonNode = objectMapper.readTree(contentAsString);
        Assert.isTrue(jsonNode.size()==2);
    }

}
