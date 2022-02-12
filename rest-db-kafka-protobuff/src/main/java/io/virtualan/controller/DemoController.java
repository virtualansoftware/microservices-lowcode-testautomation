package io.virtualan.controller;

import io.virtualan.service.DemoService;

@org.springframework.web.bind.annotation.RestController
public class DemoController {

    @org.springframework.beans.factory.annotation.Autowired
    private DemoService demoService;

    @org.springframework.web.bind.annotation.PostMapping("/demo/{id}/{address}")
    @org.springframework.web.bind.annotation.ResponseBody
    public Object triggerAddress(@org.springframework.web.bind.annotation.PathVariable int id, @org.springframework.web.bind.annotation.PathVariable String address) {
        String result = demoService.run(id, address);
        return result;
    }
}
