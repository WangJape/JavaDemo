package com.jape.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author Administrator
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {
    private String userName;
    private String sex;
    private String leader;
    private String interest;
    private String subInterest;
    private String words;
}
