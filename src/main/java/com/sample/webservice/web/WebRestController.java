package com.sample.webservice.web;

import com.sample.webservice.domain.posts.PostsSaveRequestDto;
import com.sample.webservice.service.PostsService;
import lombok.AllArgsConstructor;
import org.apache.tomcat.util.http.parser.Authorization;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.nio.file.AccessDeniedException;

@RestController
@AllArgsConstructor
public class WebRestController {

    private PostsService postsService;

    @GetMapping("/hello")
    public String hello(){
        return "HelloWorld";
    }

    //.http 테스트 처리용
    @GetMapping("/dev/hello")
    public String devHello(HttpServletRequest request){
        String auth = request.getHeader("Authorization");

        if(!"DEV".equals(auth)){
            throw new AccessDeniedException();
        }

        return "Dev Hello";
    }
    //.http 테스트 처리용
    @GetMapping("/real/hello")
    public String realHello(HttpServletRequest request){
        String auth = request.getHeader("Authorization");

        if(!"PRODUCTION".equals(auth)){
            throw new AccessDeniedException();
        }

        return "production Hello";
    }

    @PostMapping("/posts")
    public Long savePosts(@RequestBody PostsSaveRequestDto dto){
        return postsService.save(dto);
    }

    //.http 테스트 처리용
    @ResponseStatus(HttpStatus.UNAUTHORIZED)
    static class AccessDeniedException extends RuntimeException{

    }

}
