package model;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * @author admin
 * @data 2020/8/24
 */
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Person implements Serializable {
    private static final long serialVersionUID = 3829252771168681281L;

    private Integer id;
    private String username;
    private String tel;
}
