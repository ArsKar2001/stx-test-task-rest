package com.stx.domains.models;

import javax.persistence.Entity;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;

@Entity
@Table(schema = "PUBLIC", name = "COMMENTS")
public class Comment extends AbstractEntity implements Serializable {
    @ManyToOne
    @JoinColumn(name = "USER_ID")
    private User user;

    @ManyToOne
    @JoinColumn(name = "ARTIFACT_ID")
    private Artifact artifact;

    public void setUser(User user) {
        this.user = user;
    }

    public void setArtifact(Artifact artifact) {
        this.artifact = artifact;
    }

    private String content;

    public Artifact getArtifact() {
        return artifact;
    }

    public User getUser() {
        return user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
