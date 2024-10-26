package com.performance_measurement.perforamancemeasurement.Classes.WebSockets;

import org.springframework.messaging.handler.annotation.MessageMapping;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Controller;
import org.springframework.web.util.HtmlUtils;

@Controller
public class GreetingController {
    //The @MessageMapping annotation ensures that, if a message is sent to the /hello destination, the greeting() method is called.
    @MessageMapping("/hello")

    // The payload of the message is bound to a HelloMessage object, which is passed into greeting().
    @SendTo("/topic/greetings")
    public Greeting greeting(HelloMessage message) throws Exception {
        Thread.sleep(1000); // simulated delay
        return new Greeting("Hello, " + HtmlUtils.htmlEscape(message.getName()) + "!");
    }

}