package com.DoAnTotNghiep.config.security;

import com.DoAnTotNghiep.core.auth.service.UserDetailsServiceImpl;
import io.jsonwebtoken.Claims;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.util.StringUtils;
import org.springframework.web.filter.OncePerRequestFilter;
import org.springframework.web.util.WebUtils;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.util.Arrays;
import java.util.stream.Collectors;

public class JwtAuthenticationFilter extends OncePerRequestFilter {

    @Autowired
    JwtTokenProvider jwtTokenProvider;

    @Autowired
    UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
//        HttpSession session = request.getSession();

        Cookie[] cookies = request.getCookies();
        if (cookies != null) {
            System.out.println(Arrays.stream(cookies)
                    .map(c -> c.getName() + "=" + c.getValue()).collect(Collectors.joining(", ")));
        } else {
            System.out.println("No cookies");
        }

        try {
            String jwt = null;
            Cookie cookie = WebUtils.getCookie(request, "jwt");
            if (cookie != null) {
                jwt = cookie.getValue();
            }
//            jwt = (String) session.getAttribute("jwt");
            if (StringUtils.hasText(jwt)) {
                jwtTokenProvider.validateToken(jwt);
                Claims claims = jwtTokenProvider.getUserFromJWT(jwt);
                String username = (String) claims.get("username");
                UserDetails userDetails = userDetailsService.loadUserByUsername(username);
                UsernamePasswordAuthenticationToken authenticationToken = new UsernamePasswordAuthenticationToken
                        (userDetails, null, userDetails.getAuthorities());
                authenticationToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authenticationToken);
                HttpSession session = request.getSession();
                session.setAttribute("username", username);
            }
        } catch (Exception ex) {
            System.out.println("Không thành công khi xác thực người dùng");
            //throw new UnauthorizedException("Fail!!!");
        }
        filterChain.doFilter(request, response);
    }
}
