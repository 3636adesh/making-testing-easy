package com.example;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.condition.EnabledOnOs;
import org.junit.jupiter.api.condition.OS;

public class ConditionalTestExecutionDemoTest {

    @Test
    @EnabledOnOs(OS.MAC)
    void should_RunOn_MacOS_Only() {
        System.out.println("run on mac");
    }

    @Test
    @EnabledOnOs(OS.WINDOWS)
    void should_RunOn_Windows() {
        System.out.println("run on windows");
    }

    @Test
    @EnabledOnOs(OS.LINUX)
    void should_RunOn_Linux() {
        System.out.println("run on linux");
    }
}
