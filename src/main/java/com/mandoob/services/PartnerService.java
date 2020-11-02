package com.mandoob.services;

import com.mandoob.models.Partner;
import com.mandoob.repositories.PartnerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class PartnerService {

    @Autowired
    private PartnerRepository partnerRepository;


    @Transactional
    public void createPartner(Partner partner) {
        partnerRepository.save(partner);
    }


    public Partner getPartner(Long id) {
        Partner partner = partnerRepository.findById(id).get();
        if (partner == null)
            throw new IllegalArgumentException("Invalid partner Id:" + id);
        return partner;
    }

    public List<Partner> getPartners() {
        return partnerRepository.findAll();
    }


    @Transactional
    public void deletePartner(Long id) {
        Partner partner = partnerRepository.getOne(id);
        if (partner == null)
            throw new IllegalArgumentException("Invalid partner Id:" + id);
        partnerRepository.delete(partner);
    }

    @Transactional
    public void editPartner(Partner partner) {
        partnerRepository.save(partner);
    }


}
