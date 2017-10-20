//package oi.digital.chatbot.aspect;
//
//import com.fasterxml.jackson.core.JsonProcessingException;
//import com.fasterxml.jackson.databind.ObjectMapper;
//import lombok.extern.slf4j.Slf4j;
//import org.aspectj.lang.JoinPoint;
//import org.aspectj.lang.annotation.AfterReturning;
//import org.aspectj.lang.annotation.Aspect;
//import org.aspectj.lang.annotation.Before;
//import org.aspectj.lang.annotation.Pointcut;
//import org.springframework.scheduling.annotation.Async;
//import org.springframework.stereotype.Component;
//
///**
// * Created by daniel on 09/10/17.
// */
//@Aspect
//@Component
//@Slf4j
//public class ChatbotAspect {
//
//    @Pointcut("execution(* oi.digital.chatbot.controller.ChatBotController.chatbot(..))")
//    public void controllerPointCut(){
//
//    }
//
//    @Before("controllerPointCut() && args(..,request)")
//    public void before(JoinPoint joinPoint, Object request) throws Throwable {
//        logDispatch(request);
//    }
//
//    @AfterReturning(pointcut = "controllerPointCut()", returning = "result")
//    public void after(JoinPoint joinPoint, Object result) throws Throwable {
//        logDispatch(result);
//    }
//
//    @Async
//    private void logDispatch(Object object) throws JsonProcessingException {
//        ObjectMapper objectMapper = new ObjectMapper();
//        log.debug(objectMapper.writeValueAsString(object));
//    }
//
////    @AfterReturning(pointcut = "controllerPointCut() && args(..,request)", returning = "result")
////    public void after2(JoinPoint joinPoint, Object request, Object result) throws Throwable {
//////        Object proceed = joinPoint.proceed();
////        logDispatch(result);
//////        return proceed;
////    }
//}
