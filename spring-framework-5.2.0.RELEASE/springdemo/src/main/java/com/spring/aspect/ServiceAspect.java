package com.spring.aspect;

import com.spring.introduction.LittleUniverse;
import org.aopalliance.intercept.Joinpoint;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.*;
import org.springframework.stereotype.Component;

@Aspect // 被织入的通用逻辑
@Component
public class ServiceAspect {
	/**
	 *  {@link} https://www.mekau.com/4880.html
	 *  {@link} https://www.cnblogs.com/liaojie970/p/7883687.html
	 *  第一个 * 是任何返回类型的方法都会被触发
	 *  .. 该 package 和 子package
	 *  * 所有的类
	 *  .* 类里所有的方法
	 *  (..) 不管传入的参数都会执行
	 */
	@Pointcut(value = "execution(* com.spring.service..*.*(..))")
	public void embed() {};

	@Before(value = "embed()")
	public void before(JoinPoint joinPoint) {
		System.out.println("前置通知 " + joinPoint);
	}

	@After(value = "embed()")
	public void after(JoinPoint joinPoint) {
		System.out.println("后置通知 "+ joinPoint);
	}

	@Around("embed()")
	public Object aroundMe(ProceedingJoinPoint jp) throws Throwable {
		try {
			System.out.println("环绕通知之前....");
			// 回调目标对象的原有方法
			jp.proceed();
		} catch (Throwable t) {
			System.out.println(t.getMessage());;
		} finally {
			System.out.println("环绕通知之后 ....");
		}
		return new Object();
	}
	@AfterReturning(pointcut = "embed()", returning = "returnValue")
	public void afterReturning(JoinPoint joinPoint, Object returnValue) {
		System.out.println("无论是空还是有值都返回 "  + joinPoint + " ，返回值【" + returnValue +"】");
	}
	@AfterThrowing(pointcut = "embed()" , throwing = "exception")
	public void afterThrowing(Throwable exception) {
		System.out.println("在异常之后");
	}

	@DeclareParents(value = "com.spring.controller..*", defaultImpl = com.spring.introduction.impl.LittleUniverseImpl.class)
	public LittleUniverse littleUniverse;

//
//	@Around("embed()")
//	public Object aroundMe(Joinpoint joinpoint) {
//		long startTime = System.currentTimeMillis();
//		Object returnValue = null;
//		System.out.println("开始计时:" + joinpoint);
//		try {
//			returnValue = (ProceedingJoinPoint)joinpoint.proceed();
//			System.out.println("执行成功，结束计时 " +  joinpoint);
//		} catch (Throwable throwable) {
//			throwable.printStackTrace();
//			System.out.println("执行失败，结束计时 " +  joinpoint);
//		} finally {
//			long endTime = System.currentTimeMillis();
//			System.out.println("总耗时:" + joinpoint + "[" + (endTime - startTime) + "]ms");
//		}
//		return returnValue;
//	}
//
//	@AfterReturning(pointcut = "embed()", returning = "returnValue")
//	public void afterReturning(Joinpoint joinpoint, Object returnValue) {
//		System.out.println("无论是空还是有值都返回 " + joinpoint + " ，返回值【" + returnValue +"】");
//	}


//	@AfterThrowing(pointcut = "embed()", throwing = "exception")
//	public void afterThrowing(Joinpoint joinpoint, Exception exception) {
//		System.out.println("抛出异常通知 " + joinpoint + " " + exception.getMessage());
//	}
}
