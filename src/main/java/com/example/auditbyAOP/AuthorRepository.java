package com.example.auditbyAOP;

import org.springframework.data.jpa.repository.JpaRepository;
@AUDITABLE_REPO
public interface AuthorRepository extends JpaRepository<Author, Integer> {

}
