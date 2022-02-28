package com.surabhi.taskapp.filters;

import com.surabhi.taskapp.MyUserDetailsService;
import com.surabhi.taskapp.UserDetail;
import com.surabhi.taskapp.util.JwtUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
@Component
@Slf4j
public class JwtRequestFilter extends OncePerRequestFilter {

    private final MyUserDetailsService myUserDetailsServiceImpl;


    private final JwtUtil jwtUtil;

    public JwtRequestFilter(MyUserDetailsService myUserDetailsServiceImpl, JwtUtil jwtUtil) {
        this.myUserDetailsServiceImpl = myUserDetailsServiceImpl;
        this.jwtUtil = jwtUtil;
    }



    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {
        try {
            log.info("operation = doFilterInternal, status = IN_PROGRESS, message =do filter internal");
            final String authorizationHeader = request.getHeader("Authorization");
            String email = null;
            String jwt = null;
            if (authorizationHeader != null && authorizationHeader.startsWith("Bearer ")) {
                jwt = authorizationHeader.substring(7);
                email = jwtUtil.extractUseremail(jwt);
            }
            if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {
                UserDetail userDetail = this.myUserDetailsServiceImpl.loadUserByUseremail(email);

                ServletContext servletContext = request.getServletContext();
                servletContext.setAttribute("user", userDetail);

                if (jwtUtil.validateToken(jwt, userDetail)) {
                    UsernamePasswordAuthenticationToken usernamePasswordAuthenticationToken = new UsernamePasswordAuthenticationToken(
                            userDetail, null, userDetail.getAuthorities());
                    usernamePasswordAuthenticationToken
                            .setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                    SecurityContextHolder.getContext().setAuthentication(usernamePasswordAuthenticationToken);
                }
            }
            filterChain.doFilter(request, response);
            log.info("operation = doFilterInternal, status =SUCCESS, message =do filter internal");
        } catch (Exception e) {
            log.error("operation =doFilterInternal, status = ERROR, msg = error in doFilterInternal method", e);
            e.printStackTrace();
        }

    }
}
