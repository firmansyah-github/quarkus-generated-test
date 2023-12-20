// modify by the factor : Jan 29, 2024, 10:04:05 AM  
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
