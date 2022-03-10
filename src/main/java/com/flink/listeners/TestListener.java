package com.flink.listeners;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.flink.extentReports.ExtentManager;
import com.flink.singleton.Singleton;
import com.flink.utils.UserActionUtility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.ITestContext;
import org.testng.ITestListener;
import org.testng.ITestNGMethod;
import org.testng.ITestResult;

import java.util.ArrayList;
import java.util.List;

/**
 * Test listener to handle multiple testNG events
 * @author Nirmal.Shah
 */
public class TestListener implements ITestListener {

    //Extent Report Declarations
    public static final ExtentReports extent = ExtentManager.createInstance();
    private static final Logger log = LogManager.getLogger(TestListener.class);
    public static ThreadLocal<ExtentTest> test = new ThreadLocal<>();
    public static ExtentTest extentTest = null;
    Singleton singleton = Singleton.getInstance();

    @Override
    public synchronized void onStart(ITestContext context) {
        try {
            log.info("Test Suite started!");
        } catch (Exception e) {
            log.info(e);
        }
    }

    @Override
    public synchronized void onFinish(ITestContext context) {

        if (context.getFailedTests().getAllResults().size() > 0) {

            List<ITestNGMethod> methodsToRemove = new ArrayList<>();

            for (ITestResult failed_result : context.getFailedTests().getAllResults()) {
                String failed_testName = failed_result.getMethod().getMethodName();
                String failingTest_className = failed_result.getClass().getName();
                for (ITestResult passed_result : context.getPassedTests().getAllResults()) {
                    String passing_testName = passed_result.getMethod().getMethodName();
                    String passingTest_className = failed_result.getClass().getName();
                    if (failed_testName.equals(passing_testName) && passingTest_className.equals(failingTest_className)) {
                        if (passed_result.getEndMillis() > failed_result.getEndMillis()) {
                            methodsToRemove.add(failed_result.getMethod());
                            break;
                        }

                    }
                }
            }

            // remove the test that passed on retry
            for (ITestNGMethod failedMethodToRemove : methodsToRemove) {
                context.getFailedTests().removeResult(failedMethodToRemove);
            }

        } else {
            log.info("Congrats, All test cases are passed");
        }
        log.info(("Test Suite is ending!"));
        extent.flush();

    }

    @Override
    public synchronized void onTestStart(ITestResult result) {
        try {
            log.info((result.getMethod().getMethodName() + " started!"));
            extentTest = extent.createTest(result.getMethod().getMethodName(), result.getMethod().getDescription());
            test.set(extentTest);
        } catch (Exception e) {
            log.info(e);
        }

    }

    @Override
    public synchronized void onTestSuccess(ITestResult result) {
        try {
            log.info(("Test case" + result.getMethod().getMethodName() + " passed!"));
            test.get().pass("Test passed").addScreenCaptureFromBase64String(UserActionUtility.captureCurrentScreenshot(singleton.driver), result.getMethod().getMethodName()).getModel().getMedia().get(0);
        } catch (Exception e) {
            log.info(e);
        }
    }

    @Override
    public synchronized void onTestFailure(ITestResult result) {

        try {
            log.info((result.getMethod().getMethodName() + " failed!"));
            result.setStatus(ITestResult.FAILURE);
            test.get().fail(result.getThrowable()).addScreenCaptureFromBase64String(UserActionUtility.captureCurrentScreenshot(singleton.driver), result.getMethod().getMethodName()).getModel().getMedia().get(0);
        } catch (Exception e) {
            log.info(e);
        }
    }

    @Override
    public synchronized void onTestSkipped(ITestResult result) {
        try {
            log.info((result.getMethod().getMethodName() + " skipped!"));
            test.get().skip(result.getThrowable());
        } catch (Exception e) {
            log.info(e);
        }
    }

    @Override
    public void onTestFailedButWithinSuccessPercentage(ITestResult result) {
        try {
            log.info(("onTestFailedButWithinSuccessPercentage for " + result.getMethod().getMethodName()));
        } catch (Exception e) {
            log.info(e);
        }
    }
}