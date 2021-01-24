package com.gregorriegler.springtestcontextconfig;

import org.springframework.stereotype.Component;

@Component
public class ScannedComponent {

    public String hello() {
        return "world";
    }
}
