package com.lmsf.org.controllers;

import com.lmsf.org.entity.Member;
import com.lmsf.org.service.MemberService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequiredArgsConstructor
@RequestMapping("/members")
public class MemberController {
    private final MemberService memberService;
    @PostMapping("/")
    public ResponseEntity<Member> createMember(@RequestBody Member member) {
        Member savedMember = memberService.createMember(member);
        return ResponseEntity.status(HttpStatus.CREATED).body(savedMember);
    }


    @DeleteMapping("/{id}")
    public void deleteMember(@PathVariable Long id) {
        memberService.deleteMember(id);
    }
    @PutMapping
    public Member updateMember(@RequestBody Member member) {
        Member updatedMember = memberService.updateMember(member);
        return updatedMember;
    }

    @GetMapping("/{id}")
    public Member getMember(@PathVariable Long id) {
        Member member = memberService.getMember(id);
        return member;
    }


}
