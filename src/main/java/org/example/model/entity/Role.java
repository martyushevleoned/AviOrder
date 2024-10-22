package org.example.model.entity;

import org.springframework.security.core.GrantedAuthority;

public enum Role implements GrantedAuthority {

    /**
     * Пользователь.
     * Имеет возможность создать заказы и редактировать собственные заказы.
     */
    USER,

    /**
     * Сотрудник.
     * Имеет доступ к редактированию любых заказов, а не только собственных.
     */
    EMPLOYEE,

    /**
     * Админ.
     * Имеет доступ только к инструментам учёта и статистики.
     */
    ADMIN;

    @Override
    public String getAuthority() {
        return name();
    }
}
