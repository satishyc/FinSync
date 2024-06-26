package com.example.finSync.service;

import com.example.finSync.entity.User;
import com.example.finSync.entity.UserPortfolio;
import com.example.finSync.entity.mongoUserProtfolio.UserWealthDetails;
import com.example.finSync.entity.mongoUserProtfolio.UserWealthDetailsRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserWealthDetailsService {
    @Autowired
    UserWealthDetailsRepository userWealthDetailsRepository;

    public void saveUserWealth(User user, UserPortfolio userPortfolio){
        UserWealthDetails userWealthDetails = new UserWealthDetails();
        userWealthDetails.setUserName(user.getUserName());
        userWealthDetails.setAccounts(userPortfolio.getAccounts());
        userWealthDetails.setDeposits(userPortfolio.getDeposits());
        userWealthDetails.setLoans(userPortfolio.getLoans());
        userWealthDetails.setMutualFunds(userPortfolio.getMutualFunds());
        userWealthDetails.setStocks(userPortfolio.getStocks());
        userWealthDetailsRepository.save(userWealthDetails);
    }

}
