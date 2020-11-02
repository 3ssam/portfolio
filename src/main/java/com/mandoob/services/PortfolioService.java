package com.mandoob.services;

import com.mandoob.models.Portfolio;
import com.mandoob.repositories.PortfolioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PortfolioService {

    @Autowired
    private PortfolioRepository portfolioRepository;


    @Transactional
    public void createPortfolio(Portfolio portfolio) {
        portfolioRepository.save(portfolio);
    }


    public Portfolio getPortfolio(Long id) {
        Portfolio portfolio = portfolioRepository.findById(id).get();
        if (portfolio == null)
            throw new IllegalArgumentException("Invalid portfolio Id:" + id);
        return portfolio;
    }

    public List<Portfolio> getPortfolios() {
        return portfolioRepository.findAll();
    }


    @Transactional
    public void deletePortfolio(Long id) {
        Portfolio portfolio = portfolioRepository.getOne(id);
        if (portfolio == null)
            throw new IllegalArgumentException("Invalid portfolio Id:" + id);
        portfolioRepository.delete(portfolio);
    }

    @Transactional
    public void editPortfolio(Portfolio portfolio) {
        portfolioRepository.save(portfolio);
    }


}
