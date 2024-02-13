// created by the factor : Feb 13, 2024, 4:07:37 PM  
package firmansyah.domain.feature;


public interface DeleteFollowRelationship {
	boolean handle(String followedId, String userId);
}
