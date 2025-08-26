package com.springkafka.demokafka.AOP;

import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

/**
 * @Project: demokafka
 * @Package: com.springkafka.demokafka.AOP  *
 * @Author: ChuVanNam
 * @Date: 8/24/2025
 * @Time: 8:40 AM
 */

@Aspect
@Component
@Slf4j
public class LoggingAOP {
    private final ObjectMapper objectMapper = new ObjectMapper();
    // Decorator Pattern
    // Aspect Oriented Programming (AOP) - Lập trình hướng khía cạnh
    //import org.aspectj.lang.annotation.Around;
    //import org.aspectj.lang.annotation.Aspect;
    //import org.aspectj.lang.annotation.Pointcut;
    // @Around("execution(* com.springkafka.demokafka..*(..))") // Chỉ định điểm cắt cho tất cả các phương thức trong package com.springkafka.demokafka
    // @Pointcut("execution(* com.springkafka.demokafka..*(..))")
    // @Around("pointcut()") // Sử dụng điểm cắt đã định nghĩa
    // @Pointcut("execution(* com.springkafka.demokafka..*(..))") // Định nghĩa điểm cắt cho tất cả các phương thức trong package com.springkafka.demokafka

    /**
     * Phương thức này sẽ được gọi trước và sau khi thực thi bất kỳ phương thức nào trong package com.springkafka.demokafka
     *
     * @param joinPoint đại diện cho điểm nối của phương thức đang được thực thi
     * @return kết quả của phương thức đã được thực thi
     * @throws Throwable nếu có lỗi xảy ra trong quá trình thực thi
     */
//    @Around("@annotation(com.springkafka.demokafka.AOP.TrackExecutionTime)" +
//            " || execution(* com.springkafka.demokafka..*(..))" +
//            " || @within(com.springkafka.demokafka.AOP.TrackExecutionTime)" +
//            " || @target(com.springkafka.demokafka.AOP.TrackExecutionTime)" +
//            " || @within(org.springframework.stereotype.Service)" +
//            " || @within(org.springframework.stereotype.Repository)" +
//            " || @within(org.springframework.web.bind.annotation.RestController)")
    // @annotation(com.springkafka.demokafka.AOP.TrackExecutionTime) sẽ áp dụng cho các phương thức có annotation @TrackExecutionTime
    // || execution(* com.springkafka.demokafka..*(..)) sẽ áp dụng cho tất cả các phương thức trong package com.springkafka.demokafka
    // || @within(com.springkafka.demokafka.AOP.TrackExecutionTime) sẽ áp dụng cho các lớp có annotation @TrackExecutionTime
    // || @target(com.springkafka.demokafka.AOP.TrackExecutionTime) sẽ áp dụng cho các lớp có annotation @TrackExecutionTime
    // || @within(org.springframework.stereotype.Service) sẽ áp dụng cho các lớp có annotation @Service
    // || @within(org.springframework.stereotype.Repository) sẽ áp dụng cho các lớp có annotation @Repository
    // || @within(org.springframework.web.bind.annotation.RestController) sẽ áp dụng cho các lớp có annotation @RestController
    // execution khác với @annotation, @within, @target là gì?
// execution là một biểu thức định nghĩa điểm cắt dựa trên việc thực thi các phương thức, trong khi @annotation, @within, @target là các chú thích (annotations) được sử dụng để xác định điểm cắt dựa trên các thuộc tính của lớp hoặc phương thức.
    @Around("@annotation(com.springkafka.demokafka.AOP.TrackExecutionTime)")
    public Object logExecutionTime(org.aspectj.lang.ProceedingJoinPoint joinPoint) throws Throwable {
        // Lấy thời gian bắt đầu
        long start = System.currentTimeMillis();
        // joinPoint.proceed() sẽ thực thi phương thức thực tế mà AOP đang bọc
        Object proceed = joinPoint.proceed();
        // Tính toán thời gian thực thi
        long executionTime = System.currentTimeMillis() - start;

        // Ghi log thời gian thực thi
        log.info("Method {} executed in {} ms", joinPoint.getSignature(), executionTime);
        // Trả về kết quả của phương thức đã được thực thi
        return proceed;
    }

    @Around("@annotation(com.springkafka.demokafka.AOP.AdditionalRequestTrack)")
    public Object logRequestAndResponse(ProceedingJoinPoint joinPoint) throws Throwable {
        log.info("===============================================================================");
        ServletRequestAttributes attributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = attributes.getRequest();

        String uri = request.getRequestURI();
        String method = request.getMethod();

        // Lấy tham số truyền vào
        Object[] args = joinPoint.getArgs();
        // Tuỳ vào method mà lấy params hay body
        String params = args.length > 0 ? objectMapper.writeValueAsString(args[0]) : "No parameters";
        log.info("Request: {} {} with params: {}", method, uri, params);
        // Thực thi phương thức thực tế
        Object result = joinPoint.proceed();
        // Ghi log kết quả trả về
        String response = result != null ? objectMapper.writeValueAsString(result) : "No response";
        log.info("Response: {}", response);
        log.info("===============================================================================");
        // Trả về kết quả của phương thức đã được thực thi
        return result;
    }
}
