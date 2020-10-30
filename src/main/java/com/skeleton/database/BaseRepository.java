package com.skeleton.database;

import com.skeleton.model.BaseModel;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface BaseRepository<model extends BaseModel<ID>, ID extends Serializable>
        extends JpaRepository<model, ID> {

    @NotNull
    @Override
    Page<model> findAll(@NotNull Pageable pageable);
}
