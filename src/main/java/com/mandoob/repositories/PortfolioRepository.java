package com.mandoob.repositories;

import com.mandoob.models.Portfolio;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PortfolioRepository extends JpaRepository<Portfolio, Long> {
    Portfolio findByImageName(String name);
}
