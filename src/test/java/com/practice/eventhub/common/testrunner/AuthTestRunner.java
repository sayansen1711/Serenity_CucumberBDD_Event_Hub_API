package com.practice.eventhub.common.testrunner;

import org.junit.platform.suite.api.ConfigurationParameter;
import org.junit.platform.suite.api.IncludeEngines;
import org.junit.platform.suite.api.SelectClasspathResource;
import org.junit.platform.suite.api.Suite;

import static io.cucumber.junit.platform.engine.Constants.*;

@Suite
@IncludeEngines("cucumber")
//@SelectClasspathResource("features/User_Registration_API.feature")
//@SelectClasspathResource("features/User_Login_API.feature")
@SelectClasspathResource("features/Get_User_API.feature")
@ConfigurationParameter(key = PLUGIN_PROPERTY_NAME,
        value="pretty, html:target/reports/serenity.html, net.serenitybdd.cucumber.core.plugin.SerenityReporterParallel")
@ConfigurationParameter(key=GLUE_PROPERTY_NAME,
        value = "com.practice.eventhub.common.stepdefinition, com.practice.eventhub.common.util")
//@ConfigurationParameter(key = FILTER_TAGS_PROPERTY_NAME,
//        value = "@LoginTestCase or @LoginNegativeTestCase")
public class AuthTestRunner {
}
