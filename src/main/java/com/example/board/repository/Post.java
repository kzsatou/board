package com.example.board.repository;


import java.util.Date;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Data;

/**
 * 投稿.
 */
@Entity
@Table(name = "post")
@Data
public class Post {

    /** ID */
    @Id
    @Column
    @NotNull
    private String id = null;

    /** 投稿者 */
    @NotEmpty
    @Size(min = 1, max = 20)
    @Column(length = 20, nullable = false)
    private String author = null;

    /** タイトル */
    @NotEmpty
    @Size(min = 1, max = 20)
    @Column(length = 20, nullable = false)
    private String title = null;

    /** 内容 */
    @NotEmpty
    @Size(min = 1, max = 1000)
    @Column(length = 1000, nullable = false)
    private String body = null;

    /** 登録日時 */
    private Date createdDate = null;

    /** 更新日時 */
    private Date updatedDate = null;

    /** 削除済 */
    private boolean deleted = false;

}
