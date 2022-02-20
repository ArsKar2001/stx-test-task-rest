package com.stx.domains.mappers;

import com.stx.domains.dtos.ArtifactCreateRequest;
import com.stx.domains.dtos.ArtifactDto;
import com.stx.domains.dtos.ArtifactUpdateRequest;
import com.stx.domains.models.Artifact;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class ArtifactMapper implements ModelDTOMapper<Artifact, ArtifactDto> {
    private final ModelMapper mapper;

    public ArtifactMapper(ModelMapper mapper) {
        this.mapper = mapper;
    }

    @Override
    public ArtifactDto toDTO(Artifact t) {
        return mapper.map(t, ArtifactDto.class);
    }

    @Override
    public Artifact toModel(ArtifactDto f) {
        return mapper.map(f, Artifact.class);
    }

    public Artifact create(ArtifactCreateRequest request) {
        return mapper.map(request, Artifact.class);
    }

    public void update(Artifact artifact, ArtifactUpdateRequest request) {
        artifact.setCategory(request.getCategory());
        artifact.setDescription(request.getDescription());
    }

    public List<ArtifactDto> toDTO(List<Artifact> artifacts) {
        return artifacts.stream()
                .map(this::toDTO)
                .toList();
    }
}
