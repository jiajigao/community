package com.demo.community.controller;

import com.demo.community.dto.AccessTockenDTO;
import com.demo.community.dto.GitHubUser;
import com.demo.community.provider.GitHubProvider;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import java.net.http.HttpRequest;

@Controller
public class  AuthorizeController {
    @Autowired
    private GitHubProvider gitHubProvider;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String client_secret;

    @Value("${github.client.uri}")
    private String uri;


    @GetMapping("/callback")
    public String callback(@RequestParam(name = "code") String code,
                           @RequestParam(name = "state") String state,
                           HttpServletRequest request){
        AccessTockenDTO accessTockenDTO = new AccessTockenDTO();
        accessTockenDTO.setCode(code);
        accessTockenDTO.setRedirect_uri(uri);
        accessTockenDTO.setClient_id(clientId);
        accessTockenDTO.setClient_secret(client_secret);
        String accessTocken = gitHubProvider.getAccessTockenDTO(accessTockenDTO);
        GitHubUser user = gitHubProvider.getUser(accessTocken);
       // System.out.println(user.getName());
        if(user != null){
            request.getSession().setAttribute("user",user);
            return "redirect:/";
        }else {
            return "redirect:/";
        }
    }
}
