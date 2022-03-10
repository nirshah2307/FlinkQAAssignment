package com.flink.utils;

import com.flink.enumeration.ResourcesEnum;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.io.FileReader;
import java.io.IOException;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.HashMap;
import java.util.Properties;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Object to initialize the properties file & different methods to get property values
 * @author Nirmal Shah
 */
public class PropertiesCache {
	private static final Logger log = LogManager.getLogger(Utils.class);
	private final Properties properties = new Properties();

	public PropertiesCache(Path filePath, ResourcesEnum resourcesEnum) throws IOException {
		Path resourceDir = Paths.get(System.getProperty("user.dir")+ Constants.FILE_SEPARATOR,"src",resourcesEnum.name(),"resources");
		FileReader reader = new FileReader(resourceDir+Constants.FILE_SEPARATOR+filePath);
		properties.load(reader);
	}

	public String getProperty(String key) {
		return this.properties.getProperty(key);
	}

	public Set<String> getAllPropertyNames() {
		return this.properties.stringPropertyNames();
	}

	public boolean containsKey(String key) {
		return this.properties.containsKey(key);
	}

	/**
	 * Get all the properties as String Key/Pair value
	 * @return properties key/pair value
	 */
	public HashMap<String,String> getAllProperties(){
		return this.properties.entrySet().stream().collect(
				Collectors.toMap(
						e -> String.valueOf(e.getKey()),
						e -> String.valueOf(e.getValue()),
						(prev, next) -> next, HashMap::new
				));
	}
}
