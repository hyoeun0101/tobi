package com.spring.tobi.ch6;

import com.spring.tobi.ch6.pointcut.Bean;
import com.spring.tobi.ch6.pointcut.Target;
import org.junit.jupiter.api.Test;
import org.springframework.aop.aspectj.AspectJExpressionPointcut;
import static org.assertj.core.api.Assertions.*;

public class PointcutExpressionTest {
    @Test
    public void methodSignaturePointcut() throws SecurityException, NoSuchMethodException {
        AspectJExpressionPointcut pointcut = new AspectJExpressionPointcut();
        pointcut.setExpression("execution(public int "+
                "com.spring.tobi.ch6.pointcut.Target.minus(int,int) "+ "throws java.lang.RuntimeException)"
                );

        assertThat(pointcut.getClassFilter().matches(Target.class)
                && pointcut.getMethodMatcher().matches(Target.class.getMethod("minus", int.class, int.class), null))
                .isTrue();

        assertThat(pointcut.getClassFilter().matches(Target.class)
                && pointcut.getMethodMatcher().matches(Target.class.getMethod("plus", int.class, int.class), null))
                .isFalse();

        assertThat(pointcut.getClassFilter().matches(Bean.class)
                && pointcut.getMethodMatcher().matches(Target.class.getMethod("method"), null))
                .isFalse();
    }
}
