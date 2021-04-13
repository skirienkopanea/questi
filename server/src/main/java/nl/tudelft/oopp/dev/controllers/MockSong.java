package nl.tudelft.oopp.dev.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ResponseBody;

@Controller
public class MockSong {
    /**
     * GET Endpoint to retrieve a random quote.
     *
     * @return "hi"
     */
    @GetMapping("test")
    @ResponseBody
    public String getTest() {
        return "Test song";
    }
}
