package com.userdemo.UserResult.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode
public class User {

    @NonNull
    private Integer user_id;

    @NonNull
    private Integer level_id;

    @NonNull
    private Integer result;
}
