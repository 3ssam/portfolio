package com.mandoob.services;

import com.mandoob.models.Guest;
import com.mandoob.repositories.GuestRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class GuestService {

    @Autowired
    private GuestRepository guestRepository;


    @Transactional
    public void createGuest(Guest guest) {
        guestRepository.save(guest);
    }

    public boolean mailIsExist(Guest guest) {
        return guestRepository.existsByEmail(guest.getEmail());

    }

    public Guest getGuest(Long id) {
        Guest guest = guestRepository.findById(id).get();
        if (guest == null)
            throw new IllegalArgumentException("Invalid Guest Id:" + id);
        return guest;
    }

    public List<Guest> getGuests() {
        return guestRepository.findAll();
    }


    @Transactional
    public void deleteGuest(Long id) {
        Guest guest = guestRepository.getOne(id);
        if (guest == null)
            throw new IllegalArgumentException("Invalid Guest Id:" + id);
        guestRepository.delete(guest);
    }

    @Transactional
    public void editGuest(Guest guest) {
        guestRepository.save(guest);
    }


}
