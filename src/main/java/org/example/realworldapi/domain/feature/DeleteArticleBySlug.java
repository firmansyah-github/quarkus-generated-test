// modify by the factor : Feb 13, 2024, 4:06:34 PM  
package org.example.realworldapi.domain.feature;



public interface DeleteArticleBySlug {
  void handle(String authorId, String slug);
}
