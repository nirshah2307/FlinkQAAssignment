package testrunner;

import com.flink.singleton.Singleton;
import com.flink.utils.ConfigReader;
import com.flink.utils.Constants;
import com.flink.utils.UserActionUtility;
import com.flink.utils.Utils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.PageLoadStrategy;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebDriverException;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.remote.CapabilityType;
import org.testng.annotations.*;

import java.io.IOException;
import java.lang.reflect.Method;
import java.time.Duration;
import java.util.Hashtable;
import java.util.Map;

public class TestInitializer {

    private static final Logger log = LogManager.getLogger(TestInitializer.class);
    private final Singleton singleton = Singleton.getInstance();
    protected WebDriver driver;

    @BeforeSuite
    @Parameters({"environment"})
    public void beforeSetup(String environment) throws InterruptedException, IOException {
        ConfigReader configReader = new ConfigReader();
        configReader.loadConfig("application.conf");
        singleton.AUT_URL = configReader.getConfigString("environment."+environment+".url");
        //set other values
        singleton.maxRetry = Integer.parseInt(configReader.getConfigString("failRetry.MaxRetryCount"));
    }

    @BeforeTest
    @Parameters({"browser"})
    public void Setup(String browser) {
        Utils.ClearOrCreateDir();
        if (browser.equalsIgnoreCase("chrome")) {
            launchChromeBrowser(singleton.AUT_URL);
        } else if (browser.equalsIgnoreCase("firefox")) {
            launchWebdriverFirefox(singleton.AUT_URL);
        }
    }

    @AfterTest
    public void CloseBrowserWindow() throws InterruptedException {
        //driver.close();
    }

    @BeforeClass
    public void beforeClass() throws InterruptedException {
        log.info("class Name " + this.getClass().getSimpleName());
    }

    @AfterClass
    public void tearDown() {
        log.info("Tests Ended from class :::::" + this.getClass().getSimpleName());
    }

    @BeforeMethod
    public void beforeMethodCalled(Method method) throws InterruptedException {
        log.info("Testcase name is :::::: " + method.getName());
        log.info(method.getName() + " Started ::::");
    }

    @AfterMethod()
    public void afterMethodCalled(Method method) throws InterruptedException, IOException {
        log.info("Testcase execution completed :::::: " + method.getName());
    }

    @AfterSuite
    public void suiteEndReached() {
        log.info("Logger Info:: Inside suiteEndReached Method");
        log.info("Suite ended");
    }

    private void launchWebdriverFirefox(String url) {
        try {
            String scriptRunEnv = "firefoxDriverExecutable";
            if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
                System.setProperty("webdriver.gecko.driver", Constants.DRIVER_PATH + scriptRunEnv +  "\\geckodriver.exe");

            } else if (System.getProperty("os.name").toUpperCase().contains("MAC")) {
                System.setProperty("webdriver.gecko.driver",  Constants.DRIVER_PATH + scriptRunEnv + "\\geckodriver");
            }

            String downloadFilepath = Constants.CURRENT_USER_DIRECTORY + Constants.DEFAULT_FILE_DOWNLOAD_PATH;

            FirefoxProfile firefoxProfile = new FirefoxProfile();
            firefoxProfile.setPreference("browser.download.folderList", 2);
            firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
            firefoxProfile.setPreference("browser.download.dir", downloadFilepath);
            firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "text/csv");

            driver = new FirefoxDriver();
            driver.get(url);
            driver.manage().window().maximize();
            driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(300));
        } catch (Exception e) {
            log.info("Exception message ::" + e);
            driver.close();
        }
    }

    private void launchChromeBrowser(String url) {
        try {
            String scriptRunEnv = "chromeDriverExecutable";

            if (System.getProperty("os.name").toUpperCase().contains("WINDOWS")) {
                System.setProperty("webdriver.chrome.driver", Constants.DRIVER_PATH + scriptRunEnv + "\\chromedriver.exe");

            } else if (System.getProperty("os.name").toUpperCase().contains("MAC")) {
                System.setProperty("webdriver.chrome.driver", Constants.DRIVER_PATH + scriptRunEnv + "\\chromedriver");
            }
            String downloadFilepath = Constants.CURRENT_USER_DIRECTORY + Constants.DEFAULT_FILE_DOWNLOAD_PATH;
            Map<String, Object> preferences = new Hashtable<String, Object>();
            preferences.put("download.default_directory", downloadFilepath);
            preferences.put("profile.default_content_settings.popups", 0);
            preferences.put("download.prompt_for_download", "false");

            // disable flash and the PDF viewer
            preferences.put("plugins.plugins_disabled", new String[]{"Adobe Flash Player", "Chrome PDF Viewer"});

            ChromeOptions options = new ChromeOptions();
            options.setExperimentalOption("prefs", preferences);
            options.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
            options.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
			options.setPageLoadStrategy(PageLoadStrategy.NORMAL);

            log.info(" =================================================== ");
            log.info("Url is :: " + url);
            log.info(" =================================================== ");

            singleton.driver = driver = new ChromeDriver(options);
            driver.get(url);
            driver.manage().window().maximize();
            UserActionUtility.waitForPageLoad(driver);
        } catch (WebDriverException e) {
            e.printStackTrace();
            driver.close();
        }
    }
}
