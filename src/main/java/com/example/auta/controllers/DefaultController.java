package com.example.auta.controllers;import org.springframework.web.bind.annotation.RequestMapping;import org.springframework.web.bind.annotation.RestController;import javax.servlet.http.HttpServletResponse;import java.io.IOException;@RestControllerpublic class DefaultController {    @RequestMapping("/")    public void redirect(HttpServletResponse response) throws IOException {        response.sendRedirect("/swagger-ui.html");    }}