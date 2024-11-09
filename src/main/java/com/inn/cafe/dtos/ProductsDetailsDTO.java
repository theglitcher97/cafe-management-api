package com.inn.cafe.dtos;

public record ProductsDetailsDTO(
    Integer id,
    String name,
    String category,
    Integer price,
    Integer total,
    Integer quantity) {}
