package com.whb.imageProject.configuration;

import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.stereotype.Component;

/**
 * image存储路径的基础路径
 * Created by yinwenjie on 2017/1/12.
 */
@Component("ImagePathProperties")
@ConfigurationProperties(locations={"classpath:imagePath.properties"} , prefix="imagePath")
public class ImagePathProperties {
    /**
     * image存储的根路径
     * */
    private String imageRoot;

    public String getImageRoot() {
        return imageRoot;
    }

    public void setImageRoot(String imageRoot) {
        this.imageRoot = imageRoot;
    }
}