// modify by the factor : Feb 13, 2024, 4:06:34 PM  
package org.example.realworldapi.domain.feature;



public interface IsFollowingUser {
  boolean handle(String currentUserId, String followedUserId);
}
