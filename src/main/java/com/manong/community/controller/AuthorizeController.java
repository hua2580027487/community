package com.manong.community.controller;

import com.manong.community.dto.AccessTokenDTO;
import com.manong.community.dto.GithubUser;
import com.manong.community.provider.GithubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServletRequest;

@Controller
public class AuthorizeController {

    @Value("${github.client_id}")
    private String client_id;

    @Value("${github.client_secret}")
    private String client_secret;

    @Value("${github.callback_url}")
    private String callback_url;

    @Autowired
    private GithubProvider githubProvider;

    @RequestMapping("/callback")
    public String callBack(@RequestParam(name = "code") String code ,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request){
        AccessTokenDTO accessTokenDTO = new AccessTokenDTO();
        accessTokenDTO.setClient_id(client_id);
        accessTokenDTO.setClient_secret(client_secret);
        accessTokenDTO.setRedirect_uri(callback_url);
        accessTokenDTO.setCode(code);
        accessTokenDTO.setState(state);
        String accessToken = githubProvider.getAccessToken(accessTokenDTO);
        GithubUser githubUser = githubProvider.getGithubUser(accessToken);
        if(githubUser != null){
            //登录成功
            request.getSession().setAttribute("user",githubUser);
            return "redirect:/";
        }else{
            //登录失败
            return "redirect:/";
        }
    }
}
