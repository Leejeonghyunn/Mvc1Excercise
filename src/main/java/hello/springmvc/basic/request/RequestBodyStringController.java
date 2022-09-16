package hello.springmvc.basic.request;

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
import java.io.InputStream;
import java.io.Writer;
import java.nio.charset.StandardCharsets;

@Slf4j
@Controller
public class RequestBodyStringController {

    @PostMapping("/request-body-string-v1")
    public void requestBodyString(HttpServletRequest request, HttpServletResponse response) throws IOException {

        ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        //Stream으로 값을 입력할때는 어떤 바이트코드로 입력받을지 정해주어야 한다

        log.info("messageBody={}", messageBody);

        response.getWriter().write("ok");
    }

    /**
     * InputStream(Reader) : HTTP 요청 메세지 바디의 내용을 직접 조회
     * OutputStream(Writer) : HTTP 응답 메시지의 바디에 직접 결과 출력
     */
    @PostMapping("/request-body-string-v2")
    public void requestBodyStringV2(InputStream inputStream, Writer responseWriter) throws IOException {

        //ServletInputStream inputStream = request.getInputStream();
        String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        //Stream으로 값을 입력할때는 어떤 바이트코드로 입력받을지 정해주어야 한다

        log.info("messageBody={}", messageBody);

        responseWriter.write("ok");
    }

    /**
     * HttpEntity : HTTP Header, body 정보를 편리하게 조회 -> 메세지 바디 정보를 직접 조회
     * -> HttpMessageConvertor 사용 -> StringHttpMessageConverter 적용
     *
     * 응답에서도 HttpEntity 사용 가능 -> 메세지 바디 정보 직접 반환(view 조회x)
     * -> HttpMessageConvertor 사용 -> StringHttpMessageConverter 적용
     */

    @PostMapping("/request-body-string-v3")
    public HttpEntity<String> requestBodyStringV3(HttpEntity<String> httpEntity) throws IOException {

        //String messageBody = StreamUtils.copyToString(inputStream, StandardCharsets.UTF_8);
        String messageBody = httpEntity.getBody();

        log.info("messageBody={}", messageBody);

        //responseWriter.write("ok");
        return new HttpEntity<>("ok");
        //return new ResponseEntity<String>("ok", HttpStatus.CREATED); http 응답 상태코드를 넣을 수 있다
    }

    /**
     * 실무에서 가장 많이 쓰는 형태
     * @RequestBody, @ResponseBody -> 메세지 바디 정보 직접 String으로 조회,반환
     */
    @ResponseBody
    @PostMapping("/request-body-string-v4")
    public String requestBodyStringV4(@RequestBody String messageBody) throws IOException {

        //String messageBody = httpEntity.getBody();
        log.info("messageBody={}", messageBody);

        //return new HttpEntity<>("ok");
        return "ok";
    }
}
