package io.irwansyahdev96.readcollection.filter;

import com.fasterxml.jackson.databind.ObjectMapper;

import io.irwansyahdev96.readcollection.util.JwtUtil;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.util.matcher.RequestMatcher;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Component
public class SecurityServletFilter extends OncePerRequestFilter{

    @Autowired
    private JwtUtil jwtService;

    @Autowired
    private List<RequestMatcher> requestMatchers;

    @Autowired
    private ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {

        Long count = requestMatchers.stream().filter(m -> m.matches(request)).collect(Collectors.counting());

        if(!request.getRequestURI().equals("login") && count == 0) {
            final String header = request.getHeader("Authorization");
            final String[] partHead = header.split(" ");

            try {
                final Map<String,Object> parse = jwtService.parse(partHead[1]);

//				final GrantedAuthority authority = new SimpleGrantedAuthority(parse.get("rc").toString());
//				final List<GrantedAuthority> authorities = Arrays.asList(authority);

//				final Authentication authentication = new UsernamePasswordAuthenticationToken(parse.get("id"), null,
//						authorities);

                final Authentication authentication = new UsernamePasswordAuthenticationToken(parse.get("id"), null);

                SecurityContextHolder.getContext().setAuthentication(authentication);
            }catch(Exception e) {
                e.printStackTrace();

                response.setContentType("application/json");
                response.setStatus(HttpStatus.UNAUTHORIZED.value());

                return;
            }

            header.split(header);
        }

        filterChain.doFilter(request,response);
    }
}
