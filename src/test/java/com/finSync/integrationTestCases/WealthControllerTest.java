package com.finSync.integrationTestCases;

import com.finSync.entity.mongoWealth.*;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import io.jsonwebtoken.lang.Assert;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource(locations = "classpath:application-test.properties")
public class WealthControllerTest {

    @Autowired
    MockMvc mockMvc;
    @MockBean
    BankDetailsRepository bankDetailsRepository;
    @MockBean
    MutualFundPriceRepository mutualFundPriceRepository;
    @MockBean
    StockPriceRepository stockPriceRepository;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    public void setUp() {

        List<BankDetails> bankList = new ArrayList<>();
        bankList.add(new BankDetails("SBI","StateBank Of India"));
        bankList.add(new BankDetails("HDFC","Housing Development Finance Corporation"));
        Mockito.when(bankDetailsRepository.findAll()).thenReturn(bankList);
        List<MutualFundPrice> mfList = new ArrayList<>();
        mfList.add(new MutualFundPrice("JM Flexicap Fund Flexi Cap",113.2232,"Jun 18, 2024",new Date()));
        mfList.add(new MutualFundPrice("Axis Overnight Fund Overnight",1284.6086,"Jun 18, 2024",new Date()));
        Mockito.when(mutualFundPriceRepository.findAll()).thenReturn(mfList);
        List<StockPrice> stList = new ArrayList<>();
        stList.add( new StockPrice("Dynamatic Tech.",113.12,new Date()));
        stList.add(new StockPrice("SBI",900.123,new Date()));
        Mockito.when(stockPriceRepository.findAll()).thenReturn(stList);

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
