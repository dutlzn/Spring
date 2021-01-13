package org.wudiSpringFramework.aop;

import org.wudiSpringFramework.aop.annotation.Aspect;
import org.wudiSpringFramework.aop.annotation.Order;
import org.wudiSpringFramework.aop.aspect.AspectInfo;
import org.wudiSpringFramework.aop.aspect.DefaultAspect;
import org.wudiSpringFramework.core.BeanContainer;
import org.wudiSpringFramework.util.ValidationUtil;
import java.util.*;

public class AspectWeaver {
    private BeanContainer beanContainer; // 容器是绝对单例

    public AspectWeaver() {
        this.beanContainer = BeanContainer.getInstance();
    }

    public void doAop() {
        //1.获取所有的切面类
        Set<Class<?>> aspectSet = beanContainer.getClassesByAnnotation(Aspect.class);
        if(ValidationUtil.isEmpty(aspectSet)){return;}
        //2.拼装AspectInfoList，拿到通知器
        List<AspectInfo> aspectInfoList = packAspectInfoList(aspectSet);
        //3.遍历容器里的类
        Set<Class<?>> classSet = beanContainer.getClasses();
        for (Class<?> targetClass: classSet) {
            //排除AspectClass自身
            if(targetClass.isAnnotationPresent(Aspect.class)){
                continue;
            }
            //4.粗筛符合条件的Aspect
            List<AspectInfo> roughMatchedAspectList  = collectRoughMatchedAspectListForSpecificClass(aspectInfoList, targetClass);
            //5.尝试进行Aspect的织入
            wrapIfNecessary(roughMatchedAspectList,targetClass);
        }

    }

    private List<AspectInfo> packAspectInfoList(Set<Class<?>> aspectSet) {
        List<AspectInfo> aspectInfoList = new ArrayList<>();
        for(Class<?> aspectClass : aspectSet){
            if (verifyAspect(aspectClass)){
                Order orderTag = aspectClass.getAnnotation(Order.class);
                Aspect aspectTag = aspectClass.getAnnotation(Aspect.class);
                DefaultAspect defaultAspect = (DefaultAspect) beanContainer.getBean(aspectClass);
                //初始化表达式定位器
                PointcutLocator pointcutLocator = new PointcutLocator(aspectTag.pointcut());
                AspectInfo aspectInfo = new AspectInfo(orderTag.value(), defaultAspect, pointcutLocator);
                aspectInfoList.add(aspectInfo);
            } else {
                //不遵守规范则直接抛出异常
                throw new RuntimeException("@Aspect and @Order must be added to the Aspect class, and Aspect class must extend from DefaultAspect");
            }
        }
        return aspectInfoList;
    }

    //框架中一定要遵守给Aspect类添加@Aspect和@Order标签的规范，同时，必须继承自DefaultAspect.class
    //此外，@Aspect的属性值不能是它本身
    private boolean verifyAspect(Class<?> aspectClass) {
        return aspectClass.isAnnotationPresent(Aspect.class) &&
                aspectClass.isAnnotationPresent(Order.class) &&
                DefaultAspect.class.isAssignableFrom(aspectClass);
    }

    private List<AspectInfo> collectRoughMatchedAspectListForSpecificClass(List<AspectInfo> aspectInfoList, Class<?> targetClass) {
        List<AspectInfo> roughMatchedAspectList = new ArrayList<>();
        for(AspectInfo aspectInfo : aspectInfoList){
            //粗筛，找到该bean对应的切面类，把这个类add到list中
            if(aspectInfo.getPointcutLocator().roughMatches(targetClass)){
                roughMatchedAspectList.add(aspectInfo);
            }
        }
        return roughMatchedAspectList;
    }


