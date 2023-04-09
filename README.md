# spring aop

## aop의 주요 개념
- aspect : 흩어진 관심사를 모듈화 한 것
- target : aspect를 적용하는 곳(클래스, 메서드..)
- advice : 실제로 어떤 일을 해야 할 지에 대한 것, 실질적인 부가기능을 담은 구현체
- joint point : advice가 적용될 위치, 끼어들 수 있는 지점, 메서드 진입 시점, 생성자 호출 시점, 필드에서 값을 꺼내올 때 등 다양한 시점에 적용가능
- point cut : jointpoint의 상세한 스펙을 정의한 것, A란 메서드의 진입 시점에 호출할 것과 같이 더욱 구체적으로 advice가 실행될 지점을 정할 수 있음

## 스프링 aop의 특징
- 프록시 패턴 기반의 aop 구현체, 프록시 객체를 쓰는 이유는 접근 제어 및 부가기능을 추가하기 위해서임
- 스프링 빈에만 적용 가능

## aop 의존성 추가
```java
@Component
@Aspect
public class PerfAspect {

@Around("execution(* com.saelobi..*.EventService.*(..))")
public Object logPerf(ProceedingJoinPoint pjp) throws Throwable{
long begin = System.currentTimeMillis();
Object retVal = pjp.proceed(); // 메서드 호출 자체를 감쌈
System.out.println(System.currentTimeMillis() - begin);
return retVal;
}
}
```

- @Around : 타겟 메서드를 감싸서 특정 Advice를 실행한다는 의미
- "execution(* com.saelobi..*.EventService.*(..))”는 com.saelobi 아래의 패키지 경로의 EventService 객체의 모든 메서드에 Aspect를 적용하겠다는 의미
- 경로 지정 외에도 어노테이션이 붙은 포인트에 해당 Aspect를 실행할 수 있는 기능도 제공
- 어노테이션을 커스텀해서 만든 코드
```java
@Component
@Aspect
public class PerfAspect {

@Around("@annotation(PerLogging)")
public Object logPerf(ProceedingJoinPoint pjp) throws Throwable{
long begin = System.currentTimeMillis();
Object retVal = pjp.proceed(); // 메서드 호출 자체를 감쌈
System.out.println(System.currentTimeMillis() - begin);
return retVal;
}
}
```

- 실제 적용
```java
    @PerLogging
    @Override
    public void createEvent() {
        try {
            Thread.sleep(1000);
        } catch(InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Created an event");
    }
```

- 스프링 빈의 모든 메서드에 적용하는 어노테이션
```java
@Component
@Aspect
public class PerfAspect {

@Around("bean(simpleEventService)")
public Object logPerf(ProceedingJoinPoint pjp) throws Throwable{
long begin = System.currentTimeMillis();
Object retVal = pjp.proceed(); // 메서드 호출 자체를 감쌈
System.out.println(System.currentTimeMillis() - begin);
return retVal;
}
}
```

## 그 외 다양한 어노테이션
- @Before : advice 타겟 메서드가 호출되기 전에 advice 기능을 수행
- @After : 타겟 메서드의 결과에 관계없이 타겟 메서드가 완료되면
- @AfterReturning(정상적 반환 이후)  : 타겟 메서드가 성공적으로 결과값을 반환한 후에 advice 기능을 수행
- @AfterThrowing(예외 발생 이후) : 타겟 메서드가 수행 중 예외를 던지게 되면 advice 기능을 수행
- @Around(메서드 실행 전후) : advice가 타겟 메서드를 감싸서 타겟 메서드 호출 전과 후에 advice기능을 수행