package edu.bbte.idde.jaim1826.spring.model.dto.mapper;

import edu.bbte.idde.jaim1826.spring.model.dto.incoming.ComponentReqDto;
import edu.bbte.idde.jaim1826.spring.model.dto.outgoing.ComponentResDto;
import edu.bbte.idde.jaim1826.spring.model.Component;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class ComponentMapper {

    public abstract Component componentDtoToComponent(ComponentReqDto componentReqDto);

    public abstract ComponentResDto componentToComponentDto(Component component);

    public abstract Collection<ComponentResDto> componentListToDto(Collection<Component> components);

}
