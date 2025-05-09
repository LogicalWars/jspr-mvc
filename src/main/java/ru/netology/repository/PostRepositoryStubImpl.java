package ru.netology.repository;

import org.springframework.stereotype.Repository;
import ru.netology.exception.NotFoundException;
import ru.netology.model.Post;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicLong;

@Repository
public class PostRepositoryStubImpl implements PostRepository {

    private final ConcurrentHashMap<Long, Post> posts = new ConcurrentHashMap<>();
    private final AtomicLong idGenerator = new AtomicLong();

    public List<Post> all() {
        return new ArrayList<>(posts.values());
    }

    public Optional<Post> getById(long id) {
        return Optional.ofNullable(posts.get(id));
    }

    public Post save(Post post) throws NotFoundException {
        if (!posts.containsKey(post.getId()) && post.getId() != 0) {
            throw new NotFoundException("Post not found");
        } else {
            if (post.getId() == 0) {
                long id = idGenerator.incrementAndGet();
                post.setId(id);
            }
            posts.put(post.getId(), post);
            return post;
        }
    }

    public void removeById(long id) {
        posts.remove(id);
    }
}
