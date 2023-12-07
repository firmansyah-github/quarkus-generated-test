// modify by the factor : Dec 7, 2023, 4:02:02 PM  
package org.example.realworldapi.domain.feature;



public interface UnfollowUserByUsername {
  void handle(String loggedUserId, String username);
}
