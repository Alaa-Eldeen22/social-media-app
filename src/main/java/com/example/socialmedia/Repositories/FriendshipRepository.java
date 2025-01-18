package com.example.socialmedia.Repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.socialmedia.Entities.Friendship;
import com.example.socialmedia.Entities.User;
import com.example.socialmedia.Enums.FriendshipStatus;
import java.util.Optional;

public interface FriendshipRepository extends JpaRepository<Friendship, Long> {

    List<Friendship> findByRequesterAndStatus(User requester, FriendshipStatus status);

    List<Friendship> findByReceiverAndStatus(User receiver, FriendshipStatus status);

    boolean existsByRequesterAndReceiver(User requester, User receiver);

    Optional<Friendship> findByRequesterAndReceiver(User requester, User receiver);

    List<Friendship> findByRequesterOrReceiver(User requester, User receiver);

}
