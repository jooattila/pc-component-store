package edu.bbte.idde.jaim1826.spring.model.dto.mapper;

import edu.bbte.idde.jaim1826.spring.model.User;
import edu.bbte.idde.jaim1826.spring.model.dto.incoming.UserReqDto;
import edu.bbte.idde.jaim1826.spring.model.dto.outgoing.UserResDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

import java.util.Collection;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public abstract class UserMapper {
    public abstract User userDtoToUser(UserReqDto userReqDto);

    public abstract UserResDto userToUserDto(User user);

    public abstract Collection<UserResDto> userListToDto(Collection<User> users);
}
