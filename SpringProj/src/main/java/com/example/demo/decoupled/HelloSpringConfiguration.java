package com.example.demo.decoupled;

import org.springframework.context.annotation.Bean;

public class HelloSpringConfiguration {
    @Bean
    public MessageProvider provider() {
        return new HelloSpringMessageProvider();
    }

    @Bean
    public MessageRenderer renderer() {
        MessageRenderer renderer =
                new OutputMessageRenderer();
        renderer.setMessageProvider(provider());
        return renderer;
    }
}
