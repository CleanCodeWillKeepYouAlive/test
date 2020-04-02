package com.userdemo.UserResult.model;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.NonNull;

@Data
@EqualsAndHashCode
public class User {

    @NonNull
    private Integer id;

    @NonNull
    private Integer level;

    @NonNull
    private Integer result;
}
