package com.work.team5.model;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "prompts")
@Getter
@Setter
@NoArgsConstructor
public class Prompt {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String promptText;

    @Column(nullable = false, columnDefinition = "TEXT")
    private String responseText;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;

    public Prompt(User user, String promptText, String responseText) {
        this.user = user;
        this.promptText = promptText;
        this.responseText = responseText;
        this.createdAt = LocalDateTime.now();
    }
}
