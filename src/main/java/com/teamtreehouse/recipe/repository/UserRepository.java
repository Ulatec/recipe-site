package com.teamtreehouse.recipe.repository;

        import com.teamtreehouse.recipe.model.User;
        import org.springframework.data.repository.CrudRepository;
        import org.springframework.data.rest.core.annotation.RepositoryRestResource;
        import org.springframework.stereotype.Repository;

@RepositoryRestResource
public interface UserRepository extends CrudRepository<User, Long> {
    User findByUsername(String username);
}
