// modify by the factor : Feb 22, 2024, 10:16:04 PM  
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
