package me.abel.filePaths;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Map;

/**
 * @description
 * @author Abel.li
 * @contact abel0130@163.com
 * @date 2020-11-21
 * @version
 */
@Data
@Component
@ConfigurationProperties(prefix="application")
public class StartupConfiguration {

    private Map<String, String> filePaths;

    private Map<String, List<String>> pathMapping;


}
