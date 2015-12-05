package movies.spring.data.neo4j.repositories;

import movies.spring.data.neo4j.domain.Movie;
import org.springframework.data.neo4j.annotation.Query;
import org.springframework.data.neo4j.repository.GraphRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.Collection;
import java.util.List;
import java.util.Map;

/**
 * @author mh
 * @since 24.07.12
 */
// tag::repository[]
@RepositoryRestResource(collectionResourceRel = "movies", path = "movies")
public interface MovieRepository extends GraphRepository<Movie> {
    Movie findByTitle(@Param("title") String title);

    @Query("MATCH (m:Movie) WHERE m.title =~ ('(?i).*'+{title}+'.*') RETURN m")
    Collection<Movie> findByTitleContaining(@Param("title") String title);

    @Query("MATCH (m:Movie)<-[:ACTED_IN]-(a:Person) RETURN m.title as movie, collect(a.name) as cast LIMIT {limit}")
    List<Map<String,Object>> graph(@Param("limit") int limit);
}
// end::repository[]

