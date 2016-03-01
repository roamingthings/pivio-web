package io.pivio.view;

import org.apache.commons.cli.*;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.io.FileNotFoundException;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

@SpringBootApplication
public class AppLauncher {

    public static void main(String[] args) throws ParseException {
        SpringApplication.run(AppLauncher.class, args);
    }
}
