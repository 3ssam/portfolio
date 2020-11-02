package com.mandoob.services;

import com.mandoob.models.TeamMember;
import com.mandoob.repositories.TeamMemberRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.List;

@Service
public class TeamMemberService {

    @Autowired
    private TeamMemberRepository memberRepository;


    @Transactional
    public void createMember(TeamMember member) {
        memberRepository.save(member);
    }


    public TeamMember getMember(Long id) {
        TeamMember member = memberRepository.findById(id).get();
        if (member == null)
            throw new IllegalArgumentException("Invalid Member Id:" + id);
        return member;
    }

    public List<TeamMember> getMembers() {
        return memberRepository.findAll();
    }


    @Transactional
    public void deleteMember(Long id) {
        TeamMember member = memberRepository.getOne(id);
        if (member == null)
            throw new IllegalArgumentException("Invalid Member Id:" + id);
        memberRepository.delete(member);
    }

    @Transactional
    public void editMember(TeamMember member) {
        memberRepository.save(member);
    }


}
