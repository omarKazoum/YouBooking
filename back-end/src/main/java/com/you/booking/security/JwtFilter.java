package com.you.booking.security;

import com.you.booking.service.implementations.UserDetailsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class JwtFilter extends OncePerRequestFilter {
    @Autowired
    private UserDetailsService userDetailsService;
    @Autowired
    private JwtUtil jwtUtil;
    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String authorisationHeader=request.getHeader("Authorization");
        if(authorisationHeader!=null &&!authorisationHeader.isBlank() && authorisationHeader.startsWith("Bearer ")){
            String jwt=authorisationHeader.substring(7);
            if(jwt==null || jwt.isBlank()){
                response.sendError(HttpServletResponse.SC_BAD_REQUEST,"invalid jwt token");
            }else {
                try{
                    String email=jwtUtil.validateTokenAndRetrieveSubject(jwt);
                    UserDetails userDetails=userDetailsService.loadUserByUsername(email);
                    UsernamePasswordAuthenticationToken authToken=new UsernamePasswordAuthenticationToken(email,userDetails.getPassword(),userDetails.getAuthorities());
                            if(SecurityContextHolder.getContext().getAuthentication()==null){
                                SecurityContextHolder.getContext().setAuthentication(authToken);
                            }

                }catch (Exception e){
                    e.printStackTrace();
                    response.sendError(HttpServletResponse.SC_BAD_REQUEST,"invalid jwt token");
                }
            }
        }
        filterChain.doFilter(request,response);
    }
}
