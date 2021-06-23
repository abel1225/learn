package me.abel.excel.export;

import com.alibaba.excel.util.StringUtils;
import com.google.common.collect.ImmutableMap;
import com.google.common.net.MediaType;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;

import javax.servlet.http.HttpServletResponse;
import java.io.OutputStream;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.UUID;
import java.util.function.Consumer;

/**
 * @author HuangShuCheng
 */
@Slf4j
public class Downloader {

    private final Builder builder;

    private Downloader(Builder builder) {
        this.builder = builder;
    }


    public void process() {
        HttpServletResponse response = builder.response;
        String name = StringUtils.isEmpty(builder.name) ? UUID.randomUUID().toString() + ".xlsx" : builder.name;
        try {
            response.setContentType("application/octet-stream;charset=" + StandardCharsets.UTF_8);
            response.setHeader("Content-Disposition", "attachment;filename*=" + StandardCharsets.UTF_8 + "''" + URLEncoder.encode(name, StandardCharsets.UTF_8.toString()));
            response.addHeader("Pargam", "no-cache");
            response.addHeader("Cache-Control", "no-cache");

            builder.writer.accept(response.getOutputStream());
        } catch (Exception e) {
            try {
                log.error("文件下载失败", e);
                response.setContentType(MediaType.JSON_UTF_8.toString());
                response.getWriter().write(new Gson().toJson(ImmutableMap.of("err", e.getMessage())));
            } catch (Exception e2) {
                log.error("文件下载失败", e2);
            }
        }
    }

    public static class Builder {
        private Consumer<OutputStream> writer;

        private HttpServletResponse response;

        private String name;

        public Builder writer(Consumer<OutputStream> writer) {
            this.writer = writer;
            return this;
        }

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder response(HttpServletResponse response) {
            this.response = response;
            return this;
        }

        public Downloader build() {
            return new Downloader(this);
        }
    }
}
