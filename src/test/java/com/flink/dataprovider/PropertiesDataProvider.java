package com.flink.dataprovider;

import com.flink.annotations.TestMethodParameters;
import com.flink.enumeration.ResourcesEnum;
import com.flink.utils.PropertiesCache;
import org.testng.annotations.DataProvider;

import java.io.IOException;
import java.lang.reflect.Method;
import java.nio.file.NoSuchFileException;
import java.nio.file.Paths;

public class PropertiesDataProvider {

    /**
     * One need to mention <b>TestMethodParameters</b> and Property file should be at location
     * <br>test/resources/TestData/properties</br>
     * @param method method reflection
     * @return Map<String,String> of property file
     * @throws IOException if file is missing/not correct name
     */
    @DataProvider(name="getPropertiesData")
    public static Object[][] getPropertiesData(Method method) throws IOException {
        String propertyFile = "";
        if(method.isAnnotationPresent(TestMethodParameters.class)){
            TestMethodParameters annotation = method.getAnnotation(TestMethodParameters.class);
            propertyFile = annotation.propertiesFile();
        }else
            throw new NoSuchFileException("Please provide correct property file");

        PropertiesCache propertiesCache = new PropertiesCache(Paths.get("TestData/properties/"+propertyFile), ResourcesEnum.TEST);
        return new Object[][]{{propertiesCache.getAllProperties()}};
    }
}