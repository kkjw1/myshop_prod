package myshop.shop.filter;

import jakarta.servlet.*;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.MDC;

import java.io.IOException;
import java.util.UUID;

@Slf4j
public class LogbackFilter implements Filter {

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String uuid = UUID.randomUUID().toString();
        MDC.put("requestId", uuid);

        try {
            chain.doFilter(request, response);
            MDC.clear();
        } catch (Exception e) {
            throw e;
        }
    }

}
