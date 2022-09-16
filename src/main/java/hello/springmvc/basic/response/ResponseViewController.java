package hello.springmvc.basic.response;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

@Controller
public class ResponseViewController {

    @RequestMapping("/response-view-v1")
    public ModelAndView responseViewV1() {

        ModelAndView mav = new ModelAndView("response/hello")
                .addObject("data", "hello!!");
        return mav;
    }

    /**
     * String을 반환하는 경우 @ResponseBody, @RestController -> ViewResolver를 실행하지 않고
     * "response/hello"가 문자그대로 HTTP 메세지 바디에 출력된다
     * 없으면 "response/hello"로 ViewResolver가 실행되어서 뷰를 찾고, 렌더링한다
     */
    @RequestMapping("/response-view-v2")
    public String responseViewV2(Model model) { //String반환

        //ModelAndView mav = new ModelAndView("response/hello")
        // .addObject("data", "hello!!");
        model.addAttribute("data", "hello!!");

        return "response/hello"; //@Controller이면서 String을 반환하면 return이 view의 논리이름이 된다.
    }

    //@ResponseBody, HttpEntity를 사용하면 '뷰 템플릿'을 사용하는 것이 아니라, Http 메세지 바디에 직접 응답 데이터를 출력할 수 있다
}
