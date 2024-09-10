package org.skwzz.controller;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class LogTestController {

    private static final Logger logger = LoggerFactory.getLogger(LogTestController.class);

    @GetMapping("/log-test")
    public String logTest(@RequestParam(defaultValue = "info") String level) {
        // 로그 레벨에 따른 로그 생성
        switch (level.toLowerCase()) {
            case "debug":
                logger.debug("DEBUG level log - This is a debug message.");
                break;
            case "error":
                logger.error("ERROR level log - This is an error message.");
                break;
            default:
                logger.info("INFO level log - This is an info message.");
                break;
        }
        return "Logs have been generated for level: " + level.toUpperCase();
    }

    @GetMapping("/log-error")
    public String logError() {
        try {
            // 의도적으로 에러를 발생시켜 로그를 남김
            throw new RuntimeException("Test exception for error logging");
        } catch (Exception e) {
            logger.error("An error occurred: ", e);
        }
        return "Error log has been generated!";
    }
}
