// modify by the factor : Feb 13, 2024, 4:06:34 PM  
package org.example.realworldapi.domain.model.user;

import lombok.AllArgsConstructor;
import lombok.Data;



@Data
@AllArgsConstructor
public class UpdateUserInput {
    private String id;
    private String username;
    private String bio;
    private String image;
    private String email;
    private String password;
}
