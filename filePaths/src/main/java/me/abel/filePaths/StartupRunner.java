package me.abel.filePaths;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.annotation.Order;
import org.springframework.core.io.ClassPathResource;
import org.springframework.stereotype.Component;
import org.springframework.util.CollectionUtils;

import java.io.IOException;
import java.io.InputStream;
import java.net.URI;
import java.nio.file.*;
import java.nio.file.attribute.BasicFileAttributes;
import java.util.List;
import java.util.Map;

/**
 * @description
 * @author Abel.li
 * @contact abel0130@163.com
 * @date 2020-11-21
 * @version
 */
@Order(1)
@Component
@Slf4j
public class StartupRunner implements CommandLineRunner {

    private StartupConfiguration startupConfiguration;

    public StartupRunner(StartupConfiguration startupConfiguration) {
        this.startupConfiguration=startupConfiguration;
    }

    /**
     * 启动时检查文件目录相关的配置，是否已经在服务器上产生，未生成则自动产生目录，并将系统中对应文件夹下的文件放到服务器文件夹里
     * @param args
     */
    @Override
    public void run(String... args) throws IOException {
        Map<String, String> filePaths = startupConfiguration.getFilePaths();

        if (CollectionUtils.isEmpty(filePaths)) {
            log.info("文件夹信息为空，不处理 ");
            return;
        }
        for (String filePath : filePaths.values()) {
            Path path = Paths.get(filePath);
            log.info("开始创建文件夹 {}", filePath);
            if (!Files.exists(path)) {
                try {
                    Files.createDirectory(path);
                } catch (IOException e) {
                    log.info("文件夹创建失败 {}", filePath);
                    e.printStackTrace();
                }
            }
        }

        Map<String, List<String>> pathMapping = startupConfiguration.getPathMapping();
        if (CollectionUtils.isEmpty(pathMapping)) {
            log.info("文件夹映射信息为空，不处理");
            return;
        }
        for (Map.Entry<String, List<String>> entry : pathMapping.entrySet()) {
            log.info("开始处理文件夹映射信息 {}: {}", entry.getKey(), entry.getValue());
            if (!CollectionUtils.isEmpty(entry.getValue())) {
                for (String file : entry.getValue()) {
                    System.out.println(file);
                    ClassPathResource resource = new ClassPathResource(file);
                    System.out.println("resource.exists(): " + resource.exists());
                    InputStream inputStream = resource.getInputStream();
                    Path targetPath=Paths.get(filePaths.get(entry.getKey()));
                    Path newFile = Paths.get(targetPath + file.substring(file.indexOf("/")));
                    System.out.println(targetPath);
                    Files.createFile(newFile);
                    Files.copy(inputStream, newFile, StandardCopyOption.REPLACE_EXISTING);
                }
            }
//            Files.walkFileTree(filePath, new SimpleFileVisitor<Path>() {
//                @Override
//                public FileVisitResult visitFile(Path file,
//                                                 BasicFileAttributes attrs) throws IOException {
//                    Path to = targetPath.resolve(filePath.relativize(file));
//                    // 如果说父路径不存在，则创建
//                    if (Files.notExists(to.getParent())) {
//                        Files.createDirectories(to.getParent());
//                    }
//                    Files.copy(file, to);
//                    return FileVisitResult.CONTINUE;//递归遍历文件，空文件无法复制
//                }
//            });

//            Files.copy(filePath, targetPath, StandardCopyOption.REPLACE_EXISTING);
        }

    }

}
