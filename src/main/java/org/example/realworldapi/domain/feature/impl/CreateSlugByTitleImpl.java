// modify by the factor : Jan 29, 2024, 10:04:05 AM  
package org.example.realworldapi.domain.feature.impl;

import lombok.AllArgsConstructor;

import java.util.UUID;

import org.example.realworldapi.domain.feature.CreateSlugByTitle;
import org.example.realworldapi.domain.model.article.ArticleRepository;
import org.example.realworldapi.domain.model.provider.SlugProvider;



@AllArgsConstructor
public class CreateSlugByTitleImpl implements CreateSlugByTitle {

  private final ArticleRepository articleRepository;
  private final SlugProvider slugProvider;

  @Override
  public String handle(String title) {
    String slug = slugProvider.slugify(title);
    if (articleRepository.existsBySlug(slug)) {
      slug += UUID.randomUUID().toString();
    }
    return slug;
  }
}
