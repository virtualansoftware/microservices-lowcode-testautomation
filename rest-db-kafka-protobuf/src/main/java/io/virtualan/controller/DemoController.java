package io.virtualan.controller;

import io.virtualan.service.DemoService;

@org.springframework.web.bind.annotation.RestController
public class DemoController {

    @org.springframework.beans.factory.annotation.Autowired
    private DemoService demoService;

    @org.springframework.web.bind.annotation.PostMapping("/demo/{id}/{name}")
    @org.springframework.web.bind.annotation.ResponseBody
    public Object triggerAddress(@org.springframework.web.bind.annotation.PathVariable int id, @org.springframework.web.bind.annotation.PathVariable String name) {
        String result = demoService.run(id, name);
        return result;
    }
}
