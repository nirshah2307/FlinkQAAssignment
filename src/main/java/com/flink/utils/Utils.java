package com.flink.utils;

import org.apache.commons.lang3.RandomStringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.awaitility.Awaitility;

import java.awt.*;
import java.io.File;
import java.io.IOException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeFormatterBuilder;
import java.util.List;
import java.util.*;
import java.util.concurrent.TimeUnit;

/**
 * Generic Utility class file
 */
public class Utils {
    protected static final Logger log = LogManager.getLogger(Utils.class);

    public static void ClearOrCreateDir() {
        File file = new File(Constants.CURRENT_USER_DIRECTORY + Constants.DEFAULT_FILE_DOWNLOAD_PATH);
        if (!file.exists()) {
            if (file.mkdir())
                log.info("Dir created");
        } else {
            log.info("Dir already exists");
            String LocAndFileDelcmd = "del /q " + Constants.CURRENT_USER_DIRECTORY + Constants.DEFAULT_FILE_DOWNLOAD_PATH + "*";
            try {
                ProcessBuilder builder = new ProcessBuilder("cmd.exe", "/c", LocAndFileDelcmd);
                builder.start();
                log.info("Required Files deleted.");
            } catch (IOException e) {
                e.printStackTrace();
                log.error("Error Occurred while deleting file in " + LocAndFileDelcmd + " location.");
            }
        }
    }
}