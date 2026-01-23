package kr1v.malilibApiTest.config;

import kr1v.malilibApi.annotation.Config;
import kr1v.malilibApiTest.Init;
import kr1v.malilibApiTest.custo.ConfigClass;

@Config(Init.MOD_ID)
public class CustomConfigTest {
    public static final ConfigClass TEST_CUSTOM = new ConfigClass("Test custom", "This is a test for a custom config class");
}
