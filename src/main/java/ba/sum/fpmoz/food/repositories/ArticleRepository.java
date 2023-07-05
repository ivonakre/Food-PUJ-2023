package ba.sum.fpmoz.food.repositories;

import ba.sum.fpmoz.food.model.Article;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ArticleRepository extends JpaRepository<Article, Long> {}