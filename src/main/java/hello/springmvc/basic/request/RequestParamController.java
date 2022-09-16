package hello.springmvc.basic.request;

import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Slf4j
@Controller
public class RequestParamController {

    //반환타입이 void이면서 이렇게 응답에 값을 직접 집어넣으면, view를 조회하지 않는다
    @RequestMapping("/request-param-v1")
    public void requestParamV1(HttpServletRequest request, HttpServletResponse response) throws IOException {

        String username = request.getParameter("username"); //HttpServletRequest가 제공하는 getParameter
        int age = Integer.parseInt(request.getParameter("age"));

        log.info("username={}, age={}", username, age);

        response.getWriter().write("ok");
    }

    /**
     * @RequestParam 사용 : 파라미터 이름으로 바인딩
     * @ResponseBody 추가 : view조회를 무시하고, Http message body에 직접 해당 내용 입력 : viewResolver발동 x
     */
    @ResponseBody
    @RequestMapping("/request-param-v2")
    public String requestParamV2(
            @RequestParam("username") String memberName, //변수명-memberName은 아무것이나 작성가능
            @RequestParam("age") int memberAge) {
        //request.getParameter("username")

        log.info("username={}, age={}", memberName, memberAge);

        return "ok"; //뷰 조회를 진행하지 않고 문자가 HTTP에 바로 박힌다
    }

    /**
     * Http 파라미터 이름이 변수이름과 같으면 @RequestParam(name="xxx") 생략 가능
     */
    @ResponseBody
    @RequestMapping("/request-param-v3")
    public String requestParamV3(
            @RequestParam String username,
            @RequestParam int age) {

        log.info("username={}, age={}", username, age);

        return "ok"; //뷰 조회를 진행하지 않고 문자가 HTTP에 바로 박힌다
    }

    /**
     * String, int 등의 단순 타입이면 @RequestParam도 생략가능
     */
    @ResponseBody
    @RequestMapping("/request-param-v4")
    public String requestParamV4(String username, int age) {

        log.info("username={}, age={}", username, age);

        return "ok"; //뷰 조회를 진행하지 않고 문자가 HTTP에 바로 박힌다
    }

    /**
     * @RequestParam String username, @RequestParam int age -> @ModelAttribute HelloData helloData
     * -> HelloData객체가 생성되고, 요청 파라미터의 값도 모두 들어가 있다
     */
    @ResponseBody
    @RequestMapping("/model-attribute-v1")
    public String modelAttributeV1(@ModelAttribute HelloData helloData) {

        //HelloData helloData = new HelloData();
        //helloData.setUsername(username);
        //helloData.setAge(age);

        log.info("username={}, age={}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }
}
