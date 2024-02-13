// modify by the factor : Feb 13, 2024, 4:06:34 PM  
package org.example.realworldapi.domain.feature;



public interface UnfollowUserByUsername {
  void handle(String loggedUserId, String username);
}
