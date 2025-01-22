package com.example.socialmedia.Entities;

import com.example.socialmedia.Enums.ReactionType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.PrePersist;
import jakarta.persistence.PreUpdate;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "reactionss")
public class Reaction {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private ReactionType type;

    @ManyToOne
    @Column(name = "post_id", updatable = false)
    private Post post;

    @ManyToOne
    @Column(name = "comment_id", updatable = false)
    private Comment comment;

    @ManyToOne
    @JoinColumn(name = "user_id", updatable = false)
    private User user;

    @PrePersist
    @PreUpdate
    private void validateReactionTarget() {
        if ((post == null && comment == null) || (post != null && comment != null)) {
            throw new IllegalArgumentException(
                    "A reaction must be associated with either a post or a comment, but not both.");
        }
    }
}
