package hello.hellospring.controller;

import hello.hellospring.domain.Member;
import hello.hellospring.service.MemberService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;

import java.util.List;

@Controller //컨트롤러객체를 생성해서 스프링 컨테이너에 넣어둔다.
public class MemberController {

    //필드주입
    /*@Autowired private MemberService memberService;*/

    //세터주입(단점, 호출해서는안되는 메소드가 추가가가능하므로)
    /*private MemberService memberService;

    @Autowired
    public void setMemberService(MemberService memberService) {
        this.memberService = memberService;
    }*/

    //생성자 주입(생성만할 수 있는 메소드로 추가가 되지 않는다. 가장좋은방법)
    private final MemberService memberService;

    @Autowired //스프링이 연결시켜주는 역할(DI: Dependency Injection)
    public MemberController(MemberService memberService){

        this.memberService = memberService;
        System.out.println("memberService = " + memberService.getClass());
    }

    @GetMapping("/members/new")
    public String createForm(){
        return "members/createMemberForm";
    }

    @PostMapping("/members/new")
    public String create(MemberForm form){
        Member member = new Member();
        member.setName(form.getName());

        memberService.join(member);

        return "redirect:/";
    }

    @GetMapping("/members")
    public String list(Model model){
        List<Member> members = memberService.findMembers();
        model.addAttribute("members", members);
        return "members/memberList";
    }
}
