package com.lmsf.org.service;

import com.lmsf.org.entity.Author;
import com.lmsf.org.entity.Book;
import com.lmsf.org.entity.Member;
import com.lmsf.org.repository.AuthorRepository;
import com.lmsf.org.repository.BookRepository;
import com.lmsf.org.repository.MemberRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class MemberService {
    private final MemberRepository memberRepository;

    public Member createMember(Member member){
        Member savedMember = memberRepository.save(member);
        return savedMember;
    }

    public Member getMember(Long id){
        Member member = memberRepository.findById(id).get();
        return member;
    }
    public Member updateMember(Member member){
        Member updatedMember = memberRepository.save(member);
        return updatedMember;
    }
    public void deleteMember(Long id){
        memberRepository.deleteById(id);
    }

}
