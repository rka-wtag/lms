package com.lmsf.org.service;

import com.lmsf.org.entity.Member;
import com.lmsf.org.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public Member createMember(Member member){
        return memberRepository.save(member);
    }
    public Member getMember(Long id){
        return memberRepository.findById(id).get();
    }
    public Member updateMember(Member member){
        return memberRepository.save(member);
    }
    public void deleteMember(Long id){
        memberRepository.deleteById(id);
    }

}
