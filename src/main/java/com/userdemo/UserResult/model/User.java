package com.userdemo.UserResult.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
public class User {

    @NonNull
    private Integer user_id;

    @NonNull
    private Integer level_id;

    @NonNull
    private Integer result;
}
