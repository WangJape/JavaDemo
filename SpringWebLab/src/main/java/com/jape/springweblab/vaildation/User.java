package com.jape.springweblab.vaildation;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.io.Serializable;

/**
 * @author Jape
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
public class User implements Serializable {

        @NotEmpty
        private String userName;

        @NotEmpty
        private String sex;

        @NotNull
        @Email
        @Size(min = 10)
        private String email;

        @NotNull(message = "interest不能null")
        private String interest;
}
