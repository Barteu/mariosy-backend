package com.company.mariosy.DTO;

import com.company.mariosy.entity.MariosType;

import java.time.Instant;
import java.util.List;
import java.util.Set;
import java.util.UUID;

public class MariosDTO {


    private UUID externalId;
    private UUID creatorExternalId;
    private Set<UUID> receiversExternalIds;
    private String title;

    private String comment;
    private MariosType type;
    private Instant creationInstant;

    private String additionalMessage;

    private String creatorUsername;

    private List<String> receiversNames;

    public UUID getExternalId() {
        return externalId;
    }

    public void setExternalId(UUID externalId) {
        this.externalId = externalId;
    }

    public UUID getCreatorExternalId() {
        return creatorExternalId;
    }

    public void setCreatorExternalId(UUID creatorId) {
        this.creatorExternalId = creatorId;
    }

    public Set<UUID> getReceiversExternalIds() {
        return receiversExternalIds;
    }

    public void setReceiversExternalIds(Set<UUID> receiversIds) {
        this.receiversExternalIds = receiversIds;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public MariosType getType() {
        return type;
    }

    public void setType(MariosType type) {
        this.type = type;
    }

    public Instant getCreationInstant() {
        return creationInstant;
    }

    public void setCreationInstant(Instant creationInstant) {
        this.creationInstant = creationInstant;
    }

    public String getAdditionalMessage() {
        return additionalMessage;
    }

    public void setAdditionalMessage(String additionalMessage) {
        this.additionalMessage = additionalMessage;
    }

    public String getCreatorUsername() {
        return creatorUsername;
    }

    public void setCreatorUsername(String creatorUsername) {
        this.creatorUsername = creatorUsername;
    }

    public List<String> getReceiversNames() {
        return receiversNames;
    }

    public void setReceiversNames(List<String> receiversNames) {
        this.receiversNames = receiversNames;
    }
}
