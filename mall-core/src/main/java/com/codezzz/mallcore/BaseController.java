package com.codezzz.mallcore;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.servlet.ModelAndView;

/**
 * @author codezzz
 * @version 0.0.1
 * @Description:
 * @date 2021/10/18 11:32
 */

@RestController("/")
public class BaseController {

    @GetMapping
    public ModelAndView base () {
        ModelAndView modelAndView = new ModelAndView("index.html");
        return modelAndView;
    }
}
