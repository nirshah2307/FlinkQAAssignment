package com.flink.listeners;

import com.flink.singleton.Singleton;
import com.flink.utils.PropertiesCache;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.testng.IRetryAnalyzer;
import org.testng.ITestResult;

/**
 * Retry after failure
 * @author Nirmal.SHah
 */
public class Retry implements IRetryAnalyzer {

    private final Singleton singleton = Singleton.getInstance();
    private static final Logger log = LogManager.getLogger(Retry.class);
    private int count = 0;

    @Override
    public boolean retry(ITestResult iTestResult) {

        if (!iTestResult.isSuccess()) {
            if (count < singleton.maxRetry) {
                count++;
                log.info("Going to retry test case: " + iTestResult.getMethod() + ", " + count
                        + " out of " + singleton.maxRetry);
                return true;
            }
        }
        return false;
    }
}
