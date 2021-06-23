package me.abel.learn.multiThread;

import java.util.concurrent.CompletableFuture;

/**
 * @author Abel.li
 * @description
 * @contact abel0130@163.com
 * @date 2020-10-13
 */
public class CompleteService {

    public static void main(String[] args) {
        String result = CompletableFuture.supplyAsync(() -> "hello").thenApply(s -> s + " world").join();
        System.out.println(result);
        Dto dto = new Dto();
        dto.setName("AAA");
        CompletableFuture.supplyAsync(dto::getName).thenApply(s -> s + " world").join();
    }
}
