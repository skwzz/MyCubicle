package org.skwzz.global.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;
import org.skwzz.global.annotation.RoleLevel;
import org.skwzz.global.enums.MemberRole;
import org.springframework.security.access.AccessDeniedException;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class RoleLevelAspect {

    @Pointcut("@annotation(roleLevel)")
    public void roleLevelPointcut(RoleLevel roleLevel) {}

    @Before("roleLevelPointcut(roleLevel)")
    public void checkRoleLevel(JoinPoint joinPoint, RoleLevel roleLevel) throws Throwable {
        // 현재 인증된 사용자 정보를 가져옵니다.
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();

        if (authentication == null || !authentication.isAuthenticated()) {
            throw new IllegalAccessException("인증되지 않은 사용자입니다.");
        }

        // 사용자의 권한을 가져옵니다.
        String roleName = authentication.getAuthorities().stream()
                .map(grantedAuthority -> grantedAuthority.getAuthority())
                .findFirst()
                .orElseThrow(() -> new IllegalAccessException("사용자의 권한을 찾을 수 없습니다."));

        // 사용자 권한 레벨을 찾습니다.
        MemberRole userRole = MemberRole.valueOf(roleName);
        int userLevel = userRole.getLevel();

        // 요청된 권한 레벨과 비교합니다.
        if (userLevel > roleLevel.value()) {
            throw new AccessDeniedException("접근 권한이 없습니다. 필요한 권한 레벨: " + roleLevel.value());
        }
    }
}
