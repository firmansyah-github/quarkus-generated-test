package org.example.realworldapi.domain.feature;

import org.example.realworldapi.domain.model.tagRelationship.TagRelationship;
import java.util.List;



public interface FindTagRelationshipByForeignKey {
  
	List<TagRelationship> handleForTagId(java.lang.String TagId);
	List<TagRelationship> handleForArticleId(java.lang.String ArticleId);
}

