package com.mandoob.repositories;

import com.mandoob.models.Partner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface PartnerRepository extends JpaRepository<Partner, Long> {
    Partner findByImageName(String name);
}
