package hello.springmvc.basic.request;

import com.fasterxml.jackson.databind.ObjectMapper;
import hello.springmvc.basic.HelloData;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.ServletInputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.nio.charset.StandardCharsets;

/**
 * {"username" : "hello", "age" : 20 }
 * content-type : application/json
 */
@Slf4j
@Controller
public class RequestBodyJsonController {

    private ObjectMapper objectMapper = new ObjectMapper();

    /**
     * HttpServletRequest를 사용해서 직접 Http메세지 바디에서 문자를 읽어와서, 문자로 변환한다
     * 문자로 된 JSON 데이터를 Jackson 라이브러리인 objectMapper를 사용해서 '자바 객체'로 변환한다
     */

    @PostMapping("/request-body-json-v1")
    public void requestBodyJsonV1(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());

        response.getWriter().write("ok");
    }

    /**
     * 이전에 학습했던 @RequestBody를 사용해서 HTTP 메세지에서 데이터를 꺼내고 messageBody에 저장한다
     * HttpMessageConverter 사용 -> StringHttpMessageConverter 적용
     * : 메세지 바디 정보 직접 반환
     */

    @ResponseBody
    @PostMapping("/request-body-json-v2")
    public String requestBodyJsonV2(@RequestBody String messageBody) throws IOException { //String으로 교체

        //ServletInputStream inputStream = request.getInputStream();
        //String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);

        log.info("messageBody={}", messageBody);
        HelloData helloData = objectMapper.readValue(messageBody, HelloData.class); //Http Message Converter가 대신 실행해준다
        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());

        //response.getWriter().write("ok");
        return "ok";
    }

    /**
     * HttpEntity, @RequestBody를 사용하면 HttpMessageConverter가 HTTP 메세지 바디의 내용을 우리가 원하는 문자나 객체등으로 변환해준다
     * HelloData helloData = objectMapper.readValue(messageBody, HelloData.class); 작업 대신 해줌
     *
     * @RequestBody는 생략이 불가능하다 -> @ModelAttribute가 적용되어 버린다 -> Http메세지 바디가 아닌 '요청 파라미터'를 처리하게 된다
     */
    @ResponseBody
    @PostMapping("/request-body-json-v3")
    public String requestBodyJsonV3(@RequestBody HelloData data) {

        //HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username = {}, age = {}", data.getUsername(), data.getAge());

        return "ok";
    }

    @ResponseBody
    @PostMapping("/request-body-json-v4")
    public String requestBodyJsonV4(HttpEntity<HelloData> httpEntity) {

        HelloData helloData = httpEntity.getBody(); // 제네릭 타입의 <HelloData>가 반환이 된다.

        log.info("username = {}, age = {}", helloData.getUsername(), helloData.getAge());

        return "ok";
    }

    /**
     * 응답의 경우에도 @ResponseBody를 사용하면 해당 객체를 Http메세지 바디에 직접 넣어줄 수 있다. 이 경우에도 HttpEntity를 사용해도 된다
     * @RequestBody 요청
     * Json 요청 -> Http 메세지 컨버터 -> 객체
     * @ResponseBody 응답
     * 객체 -> Http 메세지 컨버터 -> Json 응답
     *
     * HttpMessageConverter 사용 -> MappingJackson2HttpMessageConverter 적용 (Accept: application/json)
     */
    @ResponseBody
    @PostMapping("/request-body-json-v5")
    public HelloData requestBodyJsonV5(@RequestBody HelloData data) { //응답에 HelloData 사용

        //HelloData helloData = objectMapper.readValue(messageBody, HelloData.class);
        log.info("username = {}, age = {}", data.getUsername(), data.getAge());

        return data;
    }
}
