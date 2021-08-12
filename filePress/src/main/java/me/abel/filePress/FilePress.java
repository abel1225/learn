package me.abel.filePress;

import net.coobird.thumbnailator.Thumbnails;

import java.io.File;
import java.io.IOException;

/**
 * @Description
 * @Author Abel.li
 * @Date 2021/8/12 下午21:07
 */
public class FilePress {

    public static void main(String[] args) {
        File file = new File("/Users/lizhen/Desktop/IMG_0034.jpeg");
        File newFile = new File("/Users/lizhen/Desktop/IMG_0038.jpeg");
        try {
            //先压缩并保存图片
            Thumbnails.of(file).scale(1.00f)   //压缩尺寸 范围（0.00--1.00）
                    .outputQuality(0.1f)                       //压缩质量 范围（0.00--1.00）
                    .outputFormat("jpg")                          //输出图片后缀
                    .toFile(newFile);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