    private void wrapIfNecessary(List<AspectInfo> roughMatchedAspectList, Class<?> targetClass) {
        if(ValidationUtil.isEmpty(roughMatchedAspectList)){return;}
        //创建动态代理对象
        AspectListExecutor aspectListExecutor = new AspectListExecutor(targetClass, roughMatchedAspectList);
        Object proxyBean = ProxyCreate.createProxy(targetClass, aspectListExecutor);
        // 更新容器
        beanContainer.addBean(targetClass, proxyBean);
    }

//    public void doAop() {
//        // 1 获取所有的切面类
//        Set<Class<?>> aspectSet = beanContainer.getClassesByAnnotation(Aspect.class);
//
//        // 2 将切面类按照不同的织入目标进行切分
//        Map<Class<? extends Annotation>, List<AspectInfo>> categorizedMap = new HashMap<>();
//        if(ValidationUtil.isEmpty(aspectSet)) {
//            return ;
//        }
//        for (Class<?> aspectClass : aspectSet) {
//            if(verifyAspect(aspectClass)) {
//                categorizeAspect(categorizedMap, aspectClass);
//            } else {
//                throw new RuntimeException("@Aspect and @Order have not been added to the Aspect class," +
//                        "or Aspect class does not extend from DefaultAspect, or the value in Aspect Tag equals @Aspect");
//            }
//        }
//        // 3 按照不同的织入目标分别去按序织入Aspect的逻辑
//        if(ValidationUtil.isEmpty(categorizedMap)) {return ;}
//        for (Class<? extends Annotation> category : categorizedMap.keySet()) {
//            weaveByCategory(category, categorizedMap.get(category));
//        }
//    }
//
//    /**
//     *
//     * @param category
//     * @param aspectInfos
//     */
//    private void weaveByCategory(Class<? extends Annotation> category, List<AspectInfo> aspectInfos) {
//        // 1 获取被代理的类的集合
//        Set<Class<?>> classSet = beanContainer.getClassesByAnnotation(category);
//        if(ValidationUtil.isEmpty(classSet)) {return ;}
//        // 2 遍历被代理类，分别为每个被代理类生成动态代理实例
//        for (Class<?> targetClass : classSet) {
//            // 创建动态代理对象
//            AspectListExecutor aspectListExecutor = new AspectListExecutor(targetClass, aspectInfos);
//            Object proxyBean = ProxyCreate.createProxy(targetClass, aspectListExecutor);
//            // 3 将动态代理对象实例添加到容器里，取代未被代理前的实例
//            beanContainer.addBean(targetClass, proxyBean);
//        }
//
//    }
//
//    /**
//     * 2.将切面类按照不同的织入目标进行切分
//     * @param categorizedMap
//     * @param aspectClass
//     */
//    private void categorizeAspect(Map<Class<? extends Annotation>, List<AspectInfo>> categorizedMap, Class<?> aspectClass) {
//        Order orderTag = aspectClass.getAnnotation(Order.class);
//        Aspect aspectTag = aspectClass.getAnnotation(Aspect.class);
//        DefaultAspect aspect = (DefaultAspect)beanContainer.getBean(aspectClass);
//        AspectInfo aspectInfo = new AspectInfo(orderTag.value(), aspect);
//        if(!categorizedMap.containsKey(aspectTag.value())) {
//            // 如果织入的joinpoint第一次出现，则以该joinpoint为key，以新创建的List<AspectInfo>为value
//            List<AspectInfo> aspectInfoList = new ArrayList<>();
//            aspectInfoList.add(aspectInfo);
//            categorizedMap.put(aspectTag.value(), aspectInfoList);
//        } else {
//            // 如果织入的joinpoint不是第一次出现，则往joinpoint对应的value里添加新的Aspect逻辑
//            List<AspectInfo> aspectInfoList = categorizedMap.get(aspectTag.value());
//            aspectInfoList.add(aspectInfo);
//        }
//    }
//
//    /**
//     *  框架中一定要遵守给Aspect类添加@Aspect和@Order标签的规范
//     *  同时，必须继承自DefaultAspect.class
//     *  此外，@Aspect的属性值不能是他本身
//     * @param aspectClass
//     * @return
//     */
//    private boolean verifyAspect(Class<?> aspectClass) {
//        return aspectClass.isAnnotationPresent(Aspect.class) &&
//                aspectClass.isAnnotationPresent(Order.class) &&
//                DefaultAspect.class.isAssignableFrom(aspectClass) &&
//                aspectClass.getAnnotation(Aspect.class).value() != Aspect.class;
//    }
}
