package org.wudiSpringFramework.aop;

import org.aspectj.weaver.tools.PointcutExpression;
import org.aspectj.weaver.tools.PointcutParser;
import org.aspectj.weaver.tools.ShadowMatch;

import java.lang.reflect.Method;

/**
 * 解析Aspect表达式并且定位被织入的目标
 */
public class PointcutLocator {
    /**
     * Pointcut解析器，直接给它赋值上AspectJ的所有表达式，以便支持对
     * 众多表达式的解析
     */
    private PointcutParser pointcutParser = PointcutParser.getPointcutParserSupportingSpecifiedPrimitivesAndUsingContextClassloaderForResolution(
//            within excutor
            PointcutParser.getAllSupportedPointcutPrimitives()
    );

    /**
     * 表达式解析器
     */
    private PointcutExpression pointcutExpression;


    public PointcutLocator(String expression) {
        this.pointcutExpression = pointcutParser.parsePointcutExpression(expression);
    }


    /**
     * 判断传入的Class对象是否是Aspect的目标代理类，即匹配Pointcut表达式（初筛）
     * @param targetClass
     * @return
     */
    public boolean roughMatches(Class<?> targetClass) {
        // 只能校验within ，无法校验execution call get set 面对无法校验的表达式，直接返回true
        return pointcutExpression.couldMatchJoinPointsInType(targetClass);
    }

    /**
     *  判断传入的Method对象是否是Aspect的目标代理方法，即匹配Pointcut表达式（精筛）
     * @param method
     * @return
     */
    public boolean accurateMatches(Method method) {
        ShadowMatch shadowMatch = pointcutExpression.matchesMethodExecution(method);
        if(shadowMatch.alwaysMatches()) return true;
        else return false;
    }



}
