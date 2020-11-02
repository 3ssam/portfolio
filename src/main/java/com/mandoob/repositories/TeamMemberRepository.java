package com.mandoob.repositories;

import com.mandoob.models.TeamMember;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TeamMemberRepository extends JpaRepository<TeamMember, Long> {
    TeamMember findByImageName(String name);
}
