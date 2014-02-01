package com.acme.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

/**
 * User: sennen
 * Date: 30/01/2014
 * Time: 22:04
 */
@Controller
@RequestMapping("/hello")
public class HelloController {
    private static final Logger LOGGER = LoggerFactory.getLogger(HelloController.class);

    @RequestMapping(method = RequestMethod.GET)
    public String sayHi() {
        LOGGER.info("Hi Client");
        return "helloPaneProvider";
    }
}
