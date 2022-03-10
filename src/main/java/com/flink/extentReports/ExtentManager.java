package com.flink.extentReports;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.aventstack.extentreports.reporter.configuration.Theme;
import com.flink.singleton.Singleton;
import com.flink.utils.Constants;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.Platform;

import java.io.File;

/**
 * For generating extent report
 * @author Nirmal.Shah
 */
public class ExtentManager {

	private static ExtentReports extent;
	private static Platform platform;
	private static final String reportFileName = Constants.EXTENT_REPORT_NAME+".html";
	private static final String macPath = Constants.CURRENT_USER_DIRECTORY + "/TestReport";
	private static final String windowsPath = Constants.CURRENT_USER_DIRECTORY + "\\TestReport";
	private static final String macReportFileLoc = macPath + "/" + reportFileName;
	private static final String winReportFileLoc = windowsPath + "\\" + reportFileName;
	private static final Logger log = LogManager.getLogger(ExtentManager.class);
	private static final Singleton singleton = Singleton.getInstance();
	
	public static ExtentReports getInstance() {
		if (extent == null)
			createInstance();
		return extent;
	}

	// Create an extent report instance
	public static ExtentReports createInstance() {
		platform = getCurrentPlatform();
		String fileName = getReportFileLocation(platform);
		ExtentSparkReporter htmlReporter = new ExtentSparkReporter(fileName);

		htmlReporter.config().setTheme(Theme.DARK);
		htmlReporter.config().setDocumentTitle(fileName);
		htmlReporter.config().setEncoding("utf-8");
		htmlReporter.config().setReportName(fileName);
		htmlReporter.config().setReportName(Constants.EXTENT_REPORT_NAME);
		
		extent = new ExtentReports();
		extent.attachReporter(htmlReporter);
		extent.setSystemInfo("Host Name", Constants.HOST_NAME);
		extent.setSystemInfo("Environment",singleton.AUT_environment);
		extent.setSystemInfo("User Name", Constants.SYSTEM_USER_NAME);
		extent.setSystemInfo("Platform",getCurrentPlatform().toString());
		return extent;
	}

	// Select the extent report file location based on platform
	private static String getReportFileLocation(Platform platform) {
		String reportFileLocation = null;
		switch (platform) {
		case MAC:
			reportFileLocation = macReportFileLoc;
			createReportPath(macPath);
			log.info("ExtentReport Path for MAC: " + macPath + "\n");
			break;
		case WINDOWS:
			reportFileLocation = winReportFileLoc;
			createReportPath(windowsPath);
			log.info("ExtentReport Path for WINDOWS: " + windowsPath + "\n");
			break;
		default:
			log.info("ExtentReport path has not been set! There is a problem!\n");
			break;
		}
		return reportFileLocation;
	}

	// Create the report path if it does not exist
	private static void createReportPath(String path) {
		File testDirectory = new File(path);
		if (!testDirectory.exists()) {
			if (testDirectory.mkdir()) {
				log.info("Directory: " + path + " is created!");
			} else {
				log.info("Failed to create directory: " + path);
			}
		} else {
			log.info("Directory already exists: " + path);
		}
	}

	// Get current platform
	private static Platform getCurrentPlatform() {
		if (platform == null) {
			String operSys = System.getProperty("os.name").toLowerCase();
			if (operSys.contains("win")) {
				platform = Platform.WINDOWS;
			} else if (operSys.contains("nix") || operSys.contains("nux") || operSys.contains("aix")) {
				platform = Platform.LINUX;
			} else if (operSys.contains("mac")) {
				platform = Platform.MAC;
			}
		}
		return platform;
	}
}