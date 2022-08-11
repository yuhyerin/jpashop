package jpabook.jpashop;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {

    @GetMapping("hello")
    public String hello(Model model){
        /** 모델에 데이터를 넣어서, 뷰로 넘길 수 있다.
         * key: data
         * value: "hello :)"
         * */
        model.addAttribute("data","hello :)");

        // return 뷰이름 (끝에.html이 자동으로 붙음)
        return "hello";
    }
}
