// modify by the factor : Jan 29, 2024, 10:04:05 AM  
package org.example.realworldapi.domain.feature;



public interface UnfollowUserByUsername {
  void handle(String loggedUserId, String username);
}
