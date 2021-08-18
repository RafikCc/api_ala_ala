package com.logistic.api.payload.response;

import java.io.Serializable;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@ToString
public class AuthResponse implements Serializable {

    private static final long serialVersionUID = 1L;

    private String token;
}
