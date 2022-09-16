package hello.springmvc.basic.request;

import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpMethod;
import org.springframework.util.MultiValueMap;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Locale;

@Slf4j
@RestController //응답값을 뷰를 찾는것이 아닌 문자 그대로를 HTTP에 박아준다
public class RequestHeaderController {

    @RequestMapping("/headers")
    public String headers(HttpServletRequest request,
                          HttpServletResponse response,
                          HttpMethod httpMethod, //Http메서드를 조회한다
                          Locale locale, //Locale 정보를 조회한다
                          @RequestHeader MultiValueMap<String, String> headerMap, //모든 Http헤더를 MultyValueMap형식으로 조회한다
                          @RequestHeader("host") String host, //특정 Http 헤더를 조회한다, 필수값 여부 : required
                          @CookieValue(value = "myCookie", required = false) String cookie) { //특정 쿠키를 조회한다

        log.info("request={}", request);
        log.info("response={}", response);
        log.info("httpMethod={}", httpMethod);
        log.info("locale={}", locale);
        log.info("headerMap={}", headerMap);
        log.info("header host={}", host);
        log.info("myCookie={}", cookie);

        return "ok";

    }
}
