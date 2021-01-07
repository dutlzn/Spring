package org.wudiSpringFramework.core;

import lombok.AccessLevel;
import lombok.NoArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.wudiSpringFramework.core.annotation.Component;
import org.wudiSpringFramework.core.annotation.Controller;
import org.wudiSpringFramework.core.annotation.Repository;
import org.wudiSpringFramework.core.annotation.Service;
import org.wudiSpringFramework.util.ClassUtil;
import org.wudiSpringFramework.util.ValidationUtil;

import java.lang.annotation.Annotation;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.ConcurrentHashMap;

@Slf4j
// 私有的构造函数
@NoArgsConstructor(access = AccessLevel.PRIVATE)
public class BeanContainer {
    /**
     * 存放所有被配置标记的目标对象的Map
     * Class对象----对应的实例
     */
    private final Map<Class<?>, Object> beanMap = new ConcurrentHashMap();
    /**
     * 加载bean的注解列表
     */
    private static final List<Class<? extends Annotation>> BEAN_ANNOTATION
            = Arrays.asList(Component.class, Controller.class, Service.class, Repository.class);


    /**
     * 获取Bean容器实例
     * @return BeanContainer
     */
    public static BeanContainer  getInstance() {
        return ContainerHolder.HOLDER.instance;
    }
    public enum ContainerHolder {
        HOLDER; // 用来存放单例
        private BeanContainer instance;
        ContainerHolder() {
            instance = new BeanContainer();
        }
    }

    /**
     * 容器是否已经加载过bean
     */
    private boolean loaded = false;

    /**
     * 是否已加载过Bean
     *
     * @return 是否已加载
     */
    public boolean isLoaded() {
        return loaded;
    }



    /**
     * 扫描加载所有Bean
     *
     * @param packageName 包名
     */
    public synchronized void loadBeans(String packageName) {
        // 判断bean容器是否被加载过
        if(isLoaded()) {
            log.warn("BeanContainer has been loaded.");
            return ;
        }
        Set<Class<?>> classSet = ClassUtil.extractPackageClass(packageName);
//        if(classSet == null || classSet.isEmpty()) {
        if(ValidationUtil.isEmpty(classSet)) {
            log.warn("extract nothing from packageName:" + packageName);
            return ;
        }
        for (Class<?> clazz : classSet) {
            for (Class<? extends Annotation> annotation : BEAN_ANNOTATION) {
                //如果类上面标记了定义的注解
                if (clazz.isAnnotationPresent(annotation)) {
                    //将目标类本身作为键，目标类的实例作为值，放入到beanMap中
                    beanMap.put(clazz, ClassUtil.newInstance(clazz, true));
                }
            }
        }

        loaded = true;
    }

    /**
     * Bean实例数量
     *
     * @return 数量
     */
    public int size() {
        return beanMap.size();
    }


}
