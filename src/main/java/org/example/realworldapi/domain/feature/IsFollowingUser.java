// modify by the factor : Feb 22, 2024, 10:16:04 PM  
package org.example.realworldapi.domain.feature;



public interface IsFollowingUser {
  boolean handle(String currentUserId, String followedUserId);
}
