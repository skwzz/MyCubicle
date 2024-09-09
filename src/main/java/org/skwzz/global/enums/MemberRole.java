package org.skwzz.global.enums;

public enum MemberRole {
    ROLE_ADMIN("ADMIN", 1),
    ROLE_MANAGER("MANAGER", 2),
    ROLE_USER("USER", 3);

    private final String title;   // 권한 설명 (명칭)
    private final int level;      // 권한 레벨 (1이 가장 높은 권한)

    MemberRole(String title, int level) {
        this.title = title;
        this.level = level;
    }

    public String getTitle() {
        return title;
    }

    public int getLevel() {
        return level;
    }
}
