package com.framework.util;

import lombok.extern.slf4j.Slf4j;


import java.io.File;
import java.io.FileFilter;
import java.net.URL;
import java.util.HashSet;
import java.util.Set;

/**
 *  类  工具包
 */
@Slf4j
public class ClassUtil {

    public static final String FILE_PROTOCOL = "file";

    /**
     * 获取包下类集合
     * @param packageName 包名
     * @return
     */
    public static Set<Class<?>> extractPackageClass(String packageName) {
        log.info("extractPackageClass:{}", packageName);
        // 获取类加载器
        ClassLoader classloader = getClassLoader();
        // 通过类加载器获取到加载的资源
        URL url = classloader.getResource(packageName.replace(".", "/"));
        if(url == null) {
            log.error("无法获取包:{} 下的资源", packageName);
            return null;
        }
        // 根据不同资源的类型，采用不同的方式进行资源的获取，并且构成集合
        Set<Class<?>> classSet = new HashSet<>();
        // 过滤出文件类型的资源
        if(url.getProtocol().equalsIgnoreCase(FILE_PROTOCOL)) {
            File packageDirectory = new File(url.getPath());
            String[] list = packageDirectory.list();
            System.out.println(packageDirectory.toString());
            extractPackageFile(classSet, packageDirectory, packageName);
        }
        // TODO: 过滤其他类型的资源


        return classSet;
    }

    /**
     *  递归获取package里面所有的class文件
     * @param classSet 包含目标类的集合
     * @param packageDirectory 文件或者目录
     * @param packageName 包名
     */
    private static void extractPackageFile(Set<Class<?>> classSet, File packageDirectory, String packageName) {
        log.info("extractPackageFile");
        // 不是文件夹就返回
        if(!packageDirectory.isDirectory()) {
            return ;
        }
        // 获取所有的文件
        File[] files = packageDirectory.listFiles(new FileFilter() {
            @Override
            public boolean accept(File pathname) {
                if(pathname.isDirectory()) return true;
                else {
                    
                }
                return false;
            }
        });


        if(files != null) {
            for (File file : files) {
//                extractPackageClass(classSet, file, packageName);
            }
        }
    }

    /**
     * 获取当前线程的类加载器
     * @return
     */
    public static ClassLoader getClassLoader() {
        return Thread.currentThread().getContextClassLoader();
    }

    public static void main(String[] args) {
        extractPackageClass("com.project.entity");
    }
}
