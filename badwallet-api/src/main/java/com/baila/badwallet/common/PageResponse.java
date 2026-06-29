package com.baila.badwallet.common;

import org.springframework.data.domain.Page;

import java.util.List;

/**
 * DTO de pagination renvoyé au client : contenu + métadonnées (page, taille, totaux).
 * Évite d'exposer directement l'objet Page de Spring Data.
 */
public record PageResponse<T>(
        List<T> content,
        int page,
        int size,
        long totalElements,
        int totalPages,
        boolean first,
        boolean last
) {

    public static <T> PageResponse<T> fromPage(Page<T> page) {
        return new PageResponse<>(
                page.getContent(),
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages(),
                page.isFirst(),
                page.isLast());
    }
}
