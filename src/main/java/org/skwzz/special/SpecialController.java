package org.skwzz.special;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class SpecialController {

    private static final Logger logger = LoggerFactory.getLogger(SpecialController.class);

    @GetMapping("/log-special")
    public String logTest(@RequestParam(defaultValue = "info") String level) {
        logger.info("INFO level log - Testing info log.");
        logger.debug("DEBUG level log - Testing debug log.");
        logger.error("ERROR level log - Testing error log.");
        return "Log test completed.";
    }
}
