package com.springkafka.demokafka.config;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.logging.log4j.ThreadContext;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.UUID;

@Component
public class RequestIdFilter extends OncePerRequestFilter {
    @Override
    // Mỗi request sẽ có một traceId riêng, lưu trong MDC (ThreadContext của Log4j2)
    // Ghi log trong cùng một request sẽ có cùng traceId để dễ theo dõi
    // Ngoài ra còn lưu method, path để tiện theo dõi
    // doFilterInternal là phương thức trừu tượng của lớp OncePerRequestFilter, được gọi một lần cho mỗi yêu cầu HTTP.
    // Nó nhận vào ba tham số: HttpServletRequest (yêu cầu HTTP), Http ServletResponse (phản hồi HTTP), và FilterChain (chuỗi các bộ lọc).
    // Phương thức này cho phép bạn thực hiện các thao tác xử lý trước và sau khi yêu cầu được chuyển tiếp qua chuỗi bộ lọc.
    // Trong ví dụ này, nó được sử dụng để thêm thông tin vào ThreadContext (MDC) trước khi tiếp tục chuỗi bộ lọc và xóa thông tin đó sau khi hoàn thành.
    // Việc này giúp theo dõi và ghi log chi tiết cho mỗi yêu cầu HTTP.
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        String traceId = UUID.randomUUID().toString();
        try {
            ThreadContext.put("traceId", traceId);
            ThreadContext.put("method", request.getMethod());
            ThreadContext.put("path", request.getRequestURI());
            filterChain.doFilter(request, response);
        } finally {
            ThreadContext.clearAll();
        }
    }
}
